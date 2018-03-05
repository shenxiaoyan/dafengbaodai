package com.liyang.domain.base;

/**
 * 操作日志查询类
 * 
 * @author huanghengkun
 * @create 2017年12月22日
 */
public class LogForSearch {

	private String entityName;

	private String actCode;

	private Integer entityId;

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getActCode() {
		return actCode;
	}

	public void setActCode(String actCode) {
		this.actCode = actCode;
	}

	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

}
