package com.liyang.controller;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.LogForSearch;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.menu.Menu;
import com.liyang.domain.menu.MenuRepository;
import com.liyang.domain.user.User;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.LogService;
import com.liyang.util.CommonUtil;
import com.liyang.util.DateUtil;
import com.liyang.util.FailReturnObject;
import com.liyang.util.SpringUtil;

/**
 * 操作日志控制器
 * 
 * @author huanghengkun
 * @create 2017年12月22日
 */
@RestController
@RequestMapping("/dafeng/log")
public class LogController {

	@Autowired
	LogService logService;
	@Autowired
	MenuRepository menuRepository;

	@GetMapping("menuList")
	public ResponseBody menuList() {
		User currentUser = CommonUtil.getPrincipal();
		if (currentUser == null || currentUser.getRole() == null) {
			throw new FailReturnObject(ExceptionResultEnum.LOG_USER_ERROR);
		}
		List<Menu> menus = menuRepository.findByVisibleRolesIn(currentUser.getRole());
		List<Map<String, Object>> menuList = menus.stream().filter(e -> !StringUtils.isEmpty(e.getEntityName()))
				.map(LogController::createParmatersMap).collect(Collectors.toList());
		return new ResponseBody(menuList);
	}

	private static Map<String, Object> createParmatersMap(Menu menu) {
		Map<String, Object> map = new HashMap<>();
		map.put("label", menu.getLabel());
		map.put("entityName", menu.getEntityName());
		return map;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseBody search(LogForSearch searchBean,
			@PageableDefault(size = 15, sort = "createdAt", direction = Direction.DESC) Pageable page) {

		// entityName有效性判断
		if (StringUtils.isEmpty(searchBean.getEntityName())) {
			throw new FailReturnObject(ExceptionResultEnum.LOG_ENTITYNAME_EMPTY);
		}
		Class clazz = null;
		try {
			clazz = Class.forName("com.liyang.domain." + searchBean.getEntityName() + "."
					+ CommonUtil.toUpperCaseFirstOne(searchBean.getEntityName()));
		} catch (ClassNotFoundException e) {
			throw new FailReturnObject(ExceptionResultEnum.LOG_ENTITYNAME_ERROR);
		}
		Object serviceObject = null;
		try {
			serviceObject = SpringUtil.getBean(searchBean.getEntityName() + "Service");
		} catch (BeansException e) {
			throw new FailReturnObject(ExceptionResultEnum.LOG_ENTITYNAME_ERROR);
		}
		if (!(serviceObject instanceof AbstractAuditorService)) {
			throw new FailReturnObject(ExceptionResultEnum.LOG_ENTITYNAME_ERROR);
		}
		AbstractAuditorService service = (AbstractAuditorService) serviceObject;
		LogRepository logRepository = service.getLogRepository();
		Page<AbstractAuditorLog> pages = logService.multiComditionSearch(searchBean, page, logRepository);

		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> resultList = new ArrayList<>();
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// forEach的时候clazz需要final，故声明一个临时变量claz
		final Class claz = clazz;
		pages.getContent().forEach(t -> {
			Map<String, Object> map = new HashMap<>();
			map.put("id", t.getId());
			map.put("createdAt", sdf.format(t.getCreatedAt()));
			map.put("createdBy", t.getCreatedBy().getNickname());
			map.put("label", t.getLabel());
			map.put("entityId", t.getEntity().getId());
			String entityName = t.getEntity().getLabel();
			try {
				Method m = t.getEntity().getClass().getMethod("getName");
				entityName = (String) m.invoke(t.getEntity());
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				// 不处理
			}
			map.put("entityName", entityName);
			map.put("difference", parseDifference(t.getDifference(), claz));
			resultList.add(map);
		});
		result.put("content", resultList);
		result.put("size", pages.getSize());
		result.put("totalElements", pages.getTotalElements());
		result.put("totalPages", pages.getTotalPages());
		result.put("number", pages.getNumber());
		return new ResponseBody(result);

	}

	private JSONArray parseDifference(String differenceString, @SuppressWarnings("rawtypes") Class clazz) {
		JSONArray difference = new JSONArray();
		JSONObject json = JSON.parseObject(differenceString);
		json.entrySet().stream().forEach(e -> {
			String column = convertColumn((String) e.getKey(), clazz);
			String oldValue = getValue(((JSONObject) e.getValue()).get("old"));
			String newValue = getValue(((JSONObject) e.getValue()).get("new"));
			JSONObject differenceValue = new JSONObject();
			differenceValue.put("column", column);
			differenceValue.put("old", oldValue);
			differenceValue.put("new", newValue);
			difference.add(differenceValue);
		});
		return difference;
	}

	private String getValue(Object value) {
		String result = "";
		if (value == null) {
			result = "--";
		} else {
			if (value instanceof String) {
				result = (String) value;
				try {
					result = DateUtil.formatEnDateString(result);
				} catch (ParseException e) {
					// 不处理
				}

			} else {
				JSONObject jsonObject = (JSONObject) value;
				// 优先使用label，否则使用id
				if (jsonObject.containsKey("label")) {
					result = jsonObject.getString("label");
				} else if (jsonObject.containsKey("id")) {
					result = jsonObject.getString("id");
				}
			}
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	private String convertColumn(String fieldName, Class clazz) {
		Field field = null;
		// 递归查询父类里是否有对应的字段
		for (Class temp = clazz; temp != Object.class; temp = temp.getSuperclass()) {
			try {
				field = temp.getDeclaredField(fieldName);
				break;
			} catch (NoSuchFieldException e) {
			} catch (SecurityException e) {
			}
		}
		if (field != null) {
			Info info = (Info) field.getAnnotation(Info.class);
			if (info != null && !StringUtils.isEmpty(info.label())) {
				return info.label();
			}
		}
		return fieldName;
	}

}
