package com.liyang.client.xiaoma;

import com.liyang.client.BaseResult;
import com.liyang.client.IResult;
import com.liyang.client.strategy.IPolicyInfo;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;
import com.liyang.domain.submitproposal.SubmitProposal;

/**
 * @author Administrator
 *
 */
public class ResultSubmitProposal extends BaseResult implements IResult, IPolicyInfo {

	private SubmitProposal submitProposal;
	private String orderId;

	@Override
	public void validate() throws ExceptionTiananParamInvliad {

	}

	/**
	 * @return the submitProposal
	 */
	public SubmitProposal getSubmitProposal() {
		return submitProposal;
	}

	/**
	 * @param submitProposal
	 *            the submitProposal to set
	 */
	public void setSubmitProposal(SubmitProposal submitProposal) {
		this.submitProposal = submitProposal;
	}

	/**
	 * @return the orderId
	 */
	@Override
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
