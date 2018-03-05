package com.liyang.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.liyang.Application;
import com.liyang.domain.querylatestpolicy.QueryLatestPolicyResult;
import com.liyang.helper.ResponseBody;
import com.liyang.service.QueryDafengApiService;
import com.liyang.util.CommonUtil;
import com.liyang.util.HttpUtil;

import net.sf.json.JSONObject;

/**  
* 类说明   
* @author lcj 
* @date 2017年9月13日  新建  
*/
@RestController
@RequestMapping("/dafeng")
public class QueryDafengApiController {
	
	
	@Autowired
	QueryLatestPolicyController queryLatestPolicyController;
	
	@Autowired
	QueryDafengApiService	queryDafengApiService;
	
	// add aop for api customer valid ,-- optimize later
	
	/*
	 * Dafeng API 续保接口/
	 */
	
	@RequestMapping(value = "/api/dfQueryLatestPolicy", method = RequestMethod.POST)
	public  ResponseBody dfRecPostQueryLatestPolicy(@RequestParam(value="api_key") String apiKey,@RequestBody Map<String, String> queLatPolMap, HttpServletRequest request) throws Exception {
		ResponseBody responseBody = queryDafengApiService.getLatestPolResSer(apiKey,queLatPolMap,request);
		return responseBody;
    }
	
	
/*	ResponseBody responseBody = queryLatestPolicyService.saveQueLatPolAndForward(queLatPolMap, request,
			"http://182.92.24.162:8088/xmcxapi/webService/enquiry/queryLatestPolicy?api_key="+xmcx_apiKey+"")*/
	
	@RequestMapping(value = "/api/dfLicenseOcr", method = RequestMethod.POST)
	public  ResponseBody dfLicenseOcr(@RequestParam(value="api_key") String apiKey,@RequestParam(value="file") MultipartFile file,
			@RequestParam(value = "fileType", required = false) String fileType,
			HttpServletRequest request) throws Exception {
		ResponseBody responseBody = queryDafengApiService.distLicenseOcr(apiKey,file,fileType,request);
		return responseBody;	
    }
	
	
	@RequestMapping(value = "/api/dfCreateEnquiry", method = RequestMethod.POST)
	public  ResponseBody dfCreateEnquiry(@RequestParam(value="api_key") String apiKey,@RequestBody Map<String, Object> creEnqMap, HttpServletRequest request) throws Exception {
		ResponseBody responseBody = queryDafengApiService.getCreateEnquiryResponse(apiKey,creEnqMap,request);
		return responseBody;
		
    }
	
	
	@RequestMapping(value = "/api/dfCreateEnquirySync", method = RequestMethod.POST)
	public  ResponseBody dfCreateEnquirySyc(@RequestParam(value="api_key") String apiKey,@RequestBody Map<String, Object> creEnqMap, HttpServletRequest request) throws Exception {
		ResponseBody responseBody = queryDafengApiService.getCreateEnquiryResponseSync(apiKey,creEnqMap,request);
		return responseBody;
		
    }
	
//	ResponseBody responseResult = createEnquiryService.saveCreEnqAndForward(creEnqMap, request,
//			"http://182.92.24.162:8088/xmcxapi/webService/enquiry/createEnquiry?api_key="+xmcx_apiKey+"");

	
	
	/**
	 * only for ping'an insurance
	 * @param apiKey
	 * @param file
	 * @param fileType
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/api/dfFileUpload", method = RequestMethod.POST)
	public  ResponseBody dfFileUpload(@RequestParam(value="api_key") String apiKey,@RequestParam(value="file") MultipartFile file,
			@RequestParam(value = "fileType", required = false) String fileType,
			HttpServletRequest request) throws IOException, UnsupportedEncodingException {
		ResponseBody responseBody = queryDafengApiService.dfFileUploadSer(apiKey,file,fileType,request);
		return responseBody;
	}
		
//		
//		String resultStr = HttpUtil.postFile(file,
//				"http://182.92.24.162:8088/xmcxapi/webService/enquiry/upToOrderImages?api_key="+xmcx_apiKey+"");
		
		
//		@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
//		public ResponseBody handleUploadProcess(@RequestParam(value="file") MultipartFile file,
//				@RequestParam(value = "fileType", required = false) String fileType,
//				@RequestParam(value = "applicationId", required = false) String applicationId,HttpServletRequest request) throws Exception
    
	
	@RequestMapping(value = "/api/dfSubmitProposal", method = RequestMethod.POST)
	public  ResponseBody dfSubmitProposal(@RequestParam(value="api_key") String apiKey,
			@RequestBody Map<String, Object> subProMap,
			HttpServletRequest request) throws Exception {
		ResponseBody responseBody = queryDafengApiService.getSubProResultResponse(apiKey,subProMap,request);
		return responseBody;
	}
	
	
	
//	@RequestMapping(value = "/api/dfUnderWritingResult", method = RequestMethod.POST)
//	public  ResponseBody dfUnderWritingResult(@RequestParam(value="api_key") String api_key,
//			@RequestParam(value = "data") String undResStr,
//			HttpServletRequest request) throws IOException, UnsupportedEncodingException {
//		ResponseBody responseBody = queryDafengApiService.saveUnderResultAndForwardSer(api_key,undResStr,request);
//		return responseBody;
//	}
//	

	
	/**
	 * waiting for solid code
	 * @param apiKey
	 * @param payStatMap
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/api/dfQueryPayState", method = RequestMethod.POST)
	public  ResponseBody dfQueryPayState(@RequestParam(value="api_key") String apiKey,
			@RequestBody Map<String, String> payStatMap,
			HttpServletRequest request) throws IOException, UnsupportedEncodingException {
		ResponseBody responseBody = queryDafengApiService.queryPayStateSer(apiKey,payStatMap,request);
		return responseBody;
	}

	
	/**
	 * 接口封装
	 * @param apiKey
	 * @param queinsurResultMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/api/dfQueryUnderwritingResult", method = RequestMethod.POST)
	public ResponseBody dfQueryQueryUnderwritingResult(@RequestParam(value = "api_key") String apiKey,
			@RequestBody Map<String, Object> queinsurResultMap, HttpServletRequest request) throws Exception {
		return queryDafengApiService.queryUnderwritingResult(apiKey, queinsurResultMap, request);
	}
}
