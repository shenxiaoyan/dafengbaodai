package com.liyang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liyang.domain.user.User;
import com.liyang.util.CommonUtil;

/**
 * @author Administrator
 *
 */
@Controller
public class IndexController {
	@RequestMapping("/")
	public String index(Model model){
		User principal = CommonUtil.getPrincipal();
		if(principal != null) {
		model.addAttribute("id", principal.getId());
		
		}
		//return "这里是主页，请用/connect 登录";
		return "index";
	}

	
}
