package com.liyang.domain.submitproposal;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import com.liyang.domain.base.AbstractAuditorState;
import com.liyang.domain.createenquiry.CreateEnquiry;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.offerresult.OfferResult;


/**
 * 投保订单
 * @author Administrator
 *
 */
@Projection(name="submitProposalProjection",types= {SubmitProposal.class})
interface SubmitProposalProjection {
	Integer getId();
	SubmitProposalParams getParams();
	OfferResult getOfferResult();
	AbstractAuditorState getState();
	Date getCreateTime();
	Customer getCustomer();
	@Value("#{target?.offerResult?.createEnquiry}")
	CreateEnquiry getCreateEnquiry();
}
