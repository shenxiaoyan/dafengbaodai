package com.liyang.domain.dafengapi;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.liyang.domain.platform.Platform;

/**  
* 类说明   
* @author lcj 
* @date 2017年9月13日  新建  
*/
@RepositoryRestResource(path="dafengApi")
public interface DafengApiCustomerRepository extends JpaRepository<DafengApiCustomer, Integer> {
	
	@RestResource(path="apiKeyAndIpAddress",rel="apiKeyAndIpAddress")
	public List<DafengApiCustomer> findByApiKeyAndIpAddress(@Param("apikey") String apikey, @Param("ipAddress") String ipAddress );
	
	@RestResource(path="platform",rel="platform")
	public List<DafengApiCustomer> findByPlatform(@Param("platform") Platform platform);
//	
//	@RestResource(path="applicationId",rel="applicationId")
//	@Query("from DafengApiCustomer dc where dc.platform =?1 ")
//	public List<DafengApiCustomer> findByApplicationId(@Param("platform") Platform platform);



}
