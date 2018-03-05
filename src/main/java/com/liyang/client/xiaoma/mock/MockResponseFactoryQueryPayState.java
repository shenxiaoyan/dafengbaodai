package com.liyang.client.xiaoma.mock;

import com.liyang.client.IMockResponseFactory;
import com.liyang.client.IResponseSupplier;

/**
 * @author Administrator
 *
 */
public class MockResponseFactoryQueryPayState implements IMockResponseFactory{

	@Override
	public IResponseSupplier getMockResponse(String mockType) {
		IResponseSupplier responseSupplier = null;
		switch(mockType){
		case "test_success":
			responseSupplier = new MockSuccessResponseSupplierQueryPayState();
			break;
		case "test_fail":
			responseSupplier = new MockFailResponseSupplierQueryPayState();
			break;
		default:
			break;
		}
		return responseSupplier;
	}
}
