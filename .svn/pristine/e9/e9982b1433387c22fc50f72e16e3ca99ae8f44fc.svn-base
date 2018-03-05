package com.liyang.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.liyang.domain.insurercompany.InsureCompany;
import com.liyang.domain.insurercompany.InsureCompanyForSearch;
import com.liyang.domain.insurercompany.InsureCompanyForUpdate;
import com.liyang.domain.insurercompany.InsureCompanyRepository;
import com.liyang.domain.insurercompany.InsureCompanyVO;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.service.InsureCompanyService;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/dafeng")
public class InsurerCompanyController {
	@Autowired
	private InsureCompanyRepository insureCompanyrepository;
	@Autowired
	InsureCompanyService insureCompanyService;

	@RequestMapping(value = "/insurerCompany/query", method = RequestMethod.GET)
	public ResponseBody query() {
		List<InsureCompany> list = insureCompanyrepository.getAllEnable();
//		for (InsureCompany insureCompany : list) {
//			insureCompany.setCreatedByDepartment(null);
//		}
//		ResponseBody response = new ResponseBody(0, "ok");
//		response.setData(list);
//		return response;
//		-------------------------------
		List<InsureCompanyVO> VOList = new ArrayList<>();
		for (InsureCompany insureCompany : list) {
			InsureCompanyVO insureCompanyVO = new InsureCompanyVO();
			BeanUtils.copyProperties(insureCompany, insureCompanyVO);
			VOList.add(insureCompanyVO);
		}
		return new ResponseBody(VOList);
	}

	@PostMapping("/searchInsureCompany")
	public ResponseBody multiConditionSearch(@RequestBody(required = false) InsureCompanyForSearch insureCompanyForSearch,
			@PageableDefault(size = 15, sort = "lastModifiedAt", direction = Direction.DESC) Pageable pageable) {
		Page<InsureCompany> insureCompanyPage = insureCompanyService.multiComditionSearch(insureCompanyForSearch,
				pageable);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("insureCompanies", insureCompanyPage.getContent());
		Map<String, Integer> pageDataMap = new HashMap<>();
		pageDataMap.put("size", insureCompanyPage.getSize());
		pageDataMap.put("totalElements", (int) insureCompanyPage.getTotalElements());
		pageDataMap.put("totalPages", insureCompanyPage.getTotalPages());
		pageDataMap.put("number", insureCompanyPage.getNumber());
		returnMap.put("page", pageDataMap);
		return new ResponseBody(returnMap);
	}

	@RequestMapping(value = "/insurerCompany/update", method = RequestMethod.PATCH)
	public ResponseBody update(@RequestBody InsureCompanyForUpdate update) {
		InsureCompany insureCompany = insureCompanyrepository.findById(update.getId());
		if (insureCompany == null) {
			throw new FailReturnObject(ExceptionResultEnum.INSURECOMPANY_ID_ERROR);
		}
		insureCompanyService.update(insureCompany, update);
		return new ResponseBody("");
	}

	@RequestMapping(value = "/insurerCompany/changeState", method = RequestMethod.PATCH)
	public ResponseBody changeState(Integer id) {
		InsureCompany insureCompany = insureCompanyrepository.findById(id);
		if (insureCompany == null) {
			throw new FailReturnObject(ExceptionResultEnum.INSURECOMPANY_ID_ERROR);
		}
		insureCompanyService.updateState(insureCompany);
		return new ResponseBody("");
	}

	@RequestMapping(value = "/insurerCompany/save", method = RequestMethod.POST)
	public ResponseBody save(@RequestBody InsureCompany company) {
		insureCompanyService.save(company);
		return new ResponseBody("");
	}
}
