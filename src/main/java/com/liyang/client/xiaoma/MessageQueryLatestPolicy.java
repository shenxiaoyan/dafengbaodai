package com.liyang.client.xiaoma;

import java.util.Map;

import org.slf4j.Logger;

import com.liyang.client.IMessage;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;
import com.liyang.domain.platform.Platform;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
public class MessageQueryLatestPolicy implements IMessage {
	private Map<String, String> queLatPolMap;
	private Platform platform;
	Logger logger;

	public MessageQueryLatestPolicy(Logger logger, Platform platform, Map<String, String> queLatPolMap) throws Exception {
		this.logger = logger;
		this.queLatPolMap = queLatPolMap;
		this.platform = platform;
		this.initDefaultValue();
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {
		
	}

	@Override
	public void initDefaultValue() throws Exception {
		prepareQueryLatestPolicyValue(queLatPolMap);
	}

	/**
	 * @return the queLatPolMap
	 */
	public Map<String, String> getQueLatPolMap() {
		return queLatPolMap;
	}

	/**
	 * @param queLatPolMap
	 *            the queLatPolMap to set
	 */
	public void setQueLatPolMap(Map<String, String> queLatPolMap) {
		this.queLatPolMap = queLatPolMap;
	}

	private void prepareQueryLatestPolicyValue(Map<String, String> queLatPolMap) {
		if (null == queLatPolMap || queLatPolMap.isEmpty()) {
			throw new FailReturnObject(ExceptionResultEnum.QUERYLATPOL_DATA_ERROR);
		}
		for (Map.Entry<String, String> dataMap : queLatPolMap.entrySet()) {
			logger.info(dataMap.getKey() + ":" + dataMap.getValue());
		}
	}

	/**
	 * @return the platform
	 */
	public Platform getPlatform() {
		return platform;
	}

	/**
	 * @param platform
	 *            the platform to set
	 */
	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

}
