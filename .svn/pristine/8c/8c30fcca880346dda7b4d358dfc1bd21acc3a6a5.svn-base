package com.liyang.listener;

import javax.transaction.Transactional;

import org.hibernate.jdbc.AbstractWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;

import com.liyang.domain.base.AbstractWorkflow;
import com.liyang.domain.base.AbstractWorkflowAct;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.AbstractWorkflowFile;
import com.liyang.domain.base.AbstractWorkflowLog;
import com.liyang.domain.base.AbstractWorkflowState;
import com.liyang.domain.base.StateRepository;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;
import com.liyang.util.CommonUtil;
import com.liyang.util.CreateValidGroup;
import com.liyang.util.UpdateValidGroup;

/**
 * @author Administrator
 *
 * @param <T>
 * @param <W>
 * @param <A>
 * @param <S>
 * @param <L>
 * @param <F>
 */
public abstract class WorkflowRestEventListener<T extends AbstractWorkflowEntity, W extends AbstractWorkflow, A extends AbstractWorkflowAct, S extends AbstractWorkflowState, L extends AbstractWorkflowLog, F extends AbstractWorkflowFile>
		extends AuditorRestEventListener<T, S, A, L> {

	AbstractWorkflowService<T, W, A, S, L, F> service;

	StateRepository<S> stateRepository;

	@Override
	public AbstractWorkflowService<T, W, A, S, L, F> getService() {
		return service;
	}

	@Autowired
	public void setService(AbstractWorkflowService<T, W, A, S, L, F> service) {
		this.service = service;
	}

	@Override
	public StateRepository<S> getStateRepository() {
		return stateRepository;
	}

	@Autowired
	@Override
	public void setStateRepository(StateRepository<S> stateRepository) {
		this.stateRepository = stateRepository;
	}

	@Override
	@Transactional
	protected void onAfterRead(T entity) {
		entity.injectStateActList();
		super.onAfterRead(entity);
	}

}
