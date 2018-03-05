package com.liyang.client.tianan.mock;

import com.liyang.client.BaseResponseSupplier;
import com.liyang.client.IMessage;
import com.liyang.client.IResponseSupplier;
import com.liyang.client.IService;

/**
 * @author nielijun
 *
 */
public class MockFailResponseSupplierQueryProposal extends BaseResponseSupplier implements IResponseSupplier {

	public MockFailResponseSupplierQueryProposal(IService service) {
		super(service);
	}
	
	public MockFailResponseSupplierQueryProposal() {
	}

	@Override
	public Object getResponse(IMessage generalMessage) throws Exception {
		String response ="{\"resultDTO\": {\"resultCode\" : \"B00Y0002\",  \"resultMess\" : \"渠道接口管理平台提示:传递的cityCode330100没有找到相应的销售业务员信息\"}}";
		return response;
	}

}
