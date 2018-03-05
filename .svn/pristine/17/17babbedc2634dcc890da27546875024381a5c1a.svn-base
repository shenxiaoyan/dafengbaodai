package com.liyang.client.xiaoma.mock;

import com.liyang.client.BaseResponseSupplier;
import com.liyang.client.IMessage;
import com.liyang.client.IResponseSupplier;
import com.liyang.client.IService;

/**
 * @author Administrator
 *
 */
public class MockFailResponseSupplierQueryPayState extends BaseResponseSupplier implements IResponseSupplier  {
public MockFailResponseSupplierQueryPayState(IService service) {
super(service);
}
public MockFailResponseSupplierQueryPayState() {
}
@Override
public Object getResponse(IMessage generalMessage) throws Exception {
	String response="{\"errorMsg\":{\"code\":\"fail\",\"message\":\"操作失败\"},\"data\":false,\"time\":null,\"successful\":false}";
	return response;
}
}
