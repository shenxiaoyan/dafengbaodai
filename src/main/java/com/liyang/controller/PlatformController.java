package com.liyang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liyang.helper.ResponseBody;
import com.liyang.service.PlatformService;

/**
 * platform相关控制器
 * 
 * @author huanghengkun
 * @create 2017年10月16日
 */
@RestController
@RequestMapping("/dafeng")
public class PlatformController {

	@Autowired
	PlatformService platformService;

	@RequestMapping(value ="/getPlatformVersion", method = RequestMethod.POST)
	public ResponseBody getPlatformVersion(@RequestParam("app_id") String applicationId) {
		ResponseBody responseBody = new ResponseBody(0, "ok");
		String version = platformService.getPlatformVersion(applicationId);
		if (version == null) {
			version = "";
		}
		responseBody.setData(version);
		return responseBody;
	}
}
