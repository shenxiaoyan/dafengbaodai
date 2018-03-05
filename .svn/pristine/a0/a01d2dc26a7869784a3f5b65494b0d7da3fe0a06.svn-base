package com.liyang.domain.department;

import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

import com.liyang.domain.departmenttype.Departmenttype;


/**
 * 类说明
 * @author Administrator
 *
 */
@Projection(name="departmentProjection",types={Department.class})
public interface DepartmentProjection {
	int getId();
	String getLabel();
	Date getCreatedAt();
	Date getLastModifiedAt();
	String getAddress();
	String getAttactPerson();
	String getAttactPhone();
	String getDescription();
	DepartmentState getState();
	Departmenttype getType();
	String  getLogo();
	Department getParent();
//	Department getCreatedByDepartment();
	
}
