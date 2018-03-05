package com.liyang.client.tianan.enums;

/**
 * @author duyiting
 *
 */
public enum DeliveryTypeEnums {
	
	ONLINEPAY("DT0001","网上支付"),
	OUTLINEPAY("DT0002","线下支付"),
	GETBTSELF("DT0003","上门自取");
	private String code;
	private String message;
	
	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	private DeliveryTypeEnums(String code, String message) {
		this.code = code;
		this.message = message;
	}
	public static boolean isExist(String code) {
		for (DeliveryTypeEnums enums : DeliveryTypeEnums.values()) {
			if(String.valueOf(enums.getCode()).equals(code)) {
				return true;
			}
		}
		return false;
	}

}
