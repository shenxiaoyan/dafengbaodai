package com.liyang.client.tianan;

import java.util.List;

import org.springframework.util.StringUtils;

import com.liyang.client.BaseRequestHeader;
import com.liyang.client.IMessage;
import com.liyang.client.tianan.exception.ExceptionTiananInitFailed;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * @author Administrator
 *
 */
public class MessageQueryProposal implements IMessage {
	// public MessageQueryProposal() throws ExceptionTiananInitFailed {
	// BaseRequestHeader requestHeader =
	// FacadeService.getInstance().getDefaultRequestHeader();
	// setRequestHead(requestHeader);
	// initDefaultValue();
	// }

	private final List<String> proList;

	private final BaseRequestHeader requestHead;

	/**
	 * 投保单列表
	 * 
	 * @return the proList
	 */
	public List<String> getProList() {
		return proList;
	}
	

	/**
	 * 请求头信息
	 * 
	 * @return the requestHead
	 */
	public BaseRequestHeader getRequestHead() {
		return requestHead;
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {

		if (this.proList.size() == 0) {
			throw new ExceptionTiananParamInvliad("投保单列表不能为空");
		}
	}

	@Override
	public void initDefaultValue() {

	}

	public MessageQueryProposal(String baseUrl, String tradeNo, List<String> proList)
			throws ExceptionTiananInitFailed, ExceptionTiananParamInvliad {
		if (StringUtils.isEmpty(tradeNo)) {
			throw new ExceptionTiananInitFailed("tradeNo为空");
		}
		BaseRequestHeader requestHeader = FacadeService.getInstance(baseUrl).getDefaultRequestHeader(tradeNo);
		this.requestHead = requestHeader;
		initDefaultValue();
		this.proList = proList;
		validate();
	}

}
