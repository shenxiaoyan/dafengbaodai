package com.liyang.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
/*
 * 核保结果推送
 */
import org.springframework.web.bind.annotation.RestController;

import com.liyang.client.CreateEnquiryJsonParseFactory;
import com.liyang.client.ICreateEnquiryJsonParseAdapter;
import com.liyang.domain.createenquiry.CreateEnquiry;
import com.liyang.domain.insuranceresult.InsuranceResult;
import com.liyang.domain.insuranceresult.InsuranceResultRepository;
import com.liyang.domain.insurercompany.InsureCompanyRepository;
import com.liyang.domain.offerresult.OfferResult;
import com.liyang.domain.offerresult.OfferResultDataResult;
import com.liyang.domain.offerresult.OfferResultRepository;
import com.liyang.domain.submitproposal.SubmitProposal;
import com.liyang.domain.submitproposal.SubmitProposalRepository;
import com.liyang.domain.underwritingresult.UnderwritingResult;
import com.liyang.domain.underwritingresult.UnderwritingResultRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.service.UnderwritingResultService;
import com.liyang.util.CommonUtil;
import com.liyang.util.FailReturnObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 核保结果推送接口 数据格式:x-www-form-urlencode 数据data={数据}
 * @author huanghengkun
 * @create 2017年12月17日
 */
@RestController
@RequestMapping("/dafeng")
public class UnderwritingResultController {

	@Autowired
	UnderwritingResultService underwritingResultService;

	@Autowired
	OfferResultRepository offerResultRepository;

	@Autowired
	SubmitProposalRepository submitProposalRepository;

	@Autowired
	UnderwritingResultRepository underwritingResultRepository;

	@Autowired
	InsureCompanyRepository insureCompanyRepository;

	@Autowired
	InsuranceResultRepository insuranceResultRepository;

	private final static Logger logger = LoggerFactory.getLogger(UnderwritingResultController.class);

//	@Value("${restIP}")
//	private String ip;

	/**
	 * 核保结果返回接口(小马)
	 * @param undResStr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/underwritingResult", method = RequestMethod.POST)
//	@org.springframework.web.bind.annotation.ResponseBody
	public String underwritingResult(@RequestParam(value = "data") String undResStr) throws Exception {
		logger.info("【小马核保结果推送返回，original数据】：" + undResStr);
		String responseResult = underwritingResultService.saveUnderResultAndForward(CommonUtil.handerToMap(undResStr));
		logger.info("【核保结果返回小马数据】：" + responseResult);
		return responseResult;
	}

	/**
	 * 核保结果推送转发,封装为指定数据
	 */
	@RequestMapping(value = "/forwardUnderwritingResult", method = RequestMethod.GET)
	public ResponseBody forwardUnderwritingResult(@RequestParam("id") Integer id) {

		UnderwritingResult result = underwritingResultRepository.getOne(id);
		if (result != null) {
			return new ResponseBody(result.getData());
		} else {
			throw new FailReturnObject(ExceptionResultEnum.OFFERRES_DATA_FAIL_ERROR);
		}
	}

	
	/**
	 * 根据orderId,数据封装,给安卓跟ios,进行出单详情页面展示
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/underwritingResultHander", method = RequestMethod.GET)
	public ResponseBody underwritingResultHander(@RequestParam("orderId") String orderId) throws Exception {
		// System.out.println(orderId);
		// 封装数据的map
		Map<String, Object> handerMap = new HashMap<String, Object>();
		if (orderId == null) {
			throw new FailReturnObject(ExceptionResultEnum.UNDERWRITING_RESULT_ORDERID_ERROR);
			// throw new FailReturnObject(100, "请带上orderId进行访问");
		}

		// 报价结果数据
		OfferResult offerResult = offerResultRepository.findOfferResultByOfferId(orderId);
		if (offerResult == null) {
			throw new FailReturnObject(ExceptionResultEnum.UNDERWRITING_ORDER_PRICE_ERROR);
			// throw new FailReturnObject(100, "该orderId的订单没有对应的报价结果,请检查");
		} else {
			handerMap.put("orderId", orderId);
			handerMap.put("modelJson", offerResult.getData().getResult().getModelJson());
			handerMap.put("company", insureCompanyRepository
					.findByInsurerCompanyId(offerResult.getData().getResult().getInsuranceCompanyId()));
			Long ciBeginDate = offerResult.getData().getResult().getForceInsuranceStartTime();
			if (ciBeginDate != null) {
				// 交强险起保时间
				handerMap.put("ciBeginDate", ciBeginDate);
				// 交强险投保时间
				handerMap.put("ciToubaoData", offerResult.getCreateEnquiry().getCreatedAt().getTime() / 1000);
			}
			Long biBeginDate = offerResult.getData().getResult().getInsuranceStartTime();
			if (biBeginDate != null) {
				// 商业险起保时间
				handerMap.put("biBeginDate", biBeginDate);
				// 商业险投保时间
				handerMap.put("biToubaoData", offerResult.getCreateEnquiry().getCreatedAt().getTime() / 1000);
			}
			handerMap.put("ratioJson", offerResult.getData().getResult().getRatioJson());
			handerMap.put("sumPrice",
					offerResult.getData().getResult().getOfferDetail().getDouble("originalPrice")
							+ offerResult.getData().getResult().getOfferDetail().getJSONObject("forcePremium")
									.getDouble("quotesPrice")
							+ offerResult.getData().getResult().getOfferDetail().getJSONObject("taxPrice")
									.getDouble("quotesPrice"));
			handerMap.put("offerDetail", offerResult.getData().getResult().getOfferDetail());
			try {
				//获取不计免赔详情
				JSONArray jsonArray = offerResult.getData().getResult().getOfferDetail().getJSONArray("insurances");
				StringBuffer deductibleDetail = new StringBuffer();
				for (Object object : jsonArray) {
					JSONObject jsonObject = (JSONObject)object;
					if (jsonObject.getDouble("quotesPrice") != 0 && jsonObject.getBoolean("compensation")) {
						int insuranceId = jsonObject.getInt("insuranceId");
						if (insuranceId == 1 || insuranceId == 63) {
							deductibleDetail.append("车损").append("、");
						}else if(insuranceId == 2 || insuranceId == 68) {
							deductibleDetail.append("三者").append("、");
						}else if (insuranceId == 3 || insuranceId == 74) {
							deductibleDetail.append("抢盗").append("、");
						}else if (insuranceId == 4 || insuranceId == 73) {
							deductibleDetail.append("司机").append("、");
						}else if (insuranceId == 5 || insuranceId == 89) {
							deductibleDetail.append("乘客").append("、");
						}else if (insuranceId == 7 || insuranceId == 75) {
							deductibleDetail.append("划痕").append("、");
						}else if (insuranceId == 8 || insuranceId == 36) {
							deductibleDetail.append("自燃").append("、");
						}else if (insuranceId == 9 || insuranceId == 16) {
							deductibleDetail.append("涉水").append("、");
						}else {
							deductibleDetail.append("");
						}
					}
				}
				if (!StringUtils.isEmpty(deductibleDetail.toString())) {
					handerMap.put("deductibleDetail", deductibleDetail.substring(0, deductibleDetail.length()-1).toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
				handerMap.put("errMsg", "后台获取不计免赔错误");
			}
			
			// 根据offerResult能拿到CreateEnquiry对象,获取到创建询价参数
			CreateEnquiry createEnquiry = offerResult.getCreateEnquiry();
			if (createEnquiry == null) {
				throw new FailReturnObject(ExceptionResultEnum.UNDERWRITING_RESULT_NOPRICE_ERROR);
				// throw new FailReturnObject(100, "该报价结果下,没有对应的询价,请检查");
			} else {
				ICreateEnquiryJsonParseAdapter adapter = new CreateEnquiryJsonParseFactory(createEnquiry)
						.createAdapter();
				handerMap.put("ownerName", adapter.getOwnerName());
				handerMap.put("city", adapter.getCityName());
				// handerMap.put("ciBeginDate",createEnquiry.getCreateEnquiryParams().get("forceInsuranceStartTime"));
				// handerMap.put("biBeginDate",
				// createEnquiry.getCreateEnquiryParams().get("insuranceStartTime"));
				handerMap.put("licenseNumber", adapter.getLicenseNumber());
				handerMap.put("schemeName", adapter.getInsurancesListSchemeName());
			}
		}

		// 提交核保数据
		SubmitProposal submitProposals = submitProposalRepository.findByOrderId(orderId);
		if (submitProposals == null) {
			throw new FailReturnObject(ExceptionResultEnum.UNDERWRITING_DATA_MIS_ERROR);
			// throw new FailReturnObject(100, "该orderId没有找到相应的提交核保数据");
		} else {
			SubmitProposal submitProposal = submitProposals;
			handerMap.put("createdAt", submitProposal.getCreateTime().getTime());
			handerMap.put("status", submitProposal.getState().getLabel());
			handerMap.put("stateCode", submitProposal.getState().getLabel());
			// 投保人
			handerMap.put("insuranceName", submitProposal.getParams().getCustomerName());
			// 被保人
			handerMap.put("beInsuranceName", submitProposal.getParams().getInsuredName());
//			handerMap.put("contactName", submitProposal.getParams().getContactName());
//			handerMap.put("contactPhone", submitProposal.getParams().getContactPhone());
//			handerMap.put("receiveAddress", submitProposal.getParams().getContactAddress().getContactAddressDetail());
			//换为返回大蜂配送信息
			handerMap.put("contactName", submitProposal.getParams().getDafengContactName());
			handerMap.put("contactPhone", submitProposal.getParams().getDafengContactPhone());
			handerMap.put("receiveAddress", submitProposal.getParams().getDafengAddress());
			handerMap.put("params", submitProposal.getParams());
			
			Date addExpTime = submitProposal.getParams().getAddressExpireTime();
			boolean addCanModify = true;
	    	if (addExpTime != null && addExpTime.compareTo(new Date()) < 0) {
	    		addCanModify = false;
	    	}
	    	if (null != submitProposal.getParams().getAddressMofied() && submitProposal.getParams().getAddressMofied()) {
	    		addCanModify = false;
			}
			handerMap.put("addCanModify", addCanModify);
		}

		// 核保结果数据,TODO  直接根据orderId查找，会存在多个，为何？？？ -------By Djh
//		UnderwritingResult underwritingResult = underwritingResultRepository.findUnderwritingResultByOrderId(orderId);
		UnderwritingResult underwritingResult = submitProposals.getUnderwritingResult();
		if (underwritingResult == null) {
			handerMap.put("underwritingJson", null);
		} else {
			handerMap.put("underwritingJson", underwritingResult.getData().getUnderwritingJson());
			Date createdAt = underwritingResult.getCreatedAt();
			OfferResultDataResult dataResultTwo = offerResult.getData().getResult();
			Integer insurComId = dataResultTwo.getInsuranceCompanyId();
			boolean payExpire = false;
			Date beginTime24Hour = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
			Date beginTime2Hour = new Date(System.currentTimeMillis() - 2 * 60 * 60 * 1000);
			if (null != createdAt) {
				switch (insurComId) {
				case 24:
					if (createdAt.getTime() < beginTime24Hour.getTime()) {
						payExpire = true;
					}
					break;

				default:
					if (createdAt.getTime() < beginTime2Hour.getTime()) {
						payExpire = true;
					}
					break;
				}
			}
			handerMap.put("payExpire", payExpire);
		}

		InsuranceResult insRes = insuranceResultRepository.findByOrderId(orderId);
		if (null != insRes) {
			handerMap.put("biPolicyNo", insRes.getData().getBiPolicyNo());
			handerMap.put("ciPolicyNo", insRes.getData().getCiPolicyNo());
		}

		// 将数据封装成指定的数据格式返回
		ResponseBody responseBody = new ResponseBody();
		responseBody.setErrorCode(0);
		responseBody.setErrorInfo("OK");
		responseBody.setData(handerMap);
		return responseBody;
	}

	/*
	 * 
	 * 请求承保查询接口
	 * 
	 */

	@RequestMapping(value = "/confirmUnderwriting", method = RequestMethod.POST)
	@org.springframework.web.bind.annotation.ResponseBody
	public ResponseBody mobileConfirmUnderwriting(@RequestParam(value = "orderId") String orderId) throws Exception {

		ResponseBody responseBody = underwritingResultService.reqConfirmUnderwriting(orderId);

		return responseBody;
	}

	/**
	 * 获取投保信息
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUnderWritingResult", method = RequestMethod.GET)
	@org.springframework.web.bind.annotation.ResponseBody
	public ResponseBody getUnderwritingResult(@RequestParam(value = "orderId") String orderId) throws Exception {
		ResponseBody responseBody = underwritingResultService.getUnderwritingResult(orderId);
		return responseBody;
	}
	
	

}
