package com.liyang.domain.identify;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * 身份信息
 * @author Administrator
 *
 */
@RepositoryRestResource
public interface IdentifyRepository extends JpaRepository<Identify, Integer>{
	public List<Identify> getByPhone(String phone);
	
	@Transactional
	public Identify save(Identify identify);
	
	public List<Identify> findByPhoneAndCodeAndExpireGreaterThan(String phone,String code, Date expire);
	
}
