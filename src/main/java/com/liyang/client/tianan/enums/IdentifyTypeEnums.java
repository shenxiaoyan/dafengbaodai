package com.liyang.client.tianan.enums;

import org.springframework.util.StringUtils;

/**
 * @author duyiting
 *
 */
public enum IdentifyTypeEnums {
	IDCARD("01","身份证"),
	RESIDENCE_BOOKLET("02","居民户口薄"),
	DRIVERS_LICENSE("03","驾驶证"),
	CERTIFICATE_OF_OFFICERS("04","军官证"),
	SOLDIER_CARD("05","士兵证"),
	OFFICER_RETIREMENT_CERTIFICATE("06","军官离退休证"),
	CHINESE_PASSPORT("07","中国护照"),
	FOREIGN_PASSPORT("08","外国护照");
	
	private String code;
	private String message;
	
	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	private IdentifyTypeEnums(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public static boolean isExist(String code) {
		for (IdentifyTypeEnums enums : IdentifyTypeEnums.values()) {
			if(enums.getCode().equals(code)) {
				return true;
			}
		}
		return false;
	}

}
