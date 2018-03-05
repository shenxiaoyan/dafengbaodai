package com.liyang.domain.team;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorState;

@Entity
@Table(name="team_state")
public class TeamState extends AbstractAuditorState<Team, TeamAct>{
	
	private static final long serialVersionUID = 1L;

	public TeamState() {
		super();
	}
	
	public TeamState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
	}
}
