package com.liyang.client.tianan;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.liyang.Application;
import com.liyang.client.IClient;
import com.liyang.client.tianan.ClientTianan;
import com.liyang.client.tianan.FacadeService;
import com.liyang.client.tianan.MessagePremiumAccurate;
import com.liyang.client.tianan.ResultPremiumAccurate;
import com.liyang.client.tianan.TypeDate;
import com.liyang.client.tianan.dto.CarInfoDto;
import com.liyang.client.tianan.dto.CombosDataDto;
import com.liyang.client.tianan.dto.ExtendInfoDTO;
import com.liyang.client.tianan.dto.ItemKindDataDto;
import com.liyang.client.tianan.dto.VehicleJingyouDto;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.customer.CustomerRepository;
import com.liyang.domain.platform.Platform;
import com.liyang.domain.platform.PlatformRepository;

/**
 * @author Administrator
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Application.class)
public class TestServicePremiumAccurate {

	@Autowired
	private PlatformRepository platformRepository;
	@Autowired
	private CustomerRepository customerRepository;

	@Test
	public void testPremiumAccurateFacade() {
		IClient client = new ClientTianan();
		FacadeService.setClient(client);
		MessagePremiumAccurate message = null;
		try {

			String quotationType = "3";
			String cityCode = "3440200";
			String startDate = "2017-11-22";
			String endDate = "2018-11-21";
			// String startDateBus = "2017-11-22";
			// String endDateBus = "2018-11-21";

			String tradeNo = "chHUnOrz";
			String licenseNo = "粤F41341";
			String engineNo = "4123412341234123";
			String frameNo = "L12345678G4314132";
			String brandName = "红旗CA7184MT4轿车";
			String rbCode = "HQD1184YQJ";
			String enrollDate = "2017-11-17";
			String transferFlag = "0";
			String ecdemicFlag = "0";
			Integer seatCount = 5;
			String carOwnerIdentifyNumber = "360281199211115054";
			Double actualValue = 123800.00;
			Double purchasePrice = 123800.00;
			String importFlag = "B";
			Double wholeWeight = 1450.00;

			VehicleJingyouDto vehicleJingyouDto = new VehicleJingyouDto();
			String vehicleCode = "HQD1184YQJ";
			vehicleJingyouDto.setVehicleCode(vehicleCode);
			String vehicleName = "红旗CA7184MT4轿车";
			vehicleJingyouDto.setVehicleName(vehicleName);
			Double price = 13800.00;
			vehicleJingyouDto.setPrice(price);
			String familyName = "奔腾";
			vehicleJingyouDto.setFamilyName(familyName);
			String priceType = "01";
			vehicleJingyouDto.setPriceType(priceType);

			CarInfoDto carInfoDto = new CarInfoDto.Builder(engineNo, licenseNo, frameNo, brandName, rbCode,
					new TypeDate(enrollDate), ecdemicFlag, seatCount, carOwnerIdentifyNumber, actualValue,
					purchasePrice, importFlag, vehicleJingyouDto).transferFlag(transferFlag).wholeWeight(wholeWeight)
							.build();
			List<CombosDataDto> combosList = new ArrayList<>();
			CombosDataDto combosDataDto1 = new CombosDataDto();
			String comboNo = "TIANANCAR01";
			combosDataDto1.setComboNo(comboNo);

			List<ItemKindDataDto> itemKindList = new ArrayList<>();
			ItemKindDataDto temKindDataDto1 = new ItemKindDataDto();
			String kindCode = "63";
			temKindDataDto1.setKindCode(kindCode);
			String kindName = "机动车损失险";
			temKindDataDto1.setKindName(kindName);
			Double amount = 123800.00;
			temKindDataDto1.setAmount(amount);
			String deductableFlag = "1";
			temKindDataDto1.setDeductableFlag(deductableFlag);

			itemKindList.add(temKindDataDto1);
			combosList.add(combosDataDto1);

			List<ExtendInfoDTO> extendInfoList = new ArrayList<>();
			ExtendInfoDTO extendInfoDTO = new ExtendInfoDTO();
			extendInfoList.add(extendInfoDTO);

			String token = "5ff7be41-2167-43bf-af29-3600996717b0";
			String mobilePhone = "18888888888";
			Customer customer = customerRepository.findByToken(token);
			Platform platform = platformRepository.findByApplicationId("ios");

			message = new MessagePremiumAccurate.Builder("http://180.168.131.15/cpf/", tradeNo, quotationType, cityCode, new TypeDate(startDate),
					new TypeDate(endDate), carInfoDto, combosList, extendInfoList, mobilePhone, customer, platform)
							.build();

			assertNotNull(message.getRequestHead());
			System.out.println(message.getRequestHead().getOpenID());
			System.out.println(message.getRequestHead().getToken());
			System.out.println(message.getRequestHead().getSign());
			System.out.println(message.getRequestHead().getTradeDate());
			System.out.println(message.getRequestHead().getTradeNo());
			System.out.println(message.getRequestHead().getTradeType());

			ResultPremiumAccurate result = null;
			try {
				result = (ResultPremiumAccurate) FacadeService.getInstance().callServicePremiumAccurate(message);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			assertNotNull(result);
			assertNotNull(result.getResultDTO());
			System.out.println(result.getResultDTO().getResultCode());
			System.out.println(result.getResultDTO().getResultMess());
			assertTrue(result.getResultDTO().isSuccess());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
