package com.liyang.client.tianan.enums;

/**
 * @author Administrator
 *
 */
public enum InsuredTypeEnums {
	INSUREDTYPE_ONE(1,"个人"),
	INSUREDtYPE_TWO(2,"团体");
	private Integer code;
	private String message;
	
	public Integer getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	private InsuredTypeEnums(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public static boolean isExist(String code) {
		for (InsuredTypeEnums enums : InsuredTypeEnums.values()) {
			if(String.valueOf(enums.getCode()).equals(code)) {
				return true;
			}
		}
		return false;
	}
}
