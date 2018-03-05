package com.liyang.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liyang.domain.webadvertisetype.WebAdvertiseType;
import com.liyang.helper.ResponseBody;
import com.liyang.service.RoleService;
import com.liyang.service.WebAdvertiseTypeService;

@RestController
@RequestMapping("/dafeng/webAdvType")
public class WebAdvertiseTypeController {

	@Autowired
	WebAdvertiseTypeService webAdvertiseTypeService;
	@Autowired
	RoleService roleService;

	/**
	 * 获取列表
	 * 
	 * @param stateCode
	 * @return
	 */
	@RequestMapping("/all")
	public ResponseBody getAllType(@RequestParam(value = "stateCode", required = false) String stateCode) {
		List<Map<String, Object>> returnList = webAdvertiseTypeService.getAll(stateCode);
		return new ResponseBody(returnList);
	}

	/**
	 * 更新
	 * 
	 * @param id
	 * @param label
	 * @param roleIds
	 * @return
	 */
	@RequestMapping("/update")
	public ResponseBody updateType(@RequestParam("id") Integer id, @RequestParam("label") String label,
			@RequestParam("roleIds") Integer[] roleIds) {
		String msg = webAdvertiseTypeService.update(id, label, roleIds);
		return new ResponseBody(msg);
	}

	@RequestMapping("/update2")
	public ResponseBody updateType(@RequestBody Map<String, Object> webAdvertiseTypeMap) {
		String msg = webAdvertiseTypeService.update2(webAdvertiseTypeMap);
		return new ResponseBody(msg);
	}

	/**
	 * 新增
	 * 
	 * @param typeCode
	 * @param label
	 * @param roleIds
	 * @return
	 */
	@RequestMapping("/add")
	public ResponseBody add(@RequestParam("typeCode") String typeCode, @RequestParam("label") String label,
			@RequestParam("roleIds") Integer[] roleIds) {
		webAdvertiseTypeService.save(typeCode, label, roleIds);
		return new ResponseBody("新增成功");
	}

	/**
	 * 启用、禁用状态切换
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/reverseState")
	public ResponseBody reverseState(@RequestParam(value = "id", required = true) Integer id) {
		String msg = webAdvertiseTypeService.reverseState(id);
		return new ResponseBody(msg);
	}

	/**
	 * 根据Id获取通知类
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/{id}")
	public ResponseBody getById(@PathVariable Integer id) {
		Map<String, Object> returnMap = webAdvertiseTypeService.findById(id);
		return new ResponseBody(returnMap);
	}

}
