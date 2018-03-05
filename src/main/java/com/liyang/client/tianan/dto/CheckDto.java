package com.liyang.client.tianan.dto;

import com.liyang.client.IDto;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * 转保校验信息
 * 
 * @author Administrator
 *
 */
public class CheckDto implements IDto {
	private String checkFlag;

	/**
	 * 校验类别
	 * 
	 * @return the checkFlag
	 */
	public String getCheckFlag() {
		return checkFlag;
	}

	/**
	 * 校验类别
	 * 
	 * @param checkFlag
	 *            the checkFlag to set
	 */
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	private String querySequenceNo;

	/**
	 * 投保查询码
	 * 
	 * @return the querySequenceNo
	 */
	public String getQuerySequenceNo() {
		return querySequenceNo;
	}

	/**
	 * 投保查询码
	 * 
	 * @param querySequenceNo
	 *            the querySequenceNo to set
	 */
	public void setQuerySequenceNo(String querySequenceNo) {
		this.querySequenceNo = querySequenceNo;
	}

	private String checkCode;

	/**
	 * 验证码,验证码图片字节流
	 * 
	 * @return the checkCode
	 */
	public String getCheckCode() {
		return checkCode;
	}

	/**
	 * 验证码,验证码图片字节流
	 * 
	 * @param checkCode
	 *            the checkCode to set
	 */
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {
		
	}
}
