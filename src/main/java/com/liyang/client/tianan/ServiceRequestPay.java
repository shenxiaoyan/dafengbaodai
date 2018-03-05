package com.liyang.client.tianan;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.liyang.client.BaseService;
import com.liyang.client.IClient;
import com.liyang.client.IMessage;
import com.liyang.client.IResponseSupplier;
import com.liyang.client.IResult;
import com.liyang.client.IService;
import com.liyang.client.IServiceObserve;
import com.liyang.client.ResponseSupplierPostJson;

/**
 * 请求支付
 * 
 * @author jacksunny
 *
 */
public class ServiceRequestPay extends BaseService implements IService {
	/**
	 * 请求支付
	 * 
	 * @param client
	 * @param serviceObserve
	 */
	public ServiceRequestPay(IClient client, IServiceObserve serviceObserve, IResponseSupplier responseSupplier,
			boolean skipAutoValidate) {
		super(client, serviceObserve, responseSupplier, skipAutoValidate);
		setPartUrl("tianan_cpf/common/pay/applyPay.do");
	}

	public ServiceRequestPay(IClient client, IServiceObserve serviceObserve, IResponseSupplier responseSupplier) {
		this(client, serviceObserve, responseSupplier, false);
	}

	public ServiceRequestPay(IClient client, IServiceObserve serviceObserve) {
		this(client, serviceObserve, new ResponseSupplierPostJson());
	}

	/**
	 * 请求支付
	 * 
	 * @param client
	 */
	public ServiceRequestPay(IClient client) {
		this(client, null, new ResponseSupplierPostJson());
	}

	@Override
	public IResult parseResult(Object response, IMessage generalMessage) {
		ResultRequestPay result = new ResultRequestPay();
		String responseText = (String) response;
		if (StringUtils.isEmpty(responseText)) {
			return result;
		}
		result = JSON.parseObject(responseText, new TypeReference<ResultRequestPay>() {
		});

		return result;
	}

	@Override
	public Object parseParameters(IMessage generalMessage) {
		String jsonString = "";
		if (null == generalMessage) {
			return jsonString;
		}
		MessageRequestPay message = (MessageRequestPay) generalMessage;
		jsonString = JSON.toJSONString(message);
		System.out.println(jsonString);
		return jsonString;
	}

	@Override
	public Object parseHeaders(IMessage message) {
		return null;
	}

}
