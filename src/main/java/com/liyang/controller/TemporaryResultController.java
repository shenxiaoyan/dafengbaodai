package com.liyang.controller;

import java.util.Map;

import javax.persistence.MappedSuperclass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liyang.service.TemporaryResultService;
import com.liyang.util.AESAndURLUtils;
/**
 * 暂存结果推送
 *推送格式为x-www-form-urlencode
 *数据结果:data={数据}
 *@author Administrator
 */
@Controller
@RequestMapping("/dafeng")
public class TemporaryResultController {
	
	@Autowired
	TemporaryResultService temporaryResultService;
	
	@RequestMapping(value="/temporaryResult",method=RequestMethod.POST)
	@ResponseBody
	public String temporaryResult(@RequestParam(value="data") String temResStr) throws Exception{
		//接收小马数据,并转发到指定平台
		String responseResult=temporaryResultService.saveTemResAndForward(AESAndURLUtils.urlDecoderAndHander(temResStr));
		return responseResult;
	}
	
}
