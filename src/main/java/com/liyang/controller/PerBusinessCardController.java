package com.liyang.controller;

import javax.servlet.http.HttpServletRequest;

import com.liyang.domain.perbusinesscard.PerBusinessCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.liyang.domain.perbusinesscard.PerBusinessCardBean;
import com.liyang.domain.perbusinesscard.PerBusinessCardRepository;
import com.liyang.helper.ResponseBody;
import com.liyang.service.PerBusinessCardService;

import java.util.Map;

@RestController
@RequestMapping("/dafeng/perCard")
public class PerBusinessCardController {
	@Autowired
	private PerBusinessCardService perBusinessCardService;
	@Autowired
	PerBusinessCardRepository perBusinessCardRepository;

	//web端查询个人名片信息
	@PostMapping(value = "/{customerId}")
	public  ResponseBody showPer(@PathVariable("customerId") Integer customerId){
		Map<String, Object> stringObjectMap = perBusinessCardService.showPer(customerId);
		return new ResponseBody(stringObjectMap);
	}


	// 移动端
	// 显示个人名片信息
	@RequestMapping(value = "/mobileSearch")
	public ResponseBody mobileSearch(HttpServletRequest request) {
		// System.out.println(perBusinessCardService.mobileSearch(request.getHeader("token")));
		return new ResponseBody(perBusinessCardService.mobileSearch(request.getHeader("token")));
	}

	// 修改个人名片
	@PostMapping("/mobileUpdate")
	public ResponseBody mobileUpdate(HttpServletRequest request, @RequestBody PerBusinessCardBean perBusinessCardBean) {
		String token = request.getHeader("token");
		perBusinessCardService.mobileUpdatePer(perBusinessCardBean, token);
		return new ResponseBody("修改成功");

	}

}
