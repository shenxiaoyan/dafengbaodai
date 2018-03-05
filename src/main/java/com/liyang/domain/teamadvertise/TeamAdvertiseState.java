package com.liyang.domain.teamadvertise;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorState;

@Entity
@Table(name = "team_advertise_state")
public class TeamAdvertiseState extends AbstractAuditorState<TeamAdvertise, TeamAdvertiseAct>{

	private static final long serialVersionUID = 1L;
	
	public TeamAdvertiseState(){
		super();
	}

	public TeamAdvertiseState(String label, Integer sort, String stateCode){
		super(label, sort, stateCode);
	}
}
