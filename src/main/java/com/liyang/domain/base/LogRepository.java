package com.liyang.domain.base;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import com.liyang.domain.user.User;

/**
 * @author Administrator
 *
 * @param <T>
 */
@SuppressWarnings("rawtypes")
@NoRepositoryBean
public interface LogRepository<T extends AbstractAuditorLog>
		extends JpaRepository<T, Integer>, JpaSpecificationExecutor<T> {

	@Query(value="select t.noticeTo from #{#entityName} t  where t.createdBy = ?#{principal} and t.act.id =:act_id  group by t.noticeTo order by t.createdAt desc")
	public List<User> latestNoticeUser(@Param("act_id") Integer actId);
	
	@Query(value = "select max(id) from #{#entityName} t group by t.entity.id")
	public List<Integer> findMaxIdGroupByEntityId();

//	@RestResource(exported = false)
//	public List<T>  findByEntityAndCreatedByAndStateCodeNot(AbstractAuditorEntity entity,User user, String state, Sort sort);
//	@RestResource(exported = false)
//	public Page<T>  findByEntityAndCreatedByAndStateCodeNot(AbstractAuditorEntity entity,User user, String state, Pageable page);
//	
//	@RestResource(exported = false)
//	public List<T>  findByEntityAndStateCodeNot(AbstractAuditorEntity entity, String state);
//	@RestResource(exported = false)
//	public List<T>  findByEntityAndStateCodeNot(AbstractAuditorEntity entity, String state, Sort sort);
//	@RestResource(exported = false)
//	public Page<T>  findByEntityAndStateCodeNot(AbstractAuditorEntity entity, String state, Pageable page);
	
	

}
