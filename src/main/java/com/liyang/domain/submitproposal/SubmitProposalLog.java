package com.liyang.domain.submitproposal;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowLog;

/**
 * 投保订单log
 * @author Administrator
 *
 */
@Entity
@Table(name="submit_proposal_log")
@Cacheable
public class SubmitProposalLog extends AbstractWorkflowLog<SubmitProposal, SubmitProposalWorkflow, SubmitProposalState, SubmitProposalAct, SubmitProposalFile>{
	
}
