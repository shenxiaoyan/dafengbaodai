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
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liyang.domain.journal.Journal;
import com.liyang.domain.journal.JournalTO;
import com.liyang.helper.ResponseBody;
import com.liyang.service.JournalService;
import com.liyang.util.PageUtil;

/**
 * @author Adam
 * @version 创建时间：2018年2月2日 下午12:46:20 类说明
 */
@RestController
@RequestMapping("/dafeng/journal")
public class JournalController {

	@Autowired
	JournalService journalService;

	/**
	 * web端获取流水列表、查询
	 */
	@PostMapping("/journalList")
	public ResponseBody JournalList(@RequestBody(required = false) JournalTO journalTO,
			@PageableDefault(size = 15, sort = "createdAt", direction = Direction.DESC) Pageable pageable) {
		Page<Journal> journalPage = journalService.query(pageable, journalTO);
		List<JournalTO> journalTOList = new ArrayList<>();
		for (Journal journal : journalPage) {
			journalTO = new JournalTO();
			BeanUtils.copyProperties(journal, journalTO);
			journalTO.setStateCode(journal.getState().getStateCode());
			journalTO.setCustomerId(journal.getCustomer().getId());
			journalTO.setCustomerPhone(journal.getCustomer().getPhone());
			journalTO.setCustomerRealname(journal.getCustomer().getAccount().getRealName());
			journalTO.setProductLabel(journal.getProduct().getLabel());
			journalTO.setInsuredName(journal.getInsuredName());
			journalTO.setProductCompanyLabel(journal.getProduct().getProductCompany().getLabel());
			journalTOList.add(journalTO);
		}
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("journals", journalTOList);
		returnMap.put("page", PageUtil.getPageData(journalPage));
		return new ResponseBody(returnMap);
	}

	/**
	 * 移动端获取自身账户流水明细+累计
	 *
	 * @return
	 */
	@PostMapping("/mobile/own")
	public ResponseBody mibileOwnJournal(HttpServletRequest request, @RequestBody(required = false) JournalTO journalTO,
			@PageableDefault(size = 15, sort = "createdAt", direction = Direction.DESC) Pageable pageable) {
		String token = request.getHeader("token");
		if (journalTO == null) {
			journalTO = new JournalTO();
		}
		journalTO.setToken(token);
		Page<Journal> journalPage = journalService.query(pageable, journalTO);
		List<JournalTO> journalTOList = new ArrayList<>();
		for (Journal journal : journalPage) {
			journalTO = new JournalTO();
			BeanUtils.copyProperties(journal, journalTO);
			journalTO.setStateCode(journal.getState().getStateCode());
			journalTO.setProductCompanyLabel(journal.getProduct().getProductCompany().getLabel());
			journalTOList.add(journalTO);
		}
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("journals", journalTOList);
		returnMap.put("commissionJS", journalService.sumSettlingByToken(token));
		returnMap.put("sumCommssion", journalService.sumSettledByToken(token) + journalService.sumSettlingByToken(token));
		returnMap.put("page", PageUtil.getPageData(journalPage));
		return new ResponseBody(returnMap);
	}

	/**
	 * 移动端累计佣金
	 * @param request
	 * @return
	 */
	@GetMapping("/mobile/commission")
	public ResponseBody mobileCommissionAlone(HttpServletRequest request) {
		String token = request.getHeader("token");
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("commissionJS", journalService.sumSettlingByToken(token));
		returnMap.put("sumCommission", journalService.sumSettledByToken(token) + journalService.sumSettlingByToken(token));
		return new ResponseBody(returnMap);
	}

}
