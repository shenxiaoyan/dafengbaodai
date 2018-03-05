package com.liyang.message;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hamcrest.core.IsInstanceOf;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liyang.util.FailReturnObject;
import com.liyang.util.CommonUtil;

/**
 * @author Administrator
 *
 */
public class Message implements Serializable {

	private String toAccount;
	private Integer syncOtherMachine;
	private String callbackCommand;
	private String fromAccount;
	private Integer msgRandom;
	private BaseMsgElement[] msgBody;
	
	@JsonProperty("SyncOtherMachine")
	public Integer getSyncOtherMachine() {
		return syncOtherMachine;
	}
	@JsonProperty("SyncOtherMachine")
	public void setSyncOtherMachine(Integer syncOtherMachine) {
		this.syncOtherMachine = syncOtherMachine;
	}
	@JsonProperty("MsgRandom")
	public Integer getMsgRandom() {
		return msgRandom;
	}
	@JsonProperty("MsgRandom")
	public void setMsgRandom(Integer msgRandom) {
		this.msgRandom = msgRandom;
	}

	@JsonProperty("To_Account")
	public String getToAccount() {
		return toAccount;
	}

	@JsonProperty("To_Account")
	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}

	@JsonProperty("CallbackCommand")
	public String getCallbackCommand() {
		return callbackCommand;
	}

	@JsonProperty("CallbackCommand")
	public void setCallbackCommand(String callbackCommand) {
		this.callbackCommand = callbackCommand;
	}

	@JsonProperty("From_Account")
	public String getFromAccount() {
		return fromAccount;
	}

	@JsonProperty("From_Account")
	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}

	@JsonProperty("MsgBody")
	public BaseMsgElement[] getMsgBody() {
		return msgBody;
	}

	@JsonProperty("MsgBody")
	public void setMsgBody(BaseMsgElement[] msgBody) {

		this.msgBody = msgBody;

	}


	
}
