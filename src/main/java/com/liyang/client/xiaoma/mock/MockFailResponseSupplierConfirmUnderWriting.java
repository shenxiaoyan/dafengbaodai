package com.liyang.client.xiaoma.mock;

import com.liyang.client.BaseResponseSupplier;
import com.liyang.client.IMessage;

/**
 * @author Administrator
 *
 */
public class MockFailResponseSupplierConfirmUnderWriting extends BaseResponseSupplier{

	private String response = "{\"data\":{\"data\":{\"orderId\":\"109-20170927131234-a9b5e\",\"licenseNumber\":\"浙AZS871\",\"state\":3,\"underwritingPriceCent\":0,\"underwritingJson\":\"{\\\"errorMsg\\\":\\\"商业险报价失败\\\"}\"},\"time\":0,\"errorMsg\":{\"code\":\"success\",\"message\":\"操作成功\"},\"successful\":true}}";

	
	@Override
	public Object getResponse(IMessage generalMessage) throws Exception {
		return response;
	}

}
