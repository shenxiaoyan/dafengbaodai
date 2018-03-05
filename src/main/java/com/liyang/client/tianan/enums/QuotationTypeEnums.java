package com.liyang.client.tianan.enums;

/**
 * @author nielijun
 *
 */
public enum QuotationTypeEnums {

	compulsoryInsurance("1","交强险"),
	businessInsurance("2","商业险"),
	compulsoryAndBusinessInsurance("3","混合保险")
	
	;
	private String code;
	private String message;

	private QuotationTypeEnums(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public static boolean isExist(String code) {
		for (QuotationTypeEnums enums : QuotationTypeEnums.values()) {
			if (enums.getCode().equals(code)) {
				return true;
			}
		}
		return false;
	}

}
