package com.liyang.client.xiaoma.mock;

import com.liyang.client.BaseResponseSupplier;
import com.liyang.client.IMessage;

/**
 * @author Administrator
 *
 */
public class MockFailResponseSupplierCreateEnquiry extends BaseResponseSupplier{

	private String response = "{\"errorMsg\":{\"code\":\"success\",\"message\":\"操作成功\"},\"data\":{\"message\":\"错误\"},\"time\":null,\"successful\":true}";

	
	@Override
	public Object getResponse(IMessage generalMessage) throws Exception {
		return response;
	}

}
