package com.liyang.domain.salesman;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.department.Department;

/**
 * 代理人Repository
 * @author Administrator
 *
 */
public interface SalesmanRepository extends AuditorEntityRepository<Salesman>,JpaSpecificationExecutor<Salesman>{
	
	
	public List<Salesman> findByDepartmentAndStateStateCode(Department department, String stateCode);

	public Salesman findByNameAndPhoneNumber(String name, String phoneNumber);
	
	@RestResource(path="findByDepartmentId")
	public List<Salesman> findByDepartment_Id(@Param("departmentId")Integer departmentId);

	@RestResource(path="findByDepartmentIdAndStateCode")
	public List<Salesman> findByDepartment_IdAndStateStateCode(@Param("departmentId")Integer departmentId, @Param("stateCode")String stateCode);
	
	public Salesman findByNameAndDepartment_Id(String name, Integer departmentId);
	
	public Salesman findByNameAndDepartment(String salesmanName, Department dep);

	public List<Salesman> findByStateStateCode(String stateCode);

	public Salesman findById(@Param("id") Integer id);
	
	
	
//	public List<Salesman> findByDepartmentId(@Param("departmentId")Integer departmentId);
}
