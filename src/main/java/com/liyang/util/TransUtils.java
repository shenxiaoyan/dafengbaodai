package com.liyang.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 */
public class TransUtils {

	@SuppressWarnings("unchecked")
	public static Map<String, Object> contentMap(String content) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> contentMap = objectMapper.readValue(content, Map.class);
		return contentMap;
	}

	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0))) {
			return s;
		} else {
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
		}
	}

	public static void mapTobean(Object obj, Map<String, Object> map) throws Exception {
		Method[] methods = obj.getClass().getMethods();
		for (Method method : methods) {
			method.setAccessible(true);
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if (method.getName().equals("set" + TransUtils.toUpperCaseFirstOne(entry.getKey()))) {
					method.invoke(obj, entry.getValue().toString());
				}
			}
		}
	}

	/**
	 * Map转换对象
	 * 
	 * @param transMap
	 * @param transObj
	 * @return
	 * @throws Exception
	 */
	public static <T> T mapTransObject(Map<String, Object> transMap, Class<T> transObj) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject(transMap);
		ObjectMapper objectMapper = new ObjectMapper();
		// fix unknow properties
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		T obj = objectMapper.readValue(jsonObject.toString(), transObj);
		return obj;
	}

	public static <T> T mapTransStringObject(Map<String, String> transMap, Class<T> transObj) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject(transMap);
		ObjectMapper objectMapper = new ObjectMapper();
		// fix unknow properties
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		T obj = objectMapper.readValue(jsonObject.toString(), transObj);
		return obj;
	}

	//
	// /**
	// * Set集合转为Json
	// * @param set
	// * @return
	// */
	// public static String set2json(Set<?> set) {
	// StringBuilder json = new StringBuilder();
	// json.append("[");
	// if (set != null && set.size() > 0) {
	// for (Object obj : set) {
	// json.append(object2json(obj));
	// json.append(",");
	// }
	// json.setCharAt(json.length() - 1, ']');
	// } else {
	// json.append("]");
	// }
	// return json.toString();
	// }
	//
	// /**
	// * 对象转换为Json
	// * @param obj
	// * @return
	// */
	// public static String object2json(Object obj) {
	// StringBuilder json = new StringBuilder();
	// if (obj == null) {
	// json.append("\"\"");
	// } else if (obj instanceof String || obj instanceof Integer
	// || obj instanceof Float || obj instanceof Boolean
	// || obj instanceof Short || obj instanceof Double
	// || obj instanceof Long || obj instanceof BigDecimal
	// || obj instanceof BigInteger || obj instanceof Byte) {
	// json.append("\"").append(string2json(obj.toString())).append("\"");
	// } else if (obj instanceof Object[]) {
	// json.append(array2json((Object[]) obj));
	// } else if (obj instanceof List) {
	// json.append(list2json((List<?>) obj));
	// } else if (obj instanceof Map) {
	// json.append(map2json((Map<?, ?>) obj));
	// } else if (obj instanceof Set) {
	// json.append(set2json((Set<?>) obj));
	// } else {
	// json.append(bean2json(obj));
	// }
	// return json.toString();
	// }

	public static Map<String, Object> object2Map(Object obj) throws Exception {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] declaredFields = obj.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			field.setAccessible(true);
			map.put(field.getName(), field.get(obj));
		}
		return map;
	}

}
