package com.liyang.client.xiaoma;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyang.client.BaseService;
import com.liyang.client.IClient;
import com.liyang.client.IMessage;
import com.liyang.client.IResponseSupplier;
import com.liyang.client.IResult;
import com.liyang.client.IService;
import com.liyang.client.IServiceObserve;
import com.liyang.client.ResponseSupplierPostForm;
import com.liyang.client.strategy.SecurityInfoXiaoma;
import com.liyang.domain.createenquiry.CreateEnquiry;
import com.liyang.domain.createenquiry.CreateEnquiryRepository;
import com.liyang.domain.createenquiry.CreateEnquiryState;
import com.liyang.domain.createenquiry.CreateEnquiryStateRepository;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.customer.CustomerRepository;
import com.liyang.domain.offerresult.OfferResult;
import com.liyang.domain.offerresult.OfferResultRepository;
import com.liyang.domain.submitproposal.SubmitProposal;
import com.liyang.domain.submitproposal.SubmitProposalActRepository;
import com.liyang.domain.submitproposal.SubmitProposalRepository;
import com.liyang.domain.submitproposal.SubmitProposalStateRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.util.FailReturnObject;
import com.liyang.util.TransUtils;

/**
 * 提交核保接口
 * 
 * @author jacksunny
 *
 */
public class ServiceSubmitProposal extends BaseService implements IService {

	CustomerRepository customerRepository;
	OfferResultRepository offerResultRepository;
	CreateEnquiryStateRepository createEnquiryStateRepository;
	CreateEnquiryRepository createEnquiryRepository;
	SubmitProposalStateRepository submitProposalStateRepository;
	SubmitProposalActRepository submitProposalActRepository;
	SubmitProposalRepository submitProposalRepository;

	// public ServiceSubmitProposal(String postToXiaoMaUrl, IClient client,
	// IServiceObserve serviceObserve,
	// IResponseSupplier responseSupplier, boolean skipAutoValidate,
	// CustomerRepository customerRepository,
	// OfferResultRepository offerResultRepository, CreateEnquiryStateRepository
	// createEnquiryStateRepository,
	// CreateEnquiryRepository createEnquiryRepository,
	// SubmitProposalStateRepository submitProposalStateRepository,
	// SubmitProposalActRepository submitProposalActRepository,
	// SubmitProposalRepository submitProposalRepository) {
	// super(client, serviceObserve, responseSupplier);
	// setPartUrl(postToXiaoMaUrl);
	// this.customerRepository = customerRepository;
	// this.offerResultRepository = offerResultRepository;
	// this.createEnquiryStateRepository = createEnquiryStateRepository;
	// this.createEnquiryRepository = createEnquiryRepository;
	// this.submitProposalStateRepository = submitProposalStateRepository;
	// this.submitProposalActRepository = submitProposalActRepository;
	// this.submitProposalRepository = submitProposalRepository;
	// }
	//
	// public ServiceSubmitProposal(String postToXiaoMaUrl, IClient client,
	// IServiceObserve serviceObserve,
	// IResponseSupplier responseSupplier, CustomerRepository
	// customerRepository,
	// OfferResultRepository offerResultRepository, CreateEnquiryStateRepository
	// createEnquiryStateRepository,
	// CreateEnquiryRepository createEnquiryRepository,
	// SubmitProposalStateRepository submitProposalStateRepository,
	// SubmitProposalActRepository submitProposalActRepository,
	// SubmitProposalRepository submitProposalRepository) {
	// this(postToXiaoMaUrl, client, serviceObserve, responseSupplier, false,
	// customerRepository,
	// offerResultRepository, createEnquiryStateRepository,
	// createEnquiryRepository,
	// submitProposalStateRepository, submitProposalActRepository,
	// submitProposalRepository);
	// }
	//
	// public ServiceSubmitProposal(String postToXiaoMaUrl, IClient client,
	// IServiceObserve serviceObserve,
	// CustomerRepository customerRepository, OfferResultRepository
	// offerResultRepository,
	// CreateEnquiryStateRepository createEnquiryStateRepository,
	// CreateEnquiryRepository createEnquiryRepository,
	// SubmitProposalStateRepository submitProposalStateRepository,
	// SubmitProposalActRepository submitProposalActRepository,
	// SubmitProposalRepository submitProposalRepository) {
	// this(postToXiaoMaUrl, client, serviceObserve, new
	// ResponseSupplierPostForm(), customerRepository,
	// offerResultRepository, createEnquiryStateRepository,
	// createEnquiryRepository,
	// submitProposalStateRepository, submitProposalActRepository,
	// submitProposalRepository);
	// }

	public ServiceSubmitProposal(SecurityInfoXiaoma securityInfo, IClient client, IServiceObserve serviceObserve,
			IResponseSupplier responseSupplier, boolean skipAutoValidate, CustomerRepository customerRepository,
			OfferResultRepository offerResultRepository, CreateEnquiryStateRepository createEnquiryStateRepository,
			CreateEnquiryRepository createEnquiryRepository,
			SubmitProposalStateRepository submitProposalStateRepository,
			SubmitProposalActRepository submitProposalActRepository,
			SubmitProposalRepository submitProposalRepository) {
		super(client, serviceObserve, responseSupplier);
		setPartUrl("xmcxapi/webService/enquiry/submitProposal?api_key=" + securityInfo.getApiKey());
		this.customerRepository = customerRepository;
		this.offerResultRepository = offerResultRepository;
		this.createEnquiryStateRepository = createEnquiryStateRepository;
		this.createEnquiryRepository = createEnquiryRepository;
		this.submitProposalStateRepository = submitProposalStateRepository;
		this.submitProposalActRepository = submitProposalActRepository;
		this.submitProposalRepository = submitProposalRepository;
	}

	public ServiceSubmitProposal(SecurityInfoXiaoma securityInfo, IClient client, IServiceObserve serviceObserve,
			IResponseSupplier responseSupplier, CustomerRepository customerRepository,
			OfferResultRepository offerResultRepository, CreateEnquiryStateRepository createEnquiryStateRepository,
			CreateEnquiryRepository createEnquiryRepository,
			SubmitProposalStateRepository submitProposalStateRepository,
			SubmitProposalActRepository submitProposalActRepository,
			SubmitProposalRepository submitProposalRepository) {
		this(securityInfo, client, serviceObserve, responseSupplier, false, customerRepository, offerResultRepository,
				createEnquiryStateRepository, createEnquiryRepository, submitProposalStateRepository,
				submitProposalActRepository, submitProposalRepository);
	}

	public ServiceSubmitProposal(SecurityInfoXiaoma securityInfo, IClient client, IServiceObserve serviceObserve,
			CustomerRepository customerRepository, OfferResultRepository offerResultRepository,
			CreateEnquiryStateRepository createEnquiryStateRepository, CreateEnquiryRepository createEnquiryRepository,
			SubmitProposalStateRepository submitProposalStateRepository,
			SubmitProposalActRepository submitProposalActRepository,
			SubmitProposalRepository submitProposalRepository) {
		this(securityInfo, client, serviceObserve, new ResponseSupplierPostForm(), customerRepository,
				offerResultRepository, createEnquiryStateRepository, createEnquiryRepository,
				submitProposalStateRepository, submitProposalActRepository, submitProposalRepository);
	}

	// @Override
	// public Object detailCallService(IMessage generalMessage) throws Exception
	// {
	// Object result = null;
	// MessageSubmitProposal message = (MessageSubmitProposal) generalMessage;
	// // // 将数据提交到小马接口
	// // String responseResult = HttpUtil.postForm(getUrl(),
	// // message.getFormParams());
	// String responseResult = (String) client.postData(getUrl(),
	// message.getFormParams());
	// // // 测试用
	// // responseResult = "{\"errorMsg\": {\"code\": \"success\",\" message\"
	// // : \" 操作成功\"},\"data\":true,\"time\": null,\"successful\": true}";
	// // message.getReslutMap().put("orderId", "200-20171020214016-317505");
	// result = responseResult;
	// return result;
	// }

	@Override
	public IResult parseResult(Object response, IMessage generalMessage) throws Exception {
		ResultSubmitProposal result = new ResultSubmitProposal();
		MessageSubmitProposal message = (MessageSubmitProposal) generalMessage;
		String responseResult = (String) response;
		SubmitProposal submitProposal = parseSubmitProposalResult(message, responseResult);
		result.setSubmitProposal(submitProposal);
		return result;
	}

	@Override
	public Object parseParameters(IMessage message) throws Exception {
		return ((MessageSubmitProposal) message).getFormParams();
	}

	@Override
	public Object parseHeaders(IMessage message) throws Exception {
		return null;
	}

	private SubmitProposal parseSubmitProposalResult(MessageSubmitProposal message, String responseResult)
			throws IOException, JsonParseException, JsonMappingException, Exception {
		if (responseResult == null) {
			throw new FailReturnObject(ExceptionResultEnum.SUBPROPOSAL_RETURN_DATA_ERROR);
		}
		ObjectMapper objectMapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, Object> originalResponseDataMap = objectMapper.readValue(responseResult, Map.class);
		Map<String, String> errorMsg;

		if (null == originalResponseDataMap || null == (errorMsg = (Map) originalResponseDataMap.get("errorMsg"))) {
			throw new FailReturnObject(ExceptionResultEnum.SUBPROPOSAL_RETURN_DATA_ERROR);
		}
		if (!("success".equals(errorMsg.get("code")))) {
			throw new FailReturnObject(ExceptionResultEnum.SUBPROPOSAL_RETURN_DATA_ERROR,
					":" + errorMsg.get("message"));
		}

		// String responseResult
		// ="{\"errorMsg\":{\"code\":\"success\",\"message\":\"操作成功\"},\"data\":true,\"time\":null,\"successful\":true}";
		// 将提交核保数据转换成对象
		SubmitProposal submitProposal = TransUtils.mapTransObject(message.getSubProMap(), SubmitProposal.class);
		// 保存提交核保结果
		submitProposal.setResponseResult(responseResult);
		// 关联平台
		submitProposal.setPlatform(message.getPlatform());
		
		//设置大蜂配送信息
		submitProposal.getParams().setDafengContactName(submitProposal.getParams().getContactName());
		submitProposal.getParams().setDafengContactPhone(submitProposal.getParams().getContactPhone());
		submitProposal.getParams().setDafengAddress(submitProposal.getParams().getContactAddress().getContactAddressDetail());
		
		// 关联用户
		Customer customer = customerRepository.findByToken(message.getToken());
		submitProposal.setCustomer(customer);
		// 关联报价结果
		String orderId = (String) message.getReslutMap().get("orderId");
		
		//TODO  为何可能是多个---by djh
		List<OfferResult> offerResultList = offerResultRepository.findByDataResultOfferId(orderId);
		if (null == offerResultList || offerResultList.isEmpty()) {
			throw new FailReturnObject(ExceptionResultEnum.SUBPROPOSAL_OFFERRES_DATA_MIS_ERROR);
		}
		submitProposal.setOfferResult(offerResultList.get(0));

		// 设置提交核保的时间
		submitProposal.setCreateTime(new Date());

		CreateEnquiryState state3 = createEnquiryStateRepository.findByStateCode("SUBMIT_ALREADY");
		CreateEnquiry cee = offerResultList.get(0).getCreateEnquiry();
		cee.setState(state3);

		createEnquiryRepository.save(cee);

		// 设置工作流的初始状态：核保中
		submitProposal.setState(submitProposalStateRepository.findByStateCode("HENBAOING"));

		// 设置工作流：出单管理流程
		submitProposal.setWorkflow(submitProposalActRepository.findByActCode("create").getStartWorkflow());
		// TODO 什么情况会已存在已有记录？？？什么情况存在多条记录？？？
		List<SubmitProposal> submitProposalList = submitProposalRepository.findByParamsOrderId(orderId);
		if (null != submitProposalList && (!submitProposalList.isEmpty())) {
			submitProposal.setId(submitProposalList.get(0).getId());
		}
		return submitProposal;
	}
}
