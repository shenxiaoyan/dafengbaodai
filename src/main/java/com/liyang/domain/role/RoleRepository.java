package com.liyang.domain.role;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.liyang.domain.base.AuditorEntityRepository;

/**
 * @author Administrator
 *
 */
//@RepositoryRestResource(excerptProjection = RoleProjection.class)
public interface RoleRepository extends AuditorEntityRepository<Role>,JpaSpecificationExecutor<Role> {
	public Role findByRoleCode(String roleCode);

	@Query("select c from Role c where c.state.stateCode = ?1")
	public Page<Role> getByStateCode(@Param("stateCode") String state, Pageable p);

//	rest/roles/search/roleWithValStaCode
	@RestResource(path = "roleWithValStaCode", rel = "roleWithValStaCode")
	@Query("select c from Role c where c.state.stateCode ='ENABLED'")		//有效
	public List<Role> findByValidStateCode();
	
	public List<Role> findByIdIn(Integer[] ids);
	@Query("select c from Role c where c.label=?1 and c.roleCode=?2 ")
	public Role findByLabelAndRoleCode(String label,String roleCode);
    //修改角色信息
	public Role findByRoleCodeAndIdNotAndStateStateCodeIn(String roleCode,Integer id,String[] stateCodes);
	public Role findByLabelAndIdNotAndStateStateCodeIn(String label,Integer id,String[] stateCodes);

	//添加角色信息
	public Role findByLabelAndStateStateCodeIn(@Param("label")String label,@Param("stateCode")String[] stateCodes);
	public Role findByRoleCodeAndStateStateCodeIn(@Param("roleCode")String roleCode,@Param("stateCode")String[] stateCodes);

}
