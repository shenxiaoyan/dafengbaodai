package com.liyang.domain.temporaryresult;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 暂存结果数据
 * @author Administrator
 *
 */
@Entity
@Table(name="temporary_result_data")
public class Data implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	//订单编号
	@Column(name="order_id")
	private String orderId;  
	//暂存车牌号码
	@Column(name="license_number")
	private String licenseNumber;  
	//状态(13-暂存成功,14-暂存失败)
	private int state;   
	
	private String type;
	//暂存信息
	@OneToOne(cascade=CascadeType.ALL,targetEntity=ProposalJson.class)
	@JoinColumn(name="temporary_result_proposal_json_id")
	private ProposalJson proposalJson;   

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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ProposalJson getProposalJson() {
		return proposalJson;
	}

	public void setProposalJson(ProposalJson proposalJson) {
		this.proposalJson = proposalJson;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
