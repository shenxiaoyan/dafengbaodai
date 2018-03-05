package com.liyang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liyang.helper.ResponseBody;
import com.liyang.service.OutlineService;

/**
 * web端首页概览
 * 
 * @author huanghengkun
 *
 */
@RestController
@RequestMapping("/dafeng/outline")
public class OutlineController {
	
	@Autowired
	private OutlineService outlineService;

	@RequestMapping("/show")
	public ResponseBody show(@RequestParam("timeNode") String timeNode) {
		return outlineService.show(timeNode);
	}

}
