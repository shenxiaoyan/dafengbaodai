package com.liyang.client.xiaoma;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;

import com.liyang.client.IMessage;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;
import com.liyang.domain.querypayment.QueryPay;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
public class MessageQueryPayState implements IMessage {

	private Map<String, String> quePayStatMap;
	private Logger logger;
	private QueryPay queryPay;
	private String orderId;

	public MessageQueryPayState(Logger logger, Map<String, String> quePayStatMap) throws Exception {
		this.logger = logger;
		this.quePayStatMap = quePayStatMap;
		this.initDefaultValue();
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {

	}

	@Override
	public void initDefaultValue() throws Exception {
		prepareValue(this);
	}

	private void prepareValue(MessageQueryPayState message) {
		message.getLogger().info("查询支付状况： 订单号" + message.getQuePayStatMap().get("orderId"));
		queryPay = new QueryPay();
		queryPay.setCreateTime(new Date());
		orderId = message.getQuePayStatMap().get("orderId");
		if (null == orderId || orderId.trim().equalsIgnoreCase("")) {
			throw new FailReturnObject(ExceptionResultEnum.QUERYPAY_MIS_DATA_ERROR);
		}
	}

	/**
	 * @return the quePayStatMap
	 */
	public Map<String, String> getQuePayStatMap() {
		return quePayStatMap;
	}

	/**
	 * @param quePayStatMap
	 *            the quePayStatMap to set
	 */
	public void setQuePayStatMap(Map<String, String> quePayStatMap) {
		this.quePayStatMap = quePayStatMap;
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
	 * @return the queryPay
	 */
	public QueryPay getQueryPay() {
		return queryPay;
	}

	/**
	 * @param queryPay
	 *            the queryPay to set
	 */
	public void setQueryPay(QueryPay queryPay) {
		this.queryPay = queryPay;
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

}
