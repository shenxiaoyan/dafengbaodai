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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jpa.query.expression.QueryExpSpecificationBuilder;
import com.jpa.query.expression.generic.GenericQueryExpSpecification;
import com.liyang.client.CreateEnquiryJsonParseFactory;
import com.liyang.client.IClient;
import com.liyang.client.ICreateEnquiryJsonParseAdapter;
import com.liyang.client.IServiceObserve;
import com.liyang.client.strategy.SecurityInfoXiaoma;
import com.liyang.client.xiaoma.ClientXiaoma;
import com.liyang.client.xiaoma.MessageCreateEnquiry;
import com.liyang.client.xiaoma.ResultCreateEnquiry;
import com.liyang.client.xiaoma.ServiceCreateEnquiry;
import com.liyang.client.xiaoma.ServiceObserveDbPersistCreateEnquiry;
import com.liyang.domain.advertise.Advertise;
import com.liyang.domain.advertise.AdvertiseRepository;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.base.AbstractWorkflowAct.ActType;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.createenquiry.CreateEnquiry;
import com.liyang.domain.createenquiry.CreateEnquiryAct;
import com.liyang.domain.createenquiry.CreateEnquiryActRepository;
import com.liyang.domain.createenquiry.CreateEnquiryFile;
import com.liyang.domain.createenquiry.CreateEnquiryForSearch;
import com.liyang.domain.createenquiry.CreateEnquiryLog;
import com.liyang.domain.createenquiry.CreateEnquiryLogRepository;
import com.liyang.domain.createenquiry.CreateEnquiryRepository;
import com.liyang.domain.createenquiry.CreateEnquiryState;
import com.liyang.domain.createenquiry.CreateEnquiryStateRepository;
import com.liyang.domain.createenquiry.CreateEnquiryWorkflow;
import com.liyang.domain.createenquiry.CreateEnquiryWorkflowRepository;
import com.liyang.domain.customer.CustomerRepository;
import com.liyang.domain.insurercompany.InsureCompany;
import com.liyang.domain.insurercompany.InsureCompanyRepository;
import com.liyang.domain.offerresult.OfferResult;
import com.liyang.domain.offerresult.OfferResultRepository;
import com.liyang.domain.platform.Platform;
import com.liyang.domain.platform.PlatformRepository;
import com.liyang.domain.role.Role;
import com.liyang.domain.role.RoleRepository;
import com.liyang.domain.submitproposal.SubmitProposal;
import com.liyang.domain.submitproposal.SubmitProposalRepository;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserRepository;
import com.liyang.domain.webadvertisetype.WebAdvertiseType;
import com.liyang.domain.webadvertisetype.WebAdvertiseTypeRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.util.FailReturnObject;
import com.liyang.websocket.MyWebSocket;
import com.liyang.websocket.SocketMessage;

import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 */
@Service
public class CreateEnquiryService extends
		AbstractWorkflowService<CreateEnquiry, CreateEnquiryWorkflow, CreateEnquiryAct, CreateEnquiryState, CreateEnquiryLog, CreateEnquiryFile> {
	/**
	 * 创建询价服务
	 */
	@Autowired
	PlatformRepository platformRepository;
	@Autowired
	CreateEnquiryActRepository createEnquiryActRepository;
	@Autowired
	CreateEnquiryStateRepository createEnquiryStateRepository;
	@Autowired
	CreateEnquiryLogRepository createEnquiryLogRepository;
	@Autowired
	CreateEnquiryRepository createEnquiryRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CreateEnquiryWorkflowRepository createEnquiryWorkflowRepository;
	@Autowired
	AuthorityJudgeService authorityJudgeService;
	@Autowired
	InsureCompanyRepository insureCompanyRepository;
	@Autowired
	SubmitProposalRepository submitProposalRepository;
	@Autowired
	OfferResultRepository offerResultRepository;
	@Autowired
	WebAdvertiseTypeRepository webAdvTypRepository;
	@Autowired
	AdvertiseRepository advertiseRepository;
	@Autowired
	UserRepository userRepository;

	private final static Logger logger = LoggerFactory.getLogger(CreateEnquiryService.class);

	/**
	 * 对平台获取过来的数据处理，处理好后的string为小马接口所需的数据
	 */
	@Transactional
	public ResponseBody saveCreEnqAndForward(Platform platform, Map<String, Object> creEnqMap,
			HttpServletRequest request, String xmcxApiKey) throws Exception {
		// Assert.notNull(xmcx_apiKey);
		String token = request.getHeader("token");
		MessageCreateEnquiry message = new MessageCreateEnquiry(logger, token, platform, creEnqMap);
		IClient client = new ClientXiaoma();
		IServiceObserve serviceObserve = new ServiceObserveDbPersistCreateEnquiry(createEnquiryRepository,
				insureCompanyRepository, offerResultRepository);
		SecurityInfoXiaoma securityInfo = new SecurityInfoXiaoma(xmcxApiKey);
		ServiceCreateEnquiry service = new ServiceCreateEnquiry(securityInfo, client, serviceObserve,
				customerRepository, createEnquiryActRepository, createEnquiryStateRepository);
		ResultCreateEnquiry result = (ResultCreateEnquiry) service.callService(message);

		try {
			WebAdvertiseType webAdvertiseType = webAdvTypRepository.findByTypeCode("createEnquiryNotice");
			if (null != webAdvertiseType) {
				Set<Role> roles = webAdvertiseType.getRoles();
				List<User> users = userRepository.findByRoleIn(roles);
				for (User user : users) {
					String title = "新的询价创建";
					JSONObject object = JSONObject.fromObject(creEnqMap.get("createEnquiryParams"));
					String content = "询价车牌：" + object.getString("licenseNumber") + "<br>询价车主："
							+ object.getString("ownerName") + "<br/>询价用户：" + creEnqMap.get("mobilePhone");
					String advertiseContent = "询价车牌：" + object.getString("licenseNumber") + "，询价车主："
							+ object.getString("ownerName") + "，询价用户：" + creEnqMap.get("mobilePhone");
					String url = "/#/workflowEntity/createEnquiry/" + result.getCreateEnquiry().getId()
							+ "/////profile/info";
					Advertise advertise = new Advertise();
					advertise.setTitle(title);
					advertise.setContent(advertiseContent);
					advertise.setIsRead(0);
					advertise.setZnxType(5);
					advertise.setType(3);
					advertise.setUser(user);
					advertise.setCreatedAt(new Date());
					advertise.setLinkUrl(url);
					advertise = advertiseRepository.save(advertise);
					SocketMessage socketMessage = new SocketMessage(title, content, url, 1);
					MyWebSocket.sendMessage(socketMessage, user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("web端消息通知发送失败");
		}

		// 返回数据指定封装
		Map<String, Object> map = new HashMap<>();
		map.put("createEnquiryId", result.getCreateEnquiry().getId());
		return new ResponseBody(map);

		/**
		 * Original code 5 reconstru 20171123
		 */
		// //String UUIDString = UUID.randomUUID().toString();
		// String UUIDString = createUnique();
		//
		// ObjectMapper objectMapper = new ObjectMapper();
		// Map<String, String> handerCreEnqMap = new HashMap<String, String>();
		//
		// // 在原数据中增加applicationId与报价唯一标识offerUnique
		// @SuppressWarnings("unchecked")
		// Map<String, Object> createEnquiryParamsMap = (HashMap<String,
		// Object>) creEnqMap.get("createEnquiryParams");
		// if(null== createEnquiryParamsMap ||
		// createEnquiryParamsMap.isEmpty()){
		// throw new FailReturnObject(ExceptionResultEnum.CREQUERY_DATA_ERROR);
		// }
		// //前台requestHeader格式:{"requestHeader":"{}"},
		// //DAFENG API has data;
		// Map<String, String> reqHeaderMap= new HashMap<String, String>();
		//
		//// (Map<String, Object>) createEnquiryParamsMap.get("requestHeader");
		//
		// String insurComName= (String)
		// createEnquiryParamsMap.get("insuranceCompanyName");
		// if(null== insurComName || insurComName.trim().equalsIgnoreCase("")){
		// throw new FailReturnObject(ExceptionResultEnum.CREQUERY_DATA_ERROR);
		// }
		// String oriReqHeaderStr= (String)
		// createEnquiryParamsMap.get("requestHeader");
		//
		// String[] insurComArray = insurComName.split(",");
		// // requestHeader中带入唯一标识与报价结果关联
		// reqHeaderMap.put("offerUnique", UUIDString);
		// reqHeaderMap.put("applicationId",platform.getApplicationId());
		// if(oriReqHeaderStr!=null){
		// reqHeaderMap.put("oriReqHeaderStr",oriReqHeaderStr);
		// }
		//
		// createEnquiryParamsMap.put("requestHeader",
		// JSONObject.fromObject(reqHeaderMap).toString());
		// creEnqMap.put("createEnquiryParams", createEnquiryParamsMap);
		//
		//
		// // 询价与成单量规则
		// enquiryRule(platform);
		//
		// // 原数据转换成小马接口需要的Map格式
		// handerCreEnqMap.put("createEnquiryParams",
		// JSONObject.fromObject(creEnqMap.get("createEnquiryParams")).toString());
		// handerCreEnqMap.put("mobilePhone", (String)
		// creEnqMap.get("mobilePhone"));
		// // 将消息发送给小马
		//
		// logger.info("询价发送给小马数据如下handerCreEnqMap....:"+handerCreEnqMap+"\n");
		// for(Entry<String, String> maps:handerCreEnqMap.entrySet()) {
		// logger.info(maps.getKey()+":"+maps.getValue());
		// }
		// String responseResult = HttpUtil.postForm(postToXiaoMaUrl,
		// handerCreEnqMap);
		// logger.info("询价直接返回responseResult.........."+ responseResult);
		// if (responseResult == null) {
		// logger.error("询价返回结果错误："+responseResult);
		// throw new
		// FailReturnObject(ExceptionResultEnum.CREQUERY_RETURN_DATA_ERROR);
		// }
		// @SuppressWarnings("unchecked")
		// Map<String, Object> originalResponseDataMap =
		// objectMapper.readValue(responseResult, Map.class);
		// Object errorMsg;
		//
		// if(null==originalResponseDataMap ||
		// null==(errorMsg=originalResponseDataMap.get("errorMsg"))
		// || !(errorMsg.toString().contains("success"))){
		// logger.error("询价返回结果错误："+responseResult);
		// throw new
		// FailReturnObject(ExceptionResultEnum.CREQUERY_RETURN_DATA_ERROR);
		// }
		//
		// // 原数据转化为对象
		// CreateEnquiry createEnquiry =
		// TransUtils.mapTransStringObject(handerCreEnqMap,
		// CreateEnquiry.class);
		// // 设置报价唯一标示，用于关联报价
		// createEnquiry.setOfferUnique(UUIDString);
		// // 设置用户，关联用户
		// createEnquiry.setCustomer(customerRepository.findByToken(request.getHeader("token")));
		// // 设置初始状态：询价中
		// createEnquiry.setState(createEnquiryStateRepository.findByStateCode("INENQUIRY"));
		// // 设置工作流：询价管理流程
		// createEnquiry.setWorkflow(createEnquiryActRepository.findByActCode("create").getStartWorkflow());
		// // 设置平台来源
		// createEnquiry.setPlatform(platform);
		// // 设置返回结果
		// createEnquiry.setResponseResult(responseResult);
		// // 设置保险公司 no need
		//// createEnquiry.setInsureCompanyIdStr(insurComName);
		//
		// //设置车主、车牌 for 查询
		// createEnquiry.setOwnerName(JSONObject.fromObject(creEnqMap.get("createEnquiryParams")).getString("ownerName"));
		// createEnquiry.setLicenseNumber(JSONObject.fromObject(creEnqMap.get("createEnquiryParams")).getString("licenseNumber"));
		//
		// // 询价数据保存
		// createEnquiry = createEnquiryRepository.save(createEnquiry);
		//
		// //create record for OfferResult.OfferUnique, InsuranceCom
		// for(int i=0; i<insurComArray.length; i++){
		// //OfferUnique, applicationId, OriReqHeaderStr
		// OfferResultDataRequestHeader offResDatRequestHeader=null;
		// try {
		// offResDatRequestHeader =
		// TransUtils.mapTransStringObject(reqHeaderMap,
		// OfferResultDataRequestHeader.class);
		// }catch (Exception e) {
		// logger.error(e.toString());
		// throw new
		// FailReturnObject(ExceptionResultEnum.OFFERRES_REQHEADER_TRANS_ERROR);
		// }
		// //InsuranceComId
		// OfferResultDataResult offResDatResult = new OfferResultDataResult();
		// offResDatResult.setInsuranceCompanyId(Integer.parseInt(insurComArray[i]));
		// InsureCompany company =
		// insureCompanyRepository.findByInsurerCompanyId(Integer.parseInt(insurComArray[i]));
		// offResDatResult.setInsuranceCompanyName(company.getName());
		//
		// OfferResultData offerResultData = new OfferResultData();
		// offerResultData.setRequestHeader(offResDatRequestHeader);
		// offerResultData.setResult(offResDatResult);
		// OfferResult offerResult = new OfferResult();
		// offerResult.setData(offerResultData);
		// offerResult.setCreateEnquiry(createEnquiry);
		// offerResult.setPlatform(platform);
		// offerResultRepository.save(offerResult);
		/*
		 * / Original code 4 reconstru
		 * 
		 */
		// // 联调时需要对其aes解码
		// // 所需初始参数
		// String UUIDString = UUID.randomUUID().toString();
		// ResponseBody responseBody = new ResponseBody();
		// ObjectMapper objectMapper = new ObjectMapper();
		// Map<String, String> handerCreEnqMap = new HashMap<String, String>();
		//
		// // 在原数据中增加applicationId与报价唯一标识offerUnique
		// Map<String, Object> createEnquiryParamsMap = (HashMap<String,
		// Object>) creEnqMap.get("createEnquiryParams");
		// //前台requestHeader格式:{"requestHeader":"{}"}
		// Map<String, Object> reqHeaderMap = objectMapper.readValue((String)
		// createEnquiryParamsMap.get("requestHeader"), Map.class);
		// // requestHeader中带入唯一标识与报价结果关联
		// reqHeaderMap.put("offerUnique", UUIDString);
		// // 自家平台applicationId位于Heards中
		//
		// // add for dafeng api customer
		// if(creEnqMap.get("applicationId")!=null){
		// reqHeaderMap.put("applicationId", creEnqMap.get("applicationId"));
		// }
		//
		// if (request.getHeader("applicationId") != null) {
		// reqHeaderMap.put("applicationId",
		// request.getHeader("applicationId"));
		// }
		//
		//
		// createEnquiryParamsMap.put("requestHeader",
		// JSONObject.fromObject(reqHeaderMap).toString());
		// creEnqMap.put("createEnquiryParams", createEnquiryParamsMap);
		//
		//
		// // remove for dafeng api customer
		// creEnqMap.remove("applicationId");
		// // 获取平台标识，判断是否具有权限访问
		// String applicationId = (String) reqHeaderMap.get("applicationId");
		// if (applicationId == null) {
		// throw new FailReturnObject(100, "请带上平台标识访问");
		// }
		// Platform platform =
		// platformRepository.findByApplicationId(applicationId); // 平台信息
		// if (platform == null) {
		// throw new FailReturnObject(200, "没有权限访问，请联系管理人员");
		// } else if ("0" == platform.getEnable()) {
		// throw new FailReturnObject(300, "权限已被关闭，请联系管理人员");
		// }
		//
		// System.out.println("转换消息发送给小马数据如下....:");
		// for(Map.Entry<String, Object> maps:creEnqMap.entrySet()) {
		// System.out.println(maps.getKey()+":"+maps.getValue());
		// }
		//
		// // 询价与成单量规则
		// enquiryRule(platform);
		//
		//// creEnqMap.clear();
		//// creEnqMap.put("createEnquiryParams", "{insuranceCompanyName=1,2,
		// cityCode=330100, cityName=杭州市, ownerName=汪伟坤,
		// insurancesList=[{insurances=[{isToubao=1, compensation=true, price=1,
		// insuranceId=1}, {isToubao=1, compensation=true, price=500000,
		// insuranceId=2}, {isToubao=1, compensation=true, price=10000,
		// insuranceId=4}, {isToubao=1, compensation=true, price=10000,
		// insuranceId=5}], forcePremium={isToubao=1}, schemeName=续保方案}],
		// transferDate=0, carTypeCode=02, licenseNumber=浙AZS871,
		// forceInsuranceStartTime=1508428800,
		// requestHeader={\"offerUnique\":\"73f9eef8-26fe-4c91-9f11-20cba4048f0b\",\"applicationId\":\"fengxianwuyou_ios\"},
		// insuranceStartTime=1508515200}");
		//// creEnqMap.put("mobilePhone","13735540844");
		//
		//
		// // 原数据转换成小马接口需要的Map格式
		// handerCreEnqMap.put("createEnquiryParams",
		// JSONObject.fromObject(creEnqMap.get("createEnquiryParams")).toString());
		// handerCreEnqMap.put("mobilePhone", (String)
		// creEnqMap.get("mobilePhone"));
		// // 将消息发送给小马
		// String responseResult = HttpUtil.postForm(postToXiaoMaUrl,
		// handerCreEnqMap);
		// System.out.println("responseResult.........."+ responseResult);
		// if (responseResult == null) {
		// throw new FailReturnObject(100, "创建询价请求失败");
		// }
		//
		//
		// // 原数据转化为对象
		// CreateEnquiry createEnquiry =
		// TransUtils.mapTransStringObject(handerCreEnqMap,
		// CreateEnquiry.class);
		// // 设置报价唯一标示，用于关联报价
		// createEnquiry.setOfferUnique(UUIDString);
		// // 设置用户，关联用户
		// createEnquiry.setCustomer(customerRepository.findByToken(request.getHeader("token")));
		// // 设置初始状态：询价中
		// createEnquiry.setState(createEnquiryStateRepository.findByStateCode("INENQUIRY"));
		// // 设置工作流：询价管理流程
		// createEnquiry.setWorkflow(createEnquiryActRepository.findByActCode("create").getStartWorkflow());
		// // 设置平台来源
		// createEnquiry.setPlatform(platform);
		// // 设置返回结果
		// createEnquiry.setResponseResult(responseResult);
		// // 设置保险公司
		//
		//
		//
		// // 询价数据保存
		// createEnquiryRepository.save(createEnquiry);
		//
		//
		// // 返回数据指定封装
		// Map<String, Object> handerMap =
		// objectMapper.readValue(responseResult, Map.class);
		// if ((Boolean) handerMap.get("successful")) {
		// Map<String, Object> map = new HashMap<>();
		// map.put("createEnquiryId", createEnquiry.getId());
		// responseBody.setData(map);
		// responseBody.setErrorCode(0);
		// responseBody.setErrorInfo("OK");
		// return responseBody;
		// } else {
		// responseBody.setErrorCode(100);
		// responseBody.setErrorInfo(JSONObject.fromObject(handerMap.get("errorMsg")).getString("message"));
		// responseBody.setData(null);
		// return responseBody;
		// }
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.service.ServiceImpl#sqlInit()
	 */
	@Override
	@PostConstruct
	public void sqlInit() {
		List<CreateEnquiryAct> findAll = createEnquiryActRepository.findAll();
		if (findAll == null || findAll.isEmpty()) {
			CreateEnquiryWorkflow createEnquiryApplyWorkflow = new CreateEnquiryWorkflow();
			createEnquiryApplyWorkflow.setLabel("询价管理流程");
			// 初始化化时工作流的初始化
			createEnquiryApplyWorkflow = createEnquiryWorkflowRepository.save(createEnquiryApplyWorkflow);

			// 动作的定义
			CreateEnquiryAct createActSave = createEnquiryActRepository
					.save(new CreateEnquiryAct("创建", "create", 10, ActGroup.UPDATE));
			CreateEnquiryAct readActSave = createEnquiryActRepository
					.save(new CreateEnquiryAct("读取", "read", 20, ActGroup.READ));
			CreateEnquiryAct updateActSave = createEnquiryActRepository
					.save(new CreateEnquiryAct("更新", "update", 30, ActGroup.UPDATE));
			CreateEnquiryAct deleteActSave = createEnquiryActRepository
					.save(new CreateEnquiryAct("删除", "delete", 40, ActGroup.UPDATE));

			CreateEnquiryState inEnquiryState = new CreateEnquiryState("询价中", 0, "INENQUIRY");
			// 询价状态下具有什么什么动作，事先规定
			inEnquiryState.setActs(
					Arrays.asList(readActSave, updateActSave, deleteActSave).stream().collect(Collectors.toSet()));
			inEnquiryState = createEnquiryStateRepository.save(inEnquiryState);

			CreateEnquiryState allState = new CreateEnquiryState("全部", 100, "ALL");
			allState.setActs(Arrays.asList(createActSave, readActSave, updateActSave, deleteActSave).stream()
					.collect(Collectors.toSet()));
			allState = createEnquiryStateRepository.save(allState);

			CreateEnquiryState cancelState = new CreateEnquiryState("询价取消", 200, "CANCEL");
			createEnquiryStateRepository.save(cancelState);

			CreateEnquiryState enquiryResultState = new CreateEnquiryState("已有询价结果", 300, "ENQUIRY_RESULT");
			// enquiryResultState已有询价结果的状态下可以做的动作
			enquiryResultState.setActs(Arrays.asList(createActSave, readActSave, updateActSave, deleteActSave).stream()
					.collect(Collectors.toSet()));
			createEnquiryStateRepository.save(enquiryResultState);

			CreateEnquiryState submitProposalState = new CreateEnquiryState("已核保", 300, "ENQUIRY_RESULT");
			// enquiryResultState已核保的状态下可以做的动作
			submitProposalState.setActs(Arrays.asList(createActSave, readActSave, updateActSave, deleteActSave).stream()
					.collect(Collectors.toSet()));
			createEnquiryStateRepository.save(submitProposalState);

			// 该工作流下具有所有的状态
			createEnquiryApplyWorkflow.setStates(new HashSet<CreateEnquiryState>(
					Arrays.asList(inEnquiryState, allState, cancelState, enquiryResultState)));
			createEnquiryApplyWorkflow = createEnquiryWorkflowRepository.save(createEnquiryApplyWorkflow);

			// createActSave创建动作下，具有的状态、工作流、动作类、角色
			createActSave.setActType(ActType.START);
			createActSave.setStartWorkflow(createEnquiryApplyWorkflow);
			createActSave.setTargetState(inEnquiryState);

			// Role role =
			// roleRepository.findByRoleCode(Role.RoleCode.valueOf("USER"));
			Role role = roleRepository.findByRoleCode("USER");
			if (role != null) {
				createActSave.setRoles(new HashSet<Role>(Arrays.asList(role)));
			}
			createEnquiryActRepository.save(createActSave);

		}
		// 将核保中变更为已提交核保
		CreateEnquiryState submitAlreadyState = createEnquiryStateRepository.findByStateCode("SUBMIT_ALREADY");
		if (submitAlreadyState != null && ("核保中").equals(submitAlreadyState.getLabel())) {
			submitAlreadyState.setLabel("已提交核保");
			submitAlreadyState = createEnquiryStateRepository.save(submitAlreadyState);
		}

		// //更换询价中和全部的顺序
		// CreateEnquiryState state1 =
		// createEnquiryStateRepository.findByStateCode("INENQUIRY");
		// CreateEnquiryState state2 =
		// createEnquiryStateRepository.findByStateCode("ALL");
		// if(state1!=null && ("询价中").equals(state1.getLabel())){
		// state1.setStateCode("ALL");
		// state1.setLabel("全部");
		// state1=createEnquiryStateRepository.save(state1);
		// }
		// if(state2!=null && ("全部").equals(state2.getLabel())){
		// state2.setStateCode("INENQUIRY");
		// state2.setLabel("询价中");
		// state2=createEnquiryStateRepository.save(state2);
		// }

		// 删除询价取消状态
		// CreateEnquiryState cancelState =
		// createEnquiryStateRepository.findByStateCode("CANCEL");
		// if (cancelState != null && ("询价取消").equals(cancelState.getLabel())
		// && ("CANCEL").equals(cancelState.getStateCode())) {
		// Set<CreateEnquiryAct> acts = cancelState.getActs();
		// for (CreateEnquiryAct createEnquiryAct : acts) {
		// Set<CreateEnquiryState> states = createEnquiryAct.getStates();
		// for (CreateEnquiryState createEnquiryState : createEnquiryAct.getStates()) {
		// if ("CANCEL".equals(createEnquiryState.getStateCode())) {
		// states.remove(createEnquiryState);
		// }
		// }
		// createEnquiryAct.setStates(states);
		// }
		// cancelState.setActs(acts);
		// createEnquiryActRepository.save(acts);
		//
		// Set<CreateEnquiryWorkflow> workflows = cancelState.getWorkflows();
		// workflows.stream().forEach(w -> w.getStates().removeIf(e ->
		// "CANCEL".equals(e.getStateCode())));
		// createEnquiryWorkflowRepository.save(workflows);
		// createEnquiryStateRepository.delete(cancelState);
		// logger.info("删除了询价取消这个状态");
		// }

		// //删除“全部”状态
		// CreateEnquiryState allState =
		// createEnquiryStateRepository.findByStateCode("ALL");
		// if (allState != null && ("全部").equals(allState.getLabel()) &&
		// ("ALL").equals(allState.getStateCode())) {
		// Set<CreateEnquiryAct> acts = allState.getActs();
		// for (CreateEnquiryAct createEnquiryAct : acts) {
		// Set<CreateEnquiryState> states = createEnquiryAct.getStates();
		// for (CreateEnquiryState createEnquiryState : createEnquiryAct.getStates()) {
		// if ("ALL".equals(createEnquiryState.getStateCode())) {
		// states.remove(createEnquiryState);
		// }
		// }
		// createEnquiryAct.setStates(states);
		// }
		// allState.setActs(acts);
		// createEnquiryActRepository.save(acts);
		//
		// Set<CreateEnquiryWorkflow> workflows = allState.getWorkflows();
		// workflows.stream().forEach(w -> w.getStates().removeIf(e ->
		// "ALL".equals(e.getStateCode())));
		// createEnquiryWorkflowRepository.save(workflows);
		// createEnquiryStateRepository.delete(allState);
		// logger.info("删除了全部这个状态");
		// }
	}

	@Override
	public LogRepository<CreateEnquiryLog> getLogRepository() {
		// TODO Auto-generated method stub
		return createEnquiryLogRepository;
	}

	@Override
	public AuditorEntityRepository<CreateEnquiry> getAuditorEntityRepository() {

		return createEnquiryRepository;
	}

	@Override
	@PostConstruct
	public void injectLogRepository() {
		new CreateEnquiry().setLogRepository(createEnquiryLogRepository);

	}

	@Override
	public CreateEnquiryLog getLogInstance() {
		// TODO Auto-generated method stub
		return new CreateEnquiryLog();
	}

	@Override
	public ActRepository<CreateEnquiryAct> getActRepository() {
		// TODO Auto-generated method stub
		return createEnquiryActRepository;
	}

	@Override
	@PostConstruct
	public void injectEntityService() {
		new CreateEnquiry().setService(this);

	}

	@Override
	public CreateEnquiryFile getFileLogInstance() {
		// TODO Auto-generated method stub
		return new CreateEnquiryFile();
	}

	public void doRead(CreateEnquiry createEnquiry) {
		System.out.println("read");
	}

	public void doCreate(CreateEnquiry createEnquiry) {
		System.out.println("create");
	}

	public void doUpdate(CreateEnquiry createEnquiry) {
		System.out.println("update");
	}

	public void doDelete(CreateEnquiry createEnquiry) {
		System.out.println("delete");
	}

	// public void validEnquireTimeStatus(String token) {
	// Date beginTime = new Date(System.currentTimeMillis()-30*60*1000);
	// List<CreateEnquiry> list =
	// createEnquiryRepository.findCreateEnquiryListByTokenAndStateAndCreateTime(token,"INENQUIRY",beginTime);
	// CreateEnquiryState state =
	// createEnquiryStateRepository.findByStateCode("ENQUIRY_RESULT");
	// for (CreateEnquiry createEnquiry : list) {
	// createEnquiry.setState(state);
	// createEnquiryRepository.save(createEnquiry);
	// }
	// }

	public void validEnquireTimeStatus(String token) {
		Date beginTime = new Date(System.currentTimeMillis() - 30 * 60 * 1000);
		List<OfferResult> offerResultList = offerResultRepository
				.findByCreateEnquiryCustomerTokenAndSuccessfulIsNullAndCreateEnquiryCreatedAtBefore(token, beginTime);
		for (OfferResult offerResult : offerResultList) {
			offerResult.getData().getResult().setErrorMsg("{\"message\":\"询价超时\"}");
			offerResult.setSuccessful(false);
			if ("INENQUIRY".equals(offerResult.getCreateEnquiry().getState().getStateCode())) {
				CreateEnquiryState state = createEnquiryStateRepository.findByStateCode("ENQUIRY_RESULT");
				offerResult.getCreateEnquiry().setState(state);
				logger.info("因询价超时，修改询价记录（Id:" + offerResult.getCreateEnquiry().getId() + "）状态为已有结果");
			}
			offerResultRepository.save(offerResult);
			logger.info("修改Id：" + offerResult.getId() + "的offerResult下的result的errorMsg为询价超时");
		}
	}

	public Page<CreateEnquiry> multiConditionSearch(CreateEnquiryForSearch createEnquiryForSearch, Pageable pageable) {

		Page<CreateEnquiry> createEnquiryPage = createEnquiryRepository.findAll(new Specification<CreateEnquiry>() {

			@Override
			public Predicate toPredicate(Root<CreateEnquiry> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				// 后端用户展示，不需要过滤是否展示属性
				// Predicate isShow = cb.equal(root.get("isShow"),0);
				// predicateList.add(isShow);

				
				Predicate offerUniqueEqual = null;// 询价单号
				if (createEnquiryForSearch != null && !StringUtils.isEmpty(createEnquiryForSearch.getOfferUnique())) {
					offerUniqueEqual = cb.equal(root.<String>get("offerUnique"), createEnquiryForSearch.getOfferUnique());
				}
				if (null != offerUniqueEqual) {
					predicateList.add(offerUniqueEqual);
				}

				Predicate ownerNameLike = null;// 车主
				if (createEnquiryForSearch != null && !StringUtils.isEmpty(createEnquiryForSearch.getOwnerName())) {
					ownerNameLike = cb.like(root.<String>get("ownerName"), "%" + createEnquiryForSearch.getOwnerName() + "%");
				}
				if (null != ownerNameLike) {
					predicateList.add(ownerNameLike);
				}

				Predicate licenseNumberLike = null;// 车牌
				if (createEnquiryForSearch != null && !StringUtils.isEmpty(createEnquiryForSearch.getLicenseNumber())) {
					licenseNumberLike = cb.like(root.<String>get("licenseNumber"), "%" + createEnquiryForSearch.getLicenseNumber() + "%");
				}
				if (null != licenseNumberLike) {
					predicateList.add(licenseNumberLike);
				}

				Predicate mobilePhoneEqual = null;// 询价人联系方式
				if (createEnquiryForSearch != null && !StringUtils.isEmpty(createEnquiryForSearch.getMobilePhone())) {
					mobilePhoneEqual = cb.equal(root.<String>get("mobilePhone"),
							createEnquiryForSearch.getMobilePhone());
				}
				if (null != mobilePhoneEqual) {
					predicateList.add(mobilePhoneEqual);
				}

				Predicate stateCodeEqual = null;// 状态
				if (createEnquiryForSearch != null && !StringUtils.isEmpty(createEnquiryForSearch.getStateCode())) {
					stateCodeEqual = cb.equal(root.get("state").<String>get("stateCode"), createEnquiryForSearch.getStateCode());
				}
				if (null != stateCodeEqual) {
					predicateList.add(stateCodeEqual);
				}

				// Order order = sort.getOrderFor("");
				// query.orde;
				// query =
				// query.multiselect(root.get("mobilePhone"),root.get("licenseNumber"));
				query.where(predicateList.toArray(new Predicate[predicateList.size()]));
				return null;
			}

		}, pageable);

		// GenericQueryExpSpecification<CreateEnquiry> queryExpression = new
		// GenericQueryExpSpecification<CreateEnquiry>(
		// new String[] { "id","offerUnique", "ownerName", "licenseNumber",
		// "mobilePhone", "state","isShow","createdAt","lastModifiedAt" });
		// queryExpression
		// .add(QueryExpSpecificationBuilder.eq("offerUnique",
		// createEnquiryForSearch.getOfferUnique(),true))
		// .add(QueryExpSpecificationBuilder.eq("ownerName",
		// createEnquiryForSearch.getOwnerName(), true))
		// .add(QueryExpSpecificationBuilder.eq("licenseNumber",
		// createEnquiryForSearch.getLicenseNumber(), true))
		// .add(QueryExpSpecificationBuilder.eq("mobilePhone",
		// createEnquiryForSearch.getMobilePhone(), true))
		// .add(QueryExpSpecificationBuilder.eq("state.stateCode",
		// createEnquiryForSearch.getStateCode(), true))
		// .add(QueryExpSpecificationBuilder.eq("isShow", 0, true));
		// Page<CreateEnquiry> createEnquiryPage =
		// createEnquiryRepository.findAll(queryExpression, pageable);
		return createEnquiryPage;
	}

	public Page<CreateEnquiry> multiConditionSearchForCustomer(CreateEnquiryForSearch createEnquiryForSearch,
			Pageable pageable, Integer customerId) {

		// Page<CreateEnquiry> createEnquiryPage = createEnquiryRepository.findAll(new
		// Specification<CreateEnquiry>() {
		//
		// @Override
		// public Predicate toPredicate(Root<CreateEnquiry> root, CriteriaQuery<?>
		// query, CriteriaBuilder cb) {
		// List<Predicate> predicateList = new ArrayList<Predicate>();
		//
		// // 用户Id
		// Predicate customerIdEqual = cb.equal(root.get("customer").get("id"),
		// customerId);
		// predicateList.add(customerIdEqual);
		//
		// // 询价单号
		// Predicate offerUniqueEqual = null;
		// if (createEnquiryForSearch != null &&
		// !StringUtils.isEmpty(createEnquiryForSearch.getOfferUnique())) {
		// //
		// offerUniqueEqual = cb.equal(root.<String>get("offerUnique"),
		// createEnquiryForSearch.getOfferUnique());
		// }
		// if (null != offerUniqueEqual) {
		// predicateList.add(offerUniqueEqual);
		// }
		//
		// // 车主 "ownerName":"郭方明"
		// Predicate ownerNameEqual = null;
		// if (createEnquiryForSearch != null &&
		// !StringUtils.isEmpty(createEnquiryForSearch.getOwnerName())) {
		// ownerNameEqual = cb.equal(root.<String>get("ownerName"),
		// createEnquiryForSearch.getOwnerName());
		// }
		// if (null != ownerNameEqual) {
		// predicateList.add(ownerNameEqual);
		// }
		//
		// // 车牌
		// Predicate licenseNumberEqual = null;
		// if (createEnquiryForSearch != null &&
		// !StringUtils.isEmpty(createEnquiryForSearch.getLicenseNumber())) {
		// licenseNumberEqual = cb.equal(root.<String>get("licenseNumber"),
		// createEnquiryForSearch.getLicenseNumber());
		// }
		// if (null != licenseNumberEqual) {
		// predicateList.add(licenseNumberEqual);
		// }
		//
		// // 状态
		// Predicate stateCodeEqual = null;
		// if (createEnquiryForSearch != null &&
		// !StringUtils.isEmpty(createEnquiryForSearch.getStateCode())) {
		// stateCodeEqual = cb.equal(root.get("state").<String>get("stateCode"),
		// createEnquiryForSearch.getStateCode());
		// }
		// if (null != stateCodeEqual) {
		// predicateList.add(stateCodeEqual);
		// }
		//
		// query.where(predicateList.toArray(new Predicate[predicateList.size()]));
		// return null;
		// }
		//
		// }, pageable);

		GenericQueryExpSpecification<CreateEnquiry> queryExpression = new GenericQueryExpSpecification<CreateEnquiry>(
				new String[] { "id", "customer", "offerUnique", "ownerName", "licenseNumber", "mobilePhone", "state",
						"isShow", "createdAt", "lastModifiedAt" });
		queryExpression.add(QueryExpSpecificationBuilder.eq("customer.id", customerId, true))
				.add(QueryExpSpecificationBuilder.eq("offerUnique", createEnquiryForSearch.getOfferUnique(), true))
				.add(QueryExpSpecificationBuilder.eq("ownerName", createEnquiryForSearch.getOwnerName(), true))
				.add(QueryExpSpecificationBuilder.eq("licenseNumber", createEnquiryForSearch.getLicenseNumber(), true))
				.add(QueryExpSpecificationBuilder.eq("mobilePhone", createEnquiryForSearch.getMobilePhone(), true))
				.add(QueryExpSpecificationBuilder.eq("state.stateCode", createEnquiryForSearch.getStateCode(), true))
				.add(QueryExpSpecificationBuilder.eq("isShow", 0, true));
		Page<CreateEnquiry> createEnquiryPage = createEnquiryRepository.findAll(queryExpression, pageable);
		return createEnquiryPage;
	}

	public Page<CreateEnquiry> multiConditionSearchForMobile(CreateEnquiryForSearch createEnquiryForSearch,
			Pageable pageable, String token) {

		Page<CreateEnquiry> createEnquiryPage = createEnquiryRepository.findAll(new Specification<CreateEnquiry>() {

			@Override
			public Predicate toPredicate(Root<CreateEnquiry> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Predicate tokenInfo = cb.equal(root.get("customer").get("token"), token);
				predicateList.add(tokenInfo);
				// Sort sort = new Sort(Direction.DESC, "createdAt");
				// Pageable pageable = new PageRequest(0,0,sort);

				// 是否显示
				Predicate isShowEqual = cb.equal(root.get("isShow"), 0);
				predicateList.add(isShowEqual);

				// 询价单号
				Predicate offerUniqueEqual = null;
				if (createEnquiryForSearch != null && !StringUtils.isEmpty(createEnquiryForSearch.getOfferUnique())) {

					offerUniqueEqual = cb.equal(root.<String>get("offerUnique"),
							createEnquiryForSearch.getOfferUnique());
				}
				if (null != offerUniqueEqual) {
					predicateList.add(offerUniqueEqual);
				}

				// 车主 "ownerName":"郭方明"
				Predicate ownerNameLike = null;
				if (createEnquiryForSearch != null && !StringUtils.isEmpty(createEnquiryForSearch.getOwnerName())) {
					ownerNameLike = cb.like(root.<String>get("ownerName"),
							"%" + createEnquiryForSearch.getOwnerName() + "%");
				}
				if (null != ownerNameLike) {
					predicateList.add(ownerNameLike);
				}

				// 车牌
				Predicate licenseNumberLike = null;
				if (createEnquiryForSearch != null && !StringUtils.isEmpty(createEnquiryForSearch.getLicenseNumber())) {
					licenseNumberLike = cb.like(root.<String>get("licenseNumber"),
							"%" + createEnquiryForSearch.getLicenseNumber() + "%");
				}
				if (null != licenseNumberLike) {
					predicateList.add(licenseNumberLike);
				}

				// 询价人联系方式
				Predicate mobilePhoneEqual = null;
				if (createEnquiryForSearch != null && !StringUtils.isEmpty(createEnquiryForSearch.getMobilePhone())) {
					mobilePhoneEqual = cb.equal(root.<String>get("mobilePhone"),
							createEnquiryForSearch.getMobilePhone());
				}
				if (null != mobilePhoneEqual) {
					predicateList.add(mobilePhoneEqual);
				}

				// 状态
				Predicate stateCodeEqual = null;
				if (createEnquiryForSearch != null && !StringUtils.isEmpty(createEnquiryForSearch.getStateCode())) {
					stateCodeEqual = cb.equal(root.get("state").<String>get("stateCode"),
							createEnquiryForSearch.getStateCode());
				}
				if (null != stateCodeEqual) {
					predicateList.add(stateCodeEqual);
				}
				query.where(predicateList.toArray(new Predicate[predicateList.size()]));
				return null;
			}

		}, pageable);

		return createEnquiryPage;
	}

	// TODO unused 已直接提取到控制器中完成
	public Map<String, List<Object>> findCreateEnquiryListByToken(String token) {
		Map<String, List<Object>> dataMap = new HashMap<>();
		List<CreateEnquiry> createEnquiryList = createEnquiryRepository.findCreateEnquiryListByToken(token);
		List<Object> createEnquiryMapList = new ArrayList<>();
		for (CreateEnquiry ce : createEnquiryList) {
			Map<String, Object> creEnqMap = new HashMap<String, Object>();
			creEnqMap.put("id", ce.getId());
			creEnqMap.put("createdAt", ce.getCreatedAt());
			ICreateEnquiryJsonParseAdapter adapter = new CreateEnquiryJsonParseFactory(ce).createAdapter();
			creEnqMap.put("licenseNumber", adapter.getLicenseNumber());
			creEnqMap.put("ownerName", adapter.getOwnerName());
			creEnqMap.put("schemeName", adapter.getInsurancesListSchemeName());
			creEnqMap.put("cityName", adapter.getCityName());
			creEnqMap.put("offerUnique", ce.getOfferUnique());
			creEnqMap.put("status", ce.getState().getLabel());
			// 返回选择的投保公司
			String insureCompanyIds = (String) adapter.getInsuranceCompanyName();
			String[] insureCompanyIdArray = insureCompanyIds.split(",");
			List<Map<String, Object>> insComMapList = new ArrayList<>();
			for (String companyIdStr : insureCompanyIdArray) {
				InsureCompany insureCompany = insureCompanyRepository
						.findByInsurerCompanyId(Integer.valueOf(companyIdStr));
				if (insureCompany != null) {
					Map<String, Object> insComMap = new HashMap<>();
					insComMap.put("insurerCompanyId", insureCompany.getInsurerCompanyId());
					insComMap.put("name", insureCompany.getName());
					insComMap.put("listIcon", insureCompany.getListIcon());
					insComMapList.add(insComMap);
				}
			}
			creEnqMap.put("insureCompany", insComMapList);
			// 返回投保的公司
			for (OfferResult offerResult : ce.getOfferResult()) {
				String offerId = offerResult.getData().getResult().getOfferId();
				List<SubmitProposal> submitProposal = submitProposalRepository.findByOrderId2(offerId);
				if (null != submitProposal && submitProposal.size() != 0) {
					creEnqMap.put("toubaoCompanyId", offerResult.getData().getResult().getInsuranceCompanyId());
					creEnqMap.put("submitProposalId", submitProposal.get(0).getId());
					creEnqMap.put("submitProposalState", submitProposal.get(0).getState().getLabel());
				}
			}
			createEnquiryMapList.add(creEnqMap);
		}
		dataMap.put("createEnquiries", createEnquiryMapList);
		return dataMap;
	}

	public Map<String, Object> findCreateEnquiryById(Integer id) {
		CreateEnquiry createEnquiry = createEnquiryRepository.findById(id);
		if (null == createEnquiry) {
			throw new FailReturnObject(ExceptionResultEnum.CREQUERY_NOIDINFO_ERROR);
		}
		Map<String, Object> creEnqMap = new HashMap<String, Object>();
		creEnqMap.put("id", createEnquiry.getId());
		creEnqMap.put("createdAt", createEnquiry.getCreatedAt());
		ICreateEnquiryJsonParseAdapter adapter = new CreateEnquiryJsonParseFactory(createEnquiry).createAdapter();
		creEnqMap.put("licenseNumber", adapter.getLicenseNumber());
		creEnqMap.put("ownerName", adapter.getOwnerName());
		creEnqMap.put("schemeName", adapter.getInsurancesListSchemeName());
		creEnqMap.put("cityName", adapter.getCityName());
		creEnqMap.put("idCard", adapter.getIdCard());
		creEnqMap.put("offerUnique", createEnquiry.getOfferUnique());
		creEnqMap.put("status", createEnquiry.getState().getLabel());
		String insureCompanyIds = (String) adapter.getInsuranceCompanyName();
		String[] insureCompanyIdArray = insureCompanyIds.split(",");
		List<Map<String, Object>> insComMapList = new ArrayList<>();
		for (String companyIdStr : insureCompanyIdArray) {
			InsureCompany insureCompany = insureCompanyRepository.findByInsurerCompanyId(Integer.valueOf(companyIdStr));
			if (insureCompany != null) {
				Map<String, Object> insComMap = new HashMap<>();
				insComMap.put("insurerCompanyId", insureCompany.getInsurerCompanyId());
				insComMap.put("name", insureCompany.getName());
				insComMap.put("listIcon", insureCompany.getListIcon());
				insComMapList.add(insComMap);
			}
		}
		creEnqMap.put("insureCompany", insComMapList);
		// 返回投保的公司
		for (OfferResult offerResult : createEnquiry.getOfferResult()) {
			String offerId = offerResult.getData().getResult().getOfferId();
			List<SubmitProposal> submitProposal = submitProposalRepository.findByOrderId2(offerId);
			if (null != submitProposal && submitProposal.size() != 0) {
				creEnqMap.put("toubaoCompanyId", offerResult.getData().getResult().getInsuranceCompanyId());
				creEnqMap.put("submitProposalId", submitProposal.get(0).getId());
				creEnqMap.put("submitProposalState", submitProposal.get(0).getState().getLabel());
			}
		}
		return creEnqMap;
	}

	public Map<String, Object> findCreateEnquiryByCustomerId(Integer customerId) {
		CreateEnquiry createEnquiry = createEnquiryRepository.findById(customerId);
		if (null == createEnquiry) {
			throw new FailReturnObject(ExceptionResultEnum.CREQUERY_NOIDINFO_ERROR);
		}
		Map<String, Object> creEnqMap = new HashMap<String, Object>();
		creEnqMap.put("id", createEnquiry.getId());
		creEnqMap.put("createdAt", createEnquiry.getCreatedAt());
		ICreateEnquiryJsonParseAdapter adapter = new CreateEnquiryJsonParseFactory(createEnquiry).createAdapter();
		creEnqMap.put("licenseNumber", adapter.getLicenseNumber());
		creEnqMap.put("ownerName", adapter.getOwnerName());
		creEnqMap.put("schemeName", adapter.getInsurancesListSchemeName());
		creEnqMap.put("cityName", adapter.getCityName());
		creEnqMap.put("offerUnique", createEnquiry.getOfferUnique());
		creEnqMap.put("status", createEnquiry.getState().getLabel());
		String insureCompanyIds = (String) adapter.getInsuranceCompanyName();
		String[] insureCompanyIdArray = insureCompanyIds.split(",");
		List<Map<String, Object>> insComMapList = new ArrayList<>();
		for (String companyIdStr : insureCompanyIdArray) {
			InsureCompany insureCompany = insureCompanyRepository.findByInsurerCompanyId(Integer.valueOf(companyIdStr));
			if (insureCompany != null) {
				Map<String, Object> insComMap = new HashMap<>();
				insComMap.put("insurerCompanyId", insureCompany.getInsurerCompanyId());
				insComMap.put("name", insureCompany.getName());
				insComMap.put("listIcon", insureCompany.getListIcon());
				insComMapList.add(insComMap);
			}
		}
		creEnqMap.put("insureCompany", insComMapList);
		// 返回投保的公司
		for (OfferResult offerResult : createEnquiry.getOfferResult()) {
			String offerId = offerResult.getData().getResult().getOfferId();
			List<SubmitProposal> submitProposal = submitProposalRepository.findByOrderId2(offerId);
			if (null != submitProposal && submitProposal.size() != 0) {
				creEnqMap.put("toubaoCompanyId", offerResult.getData().getResult().getInsuranceCompanyId());
			}
		}
		return creEnqMap;
	}

	@Transactional
	public String updateLicenseAndOwner() {
		List<CreateEnquiry> creEnquiryList = createEnquiryRepository.findByOwnerNameIsNullAndLicenseNumberIsNull();
		for (CreateEnquiry createEnquiry : creEnquiryList) {
			ICreateEnquiryJsonParseAdapter adapter = new CreateEnquiryJsonParseFactory(createEnquiry).createAdapter();
			createEnquiry.setLicenseNumber((String) adapter.getLicenseNumber());
			createEnquiry.setOwnerName((String) adapter.getOwnerName());
			createEnquiryRepository.save(createEnquiry);
		}
		return "共更新" + creEnquiryList.size() + "条记录";
	}

}
