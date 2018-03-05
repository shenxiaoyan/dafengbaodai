package com.liyang.domain.myinvite;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 用户个人邀请码
 * @author Administrator
 *
 */
@Entity
public class MyInvite {
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private int id;
	
	@Column(length=6)
	private String randomString;
	
	private Integer tag;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRandomString() {
		return randomString;
	}
	public void setRandomString(String randomString) {
		this.randomString = randomString;
	}
	public Integer getTag() {
		return tag;
	}
	public void setTag(int tag) {
		this.tag = tag;
	}
	
}
