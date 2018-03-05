package com.liyang.domain.feedback;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * 意见反馈
 * @author Administrator
 *
 */
@RepositoryRestResource
public interface FeedbackRepository extends JpaSpecificationExecutor<Feedback>,JpaRepository<Feedback, Integer> {

	@Query("from Feedback f order by f.submitTime desc")
	public Page<Feedback> query(Pageable p);	
}