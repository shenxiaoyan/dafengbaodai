package com.liyang.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Transient;
import javax.transaction.Transactional;

import org.apache.coyote.http11.InputFilter;
import org.bouncycastle.math.raw.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyang.domain.department.Department;
import com.liyang.domain.department.DepartmentRepository;
import com.liyang.domain.role.Role;
import com.liyang.domain.role.RoleRepository;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserAct;
import com.liyang.domain.user.UserRepository;
import com.liyang.domain.user.UserState;
import com.liyang.domain.user.UserStateRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.util.CommonUtil;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ReturnObject;
import com.liyang.util.TIMSignature;
import com.liyang.util.TreeNode;
import com.liyang.util.TreeNodeImpl;
import com.liyang.util.WechatProperties;

/**
 * @author Administrator
 *
 */
@Service
public class WechatLoginService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private WechatPubService wechatPubService;
	@Autowired
	private WechatProperties properties;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserStateRepository userStateRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private TIMService timService;

	private final static Pattern EMOJI = Pattern.compile(
			"[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
			Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

	// @Autowired
	// private OrganizationRoleRepository organizationRoleRepository;

	public void connect(Model model) {
		setupAttribute(model);
		UUID randomUUID = UUID.randomUUID();
		String state = randomUUID.toString();
		model.addAttribute("state", state);
		cacheManager.getCache("wechatLogin").put(state, "login:0");
	}

	/**
	 * 微信登录
	 * 
	 * @return
	 */
	public String webLogin() {
		UUID randomUUID = UUID.randomUUID();
		String state = randomUUID.toString();
		cacheManager.getCache("wechatLogin").put(state, "wechat:0");
		return state;
	}

	public void apply(Model model, User fromUser) {
		setupAttribute(model);
		UUID randomUUID = UUID.randomUUID();
		String state = randomUUID.toString();
		model.addAttribute("state", state);
		if (fromUser == null) {
			cacheManager.getCache("wechatLogin").put(state, "apply:0");
		} else {
			cacheManager.getCache("wechatLogin").put(state, "apply:" + fromUser.getId().toString());
		}
	}

	public void bind(Model model) {
		setupAttribute(model);
		UUID randomUUID = UUID.randomUUID();
		String state = randomUUID.toString();
		model.addAttribute("state", state);
		cacheManager.getCache("wechatLogin").put(state, "bind:" + CommonUtil.getPrincipal().getId().toString());

	}

	private void setupAttribute(Model model) {
		model.addAttribute("appid", properties.getAppid());
		model.addAttribute("scope", properties.getScope());
		model.addAttribute("redirect_uri", properties.getRedirectUri());
		model.addAttribute("secret", properties.getSecret());

	}

	@Transactional
	public Authentication authorize(String code, String state) {

		ValueWrapper valueWrapper = cacheManager.getCache("wechatLogin").get(state);
		if (valueWrapper == null) {
			throw new FailReturnObject(ExceptionResultEnum.WECHAT_LOGIN_CACHE_ERROR);
			// throw new FailReturnObject(110, "缓存已经过期");
		}
		// state=webchatLogin 表示微信登录
		String str = cacheManager.getCache("wechatLogin").get(state, String.class);
		System.out.println(str);
		if (str.contains("wechat")) {
			System.out.println("-----------------------");
			User user = wechatPubService.callback(code);
			System.out.println(user.getNickname());
			return doLogin(user, "wechat");
		}

		Map token = getAccessToken(code);
		User existUser = userRepository.findByUnionid(token.get("unionid").toString());
		if (str.startsWith("bind:")) {
			if (existUser != null) {
				throw new FailReturnObject(ExceptionResultEnum.WECHAT_LOGIN_BINDING_ERROR, ReturnObject.Level.DISPLAY);
				// throw new FailReturnObject(1572, "微信已经被绑定过",
				// ReturnObject.Level.DISPLAY);
			}
			Integer userid = Integer.valueOf(str.substring(5));
			if (userid != null) {
				User own = userRepository.findOne(userid);
				own = fillOwnInfo(own, token);
				userRepository.save(own);
			}
			return null;
		} else if (str.startsWith("login:")) {
			return doLogin(existUser);

		} else if (str.startsWith("apply:")) {
			if (existUser != null) {
				throw new FailReturnObject(ExceptionResultEnum.WECHAT_EXIST_USER_ERROR);
				// throw new FailReturnObject(157, "用户已存在");
			}
			User applyUser = tokenToUser(token);
			UserAct actApply = userService.getAct(applyUser, "apply", applyUser.getRole());
			User fromUser = null;
			Integer userid = Integer.valueOf(str.substring(6));
			if (userid != null && !userid.equals(0)) {
				fromUser = userRepository.findOne(userid);
			}
			if (fromUser != null) {
				applyUser.setDepartment(fromUser.getDepartment());
			}

			applyUser = userService.doBeforeAct(applyUser, actApply, null, fromUser);
			applyUser = userRepository.save(applyUser);
			applyUser.setSig(TIMSignature.generate(applyUser.getId().toString()).urlSig);
			applyUser = userRepository.save(applyUser);
			timService.addUser(applyUser.getUnionid(), applyUser.getNickname(), applyUser.getHeadimgurl());
			userService.doAfterAct(applyUser, null, fromUser);
			return null;
		} else {
			throw new FailReturnObject(ExceptionResultEnum.WECHAT_LOGIN_CACHE_FORMAT_ERROR);
			// throw new FailReturnObject(175, "微信登录缓存格式不符");
		}

	}

	private User fillOwnInfo(User user, Map token) {
		Map info = getUserInfo(token.get("access_token").toString(), token.get("openid").toString());
		user.setOpenid(token.get("openid").toString());
		user.setUnionid(token.get("unionid").toString());
		user.setNickname(CommonUtil.filter(info.get("nickname").toString()));
		user.setSex(Integer.valueOf(info.get("sex").toString()));
		user.setProvince(info.get("province").toString());
		user.setCity(info.get("city").toString());
		user.setCountry(info.get("country").toString());
		user.setHeadimgurl(info.get("headimgurl").toString());
		return user;

	}

	private User tokenToUser(Map token) {
		Map info = getUserInfo(token.get("access_token").toString(), token.get("openid").toString());
		User user = new User();
		user.setOpenid(token.get("openid").toString());
		user.setUnionid(token.get("unionid").toString());
		user.setNickname(info.get("nickname").toString());
		user.setSex(Integer.valueOf(info.get("sex").toString()));
		user.setProvince(info.get("province").toString());
		user.setCity(info.get("city").toString());
		user.setCountry(info.get("country").toString());
		user.setHeadimgurl(info.get("headimgurl").toString());
		UserState state = userStateRepository.findByStateCode("UNBORN");
		user.setBeforeState(state);
		user.setSig(TIMSignature.generate(token.get("unionid").toString()).urlSig);
		// Role role =
		// roleRepository.findByRoleCode(Role.RoleCode.valueOf("USER"));
		Role role = roleRepository.findByRoleCode("USER");
		user.setRole(role);
		return user;
	}

	private User webchatTokenToUser(Map token) {
		Map info = getUserInfo(token.get("access_token").toString(), token.get("openid").toString());
		User user = new User();
		user.setOpenid(token.get("openid").toString());
		user.setUnionid(token.get("unionid").toString());
		user.setNickname(info.get("nickname").toString());
		user.setSex(Integer.valueOf(info.get("sex").toString()));
		user.setProvince(info.get("province").toString());
		user.setCity(info.get("city").toString());
		user.setCountry(info.get("country").toString());
		user.setHeadimgurl(info.get("headimgurl").toString());
		UserState state = userStateRepository.findByStateCode("ENABLED");
		user.setBeforeState(state);
		user.setSig(TIMSignature.generate(token.get("unionid").toString()).urlSig);
		// Role role =
		// roleRepository.findByRoleCode(Role.RoleCode.valueOf("USER"));
		Role role = roleRepository.findByRoleCode("USER");
		user.setRole(role);
		return user;
	}

	/**
	 * 微信登录
	 * 
	 * @param user
	 * @param wechat
	 * @return
	 */
	private Authentication doLogin(User user, String wechat) {
		if (user == null) {
			throw new FailReturnObject(ExceptionResultEnum.WECHAT_LOGIN_NOUSER_ERROR);
			// throw new FailReturnObject(155, "用户不存在");
		} else if ("DISABLED".equals(user.getState().getStateCode())) {
			throw new FailReturnObject(ExceptionResultEnum.WECHAT_LOGIN_USER_DISABLE_ERROR);
			// throw new FailReturnObject(160, "用户被禁用");
		} else if ("DELETED".equals(user.getState().getStateCode())) {
			throw new FailReturnObject(ExceptionResultEnum.WECHAT_LOGIN_USER_DELETE_ERROR);
			// throw new FailReturnObject(163, "用户被删除");
		} else if ("APPLIED".equals(user.getState().getStateCode())) {
			throw new FailReturnObject(ExceptionResultEnum.WECHAT_LOGIN_USER_AUDIT_ERROR);
			// throw new FailReturnObject(165, "用户正在被审核");
		}
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if (user.getRole() == null) {
			throw new FailReturnObject(ExceptionResultEnum.WECHAT_LOGIN_USER_ROLE_ERROR);
			// throw new FailReturnObject(1340, "用户没有角色");
		}
		authorities.add(new SimpleGrantedAuthority(user.getRole().getRoleCode().toString()));
		List<Department> ownAndChildrenDepartments = CommonUtil.ownAndChildrenDepartments(user.getDepartment());
		if (!ownAndChildrenDepartments.isEmpty()) {
			user.setChildrenDepartments(ownAndChildrenDepartments);
		}
		Authentication authentication = new UsernamePasswordAuthenticationToken(user, wechat, authorities);
		return authentication;
	}

	/**
	 * web前端登录
	 * 
	 * @param user
	 * @return
	 */
	private Authentication doLogin(User user) {
		if (user == null) {
			throw new FailReturnObject(ExceptionResultEnum.WECHAT_LOGIN_NOUSER_ERROR);
			// throw new FailReturnObject(155, "用户不存在");
		} else if ("DISABLED".equals(user.getState().getStateCode())) {
			throw new FailReturnObject(ExceptionResultEnum.WECHAT_LOGIN_USER_DISABLE_ERROR);
			// throw new FailReturnObject(160, "用户被禁用");
		} else if ("DELETED".equals(user.getState().getStateCode())) {
			throw new FailReturnObject(ExceptionResultEnum.WECHAT_LOGIN_USER_DELETE_ERROR);
			// throw new FailReturnObject(163, "用户被删除");
		} else if ("APPLIED".equals(user.getState().getStateCode())) {
			throw new FailReturnObject(ExceptionResultEnum.WECHAT_LOGIN_USER_AUDIT_ERROR);
			// throw new FailReturnObject(165, "用户正在被审核");
		} else if ("USER".equals(user.getRole().getRoleCode())) {
			throw new FailReturnObject(ExceptionResultEnum.WECHAT_LOGIN_USER_PERMISSION_ERROR);
			// throw new FailReturnObject(166, "你没有登录的权限");
		}

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if (user.getRole() == null) {
			throw new FailReturnObject(ExceptionResultEnum.WECHAT_LOGIN_USER_ROLE_ERROR);
			// throw new FailReturnObject(1340, "用户没有角色");
		}
		authorities.add(new SimpleGrantedAuthority(user.getRole().getRoleCode().toString()));
		List<Department> ownAndChildrenDepartments = CommonUtil.ownAndChildrenDepartments(user.getDepartment());
		if (!ownAndChildrenDepartments.isEmpty()) {
			user.setChildrenDepartments(ownAndChildrenDepartments);
		}
		Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);

		return authentication;

	}

	private Map getAccessToken(String code) {
		RestTemplate restTemplate = new RestTemplate();
		String user = restTemplate
				.getForObject(
						"https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + properties.getAppid() + "&secret="
								+ properties.getSecret() + "&code=" + code + "&grant_type=authorization_code",
						String.class);
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> m = mapper.readValue(user, Map.class);
			if (m.containsKey("errcode")) {
				throw new FailReturnObject(130, "获取微信token返回错误：" + m.get("errmsg").toString());
			}

			return m;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new FailReturnObject(ExceptionResultEnum.WECHAT_LOGIN_AUTHORISE_ERROR);
			// throw new FailReturnObject(120, "用户微信授权解析失败");
		}
	}

	private Map getUserInfo(String token, String openid) {
		RestTemplate restTemplate = new RestTemplate();

		String user = restTemplate.getForObject(
				"https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openid, String.class);

		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> m = mapper.readValue(user, Map.class);
			if (m.containsKey("errcode")) {
				throw new FailReturnObject(140, "获取微信userinfo返回错误：" + m.get("errmsg").toString());
			}
			String nickname = (String) m.get("nickname");
			String name = new String(nickname.getBytes("ISO-8859-1"), "utf-8");
			m.put("nickname", filter(name));
			return m;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new FailReturnObject(ExceptionResultEnum.WECHAT_LOGIN_USER_INFO_ERROR);
			// throw new FailReturnObject(150, "用户微信信息解析失败");
		}
	}

	/**
	 * 过滤nickName的非法字符
	 * @param name
	 * @return
	 */
	private String filter(String name) {
		Matcher matcher = EMOJI.matcher(name);
		while (matcher.find()) {
			name = matcher.replaceAll("");
		}
		return name;
	}

}
