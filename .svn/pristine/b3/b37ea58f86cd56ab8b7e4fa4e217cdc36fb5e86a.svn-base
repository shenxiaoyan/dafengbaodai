package com.liyang.util;

import org.springframework.util.StringUtils;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 */
public class JsonParserUtils {
	/**
	 * 为前端服务，将数据库的字符串以Json对象返回，为的是前端解析（由于转译字符的存在，游览器解析时会带有转译字符）
	 * @param handerString
	 * @return
	 */
	public static JSONObject jsonTrans(String handerString) {
		if (StringUtils.isEmpty(handerString)) {
			return null;
		} else {
			return JSONObject.fromObject(handerString.replaceAll(":null", ":\"\"").replaceAll(": null", ":\"\""));
		}
	}

	public static boolean isGoodJson(String json) {
		if (StringUtils.isEmpty(json)) {
			return false;
		}
		try {
			new JsonParser().parse(json);
			return true;
		} catch (JsonParseException e) {
			return false;
		}
	}
}
