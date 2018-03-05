package com.liyang.domain.banner;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.liyang.domain.base.AuditorEntityRepository;

/**
 * @author Administrator
 *
 */
@RepositoryRestResource()
public interface BannerRepository extends AuditorEntityRepository<Banner>, JpaSpecificationExecutor<Banner> {

	Banner save(Banner banner);

	Page<Banner> findAll(Pageable pageable);

	/**
	 * 需求变更，方法废弃,修改完毕后删除
	 * @param list
	 * @return
	 */
	@Deprecated
	@Transactional
	@Modifying
	@Query("update Banner b set b.state=(select s from BannerState s where s.stateCode='OFFSHELF'),b.weight=null where b.id in (?1) ")
	int offshelf(List<Integer> list);

	/**
	 * 需求变更，方法废弃,修改完毕后删除
	 * @return
	 */
	@Deprecated
	@Transactional
	@Modifying
	@Query("update Banner b set b.state=(select s from BannerState s where s.stateCode='OFFSHELF'),b.weight=null where b.state=(select s from BannerState s where s.stateCode='ONSHELF')")
	int offshelfAll();

	@RestResource(exported = false)
	@Query("select b from Banner b where b.state.stateCode='ONSHELF' order by b.weight asc,b.lastModifiedAt desc")
	List<Banner> findAllEnabled();

	Banner findById(Integer id);

	@RestResource(exported = false)
	int countByStateStateCode(String stateCode);
	
	@Override
	@Modifying
	@Query("update Banner b set b.state=(select s from BannerState s where s.stateCode='DELETED') where b.id=?1 ")
	void delete(Integer id);

}
