package com.liyang.domain.user;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowAct;


/**
 * web端用户act
 * @author Administrator
 *
 */
@Entity
@Table(name="user_act")
public class UserAct extends AbstractWorkflowAct<UserState,UserWorkflow> {

	public UserAct(String label, String actCode, Integer sort, ActGroup actGroup) {
		super(label, actCode, sort, actGroup);
		// TODO Auto-generated constructor stub
	}
	public UserAct(){
		super();
	}
	
}
