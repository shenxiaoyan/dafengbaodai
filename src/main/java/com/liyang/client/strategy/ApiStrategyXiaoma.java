package com.liyang.client.strategy;

import java.util.Map;
import java.util.ServiceConfigurationError;

import org.slf4j.Logger;
import org.springframework.util.StringUtils;

import com.liyang.client.IClient;
import com.liyang.client.IMessage;
import com.liyang.client.IServiceObserve;
import com.liyang.client.tianan.MessageGetSign;
import com.liyang.client.tianan.ResultGetSign;
import com.liyang.client.xiaoma.MessageConfirmUnderWriting;
import com.liyang.client.xiaoma.MessageCreateEnquiry;
import com.liyang.client.xiaoma.MessageQueryLatestPolicy;
import com.liyang.client.xiaoma.MessageSubmitProposal;
import com.liyang.client.xiaoma.ResultConfirmUnderWriting;
import com.liyang.client.xiaoma.ResultCreateEnquiry;
import com.liyang.client.xiaoma.ResultQueryLatestPolicy;
import com.liyang.client.xiaoma.ResultSubmitProposal;
import com.liyang.client.xiaoma.ServiceConfirmUnderWriting;
import com.liyang.client.xiaoma.ServiceCreateEnquiry;
import com.liyang.client.xiaoma.ServiceQueryLatestPolicy;
import com.liyang.client.xiaoma.ServiceSubmitProposal;
import com.liyang.domain.createenquiry.CreateEnquiryActRepository;
import com.liyang.domain.createenquiry.CreateEnquiryRepository;
import com.liyang.domain.createenquiry.CreateEnquiryStateRepository;
import com.liyang.domain.customer.CustomerRepository;
import com.liyang.domain.offerresult.OfferResultRepository;
import com.liyang.domain.platform.Platform;
import com.liyang.domain.submitproposal.SubmitProposalActRepository;
import com.liyang.domain.submitproposal.SubmitProposalRepository;
import com.liyang.domain.submitproposal.SubmitProposalStateRepository;

/**
 * 
 * @author Administrator
 *
 */
public class ApiStrategyXiaoma implements IApiStrategy {

	IClient client;

	public ApiStrategyXiaoma(IClient client) {
		this.client = client;
	}

	@Override
	public ISecurityInfo parepareSecurityInfo(ISecurityInfo securityInfo, IContextInfo context)
			throws ExceptionRequired {
		SecurityInfoXiaoma result = null;
		if (securityInfo != null) {
			result = (SecurityInfoXiaoma) securityInfo;
		} else {
			throw new ExceptionRequired("请提供非空的SecurityInfo");
		}
		String apiKey = result.getApiKey();
		if (StringUtils.isEmpty(apiKey)) {
			throw new ExceptionRequired("请提供非空的ApiKey");
		}
		return result;
	}

	@Override
	public ICarModelInfo queryCarModelInfo(ISecurityInfo securityInfo, ILicenseInfo licenseInfo,
			IPolicyQueryInfo policyQueryInfo, IContextInfo context) {
		ICarModelInfo result = null;
		return result;
	}
	
	@Override
	public ICarModelInfo queryCarModelInfo(String baseUrl, ISecurityInfo securityInfo, ILicenseInfo licenseInfo,
			IPolicyQueryInfo policyQueryInfo, IContextInfo context) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPolicyInfo queryLatestPolicy(ISecurityInfo securityInfo, ILicenseInfo licenseInfo,
			IPolicyQueryInfo policyQueryInfo, IContextInfo context) throws Exception {
		IPolicyInfo result = null;
		Logger logger = context.getLogger();
		Platform platform = policyQueryInfo.getPlatform();
		Map<String, String> queLatPolMap = createQueryLatestPolicyMapFromLicenseQueryInfo(licenseInfo, policyQueryInfo,
				context);
		MessageQueryLatestPolicy message = new MessageQueryLatestPolicy(logger, platform, queLatPolMap);

		IServiceObserve serviceObserve = null;
		ServiceQueryLatestPolicy service = new ServiceQueryLatestPolicy((SecurityInfoXiaoma) securityInfo, client,
				serviceObserve);
		result = (IPolicyInfo) service.callService(message);
		return result;
	}

	private Map<String, String> createQueryLatestPolicyMapFromLicenseQueryInfo(ILicenseInfo licenseInfo,
			IPolicyQueryInfo policyQueryInfo, IContextInfo context) {
		return null;
	}

	@Override
	public IPolicyPricingInfo createEnquiry(ISecurityInfo securityInfo, ICarModelInfo carModelInfo,
			ILicenseInfo licenseInfo, IPolicyProductSetInfo policyCategoryInfo, IPolicyQueryInfo policyQueryInfo,
			IPlatformInfo platformInfo, IContextInfo context) throws Exception {
		IPolicyPricingInfo result = null;
		Logger logger = context.getLogger();
		String token = securityInfo.getToken();
		Platform platform = platformInfo.getPlatform();
		Map<String, Object> creEnqMap = createCreateEnquiryMapFromCarmodelLicenseProductsetQueryinfoPlatform(
				carModelInfo, licenseInfo, policyCategoryInfo, policyQueryInfo, platformInfo);
		MessageCreateEnquiry message = new MessageCreateEnquiry(logger, token, platform, creEnqMap);
		IServiceObserve serviceObserve = null;
		CustomerRepository customerRepository = context.getCustomerRepository();
		CreateEnquiryActRepository createEnquiryActRepository = context.getCreateEnquiryActRepository();
		CreateEnquiryStateRepository createEnquiryStateRepository = context.getCreateEnquiryStateRepository();
		ServiceCreateEnquiry service = new ServiceCreateEnquiry((SecurityInfoXiaoma) securityInfo, client,
				serviceObserve, customerRepository, createEnquiryActRepository, createEnquiryStateRepository);
		result = (IPolicyPricingInfo) service.callService(message);
		return result;
	}

	private Map<String, Object> createCreateEnquiryMapFromCarmodelLicenseProductsetQueryinfoPlatform(
			ICarModelInfo carModelInfo, ILicenseInfo licenseInfo, IPolicyProductSetInfo policyCategoryInfo,
			IPolicyQueryInfo policyQueryInfo, IPlatformInfo platformInfo) {
		return null;
	}

	@Override
	public IPolicyInfo submitParaPolicy(ISecurityInfo securityInfo, IPlatformInfo platformInfo,
			IPolicyOrderInfo policyOrderInfo, IPolicyPricingInfo policyPricingInfo, IInsureUserInfo insureUserInfo,
			IInsuredUserInfo insuredUserInfo, ICarOwnerInfo carOwnerInfo, IDeliveryInfo deliveryInfo,
			IInvoiceInfo invoiceInfo, IContextInfo context) throws Exception {
		IPolicyInfo result = null;
		Platform platform = platformInfo.getPlatform();
		Map<String, Object> subProMap = createSubmitProposalMapFromPlatformOrderPricingInsureInsuredCarownerDeliveryInvoice(
				platformInfo, policyOrderInfo, policyPricingInfo, insureUserInfo, insuredUserInfo, carOwnerInfo,
				deliveryInfo, invoiceInfo);
		String token = securityInfo.getToken();
		MessageSubmitProposal message = new MessageSubmitProposal(platform, subProMap, token);
		IServiceObserve serviceObserve = null;
		CustomerRepository customerRepository = context.getCustomerRepository();
		OfferResultRepository offerResultRepository = context.getOfferResultRepository();
		CreateEnquiryStateRepository createEnquiryStateRepository = context.getCreateEnquiryStateRepository();
		CreateEnquiryRepository createEnquiryRepository = context.getCreateEnquiryRepository();
		SubmitProposalStateRepository submitProposalStateRepository = context.getSubmitProposalStateRepository();
		SubmitProposalActRepository submitProposalActRepository = context.getSubmitProposalActRepository();
		SubmitProposalRepository submitProposalRepository = context.getSubmitProposalRepository();
		ServiceSubmitProposal service = new ServiceSubmitProposal((SecurityInfoXiaoma) securityInfo, client,
				serviceObserve, customerRepository, offerResultRepository, createEnquiryStateRepository,
				createEnquiryRepository, submitProposalStateRepository, submitProposalActRepository,
				submitProposalRepository);
		result = (IPolicyInfo) service.callService(message);
		return result;
	}

	private Map<String, Object> createSubmitProposalMapFromPlatformOrderPricingInsureInsuredCarownerDeliveryInvoice(
			IPlatformInfo platformInfo, IPolicyOrderInfo policyOrderInfo, IPolicyPricingInfo policyPricingInfo,
			IInsureUserInfo insureUserInfo, IInsuredUserInfo insuredUserInfo, ICarOwnerInfo carOwnerInfo,
			IDeliveryInfo deliveryInfo, IInvoiceInfo invoiceInfo) {
		return null;
	}

	@Override
	public IPrePayInfo preparePay(ISecurityInfo securityInfo, IPolicyInfo policyInfo, IPlatformInfo platformInfo,
			IContextInfo context) {
		IPrePayInfo result = null;
		return result;
	}

	@Override
	public IPayInfo pay(ISecurityInfo securityInfo, IPolicyInfo policyInfo, IContextInfo context) {
		IPayInfo result = null;
		return result;
	}

	@Override
	public IPayInfo confirmPay(ISecurityInfo securityInfo, IPolicyInfo policyInfo, IPayInfo payInfo,
			IContextInfo context) {
		IPayInfo result = null;
		return result;
	}

	@Override
	public IUnderWriteResultInfo confirmUnderWrite(ISecurityInfo securityInfo, IPolicyInfo policyInfo,
			IPolicyQueryInfo policyQueryInfo, IContextInfo context) throws Exception {
		IUnderWriteResultInfo result = null;
		Logger logger = context.getLogger();
		String orderId = policyInfo.getOrderId();
		MessageConfirmUnderWriting message = new MessageConfirmUnderWriting(logger, orderId);
		IServiceObserve serviceObserve = null;
		ServiceConfirmUnderWriting service = new ServiceConfirmUnderWriting(securityInfo, client, serviceObserve);
		result = (IUnderWriteResultInfo) service.callService(message);
		return result;
	}

	@Override
	public IPolicyPricingInfo createEnquiry(String baseUrl, ISecurityInfo securityInfo, ICarModelInfo carModelInfo,
			ILicenseInfo licenseInfo, IPolicyProductSetInfo policyProductSetInfo, IPolicyQueryInfo policyQueryInfo,
			IPlatformInfo platformInfo, IContextInfo context) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPolicyInfo submitParaPolicy(String baseUrl, ISecurityInfo securityInfo, IPlatformInfo platformInfo,
			IPolicyOrderInfo policyOrderInfo, IPolicyPricingInfo policyPricingInfo, IInsureUserInfo insureUserInfo,
			IInsuredUserInfo insuredUserInfo, ICarOwnerInfo carOwnerInfo, IDeliveryInfo deliveryInfo,
			IInvoiceInfo invoiceInfo, IContextInfo context) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IUnderWriteResultInfo confirmUnderWrite(String baseUrl, ISecurityInfo securityInfo, IPolicyInfo policyInfo,
			IPolicyQueryInfo policyQueryInfo, IContextInfo context) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	

}
