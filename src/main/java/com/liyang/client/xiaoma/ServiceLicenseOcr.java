package com.liyang.client.xiaoma;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liyang.client.BaseService;
import com.liyang.client.IClient;
import com.liyang.client.IMessage;
import com.liyang.client.IResponseSupplier;
import com.liyang.client.IResult;
import com.liyang.client.IService;
import com.liyang.client.IServiceObserve;
import com.liyang.client.ResponseSupplierPostFile;
import com.liyang.client.strategy.SecurityInfoXiaoma;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.util.FailReturnObject;

/**
 * 行驶证识别
 * 
 * @author jacksunny
 *
 */
public class ServiceLicenseOcr extends BaseService implements IService {

	// public ServiceLicenseOcr(String xiaomaURL, IClient client,
	// IServiceObserve serviceObserve,
	// IResponseSupplier responseSupplier, boolean skipAutoValidate) {
	// super(client, serviceObserve, responseSupplier);
	// setPartUrl(xiaomaURL);
	// }
	//
	// public ServiceLicenseOcr(String xiaomaURL, IClient client,
	// IServiceObserve serviceObserve,
	// IResponseSupplier responseSupplier) {
	// this(xiaomaURL, client, serviceObserve, responseSupplier, false);
	// }
	//
	// public ServiceLicenseOcr(String xiaomaURL, IClient client,
	// IServiceObserve serviceObserve) {
	// this(xiaomaURL, client, serviceObserve, new ResponseSupplierPostFile());
	// }

	public ServiceLicenseOcr(SecurityInfoXiaoma securityInfo, IClient client, IServiceObserve serviceObserve,
			IResponseSupplier responseSupplier, boolean skipAutoValidate) {
		super(client, serviceObserve, responseSupplier);
		setPartUrl("xmcxapi/webService/enquiry/licenseOcr?api_key=" + securityInfo.getApiKey());
	}

	public ServiceLicenseOcr(SecurityInfoXiaoma securityInfo, IClient client, IServiceObserve serviceObserve,
			IResponseSupplier responseSupplier) {
		this(securityInfo, client, serviceObserve, responseSupplier, false);
	}

	public ServiceLicenseOcr(SecurityInfoXiaoma securityInfo, IClient client, IServiceObserve serviceObserve) {
		this(securityInfo, client, serviceObserve, new ResponseSupplierPostFile());
	}

	// @Override
	// public Object detailCallService(IMessage generalMessage) throws Exception
	// {
	// MessageLicenseOcr message = (MessageLicenseOcr) generalMessage;
	// String response = "";
	// try {
	// // response = HttpUtil.postFile(message.getImgFile(), getUrl());
	// response = (String) client.postFile(getUrl(), message.getImgFile());
	// } catch (IOException e) {
	// e.printStackTrace();
	// message.getLogger().warn("小马行驶证识别接口异常");
	// throw new FailReturnObject(ExceptionResultEnum.LICENSE_INTFC_ERROR);
	// }
	// return response;
	// }

	@Override
	public IResult parseResult(Object response, IMessage generalMessage) throws Exception {
		ResultLicenseOcr result = new ResultLicenseOcr();
		System.out.println(response);
		JSONObject jsonRes = JSON.parseObject((String) response);
		if (jsonRes != null && jsonRes.getBoolean("successful") != null && jsonRes.getBoolean("successful")) {
			String jsonString = jsonRes.getJSONObject("data").toString();
			result.setDataString(jsonString);
		} else {
			throw new FailReturnObject(ExceptionResultEnum.LICENSE_IMG_ERROR);
		}
		return result;
	}

	@Override
	public Object parseParameters(IMessage message) throws Exception {
		return ((MessageLicenseOcr) message).getImgFile();
	}

	@Override
	public Object parseHeaders(IMessage message) throws Exception {
		return null;
	}

}
