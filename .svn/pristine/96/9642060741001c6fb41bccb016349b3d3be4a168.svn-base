package com.liyang.client.tianan;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.liyang.Application;
import com.liyang.client.IClient;
import com.liyang.client.tianan.ClientTianan;
import com.liyang.client.tianan.FacadeService;
import com.liyang.client.tianan.MessageQueryProposal;
import com.liyang.client.tianan.ResultQueryProposal;

/**
 * @author Administrator
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Application.class)
public class TestServiceQueryProposal {
	@Test
	public void testQueryProposal() {
		IClient client = new ClientTianan();
		FacadeService.setClient(client);
		MessageQueryProposal message = null;
		try {
			String tradeNo = "2342e2343r234r5w34t234asd3";
			List<String> proList = new ArrayList<>();
			proList.add("0643130136220150000444");
			message = new MessageQueryProposal("http://180.168.131.15/cpf/", tradeNo, proList);
			assertNotNull(message.getRequestHead());
			System.out.println(message.getRequestHead().getOpenID());
			System.out.println(message.getRequestHead().getToken());
			System.out.println(message.getRequestHead().getSign());
			System.out.println(message.getRequestHead().getTradeDate());
			System.out.println(message.getRequestHead().getTradeNo());
			System.out.println(message.getRequestHead().getTradeType());
			ResultQueryProposal result = null;
			result = (ResultQueryProposal) FacadeService.getInstance().callServiceQueryProposal(message);
		    assertNotNull(result);
		    assertNotNull(result.getResultDTO());
		    System.out.println(result.getResultDTO().getResultCode());
		    System.out.println(result.getResultDTO().getResultMess());
		    assertTrue(result.getResultDTO().isSuccess());
		} catch (Exception e) {
			e.printStackTrace();
		}
         
	}
}
