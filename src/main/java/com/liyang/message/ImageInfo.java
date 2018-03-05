package com.liyang.message;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Administrator
 *
 */
public class ImageInfo {
	
	private Integer type;
	private Integer size;
	private Integer width;
	private Integer height;
	private String url;
	
	@JsonProperty("Type")
	public Integer getType() {
		return type;
	}
	@JsonProperty("Type")
	public void setType(Integer type) {
		this.type = type;
	}
	@JsonProperty("Size")
	public Integer getSize() {
		return size;
	}
	@JsonProperty("Size")
	public void setSize(Integer size) {
		this.size = size;
	}
	@JsonProperty("Width")
	public Integer getWidth() {
		return width;
	}
	@JsonProperty("Width")
	public void setWidth(Integer width) {
		this.width = width;
	}
	@JsonProperty("Height")
	public Integer getHeight() {
		return height;
	}
	@JsonProperty("Height")
	public void setHeight(Integer height) {
		this.height = height;
	}
	@JsonProperty("URL")
	public String getUrl() {
		return url;
	}
	@JsonProperty("URL")
	public void setUrl(String url) {
		this.url = url;
	}
	

}
