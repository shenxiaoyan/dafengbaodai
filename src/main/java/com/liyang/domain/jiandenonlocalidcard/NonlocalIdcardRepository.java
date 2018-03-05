package com.liyang.domain.jiandenonlocalidcard;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Administrator
 *
 */
public interface NonlocalIdcardRepository extends JpaRepository<NonlocalIdcard, Integer>{
	
	@Query("select n.idCardNumber from NonlocalIdcard n")
	public List<String> findAllIdcardNumber();
	
}
