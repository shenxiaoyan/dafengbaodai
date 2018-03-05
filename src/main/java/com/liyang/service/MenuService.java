package com.liyang.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.menu.Menu;
import com.liyang.domain.menu.MenuAct;
import com.liyang.domain.menu.MenuActRepository;
import com.liyang.domain.menu.MenuLog;
import com.liyang.domain.menu.MenuLogRepository;
import com.liyang.domain.menu.MenuRepository;
import com.liyang.domain.menu.MenuState;
import com.liyang.domain.menu.MenuStateRepository;
import com.liyang.domain.role.RoleRepository;

/**
 * @author Administrator
 *
 */
@Service
@Order(100000)
public class MenuService extends AbstractAuditorService<Menu, MenuState, MenuAct, MenuLog> {

	@Autowired
	MenuActRepository menuActRepository;

	@Autowired
	MenuStateRepository menuStateRepository;

	@Autowired
	MenuRepository menuRepository;

	@Autowired
	MenuLogRepository menuLogRepository;

	@Autowired
	RoleRepository roleRepository;

	@Override
	@PostConstruct
	public void sqlInit() {
		// List<MenuAct> findAll = menuActRepository.findAll();
		// if(findAll == null || findAll.isEmpty()){
		if (menuActRepository.count() <= 0) {

			MenuAct save1 = menuActRepository.save(new MenuAct("创建", "create", 10, ActGroup.UPDATE));
			MenuAct save2 = menuActRepository.save(new MenuAct("读取", "read", 20, ActGroup.READ));
			MenuAct save3 = menuActRepository.save(new MenuAct("更新", "update", 30, ActGroup.UPDATE));
			MenuAct save4 = menuActRepository.save(new MenuAct("删除", "delete", 40, ActGroup.UPDATE));
			MenuAct save5 = menuActRepository.save(new MenuAct("自己的列表", "listOwn", 50, ActGroup.READ));
			MenuAct save6 = menuActRepository.save(new MenuAct("部门的列表", "listOwnDepartment", 60, ActGroup.READ));
			MenuAct save7 = menuActRepository
					.save(new MenuAct("部门和下属部门的列表", "listOwnDepartmentAndChildren", 70, ActGroup.READ));
			MenuAct save8 = menuActRepository.save(new MenuAct("通知其他人", "noticeOther", 80, ActGroup.NOTICE));
			MenuAct save9 = menuActRepository.save(new MenuAct("通知操作者", "noticeActUser", 90, ActGroup.NOTICE));
			MenuAct save10 = menuActRepository.save(new MenuAct("通知展示人", "noticeShowUser", 100, ActGroup.NOTICE));

			menuStateRepository.save(new MenuState("已创建", 0, "CREATED"));
			MenuState menuState = new MenuState("有效", 100, "ENABLED");
			menuState.setActs(Arrays.asList(save1, save2, save3, save4, save5, save6, save7).stream()
					.collect(Collectors.toSet()));
			menuStateRepository.save(menuState);
			menuStateRepository.save(new MenuState("无效", 200, "DISABLED"));
			menuStateRepository.save(new MenuState("已删除", 300, "DELETED"));

		}

	}

	@Override
	public LogRepository<MenuLog> getLogRepository() {
		// TODO Auto-generated method stub
		return menuLogRepository;
	}

	@Override
	public AuditorEntityRepository<Menu> getAuditorEntityRepository() {
		// TODO Auto-generated method stub
		return menuRepository;
	}

	@Override
	@PostConstruct
	public void injectLogRepository() {
		new Menu().setLogRepository(menuLogRepository);

	}

	@Override
	public MenuLog getLogInstance() {
		// TODO Auto-generated method stub
		return new MenuLog();
	}

	@Override
	public ActRepository<MenuAct> getActRepository() {
		// TODO Auto-generated method stub
		return menuActRepository;
	}

	@Override
	@PostConstruct
	public void injectEntityService() {
		new Menu().setService(this);

	}

}
