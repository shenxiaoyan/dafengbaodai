package com.liyang.domain.role;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.EntityRepository;
import com.liyang.domain.base.StateRepository;


/**
 * @author Administrator
 *
 */
//@RepositoryRestResource(excerptProjection = MenuProjection.class)
public interface RoleStateRepository extends StateRepository<RoleState> {
    @Query("select  c.label  from RoleState c ")
    public  List<RoleState> findAllStateCode();
	
   
}
