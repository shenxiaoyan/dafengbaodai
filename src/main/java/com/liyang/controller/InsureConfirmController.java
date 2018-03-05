package com.liyang.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.liyang.client.BaseResultDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.liyang.client.IClient;
import com.liyang.client.IMessage;
import com.liyang.client.IResponseSupplier;
import com.liyang.client.IResult;
import com.liyang.client.IService;
import com.liyang.client.IServiceObserve;
import com.liyang.client.ResponseSupplierPostJson;
import com.liyang.client.tianan.ClientTianan;
import com.liyang.client.tianan.MessageInsureConfirmation;
import com.liyang.client.tianan.ResultInsureConfirmation;
import com.liyang.client.tianan.ServiceInsureConfirmation;
import com.liyang.client.tianan.dto.ApplyInfoDto;
import com.liyang.client.tianan.exception.ExceptionTiananInitFailed;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;
import com.liyang.client.tianan.mock.MockResponseFactoryInsureConfirm;
import com.liyang.client.tianan.mock.MockSuccessResponseSupplierInsureConfirmation;
import com.liyang.domain.api.tianan.IApiParams;
import com.liyang.domain.api.tianan.InsureConfirmParams;
import com.liyang.domain.api.tianan.QueryProposalApiParams;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.client.tianan.ResultInsureConfirmation;
import com.liyang.client.tianan.exception.ExceptionTiananResult;
import com.liyang.util.FailReturnObject;

/**
 * 天安接口，投保单保存
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/dafeng")
public class InsureConfirmController implements IApiController {
	
	@Value("${tianan.base.url}")
	private String tiananBaseUrl;
	
	@RequestMapping(value = "insureConfirm", method = RequestMethod.POST)
	public ResponseBody mobileQueryProposalController(@RequestBody Map<String, Object> requestMap,
			HttpServletRequest request) {
		
		Object tiananObject = requestMap.get("tianan");
		if(tiananObject == null) {
			//错误提示
			return new ResponseBody("请添加tianan节点");
		}
		String jsonString = JSON.toJSONString(tiananObject);
		QueryProposalApiParams parms = JSON.parseObject(jsonString, new TypeReference<QueryProposalApiParams>() {
		});
	
		IServiceObserve serviceObserve = null;
		IResponseSupplier responseSupplier = new ResponseSupplierPostJson();
		if (!StringUtils.isEmpty(parms.getMockType())) {
			MockResponseFactoryInsureConfirm factory = new MockResponseFactoryInsureConfirm();
			IResponseSupplier mockResponse = factory.getMockResponse(parms.getMockType());
			if (mockResponse != null) {
				responseSupplier = mockResponse;
			}
		}
		try {
			//TODO 前端测试先把message传为null
			//IMessage message = buildMessage(params, request);
			IMessage message = null;
			
			IClient client = new ClientTianan(tiananBaseUrl);
			ResultInsureConfirmation result = null;
			IService service = new ServiceInsureConfirmation(client, serviceObserve, responseSupplier);
			result = (ResultInsureConfirmation) service.callService(message);
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
		InsureConfirmParams detailParams = (InsureConfirmParams) params;
//		String tradeNo = detailParams.getTradeNo();
//		ApplyInfoDto applyInfoDto=new ApplyInfoDto.Builder(applyName,
//		applyidentifyType, applyidentifyNumber).build();
//		String applyId = detailParams.getApplyId();
//		String insuredId = detailParams.getInsuredId();
//		String carOwnerId = detailParams.getCarOwnerId();
//		String rateMark = detailParams.getRateMark();
//		// TODO carPremiumCaculateNo 精确报价
//		String carPremiumCaculateNo = null;
//		MessageInsureConfirmation message=new
//		MessageInsureConfirmation.Builder(tradeNo, rateMark,
//		carPremiumCaculateNo, applyInfoDto, insureInfoDto, carOwerDto,
//		deliveryDto);
		MessageInsureConfirmation message = null;
		return message;
	}

	@Override
	public ResponseBody response(IResult result) throws Exception {
		ResultInsureConfirmation detailResult = (ResultInsureConfirmation) result;
		// 返回前端封装
		if (detailResult.isSuccess()) {
			Map<String, Object> resultMap = new HashMap<>();

			BaseResultDto resultDTO = detailResult.getResultDTO();
			Map<String, Object> resultDTOMap = new HashMap<>();
			resultDTOMap.put("resultCode", resultDTO.getResultCode());
			resultDTOMap.put("resultMess", resultDTO.getResultMess());
			resultMap.put("resultDTO", resultDTOMap);
			resultMap.put("cityCode", detailResult.getCityCode());
			resultMap.put("dealFlag", detailResult.getDealFlag());
			resultMap.put("dealMassage", detailResult.getDealMassage());
			resultMap.put("proposalNo", detailResult.getProposalNo());
			resultMap.put("busProposalNo", detailResult.getBusProposalNo());
			resultMap.put("busUnderWriteFlag", detailResult.getBusUnderWriteFlag());
			resultMap.put("bzProposalNo", detailResult.getBzProposalNo());
			resultMap.put("bzUnderWriteFlag", detailResult.getBzUnderWriteFlag());
			resultMap.put("evalLevel", detailResult.getEvalLevel());
			resultMap.put("rcldProposalNo", detailResult.getRcldProposalNo());
			resultMap.put("scxlProposalNo", detailResult.getScxlProposalNo());

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
