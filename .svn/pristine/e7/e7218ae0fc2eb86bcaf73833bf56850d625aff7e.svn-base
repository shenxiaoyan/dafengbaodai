package com.liyang.client.strategy;

import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.asn1.x509.PolicyQualifierId;

import com.liyang.client.IClient;
import com.liyang.client.IMessage;
import com.liyang.client.tianan.MessageGetSign;
import com.liyang.client.tianan.MessageGetSignTest;
import com.liyang.client.tianan.MessageInsureConfirmation;
import com.liyang.client.tianan.MessagePremiumAccurate;
import com.liyang.client.tianan.MessageQueryCarModel;
import com.liyang.client.tianan.MessageQueryCarModel.Builder;
import com.liyang.client.tianan.MessageQueryProposal;
import com.liyang.client.tianan.ResultGetSign;
import com.liyang.client.tianan.ResultInsureConfirmation;
import com.liyang.client.tianan.ResultPremiumAccurate;
import com.liyang.client.tianan.ResultQueryCarModel;
import com.liyang.client.tianan.ResultQueryProposal;
import com.liyang.client.tianan.ServiceGetSign;
import com.liyang.client.tianan.ServiceInsureConfirmation;
import com.liyang.client.tianan.ServicePremiumAccurate;
import com.liyang.client.tianan.ServiceQueryCarModel;
import com.liyang.client.tianan.ServiceQueryProposal;
import com.liyang.client.tianan.TypeDate;
import com.liyang.client.tianan.dto.ApplyInfoDto;
import com.liyang.client.tianan.dto.CarInfoDto;
import com.liyang.client.tianan.dto.CarOwerDto;
import com.liyang.client.tianan.dto.CombosDataDto;
import com.liyang.client.tianan.dto.DeliveryDto;
import com.liyang.client.tianan.dto.ExtendInfoDTO;
import com.liyang.client.tianan.dto.InsureInfoDto;
import com.liyang.client.tianan.dto.VehicleJingyouDto;
import com.liyang.client.tianan.exception.ExceptionTiananInitFailed;
import com.liyang.client.xiaoma.ResultCreateEnquiry;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.platform.Platform;

/**
 * 
 * @author Administrator
 *
 */
public class ApiStrategyTianan implements IApiStrategy {

	ServiceGetSign serviceGetSign;
	IClient client;

	public ApiStrategyTianan(IClient client, ServiceGetSign serviceGetSign) {
		this.serviceGetSign = serviceGetSign;
		this.client = client;
	}

	@Override
	public ISecurityInfo parepareSecurityInfo(ISecurityInfo securityInfo, IContextInfo context)
			throws ExceptionRequired {
		SecurityInfoTianan result = null;
		if (securityInfo != null) {
			result = (SecurityInfoTianan) securityInfo;
		} else {
			throw new ExceptionRequired("请提供非空的SecurityInfo");
		}
		String url = result.getUrl();
		String openID = result.getOpenID();
		String token = result.getToken();
		IMessage message = new MessageGetSign(openID, url, token);
		//TODO
		ResultGetSign res = null;
		try {
			res = (ResultGetSign) serviceGetSign.callService(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res != null) {
			result.setSign(res.getSign());
		}
		return result;
	}

	@Override
	public ICarModelInfo queryCarModelInfo(String baseUrl, ISecurityInfo securityInfo, ILicenseInfo licenseInfo,
			IPolicyQueryInfo policyQueryInfo, IContextInfo context) throws java.lang.Exception {
		ICarModelInfo result = null;
		ServiceQueryCarModel service = new ServiceQueryCarModel(client);
		String cityCode = policyQueryInfo.getCityCode();
		String brandName = licenseInfo.getBrandName();
		String enginNo = licenseInfo.getEnginNo();
		TypeDate enrollDate = new TypeDate(licenseInfo.getRegisterDate());
		TypeDate startDate = new TypeDate(policyQueryInfo.getStartDate());
		String frameNo = licenseInfo.getFrameNo();
		String licenseNo = licenseInfo.getLicenseNo();
		Builder builder = new MessageQueryCarModel.Builder(baseUrl, cityCode, brandName, enginNo, enrollDate, startDate, frameNo,
				licenseNo);
		MessageQueryCarModel message = new MessageQueryCarModel(builder);
		result = (ICarModelInfo) service.callService(message);
		return result;
	}
	
	@Override
	public ICarModelInfo queryCarModelInfo(ISecurityInfo securityInfo, ILicenseInfo licenseInfo,
			IPolicyQueryInfo policyQueryInfo, IContextInfo context) throws Exception {
		return null;
	}
	
	@Override
	public IPolicyInfo queryLatestPolicy(ISecurityInfo securityInfo, ILicenseInfo licenseInfo,
			IPolicyQueryInfo policyQueryInfo, IContextInfo context) {
		IPolicyInfo result = null;
		return result;
	}

	@Override
	public IPolicyPricingInfo createEnquiry(ISecurityInfo securityInfo, ICarModelInfo carModelInfo,
			ILicenseInfo licenseInfo, IPolicyProductSetInfo policyProductSetInfo, IPolicyQueryInfo policyQueryInfo,
			IPlatformInfo platformInfo, IContextInfo context) throws java.lang.Exception {
//		IPolicyPricingInfo result = null;
//		ServicePremiumAccurate service = new ServicePremiumAccurate(client);
//
//		String tradeNo = ((SecurityInfoTianan) securityInfo).getTradeNo();
//		String quotationType = policyQueryInfo.getPolicyCategoryType();
//		String cityCode = policyQueryInfo.getCityCode();
//		TypeDate startDate = new TypeDate(policyQueryInfo.getStartDate());
//		TypeDate endDate = new TypeDate(policyQueryInfo.getEndDate());
//		String engineNo = licenseInfo.getEnginNo();
//		String licenseNo = licenseInfo.getLicenseNo();
//		String frameNo = licenseInfo.getFrameNo();
//		String brandName = licenseInfo.getBrandName();
//		String rbCode = ((CarModelInfoTianan) carModelInfo).getCarModelCode();
//		TypeDate enrollDate = new TypeDate(licenseInfo.getRegisterDate());
//		String ecdemicFlag = ((CarModelInfoTianan) carModelInfo).getEcdemicFlag();
//		Integer seatCount = ((CarModelInfoTianan) carModelInfo).getSeatCount();
//		String carOwnerIdentifyNumber = policyQueryInfo.getCarOwnerIdentifyNumber();
//		Double actualValue = ((CarModelInfoTianan) carModelInfo).getActualValue();
//		Double purchasePrice = ((CarModelInfoTianan) carModelInfo).getPurchasePrice();
//		String importFlag = ((CarModelInfoTianan) carModelInfo).getZoneFlag();
//		VehicleJingyouDto vehicleJingyouDto = createVehicleJingyouDto(carModelInfo);
//		CarInfoDto.Builder carInfoDtoBuilder = new CarInfoDto.Builder(engineNo, licenseNo, frameNo, brandName, rbCode,
//				enrollDate, ecdemicFlag, seatCount, carOwnerIdentifyNumber, actualValue, purchasePrice, importFlag,
//				vehicleJingyouDto);
//		CarInfoDto carInfoDto = new CarInfoDto(carInfoDtoBuilder);
//		List<CombosDataDto> combosList = createCombosDtoListFromPolicyProductSetInfo(policyProductSetInfo);
//		List<ExtendInfoDTO> extendInfoList = new ArrayList<ExtendInfoDTO>();
//		String mobilePhone = policyQueryInfo.getMobilePhone();
//		Customer customer = createCustomerInfoFromLicenseInfo(licenseInfo);
//		Platform platform = createPlatformFromPlatformInfo(platformInfo);
//		MessagePremiumAccurate.Builder builder = new MessagePremiumAccurate.Builder(tradeNo, quotationType, cityCode,
//				startDate, endDate, carInfoDto, combosList, extendInfoList, mobilePhone, customer, platform);
//		MessagePremiumAccurate message = new MessagePremiumAccurate(builder);
//		result = (IPolicyPricingInfo) service.callService(message);
//		return result;
		return null;
	}
	
	@Override
	public IPolicyPricingInfo createEnquiry(String baseUrl, ISecurityInfo securityInfo, ICarModelInfo carModelInfo,
			ILicenseInfo licenseInfo, IPolicyProductSetInfo policyProductSetInfo, IPolicyQueryInfo policyQueryInfo,
			IPlatformInfo platformInfo, IContextInfo context) throws java.lang.Exception {
		IPolicyPricingInfo result = null;
		ServicePremiumAccurate service = new ServicePremiumAccurate(client);

		String tradeNo = ((SecurityInfoTianan) securityInfo).getTradeNo();
		String quotationType = policyQueryInfo.getPolicyCategoryType();
		String cityCode = policyQueryInfo.getCityCode();
		TypeDate startDate = new TypeDate(policyQueryInfo.getStartDate());
		TypeDate endDate = new TypeDate(policyQueryInfo.getEndDate());
		String engineNo = licenseInfo.getEnginNo();
		String licenseNo = licenseInfo.getLicenseNo();
		String frameNo = licenseInfo.getFrameNo();
		String brandName = licenseInfo.getBrandName();
		String rbCode = ((CarModelInfoTianan) carModelInfo).getCarModelCode();
		TypeDate enrollDate = new TypeDate(licenseInfo.getRegisterDate());
		String ecdemicFlag = ((CarModelInfoTianan) carModelInfo).getEcdemicFlag();
		Integer seatCount = ((CarModelInfoTianan) carModelInfo).getSeatCount();
		String carOwnerIdentifyNumber = policyQueryInfo.getCarOwnerIdentifyNumber();
		Double actualValue = ((CarModelInfoTianan) carModelInfo).getActualValue();
		Double purchasePrice = ((CarModelInfoTianan) carModelInfo).getPurchasePrice();
		String importFlag = ((CarModelInfoTianan) carModelInfo).getZoneFlag();
		VehicleJingyouDto vehicleJingyouDto = createVehicleJingyouDto(carModelInfo);
		CarInfoDto.Builder carInfoDtoBuilder = new CarInfoDto.Builder(engineNo, licenseNo, frameNo, brandName, rbCode,
				enrollDate, ecdemicFlag, seatCount, carOwnerIdentifyNumber, actualValue, purchasePrice, importFlag,
				vehicleJingyouDto);
		CarInfoDto carInfoDto = new CarInfoDto(carInfoDtoBuilder);
		List<CombosDataDto> combosList = createCombosDtoListFromPolicyProductSetInfo(policyProductSetInfo);
		List<ExtendInfoDTO> extendInfoList = new ArrayList<ExtendInfoDTO>();
		String mobilePhone = policyQueryInfo.getMobilePhone();
		Customer customer = createCustomerInfoFromLicenseInfo(licenseInfo);
		Platform platform = createPlatformFromPlatformInfo(platformInfo);
		MessagePremiumAccurate.Builder builder = new MessagePremiumAccurate.Builder(baseUrl, tradeNo, quotationType, cityCode,
				startDate, endDate, carInfoDto, combosList, extendInfoList, mobilePhone, customer, platform);
		MessagePremiumAccurate message = new MessagePremiumAccurate(builder);
		result = (IPolicyPricingInfo) service.callService(message);
		return result;
	}

	private VehicleJingyouDto createVehicleJingyouDto(ICarModelInfo carModelInfo) {
		return null;
	}

	private Platform createPlatformFromPlatformInfo(IPlatformInfo platformInfo) {
		return null;
	}

	private Customer createCustomerInfoFromLicenseInfo(ILicenseInfo licenseInfo) {
		return null;
	}

	private List<CombosDataDto> createCombosDtoListFromPolicyProductSetInfo(
			IPolicyProductSetInfo policyProductSetInfo) {
		return null;
	}
	
	@Override
	public IPolicyInfo submitParaPolicy(ISecurityInfo securityInfo, IPlatformInfo platformInfo,
			IPolicyOrderInfo policyOrderInfo, IPolicyPricingInfo policyPricingInfo, IInsureUserInfo insureUserInfo,
			IInsuredUserInfo insuredUserInfo, ICarOwnerInfo carOwnerInfo, IDeliveryInfo deliveryInfo,
			IInvoiceInfo invoiceInfo, IContextInfo context) throws java.lang.Exception {
//		IPolicyInfo result = null;
//		String tradeNo = ((SecurityInfoTianan) securityInfo).getTradeNo();
//		// 产品类型：传统或网电销
//		String rateMark = policyOrderInfo.getItemSaleType();
//		String carPremiumCaculateNo = ((PolicyPricingInfoTianan) policyPricingInfo).getCalculateNo();
//		ApplyInfoDto applyInfoDto = createApplyDtoFromInsureUserInfo(insureUserInfo);
//		InsureInfoDto insureInfoDto = crreateInsuredDtoFromInsuredUserInfo(insuredUserInfo);
//		CarOwerDto carOwerDto = createCarOwnerDtoFromCarOwnerInfo(carOwnerInfo);
//		DeliveryDto deliveryDto = createDeliveryDtoFromDeliveryInfo(deliveryInfo);
//		MessageInsureConfirmation.Builder builder = new MessageInsureConfirmation.Builder(tradeNo, rateMark,
//				carPremiumCaculateNo, applyInfoDto, insureInfoDto, carOwerDto, deliveryDto);
//		MessageInsureConfirmation message = new MessageInsureConfirmation(builder);
//		ServiceInsureConfirmation service = new ServiceInsureConfirmation(client);
//		result = (IPolicyInfo) service.callService(message);
//		return result;
		return null;
	}
	
	/**
	 * 后添加
	 */
	@Override
	public IPolicyInfo submitParaPolicy(String baseUrl, ISecurityInfo securityInfo, IPlatformInfo platformInfo,
			IPolicyOrderInfo policyOrderInfo, IPolicyPricingInfo policyPricingInfo, IInsureUserInfo insureUserInfo,
			IInsuredUserInfo insuredUserInfo, ICarOwnerInfo carOwnerInfo, IDeliveryInfo deliveryInfo,
			IInvoiceInfo invoiceInfo, IContextInfo context) throws java.lang.Exception {
		IPolicyInfo result = null;
		String tradeNo = ((SecurityInfoTianan) securityInfo).getTradeNo();
		// 产品类型：传统或网电销
		String rateMark = policyOrderInfo.getItemSaleType();
		String carPremiumCaculateNo = ((PolicyPricingInfoTianan) policyPricingInfo).getCalculateNo();
		ApplyInfoDto applyInfoDto = createApplyDtoFromInsureUserInfo(insureUserInfo);
		InsureInfoDto insureInfoDto = crreateInsuredDtoFromInsuredUserInfo(insuredUserInfo);
		CarOwerDto carOwerDto = createCarOwnerDtoFromCarOwnerInfo(carOwnerInfo);
		DeliveryDto deliveryDto = createDeliveryDtoFromDeliveryInfo(deliveryInfo);
		MessageInsureConfirmation.Builder builder = new MessageInsureConfirmation.Builder(baseUrl, tradeNo, rateMark,
				carPremiumCaculateNo, applyInfoDto, insureInfoDto, carOwerDto, deliveryDto);
		MessageInsureConfirmation message = new MessageInsureConfirmation(builder);
		ServiceInsureConfirmation service = new ServiceInsureConfirmation(client);
		result = (IPolicyInfo) service.callService(message);
		return result;
	}

	private DeliveryDto createDeliveryDtoFromDeliveryInfo(IDeliveryInfo deliveryInfo) {
		return null;
	}

	private CarOwerDto createCarOwnerDtoFromCarOwnerInfo(ICarOwnerInfo carOwnerInfo) {
		return null;
	}

	private InsureInfoDto crreateInsuredDtoFromInsuredUserInfo(IInsuredUserInfo insuredUserInfo) {
		return null;
	}

	private ApplyInfoDto createApplyDtoFromInsureUserInfo(IInsureUserInfo insureUserInfo) {
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
			IPolicyQueryInfo policyQueryInfo, IContextInfo context) throws java.lang.Exception {
//		IUnderWriteResultInfo result = null;
//		String tradeNo = ((SecurityInfoTianan) securityInfo).getTradeNo();
//		List<String> proList = policyQueryInfo.getProposalIdList();
//		MessageQueryProposal message = new MessageQueryProposal(tradeNo, proList);
//		ServiceQueryProposal service = new ServiceQueryProposal(client);
//		result = (IUnderWriteResultInfo) service.callService(message);
//		return result;
		return null;
	}
	
	@Override
	public IUnderWriteResultInfo confirmUnderWrite(String baseUrl, ISecurityInfo securityInfo, IPolicyInfo policyInfo,
			IPolicyQueryInfo policyQueryInfo, IContextInfo context) throws java.lang.Exception {
		IUnderWriteResultInfo result = null;
		String tradeNo = ((SecurityInfoTianan) securityInfo).getTradeNo();
		List<String> proList = policyQueryInfo.getProposalIdList();
		MessageQueryProposal message = new MessageQueryProposal(baseUrl, tradeNo, proList);
		ServiceQueryProposal service = new ServiceQueryProposal(client);
		result = (IUnderWriteResultInfo) service.callService(message);
		return result;
	}

	

}
