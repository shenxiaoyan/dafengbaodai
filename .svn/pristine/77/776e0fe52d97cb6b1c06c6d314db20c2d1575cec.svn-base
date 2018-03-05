package com.liyang.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.timgroup.TIMGroup;
import com.liyang.domain.timgroup.TIMGroupAct;
import com.liyang.domain.timgroup.TIMGroupActRepository;
import com.liyang.domain.timgroup.TIMGroupLog;
import com.liyang.domain.timgroup.TIMGroupLogRepository;
import com.liyang.domain.timgroup.TIMGroupRepository;
import com.liyang.domain.timgroup.TIMGroupState;
import com.liyang.domain.timgroup.TIMGroupStateRepository;

/**
 * @author Administrator
 *
 */
@Service
public class TIMGroupService extends AbstractAuditorService<TIMGroup, TIMGroupState, TIMGroupAct, TIMGroupLog> {

	@Autowired
	TIMGroupActRepository groupActRepository;

	@Autowired
	TIMGroupStateRepository groupStateRepository;

	@Autowired
	TIMGroupRepository groupRepository;

	@Autowired
	TIMGroupLogRepository groupLogRepository;

	@Override
	@PostConstruct
	public void sqlInit() {
		// List<TIMGroupAct> findAll = groupActRepository.findAll();
		// if(findAll == null || findAll.isEmpty()){
		if (groupActRepository.count() <= 0) {

			TIMGroupAct save1 = groupActRepository.save(new TIMGroupAct("创建", "create", 10, ActGroup.UPDATE));
			TIMGroupAct save2 = groupActRepository.save(new TIMGroupAct("读取", "read", 20, ActGroup.READ));
			TIMGroupAct save3 = groupActRepository.save(new TIMGroupAct("更新", "update", 30, ActGroup.UPDATE));
			TIMGroupAct save4 = groupActRepository.save(new TIMGroupAct("删除", "delete", 40, ActGroup.UPDATE));
			TIMGroupAct save5 = groupActRepository.save(new TIMGroupAct("自己的列表", "listOwn", 50, ActGroup.READ));
			TIMGroupAct save6 = groupActRepository
					.save(new TIMGroupAct("部门的列表", "listOwnDepartment", 60, ActGroup.READ));
			TIMGroupAct save7 = groupActRepository
					.save(new TIMGroupAct("部门和下属部门的列表", "listOwnDepartmentAndChildren", 70, ActGroup.READ));
			TIMGroupAct save8 = groupActRepository.save(new TIMGroupAct("通知其他人", "noticeOther", 80, ActGroup.OPERATE));
			TIMGroupAct save9 = groupActRepository
					.save(new TIMGroupAct("通知操作者", "noticeActUser", 90, ActGroup.OPERATE));
			TIMGroupAct save10 = groupActRepository
					.save(new TIMGroupAct("通知展示人", "noticeShowUser", 100, ActGroup.OPERATE));

			groupStateRepository.save(new TIMGroupState("已创建", 0, "CREATED"));
			TIMGroupState groupState = new TIMGroupState("有效", 100, "ENABLED");
			groupState.setActs(Arrays.asList(save1, save2, save3, save4, save5, save6, save7).stream()
					.collect(Collectors.toSet()));
			groupStateRepository.save(groupState);
			groupStateRepository.save(new TIMGroupState("无效", 200, "DISABLED"));
			groupStateRepository.save(new TIMGroupState("已删除", 300, "DELETED"));
		}

	}

	@Override
	public LogRepository<TIMGroupLog> getLogRepository() {
		// TODO Auto-generated method stub
		return groupLogRepository;
	}

	@Override
	public AuditorEntityRepository<TIMGroup> getAuditorEntityRepository() {
		// TODO Auto-generated method stub
		return groupRepository;
	}

	@Override
	@PostConstruct
	public void injectLogRepository() {
		new TIMGroup().setLogRepository(groupLogRepository);

	}

	@Override
	public TIMGroupLog getLogInstance() {
		// TODO Auto-generated method stub
		return new TIMGroupLog();
	}

	@Override
	public ActRepository<TIMGroupAct> getActRepository() {
		// TODO Auto-generated method stub
		return groupActRepository;
	}

	@Override
	@PostConstruct
	public void injectEntityService() {
		new TIMGroup().setService(this);

	}

}
