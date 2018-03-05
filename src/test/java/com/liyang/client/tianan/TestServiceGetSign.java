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

import com.liyang.Application;
import com.liyang.client.BaseRequestHeader;
import com.liyang.client.IClient;
import com.liyang.client.tianan.ClientTianan;
import com.liyang.client.tianan.FacadeService;
import com.liyang.client.tianan.MessageGetSign;
import com.liyang.client.tianan.ResultGetSign;
import com.liyang.client.tianan.ServiceGetSign;
import com.liyang.client.tianan.exception.ExceptionTiananInitFailed;
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
public class TestServiceGetSign {

	@Test
	public void testGetSign() {
		IClient client = new ClientTianan();
		ServiceGetSign service = new ServiceGetSign(client);
		assertNotNull(service);
		MessageGetSign message = new MessageGetSign();
		ResultGetSign result = null;
		try {
			result = (ResultGetSign) service.callService(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(result);
		System.out.println(client.getBaseUrl());
		System.out.println(result.getSign());
	}

	@Test
	public void testGetSignFacade() {
		try {
			BaseRequestHeader requestHeader = FacadeService.getInstance().getDefaultRequestHeader("");
			assertNotNull(requestHeader);
			System.out.println(requestHeader.getOpenID());
			System.out.println(requestHeader.getSign());
			System.out.println(requestHeader.getToken());
			System.out.println(requestHeader.getTradeDate());
			System.out.println(requestHeader.getTradeNo());
			System.out.println(requestHeader.getTradeType());
		} catch (ExceptionTiananInitFailed e) {
			e.printStackTrace();
		}
	}

}
