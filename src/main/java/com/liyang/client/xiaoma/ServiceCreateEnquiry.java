package com.liyang.client.xiaoma;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

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
import com.liyang.client.tianan.enums.ApiSupplierEnums;
import com.liyang.domain.createenquiry.CreateEnquiry;
import com.liyang.domain.createenquiry.CreateEnquiryActRepository;
import com.liyang.domain.createenquiry.CreateEnquiryLogRepository;
import com.liyang.domain.createenquiry.CreateEnquiryRepository;
import com.liyang.domain.createenquiry.CreateEnquiryStateRepository;
import com.liyang.domain.customer.CustomerRepository;
import com.liyang.domain.role.RoleRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.util.FailReturnObject;
import com.liyang.util.HttpUtil;
import com.liyang.util.TransUtils;

import net.sf.json.JSONObject;

/**
 * 查询询价
 * 
 * @author jacksunny
 *
 */
public class ServiceCreateEnquiry extends BaseService implements IService {
	CreateEnquiryActRepository createEnquiryActRepository;
	CreateEnquiryStateRepository createEnquiryStateRepository;
	CustomerRepository customerRepository;

	// public ServiceCreateEnquiry(String postToXiaoMaUrl, IClient client,
	// IServiceObserve serviceObserve,
	// IResponseSupplier responseSupplier, CustomerRepository
	// customerRepository,
	// CreateEnquiryActRepository createEnquiryActRepository,
	// CreateEnquiryStateRepository createEnquiryStateRepository, boolean
	// skipAutoValidate) {
	// super(client, serviceObserve, responseSupplier, skipAutoValidate);
	// setPartUrl(postToXiaoMaUrl);
	// this.customerRepository = customerRepository;
	// this.createEnquiryActRepository = createEnquiryActRepository;
	// this.createEnquiryStateRepository = createEnquiryStateRepository;
	// }
	//
	// public ServiceCreateEnquiry(String postToXiaoMaUrl, IClient client,
	// IServiceObserve serviceObserve,
	// IResponseSupplier responseSupplier, CustomerRepository
	// customerRepository,
	// CreateEnquiryActRepository createEnquiryActRepository,
	// CreateEnquiryStateRepository createEnquiryStateRepository) {
	// this(postToXiaoMaUrl, client, serviceObserve, responseSupplier,
	// customerRepository, createEnquiryActRepository,
	// createEnquiryStateRepository, false);
	// }
	//
	// public ServiceCreateEnquiry(String postToXiaoMaUrl, IClient client,
	// IServiceObserve serviceObserve,
	// CustomerRepository customerRepository, CreateEnquiryActRepository
	// createEnquiryActRepository,
	// CreateEnquiryStateRepository createEnquiryStateRepository) {
	// this(postToXiaoMaUrl, client, serviceObserve, new
	// ResponseSupplierPostForm(), customerRepository,
	// createEnquiryActRepository, createEnquiryStateRepository);
	// }

	public ServiceCreateEnquiry(SecurityInfoXiaoma securityInfo, IClient client, IServiceObserve serviceObserve,
			IResponseSupplier responseSupplier, CustomerRepository customerRepository,
			CreateEnquiryActRepository createEnquiryActRepository,
			CreateEnquiryStateRepository createEnquiryStateRepository, boolean skipAutoValidate) {
		super(client, serviceObserve, responseSupplier, skipAutoValidate);
		setPartUrl("xmcxapi/webService/enquiry/createEnquiry?api_key=" + securityInfo.getApiKey());
		this.customerRepository = customerRepository;
		this.createEnquiryActRepository = createEnquiryActRepository;
		this.createEnquiryStateRepository = createEnquiryStateRepository;
	}

	public ServiceCreateEnquiry(SecurityInfoXiaoma securityInfo, IClient client, IServiceObserve serviceObserve,
			IResponseSupplier responseSupplier, CustomerRepository customerRepository,
			CreateEnquiryActRepository createEnquiryActRepository,
			CreateEnquiryStateRepository createEnquiryStateRepository) {
		this(securityInfo, client, serviceObserve, responseSupplier, customerRepository, createEnquiryActRepository,
				createEnquiryStateRepository, false);
	}

	public ServiceCreateEnquiry(SecurityInfoXiaoma securityInfo, IClient client, IServiceObserve serviceObserve,
			CustomerRepository customerRepository, CreateEnquiryActRepository createEnquiryActRepository,
			CreateEnquiryStateRepository createEnquiryStateRepository) {
		this(securityInfo, client, serviceObserve, new ResponseSupplierPostForm(), customerRepository,
				createEnquiryActRepository, createEnquiryStateRepository);
	}

	// @Override
	// public Object detailCallService(IMessage generalMessage) {
	// MessageCreateEnquiry message = (MessageCreateEnquiry) generalMessage;
	// String responseResult = (String) client.postData(getUrl(),
	// message.getHanderCreEnqMap());
	// // String responseResult = HttpUtil.postForm(getUrl(),
	// // message.getHanderCreEnqMap());
	// // 测试用
	// // responseResult = "{\"errorMsg\": {\"code\": \"success\",\"message\":
	// // \"操作成功\"},\"data\": true,\"time\": null,\"successful\": true}";
	// return responseResult;
	// }

	@Override
	public IResult parseResult(Object response, IMessage generalMessage) throws Exception {
		ResultCreateEnquiry result = new ResultCreateEnquiry();
		MessageCreateEnquiry message = (MessageCreateEnquiry) generalMessage;
		String responseText = (String) response;
		CreateEnquiry createEnquiry = null;
		try {
			createEnquiry = parseCreateEnquiryResult(message, responseText);
		} catch (JsonParseException e) {
			e.printStackTrace();
			throw e;
		} catch (JsonMappingException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		result.setCreateEnquiry(createEnquiry);
		return result;
	}

	@Override
	public Object parseParameters(IMessage message) {
		MessageCreateEnquiry msg = (MessageCreateEnquiry) message;
		if (msg != null) {
			return msg.getHanderCreEnqMap();
		} else {
			return null;
		}
	}

	@Override
	public Object parseHeaders(IMessage message) {
		return null;
	}

	private CreateEnquiry parseCreateEnquiryResult(MessageCreateEnquiry message, String responseResult)
			throws IOException, JsonParseException, JsonMappingException, Exception {
		message.getLogger().info("【询价直接返回responseResult..........】：" + responseResult);
		if (responseResult == null) {
			message.getLogger().error("【询价返回结果错误，返回数据为空】 " + responseResult);
			throw new FailReturnObject(ExceptionResultEnum.CREQUERY_RETURN_DATA_ERROR);
		}
		ObjectMapper objectMapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, Object> originalResponseDataMap = objectMapper.readValue(responseResult, Map.class);
		Object errorMsg;

		if (null == originalResponseDataMap || null == (errorMsg = originalResponseDataMap.get("errorMsg"))
				|| !(errorMsg.toString().contains("success"))) {
			message.getLogger().error("【询价返回结果错误，数据为空或errorMsg Code为空】：" + responseResult);
			throw new FailReturnObject(ExceptionResultEnum.CREQUERY_RETURN_DATA_ERROR);
		}

		// 原入参数据转化为对象
		CreateEnquiry createEnquiry = TransUtils.mapTransStringObject(message.getHanderCreEnqMap(),
				CreateEnquiry.class);
		// 设置报价唯一标示，用于关联报价
		createEnquiry.setOfferUnique(message.getUUIDString());
		// 设置用户，关联用户
		createEnquiry.setCustomer(customerRepository.findByToken(message.getToken()));
		// 设置初始状态：询价中
		createEnquiry.setState(createEnquiryStateRepository.findByStateCode("INENQUIRY"));
		// 设置工作流：询价管理流程
		createEnquiry.setWorkflow(createEnquiryActRepository.findByActCode("create").getStartWorkflow());
		// 设置平台来源
		createEnquiry.setPlatform(message.getPlatform());
		// 设置返回结果
		createEnquiry.setResponseResult(responseResult);
		// 设置保险公司 no need
		// createEnquiry.setInsureCompanyIdStr(insurComName);

		// 设置车主、车牌 for 查询
		createEnquiry.setOwnerName(
				JSONObject.fromObject(message.getCreEnqMap().get("createEnquiryParams")).getString("ownerName"));
		createEnquiry.setLicenseNumber(
				JSONObject.fromObject(message.getCreEnqMap().get("createEnquiryParams")).getString("licenseNumber"));
		// 设置续保DataId做关联
		Object object = JSONObject.fromObject(message.getCreEnqMap().get("createEnquiryParams")).get("latestPolicyDataId");
		if (null != object && object instanceof Integer) {
			createEnquiry.setLatestPolicyDataId(
					JSONObject.fromObject(message.getCreEnqMap().get("createEnquiryParams")).getInt("latestPolicyDataId"));
		}
		ApiSupplierEnums apiSupplier = ApiSupplierEnums.XIAOMA;
		createEnquiry.setApiSupplier(apiSupplier);
		return createEnquiry;
	}

}
