package com.liyang.domain.platform;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Administrator
 *
 */
@RepositoryRestResource
public interface PlatformRepository extends JpaRepository<Platform, Integer> {

	public Platform findByApplicationId(String applicationId);

	public Platform findById(Integer id);
}
