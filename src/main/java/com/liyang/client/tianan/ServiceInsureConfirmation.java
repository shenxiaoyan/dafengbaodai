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
 * 投保单保存
 * 
 * @author jacksunny
 *
 */
public class ServiceInsureConfirmation extends BaseService implements IService {

	/**
	 * 投保单保存
	 * 
	 * @param client
	 * @param serviceObserve
	 */
	public ServiceInsureConfirmation(IClient client, IServiceObserve serviceObserve, IResponseSupplier responseSupplier,
			boolean skipAutoValidate) {
		super(client, serviceObserve, responseSupplier, skipAutoValidate);
		setPartUrl("tianan_cpf/access/car/insureConfirmation.mvc");
	}

	public ServiceInsureConfirmation(IClient client, IServiceObserve serviceObserve,
			IResponseSupplier responseSupplier) {
		this(client, serviceObserve, responseSupplier, false);
	}

	public ServiceInsureConfirmation(IClient client, IServiceObserve serviceObserve) {
		this(client, serviceObserve, new ResponseSupplierPostJson());
	}

	/**
	 * 投保单保存
	 * 
	 * @param client
	 */
	public ServiceInsureConfirmation(IClient client) {
		this(client, null, new ResponseSupplierPostJson());
	}

	@Override
	public IResult parseResult(Object response, IMessage generalMessage) {
		ResultInsureConfirmation result = new ResultInsureConfirmation();
		String responseString = (String) response;
		if (StringUtils.isEmpty(responseString)) {
			return result;
		}
		result = JSON.parseObject(responseString, new TypeReference<ResultInsureConfirmation>() {
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
