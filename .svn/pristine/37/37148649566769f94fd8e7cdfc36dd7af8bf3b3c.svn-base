package com.liyang.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.liyang.domain.platform.Platform;
import com.liyang.domain.platform.PlatformRepository;
import com.liyang.helper.ResponseBody;
import com.liyang.service.QueryLatestPolicyService;
import com.liyang.util.RequestUtill;

import net.sf.json.JSONObject;

/**
 * 查询续保保信息接口
 * 第三方平台需到上标识:applicationId
 * @author Administrator
 */



@RestController
@RequestMapping("/dafeng")
public class QueryLatestPolicyController {
	
//	private final static Logger logger = LoggerFactory.getLogger(QueryLatestPolicyController.class);
	
//	@Value("${xmcxapi.queryLatestPolicy.url}")
//	private String xmQueryLatestPolicyUrl ;

	@Value("${xmcxapi.apikey}")
	private String xmcxApiKey ;

	@Autowired
	QueryLatestPolicyService queryLatestPolicyService;
	
	@Autowired
	PlatformRepository platformRepository;

	/**
	 * mobile 续保接口/
	 */
	@RequestMapping(value = "/queryLatestPolicy", method = RequestMethod.POST)
	public ResponseBody mobileQueryLatestPolicy(@RequestBody Map<String, String> queLatPolMap, HttpServletRequest request)
			throws Exception {
//		logger.info("moblie查询续保数据：");
//		"/xmcxapi/webService/enquiry/queryLatestPolicy?api_key="+xmcx_apiKey+""
		System.out.println("【查询续保，App端传入参数】："+queLatPolMap);
		Platform platform = RequestUtill.getMobileAppPlatform(request,platformRepository);
		ResponseBody responseBody = queryLatestPolicyService.saveQueLatPolAndForward(platform,queLatPolMap, request,
				xmcxApiKey);
//				logger.info("查询续保结果返回：" + JSONObject.fromObject(responseBody).toString());
		return responseBody;
	}
	


	
}
