package com.liyang.client.tianan.enums;


/**
 * @author nielijun
 *
 */
public enum RateMarkEnums {

	RATEMARK_ONE(1,"传统产品"),
	RATEMARK_TWO(2,"电网销产品");
	private Integer code;
	private String message;
	
	public Integer getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	private RateMarkEnums(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	public static boolean isExist(String code) {
		for (RateMarkEnums enums : RateMarkEnums.values()) {
			if(String.valueOf(enums.getCode()).equals(code)) {
				return true;
			}
		}
		return false;
	}

	
}
