package com.liyang.client.tianan;

import java.util.HashMap;
import java.util.Map;

import com.liyang.client.BaseResultDto;
import com.liyang.client.BaseService;
import com.liyang.client.IClient;
import com.liyang.client.IMessage;
import com.liyang.client.IResponseSupplier;
import com.liyang.client.IResult;
import com.liyang.client.IService;
import com.liyang.client.IServiceObserve;
import com.liyang.client.ResponseSupplierPostJson;

/**
 * 获取签名接口
 * 
 * @author jacksunny
 *
 */
public class ServiceGetSign extends BaseService implements IService {
	public ServiceGetSign(IClient client, IServiceObserve serviceObserve, IResponseSupplier responseSupplier,
			boolean skipAutoValidate) {
		super(client, serviceObserve, responseSupplier, skipAutoValidate);
		setPartUrl("cpf/tianan_cpf/common/auth/getSign.mvc");
	}

	public ServiceGetSign(IClient client, IServiceObserve serviceObserve, IResponseSupplier responseSupplier) {
		this(client, serviceObserve, responseSupplier, false);
	}

	public ServiceGetSign(IClient client, IServiceObserve serviceObserve) {
		this(client, serviceObserve, new ResponseSupplierPostJson());
	}

	public ServiceGetSign(IClient client) {
		this(client, null, new ResponseSupplierPostJson());
	}
	
	@Override
	public IResult callService(IMessage generalMessage) {
		ResultGetSign result = new ResultGetSign();
		if (null == generalMessage) {
			return result;
		}
		String resultCode = "SUCCESS";
		String resultMess = "接口调用成功";
		BaseResultDto resultDTO = new BaseResultDto(resultCode, resultMess);
		result.setResultDTO(resultDTO);
		String sign = MessageGetSign.DEFAULT_SIGN_FOR_TEST;
		result.setSign(sign);
		return result;
	}

	public IResult callMockTestService(IMessage generalMessage) {
		ResultGetSign result = new ResultGetSign();
		if (null == generalMessage) {
			return result;
		}
		String resultCode = "SUCCESS";
		String resultMess = "接口调用成功";
		BaseResultDto resultDTO = new BaseResultDto(resultCode, resultMess);
		result.setResultDTO(resultDTO);
		String sign = MessageGetSign.DEFAULT_SIGN_FOR_TEST;
		result.setSign(sign);
		return result;
	}
	
	public IResult callMockDevService(IMessage generalMessage) {
		ResultGetSign result = new ResultGetSign();
		if (null == generalMessage) {
			return result;
		}
		String resultCode = "SUCCESS";
		String resultMess = "接口调用成功";
		BaseResultDto resultDTO = new BaseResultDto(resultCode, resultMess);
		result.setResultDTO(resultDTO);
		String sign = MessageGetSign.DEFAULT_SIGN_FOR_PROD;
		result.setSign(sign);
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.client.tianan.IService#parseResult(java.lang.Object) {
	 * "resultDTO": { "resultCode": "SUCCESS", "resultMess": "接口调用成功" }, "sign":
	 * "xxxxxxxxxxxxxx"}
	 */
	@Override
	public IResult parseResult(Object response, IMessage generalMessage) {
		ResultGetSign result = new ResultGetSign();

		// String responseText = (String) response;
		// if (StringUtils.isEmpty(responseText)) {
		// return result;
		// }
		// JsonParser parser = null;
		// JsonObject root = (JsonObject) parser.parse(responseText);
		// String resultCode =
		// root.get("resultDTO").getAsJsonObject().get("resultCode").getAsString();
		// String resultMess =
		// root.get("resultDTO").getAsJsonObject().get("resultMess").getAsString();
		// String sign = root.get("sign").getAsString();
		// result.setResultDTO(new BaseResultDto(resultCode, resultMess));
		// result.setSign(sign);
		result.setSign(MessageGetSign.DEFAULT_SIGN_FOR_TEST);
		String resultCode = "SUCCESS";
		String resultMess = "接口调用成功";
		BaseResultDto resultDto = new BaseResultDto(resultCode, resultMess);
		result.setResultDTO(resultDto);

		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.client.tianan.IService#parseParameters(com.liyang.client.
	 * tianan.IMessage) { "openID": "10001", "token": "可以是任意字符", "url":
	 * "http://www.95505.com"}
	 */
	@Override
	public Object parseParameters(IMessage generalMessage) {
		Map<String, Object> result = new HashMap<String, Object>();
		MessageGetSign message = (MessageGetSign) generalMessage;
		if (message == null) {
			return result;
		}
		result.put("openID", message.getOpenID());
		result.put("token", message.getToken());
		result.put("url", message.getUrl());

		return result;
	}

	@Override
	public Object parseHeaders(IMessage message) {
		return null;
	}

}
