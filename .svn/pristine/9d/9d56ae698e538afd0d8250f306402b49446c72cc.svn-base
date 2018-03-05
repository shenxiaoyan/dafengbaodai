package com.liyang.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.liyang.client.IClient;
import com.liyang.client.IServiceObserve;
import com.liyang.client.strategy.SecurityInfoXiaoma;
import com.liyang.client.xiaoma.ClientXiaoma;
import com.liyang.client.xiaoma.MessageQueryLatestPolicy;
import com.liyang.client.xiaoma.ResultQueryLatestPolicy;
import com.liyang.client.xiaoma.ServiceObserveDbPersistQueryLatestPolicy;
import com.liyang.client.xiaoma.ServiceQueryLatestPolicy;
import com.liyang.controller.QueryLatestPolicyController;
import com.liyang.domain.platform.Platform;
import com.liyang.domain.platform.PlatformRepository;
import com.liyang.domain.querylatestpolicy.QueryLatestPolicy;
import com.liyang.domain.querylatestpolicy.QueryLatestPolicyRepository;
import com.liyang.domain.querylatestpolicy.QueryLatestPolicyResult;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.util.FailReturnObject;
import com.liyang.util.HttpUtil;
import com.liyang.util.TransUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONObject;

/**
 * 访问续保接口时，需带上applicId相关信息
 */
/**
 * @author Administrator
 *
 */
@Service
public class QueryLatestPolicyService {

	private final static Logger logger = LoggerFactory.getLogger(QueryLatestPolicyService.class);

	@Autowired
	AuthorityJudgeService authorityJudgeService;

	@Autowired
	QueryLatestPolicyRepository queryLatestPolicyRepository;

	@Autowired
	PlatformRepository platformRepository;

//	@Value("${xmcxapi.apikey}")
//	private String xmcx_apiKey;

	// {"licenseNumber":"浙AZS871","ownerName":"汪伟坤","carTypeCode":"02"}

	public ResponseBody saveQueLatPolAndForward(Platform platform, Map<String, String> queLatPolMap,
			HttpServletRequest request, String xmcxApiKey) throws Exception {
		//Assert.notNull(xmcx_apiKey);
		MessageQueryLatestPolicy message = new MessageQueryLatestPolicy(logger, platform, queLatPolMap);
		IClient client = new ClientXiaoma();
		IServiceObserve serviceObserve = new ServiceObserveDbPersistQueryLatestPolicy(queryLatestPolicyRepository);
		SecurityInfoXiaoma securityInfo = new SecurityInfoXiaoma(xmcxApiKey);
		ServiceQueryLatestPolicy service = new ServiceQueryLatestPolicy(securityInfo, client, serviceObserve);
		// prepareQueryLatestPolicyValue(queLatPolMap);
		ResultQueryLatestPolicy result = (ResultQueryLatestPolicy) service.callService(message);

		// // 将数据post到小马接口,获取返回结果
		// String queLatPolResult = HttpUtil.postForm(postToXiaoMaUrl,
		// queLatPolMap);
		// System.out.println("queLatPolResult:" + queLatPolResult);

		// queryLatestPolicyRepository.save(queryLatestPolicy);

		return new ResponseBody(result.getQueryLatestPolicyResult().getData());

		/*
		 * Original code for reconstruction /
		 */
		// // 获取平台标识，是否具有访问权
		// String applicationId = queLatPolMap.get("applicationId");
		// if (applicationId == null) {
		// applicationId = request.getHeader("applicationId");
		// } else {
		// // 小马接口的标准，去除applicationId
		// queLatPolMap.remove("applicationId");
		// }
		//// // 判断平台是否具有访问权
		//// Platform platform =
		// authorityJudgeService.authorityJudge(applicationId);
		//
		// Platform
		// platform=platformRepository.findByApplicationId(applicationId);
		// // 将请求数据转换为对象保存
		// QueryLatestPolicy queryLatestPolicy =
		// TransUtils.mapTransStringObject(queLatPolMap,
		// QueryLatestPolicy.class);
		// queryLatestPolicy.setPlatform(platform); // 关联平台信息
		//
		//
		// // 将数据post到小马接口,获取返回结果
		// String queLatPolResult = HttpUtil.postForm(postToXiaoMaUrl,
		// queLatPolMap);
		//
		//
		// //identify the queLatPolResult
		//
		// if(null==queLatPolResult){
		// throw new
		// FailReturnObject(ExceptionResultEnum.QUERYLATPOL_RETURN_DATA_ERROR);
		// }
		//
		// // 返回结果处理
		// ResponseBody responseBody = new ResponseBody();
		// ObjectMapper objectMapper = new ObjectMapper();
		// Map<String, Object> newResponseDataMap = new HashMap<String,
		// Object>();
		// if (queLatPolResult == null) {
		// responseBody.setErrorCode(100);
		// responseBody.setErrorInfo("查询续保接口请求失败，请重新查询");
		// responseBody.setData(null);
		// return responseBody;
		// } else {
		// // 返回的结果string封装成map集合，转换为标准的封装数据
		// Map<String, Object> originalResponseDataMap =
		// objectMapper.readValue(queLatPolResult, Map.class);
		// newResponseDataMap.put("errorMsg",
		// originalResponseDataMap.get("errorMsg"));
		// newResponseDataMap.put("time", originalResponseDataMap.get("time"));
		// newResponseDataMap.put("successful",
		// originalResponseDataMap.get("successful"));
		// newResponseDataMap.put("data",
		// JSONObject.fromObject(originalResponseDataMap.get("data")));
		// System.out.println(originalResponseDataMap);
		// Map data = (Map)newResponseDataMap.get("data");
		// System.out.println("data-----" + data);
		// if(null != data){
		// Map biInfo = (Map)data.get("biInfo");
		// if(null != biInfo){
		// List<Map> insurances = (List<Map>) biInfo.get("insurances");
		// for(Map insurance : insurances){
		// insurance.remove("remark");
		// }
		// }
		// }
		//
		// // 将返回的数据转换为对象保存
		// QueryLatestPolicyResult queryLatestPolicyResult =
		// TransUtils.mapTransObject(newResponseDataMap,
		// QueryLatestPolicyResult.class);
		// queryLatestPolicy.setQueryLatestPolicyResult(queryLatestPolicyResult);
		// queryLatestPolicyRepository.save(queryLatestPolicy);
		//
		// // 将数据转换为标准的格式输出给前台
		// if ((Boolean) originalResponseDataMap.get("successful")) {
		// responseBody.setData(JSONObject.fromObject(newResponseDataMap.get("data")));
		// responseBody.setErrorCode(0);
		// responseBody.setErrorInfo("OK");
		// } else {
		// responseBody.setErrorCode(100);
		// responseBody.setErrorInfo(
		// JSONObject.fromObject(newResponseDataMap.get("errorMsg")).getString("message"));
		// responseBody.setData(originalResponseDataMap.get("data"));
		// }
		//
		// return responseBody;
		// }
	}

}
