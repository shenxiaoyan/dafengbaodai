package com.liyang.domain.department;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorState;
/**
 * 部门状态：正常、禁用、已创建、已删除
 * @author Administrator
 *
 */
@Entity
@Table(name = "department_state")
public class DepartmentState extends AbstractAuditorState<Department, DepartmentAct> {

	public DepartmentState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}
	public DepartmentState(){
		super();
	}


}
