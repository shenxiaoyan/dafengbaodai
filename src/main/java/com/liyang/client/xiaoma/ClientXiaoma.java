package com.liyang.client.xiaoma;

import com.liyang.client.BaseClient;
import com.liyang.client.IClient;

/**
 * @author Administrator
 *
 */
public class ClientXiaoma extends BaseClient implements IClient {

	public ClientXiaoma(String baseUrl) {
		super(baseUrl);
	}

	public ClientXiaoma() {
		super("http://182.92.24.162:8088/");
	}

}
