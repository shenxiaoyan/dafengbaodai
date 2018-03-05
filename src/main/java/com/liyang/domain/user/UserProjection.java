package com.liyang.domain.user;

import org.springframework.data.rest.core.config.Projection;

import com.liyang.domain.department.Department;
import com.liyang.domain.role.Role;


/**
 * web端用户
 * @author Administrator
 *
 */
@Projection(name="userProjection",types={User.class})
public interface UserProjection {
	
	Integer getId();
	String getNickname();
	String getSex();
	String getHeadimgurl();
	Department getDepartment();
	Role getRole();
	String getUsername();
	
}
