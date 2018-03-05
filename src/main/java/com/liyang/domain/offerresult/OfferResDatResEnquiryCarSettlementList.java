package com.liyang.domain.offerresult;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;

/**
 * 理赔信息
 * @author Administrator
 *
 */
@Entity
@Table(name="offer_result_enquiry_car_settlement_list")
public class OfferResDatResEnquiryCarSettlementList implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	
	@JsonIgnore
	@ManyToMany(cascade=CascadeType.ALL,mappedBy="enquiryCarSettlementList")
	private Set<OfferResultDataResult> result;
	
	/**
	 *理赔信息：以下字段解析报价需解析的
	 */
	//车牌号
	@Column(name="license_number")
	@Info(label="理赔车牌号",placeholder="",tip="",help="",secret="")
	private String licenseNumber;   
	 //理赔金额(单位：元)
	@Column(name="claimPayment")
	@Info(label="理赔金额(单位：元)",placeholder="",tip="",help="",secret="")
	private String claimPayment;  
	//发生日期
	@Column(name="accident_date")
	@Info(label="理赔发生日期",placeholder="",tip="",help="",secret="")
	private Long accidentDate;    
	/**
	 * 解析可忽略
	 */
	@Column(name="customer_car_id")
	private Integer customerCarId;
	@Column(name="enquiry_id")
	private String	enquiryId;
	@Column(name="offer_id")
	private String offerId;
	@Column(name="insurance_company_id")
	private Integer insuranceCompanyId;
	@Column(name="placed_on_file_no")
	private String placedOnFileNo;
	@Column(name="claim_no")
	private String claimNo;
	@Column(name="accident_type")
	private String accidentType;
	@Column(name="settle_lawsuit_date")
	private Long settleLawsuitDate;
	@Column(name="insurance_start_date")
	private Long insuranceStartDate;
	@Column(name="insurance_end_date")
	private String insuranceEndDate;
	@Column(name="policy_address")
	private String policyAddress;
	private Integer state;
	@Column(name="create_time")
	private Long createTime;
	@Column(name="settlement_type")
	private Long settlementType;
	@Column(name="insurance_company_name")
	@Info(label="保险公司名称")
	private String insuranceCompanyName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Set<OfferResultDataResult> getResult() {
		return result;
	}
	public void setResult(Set<OfferResultDataResult> result) {
		this.result = result;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getLicenseNumber() {
		return licenseNumber;
	}
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	public String getClaimPayment() {
		return claimPayment;
	}
	public void setClaimPayment(String claimPayment) {
		this.claimPayment = claimPayment;
	}
	public Long getAccidentDate() {
		return accidentDate;
	}
	public void setAccidentDate(Long accidentDate) {
		this.accidentDate = accidentDate;
	}
	public Integer getCustomerCarId() {
		return customerCarId;
	}
	public void setCustomerCarId(Integer customerCarId) {
		this.customerCarId = customerCarId;
	}
	public String getEnquiryId() {
		return enquiryId;
	}
	public void setEnquiryId(String enquiryId) {
		this.enquiryId = enquiryId;
	}
	public String getOfferId() {
		return offerId;
	}
	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
	public Integer getInsuranceCompanyId() {
		return insuranceCompanyId;
	}
	public void setInsuranceCompanyId(Integer insuranceCompanyId) {
		this.insuranceCompanyId = insuranceCompanyId;
	}
	public String getPlacedOnFileNo() {
		return placedOnFileNo;
	}
	public void setPlacedOnFileNo(String placedOnFileNo) {
		this.placedOnFileNo = placedOnFileNo;
	}
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	public String getAccidentType() {
		return accidentType;
	}
	public void setAccidentType(String accidentType) {
		this.accidentType = accidentType;
	}
	public Long getSettleLawsuitDate() {
		return settleLawsuitDate;
	}
	public void setSettleLawsuitDate(Long settleLawsuitDate) {
		this.settleLawsuitDate = settleLawsuitDate;
	}
	public Long getInsuranceStartDate() {
		return insuranceStartDate;
	}
	public void setInsuranceStartDate(Long insuranceStartDate) {
		this.insuranceStartDate = insuranceStartDate;
	}
	public String getInsuranceEndDate() {
		return insuranceEndDate;
	}
	public void setInsuranceEndDate(String insuranceEndDate) {
		this.insuranceEndDate = insuranceEndDate;
	}
	public String getPolicyAddress() {
		return policyAddress;
	}
	public void setPolicyAddress(String policyAddress) {
		this.policyAddress = policyAddress;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getSettlementType() {
		return settlementType;
	}
	public void setSettlementType(Long settlementType) {
		this.settlementType = settlementType;
	}
	public String getInsuranceCompanyName() {
		return insuranceCompanyName;
	}
	public void setInsuranceCompanyName(String insuranceCompanyName) {
		this.insuranceCompanyName = insuranceCompanyName;
	}
	
}
