package com.liyang.client.xiaoma;

import java.util.Map;

import org.springframework.util.StringUtils;

import com.liyang.client.BaseService;
import com.liyang.client.IClient;
import com.liyang.client.IMessage;
import com.liyang.client.IResponseSupplier;
import com.liyang.client.IResult;
import com.liyang.client.IService;
import com.liyang.client.IServiceObserve;
import com.liyang.client.ResponseSupplierPostForm;
import com.liyang.client.strategy.SecurityInfoXiaoma;
import com.liyang.util.FailReturnObject;

import net.sf.json.JSONArray;

/**
 * 修改车辆信息,修改车型
 * 
 * @author jacksunny
 *
 */
public class ServiceEnquiryCarModel extends BaseService implements IService {
	IServiceObserve serviceObserve;
	String xiaomaUrl;

	// public ServiceEnquiryCarModel(String xiaomaUrl, IClient client,
	// IServiceObserve serviceObserve,
	// IResponseSupplier responseSupplier, boolean skipAutoValidate) {
	// super(client, serviceObserve, responseSupplier, skipAutoValidate);
	// setPartUrl(xiaomaUrl);
	// }
	//
	// public ServiceEnquiryCarModel(String xiaomaUrl, IClient client,
	// IServiceObserve serviceObserve,
	// IResponseSupplier responseSupplier) {
	// this(xiaomaUrl, client, serviceObserve, responseSupplier, false);
	// }
	//
	// public ServiceEnquiryCarModel(String xiaomaUrl, IClient client,
	// IServiceObserve serviceObserve) {
	// this(xiaomaUrl, client, serviceObserve, new ResponseSupplierPostForm());
	// }

	public ServiceEnquiryCarModel(SecurityInfoXiaoma securityInfo, IClient client, IServiceObserve serviceObserve,
			IResponseSupplier responseSupplier, boolean skipAutoValidate) {
		super(client, serviceObserve, responseSupplier, skipAutoValidate);
		setPartUrl("xmcxapi/webService/enquiry/modifyEnquiryCarModel?api_key=" + securityInfo.getApiKey());
	}

	public ServiceEnquiryCarModel(SecurityInfoXiaoma securityInfo, IClient client, IServiceObserve serviceObserve,
			IResponseSupplier responseSupplier) {
		this(securityInfo, client, serviceObserve, responseSupplier, false);
	}

	public ServiceEnquiryCarModel(SecurityInfoXiaoma securityInfo, IClient client, IServiceObserve serviceObserve) {
		this(securityInfo, client, serviceObserve, new ResponseSupplierPostForm());
	}

	// @Override
	// public Object detailCallService(IMessage generalMessage) throws Exception
	// {
	// String result = null;
	// MessageEnquiryCarModel message = (MessageEnquiryCarModel) generalMessage;
	// // String modEnqCarModres = HttpUtil
	// //
	// .postBody("http://182.92.24.162:8088/xmcxapi/webService/enquiry/modifyEnquiryCarModel?api_key="
	// // + xmcx_apiKey + "",
	// // JSONObject.fromObject(message.getModenqMap()).toString());
	// String modEnqCarModres = (String) client.postJson(getUrl(),
	// JSONObject.fromObject(message.getModenqMap()).toString());
	// result = modEnqCarModres;
	//
	// return result;
	// }

	@Override
	public IResult parseResult(Object response, IMessage generalMessage) throws Exception {
		ResultEnquiryCarModel result = new ResultEnquiryCarModel();
		return result;
	}

	@Override
	public Object parseParameters(IMessage message) throws Exception {
		Map<String, Object> result = null;
		MessageEnquiryCarModel msg = (MessageEnquiryCarModel) message;
		if (null != msg && null != msg.getModenqMap()) {
			//小马此接口form提交，非json提交
//			result = JSONObject.fromObject(msg.getModenqMap()).toString();
			result = msg.getModenqMap();
			String modelParamJsonString = JSONArray.fromObject(result.get("modelParam")).toString();
			if (StringUtils.isEmpty(modelParamJsonString)) {
				throw new FailReturnObject(222,"修改车型重新报价参数--modelParam为空或转Json异常");
			}
			msg.getModenqMap().put("modelParam", modelParamJsonString);
		}
		return result;
	}

	@Override
	public Object parseHeaders(IMessage message) throws Exception {
		return null;
	}

}
