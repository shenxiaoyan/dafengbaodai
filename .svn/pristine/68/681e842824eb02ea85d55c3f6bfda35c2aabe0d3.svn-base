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
 * 上传影像资料
 * 
 * @author jacksunny
 *
 */
public class ServiceUploadAttach extends BaseService implements IService {
	/**
	 * 上传影像资料
	 * 
	 * @param client
	 * @param serviceObserve
	 */
	public ServiceUploadAttach(IClient client, IServiceObserve serviceObserve, IResponseSupplier responseSupplier,
			boolean skipAutoValidate) {
		super(client, serviceObserve, responseSupplier, skipAutoValidate);
		setPartUrl("tianan_cpf/access/car/thirdUploadAttach.mvc");
	}

	public ServiceUploadAttach(IClient client, IServiceObserve serviceObserve, IResponseSupplier responseSupplier) {
		this(client, serviceObserve, responseSupplier, false);
	}

	public ServiceUploadAttach(IClient client, IServiceObserve serviceObserve) {
		this(client, serviceObserve, new ResponseSupplierPostJson());
	}

	/**
	 * 上传影像资料
	 * 
	 * @param client
	 */
	public ServiceUploadAttach(IClient client) {
		this(client, null, new ResponseSupplierPostJson());
	}

	@Override
	public IResult parseResult(Object response, IMessage generalMessage) {
		ResultUploadAttach result = new ResultUploadAttach();
		String responseText = (String) response;
		if (StringUtils.isEmpty(responseText)) {
			return result;
		}
		result = JSON.parseObject(responseText, new TypeReference<ResultUploadAttach>() {
		});
		return result;
	}

	@Override
	public Object parseParameters(IMessage generalMessage) {
		String jsonString = "";
		if (null == generalMessage) {
			return jsonString;
		}
		MessageUploadAttach message = (MessageUploadAttach) generalMessage;
		jsonString = JSON.toJSONString(message);
		System.out.println(jsonString);
		return jsonString;
	}

	@Override
	public Object parseHeaders(IMessage message) {
		return null;
	}

}
