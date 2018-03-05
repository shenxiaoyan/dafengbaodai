package com.liyang.domain.querylatestpolicy;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 交强险续保信息
 * @author Administrator
 *
 */
@Entity
@Table(name="query_latest_policy_ci_info")
public class QueryLatestPolicyResultCiInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	//保单号
	@Column(name="policy_no")
	private String policyNo;  
//	@Column(name="license_number")
//	private String licenseNumber;  //车牌号码　　　实际返回数据无该字段
//	@Column(name="company_id")
//	private int companyId;  //保险公司ID   实际返回数据无该字段
//	@Column(name="company_name")
//	private String companyName;  //保险公司名称　　实际返回数据无该字段
	@Column(name="plan_code")
	private String planCode;   
	//保险公司名称
	@Column(name="insurance_company")
	private String  insuranceCompany;  
	//保险公司id
	@Column(name="nsurance_company_id")
	private int insuranceCompanyId;   
	//起保日期
	@Column(name="insurance_begin_time")
	private String insuranceBeginTime; 
	//终保日期
	@Column(name="insurance_end_time")
	private String insuranceEndTime;  
	 //总价格
	@Column(name="total_amount")
	private String totalAmount;   
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
//	public String getLicenseNumber() {
//		return licenseNumber;
//	}
//	public void setLicenseNumber(String licenseNumber) {
//		this.licenseNumber = licenseNumber;
//	}
//	public int getCompanyId() {
//		return companyId;
//	}
//	public void setCompanyId(int companyId) {
//		this.companyId = companyId;
//	}
//	public String getCompanyName() {
//		return companyName;
//	}
//	public void setCompanyName(String companyName) {
//		this.companyName = companyName;
//	}
	public String getInsuranceBeginTime() {
		return insuranceBeginTime;
	}
	public void setInsuranceBeginTime(String insuranceBeginTime) {
		this.insuranceBeginTime = insuranceBeginTime;
	}
	public String getInsuranceEndTime() {
		return insuranceEndTime;
	}
	public void setInsuranceEndTime(String insuranceEndTime) {
		this.insuranceEndTime = insuranceEndTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	public String getInsuranceCompany() {
		return insuranceCompany;
	}
	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}
	public int getInsuranceCompanyId() {
		return insuranceCompanyId;
	}
	public void setInsuranceCompanyId(int insuranceCompanyId) {
		this.insuranceCompanyId = insuranceCompanyId;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
}
