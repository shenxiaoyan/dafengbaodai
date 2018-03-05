package com.liyang.domain.claimmatch;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liyang.domain.user.User;

/**
 * @author Administrator
 *
 */
public interface ClaimMatchRepository extends JpaRepository<ClaimMatch, Integer>{
	
//	@Modifying
//	@Query("delete from ClaimMatch c where c.createdBy=?1")
//	public void deleteByCreatedby(User user);
	
	public List<ClaimMatch> findByCreatedBy(User user);
	
	
}
