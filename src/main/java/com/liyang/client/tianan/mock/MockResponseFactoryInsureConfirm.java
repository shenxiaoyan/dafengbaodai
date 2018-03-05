package com.liyang.client.tianan.mock;

import com.liyang.client.IMockResponseFactory;
import com.liyang.client.IResponseSupplier;

/**
 * @author duyiting
 *
 */
public class MockResponseFactoryInsureConfirm implements IMockResponseFactory{

	@Override
	public IResponseSupplier getMockResponse(String mockType) {
		IResponseSupplier responseSupplier=null;
		switch (mockType) {
		case "test_success":
			responseSupplier=new MockSuccessResponseSupplierInsureConfirmation();
			break;
		case "test_fail":
			responseSupplier=new MockFailResponseSupplierInsureConfirmation();
			break;
		default:
			break;
		}
		return responseSupplier;
	}

}
