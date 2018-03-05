package com.liyang.domain.teamadvertise;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.team.Team;

public interface TeamAdvertiseRepository extends AuditorEntityRepository<TeamAdvertise>, JpaSpecificationExecutor<TeamAdvertise>{

	List<TeamAdvertise> findByState_StateCode(String stateCode);
	
	TeamAdvertise findById(Integer id);

	Page<TeamAdvertise> findByTeamAndState_StateCodeOrderByCreatedAtDesc(Team team, String string, Pageable pageable);
}
