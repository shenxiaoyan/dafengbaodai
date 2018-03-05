package com.liyang.domain.teamobjective;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorAct;

@Entity
@Table(name="team_objective_act")
public class TeamObjectiveAct extends AbstractAuditorAct<TeamObjectiveState>{
	
	private static final long serialVersionUID = 1L;

	public TeamObjectiveAct() {
		super();
	}
	
	public TeamObjectiveAct(String label, String actCode, Integer sort, ActGroup actGroup) {
		super(label, actCode, sort, actGroup);
	}
	
}
