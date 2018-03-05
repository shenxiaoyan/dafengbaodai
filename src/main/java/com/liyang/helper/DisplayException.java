package com.liyang.helper;

/**
 * 用于报错的一个异常
 * @author Administrator
 */
public class DisplayException extends RuntimeException{
	
	public DisplayException() {
	}
	
	public DisplayException(String message){
		super(message);
	}
}
