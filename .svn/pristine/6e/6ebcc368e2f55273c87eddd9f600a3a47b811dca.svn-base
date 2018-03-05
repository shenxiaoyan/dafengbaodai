package com.liyang.domain.insuranceresult;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import com.liyang.domain.offerresult.OfferResult;
import com.liyang.domain.submitproposal.SubmitProposal;


/**
 * 类说明
 * @author Administrator
 *
 */
@Projection(name="insuranceResultProjection",types={InsuranceResult.class})
public interface InsuranceResultProjection {
	InsuranceResultData getData();
	Integer getId();
	SubmitProposal getSubmitProposal();
	Date getCreatedAt();
	
	@Value("#{target?.submitProposal?.offerResult}")
	OfferResult getOfferResult();
}
