package com.liyang.domain.base;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.liyang.annotation.Info;
import com.liyang.message.EnumOperationMessageType;
/**
 * 工作流日志
 * @author Administrator
 *
 * @param <E>
 * @param <W>
 * @param <S>
 * @param <A>
 * @param <F>
 */
@MappedSuperclass
public  class AbstractWorkflowLog<E extends AbstractWorkflowEntity,
						W extends AbstractWorkflow,
						S extends AbstractWorkflowState , A extends AbstractWorkflowAct, F extends AbstractWorkflowFile> extends AbstractAuditorLog<E,S,A>{




	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="workflow_id" )
	@Info(label="工作流",placeholder="",tip="",help="",secret="")					
	private W workflow;
	
	@Enumerated(EnumType.STRING)
	@Column(name="message_type")
	@Info(label="消息类型",placeholder="",tip="",help="行为记录在前台展示的几种类型",secret="与TIM的消息类型对应起来")					
	private EnumOperationMessageType messageType;
	
 	@OneToMany(cascade=CascadeType.ALL,mappedBy="log")
	private Set<F> files= new HashSet<F>();



	public Set<F> getFiles() {
		return files;
	}


	public void setFiles(Set<F> files) {
		this.files = files;
	}


	public EnumOperationMessageType getMessageType() {
		return messageType;
	}
	


	public void setMessageType(EnumOperationMessageType messageType) {
		this.messageType = messageType;
	}
	public W getWorkflow() {
		return workflow;
	}

	public void setWorkflow(W workflow) {
		this.workflow = workflow;
	}


	@Override
	public A getAct() {
		return super.getAct();
	}
	@Override
	public void setAct(A act) {
		super.setAct(act);
	}

	



}
