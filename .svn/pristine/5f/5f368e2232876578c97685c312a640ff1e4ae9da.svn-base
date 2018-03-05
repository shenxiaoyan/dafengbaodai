package com.liyang.domain.product;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.liyang.domain.base.AuditorEntityRepository;

/** 
* @author Adam 
* @version 创建时间：2018年1月31日 上午9:46:26 
* 类说明 
*/
public interface ProductRepository extends AuditorEntityRepository<Product>, JpaSpecificationExecutor<Product>{

	Product findByTypeAndProductCompany_Label(int type, String insuranceCompanyName);
	Product findById(Integer id);

}
