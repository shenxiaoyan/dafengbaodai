package com.liyang.domain.myinvite;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * 用户个人邀请码
 * @author Administrator
 *
 */
//@RepositoryRestResource
public interface MyInviteRepository extends JpaRepository<MyInvite, Integer>{
	@Query(value = "select count(*) from MyInvite")
	public Integer size();
	
	public MyInvite save(MyInvite invite);
	
	@Query(value="from MyInvite m where m.tag = ?1 ")
	public Page<MyInvite> getOneMyInvite(int tag ,org.springframework.data.domain.Pageable pageable);
//	
//	@Query(value="from MyInvite where tag = ?1 ")
//	public Page<MyInvite> getOneMyInvite(int tag ,org.springframework.data.domain.Pageable pageable);
}

