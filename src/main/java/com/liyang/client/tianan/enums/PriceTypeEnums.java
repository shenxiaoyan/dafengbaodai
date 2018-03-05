package com.liyang.client.tianan.enums;

/**
 * @author duyiting
 *
 */
public enum PriceTypeEnums {
	
	NEWCARPRICE("01","新车购置价"),
	NEWCARPRICE_INCLUDETAX("02","新车购置价（含税）"),
	ANALOGY_VEHICLE_PRICE("03","类比车型价"),
	ANALOGY_VEHICLE_PRICE_INCLUDETAX("04","类比车型价（含税）");
	
	private String code;
	private String message;
	
	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	private PriceTypeEnums(String code, String message) {
		this.code = code;
		this.message = message;
	}
	public static boolean isExist(String code) {
		for (PriceTypeEnums enums : PriceTypeEnums.values()) {
			if(enums.getCode().equals(code)) {
				return true;
			}
		}
		return false;
	}

}
