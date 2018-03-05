package com.liyang.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.liyang.client.IClient;
import com.liyang.client.IServiceObserve;
import com.liyang.client.strategy.SecurityInfoXiaoma;
import com.liyang.client.xiaoma.ClientXiaoma;
import com.liyang.client.xiaoma.MessageEnquiryCarModel;
import com.liyang.client.xiaoma.ResultEnquiryCarModel;
import com.liyang.client.xiaoma.ServiceEnquiryCarModel;
import com.liyang.client.xiaoma.ServiceObserveDbPersistUpdateOfferResult;
import com.liyang.domain.createenquiry.CreateEnquiryStateRepository;
import com.liyang.domain.offerresult.OfferResultRepository;
import com.liyang.helper.ResponseBody;

/**
 * 修改车型，重新报价
 * TODO 确认小马报价推送offerId是否变化
 * @author huanghengkun
 * @create 2017年12月17日
 */
@RestController
@RequestMapping("/dafeng")
public class ModifyEnquiryCarModelController {

	@Value("${xmcxapi.apikey}")
	private String xmcxApiKey;

	@Autowired
	private OfferResultRepository offerResultRepository;

	@Autowired
	private CreateEnquiryStateRepository createEnquiryStateRepository;
	
//	@Autowired
//	private CreateEnquiryRepository createEnquiryRepository;

	@RequestMapping("/modifyEnquiryCarModel")
	public ResponseBody modifyEnquiryCarModel(@RequestBody Map<String, Object> modenqMap) throws Exception {
		MessageEnquiryCarModel message = new MessageEnquiryCarModel(modenqMap, offerResultRepository);
		System.out.println("【APP端传入修改车型重新报价参数】：" + JSON.toJSONString(modenqMap));
		IClient client = new ClientXiaoma();
		IServiceObserve serviceObserve = new ServiceObserveDbPersistUpdateOfferResult(offerResultRepository,
				createEnquiryStateRepository);
		SecurityInfoXiaoma securityInfo = new SecurityInfoXiaoma(xmcxApiKey);
		ServiceEnquiryCarModel service = new ServiceEnquiryCarModel(securityInfo, client, serviceObserve);
		ResultEnquiryCarModel result = null;
		result = (ResultEnquiryCarModel) service.callService(message);
		String modEnqCarModres = "";
		if (null != (String) result.getRawResponse()) {
			modEnqCarModres = (String) result.getRawResponse();
		}
		return new ResponseBody(modEnqCarModres);
		/**
		 * Original code 5 reconstruct 201711270950
		 */
		//// map转换成指定格式map
		//// Map<String, String> maps = new HashMap<String, String>();
		//// maps.put("multiEnquiryId", (String)
		//// modenqMap.get("multiEnquiryId"));
		//// maps.put("offerId", (String) modenqMap.get("offerId"));
		//// maps.put("modelParam", JS
		//// ONObject.fromObject(modenqMap.get("modelParam")).toString());
		//
		// String modEnqCarModres = HttpUtil.postBody(
		// "http://182.92.24.162:8088/xmcxapi/webService/enquiry/modifyEnquiryCarModel?api_key="+xmcx_apiKey+"",
		// JSONObject.fromObject(modenqMap).toString());
		// String offerId = (String)modenqMap.get("offerId");
		//
		//// String offerId = "200-20170911173302-7226c7";
		// OfferResult offerResult = null ;
		// if(null != offerId){
		// offerResult =
		//// offerResultRepository.findOfferResultByOfferId(offerId);
		// }
		// if(null != offerResult){
		// CreateEnquiryState state =
		//// createEnquiryStateRepository.findByStateCode("INENQUIRY");
		//// CreateEnquiry createEnquiry = offerResult.getCreateEnquiry();
		//// createEnquiry.setCreatedAt(new Date());
		//// createEnquiry.setState(state);
		//// createEnquiryRepository.save(createEnquiry);
		// offerResult.getCreateEnquiry().setState(state);
		// offerResult.getCreateEnquiry().setCreatedAt(new Date());
		// offerResult.setSuccessful(null);
		// offerResult.getData().getResult().setErrorMsg(null);
		// offerResult.getData().getResult().setOfferId(null);
		// offerResultRepository.save(offerResult);
		// }
		// return modEnqCarModres;
	}
}
