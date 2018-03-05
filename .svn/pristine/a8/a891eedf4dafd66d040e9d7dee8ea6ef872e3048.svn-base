package com.liyang.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.liyang.client.tianan.MessageGetSign;
import com.liyang.client.tianan.MessageGetSignTest;
import com.liyang.client.tianan.MessageQueryCarModel;
import com.liyang.client.tianan.ResultGetSign;
import com.liyang.client.tianan.ResultQueryCarModel;
import com.liyang.client.tianan.ServiceGetSign;
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
 * 天安接口，获取签名接口
 * 
 * @author huanghengkun
 * @create 2017年11月24日
 */
@RestController
@RequestMapping("/dafeng")
public class GetSignController implements IApiController {

	@Autowired
	private CarModelRepository carModelRepository;

	@RequestMapping(value = "/getSign", method = RequestMethod.GET)
	public ResponseBody mobileQueryCarModelController(String baseUrl, HttpServletRequest request) {

		IServiceObserve serviceObserve = null;

		try {
			IApiParams params = null;
			IMessage message = buildMessage(params, request);
			IClient client = new ClientTianan(baseUrl);
			ResultGetSign result = null;
			IService service = new ServiceGetSign(client);
			result = (ResultGetSign) service.callService(message);
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
		IMessage result = null;
		String url = "http://www.dafengbaoxian.com";
		result = new MessageGetSignTest(url);
		return result;
	}

	@Override
	public ResponseBody response(IResult result) throws Exception {
		ResponseBody ret = null;
		ResultGetSign res = (ResultGetSign) result;
		ret = new ResponseBody(res.getSign());
		return ret;
	}

}
