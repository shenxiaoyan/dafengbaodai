package com.liyang.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import com.liyang.domain.dafengapi.DafengApiCustomer;
import com.liyang.domain.dafengapi.DafengApiCustomerRepository;
import com.liyang.helper.ResponseBody;

/**  
* 类说明   
* @author lcj 
* @date 2017年9月18日  新建  
*/


public class DafengApiUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(DafengApiUtil.class);
	
	public static boolean checkApiKey(String apiKey) {
		
		if(apiKey==null || apiKey.trim().equalsIgnoreCase("")) {
			
			return false;
		}
		return true;
	}
	
	public static boolean checkIpAddress(String ipAddress) {
		
		if(ipAddress==null || ipAddress.trim().equalsIgnoreCase("")) {
			return false;
		}
		return true;
	}

	public static List<DafengApiCustomer> findUsersWithIpAddressAndKey(String apiKey,String ipAddress,DafengApiCustomerRepository dafengApiCustomerRepository) {

		List<DafengApiCustomer> dafengApiCustomers = dafengApiCustomerRepository.findByApiKeyAndIpAddress(apiKey,ipAddress);

		if((null != dafengApiCustomers)  && (!dafengApiCustomers.isEmpty())){
			return dafengApiCustomers;
		}else{
			return null;
		}
	}
	
	public static DafengApiCustomer validApiCustByIpAddressAndKey(String apiKey,DafengApiCustomerRepository dafengApiCustomerRepository, HttpServletRequest request){
		String ipAddress =request.getRemoteAddr();
		logger.info("request.getRemoteAddr()..:" +ipAddress );
		if(!checkApiKey(apiKey)) {
			return null;
		}
		
		if(!DafengApiUtil.checkIpAddress(ipAddress)) {
			return null;
		}
		
		List<DafengApiCustomer> dafengApiCustomers =dafengApiCustomerRepository.findByApiKeyAndIpAddress(apiKey,ipAddress);
		if(dafengApiCustomers==null || dafengApiCustomers.isEmpty()) {
			return null;
		}
		return dafengApiCustomers.get(0);
	}
	

}
