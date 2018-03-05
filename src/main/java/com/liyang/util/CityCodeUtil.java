package com.liyang.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 城市代码工具类.@Component注解使spring初始化时完成该工具类的初始化工作,而不是初次调用时初始化
 * 
 * @author huanghengkun
 * @create 2017年12月26日
 */
@Component
public class CityCodeUtil {

	private static final String PATH = "/cityCode.json";

	public static final JSONArray CITY_INFO = JSON.parseArray(readFile(PATH));

	private final static Logger logger = LoggerFactory.getLogger(CityCodeUtil.class);

	public static String getCityName(String cityCode) {
		if (CITY_INFO == null) {
			logger.info("cityInfo为空");
			return "";
		}
		Iterator<Object> it = CITY_INFO.iterator();
		while (it.hasNext()) {
			JSONObject city = (JSONObject) it.next();
			if (city.getString("code").equals(cityCode)) {
				return city.getString("name");
			}
		}
		return "";
	}

	private static String readFile(String path) {
		String result = null;
		InputStream is = CityCodeUtil.class.getResourceAsStream(PATH);
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[1024];
		int c = 0;
		try {
			while ((c = is.read(buff)) > 0) {
				swapStream.write(buff, 0, c);
			}
			byte[] bytes = swapStream.toByteArray();
			result = new String(bytes, "utf-8");
		} catch (IOException e) {
			logger.error("cityCode.json路径错误:" + Log4jUtil.formatException(e));
		}
		return result;
	}

}
