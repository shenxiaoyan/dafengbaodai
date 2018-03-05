package com.liyang.domain.submitproposal;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowAct;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;

/**
 * 投保订单act
 * @author Administrator
 *
 */
@Entity
@Table(name="submit_proposal_act")
@Cacheable
public class SubmitProposalAct extends AbstractWorkflowAct<SubmitProposalState, SubmitProposalWorkflow> {

	public SubmitProposalAct(String label, String actCode, Integer sort, ActGroup actGroup) {
		super(label, actCode, sort, actGroup);
		// TODO Auto-generated constructor stub
	}
	public SubmitProposalAct(){
		super();
	}
}
