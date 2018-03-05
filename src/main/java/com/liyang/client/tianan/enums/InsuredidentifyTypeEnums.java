package com.liyang.client.tianan.enums;

/**
 * @author duyiting
 *
 */
public enum InsuredidentifyTypeEnums {
	IDCARD(1,"身份证"),
	RESIDENCE_BOOKLET(2,"民户口薄"),
	DRIVERS_LICENSE(3,"驾驶证"),
	CERTIFICATE_OF_OFFICERS(4,"军官证"),
	SOLDIER_CARD(5,"士兵证"),
	OFFICER_RETIREMENT_CERTIFICATE(6,"军官离退休证"),
	CHINESE_PASSPORT(7,"中国护照"),
	FOREIGN_PASSPORT(8,"外国护照");
	
	private Integer code;
	private String message;
	
	public Integer getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	private InsuredidentifyTypeEnums(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public static boolean isExist(String code) {
		for (InsuredidentifyTypeEnums enums : InsuredidentifyTypeEnums.values()) {
			if(String.valueOf(enums.getCode()).equals(code)) {
				return true;
			}
		}
		return false;
	}

}
