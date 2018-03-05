package com.liyang.domain.user;

import java.util.List;
import java.util.Set;

import com.liyang.domain.departmenttype.Departmenttype;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.jpa.query.expression.generic.SpecificationQueryRepository;
import com.liyang.domain.base.WorkflowEntityRepository;
import com.liyang.domain.department.Department;
import com.liyang.domain.role.Role;

//@RepositoryRestResource(excerptProjection = UserProjection.class)
/**
 * web端用户
 * 
 * @author Administrator
 *
 */
public interface UserRepository extends WorkflowEntityRepository<User>, SpecificationQueryRepository<User> {

	public User findByOpenid(@Param("openid") String openid);

	public List<User> findByRoleRoleCode(@Param("roleCode") String roleCode);

	public User findByUnionid(@Param("unionid") String unionid);

	public User findByUsernameAndStateStateCodeIn(@Param("username") String username,
			@Param("stateCode") String[] stateCode);

	public User findByUsernameAndIdNotAndStateStateCodeIn(String username, Integer id, String[] stateCodes);

	@RestResource(exported = false)
	public List<User> findByDepartmentAndRole(Department department, Role role);

	@RestResource(exported = false)
	public List<User> findByDepartmentIn(List<Department> ids);

	public User getById(Integer id);

	public User save(User user);

	@Query("select  c from User c where c.state.stateCode = ?1")
	public Page<User> getByStateCode(@Param("stateCode") String state, Pageable p);

//	public User findByUsername(@Param("username") String username);
	public List<User> findByUsername(@Param("username") String username);

	public List<User> findByRoleIn(Set<Role> roles);

	public User findByUsernameAndRole_RoleCodeAndDepartment_Id(String username, String roleCode, Integer departmentId);
	
	public List<User> findByUsernameAndStateStateCode(String username, String stateCode);

	public User findById(Integer id);
}