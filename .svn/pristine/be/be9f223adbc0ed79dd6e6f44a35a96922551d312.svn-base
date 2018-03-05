package com.liyang.domain.querypayment;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 第三方支付
 * 
 * @author Administrator
 *
 */
@Entity
public class QueryPay {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private int id;

	private Date createTime;
	private String orderId;
	private String response;
	private String licenseNumber;
	// 小马中 10：支付成功，2：未支付;天安中，0：未支付，1：已支付
	private Integer state;
	private String message;
	// 同天安接口的bankTradeDate，为交易时间
	private Date returnTime;
	// 天安多的字段
	// 第三方支付id
	private String paymentId;
	// 订单金额，单位：元
	private Double fee;
	// 扩展字段1，支付完成后按照原样返回
	private String ext1;
	// 扩展字段2，支付完成后按照原样返回
	private String ext2;
	// 上海平台交易号
	private String payNo;
	// 保单号，车险：大保单号，非车险：保单号
	private String policyNo;
	// 车险商业险保单号
	private String bizPolicyNo;
	// 车险交强险保单号
	private String forcePolicyNo;
	// 人身险保单号
	private String rcldPolicyNo;

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getBizPolicyNo() {
		return bizPolicyNo;
	}

	public void setBizPolicyNo(String bizPolicyNo) {
		this.bizPolicyNo = bizPolicyNo;
	}

	public String getForcePolicyNo() {
		return forcePolicyNo;
	}

	public void setForcePolicyNo(String forcePolicyNo) {
		this.forcePolicyNo = forcePolicyNo;
	}

	public String getRcldPolicyNo() {
		return rcldPolicyNo;
	}

	public void setRcldPolicyNo(String rcldPolicyNo) {
		this.rcldPolicyNo = rcldPolicyNo;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
}
