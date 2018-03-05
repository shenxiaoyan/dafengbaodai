package com.liyang.domain.teamadvertise;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorAct;

@Entity
@Table(name = "team_advertise_act")
public class TeamAdvertiseAct extends AbstractAuditorAct<TeamAdvertiseState>{

	private static final long serialVersionUID = 1L;
	
	public TeamAdvertiseAct(){
		super();
	}
	
	public TeamAdvertiseAct(String label, String actCode, Integer sort, ActGroup actGroup){
		super(label, actCode, sort, actGroup);
	}
}
