package com.liyang.domain.createenquiry;

import java.util.Date;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.data.rest.core.config.Projection;

import com.liyang.domain.base.AbstractAuditorState;
import com.liyang.domain.offerresult.OfferResult;
import com.liyang.domain.platform.Platform;


/**
 * 类说明
 * @author Administrator
 *
 */
@Projection(name="createEnquiryProjection",types={CreateEnquiry.class})
public interface CreateEnquiryProjection {
	String getLabel();
	Integer getId();
	Date getCreatedAt();
	Date getLastModifiedAt();
	String getMobilePhone();
	String getOfferUnique();
//	String getCreateEnquiryParams();
	AbstractAuditorState getState();
	String getCreateEnquireParams();
}
