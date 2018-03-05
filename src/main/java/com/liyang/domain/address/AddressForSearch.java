package com.liyang.domain.address;


/**
 * 根据省或者市关键字搜索地址
 * @author Administrator
 *
 */
public class AddressForSearch {

	private String stateCode;
	// 省
	private String province;
	// 市
	private String city;

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
