package com.liyang.domain.customer;


import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

import com.liyang.domain.account.Account;

/**
 * 类说明
 * @author Administrator
 *
 */
@Projection(name="customerProjection",types={Customer.class})
public interface CustomerProjection {
	Integer getId();
	String getNickname();
	Date getCreatedAt();
	Date getLastModifiedAt();
	String getHeadimgurl();
	String getMyInvite();
	String getPhone();
	Integer getTag();
	CustomerState getState();
	String getSex();
	Integer getGrade();
	Account getAccount();
}
