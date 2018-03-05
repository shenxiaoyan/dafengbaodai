package com.liyang.client.xiaoma.mock;

import com.liyang.client.IMockResponseFactory;
import com.liyang.client.IResponseSupplier;

/**
 * @author Administrator
 *
 */
public class MockResponseFactoryConfirmUnderWriting implements IMockResponseFactory{

	@Override
	public IResponseSupplier getMockResponse(String mockType) {
		IResponseSupplier responseSupplier=null;
		switch (mockType) {
		case "test_success":
			responseSupplier=new MockSuccessResponseSupplierConfirmUnderWriting();
			break;
		case "test_fail":
			responseSupplier=new MockFailResponseSupplierConfirmUnderWriting();
			break;
		default:
			break;
		}
		return responseSupplier;
	}

}
