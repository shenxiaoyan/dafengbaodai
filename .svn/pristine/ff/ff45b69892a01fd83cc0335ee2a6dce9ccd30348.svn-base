package com.liyang.listener;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;

import com.liyang.domain.base.AbstractAuditorAct;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.AbstractAuditorState;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.StateRepository;
import com.liyang.domain.user.User;
import com.liyang.service.AbstractAuditorService;
import com.liyang.util.CommonUtil;
import com.liyang.util.CreateValidGroup;
import com.liyang.util.UpdateValidGroup;

/**
 * @author Administrator
 *
 * @param <T>
 * @param <S>
 * @param <A>
 * @param <L>
 */
public abstract class AuditorRestEventListener<T extends AbstractAuditorEntity, S extends AbstractAuditorState, A extends AbstractAuditorAct , L extends AbstractAuditorLog>
		extends AbstractRepositoryEventListener<T> {

	AbstractAuditorService<T, S, A, L> service;

	StateRepository<S> stateRepository;
	
	
	public AbstractAuditorService<T, S, A, L> getService() {
		return service;
	}
	@Autowired
	public void setService(AbstractAuditorService<T, S, A, L> service) {
		this.service = service;
	}

	public StateRepository<S> getStateRepository() {
		return stateRepository;
	}
	@Autowired
	public void setStateRepository(StateRepository<S> stateRepository) {
		this.stateRepository = stateRepository;
	}

	@Override
	@Transactional
	protected void onBeforeCreate(T entity) {
		System.out.println(service.getClass().getSimpleName() + ":onBeforeCreate");
		CommonUtil.validate(entity, CreateValidGroup.class);

		if (entity.getState() == null) {
			S findByStateCode = getStateRepository().findByStateCode("CREATED");
			S findByStateCode2 = getStateRepository().findByStateCode("ENABLED");
			entity.setState(findByStateCode2);
			entity.setBeforeState(findByStateCode);
		}
		A act = getService().getAct(entity, "create",null);
		entity = getService().doBeforeAct(entity, act,null,null);
		super.onBeforeCreate(entity);
	}

	
	@Override
	@Transactional
	protected void onAfterRead(T entity) {
		System.out.println("onAfterRead");
		entity.setBeforeState(entity.getState());
		A act = getService().getAct(entity, "read",null);
		getService().doBeforeAct(entity,act,null,null);
		entity.injectCurrentUserCanActList();
		super.onAfterRead(entity);
	}
	
	@Override
	@Transactional
	protected void onAfterCreate(T entity) {
		System.out.println("onAfterCreate");
		getService().doAfterAct(entity,null,null);
		super.onAfterCreate(entity);
	}

	@Override
	@Transactional
	protected void onBeforeSave(T entity) {
		System.out.println("onBeforeSave");
		CommonUtil.validate(entity, UpdateValidGroup.class);
//		A act = getService().getAct(entity, "update",null);
		A act = null;
		if (entity.getAct() != null && !"".equals(entity.getAct().trim())) {
			act = getService().getAct(entity, entity.getAct(),null);
			
		} else {
			act = getService().getAct(entity, "update",null);
		}
		if (act != null) {
			getService().doBeforeAct(entity, act,null,null);
		}
		super.onBeforeSave(entity);
	}

	@Override
	@Transactional
	protected void onAfterSave(T entity) {
		System.out.println("onAfterSave");
		getService().doAfterAct(entity,null,null);
		super.onAfterSave(entity);
	}

	@Override
	@Transactional
	protected void onBeforeLinkSave(T parent, Object linked) {
		System.out.println("onBeforeLinkSave");
		A act = null;
		if (parent.getAct() != null && !"".equals(parent.getAct().trim())) {
			act = getService().getAct(parent, parent.getAct(), null);
		} else {
			act = getService().getAct(parent, "update", null);
		}
		if (act != null) {
			getService().doBeforeAct(parent, act , linked , null);
		}
		
		super.onBeforeLinkSave(parent, linked);
	}

	@Override
	@Transactional
	protected void onAfterLinkSave(T parent, Object linked) {
		System.out.println("onAfterLinkSave");
		getService().doAfterAct(parent,linked,null);
		super.onAfterLinkSave(parent, linked);
	}

	@Override
	@Transactional
	protected void onBeforeLinkDelete(T parent, Object linked) {
		System.out.println("onBeforeLinkDelete");
		A act = null;
		if (parent.getAct() != null && !"".equals(parent.getAct().trim())) {
			act = getService().getAct(parent, parent.getAct(), null);
		} else {
			act = getService().getAct(parent, "update", null);
		}
		if (act != null) {
			getService().doBeforeAct(parent, act , linked , null);
		}
		super.onBeforeLinkDelete(parent, linked);
	}

	@Override
	@Transactional
	protected void onAfterLinkDelete(T parent, Object linked) {
		System.out.println("onAfterLinkDelete");
		getService().doAfterAct(parent,linked,null);
		super.onAfterLinkDelete(parent, linked);
	}

	@Override
	@Transactional
	protected void onBeforeDelete(T entity) {
		System.out.println("onBeforeDelete");
		A act = getService().getAct(entity, "delete",null);
		getService().doBeforeAct(entity, act,null,null);
		super.onBeforeDelete(entity);
	}

	@Override
	@Transactional
	protected void onAfterDelete(T entity) {
		System.out.println("onAfterDelete");
		getService().doAfterAct(entity,null,null);
		super.onAfterDelete(entity);
	}

}
