package com.liyang.domain.platform;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.CreatedDate;

import com.liyang.annotation.Info;
import com.liyang.domain.createenquiry.CreateEnquiry;
import com.liyang.domain.offerresult.OfferResult;
import com.liyang.domain.submitproposal.SubmitProposalParams;
import com.liyang.domain.submitproposal.SubmitProposal;
import com.liyang.domain.submitproposal.SubmitProposalFile;
import com.liyang.domain.underwritingresult.UnderwritingResult;


/**
 * 平台
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
@Entity
@Table(name="platform",indexes={@Index(name="platform_application_id",columnList="application_id",unique=true)})
public class Platform implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	//签署公司标识
	@Column(name="application_id")
	private String applicationId;   
	//签署公司名称
	@Column(name="company_name")
	private String companyName;   
	//公司地址
	@Column(name="company_address")
	private String companyAddress;  
	
	@CreatedDate
	@Column(name="create_time")
	private Date createTime;
	//联系人
	@Column(name="contact_name")
	private String contactName;   
	//联系人电话
	@Column(name="contact_phone")
	private String contactPhone;  
	//询价的次数
	@Column(name="quiry_frequency",nullable=false,columnDefinition="INT default 0")
	private int quiryFrequency;  
	//成交量的次数
	@Column(name="clinch_frequency",nullable=false,columnDefinition="INT default 0")
	private int	clinchFrequency;   
	//平台的关闭与开启，1开启、0关闭
	private String enable;   
	//报价结果推送给平台的URL
	@Column(name="platform_offer_result_url")
	private String platformOfferResultURL;     
	//暂存结果推送给平台的URL
	@Column(name="platform_temporary_result_url")
	private String platformTemporaryResultURL;   
	//核保结果推送给平台的URL
	@Column(name="platform_underwriting_result_url")
	private String platformUnderwritingResultURL;   
	//承保结果推送给平台的URL
	@Column(name="platform_insurance_result_url")
	private String platformInsuranceResultURL;   
	// 付款结果推送给平台的URL
	@Column(name="platform_pay_result_url")
	private String platformPayResultURL;
	//版本号
	@Column(name="app_version")
	private String appVersion;  
	
	@OneToMany(cascade=CascadeType.ALL,targetEntity=SubmitProposalFile.class,mappedBy="platform")
	private Set<SubmitProposalFile> submitProposalFile;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="platform")
	private Set<UnderwritingResult> underwritingResults;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public int getQuiryFrequency() {
		return quiryFrequency;
	}
	public void setQuiryFrequency(int quiryFrequency) {
		this.quiryFrequency = quiryFrequency;
	}
	public int getClinchFrequency() {
		return clinchFrequency;
	}
	public void setClinchFrequency(int clinchFrequency) {
		this.clinchFrequency = clinchFrequency;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public String getPlatformOfferResultURL() {
		return platformOfferResultURL;
	}
	public void setPlatformOfferResultURL(String platformOfferResultURL) {
		this.platformOfferResultURL = platformOfferResultURL;
	}
	public String getPlatformTemporaryResultURL() {
		return platformTemporaryResultURL;
	}
	public void setPlatformTemporaryResultURL(String platformTemporaryResultURL) {
		this.platformTemporaryResultURL = platformTemporaryResultURL;
	}
	public String getPlatformUnderwritingResultURL() {
		return platformUnderwritingResultURL;
	}
	public void setPlatformUnderwritingResultURL(String platformUnderwritingResultURL) {
		this.platformUnderwritingResultURL = platformUnderwritingResultURL;
	}
	public String getPlatformInsuranceResultURL() {
		return platformInsuranceResultURL;
	}
	public void setPlatformInsuranceResultURL(String platformInsuranceResultURL) {
		this.platformInsuranceResultURL = platformInsuranceResultURL;
	}
	public Set<SubmitProposalFile> getSubmitProposalFile() {
		return submitProposalFile;
	}
	public void setSubmitProposalFile(Set<SubmitProposalFile> submitProposalFile) {
		this.submitProposalFile = submitProposalFile;
	}
	public String getPlatformPayResultURL() {
		return platformPayResultURL;
	}
	public void setPlatformPayResultURL(String platformPayResultURL) {
		this.platformPayResultURL = platformPayResultURL;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public Set<UnderwritingResult> getUnderwritingResults() {
		return underwritingResults;
	}
	public void setUnderwritingResults(Set<UnderwritingResult> underwritingResults) {
		this.underwritingResults = underwritingResults;
	}

	
	
}