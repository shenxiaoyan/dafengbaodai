package com.liyang.client.tianan.dto;

import java.util.List;

import com.liyang.client.IDto;
import com.liyang.client.tianan.TypeDate;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * 返回投保单列表信息
 * 
 * @author Administrator
 *
 */
public class ProposalDataDto implements IDto {
	private String handleText;

	/**
	 * 核保处理意见
	 * 
	 * @return the handleText
	 */
	public String getHandleText() {
		return handleText;
	}

	/**
	 * 核保处理意见
	 * 
	 * @param handleText
	 *            the handleText to set
	 */
	public void setHandleText(String handleText) {
		this.handleText = handleText;
	}

	private String inputTime;

	/**
	 * 录单时间
	 * 
	 * @return the inputTime
	 */
	public String getInputTime() {
		return inputTime;
	}

	/**
	 * 录单时间
	 * 
	 * @param inputTime
	 *            the inputTime to set
	 */
	public void setInputTime(String inputTime) {
		this.inputTime = inputTime;
	}

	private String policyNo;

	/**
	 * 保单号
	 * 
	 * @return the policyNo
	 */
	public String getPolicyNo() {
		return policyNo;
	}

	/**
	 * 保单号
	 * 
	 * @param policyNo
	 *            the policyNo to set
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

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

	private String rcldProposalNo;

	/**
	 * 人车联动人身险投保单号
	 * 
	 * @return the rcldProposalNo
	 */
	public String getRcldProposalNo() {
		return rcldProposalNo;
	}

	/**
	 * 人车联动人身险投保单号
	 * 
	 * @param rcldProposalNo
	 *            the rcldProposalNo to set
	 */
	public void setRcldProposalNo(String rcldProposalNo) {
		this.rcldProposalNo = rcldProposalNo;
	}

	private String rcldUnderWriteInd;

	/**
	 * 人车联动人身险投保单核保状态
	 * 
	 * @return the rcldUnderWriteInd
	 */
	public String getRcldUnderWriteInd() {
		return rcldUnderWriteInd;
	}

	/**
	 * 人车联动人身险投保单核保状态
	 * 
	 * @param rcldUnderWriteInd
	 *            the rcldUnderWriteInd to set
	 */
	public void setRcldUnderWriteInd(String rcldUnderWriteInd) {
		this.rcldUnderWriteInd = rcldUnderWriteInd;
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

	private List<RiskDTO> riskList;

	/**
	 * 险种详细信息列表
	 * 
	 * @return the riskList
	 */
	public List<RiskDTO> getRiskList() {
		return riskList;
	}

	/**
	 * 险种详细信息列表
	 * 
	 * @param riskList
	 *            the riskList to set
	 */
	public void setRiskList(List<RiskDTO> riskList) {
		this.riskList = riskList;
	}

	private String riskName;

	/**
	 * 险种名称
	 * 
	 * @return the riskName
	 */
	public String getRiskName() {
		return riskName;
	}

	/**
	 * 险种名称
	 * 
	 * @param riskName
	 *            the riskName to set
	 */
	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}

	private String status;

	/**
	 * 处理状态
	 * 
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 处理状态
	 * 
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	private TypeDate undwrtTime;

	/**
	 * 核保通过时间
	 * 
	 * @return the undwrtTime
	 */
	public TypeDate getUndwrtTime() {
		return undwrtTime;
	}

	/**
	 * 核保通过时间
	 * 
	 * @param undwrtTime
	 *            the undwrtTime to set
	 */
	public void setUndwrtTime(TypeDate undwrtTime) {
		this.undwrtTime = undwrtTime;
	}

	private TypeDate acceptdate;

	/**
	 * 承保确认时间
	 * 
	 * @return the acceptdate
	 */
	public TypeDate getAcceptdate() {
		return acceptdate;
	}

	/**
	 * 承保确认时间
	 * 
	 * @param acceptdate
	 *            the acceptdate to set
	 */
	public void setAcceptdate(TypeDate acceptdate) {
		this.acceptdate = acceptdate;
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {

	}
}
