package com.liyang.domain.insuranceresult;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 承保结果详细数据
 * @author Administrator
 *
 */
@Entity
@Table(name="insurance_result_data")
public class InsuranceResultData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	//订单编号，即为报价编号
	@Column(name="order_id")
	private String orderId;   
	//出单车牌号码
	@Column(name="license_number")
	private String licenseNumber;  
	//状态(4-承保成功,5-承保失败)
	private Integer state;   
	//交强险保单号
	@Column(name="cil_policy_no")
	private String ciPolicyNo;   
	//商业险保单号
	@Column(name="bi_policy_no")
	private String biPolicyNo;     

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

	public String getCiPolicyNo() {
		return ciPolicyNo;
	}

	public void setCiPolicyNo(String ciPolicyNo) {
		this.ciPolicyNo = ciPolicyNo;
	}

	public String getBiPolicyNo() {
		return biPolicyNo;
	}

	public void setBiPolicyNo(String biPolicyNo) {
		this.biPolicyNo = biPolicyNo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
