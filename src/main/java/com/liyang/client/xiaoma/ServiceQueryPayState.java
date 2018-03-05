package com.liyang.client.xiaoma;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.liyang.client.strategy.SecurityInfoXiaoma;
import com.liyang.domain.querypayment.QueryPay;
import com.liyang.domain.querypayment.QueryPayRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.util.FailReturnObject;
import com.liyang.util.HttpUtil;

/**
 * 查询支付状态接口
 * 
 * @author jacksunny
 *
 */
public class ServiceQueryPayState extends BaseService implements IService {
	QueryPayRepository queryPayRepository;

	// public ServiceQueryPayState(String xiaomaUrl, IClient client,
	// IServiceObserve serviceObserve,
	// IResponseSupplier responseSupplier, QueryPayRepository
	// queryPayRepository, boolean skipAutoValidate) {
	// super(client, serviceObserve, responseSupplier);
	// setPartUrl(xiaomaUrl);
	// this.queryPayRepository = queryPayRepository;
	// }
	//
	// public ServiceQueryPayState(String xiaomaUrl, IClient client,
	// IServiceObserve serviceObserve,
	// IResponseSupplier responseSupplier, QueryPayRepository
	// queryPayRepository) {
	// this(xiaomaUrl, client, serviceObserve, responseSupplier,
	// queryPayRepository, false);
	// }
	//
	// public ServiceQueryPayState(String xiaomaUrl, IClient client,
	// IServiceObserve serviceObserve,
	// QueryPayRepository queryPayRepository) {
	// this(xiaomaUrl, client, serviceObserve, new ResponseSupplierPostForm(),
	// queryPayRepository);
	// }

	public ServiceQueryPayState(SecurityInfoXiaoma securityInfo, IClient client, IServiceObserve serviceObserve,
			IResponseSupplier responseSupplier, QueryPayRepository queryPayRepository, boolean skipAutoValidate) {
		super(client, serviceObserve, responseSupplier);
		setPartUrl("xmcxapi/webService/enquiry/confirmPay?api_key=" + securityInfo.getApiKey());
		this.queryPayRepository = queryPayRepository;
	}

	public ServiceQueryPayState(SecurityInfoXiaoma securityInfo, IClient client, IServiceObserve serviceObserve,
			IResponseSupplier responseSupplier, QueryPayRepository queryPayRepository) {
		this(securityInfo, client, serviceObserve, responseSupplier, queryPayRepository, false);
	}

	public ServiceQueryPayState(SecurityInfoXiaoma securityInfo, IClient client, IServiceObserve serviceObserve,
			QueryPayRepository queryPayRepository) {
		this(securityInfo, client, serviceObserve, new ResponseSupplierPostForm(), queryPayRepository);
	}

	// @Override
	// public Object detailCallService(IMessage generalMessage) throws Exception
	// {
	// Object result = null;
	// MessageQueryPayState message = (MessageQueryPayState) generalMessage;
	// // String response = HttpUtil.postForm(getUrl(),
	// // message.getQuePayStatMap());
	// String response = (String) client.postData(getUrl(),
	// message.getQuePayStatMap());
	// // 测试用
	// // response =
	// //
	// "{\"errorMsg\":{\"code\":\"success\",\"message\":\"操作成功\"},\"data\":true,\"successful\":true}";
	// result = response;
	// return result;
	// }

	@Override
	public IResult parseResult(Object response, IMessage generalMessage) throws Exception {
		ResultQueryPayState result = new ResultQueryPayState();
		MessageQueryPayState message = (MessageQueryPayState) generalMessage;
		String responseText = (String) response;
		parseQueryPayStateResult(message, responseText);
		result.setQueryPay(message.getQueryPay());
		return result;
	}

	@Override
	public Object parseParameters(IMessage message) throws Exception {
		MessageQueryPayState msg = (MessageQueryPayState) message;
		return msg.getQuePayStatMap();
	}

	@Override
	public Object parseHeaders(IMessage message) throws Exception {
		return null;
	}

	private void parseQueryPayStateResult(MessageQueryPayState message, String response)
			throws IOException, JsonParseException, JsonMappingException {
		if (response == null) {
			throw new FailReturnObject(ExceptionResultEnum.QUERYPAY_RETURN_DATA_ERROR);
		}
		// {"errorMsg":{"code":"success","message":"操作成功"},"data":true,"time":null,"successful":true}
		ObjectMapper objectMapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, Object> originalResponseDataMap = objectMapper.readValue(response, Map.class);
		Object errorMsg;
		if (null == originalResponseDataMap || null == (errorMsg = originalResponseDataMap.get("errorMsg"))
				|| !(errorMsg.toString().contains("success"))) {
			throw new FailReturnObject(ExceptionResultEnum.QUERYPAY_RETURN_DATA_ERROR);
		}
		message.getQueryPay().setOrderId(message.getOrderId());
		message.getQueryPay().setResponse(response);
		// 保证数据库里只有一条查询确认支付的数据
		List<QueryPay> queryPayList = queryPayRepository.findByOrderId(message.getOrderId());
		if (queryPayList != null && !(queryPayList.isEmpty())) {
			message.getQueryPay().setId(queryPayList.get(0).getId());
		}
	}

}
