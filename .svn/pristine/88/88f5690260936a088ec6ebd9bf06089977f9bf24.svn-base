package com.liyang.client.tianan.mock;

import com.liyang.client.IMockResponseFactory;
import com.liyang.client.IResponseSupplier;

/**
 * @author Administrator
 *
 */
public class MockResponseFactoryQueryCarModel implements IMockResponseFactory {

	@Override
	public IResponseSupplier getMockResponse(String mockType) {
		IResponseSupplier responseSupplier = null;
		switch (mockType) {
		case "test_success":
			responseSupplier = new MockSuccessResponseSupplierQueryCarModel();
			break;
		case "test_fail":
			responseSupplier = new MockFailResponseSupplierQueryCarModel();
			break;
		default:
			break;
		}
		return responseSupplier;
	}

}
