package com.liyang.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.liyang.enums.ExceptionResultEnum;

/**
 * 返回对象
 * 
 * @author Administrator
 */
public class ResponseBody {
	/**
	 * 返回状态码 0是ok 100是错误 response_message是消息
	 */
	@JsonProperty("ActionStatus")
	private String actionStatus;
	@JsonProperty("ErrorCode")
	private Integer errorCode;
	@JsonProperty("ErrorInfo")
	private String errorInfo;

	private Object data;

	@JsonProperty(value = "ActionStatus")
	public String getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}

	@JsonProperty(value = "ErrorCode")
	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	@JsonProperty(value = "ErrorInfo")
	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ResponseBody(Integer errorCode, String errorInfo) {
		super();
		this.errorCode = errorCode;
		this.errorInfo = errorInfo;
	}

	public ResponseBody() {
	}

	public ResponseBody(Object data) {
		super();
		this.errorCode = 0;
		this.errorInfo = "OK";
		this.data = data;
	}

	public ResponseBody(ExceptionResultEnum exResEnum) {
		super();
		this.errorInfo = exResEnum.getMessage();
		this.errorCode = exResEnum.getCode();
	}

	@Override
	public String toString() {
		return "ResponseBody [ActionStatus=" + actionStatus + ", ErrorCode=" + errorCode + ", ErrorInfo=" + errorInfo
				+ ", data=" + data + "]";
	}

}
