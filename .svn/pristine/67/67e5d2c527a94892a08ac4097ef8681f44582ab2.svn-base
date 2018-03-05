package com.liyang.listener;

import javax.transaction.Transactional;

import com.liyang.domain.salesman.Salesman;
import com.liyang.domain.salesman.SalesmanAct;
import com.liyang.domain.salesman.SalesmanLog;
import com.liyang.domain.salesman.SalesmanState;
import com.liyang.util.CommonUtil;
import com.liyang.util.CreateValidGroup;

/**
 * @author Administrator
 *
 */
public class SalesmanRestEventListener extends AuditorRestEventListener<Salesman, SalesmanState, SalesmanAct, SalesmanLog> {
//	@Override
//	@Transactional
//	protected void onBeforeCreate(Salesman salesman) {
//		System.out.println(service.getClass().getSimpleName() + ":onBeforeCreate");
//		CommonUtil.validate(salesman, CreateValidGroup.class);
//
//		if (salesman.getState() == null) {
//			SalesmanState findByStateCode = getStateRepository().findByStateCode("CREATED");
//			SalesmanState findByStateCode2 = getStateRepository().findByStateCode("DISABLED");
//			salesman.setState(findByStateCode2);
//			salesman.setBeforeState(findByStateCode);
//		}
//		SalesmanAct act = getService().getAct(salesman, "create",null);
//		salesman = getService().doBeforeAct(salesman, act,null,null);
//		super.onBeforeCreate(salesman);
//	}
}
