package com.liyang.domain.base;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import com.liyang.domain.department.Department;
import com.liyang.domain.menu.Menu;
import com.liyang.domain.user.User;

/**
 * @author Administrator
 *
 * @param <T>
 */
@NoRepositoryBean
public interface AuditorEntityRepository<T extends AbstractAuditorEntity> extends EntityRepository<T> {
	

	@Query("select m from #{#entityName} m JOIN m.state s where s.stateCode = :stateCode and m.createdBy = ?#{principal} ")
	@PostAuthorize("hasPermission(returnObject ,'listOwn')")
	public Page<T> listStateOwn(@Param("stateCode") String stateCode, Pageable pageable);

	@Query("select m from #{#entityName} m JOIN m.state s where s.stateCode = :stateCode and m.createdByDepartment = ?#{principal?.department} ")
	@PostAuthorize("hasPermission(returnObject ,'listOwnDepartment')")
	public Page<T> listStateOwnDepartment(@Param("stateCode") String stateCode,Pageable pageable);

	@Query("select m from #{#entityName} m JOIN m.state s where s.stateCode = :stateCode and m.createdByDepartment in ?#{principal?.childrenDepartments} ")
	@PostAuthorize("hasPermission(returnObject ,'listOwnDepartmentAndChildren')")
	public Page<T> listStateOwnDepartmentAndChildren(@Param("stateCode") String stateCode, Pageable pageable);
	
	@Query("select m from #{#entityName} m JOIN m.state s where  s.stateCode != 'DELETED' and m.createdBy.id = ?#{principal.id} ")
	@PostAuthorize("hasPermission(returnObject ,'listOwn')")
	public Page<T> listOwn(Pageable pageable);

	@Query("select m from #{#entityName} m JOIN m.state s where s.stateCode != 'DELETED' and m.createdByDepartment = ?#{principal?.department} ")
	@PostAuthorize("hasPermission(returnObject ,'listOwnDepartment')")
	public Page<T> listOwnDepartment(Pageable pageable);

	@Query("select m from #{#entityName} m JOIN m.state s where s.stateCode != 'DELETED' and m.createdByDepartment in ?#{principal?.childrenDepartments} ")
	@PostAuthorize("hasPermission(returnObject ,'listOwnDepartmentAndChildren')")
	public Page<T> listOwnDepartmentAndChildren(Pageable pageable);

	@Override
	@PostAuthorize("hasPermission(returnObject ,'read')")
	public T findOne(Integer id);

	@Override
	@Modifying
	@Query(value="update #{#entityName} as m set m.state = (select n from m.state as n where m.state.stateCode = 'DELETED') where m.id=?1")
	void delete(Integer id);
	
	
	
}
