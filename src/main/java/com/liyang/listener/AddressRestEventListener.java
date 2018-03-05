package com.liyang.listener;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.liyang.domain.address.Address;
import com.liyang.domain.address.AddressAct;
import com.liyang.domain.address.AddressLog;
import com.liyang.domain.address.AddressState;
import com.liyang.util.CommonUtil;
import com.liyang.util.CreateValidGroup;

/**
 * @author Administrator
 *
 */
@Component
public class AddressRestEventListener extends AuditorRestEventListener<Address, AddressState, AddressAct, AddressLog> {
	@Override
	@Transactional
	protected void onBeforeCreate(Address entity) {
		System.out.println(service.getClass().getSimpleName() + ":onBeforeCreate");
		CommonUtil.validate(entity, CreateValidGroup.class);

		if (entity.getState() == null) {
			AddressState findByStateCode = getStateRepository().findByStateCode("CREATED");
			// 新增时默认为禁用
			AddressState findByStateCode2 = getStateRepository().findByStateCode("DISABLED");
			entity.setState(findByStateCode2);
			entity.setBeforeState(findByStateCode);
		}
		AddressAct act = getService().getAct(entity, "create", null);
		entity = getService().doBeforeAct(entity, act, null, null);
	}
}
