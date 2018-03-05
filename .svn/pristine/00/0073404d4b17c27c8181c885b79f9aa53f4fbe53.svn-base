package com.liyang.client.xiaoma;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.liyang.Application;
import com.liyang.client.IClient;
import com.liyang.client.IServiceObserve;
import com.liyang.client.strategy.SecurityInfoXiaoma;
import com.liyang.client.xiaoma.mock.MockSuccessResponseSupplierQueryPayState;
import com.liyang.domain.querypayment.QueryPayRepository;

/**
 * @author Administrator
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Application.class)
public class TestQueryPayState {
	private final static Logger logger = LoggerFactory.getLogger(TestQueryPayState.class);
	@Autowired
	QueryPayRepository queryPayRepository;

	@Value("${xmcxapi.apikey}")
	private String xmcxApiKey;

	@Test
	public void testQueryPayState() {
		IClient client = new ClientXiaoma();
		MessageQueryPayState message = null;
		String xiaomaUrl = null;
		IServiceObserve serviceObserve = null;
		MockSuccessResponseSupplierQueryPayState responseSupplier = new MockSuccessResponseSupplierQueryPayState();
		Map<String, String> quePayStatMap = new HashMap<>();
		SecurityInfoXiaoma securityInfo = new SecurityInfoXiaoma(xmcxApiKey);
		ServiceQueryPayState service = new ServiceQueryPayState(securityInfo, client, serviceObserve, responseSupplier,
				queryPayRepository);
		try {
			short state = 0;
			quePayStatMap.put("orderId", "109-20171022120651-26f6c");
			quePayStatMap.put("licenseNumber", "沪 A12345");
			quePayStatMap.put(String.valueOf(state), String.valueOf(10));
			quePayStatMap.put("message", "支付成功");
			message = new MessageQueryPayState(logger, quePayStatMap);
			ResultQueryPayState result = null;
			try {
				result = (ResultQueryPayState) service.callService(message);

			} catch (Exception e) {
				e.printStackTrace();
			}
			assertNotNull(result);
			assertNotNull(result.getParmas());
			System.out.println(result.getParmas());
			System.out.println(result.getRawResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
