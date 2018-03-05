package com.liyang.client;

import java.util.HashMap;
import java.util.Map;

import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * 
 * @author Administrator
 *
 */
public class BaseResult implements IResult {
	protected Object rawResponse;
	protected Map<String, Object> params = new HashMap<>();

	@Override
	public void validate() throws ExceptionTiananParamInvliad {

	}

	@Override
	public Object getRawResponse() {
		return this.rawResponse;
	}

	@Override
	public void setRawResponse(Object rawResponse) {
		this.rawResponse = rawResponse;
	}

	@Override
	public void putParmas(String key, Object value) {
		params.put(key, value);
	}

	@Override
	public Map<String, Object> getParmas() {
		return params;
	}

}
