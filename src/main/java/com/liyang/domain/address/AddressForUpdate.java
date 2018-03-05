package com.liyang.domain.address;

/**
 * 更新地址
 * @author Administrator
 *
 */
public class AddressForUpdate {
	private Integer id;
	private String stateCode;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
}
