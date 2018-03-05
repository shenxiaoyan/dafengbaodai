package com.liyang.domain.querypayment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * 第三方支付
 * @author Administrator
 *
 */
@RepositoryRestResource
public interface QueryPayRepository extends JpaRepository<QueryPay, Integer>{
	public QueryPay getByOrderId(String orderId);
	
	public List<QueryPay> findByOrderId(String orderId);
	

}
