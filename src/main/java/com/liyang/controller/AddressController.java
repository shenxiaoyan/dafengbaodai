package com.liyang.controller;

import java.text.SimpleDateFormat;
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

import com.liyang.domain.address.Address;
import com.liyang.domain.address.AddressForSearch;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.service.AddressService;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/dafeng/address")
public class AddressController {

	@Autowired
	private AddressService addressService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseBody save(@RequestBody Address address) {
		addressService.save(address);
		return new ResponseBody(0, "success");
	}

	@RequestMapping(value = "/changeState", method = RequestMethod.PATCH)
	public ResponseBody changeState(Integer id) {
		Address address = addressService.findOne(id);
		if (address == null) {
			throw new FailReturnObject(ExceptionResultEnum.ADDRESS_ID_ERROR);
		}
		addressService.updateState(address);
		return new ResponseBody(0, "success");
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseBody search(AddressForSearch search,
			@PageableDefault(size = 15, sort = "lastModifiedAt", direction = Direction.DESC) Pageable pageable) {

		Page<Address> resultPage = addressService.search(search, pageable);

		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Address address : resultPage) {
			Map<String, Object> addressMap = new HashMap<String, Object>();
			addressMap.put("id", address.getId());
			addressMap.put("province", address.getProvince());
			addressMap.put("city", address.getCity());
			addressMap.put("cityCode", address.getCityCode());
			addressMap.put("createdAt", sdf.format(address.getCreatedAt()));
			addressMap.put("lastModifiedAt", sdf.format(address.getLastModifiedAt()));
			addressMap.put("stateLabel", address.getState().getLabel());
			addressMap.put("stateCode", address.getState().getStateCode());
			resultList.add(addressMap);
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("content", resultList);
		returnMap.put("size", resultPage.getSize());
		returnMap.put("totalElements", resultPage.getTotalElements());
		returnMap.put("totalPages", resultPage.getTotalPages());
		returnMap.put("number", resultPage.getNumber());
		ResponseBody body = new ResponseBody(ExceptionResultEnum.SUCCESS);
		body.setData(returnMap);
		return body;
	}

	// @RequestMapping(value = "/all", method = RequestMethod.GET)
	// public ResponseBody findALL() {
	// return addressService.findAll();
	// }

	@RequestMapping(value = "/findAllForMobile", method = RequestMethod.GET)
	public ResponseBody findALL() {
		return addressService.findAll();
	}
}
