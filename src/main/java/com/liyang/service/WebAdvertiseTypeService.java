package com.liyang.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyang.domain.advertise.Advertise;
import com.liyang.domain.advertise.AdvertiseRepository;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.insuranceresult.InsuranceResult;
import com.liyang.domain.role.Role;
import com.liyang.domain.role.RoleRepository;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserRepository;
import com.liyang.domain.webadvertisetype.WebAdvertiseType;
import com.liyang.domain.webadvertisetype.WebAdvertiseTypeAct;
import com.liyang.domain.webadvertisetype.WebAdvertiseTypeActRepository;
import com.liyang.domain.webadvertisetype.WebAdvertiseTypeLog;
import com.liyang.domain.webadvertisetype.WebAdvertiseTypeLogRepository;
import com.liyang.domain.webadvertisetype.WebAdvertiseTypeRepository;
import com.liyang.domain.webadvertisetype.WebAdvertiseTypeState;
import com.liyang.domain.webadvertisetype.WebAdvertiseTypeStateRepository;
import com.liyang.handle.GlobalExceptionHandler;
import com.liyang.util.FailReturnObject;
import com.liyang.websocket.MyWebSocket;
import com.liyang.websocket.SocketMessage;

import net.sf.json.JSONArray;

@Service
public class WebAdvertiseTypeService extends
		AbstractAuditorService<WebAdvertiseType, WebAdvertiseTypeState, WebAdvertiseTypeAct, WebAdvertiseTypeLog> {

	@Autowired
	WebAdvertiseTypeLogRepository logRepository;
	@Autowired
	WebAdvertiseTypeRepository webAdvTypeRepository;
	@Autowired
	WebAdvertiseTypeActRepository actRepository;
	@Autowired
	WebAdvertiseTypeStateRepository stateRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	AdvertiseRepository advertiseRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(WebAdvertiseTypeService.class);
	
	@Override
	@PostConstruct
	public void sqlInit() {
		if (actRepository.count() <= 0) {
			WebAdvertiseTypeAct act1 = actRepository.save(new WebAdvertiseTypeAct("创建", "create", 10, ActGroup.UPDATE));
			WebAdvertiseTypeAct act2 = actRepository.save(new WebAdvertiseTypeAct("读取", "read", 20, ActGroup.READ));
			WebAdvertiseTypeAct act3 = actRepository.save(new WebAdvertiseTypeAct("更新", "update", 30, ActGroup.UPDATE));
			WebAdvertiseTypeAct act4 = actRepository.save(new WebAdvertiseTypeAct("删除", "delete", 40, ActGroup.UPDATE));
			WebAdvertiseTypeState stateEnabled = new WebAdvertiseTypeState("启用", 0, "ENABLED");
			stateEnabled.setActs(Arrays.asList(act1, act2, act3, act4).stream().collect(Collectors.toSet()));
			stateRepository.save(stateEnabled);
			WebAdvertiseTypeState stateDisabled = new WebAdvertiseTypeState("禁用", 100, "DISABLED");
			stateDisabled.setActs(Arrays.asList(act1, act2, act3, act4).stream().collect(Collectors.toSet()));
			stateRepository.save(stateDisabled);
		}
	}

	@Override
	public LogRepository<WebAdvertiseTypeLog> getLogRepository() {
		return logRepository;
	}

	@Override
	public WebAdvertiseTypeLog getLogInstance() {
		return new WebAdvertiseTypeLog();
	}

	@Override
	public AuditorEntityRepository<WebAdvertiseType> getAuditorEntityRepository() {
		return webAdvTypeRepository;
	}

	@Override
	public ActRepository<WebAdvertiseTypeAct> getActRepository() {
		return actRepository;
	}

	@Override
	@PostConstruct
	public void injectEntityService() {
		new WebAdvertiseType().setService(this);
	}

	@Override
	public void injectLogRepository() {
		new WebAdvertiseType().setLogRepository(logRepository);
	}

	/*
	 * 获取所有web端通知类
	 */
	public List<Map<String, Object>> getAll(String stateCode) {
		List<WebAdvertiseType> list = null;
		if (!StringUtils.isEmpty(stateCode)) {
			list = webAdvTypeRepository.findByStateStateCode(stateCode);
		} else {
			list = webAdvTypeRepository.findAll();
		}
		List<Map<String, Object>> returnList = new ArrayList<>();
		for (WebAdvertiseType webAdvertiseType : list) {
			Map<String, Object> typeMap = new HashMap<>();
			typeMap.put("id", webAdvertiseType.getId());
			typeMap.put("label", webAdvertiseType.getLabel());
			typeMap.put("stateLabel", webAdvertiseType.getState().getLabel());
			typeMap.put("stateCode", webAdvertiseType.getState().getStateCode());
			List<Map<String, Object>> rolesDataList = new ArrayList<>();
			for (Role role : webAdvertiseType.getRoles()) {
				Map<String, Object> roleDataMap = new HashMap<>();
				roleDataMap.put("id", role.getId());
				roleDataMap.put("label", role.getLabel());
				rolesDataList.add(roleDataMap);
				typeMap.put("rolesData", rolesDataList);
			}
			returnList.add(typeMap);
		}
		return returnList;
	}

	/*
	 * 更新消息label，及通知角色
	 */
	@Transactional
	public String update(Integer id, String label, Integer[] roleIds) {
		try {
			WebAdvertiseType webAdvertiseType = webAdvTypeRepository.findOne(id);
			if (!StringUtils.isEmpty(label)) {
				webAdvertiseType.setLabel(label);
			}
			if (roleIds != null) {
				List<Role> roles = roleRepository.findByIdIn(roleIds);
				Set<Role> roleSet = new HashSet<>(roles);
				webAdvertiseType.setRoles(roleSet);
			}
			webAdvTypeRepository.save(webAdvertiseType);
		} catch (Exception e) {
			e.printStackTrace();
			throw new FailReturnObject(234, "消息配置更新失败");
		}
		return "更新成功";
	}

	// /*
	// * 更新消息label，及通知角色
	// */
	// @Transactional
	// public String update1(WebAdvertiseType webAdvetiseType) {
	// try {
	// repository.save(webAdvetiseType);
	// } catch (Exception e) {
	// throw new FailReturnObject(111, "更新失败");
	// }
	// return "更新成功";
	// }

	/*
	 * 更新消息label，及通知角色
	 */
	@Transactional
	public String update2(Map<String, Object> webAdvetiseTypeMap) {
		try {
			WebAdvertiseType webAdvertiseType = webAdvTypeRepository
					.findOne(Integer.valueOf(webAdvetiseTypeMap.get("id").toString()));
			String label = String.valueOf(webAdvetiseTypeMap.get("label").toString());
			if (!StringUtils.isEmpty(label)) {
				webAdvertiseType.setLabel(label);
			}
			String stateCode = String.valueOf(webAdvetiseTypeMap.get("stateCode"));
			if (!StringUtils.isEmpty(stateCode)) {
				WebAdvertiseTypeState state = stateRepository.findByStateCode(stateCode);
				webAdvertiseType.setState(state);
			}
			JSONArray jsonArray = JSONArray.fromObject(webAdvetiseTypeMap.get("roleIds"));
			List<Integer> roleIdList = new ArrayList<>();
			for (int i = 0; i < jsonArray.size(); i++) {
				String str = String.valueOf(jsonArray.getJSONObject(i).get("id"));
				roleIdList.add(Integer.valueOf(str));
			}
			if (!roleIdList.isEmpty()) {
				List<Role> roles = roleRepository.findByIdIn(roleIdList.toArray(new Integer[roleIdList.size()]));
				Set<Role> roleSet = new HashSet<>(roles);
				webAdvertiseType.setRoles(roleSet);
			}
			webAdvTypeRepository.save(webAdvertiseType);
		} catch (Exception e) {
			e.printStackTrace();
			throw new FailReturnObject(234, "消息配置更新失败");
		}
		return "更新成功";
	}

	/*
	 * 新增通知类
	 */
	@Transactional
	public void save(String typeCode, String label, Integer[] roleIds) {
		try {
			WebAdvertiseType webAdvertiseType = new WebAdvertiseType();
			webAdvertiseType.setTypeCode(typeCode);
			webAdvertiseType.setLabel(label);
			if (roleIds != null) {
				List<Role> roles = roleRepository.findByIdIn(roleIds);
				Set<Role> roleSet = new HashSet<>(roles);
				webAdvertiseType.setRoles(roleSet);
			}
			WebAdvertiseTypeState enabledState = stateRepository.findByStateCode("ENABLED");
			webAdvertiseType.setState(enabledState);
			webAdvTypeRepository.save(webAdvertiseType);
		} catch (Exception e) {
			e.printStackTrace();
			throw new FailReturnObject(111, "新增失败");
		}
	}

	/*
	 * 切换启动/禁用状态
	 */
	@Transactional
	public String reverseState(Integer id) {
		WebAdvertiseType webAdvertiseType = webAdvTypeRepository.findOne(id);
		if ("ENABLED".equals(webAdvertiseType.getState().getStateCode())) {
			WebAdvertiseTypeState disabledState = stateRepository.findByStateCode("DISABLED");
			webAdvertiseType.setState(disabledState);
			return "禁用成功";
		} else {
			WebAdvertiseTypeState enabledState = stateRepository.findByStateCode("ENABLED");
			webAdvertiseType.setState(enabledState);
			return "启用成功";
		}
	}

	/*
	 * 更具Id获取通知类型
	 */
	public Map<String, Object> findById(Integer id) {
		WebAdvertiseType webAdvertiseType = webAdvTypeRepository.findOne(id);
		if (null == webAdvertiseType) {
			return null;
		}
		Map<String, Object> typeMap = new HashMap<>();
		typeMap.put("id", webAdvertiseType.getId());
		typeMap.put("label", webAdvertiseType.getLabel());
		typeMap.put("stateLabel", webAdvertiseType.getState().getLabel());
		typeMap.put("stateCode", webAdvertiseType.getState().getStateCode());
		List<Map<String, Object>> rolesDataList = new ArrayList<>();
		for (Role role : webAdvertiseType.getRoles()) {
			Map<String, Object> roleDataMap = new HashMap<>();
			roleDataMap.put("id", role.getId());
			roleDataMap.put("label", role.getLabel());
			rolesDataList.add(roleDataMap);
			typeMap.put("rolesData", rolesDataList);
		}
		return typeMap;
	}
	
	
	/**
	 * 承保成功后台消息通知
	 * 
	 * @param insuranceResult
	 */
	public void sendWebAdvertise(InsuranceResult insuranceResult, double sumPrice) {
		try {
			WebAdvertiseType webAdvertiseType = webAdvTypeRepository.findByTypeCode("insureResultNotice");
			if (null != webAdvertiseType) {
				Set<Role> roles = webAdvertiseType.getRoles();
				List<User> users = userRepository.findByRoleIn(roles);
				for (User user : users) {
					String licenseNumber = insuranceResult.getData().getLicenseNumber();
					String insuredName = insuranceResult.getSubmitProposal().getParams().getInsuredName();
					String title = "有新承保保单";
					String content = "承保车牌：" + licenseNumber + ",承保车主：" + insuredName + ",承保金额："
							+ String.valueOf(sumPrice) + ",订单号：" + insuranceResult.getData().getOrderId();
					String url = "/#/workflowEntity/submitProposal/" + insuranceResult.getSubmitProposal().getId() + "/"
							+ insuranceResult.getData().getOrderId() + "////profile/info";
					Advertise advertise = new Advertise();
					advertise.setTitle(title);
					advertise.setContent(content);
					advertise.setIsRead(0);
					advertise.setZnxType(4);
					advertise.setType(3);
					advertise.setOfferId(insuranceResult.getData().getOrderId());
					advertise.setUser(user);
					advertise.setCreatedAt(new Date());
					advertise.setLinkUrl(url);
					advertise = advertiseRepository.save(advertise);
					System.out.println("【消息创建】");
					SocketMessage socketMessage = new SocketMessage(title, content, url, 1);
					MyWebSocket.sendMessage(socketMessage, user);
				}
			}
		} catch (Exception e) {
			logger.error("【！！！！！！web端消息通知--承保通知--发送失败】");
			logger.error(GlobalExceptionHandler.getTraceInfo(e));
		}
	} 

}
