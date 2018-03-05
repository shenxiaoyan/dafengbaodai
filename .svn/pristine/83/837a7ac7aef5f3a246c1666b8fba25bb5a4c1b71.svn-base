package com.liyang.listener;

import org.springframework.stereotype.Component;

import com.liyang.domain.role.Role;
import com.liyang.domain.role.RoleAct;
import com.liyang.domain.role.RoleLog;
import com.liyang.domain.role.RoleState;
/**
 * @author Administrator
 *
 */
@Component
public class RoleRestEventListener extends AuditorRestEventListener<Role,RoleState,RoleAct,RoleLog> {

	@Override
	protected void onAfterRead(Role entity) {
		entity.setupVisibleMenuTree();
		super.onAfterRead(entity);
	}

}
