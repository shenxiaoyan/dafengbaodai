package com.liyang.client.xiaoma;

import com.liyang.client.BaseServiceObserveNode;
import com.liyang.client.IMessage;
import com.liyang.client.IResult;
import com.liyang.client.IServiceObserve;
import com.liyang.domain.createenquiry.CreateEnquiry;
import com.liyang.domain.createenquiry.CreateEnquiryRepository;
import com.liyang.domain.insurercompany.InsureCompany;
import com.liyang.domain.insurercompany.InsureCompanyRepository;
import com.liyang.domain.offerresult.OfferResult;
import com.liyang.domain.offerresult.OfferResultData;
import com.liyang.domain.offerresult.OfferResultDataRequestHeader;
import com.liyang.domain.offerresult.OfferResultDataResult;
import com.liyang.domain.offerresult.OfferResultRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.util.FailReturnObject;
import com.liyang.util.TransUtils;

/**
 * @author Administrator
 *
 */
public class ServiceObserveDbPersistCreateEnquiry extends BaseServiceObserveNode implements IServiceObserve {
	CreateEnquiryRepository createEnquiryRepository;
	InsureCompanyRepository insureCompanyRepository;
	OfferResultRepository offerResultRepository;

	public ServiceObserveDbPersistCreateEnquiry(CreateEnquiryRepository createEnquiryRepository,
			InsureCompanyRepository insureCompanyRepository, OfferResultRepository offerResultRepository) {
		this.createEnquiryRepository = createEnquiryRepository;
		this.insureCompanyRepository = insureCompanyRepository;
		this.offerResultRepository = offerResultRepository;
	}

	@Override
	public IMessage notifyBeforeCall(IMessage message) {
		return message;
	}

	@Override
	public IResult notifyAfterCall(IMessage message, IResult result) {
		ResultCreateEnquiry finalResult = (ResultCreateEnquiry) result;

		if (finalResult != null) {
			CreateEnquiry createEnquiry = finalResult.getCreateEnquiry();
			createEnquiry = observeCreateEnquiryDbPersist((MessageCreateEnquiry) message, createEnquiry);
			finalResult.setCreateEnquiry(createEnquiry);
		}

		return finalResult;
	}

	@Override
	public void notifyException(IMessage message, IResult result, Exception exception) {

	}

	private CreateEnquiry observeCreateEnquiryDbPersist(MessageCreateEnquiry message, CreateEnquiry createEnquiry) {
		// 询价数据保存
		createEnquiry = createEnquiryRepository.save(createEnquiry);

		// create record for OfferResult.OfferUnique, InsuranceCom
		for (int i = 0; i < message.getInsurComArray().length; i++) {
			// OfferUnique, applicationId, OriReqHeaderStr
			OfferResultDataRequestHeader offResDatRequestHeader = null;
			try {
				offResDatRequestHeader = TransUtils.mapTransStringObject(message.getReqHeaderMap(),
						OfferResultDataRequestHeader.class);
			} catch (Exception e) {
				message.getLogger().error(e.toString());
				throw new FailReturnObject(ExceptionResultEnum.OFFERRES_REQHEADER_TRANS_ERROR);
			}
			// InsuranceComId
			OfferResultDataResult offResDatResult = new OfferResultDataResult();
			offResDatResult.setInsuranceCompanyId(Integer.parseInt(message.getInsurComArray()[i]));
			InsureCompany company = insureCompanyRepository
					.findByInsurerCompanyId(Integer.parseInt(message.getInsurComArray()[i]));
			offResDatResult.setInsuranceCompanyName(company.getName());

			OfferResultData offerResultData = new OfferResultData();
			offerResultData.setRequestHeader(offResDatRequestHeader);
			offerResultData.setResult(offResDatResult);
			OfferResult offerResult = new OfferResult();
			offerResult.setData(offerResultData);
			offerResult.setCreateEnquiry(createEnquiry);
			offerResult.setPlatform(message.getPlatform());
			offerResultRepository.save(offerResult);
		}
		return createEnquiry;
	}
}
