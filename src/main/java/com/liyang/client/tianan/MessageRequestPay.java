package com.liyang.client.tianan;

import org.springframework.util.StringUtils;

import com.liyang.client.BaseRequestHeader;
import com.liyang.client.IMessage;
import com.liyang.client.tianan.exception.ExceptionTiananInitFailed;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * @author Administrator
 *
 */
public class MessageRequestPay implements IMessage {
	public MessageRequestPay(String baseUrl, String tradeNo) throws ExceptionTiananInitFailed {
		if (StringUtils.isEmpty(tradeNo)) {
			throw new ExceptionTiananInitFailed("tradeNo为空");
		}
		BaseRequestHeader requestHeader = FacadeService.getInstance(baseUrl).getDefaultRequestHeader(tradeNo);
		this.requestHead = requestHeader;
		initDefaultValue();
	}

	private final BaseRequestHeader requestHead;

	/**
	 * @return the requestHead
	 */
	public BaseRequestHeader getRequestHead() {
		return requestHead;
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {

	}

	@Override
	public void initDefaultValue() {

	}

}
