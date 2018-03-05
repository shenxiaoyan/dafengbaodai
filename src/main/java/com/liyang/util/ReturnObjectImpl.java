package com.liyang.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Administrator
 *
 */
public class ReturnObjectImpl implements ReturnObject {

	@JsonProperty("ActionStatus")
	private String actionStatus;
	@JsonProperty("ErrorInfo")
	private String errorInfo;
	@JsonProperty("ErrorCode")
	private Integer errorCode;
	@JsonProperty("MsgTime")
	private Integer msgTime;
	@JsonIgnore
	private Level level = Level.DISPLAY;
	@JsonProperty("data")
	private String data;
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String getActionStatus() {
		return actionStatus;
	}

	@Override
	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}

	@Override
	public String getErrorInfo() {
		return errorInfo;
	}

	@Override
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	@Override
	public Integer getErrorCode() {
		return errorCode;
	}

	@Override
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public Integer getMsgTime() {
		return msgTime;
	}

	@Override
	public void setMsgTime(Integer msgTime) {
		this.msgTime = msgTime;
	}

	@Override
	public String toString() {
		// return "ReturnObjectImpl [actionStatus=" + actionStatus + ",
		// errorInfo=" + errorInfo + ", errorCode="
		// + errorCode + ", msgTime=" + msgTime + "]";
		StringBuilder sb = new StringBuilder();
		return sb.append("ReturnObjectImpl").append("[actionStatus=").append(actionStatus).append(",")
				.append("errorInfo=").append(errorInfo).append(",").append("errorCode=").append(errorCode).append(",")
				.append("msgTime=").append(msgTime).append("]").toString();
	}

	@Override
	public Level getLevel() {
		// TODO Auto-generated method stub
		return level;
	}

	@Override
	public void setLevel(Level level) {
		this.level = level;

	}

}
