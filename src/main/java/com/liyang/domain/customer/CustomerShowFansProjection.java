package com.liyang.domain.customer;

import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liyang.domain.account.Account;


/**  
* 类说明   
* @author lcj 
* @date 2017年10月19日  新建  
*/


///rest/Customers?projection=showFans
@Projection(name="showFans",types={Customer.class})
public interface CustomerShowFansProjection {
	String getMyInvite();
	String getHeadimgurl();
	String getNickname();
	String getPhone();
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	Date getCreatedAt();
	Account getAccount();
	CustomerState getState();
}


