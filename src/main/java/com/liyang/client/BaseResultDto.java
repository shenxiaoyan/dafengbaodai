package com.liyang.client;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @author Administrator
 *
 */
public class BaseResultDto {
	private String resultCode;

	public BaseResultDto(String resultCode, String resultMess) {
		this.resultCode = resultCode;
		this.resultMess = resultMess;
	}

	/**
	 * 返回码
	 * 
	 * @return the resultCode
	 */
	public String getResultCode() {
		return resultCode;
	}

	/**
	 * 返回码
	 * 
	 * @param resultCode
	 *            the resultCode to set
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	private String resultMess;

	/**
	 * 返回信息
	 * 
	 * @return the resultMess
	 */
	public String getResultMess() {
		return resultMess;
	}

	/**
	 * 返回信息
	 * 
	 * @param resultMess
	 *            the resultMess to set
	 */
	public void setResultMess(String resultMess) {
		this.resultMess = resultMess;
	}

	/**
	 * 是否成功的响应 resultCode 为空，或者resultCode不为空并且值是SUCCESS
	 * 
	 * @return
	 */
	@JSONField(serialize = false)
	public boolean isSuccess() {
		return StringUtils.isEmpty(this.resultCode) || "SUCCESS".equals(this.resultCode);
	}
}
