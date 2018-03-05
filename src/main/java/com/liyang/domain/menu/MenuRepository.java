package com.liyang.domain.menu;

import java.util.List;

import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.role.Role;


/**
 * 菜单
 * @author Administrator
 *
 */
//@RepositoryRestResource(excerptProjection = MenuProjection.class)
public interface MenuRepository extends AuditorEntityRepository<Menu> {
	List<Menu> findByVisibleRolesIn(Role role);
}
