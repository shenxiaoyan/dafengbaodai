package com.liyang.client.tianan.enums;

/**
 * @author duyiting 
 *
 */
public enum GroupIdentifyTypeEnums {
	
	ORGANIZATION_CODE(1,"组织机构代码"),
	BUSINESS_REGISTRATION_NUMBER(2,"工商注册号"),
	BUSINESS_LICENSE(3,"营业执照"),
	ADMINISTRATIVE_ORGAN(4,"行政机关"),
	SOCIAL_GROUPS(5,"社会团体"),
	ARMY(6,"军队"),
	ARMED_POLICE(7,"武警"),
	FOUNDATION(8,"基金会"),
	ENTERPRISE_CODE(9,"企业代码"),
	GROUP_CUSTOMER(10,"团体客户"),
	CODE_CERTIFICATE_OF_CORPORATION_CODE(11,"社团法人代码证书"),
	COMMUNITY_ORGANIZATION_CODE_CERTIFICATE(12,"社团团体代码证书"),
	OTHER(13,"其他");
	
	
	private Integer code;
	private String message;
	
	public Integer getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	private GroupIdentifyTypeEnums(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public static boolean isExist(String code) {
		for (GroupIdentifyTypeEnums enums : GroupIdentifyTypeEnums.values()) {
			if(String.valueOf(enums.getCode()).equals(code)) {
				return true;
			}
		}
		return false;
	}

}
