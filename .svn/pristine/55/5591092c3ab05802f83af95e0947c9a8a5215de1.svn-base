package com.liyang.domain.teamobjective;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.team.Team;

public interface TeamObjectiveRepository extends AuditorEntityRepository<TeamObjective>, JpaSpecificationExecutor<TeamObjective>{

	TeamObjective findByTeamAndPeriod(Team team, String period);

	List<TeamObjective> findByTeam(Team team);

}
