package com.liyang.client.tianan;

import com.liyang.client.BaseResult;
import com.liyang.client.BaseResultDto;
import com.liyang.client.IResult;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * @author Administrator
 *
 */
public class ResultUploadAttach extends BaseResult implements IResult {
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

	@Override
	public void validate() throws ExceptionTiananParamInvliad {

	}
}
