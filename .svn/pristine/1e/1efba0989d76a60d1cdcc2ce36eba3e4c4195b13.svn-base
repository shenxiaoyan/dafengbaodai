package com.liyang.client;

import java.util.Map;

/**
 * 
 * @author Administrator
 *
 */
public interface IResult extends IValidatable {
	/**
	 * 得到原始的响应内容
	 * 
	 * @return
	 */
	Object getRawResponse();

	/**
	 * 设置原始的响应内容
	 * @param rawResponse
	 * @return
	 */
	void setRawResponse(Object rawResponse);

	/**
	 * 设置一些前端需要但是在result里没有的参数
	 * @param key
	 * @param value
	 */
	void putParmas(String key, Object value);

	/**
	 * 得到一些前端需要但是在result里没有的参数
	 * @return
	 */
	Map<String, Object> getParmas();
}
