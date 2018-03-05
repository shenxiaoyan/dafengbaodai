package com.liyang.domain.insurercompany;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.liyang.domain.base.AuditorEntityRepository;

/**
 * 保险公司
 * @author Administrator
 *
 */
public interface InsureCompanyRepository extends AuditorEntityRepository<InsureCompany>, JpaSpecificationExecutor<InsureCompany>{
	
	@Query("from InsureCompany a")
	public List<InsureCompany> getAlll();
	
	@Query("from InsureCompany a where a.state.stateCode = 'ENABLED' ")
	public List<InsureCompany> getAllEnable();
	
	@SuppressWarnings("unchecked")
	public InsureCompany save(InsureCompany company);
	
	public InsureCompany findByInsurerCompanyId(@Param("insuranceCompanyId") int insuranceCompanyId);
	
	@Query("from InsureCompany c where c.state.stateCode = ?1")
	public Page<InsureCompany> getByStateCode(@Param("stateCode") String state, Pageable p);
	
	public InsureCompany findById(@Param("id")Integer id);
}
