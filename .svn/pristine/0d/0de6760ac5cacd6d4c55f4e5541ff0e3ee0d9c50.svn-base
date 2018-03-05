package com.liyang.domain.underwritingresult;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
/**
 * 核保结果推送
 * @author Administrator
 *
 */
@RepositoryRestResource
public interface UnderwritingResultRepository extends JpaRepository<UnderwritingResult, Integer> {
	
	@Transactional
	@Override
	public UnderwritingResult save(UnderwritingResult underwritingResult);
	
	@Query("select u from UnderwritingResult u where u.data.orderId=?1")
	public UnderwritingResult findUnderwritingResultByOrderId(@Param("orderId")String orderId);
	
	public List<UnderwritingResult> findByDataOrderId(@Param("orderId") String orderId);
	
	
	
}
