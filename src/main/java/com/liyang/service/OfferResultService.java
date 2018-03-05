package com.liyang.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.liyang.domain.advertise.Advertise;
import com.liyang.domain.advertise.AdvertiseRepository;
import com.liyang.domain.createenquiry.CreateEnquiry;
import com.liyang.domain.createenquiry.CreateEnquiryRepository;
import com.liyang.domain.createenquiry.CreateEnquiryState;
import com.liyang.domain.createenquiry.CreateEnquiryStateRepository;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.dafengapi.DafengApiCustomer;
import com.liyang.domain.dafengapi.DafengApiCustomerRepository;
import com.liyang.domain.offerresult.OfferResult;
import com.liyang.domain.offerresult.OfferResultDataResult;
import com.liyang.domain.offerresult.OfferResultRepository;
import com.liyang.domain.platform.Platform;
import com.liyang.domain.platform.PlatformRepository;
import com.liyang.domain.responseReturn.Data;
import com.liyang.domain.responseReturn.ErrorMsg;
import com.liyang.domain.responseReturn.ResponseReturn;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.util.FailReturnObject;
import com.liyang.util.HttpUtil;
import com.liyang.util.PushAdvertiseUtil;
import com.liyang.util.TransUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 */
@Service
public class OfferResultService {
	@Autowired
	OfferResultRepository offerResultRepository;

	@Autowired
	RedisTemplate<String, String> redisTemplate;

	@Autowired
	PlatformRepository platformRepository;

	@Autowired
	CreateEnquiryRepository createEnquiryRepository;

	@Autowired
	AuthorityJudgeService authorityJudgeService;

	@Autowired
	XinGeService xinGeService;
	
	@Autowired
	AdvertiseRepository advertiseRepository ; 
	
	@Autowired
	CreateEnquiryStateRepository	createEnquiryStateRepository;
	
	@Autowired
	DafengApiCustomerRepository	dafengApiCustomerRepository;
	
	private final static Logger logger = LoggerFactory.getLogger(OfferResultService.class);
	
	
	@Value("${olquan.enquiryres.url}")
	private String olEnquiryResUrl ;
	
	/**
	 * 对报价结果储存,返回该报价结果的平台资源
	 * @param offResMap
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public String saveOffResAndForward(Map<String, Object> offResMap) throws Exception {
		
		OfferResult offerResult=null;
		// 将map推送的数据转化成指定对象
		try{
			offerResult = TransUtils.mapTransObject(offResMap, OfferResult.class);
		}catch (Exception e) {
			logger.error(e.toString());
			throw new FailReturnObject(ExceptionResultEnum.OFFERRES_DATA_TRANS_ERROR);
		}
		
		String offerUnique = offerResult.getData().getRequestHeader().getOfferUnique();
		String oriReqHeaderStr = offerResult.getData().getRequestHeader().getOriReqHeaderStr();
		Integer insurCompId = offerResult.getData().getResult().getInsuranceCompanyId();
		String offerId = offerResult.getData().getResult().getOfferId();
		
		List<OfferResult> offerResultList = offerResultRepository.findByDataRequestHeaderOfferUniqueAndDataResultInsuranceCompanyId(
				offerUnique,insurCompId);
		if(null==offerResultList || offerResultList.isEmpty()) {
			throw new FailReturnObject(ExceptionResultEnum.OFFERRES_DATA_MIS_ERROR);
		}
		OfferResult offerResultDB= offerResultList.get(0);
		//Id
		offerResult.setId(offerResultDB.getId());	
		CreateEnquiryState createEnquiryState =createEnquiryStateRepository.findByStateCode("ENQUIRY_RESULT");
		offerResultDB.getCreateEnquiry().setState(createEnquiryState);
		//CreateEnquiry
		offerResult.setCreateEnquiry(offerResultDB.getCreateEnquiry());		
		Platform platform= offerResultDB.getPlatform();
		offerResult.setPlatform(platform);
		offerResult.getData().setId(offerResultDB.getData().getId());
		// offerResult result set id 4 xm duplicate push// add later
		// 将数据保存
		offerResult = offerResultRepository.save(offerResult);
		
		// for Dafeng Api Customer
		List<DafengApiCustomer> dafengApiCustomers = dafengApiCustomerRepository.findByPlatform(platform);	
		if(dafengApiCustomers !=null&& (!dafengApiCustomers.isEmpty())){
			//replacce requestHeader
			@SuppressWarnings("unchecked")
			Map<String, Object> dataMap = (Map<String, Object>) offResMap.get("data");
			dataMap.put("requestHeader", oriReqHeaderStr);
			Map<String, Object> offerResultMap4Api = new HashMap<String, Object>();
			JSONObject json = JSONObject.fromObject(offResMap); 
			offerResultMap4Api.put("data", json);
			String dfApiOfferResURL = platform.getPlatformOfferResultURL();

			for(int i =0; i<2; i++){
				String offerRes4ApiResponse = HttpUtil.postRawJsonBody(dfApiOfferResURL, JSONObject.fromObject(offerResultMap4Api).toString());
				logger.info("offerRes4ApiResponse"+ offerRes4ApiResponse);
				//check response
				if(offerRes4ApiResponse!=null){	
					logger.info("offerRes 转发OL 成功");
						break;
				}
			}
			return responseReturn(offerId);
		}	
		
		// for Dafeng app Customer
		//简单数据处理封装推送至安卓和ios
		Map<String, Object> handerDataMap=new HashMap<String,Object>();
		handerDataMap.put("Id", offerResult.getId());
		handerDataMap.put("name", "offerResult");
		handerDataMap.put("create_enquiry_id", offerResult.getCreateEnquiry().getId());
		//TODO 此处为何要判断存在消息？？？ 存在推送消息，则不推送？ 修改车型或重新报价的offerId与原始报价offerId是否相同
		List<Advertise> advertiseList = advertiseRepository.findByOfferIdAndZnxType(offerId, 1);
		if(null==advertiseList || advertiseList.isEmpty()){
			Advertise advertis = generateOfferResultAdvertise(offerResult,offerId);
			// 报价结果推送到指定平台
			Customer customer = offerResult.getCreateEnquiry().getCustomer();
			PushAdvertiseUtil.pushAdvertToAppPlatform(xinGeService, customer, handerDataMap, advertis.getTitle());
		}
//		ResponseReturn responseReturn= new ResponseReturn(new ErrorMsg("success", "操作成功"),
//				new Data(offerId), true);
//		return  JSONObject.fromObject(responseReturn).toString();
		return responseReturn(offerId);

		
		/*
		 * 
		 * Original Code for Resconstitution
		 * /
		 */
//		// 原始数据data下result中存在id字段,需要去除
//		Map<String, Object> dataMap = (HashMap<String, Object>) offResMap.get("data");
//		if(null != dataMap){
//			Map<String, Object> resultMap = (HashMap<String, Object>) dataMap.get("result");
//			resultMap.remove("id");
//			dataMap.put("result", resultMap);
//			offResMap.put("data", dataMap);
//		}
//		
//		// 将map推送的数据转化成指定对象
//		OfferResult offerResult = TransUtils.mapTransObject(offResMap, OfferResult.class);
//
//		
//		// 获取到此报价单是属于哪个平台的，并关联平台
//		JSONObject datadata = JSONObject.fromObject(offResMap.get("data"));
//		String offerUnique = null ;
//		if(null != datadata){
//			JSONObject jsonObject = datadata.getJSONObject("requestHeader");
//			if(null != jsonObject){
//				String applicationId = jsonObject.getString("applicationId");
//				Platform platform = platformRepository.findByApplicationId(applicationId);
//				if (platform != null) {
//					offerResult.setPlatform(platform);
//				}
//				// 获取到报价唯一标示，并关联关联询价参数
//				offerUnique = jsonObject.getString("offerUnique");
//			}
//		}
//
//		CreateEnquiry createEnquiry = null ;
//		// 获取到唯一标示,关联到询价对象
//		if(null != offerUnique){
//			createEnquiry = createEnquiryRepository.findByOfferUnique(offerUnique);
//			//修改对应的询价状态
//		}
//		updateResultState(createEnquiry);
//		offerResult.setCreateEnquiry(createEnquiry);
//		
//		
//		
//		
//		String offerId2 = offerResult.getData().getResult().getOfferId();
//		OfferResult orr = null;
//		if(offerId2 != null ){
//			orr = offerResultRepository.findOfferResultByOfferId(offerId2);
//		}
//		if(null != orr){
//			Integer idd = orr.getId();
//			offerResult.setId(idd);
//		}
//		// 将数据保存
//		offerResultRepository.save(offerResult);
//		
//		// for Dafeng Api Customer
//		Platform platform2= createEnquiry.getPlatform();
//		List<DafengApiCustomer> dafengApiCustomers = dafengApiCustomerRepository.findByPlatform(platform2);	
//		if(dafengApiCustomers !=null&& (!dafengApiCustomers.isEmpty())){
//			// forward to Dafeng API customer
//			Map<String, String> offerResultMap4Api = new HashMap<String, String>();
//			JSONObject json = JSONObject.fromObject(offResMap); 
//			offerResultMap4Api.put("data", json.toString());
//			
//
//// different app platform --- url in db	(add later)
////			String offerRes4ApiResponse = HttpUtil.postForm(olEnquiryResUrl, offerResultMap4Api);
//			
//			
//			
//			
//			
////			System.out.println("HttpUtil.postForm(ostToDfgApiCustUrl, offerResultMap4Api)...;");
////			String offerRes4ApiResponse = HttpUtil.postForm("postToDfgApiCustUrl", offerResultMap4Api);
////			System.out.println("offerResultMap4Api...."+ offerResultMap4Api);
//			
////			String afApiOfferResURL = platform2.getPlatformOfferResultURL();
////			System.out.println("HttpUtil.postForm(afApiOfferResURL, offerResultMap4Api)...;");
////			for(int i =0; i<3; i++){
////				String offerRes4ApiResponse = HttpUtil.postForm("postToDfgApiCustUrl", offerResultMap4Api);
////				if(offerRes4ApiResponse == ){
////						break;
////				}
////			}
//			System.out.println("offerResultMap4Api.....:"+ offerResultMap4Api);
//			System.out.println("SUCCESS POST OFFER RESULT  TO API CUSTOMER" ); 
//			return responseReturn(offerResult.getData().getResult().getOfferId());
//		}	
//			
//			
//		
//		
//		//简单数据处理封装推送至安卓和ios
//		Map<String, Object> data=new HashMap<String,Object>();
//		data.put("Id", offerResult.getId());
//		data.put("name", "offerResult");
//		data.put("create_enquiry_id", offerResult.getCreateEnquiry().getId());
//		
//		
//		Map<String, Object> advertise = new HashMap<>();
//		advertise.put("title", "【报价通知】"+createEnquiry.getCreateEnquiryParams().getString("licenseNumber")+"有新的报价结果");
//		JSONObject priceJson =  offerResult.getData().getResult().getOfferDetail();
//		double originalPrice = priceJson.getDouble("originalPrice") ; 
//		double forcePrice  = priceJson.getJSONObject("forcePremium").getDouble("quotesPrice");
//		double taxPrice = priceJson.getJSONObject("taxPrice").getDouble("quotesPrice");
//		
//		double price = originalPrice + forcePrice + taxPrice ;
//		DecimalFormat df = new DecimalFormat("######0.00");   
//		String priceString = df.format(price);
//		
//		String content =  "车牌："+createEnquiry.getCreateEnquiryParams().getString("licenseNumber")
//				+"\n车主："+createEnquiry.getCreateEnquiryParams().getString("ownerName")
//				+"\n保险公司 : "+offerResult.getData().getResult().getInsuranceCompanyName() +"\n"
//				+ "保费总额： "+ priceString + "元";
//		
//		if(price <= 0.00001){
//			content = "车牌："+createEnquiry.getCreateEnquiryParams().getString("licenseNumber")
//					+"\n车主："+createEnquiry.getCreateEnquiryParams().getString("ownerName")
//					+"\n保险公司 : "+offerResult.getData().getResult().getInsuranceCompanyName() +"\n"
//					+ "报价结果 ： 报价失败";
//		}
//		
//		advertise.put("content", content);
//		
//		advertise.put("createAt", new Date().getTime());
//		
//		Advertise ad = new Advertise();
//		ad.setTitle((String) advertise.get("title"));
//		ad.setContent(content);
//		ad.setIsRead(0);
//		ad.setType(2);
//		ad.setZnxType(1);
//		ad.setToken(createEnquiry.getCustomer().getToken());
////		ad.setOfferResult(offerResult);
//		//防止站内信有多条
//		OfferResult aaa = offerResultRepository.findOfferResultByOfferId(offerId2);
////		if(aaa == null){
//			advertiseRepository.save(ad);
//			
////		}
//		
//		data.put("advertise", advertise);
//		Map<String, Object> handerDataMap=new HashMap<String,Object>();
//		handerDataMap.put("data", JSONObject.fromObject(data));
//		
//		
//		// 报价结果推送到指定平台
//		if (createEnquiry != null) {
//			Customer customer = offerResult.getCreateEnquiry().getCustomer();
//			if (customer == null) {
//				// 其他平台
//				System.out.println("推送至其他平台");
//				// HttpUtil.postBody(platform.getPlatformOfferURL(),JSONObject.fromObject(offResMap).toString());
//			} else if (customer.getClient().equals("ios")) {
//				// ios来的用户
//				System.out.println("推送至IOS平台");
//				xinGeService.pushIOS(customer.getPushToken(),handerDataMap ,(String) advertise.get("title") );
//			} else if (customer.getClient().equals("android")) {
//				// 安卓来的用户
//				System.out.println("推送至安卓平台");
//				xinGeService.pushAndroid(customer.getPushToken(),handerDataMap ,(String) advertise.get("title") , "报价结果返回");
//			} else {
//				throw new FailReturnObject(100, "报价结果无法推送");
//			} 
//		}else {
//			throw new FailReturnObject(100, "该报价结果没有询价记录,请重新询价");
//		}
//
//		return responseReturn(offerResult.getData().getResult().getOfferId());
	}

	private Advertise generateOfferResultAdvertise(OfferResult offerResult,String offerId) {
		
		CreateEnquiry createEnquiry = offerResult.getCreateEnquiry();
		JSONObject enqParamsJsoObj =createEnquiry.getCreateEnquiryParams(); 
		String licenseNumber = enqParamsJsoObj.getString("licenseNumber");
		String ownerName = enqParamsJsoObj.getString("ownerName");
		//titile
		StringBuffer titileBuf  =new StringBuffer();
		titileBuf.append("【报价通知】").append(licenseNumber).append("有新的报价结果");
		//content
		JSONObject priceJson =  offerResult.getData().getResult().getOfferDetail();
		double originalPrice = priceJson.getDouble("originalPrice") ;
		double forcePrice  = priceJson.getJSONObject("forcePremium").getDouble("quotesPrice");
		double taxPrice = priceJson.getJSONObject("taxPrice").getDouble("quotesPrice");
		double price = originalPrice + forcePrice + taxPrice ;
		DecimalFormat df = new DecimalFormat("######0.00");
		String priceString = df.format(price);

		StringBuffer contentBuf= new StringBuffer();
		contentBuf.append("车牌：").append(licenseNumber).append("\n车主：").append(ownerName);
		contentBuf.append("\n保险公司：").append(offerResult.getData().getResult().getInsuranceCompanyName());
		if(price <= 0.00001){
			contentBuf.append("\n报价结果 ： 报价失败");
		}else{
			contentBuf.append("\n保费总额： ").append(priceString).append("元");
		}
		Advertise advertise = new Advertise();
		advertise.setTitle(titileBuf.toString());
		advertise.setContent(contentBuf.toString());
		advertise.setIsRead(0);
		advertise.setType(2);
		advertise.setZnxType(1);
		advertise.setToken(createEnquiry.getCustomer().getToken());
		advertise.setOfferId(offerId);
		advertise.setCreateEnqId(createEnquiry.getId().toString());
		//防止站内信有多条
//		OfferResult aaa = offerResultRepository.findOfferResultByOfferId(offerId2);
//		if(aaa == null){
		advertise = advertiseRepository.save(advertise);
//		}
		return advertise;
	}

	protected String responseReturn(String offerId) {
		ResponseReturn responseReturn = new ResponseReturn(new ErrorMsg("success", "操作成功"), new Data(offerId), true);
		return JSONObject.fromObject(responseReturn).toString();
	}
	
	private void updateResultState(CreateEnquiry createEnquiry) {
		if(createEnquiry!=null) {
			Map<String, String> stateMap=new HashMap<String,String>();
			//fix states.
			CreateEnquiryState createEnquiryState =createEnquiryStateRepository.findByStateCode("ENQUIRY_RESULT");
			createEnquiry.setState(createEnquiryState);
			CreateEnquiry resultCreateEnquiry =createEnquiryRepository.save(createEnquiry);
			if(resultCreateEnquiry !=null){
				System.out.println("修改询价状态成功");
			}else{
				throw new FailReturnObject(ExceptionResultEnum.OFFERRES_DATA_STATE_ERROR);
//				throw new FailReturnObject(100, "修改询价状态失败");		
			}
		}else {
			throw new FailReturnObject(ExceptionResultEnum.OFFERRES_DATA_RECORD_ERROR);
//			throw new FailReturnObject(100, "该报价结果没有询价记录,请重新询价");
		}
	}

	public Set<OfferResult> getofferResultByCreId(Integer creId) {
//		http://118.31.239.203/rest/createEnquiries/1250/offerResult
		//ResponseBody responseBody = new ResponseBody();
		CreateEnquiry createEnquiry= createEnquiryRepository.findById(creId);
		if(createEnquiry== null){
			throw new FailReturnObject(ExceptionResultEnum.OFFERRES_QUERY_DATA_MIS_ERROR);
		}
		Set<OfferResult> offerResultSet= createEnquiry.getOfferResult();
		if(offerResultSet== null || offerResultSet.isEmpty()){
			throw new FailReturnObject(ExceptionResultEnum.OFFERRES_OFFER_MIS_ERROR);
		}
		return offerResultSet;
//		for(OfferResult or: offerResultSet){
//			or.setCreateEnquiry(null);
//		}
//        List<OfferResult> diaryPictureList = new ArrayList<OfferResult>(offerResultSet);   
//       
//        /*将list有序排列*/    
//        Collections.sort(diaryPictureList, new Comparator<OfferResult>() {    
//            public int compare(OfferResult arg0, OfferResult arg1) {    
//                return arg0.getId().compareTo(arg1.getId()); // 按照id排列    
//            }    
//        });   
        
//		responseBody = new ResponseBody(offerResultSet);
//		return responseBody;
		
//		List offerResultList = new ArrayList(offerResultSet);
//		JSONArray listArray=JSONArray.fromObject(offerResultList);
//		System.out.println("listObject:"+ listArray);

//		for(offerResultSet offerResultSet: offerResultSet){
//					
//		}
//		offerResultSet.
		
//		List offerResultList = new ArrayList(offerResultSet);
//		OfferResult offerResult=(OfferResult) offerResultList.get(0);
//		JsonConfig jsonConfig = new JsonConfig();
//		jsonConfig.setIgnoreDefaultExcludes(false);    
//		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);    //防止自包含
//		System.out.println(JSONObject.fromObject(offerResult, jsonConfig));
//		System.out.println("offerResultSet............" + offerResultSet);
//		System.out.println("offerResultList............" + offerResultList + " offerResult:" + offerResult + "\n JSONObject.fromObject(offerResult);" +JSONObject.fromObject(offerResult) );

		//1、使用JSONObject
//        JSONObject listObject=JSONObject.fromObject(offerResultList);
        //2、使用JSONArray
//        JSONArray listArray=JSONArray.fromObject(offerResultList);
		

//		JSONObject offerResultJson = JSONObject.fromObject(offerResultSet); 
//		System.out.println("offerResultJson............" + listArray);

		
		
	}

	public Map<String, Object> getOfferResultShareData(Integer id) {
		OfferResult offerResult = offerResultRepository.findOne(id);
		if (offerResult == null || offerResult.getData() == null) {
			throw new FailReturnObject(ExceptionResultEnum.OFFERRES_QUERY_DATA_MIS_ERROR);
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		OfferResultDataResult result = offerResult.getData().getResult();
		//车牌
		String licenseNumber = (String)offerResult.getCreateEnquiry().getLicenseNumber();
		returnMap.put("licenseNumber", licenseNumber);
		//厂牌型号
		if (result.getModelJson() != null && !StringUtils.isEmpty((String)result.getModelJson().get("modelName"))) {
			String modelName = (String)result.getModelJson().get("modelName");
			returnMap.put("modelName", modelName);
		}
		//车主
		String ownerName = (String)offerResult.getCreateEnquiry().getCreateEnquiryParams().get("ownerName");
		returnMap.put("ownerName", ownerName);
		//城市
		String cityName =  (String)offerResult.getCreateEnquiry().getCreateEnquiryParams().get("cityName");
		returnMap.put("cityName", cityName);
		
		double forcePremium = result.getOfferDetail().getJSONObject("forcePremium").getDouble("quotesPrice");
		double taxPrice = result.getOfferDetail().getJSONObject("taxPrice").getDouble("quotesPrice");
		double originalForcePrice = forcePremium+taxPrice;
		returnMap.put("originalForcePrice", originalForcePrice);
		double sumPrice = result.getOfferDetail().getDouble("originalPrice")+originalForcePrice;
		returnMap.put("sumPrice", sumPrice);
		JSONArray jsonArray = result.getOfferDetail().getJSONArray("insurances");
		StringBuffer deductibleDetail = new StringBuffer();
		for (Object object : jsonArray) {
			JSONObject jsonObject = (JSONObject)object;
			if (jsonObject.getDouble("quotesPrice") != 0 && jsonObject.getBoolean("compensation")) {
				int insuranceId = jsonObject.getInt("insuranceId");
				if (insuranceId == 1 || insuranceId == 63) {
					deductibleDetail.append("车损").append("、");
				}else if(insuranceId == 2 || insuranceId == 68) {
					deductibleDetail.append("三者").append("、");
				}else if (insuranceId == 3 || insuranceId == 74) {
					deductibleDetail.append("抢盗").append("、");
				}else if (insuranceId == 4 || insuranceId == 73) {
					deductibleDetail.append("司机").append("、");
				}else if (insuranceId == 5 || insuranceId == 89) {
					deductibleDetail.append("乘客").append("、");
				}else if (insuranceId == 7 || insuranceId == 75) {
					deductibleDetail.append("划痕").append("、");
				}else if (insuranceId == 8 || insuranceId == 36) {
					deductibleDetail.append("自燃").append("、");
				}else if (insuranceId == 9 || insuranceId == 16) {
					deductibleDetail.append("涉水").append("、");
				}else {
					deductibleDetail.append("");
				}
			}
		}
		if (!StringUtils.isEmpty(deductibleDetail.toString())) {
			returnMap.put("deductibleDetail", deductibleDetail.substring(0, deductibleDetail.length()-1).toString());
		}
		returnMap.put("data", offerResult.getData());
		return returnMap;
	}
	
	/**
	 * 从报价详情中提取保单总价
	 */
	public static double getSumPriceFromDataResult(OfferResultDataResult dataResult) {
		JSONObject offerDetail = dataResult.getOfferDetail();
		Double originalPrice = offerDetail.getDouble("originalPrice");
		Double ciBasePrice = offerDetail.getDouble("ciBasePrice");
//		Double taxPrice = dataResult.getTaxPrice().doubleValue() / 100;
		
//		Double originalPrice = dataResult.getOriginalPrice().doubleValue() / 100;
//		Double forcePremium = dataResult.getForcePremium().doubleValue() / 100;
//		Double taxPrice = dataResult.getTaxPrice().doubleValue() / 100;
		return originalPrice + ciBasePrice;
	}
	
	

}





