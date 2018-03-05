package com.liyang.client.tianan;

import com.liyang.client.IMessage;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * @author Administrator
 *
 */
public class MessageGetSign implements IMessage {

//	public static String DEFAULT_TOKEN_FOR_SIGN_TEST = "agkrcl7ofdib6tlttn79";
	public static String DEFAULT_SIGN_FOR_TEST = "DGxnjs7TToYS2v8tfASHzCzBAqcjeW+tzyK2oECl98vBe/WUsYpTfSJ68W9QdkTjWBpKuh0wrdV1fXzAWm8UhrcZnA3LS6XtVnVZKWNeGGTKerzekHBz1jiOGBypAuATELn1nLKy7T4=";
//	
//	public static String DEFAULT_TOKEN_FOR_SIGN_PROD = "xbgrdo8jv47wi7y272t5";
//	public static String DEFAULT_SIGN_FOR_PROD = "/4v4ZA5NjrOmxH9UGGeBp9ZFqWY8ktRsAZd6eUHyBBmwBP1N4w4jcfqZrhWY0xbd7oQfE1Xv+PJwsXLzKtz5UqXJMzpJEh+tz0ZUVKkm/dXY8cGQsuWAHuQA41AngipBERtPjlXnc+M=";
	public static String DEFAULT_SIGN_FOR_PROD = "PgSCGaivXRGvNUQ/NpXZ1zqtCTmbcdRltLXZR7CtcjRF19RmwZMxbKc+Pu4xeWr91AS+1g6KE4rLuTulkuxAHeHEvwEkdq/jBCz34TEu2c5bLbZJkuWeMxmUYPkC/wQklaBzMAH/G1c=";
	
	public MessageGetSign() {
		initDefaultValue();
	}

	public MessageGetSign(String openID, String url, String token) {
		initDefaultValue();
		this.openID = openID;
		this.url = url;
		this.token = token;
	}

	public MessageGetSign(String openID, String url) {
		this.openID = openID;
		this.url = url;
//		this.token = DEFAULT_TOKEN_FOR_SIGN_TEST;
	}

	private String openID;

	/**
	 * 用户标识
	 * 
	 * @return the openID
	 */
	public String getOpenID() {
		return openID;
	}

	/**
	 * 用户标识
	 * 
	 * @param openID
	 *            the openID to set
	 */
	public void setOpenID(String openID) {
		this.openID = openID;
	}

	private String token;

	/**
	 * 令牌
	 * 
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * 令牌
	 * 
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	private String url;

	/**
	 * 网址
	 * 
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 网址
	 * 
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {

	}

	@Override
	public void initDefaultValue() {
		
	}
}
