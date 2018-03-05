package com.liyang.domain.base;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import com.liyang.domain.role.Role;

/**
 * @author Administrator
 *
 * @param <T>
 */
@NoRepositoryBean
public interface ActRepository<T extends AbstractAuditorAct> extends EntityRepository<T> {
	public T findByActCode(@Param("actCode") String actCode);
	public List<T> findByIdInAndRolesRoleCode(List<Integer> ids , String roleCode);
}
