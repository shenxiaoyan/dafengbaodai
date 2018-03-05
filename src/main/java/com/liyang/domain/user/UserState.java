package com.liyang.domain.user;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowState;

/**
 * web端用户状态
 * @author Administrator
 *
 */
@Entity
@Table(name = "user_state")
@Cacheable
public class UserState extends AbstractWorkflowState<User, UserWorkflow, UserAct> {

	public UserState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public UserState() {
		super();
	}
	
}
