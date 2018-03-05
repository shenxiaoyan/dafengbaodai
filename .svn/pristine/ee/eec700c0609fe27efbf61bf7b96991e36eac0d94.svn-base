package com.liyang.domain.departmenttype;

import java.util.List;

import javax.transaction.Transactional;

import com.liyang.domain.user.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.liyang.domain.base.AuditorEntityRepository;

/**
 * @author Administrator
 *
 */
@RepositoryRestResource(excerptProjection = DepartmenttypeProjection.class)
public interface DepartmenttypeRepository
		extends AuditorEntityRepository<Departmenttype>, JpaSpecificationExecutor<Departmenttype> {
	public Departmenttype findByCode(@Param("code") String code);

	@Transactional
	@Modifying
	@Query("update Departmenttype t set t.state=(select s from DepartmenttypeState s where s.stateCode='ENABLED') where t.id=?1")
	public int enabled(Integer id);

	@Transactional
	@Modifying
	@Query("update Departmenttype t set t.state=(select s from DepartmenttypeState s where s.stateCode='DISABLED') where t.id=?1")
	public int disabled(Integer id);
	
	@Query("from Departmenttype d where d.state.stateCode='ENABLED'")
	public List<Departmenttype> findAllEnabled();

//	public Departmenttype findByDepartmentType(@Param("DepartmentType") String departmentType);
}