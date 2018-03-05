package com.liyang.domain.menu;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.EntityRepository;
import com.liyang.domain.base.StateRepository;


/**
 * 菜单状态
 * @author Administrator
 *
 */
//@RepositoryRestResource(excerptProjection = MenuProjection.class)
public interface MenuStateRepository extends StateRepository<MenuState> {
	
}
