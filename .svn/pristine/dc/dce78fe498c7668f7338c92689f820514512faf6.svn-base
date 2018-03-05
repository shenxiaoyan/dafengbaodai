package com.liyang.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liyang.client.tianan.enums.ApiSupplierEnums;
import com.liyang.domain.carModel.CarModel;
import com.liyang.domain.carModel.CarModelRepository;
import com.liyang.domain.carModel.CarModelVehicleModel;
import com.liyang.domain.offerresult.OfferResult;
import com.liyang.domain.offerresult.OfferResultRepository;
import com.liyang.domain.querylatestpolicy.QueryLatestPolicy;
import com.liyang.domain.querylatestpolicy.QueryLatestPolicyRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.service.OfferResultService;
import com.liyang.util.CommonUtil;
import com.liyang.util.FailReturnObject;

import net.sf.json.JSONObject;

/**
 * 报价结果推送 小马接口推送过来为x-www-form-urlencode格式 需要解码,数据格式为date={数据}
 * 
 * @author Administrator
 */

@RestController
@RequestMapping("/dafeng")
public class OfferResultController {

	@Autowired
	OfferResultService offerResultService;
	@Autowired
	OfferResultRepository offerResultRepository;
	@Autowired
	QueryLatestPolicyRepository queryLatestPolicyRepository;
	@Autowired
	private CarModelRepository carModelRepository;

	private final static Logger logger = LoggerFactory.getLogger(OfferResultController.class);

	/**
	 * 报价结果小马推送接口
	 * 
	 * @param offerResultSring
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/offerResult", method = RequestMethod.POST)
	public String offerResult(@RequestParam(value = "data", required = true) String offerResultSring) throws Exception {
		// 保存加转发到指定平台
		logger.info("【收到小马报价结果推送，original数据】:" + offerResultSring);
		String responseBody = offerResultService.saveOffResAndForward(CommonUtil.handerToMap(offerResultSring));
		System.out.println("报价结果返回：：：：：：" + responseBody);
		return responseBody;
	}

	/**
	 * check where to use 报价结果推送转发,封装为指定数据 单个保险公司 -- single insuranceCom when Push
	 */
	@RequestMapping(value = "/forwardOfferResult", method = RequestMethod.GET)
	public ResponseBody forwardOfferRestult(@RequestParam("id") Integer id) {
		OfferResult offerResult = offerResultRepository.findById(id);
		if (offerResult != null) {
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("data", offerResult.getData());
			String offerUnique = offerResult.getCreateEnquiry().getOfferUnique();
			getEngineAndFrame(resultMap, offerUnique, offerResult);
//			return new ResponseBody(offerResult.getData());
			return new ResponseBody(resultMap);
		} else {
			throw new FailReturnObject(ExceptionResultEnum.OFFERRES_DATA_FAIL_ERROR);
		}
	}

	/**
	 * 报价分享，获取页面所需数据
	 * 
	 * @param id
	 *            offerResult中id字段值
	 * @return
	 */
	// TODO 如报价失败，抛错提示页面报价失败，无数据
	@RequestMapping(value = "/shareOfferResultData", method = RequestMethod.GET)
	public ResponseBody shareOfferResultData(@RequestParam("id") Integer id) {
		Map<String, Object> shareOfferResultData = offerResultService.getOfferResultShareData(id);
		return new ResponseBody(shareOfferResultData);
	}

	/**
	 * 根据询价记录Id，获取该记录下报价记录
	 * 
	 * @param creId
	 *            询价记录Id字段值
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getofferResultByCreId", method = RequestMethod.POST)
	public ResponseBody offerResult(@RequestParam(value = "id", required = true) Integer creId) throws Exception {
		// CreateEnquiry createEnquiry = createEnquiryRepository.findOne(creId);
		Set<OfferResult> offerResultSet = offerResultService.getofferResultByCreId(creId);
		List<Map<String, Object>> resultList = new ArrayList<>();
		for (OfferResult offResult : offerResultSet) {
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("id", offResult.getId());
			resultMap.put("errorMsg", offResult.getErrorMsg());
			resultMap.put("successful", offResult.getSuccessful());
			resultMap.put("time", offResult.getTime());
			resultMap.put("data", offResult.getData());
			String offerUnique = offResult.getCreateEnquiry().getOfferUnique();
			getEngineAndFrame(resultMap, offerUnique, offResult);
			resultList.add(resultMap);
		}
		ResponseBody responseBody = new ResponseBody(resultList);
		return responseBody;
	}
	
	/**
	 * 添加发动机号、车架号
	 * @return
	 */
	public Map<String, Object> getEngineAndFrame(Map<String, Object> map, String offerUnique, OfferResult offResult){
		if (ApiSupplierEnums.TIANAN.equals(offResult.getCreateEnquiry().getApiSupplier())) {
			CarModel carModel = carModelRepository.findByTradeNo(offerUnique);
			if (null != carModel) {
				String requestMessageJson = carModel.getRequestMessage();
				JSONObject JSONObj = JSONObject.fromObject(requestMessageJson);
				String enginNo = JSONObj.getString("enginNo");
				String frameNo = JSONObj.getString("frameNo");
				map.put("engineNo", enginNo);
				map.put("vehicleFrameNo", frameNo);
				if (null != offResult.getCreateEnquiry().getRbCode()) {
					Set<CarModelVehicleModel> carModelList = carModel.getVehicleModelList();
					Iterator<CarModelVehicleModel> it = carModelList.iterator();
					CarModelVehicleModel model = null;
					while (it.hasNext()) {
						CarModelVehicleModel temp = it.next();
						if (offResult.getCreateEnquiry().getRbCode().equals(temp.getRbcode())) {
							model = temp;
						}
					}
					map.put("modelName", model.getBrandName());
				}
			}else {
				map.put("engineNo", "");
				map.put("vehicleFrameNo", "");
			}
			// TODO 如后续有小马、天安之外第三方接口，需处理
		} else {
			Integer latestPolicyDataId = offResult.getCreateEnquiry().getLatestPolicyDataId();
			QueryLatestPolicy queryLatestPolicy = null;
			if (latestPolicyDataId != null) {
				queryLatestPolicy = queryLatestPolicyRepository
						.findByQueryLatestPolicyResult_Data_Id(latestPolicyDataId);
			}
			if (null != queryLatestPolicy && null != queryLatestPolicy.getQueryLatestPolicyResult().getData().getCarInfo()) {
				map.put("engineNo",
						queryLatestPolicy.getQueryLatestPolicyResult().getData().getCarInfo().getEngineNo());
				map.put("vehicleFrameNo",
						queryLatestPolicy.getQueryLatestPolicyResult().getData().getCarInfo().getVehicleFrameNo());
			}else {
				map.put("engineNo","");
				map.put("vehicleFrameNo","");
			}
			if (null != offResult.getData().getResult().getModelJson() && null != offResult.getData().getResult().getModelJson().getString("modelName")) {
				map.put("modelName", offResult.getData().getResult().getModelJson().getString("modelName"));
			}else{
				map.put("modelName", "");
			}
		}
		return map;
	}
	
	
	
}






















