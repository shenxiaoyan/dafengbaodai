package com.liyang.controller;


import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liyang.domain.user.User;
import com.liyang.service.WechatLoginService;
import com.liyang.util.CommonUtil;
import com.liyang.util.ReturnObject;
import com.liyang.util.SuccessReturnObject;

/**
 * @author Administrator
 *
 */
@Controller
public class LoginController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	WechatLoginService wechatLoginService;
	
	@RequestMapping("/login")
	public String login(Model model ){
		wechatLoginService.connect(model);
		return "auth/login";
	}
		
	
	
	@RequestMapping(value = "/employeeApply")
	public String employeeApply(Model model) {

		wechatLoginService.apply(model , CommonUtil.getPrincipal());
		return "auth/login";
	}
	
	
	@RequestMapping(value = "/employeeBindWechat")
	public String employeeBindWechat(Model model) {

		wechatLoginService.bind(model);
		return "auth/login";
	}
	
	
	
	@RequestMapping(value = "/employeeApplyJson")
	@ResponseBody
	public Map employeeApplyJson(Model model) {

		wechatLoginService.apply(model , CommonUtil.getPrincipal());
		 Set<Entry<String, Object>> entrySet = model.asMap().entrySet();
		 HashMap<String, String> newmap = new HashMap<String,String>();
		for (Entry<String, Object> entry : entrySet) {
			newmap.put(entry.getKey(), entry.getValue().toString());
		}
		return newmap;
	}
}
