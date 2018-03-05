package com.liyang.domain.advertise;

import java.util.Date;

/**
 * 根据关键字搜索Advertise
 * @author Administrator
 *
 */
public class AdvertiseForSearch {
	
	private String stateCode;
	private String type;
	private Date lastModifiedBeginTime;
	private Date lastModifiedEndTime;
	private Date publishBeginTime;
	private Date publishEndTime;
	
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getLastModifiedBeginTime() {
		return lastModifiedBeginTime;
	}
	public void setLastModifiedBeginTime(Date lastModifiedBeginTime) {
		this.lastModifiedBeginTime = lastModifiedBeginTime;
	}
	public Date getLastModifiedEndTime() {
		return lastModifiedEndTime;
	}
	public void setLastModifiedEndTime(Date lastModifiedEndTime) {
		this.lastModifiedEndTime = lastModifiedEndTime;
	}
	public Date getPublishBeginTime() {
		return publishBeginTime;
	}
	public void setPublishBeginTime(Date publishBeginTime) {
		this.publishBeginTime = publishBeginTime;
	}
	public Date getPublishEndTime() {
		return publishEndTime;
	}
	public void setPublishEndTime(Date publishEndTime) {
		this.publishEndTime = publishEndTime;
	}
	
	

}
