package com.liyang.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 返回对象
 * @author Administrator
 *
 */
public interface ReturnObject {

	@JsonProperty("ActionStatus")
	public String getActionStatus();
	@JsonProperty("ActionStatus")
	public void setActionStatus(String s);
	@JsonProperty("ErrorInfo")
	public String getErrorInfo();
	@JsonProperty("ErrorInfo")
	public void setErrorInfo(String s);
	@JsonProperty("ErrorCode")
	public Integer getErrorCode();
	@JsonProperty("ErrorCode")
	public void setErrorCode(Integer i);
	@JsonProperty("MsgTime")
	public Integer getMsgTime();
	@JsonProperty("MsgTime")
	public void setMsgTime(Integer i);
	@JsonIgnore
	public Level getLevel();
	public void setLevel(Level level);
	
	public enum Level{
		DISPLAY,LOG
	}
}
