package com.liyang.client.xiaoma;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyang.client.BaseService;
import com.liyang.client.IClient;
import com.liyang.client.IMessage;
import com.liyang.client.IResponseSupplier;
import com.liyang.client.IResult;
import com.liyang.client.IService;
import com.liyang.client.IServiceObserve;
import com.liyang.client.ResponseSupplierPostForm;
import com.liyang.client.strategy.ISecurityInfo;
import com.liyang.client.strategy.SecurityInfoXiaoma;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.util.FailReturnObject;
import com.liyang.util.HttpUtil;

/**
 * 请求核保结果接口
 * 
 * @author jacksunny
 *
 */
public class ServiceConfirmUnderWriting extends BaseService implements IService {

	// public ServiceConfirmUnderWriting(String xiaomaUrl, IClient client,
	// IServiceObserve serviceObserve,
	// IResponseSupplier responseSupplier) {
	// this(xiaomaUrl, client, serviceObserve, responseSupplier, false);
	// }

	// public ServiceConfirmUnderWriting(String xiaomaUrl, IClient client,
	// IServiceObserve serviceObserve) {
	// this(xiaomaUrl, client, serviceObserve, new ResponseSupplierPostForm());
	// }

	public ServiceConfirmUnderWriting(ISecurityInfo securityInfo, IClient client, IServiceObserve serviceObserve,
			IResponseSupplier responseSupplier) {
		super(client, serviceObserve, responseSupplier, false);
		setPartUrl("xmcxapi/webService/enquiry/confirmChengbao?api_key="
				+ ((SecurityInfoXiaoma) securityInfo).getApiKey());
	}

	public ServiceConfirmUnderWriting(ISecurityInfo securityInfo, IClient client, IServiceObserve serviceObserve) {
		this(securityInfo, client, serviceObserve, new ResponseSupplierPostForm());
	}

	public ServiceConfirmUnderWriting(String xiaomaUrl, IClient client, IServiceObserve serviceObserve,
			IResponseSupplier responseSupplier, boolean skipAutoValidate) {
		super(client, serviceObserve, responseSupplier, skipAutoValidate);
		setPartUrl(xiaomaUrl);
	}

	// @Override
	// public Object detailCallService(IMessage generalMessage) throws Exception
	// {
	// MessageConfirmUnderWriting message = (MessageConfirmUnderWriting)
	// generalMessage;
	// // String responseResult = HttpUtil.postForm(getUrl(),
	// // message.getReqMap());
	// String responseResult = (String) client.postData(getUrl(),
	// message.getReqMap());
	// // 测试用,成功响应
	// // responseResult =
	// //
	// "{\"errorMsg\":{\"code\":\"success\",\"message\":\"操作成功\"},\"data\":true,\"successful\":true}";
	// // 测试用，失败响应
	// // responseResult =
	// //
	// "{\"errorMsg\":{\"code\":\"success\",\"message\":\"操作成功\"},\"data\":false,\"successful\":true}";
	// return responseResult;
	// }

	@Override
	public IResult parseResult(Object response, IMessage generalMessage) throws Exception {
		ResultConfirmUnderWriting result = new ResultConfirmUnderWriting();
		MessageConfirmUnderWriting message = (MessageConfirmUnderWriting) generalMessage;
		String responseResult = (String) response;
		parseConfirmUnderWritingResult(message, responseResult);
		return result;
	}

	@Override
	public Object parseParameters(IMessage message) throws Exception {
		MessageConfirmUnderWriting msg = (MessageConfirmUnderWriting) message;
		if (msg != null) {
			return msg.getReqMap();
		}
		return null;
	}

	@Override
	public Object parseHeaders(IMessage message) throws Exception {
		return null;
	}

	private void parseConfirmUnderWritingResult(MessageConfirmUnderWriting message, String responseResult)
			throws IOException, JsonParseException, JsonMappingException {
		if (responseResult == null) {
			throw new FailReturnObject(ExceptionResultEnum.UNDERWRITING_FAIL_ASKINTERFACE_ERROR);
		}

		// {"errorMsg":{"code":"success","message":"操作成功"},"data":true,"time":null,"successful":true}
		ObjectMapper objectMapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, Object> originalResponseDataMap = objectMapper.readValue(responseResult, Map.class);
		Object errorMsg;
		if (null == originalResponseDataMap || null == (errorMsg = originalResponseDataMap.get("errorMsg"))
				|| !(errorMsg.toString().contains("success"))) {
			throw new FailReturnObject(ExceptionResultEnum.UNDERWRITING_FAIL_ASKINTERFACE_ERROR);
		}
		message.getLogger().info("请求承保结果直接返回 :" + responseResult);
	}

}
