package com.liyang.domain.role;
/**  
* 类说明   
* @author lcj 
* @date 2017年10月13日  新建  
*/

import org.springframework.data.rest.core.config.Projection;


//   /rest/roles?projection=roleLabelAndRoleCode
@Projection(name="roleLabelAndRoleCode",types={Role.class})
public interface RoleLabelAndRoleCodeProjection {
	
    /**
     * 获取角色身份
     * @return
     */
    String getLabel(); 
    
    /**
     * 获取角色身份码
     * @return
     */
    String getRoleCode(); 

}

