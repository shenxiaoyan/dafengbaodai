package com.liyang.domain.responseReturn;

/**
 * 数据
 * @author Administrator
 *
 */
public class Data {
	private String offerId;
	public Data() {
		super();
	}
	public Data(String offerId) {
		super();
		this.offerId=offerId;
	}
	public String getOfferId() {
		return offerId;
	}
	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
}
