package com.liyang.util;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Administrator
 *
 */
public class SuccessReturnObject implements ReturnObject{
	

	private Object result;
	@JsonIgnore
	private Level level;
	
	
	public void setResult(Object result) {
		this.result = result;
	}

	public SuccessReturnObject(Object message) {
		
		this.result = message;
	}
	
	public SuccessReturnObject() {
		
		this.result = null;
	}
	

	@Override
	public String getActionStatus() {
		// TODO Auto-generated method stub
		return "OK";
	}
	
	public Object getResult() {
		// TODO Auto-generated method stub
		return result;
	}

	@Override
	public String getErrorInfo() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public Integer getErrorCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Integer getMsgTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setActionStatus(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setErrorInfo(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setErrorCode(Integer i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMsgTime(Integer i) {
		// TODO Auto-generated method stub
		
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
