package com.liyang.client.xiaoma.mock;

import com.liyang.client.BaseResponseSupplier;
import com.liyang.client.IMessage;
import com.liyang.client.IResponseSupplier;
import com.liyang.client.IService;

/**
 * @author Administrator
 *
 */
public class MockSuccessResponseSupplierSubmitProposal extends BaseResponseSupplier implements IResponseSupplier{
public MockSuccessResponseSupplierSubmitProposal(IService service) {
	super(service);
}
public MockSuccessResponseSupplierSubmitProposal() {
}
@Override
public Object getResponse(IMessage generalMessage) throws Exception {
	String response="{\"errorMsg\":{\"code\":\"success\",\"message\":\"操作成功\"},\"data\":true,\"time\":null,\"successful\":true}";
	return response;
}
}
