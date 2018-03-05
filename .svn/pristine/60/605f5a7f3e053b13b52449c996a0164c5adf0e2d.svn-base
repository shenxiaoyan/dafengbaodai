package com.liyang.client.xiaoma;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.liyang.client.IClient;
import com.liyang.client.IServiceObserve;
import com.liyang.client.strategy.SecurityInfoXiaoma;
import com.liyang.client.xiaoma.mock.MockSuccessResponseSupplierConfirmUnderWriting;

/**
 * @author Administrator
 *
 */
public class TestConfirmUnderWriting {

	@Value("${xmcxapi.apikey}")
	private String xmcxApiKey;

	private final static Logger logger = LoggerFactory.getLogger(TestConfirmUnderWriting.class);

	@Test
	public void testUnderwritingResult() {
		IClient client = new ClientXiaoma();
		MessageConfirmUnderWriting message = null;
		String xiaomaUrl = null;
		IServiceObserve serviceObserve = null;
		MockSuccessResponseSupplierConfirmUnderWriting responseSupplier = new MockSuccessResponseSupplierConfirmUnderWriting();
		SecurityInfoXiaoma securityInfo = new SecurityInfoXiaoma(xmcxApiKey);
		ServiceConfirmUnderWriting service = new ServiceConfirmUnderWriting(securityInfo, client, serviceObserve,
				responseSupplier);
		try {
			String orderId = "200-20171020214016-317505";
			message = new MessageConfirmUnderWriting(logger, orderId);
			ResultConfirmUnderWriting result = null;
			try {
				result = (ResultConfirmUnderWriting) service.callService(message);

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
