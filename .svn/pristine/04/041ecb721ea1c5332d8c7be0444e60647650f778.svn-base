package com.liyang.listener;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.liyang.domain.departmenttype.Departmenttype;
import com.liyang.domain.departmenttype.DepartmenttypeAct;
import com.liyang.domain.departmenttype.DepartmenttypeLog;
import com.liyang.domain.departmenttype.DepartmenttypeState;
import com.liyang.util.CommonUtil;
import com.liyang.util.CreateValidGroup;

/**
 * @author Administrator
 *
 */
@Component
public class DepartmenttypeRestEventListener
		extends AuditorRestEventListener<Departmenttype, DepartmenttypeState, DepartmenttypeAct, DepartmenttypeLog> {
	@Override
	@Transactional
	protected void onBeforeCreate(Departmenttype entity) {
		System.out.println(service.getClass().getSimpleName() + ":onBeforeCreate");
		CommonUtil.validate(entity, CreateValidGroup.class);

		if (entity.getState() == null) {
			DepartmenttypeState findByStateCode = getStateRepository().findByStateCode("CREATED");
			//新增时默认为禁用
			DepartmenttypeState findByStateCode2 = getStateRepository().findByStateCode("DISABLED");
			entity.setState(findByStateCode2);
			entity.setBeforeState(findByStateCode);
		}
		DepartmenttypeAct act = getService().getAct(entity, "create", null);
		entity = getService().doBeforeAct(entity, act, null, null);
	}
}
