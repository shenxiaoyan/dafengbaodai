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
 * 投保单列表查询
 * 
 * @author jacksunny
 *
 */
public class ServiceQueryProposal extends BaseService implements IService {
	/**
	 * 投保单列表查询
	 * 
	 * @param client
	 * @param serviceObserve
	 */
	public ServiceQueryProposal(IClient client, IServiceObserve serviceObserve, IResponseSupplier responseSupplier,
			boolean skipAutoValidate) {
		super(client, serviceObserve, responseSupplier, skipAutoValidate);
		setPartUrl("tianan_cpf/access/car/proposalQuery.mvc");
	}

	public ServiceQueryProposal(IClient client, IServiceObserve serviceObserve, IResponseSupplier responseSupplier) {
		this(client, serviceObserve, responseSupplier, false);
	}

	public ServiceQueryProposal(IClient client, IServiceObserve serviceObserve) {
		this(client, serviceObserve, new ResponseSupplierPostJson());
	}

	/**
	 * 投保单列表查询
	 * 
	 * @param client
	 */
	public ServiceQueryProposal(IClient client) {
		this(client, null, new ResponseSupplierPostJson());
	}

	@Override
	public IResult parseResult(Object response, IMessage generalMessage) {
		ResultQueryProposal result = new ResultQueryProposal();
		String responseText = (String) response;
		if (StringUtils.isEmpty(responseText)) {
			return result;
		}
		result = JSON.parseObject(responseText, new TypeReference<ResultQueryProposal>() {
		});
		return result;
	}

	@Override
	public Object parseParameters(IMessage generalMessage) {
		String jsonString = "";
		if (null == generalMessage) {
			return jsonString;
		}
		MessageQueryProposal message = (MessageQueryProposal) generalMessage;
		jsonString = JSON.toJSONString(message);
		System.out.println(jsonString);
		return jsonString;
	}

	@Override
	public Object parseHeaders(IMessage message) {
		return null;
	}

}
