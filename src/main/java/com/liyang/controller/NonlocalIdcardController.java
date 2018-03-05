package com.liyang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.liyang.helper.ResponseBody;
import com.liyang.service.NonlocalIdcardService;

/**
 * @author Administrator
 * 统计系统，导入保单时，非本地户口筛选
 */
@RestController
@RequestMapping("/dafeng/nonlocalIdcard")
public class NonlocalIdcardController {
	
	@Autowired	
	NonlocalIdcardService nonlocIdService;
	
	/**
	 * excel导入非本地户口，没有前端页面，需开发人员手动维护
	 * @param file
	 * @return
	 */
	@RequestMapping("/import")
	public ResponseBody batchImport(MultipartFile file) {
		String msg = nonlocIdService.batchImport(file);
		return new ResponseBody(msg);
	}
	
}
