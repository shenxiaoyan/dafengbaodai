package com.liyang.domain.account;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 账户信息VO
 * 
 * @author Adam
 *
 */
public class AccountVO{

	private String realName;

	private String phone;

	private String payAccountPlatform;

	private String payAccountId;

	private double balance;

	private double totalBalance;

	private int status;

	@Pattern(regexp = "^\\d{15}$|^\\d{17}[0-9Xx]$", message = "请输入正确的身份证号")
	private String identity;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime;
	
	private Boolean bankcardBinded;
	
	@NotBlank(message = "银行卡号不能为空")
	private String bankcardNo;
	
//	@NotBlank(message = "开户行不能为空")
	private String bankName;
	
	@NotBlank(message = "开户行支行不能为空")
	private String subbranch;
	
	private String bankcardName;
	
	private String bankIcon;
	
	private String verificationCode;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPayAccountPlatform() {
		return payAccountPlatform;
	}

	public void setPayAccountPlatform(String payAccountPlatform) {
		this.payAccountPlatform = payAccountPlatform;
	}

	public String getPayAccountId() {
		return payAccountId;
	}

	public void setPayAccountId(String payAccountId) {
		this.payAccountId = payAccountId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(double totalBalance) {
		this.totalBalance = totalBalance;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean getBankcardBinded() {
		return bankcardBinded;
	}

	public void setBankcardBinded(Boolean bankcardBinded) {
		this.bankcardBinded = bankcardBinded;
	}

	public String getBankcardNo() {
		return bankcardNo;
	}

	public void setBankcardNo(String bankcardNo) {
		this.bankcardNo = bankcardNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getSubbranch() {
		return subbranch;
	}

	public void setSubbranch(String subbranch) {
		this.subbranch = subbranch;
	}

	public String getBankcardName() {
		return bankcardName;
	}

	public void setBankcardName(String bankcardName) {
		this.bankcardName = bankcardName;
	}

	public String getBankIcon() {
		return bankIcon;
	}

	public void setBankIcon(String bankIcon) {
		this.bankIcon = bankIcon;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	
}
