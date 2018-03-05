package com.liyang.client.xiaoma.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyang.client.BaseResponseSupplier;
import com.liyang.client.IMessage;
import com.liyang.client.IResponseSupplier;
import com.liyang.client.IService;

/**
 * @author Administrator
 *
 */
public class MockSuccessResponseSupplierQueryPayState extends BaseResponseSupplier implements IResponseSupplier{
	
	public MockSuccessResponseSupplierQueryPayState(IService service) {
		super(service);
	}
	
	public MockSuccessResponseSupplierQueryPayState() {
	}

	@Override
	public Object getResponse(IMessage generalMessage) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		String response ="{\"errorMsg\": {\"code\": \"success\",\"message\": \"操作成功\"},\"data\": {\"orderId\": \"109-20161129170215-70d41\",\"licenseNumber\": \"沪 A12345\",\"state\": 10,\"message\": \"支付成功\"},\"successful\": true}";
		
		return response;
}
}
