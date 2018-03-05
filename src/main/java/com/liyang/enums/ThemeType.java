package com.liyang.enums;

public enum ThemeType {
	
	APHORISM("名言"),
	FESTIVAL("节日"),
	PRODUCT("产品"),
	OTHER("其他");
	
	private String message;

	ThemeType(String message) {
		this.message = message;
	}



	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
