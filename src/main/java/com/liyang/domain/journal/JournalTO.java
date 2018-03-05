package com.liyang.domain.journal;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.enums.InsuranceType;

public class JournalTO {

	// private Double vehicleCommission = 0.0;
	// private Double compulsoryCommission = 0.0;
	// private Double commercialCommission = 0.0;

	@JsonIgnore
	private String token;
	private String orderId;
	private String carLicense;
	private String rejectReason;
	private Date insureTime;// 承保时间
	private Date createdAt;
	private Integer customerId;// 用户编号
	private InsuranceType insuranceType;// 产品类型
	private String insuredName;// 被保人
	private String stateCode;// 状态：结算中，已结算
	private String customerRealname;
	private String customerPhone;// 客户电话
	private String productLabel;// 险种名称
	private String productCompanyLabel;
	private Double commission;// 结算金额
	private Double compulsoryCommissionRate;// 交强险返佣比例
	private Double commercialCommissionRate;// 商业险返佣比例
	private Double vehicleCommissionRate;// 车船税返佣比例
	private Double commissionRate;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCarLicense() {
		return carLicense;
	}

	public void setCarLicense(String carLicense) {
		this.carLicense = carLicense;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public Date getInsureTime() {
		return insureTime;
	}

	public void setInsureTime(Date insureTime) {
		this.insureTime = insureTime;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public InsuranceType getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
	}

	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getCustomerRealname() {
		return customerRealname;
	}

	public void setCustomerRealname(String customerRealname) {
		this.customerRealname = customerRealname;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getProductLabel() {
		return productLabel;
	}

	public void setProductLabel(String productLabel) {
		this.productLabel = productLabel;
	}

	public String getProductCompanyLabel() {
		return productCompanyLabel;
	}

	public void setProductCompanyLabel(String productCompanyLabel) {
		this.productCompanyLabel = productCompanyLabel;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public Double getCompulsoryCommissionRate() {
		return compulsoryCommissionRate;
	}

	public void setCompulsoryCommissionRate(Double compulsoryCommissionRate) {
		this.compulsoryCommissionRate = compulsoryCommissionRate;
	}

	public Double getCommercialCommissionRate() {
		return commercialCommissionRate;
	}

	public void setCommercialCommissionRate(Double commercialCommissionRate) {
		this.commercialCommissionRate = commercialCommissionRate;
	}

	public Double getVehicleCommissionRate() {
		return vehicleCommissionRate;
	}

	public void setVehicleCommissionRate(Double vehicleCommissionRate) {
		this.vehicleCommissionRate = vehicleCommissionRate;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Double getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(Double commissionRate) {
		this.commissionRate = commissionRate;
	}

}
