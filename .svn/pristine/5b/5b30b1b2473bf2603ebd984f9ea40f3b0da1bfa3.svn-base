package com.liyang.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liyang.domain.user.User;
import com.liyang.message.Message;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ReturnObject;
import com.liyang.util.SuccessReturnObject;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/message")
public class MessageDispatch {

	@Value("${spring.tim.admin}")
	private String admin;
	 

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	

	@RequestMapping(value = "/dispatch", method = {RequestMethod.POST,RequestMethod.PUT,RequestMethod.PATCH,RequestMethod.GET})
	public String act(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		return (sb.append("forward:").append(request.getRequestURI())).toString();
		//return "forward:" + request.getRequestURI();
	}



}
