package com.liyang.client;

import com.baidu.aip.ocr.AipOcr;

/**
 * 单例百度客户端
 * @author Administrator
 *
 */
public class AipOcrSingleton {
	
	public static final String APP_ID = "10861977";
	public static final String API_KEY = "svNigGvjXu6U9OeXn7V9QHCr";
	public static final String SECRET_KEY = "xfM6uTxMxsrIwmXCyVQ1kMLO4k5Qo38Q";
	
	private AipOcr client;
	
	public static volatile AipOcrSingleton instance = null;
	
	
	private AipOcrSingleton(AipOcr client) {
		this.client = client;
	}
	
	public static AipOcrSingleton getInstance() {
		if (null == instance) {
			synchronized(AipOcrSingleton.class) {
				if (null == instance) {
					instance = new AipOcrSingleton(new AipOcr(APP_ID, API_KEY, SECRET_KEY));
				}
			}
		}
		return instance;
	}

	public AipOcr getClient() {
		return client;
	}

	public void setClient(AipOcr client) {
		this.client = client;
	}
	
	
}
