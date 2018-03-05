package com.liyang.domain.submitproposal;

import org.springframework.data.rest.core.config.Projection;

/**
 * 投保订单状态
 * @author Administrator
 *
 */
@Projection(name="onlyLabel",types={SubmitProposalState.class})
public interface SubmitProposalStateProjection {
	public String getLabel();
	public String getStateCode();
}
