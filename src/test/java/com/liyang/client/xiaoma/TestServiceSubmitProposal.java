package com.liyang.client.xiaoma;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.liyang.Application;
import com.liyang.client.IClient;
import com.liyang.client.IServiceObserve;
import com.liyang.client.strategy.SecurityInfoXiaoma;
import com.liyang.client.tianan.FacadeService;
import com.liyang.client.xiaoma.ClientXiaoma;
import com.liyang.client.xiaoma.MessageSubmitProposal;
import com.liyang.client.xiaoma.ResultSubmitProposal;
import com.liyang.client.xiaoma.mock.MockSuccessResponseSupplierSubmitProposal;
import com.liyang.domain.createenquiry.CreateEnquiryRepository;
import com.liyang.domain.createenquiry.CreateEnquiryStateRepository;
import com.liyang.domain.customer.CustomerRepository;
import com.liyang.domain.offerresult.OfferResultRepository;
import com.liyang.domain.platform.Platform;
import com.liyang.domain.submitproposal.SubmitProposalActRepository;
import com.liyang.domain.submitproposal.SubmitProposalParams;
import com.liyang.domain.submitproposal.SubmitProposalRepository;
import com.liyang.domain.submitproposal.SubmitProposalStateRepository;
import com.liyang.service.SubmitProposalService;

/**
 * @author Administrator
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Application.class)
public class TestServiceSubmitProposal {
	@Autowired
	CustomerRepository customerRepository ;
	@Autowired
	OfferResultRepository offerResultRepository;
	@Autowired
	CreateEnquiryStateRepository createEnquiryStateRepository ;
	@Autowired
	CreateEnquiryRepository createEnquiryRepository ;
	@Autowired
	SubmitProposalRepository submitProposalRepository ;
	@Autowired
	SubmitProposalActRepository submitProposalActRepository ;
	@Autowired
	SubmitProposalStateRepository submitProposalStateRepository;
	@Test
	public void testServiceSubmitProposal() {
		IClient client = new ClientXiaoma();
		MessageSubmitProposal message = null;
		IServiceObserve serviceObserve = null;
		MockSuccessResponseSupplierSubmitProposal responseSupplier = new MockSuccessResponseSupplierSubmitProposal();
		String apikey="12542165";
		SecurityInfoXiaoma infoXiaoma=new SecurityInfoXiaoma(apikey);
		ServiceSubmitProposal service = new ServiceSubmitProposal(infoXiaoma, client, serviceObserve, responseSupplier, customerRepository, offerResultRepository, createEnquiryStateRepository, createEnquiryRepository, submitProposalStateRepository, submitProposalActRepository, submitProposalRepository);
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> subProMap = new HashMap<>();

		String token = "1111";
		try {
			
			Platform platform = null;

			params.put("orderId", "200-20171020214016-317505");
			params.put("insuredName", "刘家福");
			params.put("insuredIdNo", "330402199003033919");
			params.put("insuredPhone", "15251659873");
			params.put("customerName", "徐龙雨");
			params.put("customerIdNo", "330402199003033919");
			params.put("customerPhone", "15251659873");
			HashMap<String, Object> map=new  HashMap<String,Object>();
			map.put("acceptProvince", "310000");
			map.put("acceptProvinceName", "上海");
			map.put("address", "郭守敬路 498 号");
			map.put("contactAddressDetail", "上海 上海市 静安区郭守敬路 498 号");
			params.put("contactAddress", map);
			HashMap<String,Object> hashMap = new HashMap<String,Object>();
			hashMap.put("isInvoice", 3);
			hashMap.put("email", "123@qq.com");
			hashMap.put("phone", "12313");
			hashMap.put("title", "321321");
			
			
			params.put("invoiceInfo", hashMap);
			params.put("ownerIdCard", "330402199003033919");
			params.put("contactName", "徐龙雨 zz");
			params.put("contactPhone", "15251659873");
			// subProMap.put(, value)
			
			subProMap.put("params", params);
			message = new MessageSubmitProposal(platform, subProMap, token);
			ResultSubmitProposal result = null;
			try {
				result = (ResultSubmitProposal) service.callService(message);
				
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
