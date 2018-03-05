package com.liyang.domain.submitproposal;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowState;

/**
 * 投保订单状态
 * @author Administrator
 *
 */
@Entity
@Table(name="submit_proposal_state")
public class SubmitProposalState extends AbstractWorkflowState<SubmitProposal, SubmitProposalWorkflow, SubmitProposalAct> {

	public SubmitProposalState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public SubmitProposalState() {
		super();
	}
}
