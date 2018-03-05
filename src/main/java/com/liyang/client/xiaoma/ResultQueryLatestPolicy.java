package com.liyang.client.xiaoma;

import com.liyang.client.BaseResult;
import com.liyang.client.IResult;
import com.liyang.client.strategy.IPolicyInfo;
import com.liyang.domain.querylatestpolicy.QueryLatestPolicy;
import com.liyang.domain.querylatestpolicy.QueryLatestPolicyResult;

/**
 * @author Administrator
 *
 */
public class ResultQueryLatestPolicy extends BaseResult implements IResult, IPolicyInfo {
	private QueryLatestPolicyResult queryLatestPolicyResult;
	private QueryLatestPolicy queryLatestPolicy;
	private String orderId;

	/**
	 * @return the queryLatestPolicyResult
	 */
	public QueryLatestPolicyResult getQueryLatestPolicyResult() {
		return queryLatestPolicyResult;
	}

	/**
	 * @param queryLatestPolicyResult
	 *            the queryLatestPolicyResult to set
	 */
	public void setQueryLatestPolicyResult(QueryLatestPolicyResult queryLatestPolicyResult) {
		this.queryLatestPolicyResult = queryLatestPolicyResult;
	}

	/**
	 * @return the queryLatestPolicy
	 */
	public QueryLatestPolicy getQueryLatestPolicy() {
		return queryLatestPolicy;
	}

	/**
	 * @param queryLatestPolicy
	 *            the queryLatestPolicy to set
	 */
	public void setQueryLatestPolicy(QueryLatestPolicy queryLatestPolicy) {
		this.queryLatestPolicy = queryLatestPolicy;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.client.strategy.IPolicyInfo#getOrderId()
	 */
	@Override
	public String getOrderId() {
		return this.orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
