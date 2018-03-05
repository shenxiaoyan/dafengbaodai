package com.liyang.domain.identify;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 身份信息
 * 
 * @author Administrator
 *
 */
@Table(name = "identify")
@Entity
public class Identify {
	private int id;
	private String phone;
	private String code;
	private Date expire;
	private String invite;

	public String getInvite() {
		return invite;
	}

	public void setInvite(String invite) {
		this.invite = invite;
	}

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	public int getId() {
		return id;
	}

	public Date getExpire() {
		return expire;
	}

	public void setExpire(Date expire) {
		this.expire = expire;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(length = 13)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(length = 7)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	
}
