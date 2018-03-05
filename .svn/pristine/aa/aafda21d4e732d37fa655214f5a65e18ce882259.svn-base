package com.liyang.domain.address;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.web.PageableDefault;

import com.liyang.domain.base.AuditorEntityRepository;

/**
 * @author Administrator
 *
 */
@RepositoryRestResource
public interface AddressRepository extends AuditorEntityRepository<Address>, JpaSpecificationExecutor<Address> {

	
	public Page<Address> findByStateStateCode(@Param("stateCode") String stateCode,
			@PageableDefault(20) Pageable pageable);

	
	public Address save(Address address);

	
	public Address findById(Integer id);

	@Query("select distinct a.province from Address a where a.state.stateCode='ENABLED'")
	public List<String> findAllProvinces();

	@Query("select a from Address a where a.province=?1 and a.state.stateCode='ENABLED'")
	public List<Address> findCitiesByProvince(String province);
}
