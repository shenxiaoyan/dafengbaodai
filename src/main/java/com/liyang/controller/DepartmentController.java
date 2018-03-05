package com.liyang.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.liyang.domain.department.Department;
import com.liyang.domain.department.DepartmentForSearch;
import com.liyang.domain.department.DepartmentForUpdate;
import com.liyang.domain.departmenttype.Departmenttype;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.service.DepartmentService;
import com.liyang.service.DepartmenttypeService;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/dafeng/department")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private DepartmenttypeService departmenttypeService;

	@RequestMapping(value="/search", method=RequestMethod.GET)
	public ResponseBody search(DepartmentForSearch search, @PageableDefault(15) Pageable pageable) {
		Page<Department> page = departmentService.search(search, pageable);

		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Department department : page) {
			Map<String, Object> departmentMap = new HashMap<String, Object>();
			departmentMap.put("id", department.getId());
			departmentMap.put("label", department.getLabel());
			departmentMap.put("parent", department.getParent() == null ? null : department.getParent().getLabel());
			departmentMap.put("type", department.getType() == null ? null : department.getType().getLabel());
			departmentMap.put("typeId", department.getType() == null ? null : department.getType().getId());
			departmentMap.put("attactPerson", department.getAttactPerson());
			departmentMap.put("attactPhone", department.getAttactPhone());
			departmentMap.put("address", department.getAddress());
			departmentMap.put("createAt",
					department.getCreatedAt() == null ? null : sdf.format(department.getCreatedAt()));
			departmentMap.put("lastModifiedAt",
					department.getLastModifiedAt() == null ? null : sdf.format(department.getLastModifiedAt()));
			departmentMap.put("stateLabel", department.getState() == null ? null : department.getState().getLabel());
			departmentMap.put("stateCode", department.getState() == null ? null : department.getState().getStateCode());
			returnList.add(departmentMap);
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("content", returnList);
		returnMap.put("size", page.getSize());
		returnMap.put("totalElements", page.getTotalElements());
		returnMap.put("totalPages", page.getTotalPages());
		returnMap.put("number", page.getNumber());
		return new ResponseBody(returnMap);
	}

	@PatchMapping(value = "/changeState/{id}")
	public ResponseBody changeState(@PathVariable Integer id) {
		Department department = departmentService.findOne(id);
		if (department == null) {
			throw new FailReturnObject(ExceptionResultEnum.DEPARTMENTTYPE_ID_ERROR);
		}
		departmentService.updateState(department);
		return new ResponseBody(ExceptionResultEnum.SUCCESS);
	}

	@PostMapping(value = "/save")
	public ResponseBody save(@RequestBody Department department) {
		Departmenttype type = departmenttypeService.findOne(department.getTypeId());
		department.setType(type);
		department.setParent(departmentService.findOne(department.getParentId()));
		departmentService.save(department);
		return new ResponseBody(ExceptionResultEnum.SUCCESS);
	}

	@PatchMapping(value = "/edit/{id}")
	public ResponseBody edit(@PathVariable Integer id, @RequestBody DepartmentForUpdate updateBean) {
		Department department = departmentService.findOne(id);
		if (department == null) {
			throw new FailReturnObject(ExceptionResultEnum.DEPARTMENTTYPE_ID_ERROR);
		}
		departmentService.update(department, updateBean);
		return new ResponseBody(ExceptionResultEnum.SUCCESS);
	}
}
