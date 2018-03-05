package com.liyang.client.xiaoma.mock;

import com.liyang.client.BaseResponseSupplier;
import com.liyang.client.IMessage;

/**
 * @author Administrator
 *
 */
public class MockSuccessResponseSupplierQueryLatesPolicy extends BaseResponseSupplier {

	private String response = "{\"errorMsg\":{\"code\":\"success\",\"message\":\"操作成功\"},\"data\":{\"biInfo\":{\"policyNo\":\"PDAA201632090000014156\",\"licenseNumber\":\"苏JUA230\",\"companyId\":2,\"companyName\":\"人保保险\",\"insuranceBeginTime\":\"2016-02-1900:00:00\",\"insuranceEndTime\":\"2017-02-1900:00:00\",\"insuranceJson\":[{\"insuranceId\":1,\"insuranceName\":\"车辆损失险\",\"type\":2,\"isToubao\":\"1\",\"compensation\":false,\"price\":1,\"options\":\"\",\"englishName\":\"车损\",\"state\":1,\"retCode\":0,\"remark\":\"\",\"amountStr\":\"投保\",\"isSupported\":true,\"weixinSource\":\"xiaoma\"},{\"insuranceId\":2,\"insuranceName\":\"第三者责任险\",\"type\":3,\"isToubao\":\"1\",\"compensation\":false,\"price\":1000000,\"options\":\"50000,100000,200000,300000,500000,1000000\",\"englishName\":\"三者\",\"state\":1,\"retCode\":0,\"remark\":\"\",\"amountStr\":\"100万\",\"isSupported\":true,\"weixinSource\":\"xiaoma\"}]},\"ciInfo\":{\"policyNo\":\"PDAA201632090000014156\",\"licenseNumber\":\"苏JUA230\",\"companyId\":2,\"companyName\":\"人保保险\",\"insuranceBeginTime\":\"2016-02-1900:00:00\",\"insuranceEndTime\":\"2017-02-1900:00:00\"}},\"time\":null,\"successful\":true}";

	@Override
	public Object getResponse(IMessage generalMessage) throws Exception {
		return response;
	}

}
