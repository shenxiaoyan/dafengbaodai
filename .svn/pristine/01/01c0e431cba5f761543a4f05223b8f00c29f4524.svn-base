package com.liyang.message;

import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Administrator
 *
 */
public class BaseMsgElement implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EnumOperationMessageType msgType;
	private Object msgContent;

	

	@JsonProperty("MsgType")
	public EnumOperationMessageType getMsgType() {
		return msgType;
	}
	@JsonProperty("MsgType")
	public void setMsgType(EnumOperationMessageType msgType) {
		this.msgType = msgType;
	}

	@JsonProperty("MsgContent")
	public Object getMsgContent() {
		return msgContent;
	}
	@JsonProperty("MsgContent")
	public void setMsgContent(Object msgContent) {
		this.msgContent = msgContent;
	}
	

}