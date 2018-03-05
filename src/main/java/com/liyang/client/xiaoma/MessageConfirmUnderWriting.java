package com.liyang.client.xiaoma;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.liyang.client.IMessage;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
public class MessageConfirmUnderWriting implements IMessage {
	private Logger logger;
	private String orderId;
	private Map<String, String> reqMap;

	public MessageConfirmUnderWriting(Logger logger, String orderId) throws Exception {
		this.setLogger(logger);
		this.orderId = orderId;
		this.initDefaultValue();
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {

	}

	@Override
	public void initDefaultValue() throws Exception {
		this.reqMap = prepareConfirmUnderWritingValue(this);
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * @param logger
	 *            the logger to set
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	/**
	 * @return the reqMap
	 */
	public Map<String, String> getReqMap() {
		return reqMap;
	}

	/**
	 * @param reqMap
	 *            the reqMap to set
	 */
	public void setReqMap(Map<String, String> reqMap) {
		this.reqMap = reqMap;
	}

	private Map<String, String> prepareConfirmUnderWritingValue(MessageConfirmUnderWriting message) {
		message.getLogger().info("请求承保结果.orderId:" + message.getOrderId());
		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("orderId", message.getOrderId());
		if (null == message.getOrderId() || message.getOrderId().trim().equalsIgnoreCase("")) {
			throw new FailReturnObject(ExceptionResultEnum.INSURANCERES_MIS_DATA_ERROR);
		}
		return reqMap;
	}
}
