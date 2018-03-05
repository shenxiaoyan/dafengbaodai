package com.liyang.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import com.liyang.domain.departmenttype.Departmenttype;
import com.liyang.domain.departmenttype.DepartmenttypeForSearch;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.service.DepartmenttypeService;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/dafeng/departmenttype")
public class DepartmenttypeController {

	@Autowired
	private DepartmenttypeService departmenttypeService;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseBody search(DepartmenttypeForSearch search,  @PageableDefault(size = 15, sort = "lastModifiedAt", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Departmenttype> page = departmenttypeService.search(search, pageable);
		// 数据封装
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Departmenttype departmenttype : page) {
			Map<String, Object> typeMap = new HashMap<String, Object>();
			typeMap.put("label", departmenttype.getLabel());
			typeMap.put("contactName", departmenttype.getContactName());
			typeMap.put("contactPhone", departmenttype.getContactPhone());
			typeMap.put("contactAddress", departmenttype.getContactAddress());
			typeMap.put("state", departmenttype.getState() == null ? null : departmenttype.getState().getLabel());
			typeMap.put("stateCode",
					departmenttype.getState() == null ? null : departmenttype.getState().getStateCode());
			typeMap.put("createdAt",
					departmenttype.getCreatedAt() == null ? null : sdf.format(departmenttype.getCreatedAt()));
			typeMap.put("lastModifiedAt",
					departmenttype.getLastModifiedAt() == null ? null : sdf.format(departmenttype.getLastModifiedAt()));
			typeMap.put("id", departmenttype.getId());
			returnList.add(typeMap);
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("content", returnList);
		returnMap.put("size", page.getSize());
		returnMap.put("totalElements", page.getTotalElements());
		returnMap.put("totalPages", page.getTotalPages());
		returnMap.put("number", page.getNumber());
		return new ResponseBody(returnMap);
	}

	@RequestMapping(value = "/changeState", method = RequestMethod.PATCH)
	public ResponseBody changeState(@RequestParam(name = "id") Integer id) {
		Departmenttype type = departmenttypeService.findOne(id);
		if (type == null) {
			throw new FailReturnObject(ExceptionResultEnum.DEPARTMENTTYPE_ID_ERROR);
		}
		departmenttypeService.updateState(type);
		return new ResponseBody("");
	}

	@RequestMapping(value = "/findListDept", method = RequestMethod.GET)
	public ResponseBody findListDept(@RequestParam(name = "id") Integer id, @PageableDefault(15)Pageable pageable) {
		return departmenttypeService.findListDept(id, pageable);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseBody save(@RequestBody Departmenttype departmenttype) {
		departmenttypeService.save(departmenttype);
		return new ResponseBody("");
	}

	//编辑部门类型
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ResponseBody edit(@RequestBody DepartmenttypeForSearch departmenttypeForSearch) {
		departmenttypeService.edit(departmenttypeForSearch);
		return new ResponseBody("ok");
	}

	//根据部门类型id查出相关信息进行回显
	@RequestMapping(value = "/{id}")
	public ResponseBody findOne(@PathVariable("id") Integer id){
		Departmenttype one = departmenttypeService.findOne(id);
		Map<String ,Object> map=new HashMap<String, Object>();
		map.put("label",one.getLabel());
		map.put("createdAt",one.getCreatedAt());
		map.put("lastModifiedAt",one.getLastModifiedAt());
		map.put("contactName",one.getContactName());
		map.put("contactAddress",one.getContactAddress());
		map.put("stateCode",one.getState().getStateCode());
		map.put("contactPhone",one.getContactPhone());
		return  new ResponseBody(map);
	}
}
