package com.liyang.client.tianan;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.liyang.client.BaseResult;
import com.liyang.client.BaseResultDto;
import com.liyang.client.IResult;
import com.liyang.client.strategy.IUnderWriteResultInfo;
import com.liyang.client.tianan.dto.ProposalDataDto;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * @author Administrator
 *
 */
public class ResultQueryProposal extends BaseResult implements IResult, IUnderWriteResultInfo {
	private BaseResultDto resultDTO;

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

	private String dealFlag;

	/**
	 * 处理标志
	 * 
	 * @return the dealFlag
	 */
	public String getDealFlag() {
		return dealFlag;
	}

	/**
	 * 处理标志
	 * 
	 * @param dealFlag
	 *            the dealFlag to set
	 */
	public void setDealFlag(String dealFlag) {
		this.dealFlag = dealFlag;
	}

	private String dealMessage;

	/**
	 * 处理标志信息
	 * 
	 * @return the dealMessage
	 */
	public String getDealMessage() {
		return dealMessage;
	}

	/**
	 * 处理标志信息
	 * 
	 * @param dealMessage
	 *            the dealMessage to set
	 */
	public void setDealMessage(String dealMessage) {
		this.dealMessage = dealMessage;
	}

	private List<ProposalDataDto> proposalDataDtoList;

	/**
	 * 投保单号列表
	 * 
	 * @return the proposalDataDtoList
	 */
	public List<ProposalDataDto> getProposalDataDtoList() {
		return proposalDataDtoList;
	}

	/**
	 * 投保单号列表
	 * 
	 * @param proposalDataDtoList
	 *            the proposalDataDtoList to set
	 */
	public void setProposalDataDtoList(List<ProposalDataDto> proposalDataDtoList) {
		this.proposalDataDtoList = proposalDataDtoList;
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
		if (!isSuccess()) {
			// 优先返回resultDTO里的错误
			if (resultDTO != null && !resultDTO.isSuccess()) {
				errorMess = resultDTO.getResultMess();
				// 再返回dealMessage的错误消息
			} else if (!"1".equals(dealFlag)) {
				errorMess = dealMessage;
			} else {
				// resultDTO is null
				errorMess = "系统异常";
			}
		}
		return errorMess;
	}
}
