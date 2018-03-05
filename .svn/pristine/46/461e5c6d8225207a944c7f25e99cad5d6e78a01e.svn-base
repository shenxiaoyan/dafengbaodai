package com.liyang.client.xiaoma;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.liyang.client.IClient;
import com.liyang.client.IServiceObserve;
import com.liyang.client.strategy.SecurityInfoXiaoma;
import com.liyang.client.xiaoma.mock.MockSuccessResponseSupplierQueryLatesPolicy;
import com.liyang.domain.platform.Platform;

/**
 * @author Administrator
 *
 */
public class TestQueryLatestPolicy {

	@Value("${xmcxapi.apikey}")
	private String xmcxApiKey;

	private final static Logger logger = LoggerFactory.getLogger(TestQueryLatestPolicy.class);

	@Test
	public void test() {
		IClient client = new ClientXiaoma();
		MessageQueryLatestPolicy message = null;
		String xiaomaUrl = null;
		IServiceObserve serviceObserve = null;

		MockSuccessResponseSupplierQueryLatesPolicy responseSupplier = new MockSuccessResponseSupplierQueryLatesPolicy();
		SecurityInfoXiaoma securityInfo = new SecurityInfoXiaoma(xmcxApiKey);
		ServiceQueryLatestPolicy service = new ServiceQueryLatestPolicy(securityInfo, client, serviceObserve,
				responseSupplier);
		try {
			Platform platform = new Platform();
			Map<String, String> queLatPolMap = new HashMap<>();
			queLatPolMap.put("licenseNumber", "浙AZS871");
			queLatPolMap.put("ownerName", "汪伟坤");
			queLatPolMap.put("carTypeCode", "02");
			queLatPolMap.put("cityCode", "330100");
			message = new MessageQueryLatestPolicy(logger, platform, queLatPolMap);
			ResultQueryLatestPolicy result = null;
			try {
				result = (ResultQueryLatestPolicy) service.callService(message);

			} catch (Exception e) {
				e.printStackTrace();
			}
			assertNotNull(result);
			// assertNotNull(null);
			assertNotNull(result.getParmas());
			// System.out.println(result.getParmas());
			System.out.println(result.getRawResponse());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
