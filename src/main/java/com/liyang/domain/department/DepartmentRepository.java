package com.liyang.domain.department;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.departmenttype.Departmenttype;
/**
 * @author Administrator
 *
 */
@RepositoryRestResource(excerptProjection = DepartmentProjection.class) 
public interface DepartmentRepository extends AuditorEntityRepository<Department>,JpaSpecificationExecutor<Department> {
	
	@Query("from Department d where d.state.stateCode = ?1")
	public Page<Department> getByStateCode(@Param("stateCode") String stateCode , Pageable p); 
	
	@RestResource(path = "departWithValStaCode", rel = "departWithValStaCode")
	@Query("from Department d where d.state.stateCode ='CREATED'")		//	正常
	public List<Department> findByValidStateCode();	
	
	public List<Department> findByLabelAndType(@Param("label") String label,@Param("type") Departmenttype departmentType);	
	
	public List<Department> findByTypeLabel(@Param("departmenttypeLabel") String departmenttypeLabel);

	public  List<Department> findByTypeAndLabel(@Param("label") String departmentLabel,@Param("type")Departmenttype departmenttype);
	@RestResource(path="findByTypeLabelAndStateCode")
	public List<Department> findByTypeLabelAndStateStateCode(@Param("departmenttypeLabel") String departmenttypeLabel, @Param("stateCode")String stateCode);
	
	@Query("from Department d where d.type.id = ?1")
	public Page<Department> findByTypeId(@Param("id") Integer id, Pageable p);
	
	@Query("from Department d where d.state.stateCode='ENABLED'")
	public List<Department> findAllEnabled();
	
	public Department findByLabel(String label);
	
}