package com.liyang.domain.querylatestpolicy;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * 查询续保
 * @author Administrator
 *
 */
@RepositoryRestResource
public interface QueryLatestPolicyRepository extends JpaRepository<QueryLatestPolicy,Integer> {
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public QueryLatestPolicy save(QueryLatestPolicy queryLatestPolicy);

	public QueryLatestPolicy findByLicenseNumberOrderByIdDesc(String licenseNumber);

	public QueryLatestPolicy findByQueryLatestPolicyResult_Data_Id(Integer latestPolicyDataId);

}
