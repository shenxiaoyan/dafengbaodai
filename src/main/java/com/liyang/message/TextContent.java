package com.liyang.message;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Administrator
 *
 */
public class TextContent implements IContent{
	
	
	private String text;
	@JsonProperty("Text")
	public String getText() {
		return text;
	}
	@JsonProperty("Text")
	public void setText(String text) {
		this.text = text;
	}
	
}
