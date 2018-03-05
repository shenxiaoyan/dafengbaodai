package com.liyang.client.tianan.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.liyang.client.IClient;
import com.liyang.client.IMessage;
import com.liyang.client.IMockResponseFactory;
import com.liyang.client.IResponseSupplier;
import com.liyang.client.IService;
import com.liyang.client.IServiceObserve;
import com.liyang.client.ResponseSupplierPostJson;
import com.liyang.client.tianan.ClientTianan;
import com.liyang.client.tianan.ServiceObserveDbPersistPremiumAccurate;
import com.liyang.client.tianan.ServicePremiumAccurate;
import com.liyang.client.tianan.mock.MockResponseFactoryPremiumAccurate;
import com.liyang.domain.advertise.AdvertiseRepository;
import com.liyang.domain.api.tianan.PremiumAccurateApiParams;
import com.liyang.domain.createenquiry.CreateEnquiryRepository;
import com.liyang.domain.createenquiry.CreateEnquiryStateRepository;
import com.liyang.domain.offerresult.OfferResultRepository;
import com.liyang.service.XinGeService;
import com.liyang.util.Log4jUtil;

/**
 * @author Administrator
 *
 */
public class PremiumAccurateThread implements Runnable {

	private PremiumAccurateApiParams params;
	private CreateEnquiryRepository createEnquiryRepository;
	private XinGeService xinGeService;
	private AdvertiseRepository advertiseRepository;
	private OfferResultRepository offerResultRepository;
	private CreateEnquiryStateRepository createEnquiryStateRepository;
	private IMessage message;
	private String baseUrl;
	

	private final static Logger logger = LoggerFactory.getLogger(PremiumAccurateThread.class);

	public PremiumAccurateThread(String baseUrl, PremiumAccurateApiParams params, CreateEnquiryRepository createEnquiryRepository,
			XinGeService xinGeService, AdvertiseRepository advertiseRepository,
			OfferResultRepository offerResultRepository, CreateEnquiryStateRepository createEnquiryStateRepository,
			IMessage message) {
		if (params == null) {
			throw new NullPointerException("params为空");
		}
		this.params = params;
		this.createEnquiryRepository = createEnquiryRepository;
		this.xinGeService = xinGeService;
		this.advertiseRepository = advertiseRepository;
		this.offerResultRepository = offerResultRepository;
		this.createEnquiryStateRepository = createEnquiryStateRepository;
		if (message == null) {
			throw new NullPointerException("message为空");
		}
		this.message = message;
		this.baseUrl = baseUrl;
	}

	@Override
	public void run() {
		IResponseSupplier responseSupplier = new ResponseSupplierPostJson();
		if (!StringUtils.isEmpty(params.getMockType())) {
			IMockResponseFactory factory = new MockResponseFactoryPremiumAccurate();
			IResponseSupplier mockResponse = factory.getMockResponse(params.getMockType());
			if (mockResponse != null) {
				responseSupplier = mockResponse;
			}
		}
		IClient client = new ClientTianan(baseUrl);
		IServiceObserve serviceObserve = new ServiceObserveDbPersistPremiumAccurate(xinGeService, advertiseRepository,
				offerResultRepository, createEnquiryRepository, createEnquiryStateRepository);
		IService service = new ServicePremiumAccurate(client, serviceObserve, responseSupplier);
		try {
			service.callService(message);
		} catch (Exception e) {
			logger.error(Log4jUtil.formatException(e));
		}
	}

}
