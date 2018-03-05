package com.liyang.util;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.liyang.domain.advertise.Advertise;
import com.liyang.domain.customer.Customer;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.service.XinGeService;

/**
 * 类说明
 * 
 * @author lcj
 * @date 2017年11月1日 新建
 */

public class PushAdvertiseUtil {
	private final static Logger logger = LoggerFactory.getLogger(PushAdvertiseUtil.class);
	
	/**
	 * 承保接口推送至指定平台
	 * @param xinGeService
	 * @param customer
	 * @param handerDataMap
	 * @param title
	 * @return
	 */
	public static boolean pushAdvertToAppPlatform(XinGeService xinGeService, Customer customer,
			Map<String, Object> handerDataMap, String title) {
		if (customer == null) {
			// 其他平台
			logger.info("推送至其他平台");
			// HttpUtil.postBody(platform.getPlatformOfferURL(),JSONObject.fromObject(offResMap).toString());
		} else if ("ios".equals(customer.getClient())) {
			// ios来的用户
			logger.info("推送至IOS平台");
			xinGeService.pushIOS(customer.getPushToken(), handerDataMap, title);
		} else if ("android".equals(customer.getClient())) {
			// 安卓来的用户
			logger.info("推送至安卓平台");
			xinGeService.pushAndroid(customer.getPushToken(), handerDataMap, title, "结果返回");
		} else {
			throw new FailReturnObject(ExceptionResultEnum.ADVERUSE_DATA_PUSH_ERROR);
		}
		return true;

	}

}
