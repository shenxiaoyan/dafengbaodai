package com.liyang.domain.user;

import org.springframework.data.repository.query.Param;

import com.liyang.domain.base.StateRepository;

//@RepositoryRestResource(excerptProjection = AbstractWorkflowStateProjection.class)
/**
 * web端用户状态
 * @author Administrator
 *
 */
public interface UserStateRepository extends StateRepository<UserState> {
	
	public UserState getByStateCode(@Param("stateCode") String stateCode);
}