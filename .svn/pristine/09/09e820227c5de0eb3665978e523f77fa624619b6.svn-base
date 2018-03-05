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
import com.liyang.client.tianan.MessageUploadAttach;
import com.liyang.client.tianan.ResultUploadAttach;
import com.liyang.client.tianan.dto.AttachmentInfoDto;
import com.liyang.client.tianan.enums.FileTypeEnums;

/**
 * @author Administrator
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes=Application.class)
public class TestServiceUploadAttach {
       @Test
       public void testUploadAttach() {
		IClient client=new ClientTianan();
		FacadeService.setClient(client);
		MessageUploadAttach message=null;
		try {
			String tradeNo="chHUnOrz";
			String proposalNo="11228053900288049995";
			List<AttachmentInfoDto>  attachmentList=new ArrayList<>();
			AttachmentInfoDto dto = new AttachmentInfoDto(20,"holderIdCard", "投保人证件", "330621198812140074");
			attachmentList.add(0, dto);
			message=new MessageUploadAttach("http://180.168.131.15/cpf/", tradeNo, proposalNo, attachmentList);
			assertNotNull(message.getRequestHead());
			System.out.println(message.getRequestHead().getOpenID());
			System.out.println(message.getRequestHead().getToken());
			System.out.println(message.getRequestHead().getSign());
			System.out.println(message.getRequestHead().getTradeDate());
			System.out.println(message.getRequestHead().getTradeNo());
			System.out.println(message.getRequestHead().getTradeType());
			ResultUploadAttach result=null;
			result=FacadeService.getInstance().callServiceUploadAttach(message);
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
