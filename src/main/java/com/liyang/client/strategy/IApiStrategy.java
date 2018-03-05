package com.liyang.client.strategy;

import com.liyang.client.tianan.exception.ExceptionTiananInitFailed;

/**
 * 
 * @author Administrator
 *
 */
public interface IApiStrategy {
	/**
	 * 准备身份信息
	 * 
	 * @param securityInfo
	 * @param context
	 * @return
	 * @throws ExceptionRequired
	 */
	ISecurityInfo parepareSecurityInfo(ISecurityInfo securityInfo, IContextInfo context) throws ExceptionRequired;

	/**
	 * 查询车型
	 * 
	 * @param licenseInfo
	 * @param policyQueryInfo
	 * @param securityInfo
	 * @param context
	 * @return
	 * @throws ExceptionTiananInitFailed
	 * @throws Exception
	 */
	ICarModelInfo queryCarModelInfo(ISecurityInfo securityInfo, ILicenseInfo licenseInfo,
			IPolicyQueryInfo policyQueryInfo, IContextInfo context) throws Exception;

	/**
	 * 车型查询信息
	 * @param baseUrl
	 * @param securityInfo
	 * @param licenseInfo
	 * @param policyQueryInfo
	 * @param context
	 * @return
	 * @throws Exception
	 */
	ICarModelInfo queryCarModelInfo(String baseUrl, ISecurityInfo securityInfo, ILicenseInfo licenseInfo,
			IPolicyQueryInfo policyQueryInfo, IContextInfo context) throws Exception;
	/**
	 * 查询续保信息
	 * 
	 * @param licenseInfo
	 * @param policyQueryInfo
	 * @param context
	 * @param securityInfo
	 * @return
	 * @throws Exception
	 */
	IPolicyInfo queryLatestPolicy(ISecurityInfo securityInfo, ILicenseInfo licenseInfo,
			IPolicyQueryInfo policyQueryInfo, IContextInfo context) throws Exception;

	
	/**
	 * 询价精确报价
	 * @param securityInfo
	 * @param carModelInfo
	 * @param licenseInfo
	 * @param policyCategoryInfo
	 * @param policyQueryInfo
	 * @param platformInfo
	 * @param context
	 * @return
	 * @throws Exception
	 */
	IPolicyPricingInfo createEnquiry(ISecurityInfo securityInfo, ICarModelInfo carModelInfo, ILicenseInfo licenseInfo,
			IPolicyProductSetInfo policyCategoryInfo, IPolicyQueryInfo policyQueryInfo, IPlatformInfo platformInfo,
			IContextInfo context) throws Exception;
	
	/**
	 * 询价精确报价
	 * @param baseUrl
	 * @param securityInfo
	 * @param carModelInfo
	 * @param licenseInfo
	 * @param policyProductSetInfo
	 * @param policyQueryInfo
	 * @param platformInfo
	 * @param context
	 * @return
	 * @throws Exception
	 */
	IPolicyPricingInfo createEnquiry(String baseUrl, ISecurityInfo securityInfo, ICarModelInfo carModelInfo,
			ILicenseInfo licenseInfo, IPolicyProductSetInfo policyProductSetInfo, IPolicyQueryInfo policyQueryInfo,
			IPlatformInfo platformInfo, IContextInfo context) throws Exception;

	
	
	/**
	 * 提交核保投保
	 * @param securityInfo
	 * @param platformInfo
	 * @param policyOrderInfo
	 * @param policyPricingInfo
	 * @param insureUserInfo
	 * @param insuredUserInfo
	 * @param carOwnerInfo
	 * @param deliveryInfo
	 * @param invoiceInfo
	 * @param context
	 * @return
	 * @throws Exception
	 */
	IPolicyInfo submitParaPolicy(ISecurityInfo securityInfo, IPlatformInfo platformInfo,
			IPolicyOrderInfo policyOrderInfo, IPolicyPricingInfo policyPricingInfo, IInsureUserInfo insureUserInfo,
			IInsuredUserInfo insuredUserInfo, ICarOwnerInfo carOwnerInfo, IDeliveryInfo deliveryInfo,
			IInvoiceInfo invoiceInfo, IContextInfo context) throws Exception;
	
	
	/**
	 * 提交核保投保
	 * @param baseUrl
	 * @param securityInfo
	 * @param platformInfo
	 * @param policyOrderInfo
	 * @param policyPricingInfo
	 * @param insureUserInfo
	 * @param insuredUserInfo
	 * @param carOwnerInfo
	 * @param deliveryInfo
	 * @param invoiceInfo
	 * @param context
	 * @return
	 * @throws Exception
	 */
	IPolicyInfo submitParaPolicy(String baseUrl, ISecurityInfo securityInfo, IPlatformInfo platformInfo,
			IPolicyOrderInfo policyOrderInfo, IPolicyPricingInfo policyPricingInfo, IInsureUserInfo insureUserInfo,
			IInsuredUserInfo insuredUserInfo, ICarOwnerInfo carOwnerInfo, IDeliveryInfo deliveryInfo,
			IInvoiceInfo invoiceInfo, IContextInfo context) throws Exception;
	
	
	

	/**
	 * 准备支付
	 * @param securityInfo
	 * @param policyInfo
	 * @param platformInfo
	 * @param context
	 * @return
	 */
	IPrePayInfo preparePay(ISecurityInfo securityInfo, IPolicyInfo policyInfo, IPlatformInfo platformInfo,
			IContextInfo context);

	
	/**
	 * 支付
	 * @param securityInfo
	 * @param policyInfo
	 * @param context
	 * @return
	 */
	IPayInfo pay(ISecurityInfo securityInfo, IPolicyInfo policyInfo, IContextInfo context);

	
	/**
	 * 确认支付
	 * @param securityInfo
	 * @param policyInfo
	 * @param payInfo
	 * @param context
	 * @return
	 */
	IPayInfo confirmPay(ISecurityInfo securityInfo, IPolicyInfo policyInfo, IPayInfo payInfo, IContextInfo context);

	
	/**
	 * 确认承保结果
	 * @param securityInfo
	 * @param policyInfo
	 * @param policyQueryInfo
	 * @param context
	 * @return
	 * @throws Exception
	 */
	IUnderWriteResultInfo confirmUnderWrite(ISecurityInfo securityInfo, IPolicyInfo policyInfo,
			IPolicyQueryInfo policyQueryInfo, IContextInfo context) throws Exception;

	/**
	 * 确认承保结果
	 * @param baseUrl
	 * @param securityInfo
	 * @param policyInfo
	 * @param policyQueryInfo
	 * @param context
	 * @return
	 * @throws Exception
	 */
	IUnderWriteResultInfo confirmUnderWrite(String baseUrl, ISecurityInfo securityInfo, IPolicyInfo policyInfo,
			IPolicyQueryInfo policyQueryInfo, IContextInfo context) throws Exception;

	
}
