package com.liyang.domain.base;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

/**
 * @author Administrator
 *
 * @param <T>
 */
@NoRepositoryBean
public interface StateRepository<T extends AbstractAuditorState> extends EntityRepository<T> {
	public T findByStateCode(@Param("stateCode") String stateCode);
	
	@Query("select s from #{#entityName} s where s.stateCode<>'CREATED' and s.stateCode<>'DELETED' order by s.sort")
	List<T> findForShow();
}
