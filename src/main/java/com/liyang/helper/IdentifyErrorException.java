package com.liyang.helper;


/**
 * 验证码错误异常，到时候会有处理？
 * @author Administrator
 */
public class IdentifyErrorException extends RuntimeException{
	
	public IdentifyErrorException(String message){
		super(message);
	}
	
	public IdentifyErrorException(){
	}
}
