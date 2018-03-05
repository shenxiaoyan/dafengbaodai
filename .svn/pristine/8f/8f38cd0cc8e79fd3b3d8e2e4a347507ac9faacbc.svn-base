package com.liyang.domain.createenquiry;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowState;


/**
 * 询价状态
 * @author Administrator
 *
 */
@Entity
@Table(name = "create_enquiry_state")
@Cacheable
public class CreateEnquiryState extends AbstractWorkflowState<CreateEnquiry, CreateEnquiryWorkflow, CreateEnquiryAct> {
	
	private static final long serialVersionUID = 1L;

	public CreateEnquiryState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
	}

	public CreateEnquiryState() {
		super();
	}
}
