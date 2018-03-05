package com.liyang.domain.responseReturn;

/**
 * 提示信息
 * @author Administrator
 *
 */
public class ErrorMsg {
	private String code;
	private String message;
	public ErrorMsg() {
		super();
	}
	public ErrorMsg(String code,String message) {
		super();
		this.code=code;
		this.message=message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
