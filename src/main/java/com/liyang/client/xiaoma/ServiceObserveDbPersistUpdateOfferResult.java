package com.liyang.client.xiaoma;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.liyang.client.BaseServiceObserveNode;
import com.liyang.client.IMessage;
import com.liyang.client.IResult;
import com.liyang.client.IServiceObserve;
import com.liyang.domain.createenquiry.CreateEnquiry;
import com.liyang.domain.createenquiry.CreateEnquiryState;
import com.liyang.domain.createenquiry.CreateEnquiryStateRepository;
import com.liyang.domain.offerresult.OfferResult;
import com.liyang.domain.offerresult.OfferResultRepository;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
public class ServiceObserveDbPersistUpdateOfferResult extends BaseServiceObserveNode implements IServiceObserve {
	OfferResultRepository offerResultRepository;
	CreateEnquiryStateRepository createEnquiryStateRepository;

	public ServiceObserveDbPersistUpdateOfferResult(OfferResultRepository offerResultRepository,
			CreateEnquiryStateRepository createEnquiryStateRepository) {
		this.offerResultRepository = offerResultRepository;
		this.createEnquiryStateRepository = createEnquiryStateRepository;
	}

	@Override
	public IMessage notifyBeforeCall(IMessage message) {
		return null;
	}

	@Override
	public IResult notifyAfterCall(IMessage message, IResult result) {
		ResultEnquiryCarModel finalRes = (ResultEnquiryCarModel) result;
		MessageEnquiryCarModel msg = (MessageEnquiryCarModel) message;

		if (null != finalRes && null != msg && null != msg.getModenqMap()) {
			String offerId = (String) msg.getModenqMap().get("offerId");
			OfferResult offerResult = null;
			offerResult = offerResultRepository.findOfferResultByOfferId(offerId);
			if (null != offerResult) {
				CreateEnquiry createEnquiry = offerResult.getCreateEnquiry();
				Set<OfferResult> offResultList = createEnquiry.getOfferResult();
				List<String> offerIdList = new ArrayList<>();
				for (OfferResult offResult : offResultList) {
					if (null != offResult.getData() && null != offResult.getData() && null != offResult.getData().getResult().getOfferId()) {
						offerIdList.add(offResult.getData().getResult().getOfferId());
					}
				}
				if (offerIdList.size() == 1) {
					CreateEnquiryState state = createEnquiryStateRepository.findByStateCode("INENQUIRY");
					offerResult.getCreateEnquiry().setState(state);
				}
//				if (offResultList.size() == 1) {
//					CreateEnquiryState state = createEnquiryStateRepository.findByStateCode("INENQUIRY");
//					offerResult.getCreateEnquiry().setState(state);
//				}
				
				offerResult.getCreateEnquiry().setCreatedAt(new Date());
				offerResult.setSuccessful(false);
				offerResult.getData().getResult().setErrorMsg(null);
				offerResult.getData().getResult().setOfferId(null);
				offerResultRepository.save(offerResult);
			}else{
				throw new FailReturnObject(222, "无法获取该条报价记录原始数据");
			}
		}
		return finalRes;
	}

	@Override
	public void notifyException(IMessage message, IResult result, Exception exception) {

	}

}
