package com.liyang.domain.insurercompany;

import com.liyang.domain.base.BaseEntityVO;

public class InsureCompanyVO extends BaseEntityVO {

	private int insurerCompanyId;

	private String name;

	private String account;

	private String password;

	// private int status;

	private int property;

	private String listIcon;

	private String detailIcon;

	// private Platform platform;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getProperty() {
		return property;
	}

	public void setProperty(int property) {
		this.property = property;
	}

	public String getListIcon() {
		return listIcon;
	}

	public void setListIcon(String listIcon) {
		this.listIcon = listIcon;
	}

	public String getDetailIcon() {
		return detailIcon;
	}

	public void setDetailIcon(String detailIcon) {
		this.detailIcon = detailIcon;
	}

	public int getInsurerCompanyId() {
		return insurerCompanyId;
	}

	public void setInsurerCompanyId(int insurerCompanyId) {
		this.insurerCompanyId = insurerCompanyId;
	}

}
