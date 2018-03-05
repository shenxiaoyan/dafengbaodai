package com.liyang.domain.advertise;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorState;
import com.liyang.domain.base.AbstractWorkflowState;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserAct;
import com.liyang.domain.user.UserWorkflow;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "advertise_state")
@Cacheable
public class AdvertiseState extends AbstractAuditorState<Advertise, AdvertiseAct> {
	
	public AdvertiseState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public AdvertiseState() {
		super();
	}
	
}
