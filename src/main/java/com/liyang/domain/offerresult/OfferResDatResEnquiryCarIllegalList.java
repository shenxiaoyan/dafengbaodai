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
 * 询价车辆违章信息
 * @author Administrator
 *
 */
@Entity
@Table(name="offer_result_enquiry_car_illegal_list")
public class OfferResDatResEnquiryCarIllegalList implements Serializable {
private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;
	
	@JsonIgnore
	@ManyToMany(cascade=CascadeType.ALL,mappedBy="enquiryCarIllegalList")
	private Set<OfferResultDataResult> result;
	
	/**
	 * 违章信息，以下字段报价结构需解析
	 */
	//车牌号   
	@Column(name="license_number")
	@Info(label="违章记录车牌号",placeholder="",tip="",help="",secret="")
	private String  licenseNumber;  
	//违章行为
	@Column(name="illegal_behavior")
	@Info(label="违章记录违章行为",placeholder="",tip="",help="",secret="")
	private String illegalBehavior; 
	// 违章时间
	@Column(name="illegal_time")
	@Info(label="违章记录违章时间",placeholder="",tip="",help="",secret="")
	private Long illegalTime;   
	//发生地点
	@Column(name="illegal_address")
	@Info(label="违章记录发生地点",placeholder="",tip="",help="",secret="")
	private String illegalAddress;  
	//结案时间
	@Column(name="illegal_dispose_time")
	@Info(label="违章记录结案时间",placeholder="",tip="",help="",secret="")
	private Long illegalDisposeTime;   
	
	/**
	 * 以下字段。解析是可忽略
	 */
	@Column(name="customer_car_id")
	private Integer customerCarId;
	@Column(name="enquiry_id")
	private String	enquiryId;
	@Column(name="offer_id")
	private String offerId;
	@Column(name="illegal_no")
	private String illegalNo;
	@Column(name="illegal_behavior_type")
	private String illegalBehaviorType;
	@Column(name="decisio_no")
	private String decisionNo;
	private Integer state;
	@Column(name="“create_time")
	private Long createTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	public String getIllegalBehavior() {
		return illegalBehavior;
	}
	public void setIllegalBehavior(String illegalBehavior) {
		this.illegalBehavior = illegalBehavior;
	}
	public Long getIllegalTime() {
		return illegalTime;
	}
	public void setIllegalTime(Long illegalTime) {
		this.illegalTime = illegalTime;
	}
	public String getIllegalAddress() {
		return illegalAddress;
	}
	public void setIllegalAddress(String illegalAddress) {
		this.illegalAddress = illegalAddress;
	}
	public Long getIllegalDisposeTime() {
		return illegalDisposeTime;
	}
	public void setIllegalDisposeTime(Long illegalDisposeTime) {
		this.illegalDisposeTime = illegalDisposeTime;
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
	public String getIllegalNo() {
		return illegalNo;
	}
	public void setIllegalNo(String illegalNo) {
		this.illegalNo = illegalNo;
	}
	public String getIllegalBehaviorType() {
		return illegalBehaviorType;
	}
	public void setIllegalBehaviorType(String illegalBehaviorType) {
		this.illegalBehaviorType = illegalBehaviorType;
	}
	public String getDecisionNo() {
		return decisionNo;
	}
	public void setDecisionNo(String decisionNo) {
		this.decisionNo = decisionNo;
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
	public Set<OfferResultDataResult> getResult() {
		return result;
	}
	public void setResult(Set<OfferResultDataResult> result) {
		this.result = result;
	}

}
