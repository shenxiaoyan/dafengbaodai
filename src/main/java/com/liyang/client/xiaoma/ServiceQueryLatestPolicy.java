package com.liyang.client.xiaoma;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyang.client.BaseService;
import com.liyang.client.IClient;
import com.liyang.client.IMessage;
import com.liyang.client.IResponseSupplier;
import com.liyang.client.IResult;
import com.liyang.client.IService;
import com.liyang.client.IServiceObserve;
import com.liyang.client.ResponseSupplierPostForm;
import com.liyang.client.strategy.SecurityInfoXiaoma;
import com.liyang.domain.querylatestpolicy.QueryLatestPolicy;
import com.liyang.domain.querylatestpolicy.QueryLatestPolicyResult;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.util.FailReturnObject;
import com.liyang.util.TransUtils;

import net.sf.json.JSONObject;

/**
 * 访问续保接口
 * 
 * @author jacksunny
 *
 */
public class ServiceQueryLatestPolicy extends BaseService implements IService {

	// public ServiceQueryLatestPolicy(String postToXiaoMaUrl, IClient client,
	// IServiceObserve serviceObserve,
	// IResponseSupplier responseSupplier, boolean skipAutoValidate) {
	// super(client, serviceObserve, responseSupplier);
	// setPartUrl(postToXiaoMaUrl);
	// }
	//
	// public ServiceQueryLatestPolicy(String postToXiaoMaUrl, IClient client,
	// IServiceObserve serviceObserve,
	// IResponseSupplier responseSupplier) {
	// this(postToXiaoMaUrl, client, serviceObserve, responseSupplier, false);
	// }
	//
	// public ServiceQueryLatestPolicy(String postToXiaoMaUrl, IClient client,
	// IServiceObserve serviceObserve) {
	// this(postToXiaoMaUrl, client, serviceObserve, new
	// ResponseSupplierPostForm());
	// }

	public ServiceQueryLatestPolicy(SecurityInfoXiaoma securityInfo, IClient client, IServiceObserve serviceObserve,
			IResponseSupplier responseSupplier, boolean skipAutoValidate) {
		super(client, serviceObserve, responseSupplier);
		setPartUrl("xmcxapi/webService/enquiry/queryLatestPolicy?api_key=" + securityInfo.getApiKey());
	}

	public ServiceQueryLatestPolicy(SecurityInfoXiaoma securityInfo, IClient client, IServiceObserve serviceObserve,
			IResponseSupplier responseSupplier) {
		this(securityInfo, client, serviceObserve, responseSupplier, false);
	}

	public ServiceQueryLatestPolicy(SecurityInfoXiaoma securityInfo, IClient client, IServiceObserve serviceObserve) {
		this(securityInfo, client, serviceObserve, new ResponseSupplierPostForm());
	}

	// @Override
	// public Object detailCallService(IMessage generalMessage) throws Exception
	// {
	// Object result = null;
	// MessageQueryLatestPolicy message = (MessageQueryLatestPolicy)
	// generalMessage;
	// // 将数据post到小马接口,获取返回结果
	// // String queLatPolResult = HttpUtil.postForm(getUrl(),
	// // message.getQueLatPolMap());
	// String queLatPolResult = (String) client.postData(getUrl(),
	// message.getQueLatPolMap());
	// // 测试用
	// // queLatPolResult =
	// //
	// "{\"errorMsg\":{\"code\":\"success\",\"message\":\"操作成功\"},\"data\":{\"biInfo\":{\"policyNo\":\"PDAA201632090000014156\",\"licenseNumber\":\"苏JUA230\",\"companyId\":2,\"companyName\":\"人保保险\",\"insuranceBeginTime\":\"2016-02-1900:00:00\",\"insuranceEndTime\":\"2017-02-1900:00:00\",\"insuranceJson\":[{\"insuranceId\":1,\"insuranceName\":\"车辆损失险\",\"type\":2,\"isToubao\":\"1\",\"compensation\":false,\"price\":1,\"options\":\"\",\"englishName\":\"车损\",\"state\":1,\"retCode\":0,\"remark\":\"\",\"amountStr\":\"投保\",\"isSupported\":true,\"weixinSource\":\"xiaoma\"},{\"insuranceId\":2,\"insuranceName\":\"第三者责任险\",\"type\":3,\"isToubao\":\"1\",\"compensation\":false,\"price\":1000000,\"options\":\"50000,100000,200000,300000,500000,1000000\",\"englishName\":\"三者\",\"state\":1,\"retCode\":0,\"remark\":\"\",\"amountStr\":\"100万\",\"isSupported\":true,\"weixinSource\":\"xiaoma\"}]},\"ciInfo\":{\"policyNo\":\"PDAA201632090000014156\",\"licenseNumber\":\"苏JUA230\",\"companyId\":2,\"companyName\":\"人保保险\",\"insuranceBeginTime\":\"2016-02-1900:00:00\",\"insuranceEndTime\":\"2017-02-1900:00:00\"}},\"time\":null,\"successful\":true}";
	// System.out.println("queLatPolResult:" + queLatPolResult);
	// result = queLatPolResult;
	// return result;
	// }

	@Override
	public IResult parseResult(Object response, IMessage generalMessage) throws Exception {
		ResultQueryLatestPolicy result = new ResultQueryLatestPolicy();
		MessageQueryLatestPolicy message = (MessageQueryLatestPolicy) generalMessage;
		String responseText = (String) response;
		if (null == responseText) {
			throw new FailReturnObject(ExceptionResultEnum.QUERYLATPOL_RETURN_DATA_ERROR);
		}
//		System.out.println("queLatPolResult:" + responseText);
		// 将请求数据转换为对象保存
		QueryLatestPolicy queryLatestPolicy = TransUtils.mapTransStringObject(message.getQueLatPolMap(),
				QueryLatestPolicy.class);

		// 返回结果处理
		ObjectMapper objectMapper = new ObjectMapper();

		Map<String, Object> originalResponseDataMap = objectMapper.readValue(responseText, Map.class);

		JSONObject dataJsonObj = JSONObject.fromObject(originalResponseDataMap.get("data"));
		originalResponseDataMap.put("data", dataJsonObj);

		QueryLatestPolicyResult queryLatestPolicyResult = TransUtils.mapTransObject(originalResponseDataMap,
				QueryLatestPolicyResult.class);

		if (null == queryLatestPolicyResult || null == queryLatestPolicyResult.getErrorMsg()
				|| !("success".equalsIgnoreCase(queryLatestPolicyResult.getErrorMsg().getCode()))) {
			throw new FailReturnObject(ExceptionResultEnum.QUERYLATPOL_RETURN_DATA_ERROR);
		}

		// aop already check, for mobile
		// String applicationId = request.getHeader("applicationId");
		// Platform
		// platform=platformRepository.findByApplicationId(applicationId);
		// 关联平台信息
		queryLatestPolicy.setPlatform(message.getPlatform()); 
		queryLatestPolicy.setQueryLatestPolicyResult(queryLatestPolicyResult);
		//TODO 确认需要重复放置两次queryLatestPolicyResult？？？  --by Djh
		result.setQueryLatestPolicy(queryLatestPolicy);
		result.setQueryLatestPolicyResult(queryLatestPolicyResult);
		return result;
	}

	@Override
	public Object parseParameters(IMessage message) throws Exception {
		return ((MessageQueryLatestPolicy) message).getQueLatPolMap();
	}

	@Override
	public Object parseHeaders(IMessage message) throws Exception {
		return null;
	}

}
