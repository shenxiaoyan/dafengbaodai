package com.liyang.domain.base;

import java.util.Set;

import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * 工作流状态
 * 
 * @author Administrator
 *
 * @param <E>
 * @param <W>
 * @param <A>
 */
@MappedSuperclass
public class AbstractWorkflowState<E extends AbstractWorkflowEntity, W extends AbstractWorkflow, A extends AbstractWorkflowAct>
		extends AbstractAuditorState<E, A> {

	public AbstractWorkflowState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public AbstractWorkflowState() {
		super();
	}

	@ManyToMany(mappedBy = "states")
	private Set<W> workflows;

	@OneToMany(mappedBy = "targetState")
	private Set<A> fromActs;

	@Transient
	private Boolean done = false;

	public Boolean getDone() {
		return this.done;
	}

	public Set<A> getFromActs() {
		return fromActs;
	}

	public void setFromActs(Set<A> fromActs) {
		this.fromActs = fromActs;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}

	public Set<W> getWorkflows() {
		return workflows;
	}

	public void setWorkflows(Set<W> workflows) {
		this.workflows = workflows;
	}

}
