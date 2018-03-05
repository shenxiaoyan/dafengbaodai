package com.liyang.listener;

import org.springframework.stereotype.Component;

import com.liyang.domain.submitproposal.SubmitProposal;
import com.liyang.domain.submitproposal.SubmitProposalAct;
import com.liyang.domain.submitproposal.SubmitProposalLog;
import com.liyang.domain.submitproposal.SubmitProposalState;

/**
 * @author Administrator
 *
 */
@Component
public class SubmitProposalRestEventListener extends AuditorRestEventListener<SubmitProposal, SubmitProposalState, SubmitProposalAct, SubmitProposalLog> {

}
 