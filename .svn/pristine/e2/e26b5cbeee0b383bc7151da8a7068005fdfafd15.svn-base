package com.liyang.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.liyang.domain.salesman.Salesman;
import com.liyang.domain.salesman.SalesmanForSearch;
import com.liyang.domain.salesman.SalesmanForUpdate;
import com.liyang.domain.salesman.SalesmanRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.service.SalesmanService;
import com.liyang.util.FailReturnObject;

/**
 * 代理人
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/dafeng/salesman")
public class SalesmanController {
	
	@Autowired
	SalesmanService salesmanService;
	
	@Autowired
	SalesmanRepository salesmanRepository;
	
	@RequestMapping("/create")
	public ResponseBody createSalesman(Salesman salesman) {
		Salesman saveSalesman = salesmanService.saveSalesman(salesman);
		if (null == saveSalesman) {
			throw new FailReturnObject(ExceptionResultEnum.SALESMAN_CREATE_ERROR);
		}
		return new ResponseBody("新增成功");
	}
	
	/**
	 * 更新代理人
	 * @param id
	 * @param name
	 * @param phoneNumber
	 * @param departmentId
	 * @return
	 */
	@RequestMapping("/update")
	public ResponseBody updateSalesman(@RequestBody SalesmanForUpdate updateBean) {
		Salesman salesman = salesmanRepository.findById(updateBean.getId());
		if (salesman == null) {
			throw new FailReturnObject(ExceptionResultEnum.SALESMAN_ID_ERROR);
		}
		salesmanService.updateSalesman(salesman,updateBean);
		return new ResponseBody(ExceptionResultEnum.SUCCESS);
	}
	
	@RequestMapping(value = "/changeState", method = RequestMethod.PATCH)
	public ResponseBody changeState(Integer id) {
		Salesman salesman = salesmanRepository.findById(id);
		if (salesman == null) {
			throw new FailReturnObject(ExceptionResultEnum.SALESMAN_ID_ERROR);
		}
		salesmanService.updateState(salesman);
		return new ResponseBody("");
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.DELETE) 
	public ResponseBody delete (Integer id) {
		Salesman salesman = salesmanRepository.findById(id);
		if (salesman == null) {
			throw new FailReturnObject(ExceptionResultEnum.SALESMAN_ID_ERROR);
		}
		salesmanService.delete(salesman);
		return new ResponseBody(ExceptionResultEnum.SUCCESS);
	}
	
	/**
	 * 
	 */
//	@RequestMapping("/delete")
//	public ResponseBody deleteSalesman(@RequestParam(required=true) String id) {
//		Integer id1 = Integer.valueOf(id);
//		salesmanRepository.updateSalesmanStateById(id1);
//		return new ResponseBody(ExceptionResultEnum.SALESMAN_EDIT_ERROR);
//	}
	
	/**
	 * 获取当前用户所在部门下的营业员
	 * @return
	 */
//	统计中调用
//	@RequestMapping("/findOwnDepartmentSalesmen")
//	public List<Salesman> findOwnDepartmentSalesmen(){
//		List<Salesman> list = salesmanService.findOwnDepartmentSalesmen();
//		for (Salesman salesman : list) {
//			salesman.setDepartment(null);
//			salesman.setState(null);
//			salesman.setCreatedByDepartment(null);
//		}
//		return list;
//	}
	
	/**
	 * 代理人列表多条件搜索
	 * @param salesmanForSearch
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value="/searchSalesman",method=RequestMethod.POST)
	public ResponseBody multiConditionSearch(@RequestBody(required =false) SalesmanForSearch salesmanForSearch, @PageableDefault(value=15,sort={"lastModifiedAt"},direction=Direction.DESC) Pageable pageable) {
		Page<Salesman> salesmanPage = salesmanService.multiConditionSearch(salesmanForSearch, pageable);
		List<Object> salesmanMapList = new ArrayList<>();
		for (Salesman s : salesmanPage) {
			Map<String,Object> salesmanMap = new HashMap<>();
			salesmanMap.put("id", s.getId());
			salesmanMap.put("label",s.getLabel());
			salesmanMap.put("createdAt", s.getCreatedAt());
			salesmanMap.put("lastModifiedAt", s.getLastModifiedAt());
			salesmanMap.put("enabled", s.getEnabled());
			salesmanMap.put("name", s.getName());
			salesmanMap.put("departmentLabel",s.getDepartment()== null ? null : s.getDepartment().getLabel());
			salesmanMap.put("phoneNumber", s.getPhoneNumber());
			salesmanMap.put("stateCode", s.getState()== null ? null : s.getState().getStateCode());
			salesmanMapList.add(salesmanMap);
			}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("salesmen", salesmanMapList);
		returnMap.put("size", salesmanPage.getSize());
		returnMap.put("totalElements", (int) salesmanPage.getTotalElements());
		returnMap.put("totalPages", salesmanPage.getTotalPages());
		returnMap.put("number", salesmanPage.getNumber());
		return 	new ResponseBody(returnMap);    
		
	}	
}