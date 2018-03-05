package com.liyang.domain.user;

import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

import com.liyang.domain.department.Department;
import com.liyang.domain.role.Role;

/**  
* web端用户   
* @author lcj 
* @date 2017年10月18日  新建  
*/

///rest/users?projection=showInEnd
///rest/users/search/getByStateCode?stateCode=ENABLED&projection=showInEnd
///rest/departments/1/employees?projection=showInEnd
@Projection(name="showInEnd",types={User.class})
public interface UserShowInEndProjection {
	Integer getId();
	String getNickname();
	String getUsername();
	Department	getDepartment();
	Role getRole(); 
	UserState getState();
	Date getLastModifiedAt();
}

