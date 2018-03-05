package com.liyang.domain.temporaryresult;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * 暂存结果
 * @author Administrator
 *
 */
@RepositoryRestResource
public interface TemporaryResultRepository extends JpaRepository<TemporaryResult, Integer> {
	
	@Transactional
	@Override
	public TemporaryResult save(TemporaryResult temporaryResult);
}
