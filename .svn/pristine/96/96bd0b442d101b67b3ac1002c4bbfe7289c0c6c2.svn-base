package com.liyang.client.tianan.enums;

/**
 * @author duyiting
 *
 */
public enum FileTypeEnums {
	holderIdCard(1001,"投保人证件"),
	insuredIdCard(1002,"被保险人证件"),
	vehicleLicense(1003,"车辆行驶证"),
	vehiclePhone(1004,"验车照片"),
	insurePhone(1005,"投保照片"),
	insureImage(1006,"投保影像")
	;
	private Integer code;
	private String message;
	private FileTypeEnums(Integer code,String message) {
		this.code=code;
		this.message=message;
	}
	public Integer getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public static boolean isExist(String code) {
		for (FileTypeEnums enums : FileTypeEnums.values()) {
			if(String.valueOf(enums.getCode()).equals(code)) {
				return true;
			}
		}
		return false;
	}

	
}
