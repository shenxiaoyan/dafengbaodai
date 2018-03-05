package com.liyang.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyang.aliyunsms.AliyunSmsService;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.customer.CustomerRepository;
import com.liyang.domain.insuranceresult.InsuranceResult;
import com.liyang.handle.GlobalExceptionHandler;

@Service
public class SmsService {
	
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	AliyunSmsService aliyunSmsService;

	/**
	 * 承保成功，短信通知出单人
	 * 
	 * @return
	 */
	public Boolean sendMessage(String insuredName, String license, String account, String insureCompany, String bio,
			String cio, Integer customerId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("insuredName", insuredName);
		param.put("license", license);
		param.put("account", account);
		param.put("insureCompany", insureCompany);
		param.put("bio", bio);
		param.put("cio", cio);
		Customer customer = customerRepository.findPhoneById(customerId);
		boolean sendMes = false;
		if (customer != null) {
			sendMes = aliyunSmsService.sendMes(customer.getPhone(), param, "SMS_123737313");
		}
		return sendMes;
	}
	
	/**
	 * 承保成功，短信通知出单人(重写)
	 * 
	 * @return
	 */
	public void sendMessage(InsuranceResult insuranceResult, double sumPrice, String insureCompany, String bio,
			String cio, Customer customer, Logger occerLogger) {
		try {
			String insuredName = insuranceResult.getSubmitProposal().getParams().getInsuredName();
			String license = insuranceResult.getData().getLicenseNumber();
			DecimalFormat df = new DecimalFormat("#0.00");
			String priceStr = df.format(sumPrice);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("insuredName", insuredName);
			param.put("license", license);
			param.put("account", priceStr);
			param.put("insureCompany", insureCompany);
			param.put("bio", bio);
			param.put("cio", cio);
			aliyunSmsService.sendMes(customer.getPhone(), param, "SMS_123737313");
			occerLogger.info("【------承保短信发送成功------】");
		} catch (Exception e) {
			occerLogger.error("【！！！！！！--------承保短信发送失败-------】");
			occerLogger.error(GlobalExceptionHandler.getTraceInfo(e));
		}
	}
}
