package com.liyang.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyang.client.IClient;
import com.liyang.client.IServiceObserve;
import com.liyang.client.strategy.SecurityInfoXiaoma;
import com.liyang.client.xiaoma.ClientXiaoma;
import com.liyang.client.xiaoma.MessageQueryPayState;
import com.liyang.client.xiaoma.ResultQueryPayState;
import com.liyang.client.xiaoma.ServiceObserveDbPersistQueryPayState;
import com.liyang.client.xiaoma.ServiceQueryPayState;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.dafengapi.DafengApiCustomer;
import com.liyang.domain.dafengapi.DafengApiCustomerRepository;
import com.liyang.domain.insuranceresult.InsuranceResult;
import com.liyang.domain.insuranceresult.InsuranceResultData;
import com.liyang.domain.insuranceresult.InsuranceResultRepository;
import com.liyang.domain.platform.Platform;
import com.liyang.domain.querypayment.QueryPay;
import com.liyang.domain.querypayment.QueryPayRepository;
import com.liyang.domain.querypayment.RequestPay;
import com.liyang.domain.submitproposal.SubmitProposal;
import com.liyang.domain.submitproposal.SubmitProposalRepository;
import com.liyang.domain.submitproposal.SubmitProposalState;
import com.liyang.domain.submitproposal.SubmitProposalStateRepository;
import com.liyang.domain.team.Team;
import com.liyang.domain.teamobjective.TeamObjective;
import com.liyang.domain.teamobjective.TeamObjectiveRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.handle.GlobalExceptionHandler;
import com.liyang.helper.ResponseBody;
import com.liyang.util.DateUtil;
import com.liyang.util.FailReturnObject;
import com.liyang.util.HttpUtil;
import com.liyang.util.PushAdvertiseUtil;
import com.liyang.util.TransUtils;

import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 */
@Service
public class QueryPayStateService {
	@Autowired
	private QueryPayRepository queryPayRepository;
	@Autowired
	private SubmitProposalRepository submitProposalRepository;
	@Autowired
	private XinGeService xinGeService;
	@Autowired
	private SubmitProposalStateRepository submitProposalStateRepository;
	@Autowired
	private DafengApiCustomerRepository dafengApiCustomerRepository;
	@Autowired
	private InsuranceResultRepository insuranceResultRepository;
	@Autowired
	WebAdvertiseTypeService webAdvTypeService;
	@Autowired
	JournalService journalService;
	@Autowired
	SmsService smsService;
	@Autowired
	TeamObjectiveRepository teamObjRepository;

	private final static Logger logger = LoggerFactory.getLogger(QueryPayStateService.class);

	@Transactional
	public ResponseBody saveQuePayState(Map<String, String> quePayStatMap, HttpServletRequest request,
			String xmcxApiKey) throws Exception {
		//Assert.notNull(xmcx_apiKey);
		MessageQueryPayState message = new MessageQueryPayState(logger, quePayStatMap);
		IClient client = new ClientXiaoma();
		IServiceObserve serviceObserve = new ServiceObserveDbPersistQueryPayState(queryPayRepository);
		SecurityInfoXiaoma securityInfo = new SecurityInfoXiaoma(xmcxApiKey);
		ServiceQueryPayState service = new ServiceQueryPayState(securityInfo, client, serviceObserve,
				queryPayRepository);
		ResultQueryPayState result = (ResultQueryPayState) service.callService(message);

		return new ResponseBody(ExceptionResultEnum.SUCCESS);

		/**
		 * Original code 5 reconstruct 20171123
		 */
		// logger.info("查询支付状况： 订单号" + quePayStatMap.get("orderId"));
		// QueryPay queryPay = new QueryPay();
		// queryPay.setCreateTime(new Date());
		// String orderId = quePayStatMap.get("orderId");
		// if (null == orderId || orderId.trim().equalsIgnoreCase("")) {
		// throw new
		// FailReturnObject(ExceptionResultEnum.QUERYPAY_MIS_DATA_ERROR);
		// }
		//
		// String response = HttpUtil.postForm(xiaomaUrl, quePayStatMap);
		//
		// if (response == null) {
		// throw new
		// FailReturnObject(ExceptionResultEnum.QUERYPAY_RETURN_DATA_ERROR);
		// }
		// //
		// {"errorMsg":{"code":"success","message":"操作成功"},"data":true,"time":null,"successful":true}
		// ObjectMapper objectMapper = new ObjectMapper();
		// @SuppressWarnings("unchecked")
		// Map<String, Object> originalResponseDataMap =
		// objectMapper.readValue(response, Map.class);
		// Object errorMsg;
		// if (null == originalResponseDataMap || null == (errorMsg =
		// originalResponseDataMap.get("errorMsg"))
		// || !(errorMsg.toString().contains("success"))) {
		// throw new
		// FailReturnObject(ExceptionResultEnum.QUERYPAY_RETURN_DATA_ERROR);
		// }
		// queryPay.setOrderId(orderId);
		// queryPay.setResponse(response);
		// // 保证数据库里只有一条查询确认支付的数据
		// List<QueryPay> queryPayList =
		// queryPayRepository.findByOrderId(orderId);
		// if (queryPayList != null && !(queryPayList.isEmpty())) {
		// queryPay.setId(queryPayList.get(0).getId());
		// }
		//
		// queryPayRepository.save(queryPay);
		// logger.info("查询支付直接返回：" +
		// JSONObject.fromObject(response).toString());
		// return new ResponseBody(ExceptionResultEnum.SUCCESS);
	}

	public String doConfirmPay(Map<String, Object> cfmMap) {

		QueryPay queryPay = null;
		// 将map推送的数据转化成指定对象
		try {
			queryPay = TransUtils.mapTransObject(cfmMap, QueryPay.class);

		} catch (Exception e) {
			logger.error(e.toString());
			throw new FailReturnObject(ExceptionResultEnum.COMFIRM_PAYRESULT_DATA_TRANS_ERROR);
		}

		String orderId = queryPay.getOrderId();
		if (null == orderId || orderId.trim().equalsIgnoreCase("")) {
			throw new FailReturnObject(ExceptionResultEnum.COMFIRM_PAYRESULT_DATA_MIS_ERROR);
		}

		List<QueryPay> queryPayList = queryPayRepository.findByOrderId(orderId);
		if (queryPayList != null && !(queryPayList.isEmpty())) {
			queryPay.setId(queryPayList.get(0).getId());
		}
		queryPay.setReturnTime(new Date());
		queryPayRepository.save(queryPay);

		List<SubmitProposal> subProposalList = submitProposalRepository.findByParamsOrderId(orderId);
		if (subProposalList == null || subProposalList.isEmpty()) {
			throw new FailReturnObject(ExceptionResultEnum.COMFIRM_PAYRESULT_NOORDERINFO_ERROR);
		}
		SubmitProposal submitProposal = subProposalList.get(0);
		SubmitProposalState sps = null;
		if (10 == queryPay.getState()) {
			sps = submitProposalStateRepository.findByStateCode("PAYMENT_SUCCESS");
			submitProposal.setState(sps);
		}
		submitProposalRepository.save(submitProposal);
		// TODO 转发OL
		// 判断是否是api用户
		Platform platform = submitProposal.getPlatform();
		List<DafengApiCustomer> apiCustomers = dafengApiCustomerRepository.findByPlatform(platform);
		if (apiCustomers != null && !apiCustomers.isEmpty()) {
			Map<String, String> payResultMap4Api = new HashMap<String, String>();
			JSONObject json = JSONObject.fromObject(cfmMap);
			// TODO 新增PlatformPayResultURL
			String dfApiPayResURL = platform.getPlatformPayResultURL();
			for (int i = 0; i < 3; i++) {
				String payRes4ApiResponse = HttpUtil.postRawJsonBody(dfApiPayResURL, json.toString());
				if (payRes4ApiResponse != null) { // check response
					logger.info("payResult转发OL 成功:" + payRes4ApiResponse);
					break;
				}
			}
			return "{\"data\":{\"orderId\":\"" + orderId
					+ "\"},\"errorMsg\":{\"code\":\"success\",\"message\":\"操作成功\"},\"successful\":true}";
		}
		// push advertise for MobileApp
		Map<String, Object> handerDataMap = new HashMap<String, Object>();
		handerDataMap.put("name", "confirmPay");
		handerDataMap.put("orderId", orderId);

		Customer customer = submitProposal.getCustomer();
		PushAdvertiseUtil.pushAdvertToAppPlatform(xinGeService, customer, handerDataMap, "确认支付结果");
		return "{\"data\":{\"orderId\":\"" + orderId
				+ "\"},\"errorMsg\":{\"code\":\"success\",\"message\":\"操作成功\"},\"successful\":true}";
	}

	/*
	 * Original Code 4 Reconstru
	 */
	// //文档坑人
	//// Map data =new HashMap();
	//// if(null != cfmMap){
	//// data = (Map)cfmMap.get("data");
	//// }
	// String orderId = null ;
	// if(cfmMap != null ){
	// orderId = (String)cfmMap.get("orderId");
	// }
	// QueryPay queryPay = null ;
	// if(null != orderId){
	// queryPay = queryPayRepository.getByOrderId(orderId);
	// }
	//
	// if(null == queryPay){
	// throw new
	// FailReturnObject(ExceptionResultEnum.COMFIRM_PAYRESULT_NOPAYRESULT_ERROR);
	//
	// }
	// queryPay.setLicenseNumber((String)cfmMap.get("licenseNumber"));
	// queryPay.setReturnTime(new Date());
	// queryPay.setState((Integer)cfmMap.get("state"));
	// queryPay.setMessage((String) cfmMap.get("message"));
	//
	// queryPayRepository.save(queryPay);
	//
	// Map<String, Object> result = new HashMap<>();
	// Map<String, Object> data2 = new HashMap<>();
	// data2.put("name", "confirmPay");
	// data2.put("orderId", orderId);
	// result.put("data", data2);
	//
	// SubmitProposal submitProposal =
	// submitProposalRepository.findByOrderId(orderId);
	// if(null == submitProposal){
	// throw new
	// FailReturnObject(ExceptionResultEnum.COMFIRM_PAYRESULT_NOORDERINFO_ERROR);
	//// throw new DisplayException("找不到该orderId的核保信息");
	// }
	//
	// if(10 == (Integer)cfmMap.get("state")){
	// SubmitProposalState sps =
	// submitProposalStateRepository.findByStateCode("PAYMENT_SUCCESS");
	// submitProposal.setState(sps);
	//
	// submitProposalRepository.save(submitProposal);
	//
	// Customer customer = submitProposal.getCustomer();
	// if( null == customer){
	// throw new
	// FailReturnObject(ExceptionResultEnum.COMFIRM_PAYRESULT_NOUSERINFO_ERROR);
	//// throw new DisplayException("找不到该orderId的用户信息");
	// }
	//
	// if ("ios".equals(customer.getClient())) {
	// // ios来的用户
	// System.out.println("推送至IOS平台");
	// xinGeService.pushIOS(customer.getPushToken(),result,"确认支付结果");
	// } else if ("android".equals(customer.getClient())) {
	// // 安卓来的用户
	// System.out.println("推送至安卓平台");
	// xinGeService.pushAndroid(customer.getPushToken(),result,"确认支付结果","确认支付结果返回");
	// } else {
	// throw new
	// FailReturnObject(ExceptionResultEnum.COMFIRM_PAYRESULT_PRICERESULLT_ERROR);
	//// throw new FailReturnObject(100, "报价结果无法推送");
	// }
	// }else{
	//// SubmitProposalState sps =
	// submitProposalStateRepository.findByStateCode("PAYMENT_FAILD");
	//// submitProposal.setState(sps);
	// }
	//
	// // TODO 转发OL
	// /*// 判断是否是api用户
	// Platform platform = submitProposal.getPlatform();
	// List<DafengApiCustomer> apiCustomers =
	// dafengApiCustomerRepository.findByPlatform(platform);
	// if (apiCustomers != null && !apiCustomers.isEmpty()) {
	// Map<String, String> payResultMap4Api = new HashMap<String, String>();
	// JSONObject json = JSONObject.fromObject(cfmMap);
	// payResultMap4Api.put("data", json.toString());
	// //TODO 新增PlatformPayResultURL
	// String dfApiPayResURL = platform.getPlatformPayResultURL();
	// for (int i = 0; i < 3; i++) {
	// String offerRes4ApiResponse = HttpUtil.postBody(dfApiPayResURL,
	// JSONObject.fromObject(payResultMap4Api).toString());
	// if (offerRes4ApiResponse.equalsIgnoreCase("true")) { // check response
	// logger.info("payResult转发OL 成功");
	// break;
	// }
	// }
	// }*/
	//
	// return "{\"data\":{\"orderId\":\""+ orderId +
	// "\"},\"errorMsg\":{\"code\":\"success\",\"message\":\"操作成功\"},\"successful\":true}";
	// }

	@Transactional
	public String doConfirmPayTianan(RequestPay requestPay) throws Exception {

		List<SubmitProposal> subProposalList = submitProposalRepository.findByParamsOrderId(requestPay.getOrderNo());
		if (subProposalList == null || subProposalList.isEmpty()) {
			throw new FailReturnObject(ExceptionResultEnum.COMFIRM_PAYRESULT_NOORDERINFO_ERROR_TIANAN);
		}
		SubmitProposal submitProposal = subProposalList.get(0);

		// 保存支付记录
		QueryPay queryPay = new QueryPay();
		// 获取渠道商订单号，等同于小马的orderId
		String orderNo = requestPay.getOrderNo();
		if (null == orderNo || orderNo.trim().equalsIgnoreCase("")) {
			throw new FailReturnObject(ExceptionResultEnum.COMFIRM_PAYRESULT_DATA_MIS_ERROR, ":orderNo为空");
		}
		queryPay.setOrderId(orderNo);
		// 天安网销订单号，与小马的orderId是两个不同的字段
		String orderId = requestPay.getOrderId();
		queryPay.setPaymentId(orderId);
		if (requestPay.getStatue() == null) {
			throw new FailReturnObject(ExceptionResultEnum.COMFIRM_PAYRESULT_DATA_MIS_ERROR, ":statue为空");
		}
		queryPay.setState(convertStateForTianan(requestPay.getStatue()));
		if (queryPay.getState().intValue() == 10) {
			queryPay.setMessage("支付成功");
		} else {
			queryPay.setMessage("未支付");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date returnTime = sdf.parse(requestPay.getBankTradeDate());
		queryPay.setReturnTime(returnTime);
		queryPay.setFee(requestPay.getFee());
		queryPay.setExt1(requestPay.getExt1());
		queryPay.setExt1(requestPay.getExt1());
		queryPay.setPayNo(requestPay.getPayNo());
		queryPay.setPolicyNo(requestPay.getPolicyNo());
		queryPay.setBizPolicyNo(requestPay.getBizPolicyNo());
		queryPay.setForcePolicyNo(requestPay.getForcePolicyNo());
		queryPay.setRcldPolicyNo(requestPay.getRcldPolicyNo());
		queryPay.setCreateTime(new Date());
		queryPay.setLicenseNumber(submitProposal.getOfferResult().getCreateEnquiry().getLicenseNumber());
		queryPayRepository.save(queryPay);
		// 天安支付成功则表示承保成功,保存承保成功结果
		if (queryPay.getState().intValue() == 10) {
			InsuranceResult insuranceResult = new InsuranceResult();
			InsuranceResultData insuranceResultData = new InsuranceResultData();
			insuranceResultData.setOrderId(orderNo);
			insuranceResultData.setState(4); // 状态(4-承保成功,5-承保失败)
			insuranceResultData.setBiPolicyNo(requestPay.getBizPolicyNo());
			insuranceResultData.setCiPolicyNo(requestPay.getForcePolicyNo());
			try {
				insuranceResultData.setLicenseNumber(submitProposal.getOfferResult().getCreateEnquiry().getLicenseNumber());
			} catch (NullPointerException e) {
				logger.info("天安确认支付车牌获取空指针异常");
			}
			insuranceResult.setData(insuranceResultData);
			insuranceResult.setSuccessful(true);
			Platform platform = submitProposal.getPlatform();
			if (platform != null) {
				platform.setClinchFrequency(platform.getClinchFrequency() + 1);
			}
			insuranceResult.setPlatform(platform);
			List<InsuranceResult> insurResList = insuranceResultRepository.findByDataOrderId(orderNo);
			if (insurResList != null && !(insurResList.isEmpty())) {
				insuranceResult.setId(insurResList.get(0).getId());
			}
			// 关联团队业绩
			Team team = submitProposal.getCustomer().getTeam();
			if (null != team) {
				TeamObjective teamObjective = teamObjRepository.findByTeamAndPeriod(team, DateUtil.format2YearMonthStr(new Date()));
				if (insurResList.isEmpty()) {
					teamObjective.setAutoCompletion(teamObjective.getAutoCompletion() + requestPay.getFee());
				}
				insuranceResult.setTeamObjective(teamObjective);
			}
			// 关联订单
			insuranceResult.setSubmitProposal(submitProposal);
			insuranceResult = insuranceResultRepository.save(insuranceResult);
			// 修改订单状态
			// 若支付成功，订单的状态变更为承保成功
			SubmitProposalState sps = submitProposalStateRepository.findByStateCode("CHENGBAO_SUCCESS");
			// 设置大蜂地址可修改时间
			submitProposal.getParams().setAddressExpireTime(DateUtil.get24HLater(new Date()));
			submitProposal.setState(sps);
			submitProposalRepository.save(submitProposal);
			// 消息推送
			Customer customer = submitProposal.getCustomer();
			try {
				Map<String, Object> handerDataMap = new HashMap<String, Object>();
				handerDataMap.put("name", "confirmPay");
				handerDataMap.put("orderId", orderNo);
				PushAdvertiseUtil.pushAdvertToAppPlatform(xinGeService, customer, handerDataMap, "确认承保结果");
			} catch (Exception e) {
				logger.error("【-------承保结果推送发生异常------】");
				logger.error(GlobalExceptionHandler.getTraceInfo(e));
			}
			webAdvTypeService.sendWebAdvertise(insuranceResult, requestPay.getFee()); // 后台消息通知
			journalService.generateJournalAndSave(insuranceResult, submitProposal, "天安保险", orderNo, returnTime, logger);// 创建账户流水
			// TODO 因为模板长度问题，天安短信暂无法发送
//			smsService.sendMessage(insuranceResult, requestPay.getFee(), "天安保险", requestPay.getBizPolicyNo(), requestPay.getForcePolicyNo(), customer, logger);
		}
		return "success";
		//未支付不修改状态
	}

	/**
	 * 小马中 10：支付成功，2：未支付;天安中，0：未支付，1：已支付
	 * @param statue
	 * @return
	 */
	private Integer convertStateForTianan(Integer statue) {
		if (1 == statue.intValue()) {
			return 10;
		} else if (0 == statue.intValue()) {
			return 2;
		} else {
			throw new FailReturnObject(ExceptionResultEnum.COMFIRM_PAYRESULT_DATA_MIS_ERROR,
					":statue为" + statue.intValue());
		}
	}

}
