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
import com.liyang.client.tianan.ServiceInsureConfirmation;
import com.liyang.client.tianan.ServiceObserveDbPersistInsureConfirmation;
import com.liyang.client.tianan.mock.MockResponseFactoryInsureConfirm;
import com.liyang.domain.advertise.AdvertiseRepository;
import com.liyang.domain.api.tianan.InsureConfirmationParams;
import com.liyang.domain.submitproposal.SubmitProposalRepository;
import com.liyang.domain.submitproposal.SubmitProposalStateRepository;
import com.liyang.domain.underwritingresult.UnderwritingResultRepository;
import com.liyang.service.XinGeService;
import com.liyang.util.Log4jUtil;

/**
 * 天安投保单保存线程
 * 
 * @author huanghengkun
 * @create 2017年12月17日
 */
public class InsureConfirmationThread implements Runnable {

	private InsureConfirmationParams params;
	private IMessage message;
	private XinGeService xinGeService;
	private SubmitProposalRepository submitProposalRepository;
	private UnderwritingResultRepository underwritingResultRepository;
	private AdvertiseRepository advertiseRepository;
	private SubmitProposalStateRepository submitProposalStateRepository;
	private String baseUrl;
	
	private final static Logger logger = LoggerFactory.getLogger(InsureConfirmationThread.class);

	public InsureConfirmationThread(String baseUrl, InsureConfirmationParams params, IMessage message, XinGeService xinGeService,
			SubmitProposalRepository submitProposalRepository,
			UnderwritingResultRepository underwritingResultRepository, AdvertiseRepository advertiseRepository,
			SubmitProposalStateRepository submitProposalStateRepository) {
		if (params == null) {
			throw new NullPointerException("params为空");
		}
		this.params = params;
		if (message == null) {
			throw new NullPointerException("message为空");
		}
		this.message = message;
		this.xinGeService = xinGeService;
		this.submitProposalRepository = submitProposalRepository;
		this.underwritingResultRepository = underwritingResultRepository;
		this.advertiseRepository = advertiseRepository;
		this.submitProposalStateRepository = submitProposalStateRepository;
		this.baseUrl = baseUrl;
	}

	@Override
	public void run() {
		IResponseSupplier responseSupplier = new ResponseSupplierPostJson();
		if (!StringUtils.isEmpty(params.getMockType())) {
			IMockResponseFactory factory = new MockResponseFactoryInsureConfirm();
			IResponseSupplier mockResponse = factory.getMockResponse(params.getMockType());
			if (mockResponse != null) {
				responseSupplier = mockResponse;
			}
		}
		IClient client = new ClientTianan(baseUrl);
		IServiceObserve serviceObserve = new ServiceObserveDbPersistInsureConfirmation(submitProposalRepository,
				underwritingResultRepository, advertiseRepository, xinGeService, submitProposalStateRepository);
		IService service = new ServiceInsureConfirmation(client, serviceObserve, responseSupplier);
		try {
			service.callService(message);
		} catch (Exception e) {
			logger.error(Log4jUtil.formatException(e));
		}
	}

}
