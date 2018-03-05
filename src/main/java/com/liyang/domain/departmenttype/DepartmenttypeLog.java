package com.liyang.domain.departmenttype;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorLog;
/**
 * 部门类型日志
 * @author Administrator
 *
 */
@Entity
@Table(name="departmenttype_log")
public class DepartmenttypeLog extends AbstractAuditorLog<Departmenttype,DepartmenttypeState,DepartmenttypeAct> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


}
