package com.liyang.domain.submitproposal;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.liyang.annotation.Info;
import com.liyang.domain.platform.Platform;

/**
 * 订单提交参数
 * 
 * @author huanghengkun
 * @create 2017年12月17日
 */
@Entity
@Table(name = "submit_proposal_params")
public class SubmitProposalParams implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	// 由报价中的编号提供
	@Column(name = "order_id")
	private String orderId; 
	// 被保人姓名
	@Column(name = "insured_name")
	private String insuredName; 
	// 被保人身份证号
	@Column(name = "insured_idNo")
	private String insuredIdNo; 
	// 被保人联系方式
	@Column(name = "insured_phone")
	private String insuredPhone; 
	// 投保人姓名
	@Column(name = "customer_name")
	private String customerName;
	// 投保人联系方式
	@Column(name = "customer_phone")
	private String customerPhone; 
	// 投保人身份证
	@Column(name = "customer_idNo")
	private String customerIdNo; 
	// 投保人姓名
	@Column(name = "contact_name")
	private String contactName; 
	// 大蜂配送信息-联系人（投保人）
	private String dafengContactName;
	// 收件人联系方式
	@Column(name = "contact_phone")
	private String contactPhone; 
	// 大蜂配送信息-联系方式
	private String dafengContactPhone;
	// 车主身份证
	@Column(name = "owner_idCard")
	private String ownerIdCard; 
	@Column(name = "owner_mobilePhone")
	private String ownerMobilePhone;
	// 车主姓名
	@Column(name = "owner_name")
	private String ownerName; 
	@Column(name = "owner_address")
	@Info(label = "车主身份证地址")
	private String ownerAddress;
	@Column(name = "applicant_ddress")
	@Info(label = "投保人身份证地址")
	private String applicantAddress;
	@Column(name = "insured_address")
	@Info(label = "被保 人身份证地址")
	private String insuredAddress;
	// 大蜂配送信息-地址
	@Column(name="dafeng_address")
	@Info(label="大蜂配送地址")
	private String dafengAddress;
	@Column(name = "address_expire_time")
	@Info(label = "地址修改过期时间")
	private Date addressExpireTime;
	@Column(name = "address_modified")
	@Type(type = "yes_no")
	@Info(label = "地址是否修改过")
	private Boolean addressMofied = false;
	// 收件人地址
	@OneToOne(targetEntity = SubProParamsContactAddress.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "submit_proposal_contactAddress_id")
	private SubProParamsContactAddress contactAddress; 
	// 证件照片
	@OneToOne(targetEntity = SubProParamsImageJson.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "submit_proposal_imageJson_id")
	private SubProParamsImageJson imageJson; 
	// 发票信息
	@OneToOne(targetEntity = SubProParamsInvoiceInfo.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "submit_proposal_invoiceInfo_id")
	private SubProParamsInvoiceInfo invoiceInfo; 

	@CreatedDate
	@Column(name = "created_at")
	private Date createdAt;

	@LastModifiedDate
	@Column(name = "last_modified_at")
	private Date lastModifiedAt;

	@OneToOne(cascade = CascadeType.ALL, targetEntity = Platform.class)
	@JoinColumn(name = "platform_id")
	private Platform platform;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public String getInsuredIdNo() {
		return insuredIdNo;
	}

	public void setInsuredIdNo(String insuredIdNo) {
		this.insuredIdNo = insuredIdNo;
	}

	public String getInsuredPhone() {
		return insuredPhone;
	}

	public void setInsuredPhone(String insuredPhone) {
		this.insuredPhone = insuredPhone;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerIdNo() {
		return customerIdNo;
	}

	public void setCustomerIdNo(String customerIdNo) {
		this.customerIdNo = customerIdNo;
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

	public SubProParamsContactAddress getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(SubProParamsContactAddress contactAddress) {
		this.contactAddress = contactAddress;
	}

	public SubProParamsImageJson getImageJson() {
		return imageJson;
	}

	public void setImageJson(SubProParamsImageJson imageJson) {
		this.imageJson = imageJson;
	}

	public SubProParamsInvoiceInfo getInvoiceInfo() {
		return invoiceInfo;
	}

	public void setInvoiceInfo(SubProParamsInvoiceInfo invoiceInfo) {
		this.invoiceInfo = invoiceInfo;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	public String getOwnerIdCard() {
		return ownerIdCard;
	}

	public void setOwnerIdCard(String ownerIdCard) {
		this.ownerIdCard = ownerIdCard;
	}

	public String getOwnerMobilePhone() {
		return ownerMobilePhone;
	}

	public void setOwnerMobilePhone(String ownerMobilePhone) {
		this.ownerMobilePhone = ownerMobilePhone;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerAddress() {
		return ownerAddress;
	}

	public void setOwnerAddress(String ownerAddress) {
		this.ownerAddress = ownerAddress;
	}

	public String getApplicantAddress() {
		return applicantAddress;
	}

	public void setApplicantAddress(String applicantAddress) {
		this.applicantAddress = applicantAddress;
	}

	public String getInsuredAddress() {
		return insuredAddress;
	}

	public void setInsuredAddress(String insuredAddress) {
		this.insuredAddress = insuredAddress;
	}
	
	public String getDafengAddress() {
		return dafengAddress;
	}

	public void setDafengAddress(String dafengAddress) {
		this.dafengAddress = dafengAddress;
	}
	
	public Date getAddressExpireTime() {
		return addressExpireTime;
	}

	public void setAddressExpireTime(Date addressExpireTime) {
		this.addressExpireTime = addressExpireTime;
	}

	public Boolean getAddressMofied() {
		return addressMofied;
	}

	public void setAddressMofied(Boolean addressMofied) {
		this.addressMofied = addressMofied;
	}

	public String getDafengContactName() {
		return dafengContactName;
	}

	public void setDafengContactName(String dafengContactName) {
		this.dafengContactName = dafengContactName;
	}

	public String getDafengContactPhone() {
		return dafengContactPhone;
	}

	public void setDafengContactPhone(String dafengContactPhone) {
		this.dafengContactPhone = dafengContactPhone;
	}

	@Override
	public String toString() {
		return "SubmitProposal [id=" + id + ", orderId=" + orderId + ", insuredName=" + insuredName + ", insuredIdNo="
				+ insuredIdNo + ", insuredPhone=" + insuredPhone + ", customerName=" + customerName + ", customerPhone="
				+ customerPhone + ", customerIdNo=" + customerIdNo + ", contactName=" + contactName + ", contactPhone="
				+ contactPhone + ", ownerIdCard=" + ownerIdCard + ", ownerMobilePhone=" + ownerMobilePhone
				+ ", contactAddress=" + contactAddress + ", imageJson=" + imageJson + ", invoiceInfo=" + invoiceInfo
				+ ", createdAt=" + createdAt + ", lastModifiedAt=" + lastModifiedAt + "]";
	}

}
