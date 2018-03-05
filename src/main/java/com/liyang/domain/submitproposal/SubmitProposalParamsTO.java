package com.liyang.domain.submitproposal;

import java.util.Date;

import com.liyang.domain.platform.Platform;

/**
 * @author Adam
 * @version 创建时间：2018年1月29日 下午6:29:50 类说明
 */
public class SubmitProposalParamsTO {
	private Integer id;
	private String orderId; // 由报价中的编号提供
	private String insuredName; // 被保人姓名
	private String insuredIdNo; // 被保人身份证号
	private String insuredPhone; // 被保人联系方式
	private String customerName; // 投保人姓名
	private String customerPhone; // 投保人联系方式
	private String customerIdNo; // 投保人身份证
	private String contactName; // 投保人姓名
	private String dafengContactName; // 投保人姓名
	private String contactPhone; // 收件人联系方式
	private String dafengContactPhone; // 大蜂配送信息-联系方式
	private String ownerIdCard; // 车主身份证
	private String ownerMobilePhone;
	private String ownerName; // 车主姓名
	private String ownerAddress; // 车主身份证地址
	private String applicantAddress; // 投保人身份证地址
	private String insuredAddress; // 被保 人身份证地址
	private String dafengAddress; // 大蜂配送信息-地址
	private Date addressExpireTime; // 地址修改过期时间
	private Boolean addressMofied = false; // 地址是否修改过
	private SubProParamsContactAddress contactAddress; // 收件人地址
	private SubProParamsImageJson imageJson; // 证件照片
	private SubProParamsInvoiceInfo invoiceInfo; // // 发票信息
	private Date createdAt;
	private Date lastModifiedAt;
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

	public String getDafengContactName() {
		return dafengContactName;
	}

	public void setDafengContactName(String dafengContactName) {
		this.dafengContactName = dafengContactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getDafengContactPhone() {
		return dafengContactPhone;
	}

	public void setDafengContactPhone(String dafengContactPhone) {
		this.dafengContactPhone = dafengContactPhone;
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

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

}
