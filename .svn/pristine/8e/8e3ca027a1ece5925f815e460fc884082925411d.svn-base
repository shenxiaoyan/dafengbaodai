package com.liyang.client.xiaoma.mock;

import com.liyang.client.BaseResponseSupplier;
import com.liyang.client.IMessage;
import com.liyang.client.IResponseSupplier;
import com.liyang.client.IService;

/**
 * @author Administrator
 *
 */
public class MockFailResponseSupplierSubmitProposal extends BaseResponseSupplier implements IResponseSupplier {
public MockFailResponseSupplierSubmitProposal(IService service) {
super(service);
}
public MockFailResponseSupplierSubmitProposal() {
}
	@Override
	public Object getResponse(IMessage generalMessage) throws Exception {
		String response="{\"errorMsg\":{\"code\":\"success\",\"message\":\"操作失败\"},\"data\":false,\"time\":null,\"successful\":false}";
		return response;
	}

}
