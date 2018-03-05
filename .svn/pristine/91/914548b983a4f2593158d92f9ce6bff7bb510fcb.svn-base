package com.liyang.domain.querylatestpolicy;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.minidev.json.annotate.JsonIgnore;

/**
 * 查询续保规则
 * @author Administrator
 *
 */
@Entity
@Table(name="query_latest_policy_depend_rule_json")
public class DependRuleJson implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="depend_desc")
	private String dependDesc;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDependDesc() {
		return dependDesc;
	}

	public void setDependDesc(String dependDesc) {
		this.dependDesc = dependDesc;
	}
}
