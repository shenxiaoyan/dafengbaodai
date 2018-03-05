package com.liyang.client.tianan.dto;

import com.liyang.client.IDto;
import com.liyang.client.tianan.TypeDate;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * 险种详细信息列表
 * 
 * @author Administrator
 *
 */
public class RiskDTO implements IDto {
	private String proposalNo;

	/**
	 * 投保单号
	 * 
	 * @return the proposalNo
	 */
	public String getProposalNo() {
		return proposalNo;
	}

	/**
	 * 投保单号
	 * 
	 * @param proposalNo
	 *            the proposalNo to set
	 */
	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	private String subProposalNo;

	/**
	 * 分投保单号
	 * 
	 * @return the subProposalNo
	 */
	public String getSubProposalNo() {
		return subProposalNo;
	}

	/**
	 * 分投保单号
	 * 
	 * @param subProposalNo
	 *            the subProposalNo to set
	 */
	public void setSubProposalNo(String subProposalNo) {
		this.subProposalNo = subProposalNo;
	}

	private String subPolicyNo;

	/**
	 * 分保单号
	 * 
	 * @return the subPolicyNo
	 */
	public String getSubPolicyNo() {
		return subPolicyNo;
	}

	/**
	 * 分保单号
	 * 
	 * @param subPolicyNo
	 *            the subPolicyNo to set
	 */
	public void setSubPolicyNo(String subPolicyNo) {
		this.subPolicyNo = subPolicyNo;
	}

	private String planCode;

	/**
	 * 计划代码
	 * 
	 * @return the planCode
	 */
	public String getPlanCode() {
		return planCode;
	}

	/**
	 * 计划代码
	 * 
	 * @param planCode
	 *            the planCode to set
	 */
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	private String riskCode;

	/**
	 * 险种编码
	 * 
	 * @return the riskCode
	 */
	public String getRiskCode() {
		return riskCode;
	}

	/**
	 * 险种编码
	 * 
	 * @param riskCode
	 *            the riskCode to set
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	private TypeDate startDate;

	/**
	 * 起保日期
	 * 
	 * @return the startDate
	 */
	public TypeDate getStartDate() {
		return startDate;
	}

	/**
	 * 起保日期
	 * 
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(TypeDate startDate) {
		this.startDate = startDate;
	}

	private TypeDate endDate;

	/**
	 * 终保日期
	 * 
	 * @return the endDate
	 */
	public TypeDate getEndDate() {
		return endDate;
	}

	/**
	 * 终保日期
	 * 
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(TypeDate endDate) {
		this.endDate = endDate;
	}

	private String commenceDate;

	/**
	 * 投保公司保单开始日
	 * 
	 * @return the commenceDate
	 */
	public String getCommenceDate() {
		return commenceDate;
	}

	/**
	 * 投保公司保单开始日
	 * 
	 * @param commenceDate
	 *            the commenceDate to set
	 */
	public void setCommenceDate(String commenceDate) {
		this.commenceDate = commenceDate;
	}

	private String uwYear;

	/**
	 * 承保年度
	 * 
	 * @return the uwYear
	 */
	public String getUwYear() {
		return uwYear;
	}

	/**
	 * 承保年度
	 * 
	 * @param uwYear
	 *            the uwYear to set
	 */
	public void setUwYear(String uwYear) {
		this.uwYear = uwYear;
	}

	private String currency;

	/**
	 * 保单币别
	 * 
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * 保单币别
	 * 
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	private String sumInsured;

	/**
	 * 险种总保额
	 * 
	 * @return the sumInsured
	 */
	public String getSumInsured() {
		return sumInsured;
	}

	/**
	 * 险种总保额
	 * 
	 * @param sumInsured
	 *            the sumInsured to set
	 */
	public void setSumInsured(String sumInsured) {
		this.sumInsured = sumInsured;
	}

	private String sumGrossPremium;

	/**
	 * 险种总毛保费
	 * 
	 * @return the sumGrossPremium
	 */
	public String getSumGrossPremium() {
		return sumGrossPremium;
	}

	/**
	 * 险种总毛保费
	 * 
	 * @param sumGrossPremium
	 *            the sumGrossPremium to set
	 */
	public void setSumGrossPremium(String sumGrossPremium) {
		this.sumGrossPremium = sumGrossPremium;
	}

	private String sumNetPremium;

	/**
	 * 险种总净保费
	 * 
	 * @return the sumNetPremium
	 */
	public String getSumNetPremium() {
		return sumNetPremium;
	}

	/**
	 * 险种总净保费
	 * 
	 * @param sumNetPremium
	 *            the sumNetPremium to set
	 */
	public void setSumNetPremium(String sumNetPremium) {
		this.sumNetPremium = sumNetPremium;
	}

	private String sumUWPremium;

	/**
	 * 险种总承保保费
	 * 
	 * @return the sumUWPremium
	 */
	public String getSumUWPremium() {
		return sumUWPremium;
	}

	/**
	 * 险种总承保保费
	 * 
	 * @param sumUWPremium
	 *            the sumUWPremium to set
	 */
	public void setSumUWPremium(String sumUWPremium) {
		this.sumUWPremium = sumUWPremium;
	}

	private String validInd;

	/**
	 * 有效标志
	 * 
	 * @return the validInd
	 */
	public String getValidInd() {
		return validInd;
	}

	/**
	 * 有效标志
	 * 
	 * @param validInd
	 *            the validInd to set
	 */
	public void setValidInd(String validInd) {
		this.validInd = validInd;
	}

	private String effectFlag;

	/**
	 * 即时生效标志
	 * 
	 * @return the effectFlag
	 */
	public String getEffectFlag() {
		return effectFlag;
	}

	/**
	 * 即时生效标志
	 * 
	 * @param effectFlag
	 *            the effectFlag to set
	 */
	public void setEffectFlag(String effectFlag) {
		this.effectFlag = effectFlag;
	}

	private String sumDiscount;

	/**
	 * 全单折扣
	 * 
	 * @return the sumDiscount
	 */
	public String getSumDiscount() {
		return sumDiscount;
	}

	/**
	 * 全单折扣
	 * 
	 * @param sumDiscount
	 *            the sumDiscount to set
	 */
	public void setSumDiscount(String sumDiscount) {
		this.sumDiscount = sumDiscount;
	}

	private String specialReinsInd;

	/**
	 * 特殊分保标志
	 * 
	 * @return the specialReinsInd
	 */
	public String getSpecialReinsInd() {
		return specialReinsInd;
	}

	/**
	 * 特殊分保标志
	 * 
	 * @param specialReinsInd
	 *            the specialReinsInd to set
	 */
	public void setSpecialReinsInd(String specialReinsInd) {
		this.specialReinsInd = specialReinsInd;
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {

	}
}
