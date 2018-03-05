package com.liyang.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.liyang.controller.CreateEnquiryController;
import com.liyang.controller.FileUploadController;
import com.liyang.controller.InsuranceResultController;
import com.liyang.controller.QueryLatestPolicyController;
import com.liyang.controller.QueryPayStateController;
import com.liyang.controller.SubmitProposalController;
import com.liyang.controller.UnderwritingResultController;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.dafengapi.DafengApiCustomer;
import com.liyang.domain.dafengapi.DafengApiCustomerRepository;
import com.liyang.domain.platform.Platform;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.util.DafengApiUtil;
import com.liyang.util.FailReturnObject;

import net.sf.json.JSONObject;

/**
 * 类说明
 * 
 * @author lcj
 * @date 2017年9月13日 新建
 */
@Service
public class QueryDafengApiService {

	@Autowired
	DafengApiCustomerRepository dafengApiCustomerRepository;

	@Autowired
	QueryLatestPolicyController queryLatestPolicyController;

	@Autowired
	QueryLatestPolicyService queryLatestPolicyService;

	@Autowired
	CreateEnquiryController createEnquiryController;

	@Autowired
	FileUploadController fileUploadController;

	@Autowired
	SubmitProposalController submitProposalController;

	@Autowired
	SubmitProposalService submitProposalService;

	@Autowired
	UnderwritingResultController underwritingResultController;

	@Autowired
	QueryPayStateService queryPayStateService;

	@Autowired
	InsuranceResultController insuranceResultController;

	@Autowired
	CreateEnquiryService createEnquiryService;

	@Autowired
	UnderwritingResultService underwritingResultService;

	@Autowired
	LicenseService licenseService;

	@Value("${xmcxapi.queryLatestPolicy.url}")
	private String xmQueryLatestPolicyUrl;

	@Value("${xmcxapi.createEnquiry.url}")
	private String xmCreateEnquiryUrl;

	@Value("${xmcxapi.createEnquirySync.url}")
	private String xmCreateEnquirySyncUrl;

	@Value("${xmcxapi.licenseOcr.url}")
	private String licenseOcrUrl;

	@Value("${xmcxapi.submitProposal.url}")
	private String xmSubPropsalUrl;

	@Value("${xmcxapi.queryPayState.url}")
	private String xmQueryPayStatUrl;

	@Value("${xmcxapi.apikey}")
	private String xmcxApiKey;

	private final static Logger logger = LoggerFactory.getLogger(QueryDafengApiService.class);

	private static String MESSAGE_API_KEY_ERROR = "api-key 错误 或 ipAddress 不在白名单 ";
	private static String MESSAGE_API_KEY_MIS = "api-key 缺省";
	private static String MESSAGE_IP_ADDRESS_MIS = "ip-Address 缺省";
	// public List<DafengApiCustomer> findUsersWithIpAddressAndKey(String
	// apiKey,String ipAddress) {
	//
	// List<DafengApiCustomer> dafengApiCustomers =
	// dafengApiCustomerRepository.findByApiKeyAndIpAddress(apiKey,ipAddress);
	//
	// if(!dafengApiCustomers.isEmpty()){
	// return dafengApiCustomers;
	// }else{
	// return null;
	// }
	// }

	public ResponseBody getLatestPolResSer(String apiKey, Map<String, String> queLatPolMap, HttpServletRequest request)
			throws Exception {

		// Boolean flag = false;
		// if(!DafengApiUtil.checkApiKey(apiKey)) {
		// return new ResponseBody(101,MESSAGE_API_KEY_MIS);
		// }
		//
		// String ipAddress =request.getRemoteAddr();
		// System.out.println("request.getRemoteAddr()....."+ipAddress); // get
		// the api_customer ipAddress.
		// //
		// if(!DafengApiUtil.checkIpAddress(ipAddress)) {
		// return new ResponseBody(101,MESSAGE_IP_ADDRESS_MIS);
		// }
		// /*不可逆加密采用MD5加密算法,加密api_key 到DB查询
		// * */
		//
		// //check apikey value
		// List<DafengApiCustomer> dafengApiCustomers =
		// findUsersWithIpAddressAndKey(apiKey,ipAddress);
		//
		// if(dafengApiCustomers!=null) {
		// flag=true;
		// }
		// System.out.println("validUserWithIpAddressAndKey(apiKey,ipAddress);"
		// +flag);
		//
		// if(!flag){
		// return new ResponseBody(101,MESSAGE_API_KEY_ERROR);
		// }
		//
		//
		//
		// String applicationId
		// =dafengApiCustomers.get(0).getPlatform().getApplicationId();

		Platform platform = null;

		DafengApiCustomer dafengApiCustomer = DafengApiUtil.validApiCustByIpAddressAndKey(apiKey,
				dafengApiCustomerRepository, request);
		if (dafengApiCustomer == null) {
			// return new
			// ResponseBody(ExceptionResultEnum.DAFENGAPI_API_KEY_DATA_ERROR);
			throw new FailReturnObject(ExceptionResultEnum.DAFENGAPI_API_KEY_DATA_ERROR);
		}

		if ((platform = dafengApiCustomer.getPlatform()) == null) {
			// return new
			// ResponseBody(ExceptionResultEnum.DAFENGAPI_API_KEY_DATA_ERROR);
			throw new FailReturnObject(ExceptionResultEnum.DAFENGAPI_PLATFORM_MIS_ERROR);
		} else if ("0".equalsIgnoreCase(platform.getEnable())) { // varchar in
																	// DB column
			throw new FailReturnObject(ExceptionResultEnum.PLATFORM_DISABLE_ERROR);
		}

		// 注册 Dafeng API 的 用户

		logger.info("DafengAPI查询续保数据：");
		ResponseBody responseBody = queryLatestPolicyService.saveQueLatPolAndForward(platform, queLatPolMap, request,
				xmcxApiKey);

		return responseBody;

		// // 读取请求内容
		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(request.getInputStream()));
		// String line = null;
		// StringBuilder sb = new StringBuilder();
		// while((line = br.readLine())!=null){
		// sb.append(line);
		// }
		//
		// // 将资料解码
		// String reqBody = sb.toString();
		// System.out.println("reqBody..... " + reqBody);
		// String reqDecodeBody = URLDecoder.decode(reqBody, HTTP.UTF_8);
		// System.out.println("reqDecodeBody........"+ reqDecodeBody);
	}

	public ResponseBody getCreateEnquiryResponse(String apiKey, Map<String, Object> creEnqMap,
			HttpServletRequest request) throws Exception {

		// /*不可逆加密采用MD5加密算法,加密api_key 到DB查询
		// * */
		// String applicationId
		// =dafengApiCustomers.get(0).getPlatform().getApplicationId();
		//
		Platform platform = null;
		DafengApiCustomer dafengApiCustomer = DafengApiUtil.validApiCustByIpAddressAndKey(apiKey,
				dafengApiCustomerRepository, request);
		if (dafengApiCustomer == null) {
			// return new ResponseBody(101,MESSAGE_API_KEY_ERROR);
			throw new FailReturnObject(ExceptionResultEnum.DAFENGAPI_API_KEY_DATA_ERROR);
		}
		if ((platform = dafengApiCustomer.getPlatform()) == null) {
			// return new
			// ResponseBody(ExceptionResultEnum.DAFENGAPI_API_KEY_DATA_ERROR);
			throw new FailReturnObject(ExceptionResultEnum.DAFENGAPI_PLATFORM_MIS_ERROR);
		} else if ("0".equalsIgnoreCase(platform.getEnable())) { // varchar in
																	// DB column
			throw new FailReturnObject(ExceptionResultEnum.PLATFORM_DISABLE_ERROR);
		}

		// //add for CreateEnquiryService check.
		// creEnqMap.put("applicationId", applicationId);

		logger.info("DafengAPI查询询价：");

		ResponseBody responseBody = createEnquiryService.saveCreEnqAndForward(platform, creEnqMap, request,
				xmcxApiKey);

		return responseBody;

	}

	public ResponseBody getCreateEnquiryResponseSync(String apiKey, Map<String, Object> creEnqMap,
			HttpServletRequest request) throws Exception {
		Platform platform = null;
		DafengApiCustomer dafengApiCustomer = DafengApiUtil.validApiCustByIpAddressAndKey(apiKey,
				dafengApiCustomerRepository, request);
		if (dafengApiCustomer == null) {
			// return new ResponseBody(101,MESSAGE_API_KEY_ERROR);
			throw new FailReturnObject(ExceptionResultEnum.DAFENGAPI_API_KEY_DATA_ERROR);
		}
		if ((platform = dafengApiCustomer.getPlatform()) == null) {
			// return new
			// ResponseBody(ExceptionResultEnum.DAFENGAPI_API_KEY_DATA_ERROR);
			throw new FailReturnObject(ExceptionResultEnum.DAFENGAPI_PLATFORM_MIS_ERROR);
		} else if ("0".equalsIgnoreCase(platform.getEnable())) { // varchar in
																	// DB column
			throw new FailReturnObject(ExceptionResultEnum.PLATFORM_DISABLE_ERROR);
		}

		logger.info("DafengAPI查询询价：");

		ResponseBody responseBody = createEnquiryService.saveCreEnqAndForward(platform, creEnqMap, request,
				xmcxApiKey);

		return responseBody;
	}

	public ResponseBody distLicenseOcr(String apiKey, MultipartFile file, String fileType,
			HttpServletRequest request) {
		Platform platform = null;
		DafengApiCustomer dafengApiCustomer = DafengApiUtil.validApiCustByIpAddressAndKey(apiKey,
				dafengApiCustomerRepository, request);
		if (dafengApiCustomer == null) {
			throw new FailReturnObject(ExceptionResultEnum.DAFENGAPI_API_KEY_DATA_ERROR);
		}
		if ((platform = dafengApiCustomer.getPlatform()) == null) {
			throw new FailReturnObject(ExceptionResultEnum.DAFENGAPI_PLATFORM_MIS_ERROR);
		} else if ("0".equalsIgnoreCase(platform.getEnable())) { // varchar in
																	// DB column
			throw new FailReturnObject(ExceptionResultEnum.PLATFORM_DISABLE_ERROR);
		}
		// 小马行驶证识别接口
		String xiaomaURL = xmcxApiKey;
		return licenseService.doDistinguish(file, xiaomaURL);

	}

	public ResponseBody dfFileUploadSer(String apiKey, MultipartFile file, String fileType,
			HttpServletRequest request) {
		String applicationId;
		DafengApiCustomer dafengApiCustomer = DafengApiUtil.validApiCustByIpAddressAndKey(apiKey,
				dafengApiCustomerRepository, request);
		if (dafengApiCustomer == null) {
			return new ResponseBody(101, MESSAGE_API_KEY_ERROR);
		}
		applicationId = dafengApiCustomer.getPlatform().getApplicationId();

		try {
			ResponseBody responseBody = fileUploadController.handleUploadProcess(file, fileType, applicationId,
					request);
			return responseBody;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBody(101, e.getMessage());
			// throw new MyDafengException(); //add later
		}

	}

	public ResponseBody getSubProResultResponse(String apiKey, Map<String, Object> subProMap,
			HttpServletRequest request) throws Exception {

		Platform platform = null;
		DafengApiCustomer dafengApiCustomer = DafengApiUtil.validApiCustByIpAddressAndKey(apiKey,
				dafengApiCustomerRepository, request);
		if (dafengApiCustomer == null) {
			throw new FailReturnObject(ExceptionResultEnum.DAFENGAPI_API_KEY_DATA_ERROR);
		}
		if ((platform = dafengApiCustomer.getPlatform()) == null) {
			throw new FailReturnObject(ExceptionResultEnum.DAFENGAPI_PLATFORM_MIS_ERROR);
		} else if ("0".equalsIgnoreCase(platform.getEnable())) { // varchar in
																	// DB column
			throw new FailReturnObject(ExceptionResultEnum.PLATFORM_DISABLE_ERROR);
		}
		ResponseBody responseBody = submitProposalService.saveSubProResultAndForward(platform, subProMap, request,
				xmSubPropsalUrl + "?apiKey=" + xmcxApiKey);
		return responseBody;

		// String applicationId;
		// DafengApiCustomer dafengApiCustomer
		// =DafengApiUtil.validApiCustByIpAddressAndKey(apiKey,
		// dafengApiCustomerRepository, request);
		// if(dafengApiCustomer==null){
		// return new ResponseBody(101,MESSAGE_API_KEY_ERROR);
		// }
		// applicationId =dafengApiCustomer.getPlatform().getApplicationId();
		// //add for submitProposalService check.
		// subProMap.put("applicationId", applicationId);
		//
		// try {
		// ResponseBody responseBody=
		// submitProposalController.mobileSubmitProposal(subProMap, request);
		// return responseBody;
		// } catch (Exception e) {
		//
		// e.printStackTrace();
		// return new ResponseBody(101,e.getMessage());
		//// throw new MyDafengException(); //add later
		// }
	}

	public ResponseBody queryPayStateSer(String apiKey, Map<String, String> payStatMap, HttpServletRequest request)
			throws JsonParseException, JsonMappingException, IOException {

		Platform platform = null;
		DafengApiCustomer dafengApiCustomer = DafengApiUtil.validApiCustByIpAddressAndKey(apiKey,
				dafengApiCustomerRepository, request);
		if (dafengApiCustomer == null) {
			throw new FailReturnObject(ExceptionResultEnum.DAFENGAPI_API_KEY_DATA_ERROR);
		}
		if ((platform = dafengApiCustomer.getPlatform()) == null) {
			throw new FailReturnObject(ExceptionResultEnum.DAFENGAPI_PLATFORM_MIS_ERROR);
		} else if ("0".equalsIgnoreCase(platform.getEnable())) { // varchar in
																	// DB column
			throw new FailReturnObject(ExceptionResultEnum.PLATFORM_DISABLE_ERROR);
		}
		ResponseBody responseBody;
		try {
			responseBody = queryPayStateService.saveQuePayState(payStatMap, request, xmcxApiKey);
		} catch (Exception e) {
			e.printStackTrace();
			throw new FailReturnObject(0, e.getMessage());
		}

		return responseBody;
	}

	// public ResponseBody saveUnderResultAndForwardSer(String apiKey, String
	// undResStr, HttpServletRequest request) {
	// String applicationId;
	// DafengApiCustomer dafengApiCustomer
	// =DafengApiUtil.validApiCustByIpAddressAndKey(apiKey,
	// dafengApiCustomerRepository, request);
	// if(dafengApiCustomer==null){
	// return new ResponseBody(101,MESSAGE_API_KEY_ERROR);
	// }
	// applicationId =dafengApiCustomer.getPlatform().getApplicationId();
	//
	// try {
	// String responseResult
	// =underwritingResultController.underwritingResult(undResStr);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// return new ResponseBody(101,e.getMessage());
	// }
	//
	//
	// return null;
	// }

	public ResponseBody queryUnderwritingResult(String apiKey, Map<String, Object> queinsurResultMap,
			HttpServletRequest request) throws Exception {
		Platform platform = null;
		DafengApiCustomer dafengApiCustomer = DafengApiUtil.validApiCustByIpAddressAndKey(apiKey,
				dafengApiCustomerRepository, request);
		if (dafengApiCustomer == null) {
			throw new FailReturnObject(ExceptionResultEnum.DAFENGAPI_API_KEY_DATA_ERROR);
		}
		if ((platform = dafengApiCustomer.getPlatform()) == null) {
			throw new FailReturnObject(ExceptionResultEnum.DAFENGAPI_PLATFORM_MIS_ERROR);
		} else if ("0".equalsIgnoreCase(platform.getEnable())) { // varchar in
																	// DB column
			throw new FailReturnObject(ExceptionResultEnum.PLATFORM_DISABLE_ERROR);
		}
		ResponseBody responseBody = underwritingResultService
				.reqConfirmUnderwriting((String) queinsurResultMap.get("orderId"));
		return responseBody;
	}

}
