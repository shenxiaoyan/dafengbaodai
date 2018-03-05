package com.liyang.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;

import org.apache.tomcat.util.buf.UEncoder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.liyang.domain.role.Role;
import com.liyang.domain.role.RoleRepository;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserRepository;
import com.liyang.domain.user.UserState;
import com.liyang.domain.user.UserStateRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.util.FailReturnObject;

import javassist.compiler.ast.NewExpr;

/**
 * 微信公众号
 * 
 * @author win7
 *
 */
@Service
public class WechatPubService {
	@Value("${spring.wlqz.wechat.appid}")
	private  String appid ;
	// 公众号的appsecret
	@Value("${spring.wlqz.wechat.secret}")
	private  String security ;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WechatGetService wechatGetService;
	@Autowired
	private UserStateRepository userStateRepository;
	@Autowired
	private RoleRepository roleRepository;
	/**
	 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI
	 * &response_type=code&scope=SCOPE&state=STATE#wechat_redirect
	 * 
	 * @return
	 */
	/**
	 * 授权登录
	 * 
	 * @param redirect
	 *            要重定向的url
	 * @return
	 */
	public String authenize(String redirect) {
		String redirectUrl = URLEncoder.encode(redirect);
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri="
				+ redirectUrl + "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
		return url;

	}

	/**
	 * 
	 * @param code
	 * @return
	 */
	public User callback(String code) {
		Map<String,String> tokenAuth = getAccessToken(code);  
		//Map token_auth=new HashMap<>();
		User user = doApply(tokenAuth);
		return user;
	}
	
	/**
	 * 根据微信用户的openid在数据库中查找。没有找到根据微信用户凭证，用户唯一标识从微信后台获取，然后保存。
	 * 
	 * @param token
	 *            封装了微信用户的信息
	 * @return 微信用户
	 * 
	 */
	@Transactional
	private User doApply(Map<String,String> tokenAuth) {
		User user = null;
		
		String tokenQuanju=wechatGetService.getCacheTokenTotal();    
		Map<String,String> info = getUserInfo(tokenQuanju, tokenAuth.get("openid").toString());
		//这里是获取uid
		user = userRepository.findByUnionid(info.get("unionid").toString());
		
		if (user != null) {
			System.out.println(user.getUnionid());
			return user;
		}
		user = new User();
		user.setOpenid(info.get("openid"));
		user.setNickname(info.get("nickname"));
	/*	if (info.get("sex")!=null) {
			user.setSex((int));
		}*/
		user.setUnionid(info.get("unionid"));
		user.setProvince(info.get("province"));
		user.setCity(info.get("city"));
		user.setCountry(info.get("country"));
		user.setHeadimgurl(info.get("headimgurl"));
		UserState userState=userStateRepository.findByStateCode("ENABLED");
		/*user.setLanguage(info.get("language").toString());
		user.setGroupid((Integer)info.get("groupid"));
		user.setSubscribe(info.get("subscribe").toString());
		user.setSubscribe_time((Integer) info.get("subscribe_time"));
		user.setRemark(info.get("remark").toString());*/
//		user.setSig(TIMSignature.generate(info.get("unionid").toString()).urlSig);
//		Role role= roleRepository.findByRoleCode(RoleCode.USER);
		Role role= roleRepository.findByRoleCode("USER");
		user.setState(userState);
		user.setRole(role);
		user = userRepository.save(user);
		System.out.println(user.getUnionid());
		return user;
	}

	/**
	 * 获取access_token
	 * 
	 * @param code
	 *            状态码
	 * @return
	 */
	private Map getAccessToken(String code) {
		String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid
				+ "&secret=" + security + "&code=" + code + "&grant_type=authorization_code";
			Map<String, Object> m = getMapFromJSON(url);
			if (m.containsKey("errcode")) {
				throw new FailReturnObject(130, "获取微信token返回错误：" +m.get("errcode")+ m.get("errmsg").toString());
			}
			return m;
	}

	/**
	 * 获取微信用户信息
	 * 
	 * @param token
	 *            用户凭证
	 * @param openid
	 *            用户唯一标识
	 * @return 微信用户信息集合
	 */
	private Map getUserInfo(String token, String openid) {
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + token + "&openid=" + openid+"&lang=zh_CN ";
		try {
			Map<String, Object> m = getMapFromJSON(url);
			if (m.containsKey("errcode")) {
				throw new FailReturnObject(140, "获取微信userinfo返回错误：" + m.get("errmsg").toString());
			}
			String nickname = (String) m.get("nickname");
			String name = new String(nickname.getBytes("ISO-8859-1"), "utf-8");
			m.put("nickname", name);
			return m;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new FailReturnObject(ExceptionResultEnum.WECHAT_LOGIN_USER_INFO_ERROR);
//			throw new FailReturnObject(150, "用户微信信息解析失败");
		}
	}



	/**
	 * get请求url，返回的json数据包装成Map
	 * 
	 * @param url
	 * @return 保存了json数据的map
	 */
	public Map<String, Object> getMapFromJSON(String url) {
		RestTemplate restTemplate = new RestTemplate();
		//这句话可以请求资源，类似于客户端请求 
		String result = restTemplate.getForObject(url, String.class);
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(result);
		Map<String, Object> m = null;
		try {
			m = mapper.readValue(result, Map.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m;
	}
	

	
	/**
	 * post 携带json请求url，返回的json为String
	 * 
	 * @param url
	 * @return json格式的String
	 */
	private String getStringFromJSON(String url, String menuJson) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json;charset=utf-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		net.sf.json.JSONObject jsonObj = net.sf.json.JSONObject.fromObject(menuJson);
		HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);
		String result = restTemplate.postForObject(url, formEntity, String.class);
		return result;
	}

}
