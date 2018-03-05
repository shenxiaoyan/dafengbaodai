package com.liyang.domain.submitproposal;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflow;

/**
 * 投保订单工作流
 * @author Administrator
 *
 */
@Entity
@Table
@Cacheable
public class SubmitProposalWorkflow extends AbstractWorkflow< SubmitProposal,SubmitProposalState> {

}
