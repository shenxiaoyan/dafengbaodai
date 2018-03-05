package com.liyang.message;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Administrator
 *
 */
public class FaceContent implements IContent {

	private Integer index;
	private String data;

	@JsonProperty("Index")
	public Integer getIndex() {
		return index;
	}

	@JsonProperty("Index")
	public void setIndex(Integer index) {
		this.index = index;
	}

	@JsonProperty("Data")
	public String getData() {
		return data;
	}

	@JsonProperty("Data")
	public void setData(String data) {
		this.data = data;
	}
}
