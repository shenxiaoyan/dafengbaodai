package com.liyang.client.xiaoma;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.liyang.client.IMessage;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;
import com.liyang.domain.offerresult.OfferResult;
import com.liyang.domain.offerresult.OfferResultRepository;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
public class MessageEnquiryCarModel implements IMessage {
	private Map<String, Object> modenqMap;
	OfferResultRepository offerResultRepository;
	
	public MessageEnquiryCarModel(Map<String, Object> modenqMap, OfferResultRepository offerResultRepository) {
		this.modenqMap = modenqMap;
		this.offerResultRepository = offerResultRepository;
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {
		if (StringUtils.isEmpty((String)modenqMap.get("multiEnquiryId"))) {
			throw new FailReturnObject(111,"修改车型报价参数错误，无法获取询价统一编号(multiEnquiryId), 请重新询价！");
		}
		if (StringUtils.isEmpty((String)modenqMap.get("offerId"))) {
			throw new FailReturnObject(111,"修改车型报价参数错误，报价单号为空");
		}
		List<OfferResult> offerResultList = offerResultRepository.findByDataResultOfferId((String)modenqMap.get("offerId"));
		if (null == offerResultList || offerResultList.size() == 0) {
			throw new FailReturnObject(111,"修改车型报价参数错误，无法获取该记录单号中数据");
		}
		if (StringUtils.isEmpty(modenqMap.get("modelParam").toString())) {
			throw new FailReturnObject(111,"修改车型报价参数错误，modelParam 为空");
		}
	}

	@Override
	public void initDefaultValue() throws Exception {

	}

	/**
	 * @return the modenqMap
	 */
	public Map<String, Object> getModenqMap() {
		return modenqMap;
	}

	/**
	 * @param modenqMap
	 *            the modenqMap to set
	 */
	public void setModenqMap(Map<String, Object> modenqMap) {
		this.modenqMap = modenqMap;
	}

}
