package com.liyang.client.tianan;

import com.alibaba.fastjson.annotation.JSONField;
import com.liyang.client.BaseResult;
import com.liyang.client.BaseResultDto;
import com.liyang.client.IResult;
import com.liyang.client.strategy.IPolicyInfo;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * @author Administrator
 *
 */
public class ResultInsureConfirmation extends BaseResult implements IResult, IPolicyInfo {
	private BaseResultDto resultDTO;
	private String orderId;

	/**
	 * 返回结果
	 * 
	 * @return the resultDTO
	 */
	public BaseResultDto getResultDTO() {
		return resultDTO;
	}

	/**
	 * 返回结果
	 * 
	 * @param resultDTO
	 *            the resultDTO to set
	 */
	public void setResultDTO(BaseResultDto resultDTO) {
		this.resultDTO = resultDTO;
	}

	private String cityCode;

	/**
	 * 城市代码
	 * 
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * 城市代码
	 * 
	 * @param cityCode
	 *            the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	private String dealFlag;

	/**
	 * 处理结果标志
	 * 
	 * @return the dealFlag
	 */
	public String getDealFlag() {
		return dealFlag;
	}

	/**
	 * 处理结果标志
	 * 
	 * @param dealFlag
	 *            the dealFlag to set
	 */
	public void setDealFlag(String dealFlag) {
		this.dealFlag = dealFlag;
	}

	private String dealMassage;

	/**
	 * 处理结果信息
	 * 
	 * @return the dealMassage
	 */
	public String getDealMassage() {
		return dealMassage;
	}

	/**
	 * 处理结果信息
	 * 
	 * @param dealMassage
	 *            the dealMassage to set
	 */
	public void setDealMassage(String dealMassage) {
		this.dealMassage = dealMassage;
	}

	private String policyNo;

	/**
	 * 保单号
	 * 
	 * @return
	 */
	public String getPolicyNo() {
		return policyNo;
	}

	/**
	 * 保单号
	 * 
	 * @param policyNo
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

	private String busProposalNo;

	/**
	 * 商业险投保单号
	 * 
	 * @return the busProposalNo
	 */
	public String getBusProposalNo() {
		return busProposalNo;
	}

	/**
	 * 商业险投保单号
	 * 
	 * @param busProposalNo
	 *            the busProposalNo to set
	 */
	public void setBusProposalNo(String busProposalNo) {
		this.busProposalNo = busProposalNo;
	}

	private String busUnderWriteFlag;

	/**
	 * 商业险核保状态
	 * 
	 * @return the busUnderWriteFlag
	 */
	public String getBusUnderWriteFlag() {
		return busUnderWriteFlag;
	}

	/**
	 * 商业险核保状态
	 * 
	 * @param busUnderWriteFlag
	 *            the busUnderWriteFlag to set
	 */
	public void setBusUnderWriteFlag(String busUnderWriteFlag) {
		this.busUnderWriteFlag = busUnderWriteFlag;
	}

	private String bzProposalNo;

	/**
	 * 交强险投保单号
	 * 
	 * @return the bzProposalNo
	 */
	public String getBzProposalNo() {
		return bzProposalNo;
	}

	/**
	 * 交强险投保单号
	 * 
	 * @param bzProposalNo
	 *            the bzProposalNo to set
	 */
	public void setBzProposalNo(String bzProposalNo) {
		this.bzProposalNo = bzProposalNo;
	}

	private String bzUnderWriteFlag;

	/**
	 * 交强险核保状态
	 * 
	 * @return the bzUnderWriteFlag
	 */
	public String getBzUnderWriteFlag() {
		return bzUnderWriteFlag;
	}

	/**
	 * 交强险核保状态
	 * 
	 * @param bzUnderWriteFlag
	 *            the bzUnderWriteFlag to set
	 */
	public void setBzUnderWriteFlag(String bzUnderWriteFlag) {
		this.bzUnderWriteFlag = bzUnderWriteFlag;
	}

	private String evalLevel;

	/**
	 * 风险等级
	 * 
	 * @return the evalLevel
	 */
	public String getEvalLevel() {
		return evalLevel;
	}

	/**
	 * 风险等级
	 * 
	 * @param evalLevel
	 *            the evalLevel to set
	 */
	public void setEvalLevel(String evalLevel) {
		this.evalLevel = evalLevel;
	}

	private String rcldProposalNo;

	/**
	 * 人身险投保单号
	 * 
	 * @return the rcldProposalNo
	 */
	public String getRcldProposalNo() {
		return rcldProposalNo;
	}

	/**
	 * 人身险投保单号
	 * 
	 * @param rcldProposalNo
	 *            the rcldProposalNo to set
	 */
	public void setRcldProposalNo(String rcldProposalNo) {
		this.rcldProposalNo = rcldProposalNo;
	}

	private String scxlProposalNo;

	/**
	 * 联动随车行李险投保单号
	 * 
	 * @return the scxlProposalNo
	 */
	public String getScxlProposalNo() {
		return scxlProposalNo;
	}

	/**
	 * 联动随车行李险投保单号
	 * 
	 * @param scxlProposalNo
	 *            the scxlProposalNo to set
	 */
	public void setScxlProposalNo(String scxlProposalNo) {
		this.scxlProposalNo = scxlProposalNo;
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {

	}

	@JSONField(serialize = false)
	public boolean isSuccess() {
		return resultDTO != null && resultDTO.isSuccess() && "1".equals(dealFlag);
	}

	@JSONField(serialize = false)
	public String getErrorMess() {
		String errorMess = "";
		// 首先确认没有成功
		if (!isSuccess()) { 
			// 优先返回resultDTO里的错误
			if (resultDTO != null && !resultDTO.isSuccess()) { 
				errorMess = resultDTO.getResultMess();
				// 再返回dealMessage的错误信息
			} else if (!"1".equals(dealFlag)) {
				errorMess = dealMassage;
			} else {
				// resultDTO为null
				errorMess = "系统异常";
			}
		}
		return errorMess;
	}

	/**
	 * @return the orderId
	 */
	@Override
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
