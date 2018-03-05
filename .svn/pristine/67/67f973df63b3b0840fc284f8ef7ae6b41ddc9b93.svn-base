package com.liyang.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.advertise.Advertise;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.otherInsure.OtherInsure;
import com.liyang.domain.otherInsure.OtherInsureAct;
import com.liyang.domain.otherInsure.OtherInsureActRepository;
import com.liyang.domain.otherInsure.OtherInsureLog;
import com.liyang.domain.otherInsure.OtherInsureLogRepository;
import com.liyang.domain.otherInsure.OtherInsureRepository;
import com.liyang.domain.otherInsure.OtherInsureState;
import com.liyang.domain.otherInsure.OtherInsureStateRepository;
import com.liyang.domain.role.RoleAct;
import com.liyang.domain.role.RoleRepository;

/**
 * @author Administrator
 *
 */
@Service
@Order(1)
public class OtherInsureService
		extends AbstractAuditorService<OtherInsure, OtherInsureState, OtherInsureAct, OtherInsureLog> {

	@Autowired
	OtherInsureActRepository otherInsureActRepository;

	@Autowired
	OtherInsureStateRepository otherInsureStateRepository;

	@Autowired
	OtherInsureRepository otherInsureRepository;

	@Autowired
	OtherInsureLogRepository otherInsureLogRepository;

	@Autowired
	RoleRepository roleRepository;

	@Override
	@PostConstruct
	public void sqlInit() {
		// List<OtherInsureAct> findAll = otherInsureActRepository.findAll();
		// if(findAll == null || findAll.isEmpty()){
		if (otherInsureActRepository.count() <= 0) {
			OtherInsureState state1 = otherInsureStateRepository.save(new OtherInsureState("正常", 0, "ENABLED"));

			OtherInsureAct save1 = otherInsureActRepository
					.save(new OtherInsureAct("创建", "create", 10, ActGroup.UPDATE));
			OtherInsureAct save2 = otherInsureActRepository.save(new OtherInsureAct("读取", "read", 20, ActGroup.READ));
			OtherInsureAct save3 = otherInsureActRepository
					.save(new OtherInsureAct("更新", "update", 30, ActGroup.UPDATE));
			OtherInsureAct save4 = otherInsureActRepository
					.save(new OtherInsureAct("删除", "delete", 40, ActGroup.UPDATE));
		}
	}

	@Override
	public LogRepository<OtherInsureLog> getLogRepository() {
		// TODO Auto-generated method stub
		return otherInsureLogRepository;
	}

	@Override
	public AuditorEntityRepository<OtherInsure> getAuditorEntityRepository() {
		// TODO Auto-generated method stub
		return otherInsureRepository;
	}

	@Override
	@PostConstruct
	public void injectLogRepository() {
		new Advertise().setLogRepository(otherInsureLogRepository);

	}

	@Override
	public OtherInsureLog getLogInstance() {
		// TODO Auto-generated method stub
		return new OtherInsureLog();
	}

	@Override
	public ActRepository<OtherInsureAct> getActRepository() {
		// TODO Auto-generated method stub
		return otherInsureActRepository;
	}

	@Override
	@PostConstruct
	public void injectEntityService() {
		new OtherInsure().setService(this);

	}
}
