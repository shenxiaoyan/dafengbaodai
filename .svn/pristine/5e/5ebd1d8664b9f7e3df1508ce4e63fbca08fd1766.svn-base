package com.liyang.domain.upgradeapply;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


/**
 * @author Administrator
 *
 */
@RepositoryRestResource
public interface UpgradeApplyRepository extends JpaRepository<UpgradeApply, Integer>{
	@Transactional
	public UpgradeApply save(UpgradeApply apply);
	
	@Query("from UpgradeApply u")
	public List<UpgradeApply> query();
	
	@Query("from UpgradeApply u where u.phone = ?1  order by u.applyTime desc")
	public List<UpgradeApply> queryLatest(String phone);
	
	public UpgradeApply queryById(Integer id);
}
