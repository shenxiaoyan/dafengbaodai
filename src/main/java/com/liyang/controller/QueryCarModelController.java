package com.liyang.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.liyang.client.IClient;
import com.liyang.client.IMessage;
import com.liyang.client.IResponseSupplier;
import com.liyang.client.IResult;
import com.liyang.client.IService;
import com.liyang.client.IServiceObserve;
import com.liyang.client.ResponseSupplierPostJson;
import com.liyang.client.tianan.ClientTianan;
import com.liyang.client.tianan.MessageQueryCarModel;
import com.liyang.client.tianan.ResultQueryCarModel;
import com.liyang.client.tianan.ServiceObserveDbPersistQueryCarModel;
import com.liyang.client.tianan.ServiceQueryCarModel;
import com.liyang.client.tianan.TypeDate;
import com.liyang.client.tianan.dto.VehicleModelDTO;
import com.liyang.client.tianan.exception.ExceptionTiananInitFailed;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;
import com.liyang.client.tianan.exception.ExceptionTiananResult;
import com.liyang.client.tianan.mock.MockResponseFactoryQueryCarModel;
import com.liyang.domain.api.tianan.CarModelApiParmas;
import com.liyang.domain.api.tianan.IApiParams;
import com.liyang.domain.carModel.CarModelRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.util.FailReturnObject;

/**
 * 天安接口，查询车型接口
 * 
 * @author huanghengkun
 * @create 2017年11月24日
 */
@RestController
@RequestMapping("/dafeng")
public class QueryCarModelController implements IApiController {

	@Value("${tianan.base.url}")
	private String tiananBaseUrl;
	@Autowired
	private CarModelRepository carModelRepository;

	@RequestMapping(value = "queryCarModel", method = RequestMethod.POST)
	public ResponseBody mobileQueryCarModelController(@RequestBody Map<String, Object> requestMap,
			HttpServletRequest request) {

		Object tiananObject = requestMap.get("tianan");
		String jsonString = JSON.toJSONString(tiananObject);
		System.out.println("--------"+jsonString);
		CarModelApiParmas parms = JSON.parseObject(jsonString, new TypeReference<CarModelApiParmas>() {
		});

		IServiceObserve serviceObserve = new ServiceObserveDbPersistQueryCarModel(carModelRepository);
		IResponseSupplier responseSupplier = new ResponseSupplierPostJson();
		if (!StringUtils.isEmpty(parms.getMockType())) {
			MockResponseFactoryQueryCarModel factory = new MockResponseFactoryQueryCarModel();
			IResponseSupplier mockResponseSupplier = factory.getMockResponse(parms.getMockType());
			if (mockResponseSupplier != null) {
				responseSupplier = mockResponseSupplier;
			}
		}

		try {
			IMessage message = buildMessage(parms, request);
			IClient client = new ClientTianan(tiananBaseUrl);
			ResultQueryCarModel result = null;
			IService service = new ServiceQueryCarModel(client, serviceObserve, responseSupplier);
			result = (ResultQueryCarModel) service.callService(message);
			return response(result);
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof ExceptionTiananInitFailed) {
				throw new FailReturnObject(ExceptionResultEnum.API_TIANAN_INITFAILED, ":" + e.getMessage());
			} else if (e instanceof ExceptionTiananParamInvliad) {
				throw new FailReturnObject(ExceptionResultEnum.API_TIANAN_PARAMINVLIAD, ":" + e.getMessage());
			} else {
				throw new FailReturnObject(ExceptionResultEnum.API_TIANAN_ERROR, ":" + e.getMessage());
			}
		}

	}

	@Override
	public IMessage buildMessage(IApiParams params, HttpServletRequest request) throws Exception {
		CarModelApiParmas detailParmas = (CarModelApiParmas) params;
		String cityCode = detailParmas.getCityCode();
		String brandName = detailParmas.getBrandName();
		String enginNo = detailParmas.getEnginNo();
		TypeDate enrollDate = null;
		try {
			enrollDate = new TypeDate(detailParmas.getEnrollDate());
		} catch (ExceptionTiananParamInvliad e) {
			throw new ExceptionTiananParamInvliad("初次登记日期:" + e.getMessage());
		}
		TypeDate startDate = null;
		try {
			startDate = new TypeDate(detailParmas.getStartDate());
		} catch (ExceptionTiananParamInvliad e) {
			throw new ExceptionTiananParamInvliad("起保日期:" + e.getMessage());
		}
		String frameNo = detailParmas.getFrameNo();
		String licenseNo = detailParmas.getLicenseNo();
		// page和rows暂时不用，使用默认的 page=1 rows=100 获取全部车型
		// String page = parms.getPage();
		// String rows = parms.getRows();
		Date purchaseDate = detailParmas.getPurchaseDate();
		MessageQueryCarModel.Builder builder = new MessageQueryCarModel.Builder(tiananBaseUrl, cityCode, brandName, enginNo,
				enrollDate, startDate, frameNo, licenseNo);
		if (purchaseDate != null) {
			try {
				builder.purchaseDate(new TypeDate(purchaseDate));
			} catch (ExceptionTiananParamInvliad e) {
				throw new ExceptionTiananParamInvliad("车辆购置日期:" + e.getMessage());
			}
		}
		MessageQueryCarModel message = builder.build();
		return message;
	}

	@Override
	public ResponseBody response(IResult result) throws Exception {
		ResultQueryCarModel detailResult = (ResultQueryCarModel) result;
		// 返回前端封装
		if (detailResult.isSuccess()) {
			Map<String, Object> resultMap = new HashMap<>();
			List<Map<String, Object>> vehicleModelList = new ArrayList<>();

			Integer total = detailResult.getTotal();
			for (VehicleModelDTO vehicleModel : detailResult.getVehicleModelList()) {
				Map<String, Object> vehicleModelMap = new HashMap<>();
				// 车辆型号
				vehicleModelMap.put("brandName", vehicleModel.getBrandName());
				// 车款名称
				vehicleModelMap.put("carName", vehicleModel.getCarName());
				// 排量(单位毫升-->升)
				vehicleModelMap.put("exhaustCapacity", vehicleModel.getExhaustCapacity() / 1000);
				// 核定载客
				vehicleModelMap.put("seatCount", vehicleModel.getSeatCount());
				// 年款(上市年份)
				vehicleModelMap.put("marketDate", vehicleModel.getMarketDate());
				// 新车购置价
				vehicleModelMap.put("purchasePrice", vehicleModel.getPurchasePrice());
				// 实际价值
				vehicleModelMap.put("actualValue", vehicleModel.getActualValue());
				//车型代码
				vehicleModelMap.put("rbCode", vehicleModel.getRbcode());
				vehicleModelList.add(vehicleModelMap);
			}
			resultMap.put("vehicleModelList", vehicleModelList);
			resultMap.put("total", total);

			if (result.getParmas() != null) {
				for (Map.Entry<String, Object> entry : result.getParmas().entrySet()) {
					resultMap.put(entry.getKey(), entry.getValue());
				}
			}
			return new ResponseBody(resultMap);
		} else {
			throw new ExceptionTiananResult(detailResult.getErrorMess());
		}
	}
}
