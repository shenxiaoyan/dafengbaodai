package com.liyang.client.tianan.mock;

import com.liyang.client.BaseResponseSupplier;
import com.liyang.client.IMessage;
import com.liyang.client.IResponseSupplier;

/**
 * @author Administrator
 *
 */
public class MockFailResponseSupplierQueryCarModel extends BaseResponseSupplier implements IResponseSupplier {
	
	private String response = "{  \"resultDTO\": {  \"resultCode\" : \"B00Y0002\",  \"resultMess\" : \"渠道接口管理平台提示:传递的cityCode330100没有找到相应的销售业务员信息\"}}";

	@Override
	public Object getResponse(IMessage generalMessage) throws Exception {
		return response;
	}


}
