package com.liyang.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 重新报价接口
 * TODO 与确认报价撤销成功后，重新报价的offerId是否变化？？？		------by Djh
 * @author Administrator
 */
@RestController
@RequestMapping("/dafeng")
public class RevocationQuotationController {

	@Value("${xmcxapi.apikey}")
	private String xmcxApiKey ;
	
//	@RequestMapping(value = "/revocationQuotation", method = RequestMethod.POST)
//	public ResponseBody revocationQuotation(@RequestBody Map<String, String> revQuoMap) throws Exception {
//		String revQuoRes = HttpUtil.postForm(
//				"http://182.92.24.162:8088/xmcxapi/webService/enquiry/revocationQuotation?api_key="+xmcx_apiKey+"",
//				revQuoMap);
//		// 将数据封装成指定格式返回
//		Map<String, Object> handerToMap = CommonUtil.handerToMap(revQuoRes);
//		ResponseBody responseBody = new ResponseBody();
//		if ((Boolean) handerToMap.get("successful")) {
//			responseBody.setData(JSONObject.fromObject(handerToMap.get("data")));
//			responseBody.setErrorCode(0);
//			responseBody.setErrorInfo("OK");
//		} else {
//			responseBody.setErrorCode(100);
//			responseBody.setErrorInfo(JSONObject.fromObject(handerToMap.get("errorMsg")).getString("message"));
//			responseBody.setData(handerToMap.get("data"));
//		}
//		return responseBody;
//	}
}
