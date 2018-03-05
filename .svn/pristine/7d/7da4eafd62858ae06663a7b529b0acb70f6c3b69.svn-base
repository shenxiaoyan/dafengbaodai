package com.liyang.controller;

import com.liyang.client.CreateEnquiryJsonParseFactory;
import com.liyang.client.ICreateEnquiryJsonParseAdapter;
import com.liyang.domain.offerresult.OfferResult;
import com.liyang.domain.offerresult.OfferResultDataResult;
import com.liyang.domain.role.*;
import com.liyang.domain.submitproposal.SubmitProposal;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.service.RoleService;
import com.liyang.util.FailReturnObject;

import java.util.*;

/**
 * 角色管理
 * 
 * @author huanghengkun
 * @create 2017年12月29日
 */
@RestController
@RequestMapping("/dafeng/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	@Autowired
    private RoleStateRepository roleStateRepository;
	//添加角色
	@PostMapping(value = "/saveRole")
	public ResponseBody saveRole(@RequestBody(required=false)RoleForUpdate roleForUpdate) {
		roleService.saveRole(roleForUpdate.getLabel(),roleForUpdate.getRoleCode());
		return new ResponseBody(ExceptionResultEnum.SUCCESS);
	}

	/**
	 * 获取所有状态列表
	 * @return
	 */
	@RequestMapping("/findStateCodeList")
	public  ResponseBody finedStateCode(){
		return new ResponseBody( roleStateRepository.findAllStateCode());
	}

	/**
	 * 根据状态查询相关角色信息
	 * @return
	 */
	@PostMapping("/getByStateCode")
	public  ResponseBody getByStateCode(@RequestBody RoleForSearch rfs, @PageableDefault(value = 15, sort = { "lastModifiedAt" }, direction = Sort.Direction.DESC) Pageable pageable){
		if (rfs==null) {
			rfs=new RoleForSearch();
		}
		Map<String,Object> rolesPage = roleService.mulByStateCodePage(rfs, pageable);
		return new ResponseBody(rolesPage);

	}

//更新编辑角色信息
	@PatchMapping("/{id}")
	public ResponseBody editRole(@PathVariable Integer id, @RequestBody RoleForUpdate updateBean) {
		Role role = roleService.findOne(id);
		if (role == null) {
			throw new FailReturnObject(ExceptionResultEnum.ROLE_ID_ERROR);
		}
		roleService.update(role, updateBean,id);
		return new ResponseBody(ExceptionResultEnum.SUCCESS);
	}
	//禁用启用角色
	@PostMapping("/disabled")
	public ResponseBody enabled( @RequestBody RoleForUpdate updateBean) {
		roleService.updateStateCode(updateBean.getId());
		return new ResponseBody(ExceptionResultEnum.SUCCESS);
	}
//删除角色
	@DeleteMapping("/{id}")
	public ResponseBody deleteRole(@PathVariable Integer id) {
		Role role = roleService.findOne(id);
		if (role == null) {
			throw new FailReturnObject(ExceptionResultEnum.ROLE_ID_ERROR);
		}
		roleService.delete(role);
		return new ResponseBody(ExceptionResultEnum.SUCCESS);
	}


}
