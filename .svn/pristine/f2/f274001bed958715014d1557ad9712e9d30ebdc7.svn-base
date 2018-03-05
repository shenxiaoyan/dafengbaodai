package com.liyang.domain.exception;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.liyang.domain.base.BaseEntity;
import com.liyang.util.ReturnObject;

/**
 * 异常
 * @author Administrator
 *
 */
@Entity
@Table(name = "exception")
public class Exception extends BaseEntity implements ReturnObject {
	@Column(name = "action_status")
	private String actionStatus;
	@Lob
	@Column(name = "error_info")
	private String errorInfo;
	@Column(name = "error_code")
	private Integer errorCode;

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
	@Transient
	public Integer getMsgTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMsgTime(Integer i) {
		// TODO Auto-generated method stub

	}

	@Override
	@JsonIgnore
	public Level getLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLevel(Level level) {
		// TODO Auto-generated method stub

	}

}
