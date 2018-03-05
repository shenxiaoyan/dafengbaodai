package com.liyang.domain.base;

import java.util.Date;

/**
 * 基础类视图对象
 * 
 * @author Administrator
 *
 */
public abstract class BaseEntityVO {

	private Integer id;

	private String label;

	// private User createdBy;

	// private Department createdByDepartment;

	// private User lastModifiedBy;

	private Date createdAt;

	private Date lastModifiedAt;

	private StateVO state;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	public StateVO getState() {
		return state;
	}

	public void setState(StateVO state) {
		this.state = state;
	}

}
