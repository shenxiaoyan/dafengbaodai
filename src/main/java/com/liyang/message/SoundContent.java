package com.liyang.message;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Administrator
 *
 */
public class SoundContent implements IContent{
	
	
private String uuid;
private Integer size;
private Integer second;

@JsonProperty("UUID")
public String getUuid() {
	return uuid;
}
@JsonProperty("UUID")
public void setUuid(String uuid) {
	this.uuid = uuid;
}
@JsonProperty("Size")
public Integer getSize() {
	return size;
}
@JsonProperty("Size")
public void setSize(Integer size) {
	this.size = size;
}
@JsonProperty("Second")
public Integer getSecond() {
	return second;
}
@JsonProperty("Second")
public void setSecond(Integer second) {
	this.second = second;
}

	
}
