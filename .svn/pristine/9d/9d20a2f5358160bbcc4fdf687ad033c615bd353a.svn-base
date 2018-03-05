package com.liyang.domain.base;

import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.role.Role;
import com.liyang.message.EnumOperationMessageType;
/**
 * @author Administrator
 *
 * @param <S>
 * @param <W>
 */
@MappedSuperclass
public class AbstractWorkflowAct<S extends AbstractWorkflowState,W extends AbstractWorkflow> extends AbstractAuditorAct<S>{
	

	public AbstractWorkflowAct(String label, String actCode, Integer sort, ActGroup actGroup) {
		super(label, actCode, sort, actGroup);
		// TODO Auto-generated constructor stub
	}
	public AbstractWorkflowAct(){
		super();
	}

	@Enumerated(EnumType.STRING)
	@Column(name="act_type")
	private ActType actType = ActType.NORMAL;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="target_state_id")
	private S targetState;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="start_workflow_id")
	private W startWorkflow;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="next_workflow_id")
	private W nextWorkflow;
	
	//顶级目录
	@Info(label="一级目录",placeholder="",tip="",help="用于上传图片分类",secret="")				
	private String topcategory="-";
	//子级目录
	@Info(label="二级目录",placeholder="",tip="",help="用于上传图片分类",secret="")				
	private String subcategory="-";
	
	
	@Transient
	private Boolean done=false;

	

	public String getTopcategory() {
		return topcategory;
	}
	public void setTopcategory(String topcategory) {
		this.topcategory = topcategory;
	}
	public String getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}
	public ActType getActType() {
		return actType;
	}


	public void setActType(ActType actType) {
		this.actType = actType;
	}

	public W getStartWorkflow() {
		return startWorkflow;
	}
	public void setStartWorkflow(W startWorkflow) {
		this.startWorkflow = startWorkflow;
	}
	public Boolean getDone(){
		return this.done;
	}
	
	public void setDone(Boolean done){
		this.done = done;
	}

	public S getTargetState() {
		return targetState;
	}

	public void setTargetState(S targetState) {
		this.targetState = targetState;
	}


	public W getNextWorkflow() {
		return nextWorkflow;
	}


	public void setNextWorkflow(W nextWorkflow) {
		this.nextWorkflow = nextWorkflow;
	}


	public static enum ActType{
		START,NORMAL,END
	}	

}
