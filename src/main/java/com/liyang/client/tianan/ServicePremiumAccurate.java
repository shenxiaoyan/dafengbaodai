package com.liyang.client.tianan;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.liyang.client.BaseService;
import com.liyang.client.IClient;
import com.liyang.client.IMessage;
import com.liyang.client.IResponseSupplier;
import com.liyang.client.IResult;
import com.liyang.client.IService;
import com.liyang.client.IServiceObserve;
import com.liyang.client.ResponseSupplierPostJson;

/**
 * 精确报价
 * 
 * @author jacksunny
 *
 */
public class ServicePremiumAccurate extends BaseService implements IService {
	/**
	 * 精确报价
	 * 
	 * @param client
	 * @param serviceObserve
	 */
	public ServicePremiumAccurate(IClient client, IServiceObserve serviceObserve, IResponseSupplier responseSupplier,
			boolean skipAutoValidate) {
		super(client, serviceObserve, responseSupplier);
		setPartUrl("tianan_cpf/access/car/premiumAccurate.mvc");
	}

	public ServicePremiumAccurate(IClient client, IServiceObserve serviceObserve, IResponseSupplier responseSupplier) {
		this(client, serviceObserve, responseSupplier, false);
	}

	public ServicePremiumAccurate(IClient client, IServiceObserve serviceObserve) {
		this(client, serviceObserve, new ResponseSupplierPostJson());
	}

	/**
	 * 精确报价
	 * 
	 * @param client
	 */
	public ServicePremiumAccurate(IClient client) {
		this(client, null, new ResponseSupplierPostJson());
	}

	@Override
	public IResult parseResult(Object response, IMessage generalMessage) {
		ResultPremiumAccurate result = new ResultPremiumAccurate();
		String responseText = (String) response;
		if (StringUtils.isEmpty(responseText)) {
			return result;
		}
		result = JSON.parseObject(responseText, new com.alibaba.fastjson.TypeReference<ResultPremiumAccurate>() {

		});
		return result;
	}

	@Override
	public Object parseParameters(IMessage generalMessage) {
		String jsonString = "";
		if (generalMessage == null) {
			return jsonString;
		}
		jsonString = JSON.toJSONString(generalMessage);
		return jsonString;
	}

	@Override
	public Object parseHeaders(IMessage message) {
		return null;
	}

}
