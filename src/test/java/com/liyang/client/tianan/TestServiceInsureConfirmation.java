package com.liyang.client.tianan;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.validation.constraints.AssertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.liyang.Application;
import com.liyang.client.IClient;
import com.liyang.client.tianan.ClientTianan;
import com.liyang.client.tianan.FacadeService;
import com.liyang.client.tianan.MessageInsureConfirmation;
import com.liyang.client.tianan.ResultInsureConfirmation;
import com.liyang.client.tianan.TypeDate;
import com.liyang.client.tianan.dto.ApplyInfoDto;
import com.liyang.client.tianan.dto.ApplyInfoDto.Builder;
import com.liyang.client.tianan.dto.CarOwerDto;
import com.liyang.client.tianan.dto.DeliveryDto;
import com.liyang.client.tianan.dto.InsureInfoDto;

/**
 * @author Administrator
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Application.class)
public class TestServiceInsureConfirmation {
	@Test
public void  testServiceInsureConfirmation() {
		IClient client = new ClientTianan();
		FacadeService.setClient(client);
		MessageInsureConfirmation message=null;
		try {
			String rateMark="2";
			String carPremiumCaculateNo="156154815625";
            String insuredType="1";
            String applybirthDate="1987-02-12";
            String applyName="杨薇娜";
            String applyidentifyType="01";
            String applyidentifyNumber="360281199211115054";
            String applysex="1";
            String applymobilePhone="15984756234";
            String applyAddress="广东省惠州市";
			ApplyInfoDto applyInfoDto=new ApplyInfoDto.Builder(applyName, applyidentifyType, applyidentifyNumber).insuredType(insuredType).applybirthDate(new TypeDate(applybirthDate)).applysex(applysex).applymobilePhone(applymobilePhone).applyAddress(applyAddress).build();

            String insuredbirthDate="1987-02-12";
            String insuredName="杨薇娜";
            String insuredidentifyType="01";
            String insuredidentifyNumber="360281199211115054";
            String insuredsex="1";
            String insuredmobilePhone="15984756234";
            String insuredAddress="广东省惠州市";
            InsureInfoDto insureInfoDto=new InsureInfoDto.Builder(new TypeDate(insuredbirthDate),insuredName, insuredidentifyType, insuredidentifyNumber, insuredsex, insuredmobilePhone, insuredAddress).build();
            
            String carOwnerIdentifyType="01";
            String carOwnerIdentifyNumber="360281199211115054";
            CarOwerDto carOwerDto=new CarOwerDto(carOwnerIdentifyType, carOwnerIdentifyNumber);
            
            String tradeNo =  "chHUnOrz";
            String acceptName="杨薇娜";
            String acceptTelephone="15984756234";
            String acceptProvince="440000";
            String acceptCity="3440200";
            String acceptTown="140501";
            String acceptAddress="广东省惠州市";
            String licenceNo="粤F41341";
            String deliveryType="DT0002";
            String appointmentTime="2017-11-27";
            DeliveryDto deliveryDto=new DeliveryDto.Builder(acceptName, acceptTelephone, acceptProvince, acceptCity, acceptTown, acceptAddress, licenceNo, deliveryType,new TypeDate(appointmentTime)).build();
            message=new MessageInsureConfirmation.Builder("http://180.168.131.15/cpf/",tradeNo, rateMark, carPremiumCaculateNo, applyInfoDto, insureInfoDto, carOwerDto, deliveryDto).build();
            assertNotNull(message.getRequestHead());
            System.out.println(message.getRequestHead().getOpenID());
            System.out.println(message.getRequestHead().getToken());
            System.out.println(message.getRequestHead().getTradeDate());
			System.out.println(message.getRequestHead().getTradeNo());
			System.out.println(message.getRequestHead().getTradeType());
			ResultInsureConfirmation result=null;
			try {
				result=(ResultInsureConfirmation)FacadeService.getInstance().callServiceInsureConfirmation(message);
			} catch (Exception e) {
				e.printStackTrace();			
				}
			assertNotNull(result);
			assertNotNull(result.getResultDTO());
			System.out.println(result.getResultDTO());
			System.out.println(result.getDealMassage());
			assertFalse(result.getResultDTO().isSuccess());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
}
}
