package com.liyang.domain.submitproposal;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.liyang.domain.base.StateRepository;

//@RepositoryRestResource(excerptProjection = AbstractWorkflowStateProjection.class)
/**
 * 投保订单状态
 * @author Administrator
 *
 */
public interface SubmitProposalStateRepository extends StateRepository<SubmitProposalState> {
	public SubmitProposalState findByStateCode(String stateCode);
	
	@Query("from SubmitProposalState ")
	public List<SubmitProposalState> getAllState();
}
