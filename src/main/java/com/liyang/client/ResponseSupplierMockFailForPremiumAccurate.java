package com.liyang.client;

/**
 * 
 * @author Administrator
 *
 */
public class ResponseSupplierMockFailForPremiumAccurate extends BaseResponseSupplier{
	
	public ResponseSupplierMockFailForPremiumAccurate(IService service) {
		super(service);
		}
	
	public ResponseSupplierMockFailForPremiumAccurate() {
	}
	
	@Override
	public Object getResponse(IMessage generalMessage) throws Exception {
		String responseFail="\"resultDTO\":{\"resultCode\":\"BRC_0002\",\"resultMess\":\"渠道接口管理平台提示:转保校验码录入错误，请重新输入。\"}{\"errorMsg\":{\"code\":\"success\",\"message\":\"操作成功\"},\"data\":true,\"time\":null,\"successful\":true}";
		return responseFail;
	}
	
	protected Object postJson(IMessage generalMessage) throws Exception {
		String url = service.getUrl();
		Object params = service.parseParameters(generalMessage);
		Object headers = service.parseHeaders(generalMessage);
		Object response = null;
		response = service.getClient().postJson(url, (String) params, headers);
		return response;
	}

}
