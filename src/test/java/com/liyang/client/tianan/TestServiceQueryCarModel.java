package com.liyang.client.tianan;

import static org.junit.Assert.*;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.liyang.Application;
import com.liyang.client.IClient;
import com.liyang.client.IResponseSupplier;
import com.liyang.client.IService;
import com.liyang.client.IServiceObserve;
import com.liyang.client.tianan.ClientTianan;
import com.liyang.client.tianan.FacadeService;
import com.liyang.client.tianan.MessageGetSign;
import com.liyang.client.tianan.MessageQueryCarModel;
import com.liyang.client.tianan.ResultGetSign;
import com.liyang.client.tianan.ResultQueryCarModel;
import com.liyang.client.tianan.ServiceGetSign;
import com.liyang.client.tianan.ServiceObserveDbPersistQueryCarModel;
import com.liyang.client.tianan.ServiceQueryCarModel;
import com.liyang.client.tianan.TypeDate;
import com.liyang.client.tianan.exception.ExceptionTiananInitFailed;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;
import com.liyang.client.tianan.mock.MockSuccessResponseSupplierQueryCarModel;
import com.liyang.domain.carModel.CarModelRepository;
import com.liyang.domain.department.Department;
import com.liyang.domain.department.DepartmentRepository;
import com.liyang.domain.menu.Menu;
import com.liyang.domain.menu.MenuRepository;
import com.liyang.domain.menu.MenuStateRepository;
import com.liyang.domain.role.RoleRepository;
import com.liyang.domain.user.UserRepository;
import com.liyang.service.MenuService;
import com.liyang.service.UserService;
import com.liyang.util.CommonUtil;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Application.class)
public class TestServiceQueryCarModel {

	@Autowired
	private CarModelRepository carModelRepository;

	@Test
	public void testGetSignFacade() {
		IClient client = new ClientTianan();
		FacadeService.setClient(client);
		MessageQueryCarModel message = null;
		try {

			String cityCode = "3330100";
			// message.setCityCode(cityCode);
			String brandName = "HG7240";
			// message.setBrandName(brandName);
			String enginNo = "ASDWE23EASDQW";
			// message.setEnginNo(enginNo);
			String enrollDate = "2014-09-05";
			// message.setEnrollDate(new TypeDate(enrollDate));
			String startDate = "2020-04-22";
			// message.setStartDate(new TypeDate(startDate));
			String frameNo = "12342R34R534T5465";
			// message.setFrameNo(frameNo);
			String licenseNo = "浙A1151";
			// message.setLicenseNo(licenseNo);

			// message = new MessageQueryCarModel(cityCode, brandName, enginNo,
			// new TypeDate(enrollDate),
			// new TypeDate(startDate), frameNo, licenseNo);
			String purchaseDate = "2014-08-20";
			// message.setPurchaseDate(new TypeDate(purchaseDate));
			String baseUrl = "http://180.168.131.15/cpr/";
			message = new MessageQueryCarModel.Builder(baseUrl, cityCode, brandName, enginNo, new TypeDate(enrollDate),
					new TypeDate(startDate), frameNo, licenseNo).purchaseDate(new TypeDate(purchaseDate)).build();
			assertNotNull(message.getRequestHead());
			System.out.println(message.getRequestHead().getOpenID());
			System.out.println(message.getRequestHead().getToken());
			System.out.println(message.getRequestHead().getSign());
			System.out.println(message.getRequestHead().getTradeDate());
			System.out.println(message.getRequestHead().getTradeNo());
			System.out.println(message.getRequestHead().getTradeType());
			ResultQueryCarModel result = null;
			IServiceObserve serviceObserve = new ServiceObserveDbPersistQueryCarModel(carModelRepository);
			IResponseSupplier responseSupplier = new MockSuccessResponseSupplierQueryCarModel();
			IService service = new ServiceQueryCarModel(client, serviceObserve, responseSupplier);
			try {
				result = (ResultQueryCarModel) service.callService(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
			assertNotNull(result);
			assertNotNull(result.getResultDTO());
			System.out.println(result.getResultDTO().getResultCode());
			System.out.println(result.getResultDTO().getResultMess());
			System.out.println(JSON.toJSONString(result.getVehicleModelList().get(0)));
			assertTrue(result.getResultDTO().isSuccess());
		} catch (ExceptionTiananInitFailed e) {
			e.printStackTrace();
		} catch (ExceptionTiananParamInvliad e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
