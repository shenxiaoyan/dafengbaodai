package com.liyang.client;

import java.io.IOException;

/**
 * 
 * @author Administrator
 *
 */
public interface IClient {
	/**
	 * 获取url
	 * @return the baseUrl
	 */
	String getBaseUrl();

	/**
	 * 设置url
	 * @param baseUrl
	 *            the baseUrl to set
	 */
	void setBaseUrl(String baseUrl);

	/**
	 * @param url
	 * @param jsonString
	 * @return
	 */
	Object postJson(String url, String jsonString);

	/**
	 * @param url
	 * @param jsonString
	 * @param headers
	 * @return
	 */
	Object postJson(String url, String jsonString, Object headers);

	/**
	 * @param url
	 * @param params
	 * @return
	 */
	Object postData(String url, Object params);

	/**
	 * @param url
	 * @param params
	 * @param headers
	 * @return
	 */
	Object postData(String url, Object params, Object headers);

	/**
	 * @param url
	 * @param params
	 * @return
	 */
	Object getData(String url, Object params);

	/**
	 * @param url
	 * @param params
	 * @param headers
	 * @return
	 */
	Object getData(String url, Object params, Object headers);

	/**
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	Object postFile(String url, Object params) throws Exception;

	/**
	 * @param url
	 * @param params
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	Object postFile(String url, Object params, Object headers) throws Exception;
}
