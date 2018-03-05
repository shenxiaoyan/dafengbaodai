package com.liyang.client.tianan;

import org.springframework.util.StringUtils;

import com.liyang.client.BaseRequestHeader;
import com.liyang.client.IMessage;
import com.liyang.client.tianan.dto.ApplyInfoDto;
import com.liyang.client.tianan.dto.CarOwerDto;
import com.liyang.client.tianan.dto.DeliveryDto;
import com.liyang.client.tianan.dto.InsureInfoDto;
import com.liyang.client.tianan.dto.ScxlSalesDto;
import com.liyang.client.tianan.enums.RateMarkEnums;
import com.liyang.client.tianan.exception.ExceptionTiananInitFailed;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * @author Administrator
 *
 */
public class MessageInsureConfirmation implements IMessage {
	// public MessageInsureConfirmation() throws ExceptionTiananInitFailed {
	// BaseRequestHeader requestHeader =
	// FacadeService.getInstance().getDefaultRequestHeader();
	// setRequestHead(requestHeader);
	// initDefaultValue();
	// }

	private final String rateMark;

	private final String carPremiumCaculateNo;

	private final ApplyInfoDto applyInfoDto;

	private final InsureInfoDto insureInfoDto;

	private final CarOwerDto carOwerDto;

	private final DeliveryDto deliveryDto;

	private final BaseRequestHeader requestHead;

	private final String businessDepartmentCode;

	private final String businessTeamCode;

	private final String businessServiceCode;

	private final String companyCode;

	private final String solutionCode;

	private final String salesmanCode;

	private final String salesmanName;

	private final String customerSource;

	private final String businessIssueInd;

	private final String outerSystem;

	private final String businessNature;

	private final String rcldInd;

	private final String rcldPlan;

	private final String rcldUwCount;

	private final String rcldProductName;

	private final String rcldProductAmt;

	private final ScxlSalesDto scxlSalesDto;

	/**
	 * 产品类型
	 * 
	 * @return the rateMark
	 */
	public String getRateMark() {
		return rateMark;
	}

	/**
	 * 保费计算识别码
	 * 
	 * @return the carPremiumCaculateNo
	 */
	public String getCarPremiumCaculateNo() {
		return carPremiumCaculateNo;
	}

	/**
	 * 投保人信息
	 * 
	 * @return the applyInfoDto
	 */
	public ApplyInfoDto getApplyInfoDto() {
		return applyInfoDto;
	}

	/**
	 * 被保人信息
	 * 
	 * @return the insureInfoDto
	 */
	public InsureInfoDto getInsureInfoDto() {
		return insureInfoDto;
	}

	/**
	 * 车主信息
	 * 
	 * @return the carOwerDto
	 */
	public CarOwerDto getCarOwerDto() {
		return carOwerDto;
	}

	/**
	 * 配送信息
	 * 
	 * @return the deliveryDto
	 */
	public DeliveryDto getDeliveryDto() {
		return deliveryDto;
	}

	/**
	 * 请求头信息
	 * 
	 * @return the requestHead
	 */
	public BaseRequestHeader getRequestHead() {
		return requestHead;
	}

	/**
	 * 真实归属部门
	 * 
	 * @return the businessDepartmentCode
	 */
	public String getBusinessDepartmentCode() {
		return businessDepartmentCode;
	}

	/**
	 * 真实归属团队
	 * 
	 * @return the businessTeamCode
	 */
	public String getBusinessTeamCode() {
		return businessTeamCode;
	}

	/**
	 * 真实归属业务员
	 * 
	 * @return the businessServiceCode
	 */
	public String getBusinessServiceCode() {
		return businessServiceCode;
	}

	/**
	 * 虚拟归属部门
	 * 
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 虚拟归属团队
	 * 
	 * @return the solutionCode
	 */
	public String getSolutionCode() {
		return solutionCode;
	}

	/**
	 * 虚拟归属业务员
	 * 
	 * @return the salesmanCode
	 */
	public String getSalesmanCode() {
		return salesmanCode;
	}

	/**
	 * 虚拟业务员名称
	 * 
	 * @return the salesmanName
	 */
	public String getSalesmanName() {
		return salesmanName;
	}

	/**
	 * 客户来源
	 * 
	 * @return the customerSource
	 */
	public String getCustomerSource() {
		return customerSource;
	}

	/**
	 * 是否B2A业务员
	 * 
	 * @return the businessIssueInd
	 */
	public String getBusinessIssueInd() {
		return businessIssueInd;
	}

	/**
	 * 外围系统标志
	 * 
	 * @return the outerSystem
	 */
	public String getOuterSystem() {
		return outerSystem;
	}

	/**
	 * 业务来源
	 * 
	 * @return the businessNature
	 */
	public String getBusinessNature() {
		return businessNature;
	}

	/**
	 * 人车联动标志
	 * 
	 * @return the rcldInd
	 */
	public String getRcldInd() {
		return rcldInd;
	}

	/**
	 * 人车联动方案
	 * 
	 * @return the rcldPlan
	 */
	public String getRcldPlan() {
		return rcldPlan;
	}

	/**
	 * 人车联动份数
	 * 
	 * @return the rcldUwCount
	 */
	public String getRcldUwCount() {
		return rcldUwCount;
	}

	/**
	 * 非车产品名称
	 * 
	 * @return the rcldProductName
	 */
	public String getRcldProductName() {
		return rcldProductName;
	}

	/**
	 * 产品金额
	 * 
	 * @return the rcldProductAmt
	 */
	public String getRcldProductAmt() {
		return rcldProductAmt;
	}

	/**
	 * 随车行李险销售信息
	 * 
	 * @return the scxlSalesDto
	 */
	public ScxlSalesDto getScxlSalesDto() {
		return scxlSalesDto;
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {
		if (StringUtils.isEmpty(this.rateMark)) {
			throw new ExceptionTiananParamInvliad("产品类型不能为空");
		}else{
			if(!RateMarkEnums.isExist(this.rateMark)){
				throw new ExceptionTiananParamInvliad("产品类型不匹配");
			}
		}
		if (StringUtils.isEmpty(this.carPremiumCaculateNo)) {
			throw new ExceptionTiananParamInvliad("保费计算识别码不能为空");
		}
		this.applyInfoDto.validate();
		this.insureInfoDto.validate();
		this.carOwerDto.validate();
		this.deliveryDto.validate();

	}

	@Override
	public void initDefaultValue() {

	}

	public MessageInsureConfirmation(Builder builder) {
		this.rateMark = builder.rateMark;
		this.carPremiumCaculateNo = builder.carPremiumCaculateNo;
		this.applyInfoDto = builder.applyInfoDto;
		this.insureInfoDto = builder.insureInfoDto;
		this.carOwerDto = builder.carOwerDto;
		this.deliveryDto = builder.deliveryDto;
		this.requestHead = builder.requestHead;
		this.businessDepartmentCode = builder.businessDepartmentCode;
		this.businessTeamCode = builder.businessTeamCode;
		this.businessServiceCode = builder.businessServiceCode;
		this.companyCode = builder.companyCode;
		this.solutionCode = builder.solutionCode;
		this.salesmanCode = builder.salesmanCode;
		this.salesmanName = builder.salesmanName;
		this.customerSource = builder.customerSource;
		this.businessIssueInd = builder.businessIssueInd;
		this.outerSystem = builder.outerSystem;
		this.businessNature = builder.businessNature;
		this.rcldInd = builder.rcldInd;
		this.rcldPlan = builder.rcldPlan;
		this.rcldUwCount = builder.rcldUwCount;
		this.rcldProductName = builder.rcldProductName;
		this.rcldProductAmt = builder.rcldProductAmt;
		this.scxlSalesDto = builder.scxlSalesDto;
	}

	public static class Builder {
		private final String rateMark;

		private final String carPremiumCaculateNo;

		private final ApplyInfoDto applyInfoDto;

		private final InsureInfoDto insureInfoDto;

		private final CarOwerDto carOwerDto;

		private final DeliveryDto deliveryDto;

		private final BaseRequestHeader requestHead;

		private String businessDepartmentCode;

		private String businessTeamCode;

		private String businessServiceCode;

		private String companyCode;

		private String solutionCode;

		private String salesmanCode;

		private String salesmanName;

		private String customerSource;

		private String businessIssueInd;

		private String outerSystem;

		private String businessNature;

		private String rcldInd;

		private String rcldPlan;

		private String rcldUwCount;

		private String rcldProductName;

		private String rcldProductAmt;

		private ScxlSalesDto scxlSalesDto;

		public Builder(String baseUrl, String tradeNo, String rateMark, String carPremiumCaculateNo, ApplyInfoDto applyInfoDto,
				InsureInfoDto insureInfoDto, CarOwerDto carOwerDto, DeliveryDto deliveryDto)
				throws ExceptionTiananInitFailed {
			if (StringUtils.isEmpty(tradeNo)) {
				throw new ExceptionTiananInitFailed("tradeNo为空");
			}
			BaseRequestHeader requestHeader = FacadeService.getInstance(baseUrl).getDefaultRequestHeader(tradeNo);
			this.requestHead = requestHeader;
			initDefaultValue();
			this.rateMark = rateMark;
			this.carPremiumCaculateNo = carPremiumCaculateNo;
			this.applyInfoDto = applyInfoDto;
			this.insureInfoDto = insureInfoDto;
			this.carOwerDto = carOwerDto;
			this.deliveryDto = deliveryDto;

		}

		public void initDefaultValue() {

		}

		public Builder businessTeamCode(String businessTeamCode) {
			this.businessServiceCode = businessTeamCode;
			return this;
		}

		public Builder businessServiceCode(String businessServiceCode) {
			this.businessServiceCode = businessServiceCode;
			return this;
		}

		public Builder companyCode(String companyCode) {
			this.companyCode = companyCode;
			return this;
		}

		public Builder solutionCode(String solutionCode) {
			this.solutionCode = solutionCode;
			return this;
		}

		public Builder salesmanCode(String salesmanCode) {
			this.salesmanCode = salesmanCode;
			return this;
		}

		public Builder salesmanName(String salesmanName) {
			this.salesmanName = salesmanName;
			return this;
		}

		public Builder customerSource(String customerSource) {
			this.customerSource = customerSource;
			return this;
		}

		public Builder businessIssueInd(String businessIssueInd) {
			this.businessIssueInd = businessIssueInd;
			return this;
		}

		public Builder outerSystem(String outerSystem) {
			this.outerSystem = outerSystem;
			return this;
		}

		public Builder businessNature(String businessNature) {
			this.businessNature = businessNature;
			return this;
		}

		public Builder rcldInd(String rcldInd) {
			this.rcldInd = rcldInd;
			return this;
		}

		public Builder rcldPlan(String rcldPlan) {
			this.rcldPlan = rcldPlan;
			return this;
		}

		public Builder rcldUwCount(String rcldUwCount) {
			this.rcldUwCount = rcldUwCount;
			return this;
		}

		public Builder rcldProductName(String rcldProductName) {
			this.rcldProductName = rcldProductName;
			return this;
		}

		public Builder rcldProductAmt(String rcldProductAmt) {
			this.rcldProductAmt = rcldProductAmt;
			return this;
		}

		public Builder scxlSalesDto(ScxlSalesDto scxlSalesDto) {
			this.scxlSalesDto = scxlSalesDto;
			return this;
		}

		public MessageInsureConfirmation build() throws ExceptionTiananParamInvliad {
			MessageInsureConfirmation message = new MessageInsureConfirmation(this);
			message.validate();
			return message;
		}
	}

}
