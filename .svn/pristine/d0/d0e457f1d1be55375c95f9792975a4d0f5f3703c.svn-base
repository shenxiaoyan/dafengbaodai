package com.liyang.domain.department;
/**  
* 类说明   
* @author lcj 
* @date 2017年10月13日  新建  
*/
import org.springframework.data.rest.core.config.Projection;

import com.liyang.domain.departmenttype.Departmenttype;



///rest/roles?projection=roleLabelAndRoleCode
@Projection(name="labelAndDepartmentType",types={Department.class})
public interface DepartmentLabelAndDepartmentTypeProjection {
		String getLabel(); 
		Departmenttype getType(); 

}
