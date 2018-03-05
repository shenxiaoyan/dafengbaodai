package com.liyang.domain.offerresult;

import org.springframework.data.rest.core.config.Projection;

import com.liyang.domain.createenquiry.CreateEnquiry;


/**
 * 类说明
 * @author Administrator
 *
 */
@Projection(name="offerResultProjection",types={OfferResult.class})
public interface OfferResultProjection {
	Integer getId();
	OfferResultData getData();
	OfferResultErrorMsg getErrorMsg();
	CreateEnquiry getCreateEnquiry();
	Boolean getSuccessful(); 
}
