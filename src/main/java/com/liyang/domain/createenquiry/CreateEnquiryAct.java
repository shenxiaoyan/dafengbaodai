package com.liyang.domain.createenquiry;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowAct;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;


/**
 * @author Administrator
 *
 */
@Entity
@Table(name="create_enquiry_act")
@Cacheable
public class CreateEnquiryAct extends AbstractWorkflowAct<CreateEnquiryState,CreateEnquiryWorkflow> {
	
	public CreateEnquiryAct(String label, String actCode, Integer sort, ActGroup actGroup) {
		super(label, actCode, sort, actGroup);
		// TODO Auto-generated constructor stub
	}
	public CreateEnquiryAct(){
		super();
	}
}
