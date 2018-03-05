package com.liyang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyang.domain.customer.Customer;
import com.liyang.domain.customer.CustomerRepository;
import com.liyang.domain.platform.Platform;
import com.liyang.domain.platform.PlatformRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.util.FailReturnObject;


/**  
* 类说明   
* @author lcj 
* @date 2017年10月23日 重构修改
*/

@Service
public class AuthorityJudgeService {
	@Autowired
	PlatformRepository platformRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	/**
	 * platform authority Identify
	 */
	public Platform authorityJudge(String applicationId) throws Exception{
		if(applicationId==null) {
			throw new FailReturnObject(ExceptionResultEnum.PLATFORM_APPLICATIONID_MIS_ERROR);
		}
		//平台信息
		Platform platform=platformRepository.findByApplicationId(applicationId);  
		if(platform==null ){
			throw new FailReturnObject(ExceptionResultEnum.PLATFORM_DATA_ERROR);
		}else if("0".equalsIgnoreCase(platform.getEnable())){		//varchar in DB column
			throw new FailReturnObject(ExceptionResultEnum.PLATFORM_DISABLE_ERROR);
		}
		
		return platform;
	}
	
	/*
	 * Customer token authority Identify
	 */
	
	public Customer tokenJudge(String token){
		if(token==null) {
			throw new FailReturnObject(ExceptionResultEnum.CUSTOMER_TOKEN_MIS_ERROR);
		}
		Customer customer=customerRepository.findByToken(token);
		if(customer==null) {
			throw new FailReturnObject(ExceptionResultEnum.CUSTOMER_DATA_ERROR);

		}else if("DISABLED".equalsIgnoreCase(customer.getStateCode())){		//super.getState().getStateCode();
			throw new FailReturnObject(ExceptionResultEnum.CUSTOMER_DISABLE_ERROR);
		}
		
		return customer;
	}
	
	public Boolean customerJudge(Customer customer){
		if(customer==null) {
			throw new FailReturnObject(ExceptionResultEnum.CUSTOMER_DATA_ERROR);

		}else if("DISABLED".equalsIgnoreCase(customer.getStateCode())){		//super.getState().getStateCode();
			throw new FailReturnObject(ExceptionResultEnum.CUSTOMER_DISABLE_ERROR);
		}
		
		return true;
	}
	
}
