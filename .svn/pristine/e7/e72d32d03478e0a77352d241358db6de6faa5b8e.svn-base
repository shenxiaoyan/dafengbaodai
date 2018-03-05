package com.liyang.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.liyang.client.IClient;
import com.liyang.client.IServiceObserve;
import com.liyang.client.strategy.SecurityInfoXiaoma;
import com.liyang.client.xiaoma.ClientXiaoma;
import com.liyang.client.xiaoma.MessageConfirmUnderWriting;
import com.liyang.client.xiaoma.ResultConfirmUnderWriting;
import com.liyang.client.xiaoma.ServiceConfirmUnderWriting;
import com.liyang.domain.advertise.Advertise;
import com.liyang.domain.advertise.AdvertiseRepository;
import com.liyang.domain.base.AbstractAuditorState;
import com.liyang.domain.createenquiry.CreateEnquiry;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.dafengapi.DafengApiCustomer;
import com.liyang.domain.dafengapi.DafengApiCustomerRepository;
import com.liyang.domain.insuranceresult.InsuranceResult;
import com.liyang.domain.insuranceresult.InsuranceResultData;
import com.liyang.domain.insuranceresult.InsuranceResultRepository;
import com.liyang.domain.offerresult.OfferResult;
import com.liyang.domain.offerresult.OfferResultData;
import com.liyang.domain.offerresult.OfferResultRepository;
import com.liyang.domain.platform.Platform;
import com.liyang.domain.submitproposal.SubmitProposal;
import com.liyang.domain.submitproposal.SubmitProposalParams;
import com.liyang.domain.submitproposal.SubmitProposalRepository;
import com.liyang.domain.submitproposal.SubmitProposalState;
import com.liyang.domain.submitproposal.SubmitProposalStateRepository;
import com.liyang.domain.underwritingresult.UnderwritingResult;
import com.liyang.domain.underwritingresult.UnderwritingResultRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.util.FailReturnObject;
import com.liyang.util.HttpUtil;
import com.liyang.util.PushAdvertiseUtil;
import com.liyang.util.TransUtils;

import net.sf.json.JSONObject;

/**
 * 核保结果
 * @author Administrator
 *
 */
@Service
public class UnderwritingResultService {

	@Autowired
	InsuranceResultRepository insuranceResultRepository;

	@Autowired
	UnderwritingResultRepository underwritingResultRepository;

	@Autowired
	OfferResultRepository offerResultRepository;

	@Autowired
	SubmitProposalRepository submitProposalRepository;

	@Autowired
	XinGeService xinGeService;

	@Autowired
	AdvertiseRepository advertiseRepository;

	@Autowired
	SubmitProposalStateRepository submitProposalStateRepository;

	@Autowired
	DafengApiCustomerRepository dafengApiCustomerRepository;

	@Value("${xmcxapi.confirmunderwriting.url}")
	private String xmConfUnderWritingUrl;

	@Value("${xmcxapi.apikey}")
	private String xmApikey;

	private final static Logger logger = LoggerFactory.getLogger(UnderwritingResultService.class);
	
	@Transactional
	public String saveUnderResultAndForward(Map<String, Object> underResultMap) throws Exception {

		UnderwritingResult underwritingResult = null;
		try {
			underwritingResult = TransUtils.mapTransObject(underResultMap, UnderwritingResult.class);
		} catch (Exception e) {
			logger.error(e.toString());
			throw new FailReturnObject(ExceptionResultEnum.UNDERWRITING_DATA_TRANS_ERROR);
		}
		String orderId = underwritingResult.getData().getOrderId();
		//TODO 为何是List?	by------Djh
		List<SubmitProposal> submitProposalList = submitProposalRepository.findByParamsOrderId(orderId);
		if (null == submitProposalList || submitProposalList.isEmpty()) {
			throw new FailReturnObject(ExceptionResultEnum.UNDERWRITING_SUBPROPOSAL_DATA_MIS_ERROR);
		}
		underwritingResult.setCreatedAt(new Date());
		SubmitProposal submitProposal = submitProposalList.get(0);
		submitProposal.setUnderwritingResult(underwritingResult);
		// 根据orderId，关联平台
		Platform platform = submitProposal.getPlatform();
		underwritingResult.setPlatform(platform);

		// 原来有记录的则是更新
		List<UnderwritingResult> undwritingResultList = underwritingResultRepository.findByDataOrderId(orderId);
		if (null != undwritingResultList && !(undwritingResultList.isEmpty())) {
			underwritingResult.setId(undwritingResultList.get(0).getId());
			underwritingResult.getData().setId(undwritingResultList.get(0).getData().getId());
		}
		Integer state = underwritingResult.getData().getState();
		SubmitProposalState subProposalState = null;
		if (2 == state) {
			subProposalState = submitProposalStateRepository.findByStateCode("HENBAO_SUCCESS_PAYMENT");
		} else if (15 == state) {
			subProposalState = submitProposalStateRepository.findByStateCode("HENBAO_PERSON");
		} else if (16 == state) {
			subProposalState = submitProposalStateRepository.findByStateCode("HEBAO_WAIT_CONFRIM");
		} else {
			subProposalState = submitProposalStateRepository.findByStateCode("HENBAO_FAILED");
		}

		// 15-人工核保中,16:核保通过待确认,
		submitProposal.setState(subProposalState);
		underwritingResult.setSubmitProposal(submitProposal);
		// 保存核保推送结果
		underwritingResult = underwritingResultRepository.save(underwritingResult);

		// for Dafeng Api Customer
		List<DafengApiCustomer> dafengApiCustomers = dafengApiCustomerRepository.findByPlatform(platform);
		if (dafengApiCustomers != null && (!dafengApiCustomers.isEmpty())) {
			// forward to Dafeng API customer
			Map<String, Object> underWritResultMap4Api = new HashMap<String, Object>();
			String dfApiUndWriResURL = platform.getPlatformUnderwritingResultURL();
			for (int i = 0; i < 3; i++) {
				String underWritRes4ApiResponse = HttpUtil.postRawJsonBody(dfApiUndWriResURL,
						JSONObject.fromObject(underResultMap).toString());
				if (underWritRes4ApiResponse != null) { // check response
					logger.info("【underWritRes4ApiResponse 转发OL 成功】:" + underWritRes4ApiResponse);
					break;
				}
			}
			return responseReturn(orderId);
		}

		// 简单数据处理封装推送至安卓和ios
		Map<String, Object> handerDataMap = new HashMap<String, Object>();
		handerDataMap.put("Id", underwritingResult.getId());
		handerDataMap.put("name", "underwritingResult");
		handerDataMap.put("orderId", orderId);

		List<Advertise> advertiseList = advertiseRepository.findByOfferIdAndZnxType(orderId, 2);
		if (null == advertiseList || advertiseList.isEmpty()) {
			Advertise advertis = generateUnderWritingAdvertise(submitProposal, orderId);
			// 报价结果推送到指定平台
			Customer customer = submitProposal.getCustomer();
			PushAdvertiseUtil.pushAdvertToAppPlatform(xinGeService, customer, handerDataMap, advertis.getTitle());
		}
		
		//web端通知发送
		
		
		

		return responseReturn(orderId);

		/*
		 * Original Code 4 Reconstru
		 * 
		 */
		// //将核保结果数据封装成对象
		// UnderwritingResult underwritingResult = null;
		// try{
		// underwritingResult=TransUtils.mapTransObject(underResultMap,UnderwritingResult.class);
		// }catch (Exception e) {
		// logger.error(e.toString());
		// throw new
		// FailReturnObject(ExceptionResultEnum.UNDERWRITING_DATA_TRANS_ERROR);
		// }
		//
		// //根据orderId，关联平台
		// String
		// orderId=JSONObject.fromObject(underResultMap).getJSONObject("data").getString("orderId");
		// Platform
		// platform=offerResultRepository.findPlatformByOfferId(orderId);
		// underwritingResult.setPlatform(platform);
		//
		//
		// //根据orderId，关联提交核保数据
		// SubmitProposal
		// submitProposals=submitProposalRepository.findByOrderId(orderId);
		//
		// //保存核保推送结果
		// //原来有记录的则是更新
		// UnderwritingResult underres =
		// underwritingResultRepository.findUnderwritingResultByOrderId(orderId);
		// if( null != underres){
		// Integer idd = underres.getId();
		// underwritingResult.setId(idd);
		// }
		// underwritingResultRepository.save(underwritingResult);
		//
		// submitProposals.setUnderwritingResult(underwritingResult);
		//
		// Integer state =
		// JSONObject.fromObject(underResultMap).getJSONObject("data").getInt("state");
		// String statee = null ;
		// if(state == 2){
		// SubmitProposalState sps =
		// submitProposalStateRepository.findByStateCode("HENBAO_SUCCESS_PAYMENT");
		// statee = "已通过";
		// submitProposals.setState(sps);
		// }else{
		// SubmitProposalState sps2 =
		// submitProposalStateRepository.findByStateCode("HENBAO_FAILED");
		// JSONObject undwri =
		// underwritingResult.getData().getUnderwritingJson();
		// statee = "未通过";
		// if(undwri != null){
		// statee += "\n失败原因:"+ undwri.getString("errorMsg");
		// }
		// submitProposals.setState(sps2);
		// }
		//
		//
		// submitProposalRepository.save(submitProposals);
		//
		// // for Dafeng Api Customer
		// List<DafengApiCustomer> dafengApiCustomers =
		// dafengApiCustomerRepository.findByPlatform(platform);
		// if(dafengApiCustomers !=null&& (!dafengApiCustomers.isEmpty())){
		// // forward to Dafeng API customer
		// Map<String, String> underWritResultMap4Api = new HashMap<String,
		// String>();
		// JSONObject underResJson = JSONObject.fromObject(underResultMap);
		// underWritResultMap4Api.put("data", underResJson.toString());
		// System.out.println(" underWritResultMap4Api)...:"+
		// underWritResultMap4Api);
		//// String offerRes4ApiResponse =
		// HttpUtil.postForm("postToDfgApiCustUrl", offerResultMap4Api);
		//
		// System.out.println("SUCCESS POST UNDER WRITING TO API CUSTOMER" );
		// return responseReturn(orderId);
		// }
		//
		//
		// //简单数据处理封装推送至安卓和ios
		// Map<String, Object> data=new HashMap<String,Object>();
		// data.put("Id", underwritingResult.getId());
		// data.put("orderId", orderId);
		// data.put("name","underwritingResult");
		// Map<String, Object> handerDataMap=new HashMap<String,Object>();
		//
		// Customer customer=submitProposals.getCustomer();
		//
		// Map<String, Object> advertise = new HashMap<>();
		// advertise.put("title",
		// "【核保通知】"+submitProposals.getOfferResult().getCreateEnquiry().getCreateEnquiryParams().getString("licenseNumber")+"有新的核保结果");
		// String content =
		// "车牌:"+submitProposals.getOfferResult().getCreateEnquiry().getCreateEnquiryParams().getString("licenseNumber")
		// +"\n被保人:"+submitProposals.getParams().getInsuredName()
		// + "\n保险公司:" +
		// submitProposals.getOfferResult().getData().getResult().getInsuranceCompanyName()
		// + "\n核保状态:" + statee ;
		// advertise.put("content", content);
		// advertise.put("createAt", new Date());
		//
		// Advertise ad = new Advertise();
		// ad.setTitle((String) advertise.get("title"));
		// ad.setContent(content);
		// ad.setIsRead(0);
		// ad.setZnxType(2);
		// ad.setType(2);
		// ad.setToken(customer.getToken());
		// advertiseRepository.save(ad);
		//
		// data.put("advertise", advertise);
		// handerDataMap.put("data", JSONObject.fromObject(data));
		//
		//
		// //获取用户,推送平台
		// if(customer==null) {
		// //将结果post到指定的第三方平台
		// System.out.println("推送至其他平台");
		//// HttpUtil.postBody(platform.getPlatformOfferURL(),
		// JSONObject.fromObject(temResMap).toString());
		// }else if(customer.getClient().equals("ios")) {
		// // ios来的用户
		// System.out.println("推送至IOS平台");
		// xinGeService.pushIOS(customer.getPushToken(), handerDataMap,(String)
		// advertise.get("title"));
		// }else if(customer.getClient().equals("android")) {
		// // 安卓来的用户
		// System.out.println("推送至安卓平台");
		// xinGeService.pushAndroid(customer.getPushToken(),
		// handerDataMap,(String) advertise.get("title"),"核保结果返回");
		// }else {
		// throw new
		// FailReturnObject(ExceptionResultEnum.UNDERWRITING_RESULT_PUSN_ERROR);
		//// throw new FailReturnObject(600, "核保结果无法推送");
		// }
		// return responseReturn(orderId);
	}

	private Advertise generateUnderWritingAdvertise(SubmitProposal submitProposal, String orderId) {
		OfferResult offerResult = submitProposal.getOfferResult();
		String token = submitProposal.getCustomer().getToken();
		String licenseNumber = submitProposal.getUnderwritingResult().getData().getLicenseNumber();
		String insuredName = submitProposal.getParams().getInsuredName();
		String statLabel = submitProposal.getState().getLabel();
		// titile
		StringBuffer titileBuf = new StringBuffer();
		titileBuf.append("【核保通知】").append(licenseNumber).append("有新的核保结果");

		// content
		StringBuffer contentBuf = new StringBuffer();
		contentBuf.append("车牌：").append(licenseNumber).append("\n被保人：").append(insuredName);
		contentBuf.append("\n保险公司：").append(offerResult.getData().getResult().getInsuranceCompanyName());
		contentBuf.append("\n核保状态:").append(statLabel);

		Advertise advertise = new Advertise();
		advertise.setTitle(titileBuf.toString());
		advertise.setContent(contentBuf.toString());
		advertise.setIsRead(0);
		advertise.setZnxType(2);
		advertise.setType(2);
		advertise.setToken(token);
		advertise.setOfferId(orderId);
		advertise = advertiseRepository.save(advertise);

		return advertise;
	}

	public String responseReturn(String offerId) {
		return "{\"data\":{\"orderId\":\"" + offerId
				+ "\"},\"errorMsg\":{\"code\":\"success\",\"message\":\"操作成功\"},\"successful\":true}";
	}

	public ResponseBody reqConfirmUnderwriting(String orderId) throws Exception {
		//Assert.notNull(xmApikey);
		MessageConfirmUnderWriting message = new MessageConfirmUnderWriting(logger, orderId);
		IClient client = new ClientXiaoma();
		IServiceObserve serviceObserve = null;
		String xiaomaUrl = xmConfUnderWritingUrl + "?api_key=" + xmApikey;
		// ServiceConfirmUnderWriting service = new
		// ServiceConfirmUnderWriting(XiaomaUrl, client, serviceObserve);
		SecurityInfoXiaoma securityInfo = new SecurityInfoXiaoma(xmApikey);
		ServiceConfirmUnderWriting service = new ServiceConfirmUnderWriting(securityInfo, client, serviceObserve);
		ResultConfirmUnderWriting result = (ResultConfirmUnderWriting) service.callService(message);

		return new ResponseBody(ExceptionResultEnum.SUCCESS);

		/**
		 * Original code 5 reconstruct 20171124
		 */
		// logger.info("请求承保结果.orderId:" + orderId);
		// Map<String, String> reqMap = new HashMap<String, String>();
		// reqMap.put("orderId", orderId);
		// if (null == orderId || orderId.trim().equalsIgnoreCase("")) {
		// throw new
		// FailReturnObject(ExceptionResultEnum.INSURANCERES_MIS_DATA_ERROR);
		// }
		//
		// String responseResult = HttpUtil.postForm(xmConfUnderWritingUrl +
		// "?api_key=" + xmApikey, reqMap);
		//
		// if (responseResult == null) {
		// throw new
		// FailReturnObject(ExceptionResultEnum.UNDERWRITING_FAIL_ASKINTERFACE_ERROR);
		// }
		//
		// //
		// {"errorMsg":{"code":"success","message":"操作成功"},"data":true,"time":null,"successful":true}
		// ObjectMapper objectMapper = new ObjectMapper();
		// @SuppressWarnings("unchecked")
		// Map<String, Object> originalResponseDataMap =
		// objectMapper.readValue(responseResult, Map.class);
		// Object errorMsg;
		// if (null == originalResponseDataMap || null == (errorMsg =
		// originalResponseDataMap.get("errorMsg"))
		// || !(errorMsg.toString().contains("success"))) {
		// throw new
		// FailReturnObject(ExceptionResultEnum.UNDERWRITING_FAIL_ASKINTERFACE_ERROR);
		// }
		// logger.info("请求承保结果直接返回 :" + responseResult);
		// return new ResponseBody(ExceptionResultEnum.SUCCESS);

	}

	public ResponseBody getUnderwritingResult(String orderId) throws Exception {

		// List<SubmitProposal> submitProposals =
		// submitProposalRepository.findByOrderId2(orderId);
		// Map<String,Object> uwrMap = new HashMap<>();
		// TODO
		SubmitProposal proposal = submitProposalRepository.findByOrderId(orderId);
		List<Object> resultList = new ArrayList<Object>();
		// for (SubmitProposal proposal : submitProposals) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("id", proposal.getId());
		resultMap.put("creteTime", proposal.getCreateTime());
		// 获取信息中的state部分
		AbstractAuditorState state = proposal.getState();
		Map<String, Object> stateMap = new HashMap<>();
		stateMap.put("id", state.getId());
		stateMap.put("label", state.getLabel());
		stateMap.put("createAt", state.getCreatedAt());
		stateMap.put("lastModifiedAt", state.getLastModifiedAt());
		stateMap.put("sort", state.getSort());
		stateMap.put("stateCode", state.getStateCode());
		stateMap.put("stateGroup", state.getStateGroup());
		resultMap.put("state", stateMap);
		// 获取信息中params部分
		SubmitProposalParams params = proposal.getParams();
		resultMap.put("params", params);
		// 获取信息中offerResult部分
		OfferResult offerResult = proposal.getOfferResult();
		Map<String, Object> offerResultMap = new HashMap<>();
		offerResultMap.put("id", offerResult.getId());
		offerResultMap.put("errorMsg", offerResult.getErrorMsg());
		offerResultMap.put("successful", offerResult.getSuccessful());
		offerResultMap.put("time", offerResult.getTime());
		if (offerResult != null) {
			OfferResultData data = offerResult.getData();
			offerResultMap.put("data", data);
			// 获取createEnquiry部分
			CreateEnquiry createEnquiry = offerResult.getCreateEnquiry();
			Map<String, Object> ceMap = new HashMap<>();
			ceMap.put("id", createEnquiry.getId());
			ceMap.put("label", createEnquiry.getLabel());
			ceMap.put("createAt", createEnquiry.getCreatedAt());
			ceMap.put("lastModifiedAt", createEnquiry.getLastModifiedAt());
			ceMap.put("stateAcList", createEnquiry.getStateActList());
			ceMap.put("fileCategoryTree", createEnquiry.getFileCategoryTree());
			ceMap.put("filePackage", createEnquiry.getFilePackage());
			ceMap.put("topcategory", createEnquiry.getTopcategory());
			ceMap.put("subcategory", createEnquiry.getSubcategory());
			ceMap.put("mobilePhone", createEnquiry.getMobilePhone());
			ceMap.put("createEnquiryParams", createEnquiry.getCreateEnquiryParams());
			ceMap.put("ownerName", createEnquiry.getOwnerName());
			ceMap.put("licenseNumber", createEnquiry.getLicenseNumber());
			ceMap.put("responseResult", createEnquiry.getResponseResult());
			ceMap.put("offerUnique", createEnquiry.getOfferUnique());
			ceMap.put("isShow", createEnquiry.getIsShow());
			ceMap.put("stateCode", createEnquiry.getStateCode());
			resultMap.put("createEnquiry", ceMap);
		}
		resultMap.put("offerResult", offerResultMap);
		// resultMap.put("id", proposal.getId());
		// resultMap.put("createTime", proposal.getCreateTime());
		// resultMap.put("insuredName", params.getInsuredName());
		// 获取customer部分
		Customer customer = proposal.getCustomer();
		Map<String, Object> customerMap = new HashMap<>();
		customerMap.put("id", customer.getId());
		customerMap.put("label", customer.getLabel());
		customerMap.put("createAt", customer.getCreatedAt());
		customerMap.put("lastModifiedAt", customer.getLastModifiedAt());
		customerMap.put("stateAcList", customer.getStateActList());
		customerMap.put("fileCategoryTree", customer.getFileCategoryTree());
		customerMap.put("filePackage", customer.getFilePackage());
		customerMap.put("topcategory", customer.getTopcategory());
		customerMap.put("subcategory", customer.getSubcategory());
		customerMap.put("nickname", customer.getNickname());
		customerMap.put("sex", customer.getSex());
		customerMap.put("headimgurl", customer.getHeadimgurl());
		customerMap.put("invite", customer.getInvite());
		customerMap.put("myInvite", customer.getMyInvite());
		customerMap.put("token", customer.getToken());
		customerMap.put("pushToken", customer.getPushToken());
		customerMap.put("client", customer.getClient());
		customerMap.put("tag", customer.getTag());
		customerMap.put("phone", customer.getPhone());
		customerMap.put("grade", customer.getGrade());
		customerMap.put("createTime", customer.getCreateTime());
		customerMap.put("updateTime", customer.getUpdateTime());
		customerMap.put("stateCode", customer.getStateCode());
		resultMap.put("customer", customerMap);

		SubmitProposal submitProposal = submitProposalRepository.getById(proposal.getId());
		UnderwritingResult underWritingResult = submitProposal.getUnderwritingResult();
		if (null != underWritingResult) {
			resultMap.put("underwrittingJson", underWritingResult.getData().getUnderwritingJson());
		}

		InsuranceResult insuranceResult = insuranceResultRepository.findByOrderId(orderId);
		if (insuranceResult != null) {
			InsuranceResultData insuranceResultData = insuranceResult.getData();
			if (insuranceResultData != null) {
				Map<String, Object> irDataMap = new HashMap<>();
				irDataMap.put("offerId", insuranceResultData.getOrderId());
				irDataMap.put("state", insuranceResultData.getState());
				irDataMap.put("ciPolicyNo", insuranceResultData.getCiPolicyNo());
				irDataMap.put("biPolicyNo", insuranceResultData.getBiPolicyNo());
				resultMap.put("insuranceResult", irDataMap);
			}

		}

		resultList.add(resultMap);
		// }
		return new ResponseBody(resultList);

	}

}
