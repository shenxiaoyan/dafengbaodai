package com.liyang.domain.underwritingresult;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.liyang.util.JsonParserUtils;

import net.sf.json.JSONObject;

/**
 * 核保结果推送数据
 * @author Administrator
 *
 */
@Entity
@Table(name = "underwriting_result_data")
public class UnderwritingResultData implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	// 订单编号
	@Column(name = "order_id")
	private String orderId; 
	// 核保车牌号码
	@Column(name = "license_number")
	private String licenseNumber; 
	// 状态(2-核保成功,3-核保失败,15-人工核保中,16-核保通过待确认)
	private int state; 
	// 核保总价,单位(分)
	@Column(name = "underwriting_price_cent")
	private int underwritingPriceCent; 
	// 核保信息 ,该字段为字符串 之前为对象,现修改
	@Column(name = "under_writing_json", columnDefinition = "varchar(256) default \"\" ", length = 2000)
	private String underwritingJson; 

	
	/**
	 * 天安新增字段 
	 */
	// 商业险核保状态
	private String busUnderWriteFlag;
	// 交强险核保状态
	private String bzUnderWriteFlag;
	// 风险等级
	private String evalLevel;
	// 投保单号
	private String proposalNo;
	// 保单号
	private String policyNo;

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getBusUnderWriteFlag() {
		return busUnderWriteFlag;
	}

	public void setBusUnderWriteFlag(String busUnderWriteFlag) {
		this.busUnderWriteFlag = busUnderWriteFlag;
	}

	public String getBzUnderWriteFlag() {
		return bzUnderWriteFlag;
	}

	public void setBzUnderWriteFlag(String bzUnderWriteFlag) {
		this.bzUnderWriteFlag = bzUnderWriteFlag;
	}

	public String getEvalLevel() {
		return evalLevel;
	}

	public void setEvalLevel(String evalLevel) {
		this.evalLevel = evalLevel;
	}

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

	public int getUnderwritingPriceCent() {
		return underwritingPriceCent;
	}

	public void setUnderwritingPriceCent(int underwritingPriceCent) {
		this.underwritingPriceCent = underwritingPriceCent;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JSONObject getUnderwritingJson() {
		return JsonParserUtils.jsonTrans(underwritingJson);
	}

	public void setUnderwritingJson(String underwritingJson) {
		this.underwritingJson = underwritingJson;
	}

}
