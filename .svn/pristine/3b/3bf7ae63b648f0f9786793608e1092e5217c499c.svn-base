package com.liyang.domain.perbusinesscard;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.liyang.domain.base.AuditorEntityRepository;
import org.springframework.data.repository.query.Param;

public interface PerBusinessCardRepository  extends AuditorEntityRepository<PerBusinessCard>,JpaSpecificationExecutor<PerBusinessCard>{
	
	public PerBusinessCard findByCustomer_Id(@Param("customerId") Integer customerId);
	public PerBusinessCard findByCustomerToken(@Param("token") String token);
	public PerBusinessCard findByPhone(@Param("phone") String phone);


}
