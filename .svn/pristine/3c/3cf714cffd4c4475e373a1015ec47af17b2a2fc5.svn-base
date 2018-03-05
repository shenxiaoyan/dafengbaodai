package com.liyang.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liyang.domain.offerresult.OfferResult;
import com.liyang.domain.offerresult.OfferResultDataResult;
import com.liyang.domain.offerresult.OfferResultRepository;
import com.liyang.domain.submitproposal.SubmitProposal;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jayway.jsonpath.internal.function.numeric.Sum;
import com.liyang.domain.createenquiry.CreateEnquiryRepository;
import com.liyang.domain.customer.CustomerRepository;
import com.liyang.domain.insurercompany.InsureCompany;
import com.liyang.domain.insurercompany.InsureCompanyRepository;
import com.liyang.domain.submitproposal.SubmitProposalRepository;
import com.liyang.helper.ResponseBody;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
@Service
public class OutlineService {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CreateEnquiryRepository createEnquiryRepository;
	@Autowired
	private SubmitProposalRepository submitProposalRepository;
	@Autowired
	private InsureCompanyRepository insureCompanyRepository;
	@Autowired
	private OfferResultRepository offerResultRepository;
	// 承保成功状态码
	private final static String STATE_CODE_CHENGBAO_SUCCESS = "CHENGBAO_SUCCESS";
	// 核保成功代付款状态码
	private final static String STATE_CODE_HENBAO_SUCCESS_PAYMENT = "HENBAO_SUCCESS_PAYMENT";
	private final static String[] stateCodes = { "HENBAO_SUCCESS_PAYMENT", "PAYMENT_SUCCESS", "PAYMENT_FAILD",
			"PAYMENT_CANCEL", "CHENGBAO_FAILD", "CHENGBAO_SUCCESS", "HENBAO_PERSON", "HEBAO_WAIT_CONFRIM" };

	public Map<String, Object> getResult(Date beginDate, Date endDate) {
		// 总用户数
		long customerCount = customerRepository.count();
		// 新增的用户数
		long newCustomerCount = customerRepository.countByCreatedAtBetween(beginDate, endDate);
		// 询价数量
		long createEnquiryCount = createEnquiryRepository.countByCreatedAtBetween(beginDate, endDate);
		// 出单数量
		long submitProposalCount = submitProposalRepository.countByCreateTimeBetween(beginDate, endDate);
		// 核保通过数量
		long hebaoSuccessPaymentCount = submitProposalRepository
				.countByState_StateCodeAndCreateTimeBetween(STATE_CODE_HENBAO_SUCCESS_PAYMENT, beginDate, endDate);
		// 承保成功
		long chengbaoSuccessCount = submitProposalRepository
				.countByState_StateCodeAndCreateTimeBetween(STATE_CODE_CHENGBAO_SUCCESS, beginDate, endDate);
		// 承保成功的订单列表
		List<SubmitProposal> submitProposalList = submitProposalRepository
				.findByStateStateCodeAndCreateTimeBetween(STATE_CODE_CHENGBAO_SUCCESS, beginDate, endDate);
		OfferResultDataResult dataResult;
		// 每笔订单的金额
		double autoCompletion = 0;
		// 承保成功的总数量
		int num = 0;
		// 承保成功的总金额
		double sumPrice = 0;
		for (SubmitProposal submitProposal : submitProposalList) {
			dataResult = submitProposal.getOfferResult().getData().getResult();
			if (dataResult.getOfferDetail() != null) {
				double forcePremium = dataResult.getOfferDetail().getJSONObject("forcePremium")
						.getDouble("quotesPrice");
				double taxPrice = dataResult.getOfferDetail().getJSONObject("taxPrice").getDouble("quotesPrice");
				double originalForcePrice = forcePremium + taxPrice;
				double originalPrice = dataResult.getOfferDetail().getDouble("originalPrice");
				autoCompletion = +(originalForcePrice + originalPrice);
				sumPrice += autoCompletion;
				num += 1;
			}
		}

		// 承保成功总金额， 单位：分
		// Double price =
		// submitProposalRepository.queryPrice(STATE_CODE_CHENGBAO_SUCCESS, beginDate,
		// endDate);
		// System.out.println(price+"--------------------------");
		//
		//
		// if (price != null) {
		// price /= 100;
		// } else {
		// price = 0.0;
		// }
		// OfferResult offerResult=new OfferResult();
		// JSONObject priceJson = offerResult.getData().getResult().getOfferDetail();
		// double originalPrice = priceJson.getDouble("originalPrice") ;
		// double forcePrice =
		// priceJson.getJSONObject("forcePremium").getDouble("quotesPrice");
		// double taxPrice =
		// priceJson.getJSONObject("taxPrice").getDouble("quotesPrice");
		// double sumPrice = originalPrice + forcePrice + taxPrice ;
		// DecimalFormat df = new DecimalFormat("######0.00");
		// String priceString = df.format(price);
		/////////////////////// 承保成功饼图：各承保公司承保成功比例
		// 取得所有承保公司
		List<InsureCompany> list = insureCompanyRepository.getAllEnable();
		// 存放list
		List<Map<String, Object>> companyInfo = new ArrayList<Map<String, Object>>();
//		Long s = null;
		for (InsureCompany company : list) {
			Map<String, Object> companyMap = new HashMap<String, Object>();
			companyMap.put("name", company.getName());
			Long count = submitProposalRepository
					.countByState_StateCodeAndOfferResult_Data_Result_InsuranceCompanyIdAndCreateTimeBetween(
							STATE_CODE_CHENGBAO_SUCCESS, company.getInsurerCompanyId(), beginDate, endDate);// 对应公司的承保成功数量
//			s = count;
//			countByState_StateCodeAndCreateTimeBetween(STATE_CODE_CHENGBAO_SUCCESS, beginDate, endDate)
			companyMap.put("value", count);
			companyInfo.add(companyMap);
		}
		// 如果所有保险公司的承保成功数都是0的话，则去掉该值
		if (companyInfo.stream().allMatch(e -> (Long) e.get("value") == 0)) {
			companyInfo.forEach(e -> e.remove("value"));
		}

		// 漏斗图
		List<Map<String, Object>> funnelInfo = new ArrayList<Map<String, Object>>();
		// 总数
		double sum = (double) (createEnquiryCount + submitProposalCount + hebaoSuccessPaymentCount
				+ chengbaoSuccessCount);
		Map<String, Object> funnelMapCreateEnquiry = new HashMap<String, Object>();
		Map<String, Object> funnelMapSubmitProposal = new HashMap<String, Object>();
		Map<String, Object> funnelMapHebaoSuccessPayment = new HashMap<String, Object>();
		Map<String, Object> funnelMapChengbaoSuccess = new HashMap<String, Object>();
		funnelMapCreateEnquiry.put("name", "发起询价");
		funnelMapCreateEnquiry.put("value", (int) (createEnquiryCount / sum * 100 + 0.5));
		funnelMapSubmitProposal.put("name", "预约出单");
		funnelMapSubmitProposal.put("value", (int) (submitProposalCount / sum * 100 + 0.5));
		funnelMapHebaoSuccessPayment.put("name", "核保通过");
		funnelMapHebaoSuccessPayment.put("value", (int) (hebaoSuccessPaymentCount / sum * 100 + 0.5));
		funnelMapChengbaoSuccess.put("name", "承保成功");
		funnelMapChengbaoSuccess.put("value", (int) (chengbaoSuccessCount / sum * 100 + 0.5));
		funnelInfo.add(funnelMapCreateEnquiry);
		funnelInfo.add(funnelMapSubmitProposal);
		funnelInfo.add(funnelMapHebaoSuccessPayment);
		funnelInfo.add(funnelMapChengbaoSuccess);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 总前端用户数
		resultMap.put("customerCount", customerCount);
		// 新增前端用户
		resultMap.put("newCustomerCount", newCustomerCount);
		// 发起询价数
		resultMap.put("createEnquiryCount", createEnquiryCount);
		// 预约出单数
		resultMap.put("submitProposalCount", submitProposalCount);
		// 核保成功代付款数
		resultMap.put("hebaoSuccessPaymentCount", hebaoSuccessPaymentCount);
		// 承保成功数
		resultMap.put("chengbaoSuccessCount", chengbaoSuccessCount);
		// 承保成功总金额
		resultMap.put("chengbaoPriceSum", sumPrice);
		// 承保成功饼图
		resultMap.put("insureCompanyInfo", companyInfo);
		// 漏斗图信息
		resultMap.put("funnelInfo", funnelInfo);
		return resultMap;
	}

	public ResponseBody show(String timeNode) {
		ResponseBody body = new ResponseBody();
		// 取到当天0点
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date endDate = calendar.getTime();
		if ("day".equals(timeNode)) {
			// 取到昨天0点
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(endDate);
			calendar2.add(Calendar.DATE, -1);
			Date beginDate = calendar2.getTime();
			Map<String, Object> resultMap = this.getResult(beginDate, endDate);
			body.setErrorCode(0);
			body.setErrorInfo("ok");
			body.setData(resultMap);
		} else if ("week".equals(timeNode)) {
			// 取到一周前0点
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(endDate);
			calendar2.add(Calendar.WEEK_OF_MONTH, -1);
			Date beginDate = calendar2.getTime();
			Map<String, Object> resultMap = this.getResult(beginDate, endDate);
			body.setErrorCode(0);
			body.setErrorInfo("ok");
			body.setData(resultMap);
		} else if ("month".equals(timeNode)) {
			// 取到一个月前0点
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(endDate);
			calendar2.add(Calendar.MONTH, -3);
			Date beginDate = calendar2.getTime();
			Map<String, Object> resultMap = this.getResult(beginDate, endDate);
			body.setErrorCode(0);
			body.setErrorInfo("ok");
			body.setData(resultMap);
		} else {
			body.setErrorCode(100);
			body.setErrorInfo("timeNode错误");
		}
		return body;
	}
}
