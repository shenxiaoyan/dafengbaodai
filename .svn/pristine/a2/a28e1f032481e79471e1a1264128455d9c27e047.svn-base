package com.liyang.domain.account;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 账户信息
 * 
 * @author Administrator
 *
 */
@Table(name = "account")
@Entity
public class Account implements Serializable {
	
	private static final long serialVersionUID = 54L;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private int id;

	/**
	 * 真实姓名
	 */
	@NotNull(message = "请填写真实姓名")
	private String realName;

	// @NotNull(message="手机号码不能为空")
	// @Pattern(regexp="^1\\d{10}$",message="手机号码格式不正确")
	/**
	 * 手机号码
	 */
	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	// @NotNull(message="支付平台不能为空")
	// @Pattern(regexp="支付宝",message="现在支持的平台有支付宝")
	/**
	 * 打款账户平台
	 */
	private String payAccountPlatform;
	/**
	 * 打款账户号
	 */
//	@NotNull(message = "支付账号不能为空")
	private String payAccountId;
	/**
	 * 账户余额
	 */
	// @Min(value=0,message="账户余额不能小于0")
	private double balance;
	/**
	 * 历史总额
	 */
	// @Min(value=0,message="账户余额不能小于0")
	private double totalBalance;
	/**
	 * 账户状态 0是正常 1是冻结
	 */
	// @Max(value=1,message="账户状态只能是1或0")
	// @Min(value=0,message="账户状态只能是1或0")
	private int status;
	/**
	 * 身份证号
	 */
	@Pattern(regexp = "^\\d{15}$|^\\d{17}[0-9Xx]$", message = "请输入正确的身份证号")
	private String identity;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	private Date updateTime;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// @Column(length=11)
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	// @Column(length=20)
	public String getPayAccountPlatform() {
		return payAccountPlatform;
	}

	public void setPayAccountPlatform(String payAccountPlatform) {
		this.payAccountPlatform = payAccountPlatform;
	}

	// @Column(length=30)
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

	// @Column(length=20)
	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

}
