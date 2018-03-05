package com.liyang.service;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.liyang.domain.customer.Customer;
import com.liyang.domain.customer.CustomerRepository;
import com.liyang.util.XingeUtils;
import com.tencent.xinge.Message;
import com.tencent.xinge.MessageIOS;
import com.tencent.xinge.XingeApp;

/**
 * @author Administrator
 *
 */
@Service
public class XinGeService {

	private static final Long IOS_ACCESS_ID = 2200266045L;

	private static final String IOS_SECRET_KEY = "c08fe5711f4c844ed8ecaacb11403b64";

	private static final Long ANDROID_ACCESS_ID = 2100266044L;

	private static final String ANDROID_SECRET_KEY = "11853d122bd9ae382342f84d3e4166cf";

	@Autowired
	private CustomerRepository customerRepository;
	
	@Value("${ios.push.env}")
	private String iosEnv;

	public void pushIOS(String pushToken, Map<String, Object> dataMap, String title) {
		XingeApp xinge = new XingeApp(IOS_ACCESS_ID, IOS_SECRET_KEY);
		MessageIOS messageIOS = new MessageIOS();
		messageIOS.setType(MessageIOS.TYPE_APNS_NOTIFICATION);
		messageIOS.setAlert(title);
		messageIOS.setCustom(dataMap);
		messageIOS.setBadge(1);
		messageIOS.setSound("beep.wav");
		for (int i = 0; i < 6; i++) {
			JSONObject jsonObject = XingeUtils.demoPushSingleDeviceNotificationIOS(messageIOS, pushToken, xinge, iosEnv);
			Integer code = jsonObject.getInt("ret_code");
			if (code.equals(0)) {
				System.out.println("ios推送成功" + jsonObject);
				break;
			} else {
				System.out.println("ios推送失败,第" + i + "失败原因:" + jsonObject + "::::重新推送，等待20秒重新推送");
				try {
					Thread.sleep(20000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void pushAndroid(String pushToken, Map<String, Object> dataMap, String title, String content) {
		XingeApp xinge = new XingeApp(ANDROID_ACCESS_ID, ANDROID_SECRET_KEY);
		Message message = new Message();
		message.setType(Message.TYPE_NOTIFICATION);
		message.setTitle(title);
		message.setContent(content);
		message.setCustom(dataMap);
		for (int i = 0; i < 5; i++) {
			JSONObject jsonObject = XingeUtils.demoPushSingleDeviceNotification(message, pushToken, xinge);
			Integer code = jsonObject.getInt("ret_code");
			if (code.equals(0)) {
				System.out.println("安卓推送成功" + jsonObject);
				break;
			} else {
				System.out.println("安卓推送失败,第" + i + "失败原因:" + jsonObject + "::::等待20秒重新推送");
				try {
					Thread.sleep(20000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void pushAdvertiseAllAndriodAndIOS(Map<String, Object> dataMap) {
		List<Customer> list = customerRepository.query2();
		for (Customer c : list) {
			String client = c.getClient();
			if ("android".equals(client)) {
				XingeApp xinge = new XingeApp(ANDROID_ACCESS_ID, ANDROID_SECRET_KEY);
				Message message = new Message();
				message.setType(Message.TYPE_NOTIFICATION);
				message.setTitle("大蜂保险");
				message.setContent("系统公告");
				message.setCustom(dataMap);
				if (c.getPushToken() != null) {
					JSONObject jsonObject = XingeUtils.demoPushSingleDeviceNotification(message, c.getPushToken(),
							xinge);
					Integer code = jsonObject.getInt("ret_code");
					if (code.equals(0)) {
						System.out.println("公告推送成功" + jsonObject);
					} else {
						System.out.println("公告推送失败,失败原因:" + jsonObject + "顾客id:" + c.getId());
					}
				}
			} else if ("ios".equals(client)) {
				XingeApp xinge = new XingeApp(IOS_ACCESS_ID, IOS_SECRET_KEY);
				MessageIOS messageIOS = new MessageIOS();
				messageIOS.setType(MessageIOS.TYPE_APNS_NOTIFICATION);
				messageIOS.setAlert("大蜂保险");
				messageIOS.setCustom(dataMap);
				messageIOS.setBadge(1);
				messageIOS.setSound("beep.wav");
				if (c.getPushToken() != null) {
					JSONObject jsonObject = XingeUtils.demoPushSingleDeviceNotificationIOS(messageIOS, c.getPushToken(),
							xinge, iosEnv);
					Integer code = jsonObject.getInt("ret_code");
					if (code.equals(0)) {
						System.out.println("公告推送成功" + jsonObject);
					} else {
						System.out.println("公告推送失败,失败原因:" + jsonObject + "顾客id:" + c.getId());
					}
				}
			} else {
			}

		}
	}

}
