package com.liyang.client.tianan.enums;

/**
 * 天安不计免赔险种枚举类
 * 
 * @author huanghengkun
 * @create 2017年12月14日
 */
public enum AdditionalEnums {
	//机动车损失险不计免赔
	A63("A63", "机动车损失险不计免赔", "63"), 
	A68("A68", "第三者责任险不计免赔", "68"), 
	A73("A73", "驾驶人责任险不计免赔", "73"), 
	A89("A89", "乘客责任险不计免赔",	"89"), 
	A74("A74", "全车盗抢险不计免赔", "74"), 
	A75("A75", "车身划痕损失险不计免赔", "75"), 
	A36("A36", "自燃损失险不计免赔", "36"), 
	A16("A16", "发动机涉水损失险不计免赔", "16"), 
	A11("A11", "新增设备损失险不计免赔", "11"), 
	A18("A18", "精神损害抚慰金责任险不计免赔", "18");

	private String code;
	private String name;
	private String mainInsurance;

	private AdditionalEnums(String code, String name, String mainInsurance) {
		this.code = code;
		this.name = name;
		this.mainInsurance = mainInsurance;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getMainInsurance() {
		return mainInsurance;
	}

	public static boolean isExist(String code) {
		for (AdditionalEnums enums : AdditionalEnums.values()) {
			if (String.valueOf(enums.getCode()).equals(code)) {
				return true;
			}
		}
		return false;
	}

	public static String findCodeByMainInsurance(String mainInsurance) {
		for (AdditionalEnums enums : AdditionalEnums.values()) {
			if (String.valueOf(enums.getMainInsurance()).equals(mainInsurance)) {
				return enums.code;
			}
		}
		return null;
	}
}
