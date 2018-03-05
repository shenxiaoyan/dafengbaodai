package com.liyang.controller;

import java.util.UUID;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.liyang.domain.customer.Customer;
import com.liyang.domain.customer.CustomerProjectionClass;
import com.liyang.domain.customer.CustomerRepository;
import com.liyang.domain.identify.Identify;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.service.Login2Service;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value="/dafeng")
public class Login2Controller {	
	@Autowired
	private Login2Service login2Service ; 
	



	/**
	 * 登录/ login4Mob
	 */
	@RequestMapping(value="/loginIn",method=RequestMethod.POST)
	public ResponseBody loginIn(@RequestBody Identify iden,
								HttpSession session,HttpServletRequest request){
		
		return login2Service.loginIn4Mob(iden,session,request);
		
	}


	


	
}
