package com.liyang.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.liyang.domain.customer.CustomerVO;
import com.liyang.domain.otherinsureinterestperson.OtherInsureInterestPerson;
import com.liyang.domain.otherinsureinterestperson.OtherInsureInterestPersonForSearch;
import com.liyang.helper.ResponseBody;
import com.liyang.service.OtherInsureInterestService;
import com.liyang.util.PageUtil;

/**
 * 前端用户点击感兴趣的控制器
 * 
 * @author Administrator
 */

@RestController
@RequestMapping("/dafeng")
public class OtherInsureInterestController {

	@Autowired
	private OtherInsureInterestService otherInsureInterestService;

	@RequestMapping(value = "/otherInsureInterest", method = RequestMethod.POST)
	public ResponseBody clickInterest(@RequestBody OtherInsureInterestPerson oiip, HttpServletRequest request) {
		otherInsureInterestService.clickInterest(oiip, request);
		return new ResponseBody(0, "ok");
	}

	@PostMapping("/searchOtherInsure")
	public Map<String, Object> multiConditionSearch(
			@RequestBody OtherInsureInterestPersonForSearch otherInsureForSearch,
			@PageableDefault(15) Pageable pageable) {
		Page<OtherInsureInterestPerson> page = otherInsureInterestService.multiConditionSearch(otherInsureForSearch,
				pageable);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (OtherInsureInterestPerson oiip : page) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", oiip.getId());
			map.put("title", oiip.getTitle());
			map.put("clickTime", oiip.getClickTime().getTime() / 1000);
			if (null != oiip.getCustomer()) {
				// oiip.getCustomer().setSubmitProposalFile(null);
				// oiip.getCustomer().setDafengApiCustomer(null);
				// if (null!=oiip.getCustomer().getState()) {
				// oiip.getCustomer().getState().setActs(null);
				// oiip.getCustomer().getState().setFromActs(null);
				// oiip.getCustomer().getState().setWorkflows(null);
				// }
				// -------------------------------
				CustomerVO customerVO = new CustomerVO();
				BeanUtils.copyProperties(oiip.getCustomer(), customerVO);
				map.put("customer", customerVO);
			}
			// map.put("customer", oiip.getCustomer());
			list.add(map);
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("otherInsureInterestPersons", list);
		returnMap.put("page", PageUtil.getPageData(page));
		return returnMap;
	}

}
