package com.liyang.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.liyang.client.BaseResultDto;
import com.liyang.client.IClient;
import com.liyang.client.IMessage;
import com.liyang.client.IResponseSupplier;
import com.liyang.client.IResult;
import com.liyang.client.IService;
import com.liyang.client.IServiceObserve;
import com.liyang.client.ResponseSupplierPostJson;
import com.liyang.client.tianan.ClientTianan;
import com.liyang.client.tianan.MessageQueryProposal;
import com.liyang.client.tianan.ResultQueryProposal;
import com.liyang.client.tianan.ServiceQueryProposal;
import com.liyang.client.tianan.exception.ExceptionTiananInitFailed;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;
import com.liyang.client.tianan.exception.ExceptionTiananResult;
import com.liyang.client.tianan.mock.MockResponseFactoryQueryProposal;
import com.liyang.domain.api.tianan.IApiParams;
import com.liyang.domain.api.tianan.QueryProposalApiParams;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.util.FailReturnObject;
import com.liyang.client.tianan.dto.ProposalDataDto;
import com.liyang.client.tianan.dto.RiskDTO;

/**
 * 天安接口，投保单列表查询
 * 
 * @author duyiting
 * @create 2017.11.28
 * 
 * 
 */

@RestController
@RequestMapping("/dafeng")
public class QueryProposalController implements IApiController {

	// @Autowired
	// private QueryProposalRepository queryProposalRepository;
	@Value("${tianan.base.url}")
	private String tiananBaseUrl;

	@RequestMapping(value = "/queryProposal", method = RequestMethod.POST)
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

		// IServiceObserve serviceObserve = new
		// ServiceObserveDbPersistQueryProposal(queryProposalRepository);
		IServiceObserve serviceObserve = null;
		IResponseSupplier responseSupplier = new ResponseSupplierPostJson();
		if (!StringUtils.isEmpty(parms.getMockType())) {
			MockResponseFactoryQueryProposal factory = new MockResponseFactoryQueryProposal();
			IResponseSupplier mockResponse = factory.getMockResponse(parms.getMockType());
			if (mockResponse != null) {
				responseSupplier = mockResponse;
			}
		}

		try {
			//前端测试先把message传为null
//			IMessage message = buildMessage(parms, request);
			IMessage message = null;
			IClient client = new ClientTianan(tiananBaseUrl);
			ResultQueryProposal result = null;
			IService service = new ServiceQueryProposal(client, serviceObserve, responseSupplier);
			result = (ResultQueryProposal) service.callService(message);
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
		// TODO Auto-generated method stub
		QueryProposalApiParams detailParams = (QueryProposalApiParams) params;
		List<String> proList = detailParams.getProList();
		String tradeNo = detailParams.getTradeNo();
		MessageQueryProposal message = new MessageQueryProposal(tiananBaseUrl, tradeNo, proList);
		return message;
	}

	@Override
	public ResponseBody response(IResult result) throws Exception {
		// TODO Auto-generated method stub
		ResultQueryProposal detailResult = (ResultQueryProposal) result;
		// 返回前端封装
		if (detailResult.isSuccess()) {
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("dealFlag", detailResult.getDealFlag());
			resultMap.put("dealMessage", detailResult.getDealMessage());

			Map<String, Object> resultDTOMap = new HashMap<>();
			BaseResultDto resultDTO = detailResult.getResultDTO();
			resultDTOMap.put("resultCode", resultDTO.getResultCode());
			resultDTOMap.put("resultMess", resultDTO.getResultMess());
			resultMap.put("resultDTO", resultDTOMap);

			List<Map<String, Object>> proposalDataList = new ArrayList<>();
			List<ProposalDataDto> proposalDataDtoList = detailResult.getProposalDataDtoList();
			if (proposalDataDtoList != null) {
				for (ProposalDataDto proposalData : proposalDataDtoList) {
					Map<String, Object> proposalDataMap = new HashMap<>();
					// 核保处理意见
					proposalDataMap.put("handleText", proposalData.getHandleText());
					// 录单时间
					proposalDataMap.put("inputTime", proposalData.getInputTime());
					// 保单号
					proposalDataMap.put("policyNo", proposalData.getPolicyNo());
					// 投保单号
					proposalDataMap.put("proposalNo", proposalData.getProposalNo());
					// 人车联动人身险投保单号
					proposalDataMap.put("rcldProposalNo", proposalData.getRcldProposalNo());
					// 人车联动人身险投保单核保状态
					proposalDataMap.put("rcldUnderWriteInd", proposalData.getRcldUnderWriteInd());
					// 险种编码
					proposalDataMap.put("riskCode", proposalData.getRiskCode());

					List<Map<String, Object>> riskList = new ArrayList<>();
					List<RiskDTO> riskDtoList = proposalData.getRiskList();
					if (riskDtoList != null) {
						for (RiskDTO riskDto : riskDtoList) {
							Map<String, Object> riskDtoMap = new HashMap<>();
							riskDtoMap.put("proposalNo", riskDto.getProposalNo());
							riskDtoMap.put("subProposalNo", riskDto.getSubProposalNo());
							riskDtoMap.put("subPolicyNo", riskDto.getSubPolicyNo());
							riskDtoMap.put("planCode", riskDto.getPlanCode());
							riskDtoMap.put("riskCode", riskDto.getRiskCode());
							riskDtoMap.put("startDate", riskDto.getStartDate() == null ? null : riskDto.getStartDate());
							riskDtoMap.put("endDate", riskDto.getEndDate() == null ? null : riskDto.getEndDate());
							riskDtoMap.put("commenceDate",
									riskDto.getCommenceDate() == null ? null : riskDto.getCommenceDate());
							riskDtoMap.put("uwYear", riskDto.getUwYear());
							riskDtoMap.put("currency", riskDto.getCurrency());
							riskDtoMap.put("sumInsured", riskDto.getSumInsured());
							riskDtoMap.put("sumGrossPremium", riskDto.getSumGrossPremium());
							riskDtoMap.put("sumNetPremium", riskDto.getSumNetPremium());
							riskDtoMap.put("sumUWPremium", riskDto.getSumUWPremium());
							riskDtoMap.put("validInd", riskDto.getValidInd());
							riskDtoMap.put("effectFlag", riskDto.getEffectFlag());
							riskDtoMap.put("sumDiscount", riskDto.getSumDiscount());
							riskDtoMap.put("specialReinsInd", riskDto.getSpecialReinsInd());
							riskList.add(riskDtoMap);
						}
					}
					// 险种详细信息列表
					proposalDataMap.put("riskList", riskList);
					// 险种名称
					proposalDataMap.put("riskName", proposalData.getRiskName());
					// 处理状态
					proposalDataMap.put("status", proposalData.getStatus());
					// 核保通过时间
					proposalDataMap.put("undwrtTime",
							proposalData.getUndwrtTime() == null ? null : proposalData.getUndwrtTime());
					// 承保确认时间
					proposalDataMap.put("acceptdate",
							proposalData.getAcceptdate() == null ? null : proposalData.getAcceptdate());
					/*
					 * proposalDataMap.put("undwrtTime","2015-3-13");
					 * proposalDataMap.put("acceptdate","2015-3-14");
					 */
					proposalDataList.add(proposalDataMap);
				}
			}

			resultMap.put("proposalDataList", proposalDataList);

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
