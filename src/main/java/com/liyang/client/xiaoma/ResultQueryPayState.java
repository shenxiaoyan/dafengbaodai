package com.liyang.client.xiaoma;

import com.liyang.client.BaseResult;
import com.liyang.client.IResult;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;
import com.liyang.domain.querypayment.QueryPay;

/**
 * @author Administrator
 *
 */
public class ResultQueryPayState extends BaseResult implements IResult {

	private QueryPay queryPay;

	@Override
	public void validate() throws ExceptionTiananParamInvliad {

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

}
