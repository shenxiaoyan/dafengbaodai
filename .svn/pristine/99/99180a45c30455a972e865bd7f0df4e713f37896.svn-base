package com.liyang.controller;


import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liyang.service.WechatLoginService;
import com.liyang.util.CommonUtil;

/**
 * template
 * @author Administrator
 *
 */
@Controller
public class TemplateController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/template/{entityName}/{viewType}", method = RequestMethod.GET)
	public String view(@PathVariable(value="entityName") String entityName , @PathVariable(value="viewType") String viewType) {
		String template = CommonUtil.template(entityName, viewType);
		return template;
	}
	
	
}
