package com.liyang.domain.team;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.liyang.domain.base.AuditorEntityRepository;

public interface TeamRepository extends AuditorEntityRepository<Team>, JpaSpecificationExecutor<Team>{
	
	public Team findByLabelAndState_StateCodeIn(String name, String[] stateCodes);

	public Team findByTeamInviteCode(String teamInviteCode);
	
	// mobile权限问题，添加此方法
	public Team findById(Integer teamId);

	public List<Team> findByState_StateCode(String stateCode);
	
}
