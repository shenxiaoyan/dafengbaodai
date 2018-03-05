package com.liyang.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liyang.domain.insuranceresult.InsuranceResult;
import com.liyang.domain.insuranceresult.InsuranceResultForSearch;
import com.liyang.domain.offerresult.OfferResultDataResult;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.service.InsuranceResultService;
import com.liyang.util.CommonUtil;
import com.liyang.util.ExcelUtil;
import com.liyang.util.FailReturnObject;

/**
 * 承保结果推送接口 数据类型为x-www-form-urlencode 数据格式： data={数据}
 * 
 * @author Administrator
 */

@RestController
@RequestMapping("/dafeng")
public class InsuranceResultController {

	@Autowired
	InsuranceResultService insuranceResultService;

	private final static Logger logger = LoggerFactory.getLogger(InsuranceResultController.class);

	/**
	 * 承保结果小马推送接口
	 * 
	 * @param insResStr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insuranceResult", method = RequestMethod.POST)
	public String insuranceResult(@RequestParam(value = "data") String insResStr) throws Exception {
		logger.info("【承保结果小马推送返回】：" + insResStr);
		String responseRes = insuranceResultService.saveInsRes(CommonUtil.handerToMap(insResStr));
		logger.info("【承保处理结果返回小马 】：" + responseRes);
		// 实际联调时加上aes算法
		return responseRes;
	}

	@PostMapping("/searchInsuranceResultById")
	public Map<String, Object> multiConditionSearchById(
			@RequestBody(required = false) InsuranceResultForSearch insuranceResultForSearch,
			@PageableDefault(15) Pageable pageable) {
		Integer customerId = insuranceResultForSearch.getCustomerId();
		Page<InsuranceResult> insuranceResultPage = insuranceResultService
				.multiConditionSearchById(insuranceResultForSearch, pageable, customerId);
		List<Object> list = new ArrayList<Object>();
		for (InsuranceResult is : insuranceResultPage) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", is.getId());
			map.put("data", is.getData());
			map.put("createdAt", is.getCreatedAt());
			OfferResultDataResult result = is.getSubmitProposal().getOfferResult().getData().getResult();
			if (result != null) {
				Map<String, Object> offerResult = new HashMap<>();
				offerResult.put("insuranceCompanyName", result.getInsuranceCompanyName());
				if (result.getOfferDetail() != null) {
					// offerResult.put("forcePremium",
					// result.getOfferDetail().get("forcePremium"));
					// offerResult.put("taxPrice",
					// result.getOfferDetail().get("taxPrice"));
					double forcePremium = result.getOfferDetail().getJSONObject("forcePremium")
							.getDouble("quotesPrice");
					double taxPrice = result.getOfferDetail().getJSONObject("taxPrice").getDouble("quotesPrice");
					double originalForcePrice = forcePremium + taxPrice;
					offerResult.put("originalForcePrice", originalForcePrice);
					double originalPrice = result.getOfferDetail().getDouble("originalPrice");
					offerResult.put("originalPrice", originalPrice);
					offerResult.put("sumPrice", originalForcePrice + originalPrice);
					map.put("offerResult", offerResult);
				}
			}
			Map<String, Object> submitProposal = new HashMap<>();
			if (is.getSubmitProposal().getParams() != null) {
				submitProposal.put("ownerName", is.getSubmitProposal().getParams().getOwnerName());
				submitProposal.put("customerPhone", is.getSubmitProposal().getParams().getCustomerPhone());
				submitProposal.put("contactName", is.getSubmitProposal().getParams().getContactName());
				submitProposal.put("contactPhone", is.getSubmitProposal().getParams().getContactPhone());
				submitProposal.put("contactAddress", is.getSubmitProposal().getParams().getContactAddress());
				// 返回数据添加大蜂配送信息
				submitProposal.put("dafengContactName", is.getSubmitProposal().getParams().getDafengContactName());
				submitProposal.put("dafengContactPhone", is.getSubmitProposal().getParams().getDafengContactPhone());
				submitProposal.put("dafengAddress", is.getSubmitProposal().getParams().getDafengAddress());
				map.put("submitProposal", submitProposal);
			}
			list.add(map);
		}
		Map<String, Integer> pageDataMap = new HashMap<>();
		pageDataMap.put("size", insuranceResultPage.getSize());
		pageDataMap.put("totalElements", (int) insuranceResultPage.getTotalElements());
		pageDataMap.put("totalPages", insuranceResultPage.getTotalPages());
		pageDataMap.put("number", insuranceResultPage.getNumber());
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("insuranceResults", list);
		returnMap.put("page", pageDataMap);
		return returnMap;
	}

	/**
	 * 保单（承保结果）多条件查询
	 * 
	 * @param insuranceResultForSearch
	 * @param pageable
	 * @return
	 */
	@PostMapping("/searchInsuranceResult")
	public Map<String, Object> multiConditionSearch(
			@RequestBody(required = false) InsuranceResultForSearch insuranceResultForSearch,
			@PageableDefault(value = 15, sort = "createdAt", direction = Direction.DESC) Pageable pageable) {
		Page<InsuranceResult> insuranceResultPage = insuranceResultService
				.multiConditionSearch(insuranceResultForSearch, pageable);
		List<Object> list = new ArrayList<Object>();
		for (InsuranceResult is : insuranceResultPage) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", is.getId());
			map.put("data", is.getData());
			map.put("createdAt", is.getCreatedAt());
			OfferResultDataResult result = is.getSubmitProposal().getOfferResult().getData().getResult();
			if (result != null) {
				Map<String, Object> offerResult = new HashMap<>();
				offerResult.put("insuranceCompanyName", result.getInsuranceCompanyName());
				if (result.getOfferDetail() != null) {
					double forcePremium = result.getOfferDetail().getJSONObject("forcePremium")
							.getDouble("quotesPrice");
					double taxPrice = result.getOfferDetail().getJSONObject("taxPrice").getDouble("quotesPrice");
					double originalForcePrice = forcePremium + taxPrice;
					offerResult.put("originalForcePrice", originalForcePrice);
					double originalPrice = result.getOfferDetail().getDouble("originalPrice");
					offerResult.put("originalPrice", originalPrice);
					offerResult.put("sumPrice", originalForcePrice + originalPrice);
					map.put("offerResult", offerResult);
					// offerResult.put("forcePremium", result.getOfferDetail().get("forcePremium"));
					// offerResult.put("taxPrice", result.getOfferDetail().get("taxPrice"));
					// map.put("offerResult", offerResult);
				}
			}
			Map<String, Object> submitProposal = new HashMap<>();
			if (is.getSubmitProposal().getParams() != null) {
				submitProposal.put("ownerName", is.getSubmitProposal().getParams().getOwnerName());
				submitProposal.put("customerPhone", is.getSubmitProposal().getParams().getCustomerPhone());
				submitProposal.put("contactName", is.getSubmitProposal().getParams().getContactName());
				submitProposal.put("contactPhone", is.getSubmitProposal().getParams().getContactPhone());
				submitProposal.put("contactAddress", is.getSubmitProposal().getParams().getContactAddress());
				// 返回数据添加大蜂配送信息
				submitProposal.put("dafengContactName", is.getSubmitProposal().getParams().getDafengContactName());
				submitProposal.put("dafengContactPhone", is.getSubmitProposal().getParams().getDafengContactPhone());
				submitProposal.put("dafengAddress", is.getSubmitProposal().getParams().getDafengAddress());
				map.put("submitProposal", submitProposal);
			}
			list.add(map);
		}
		Map<String, Integer> pageDataMap = new HashMap<>();
		pageDataMap.put("size", insuranceResultPage.getSize());
		pageDataMap.put("totalElements", (int) insuranceResultPage.getTotalElements());
		pageDataMap.put("totalPages", insuranceResultPage.getTotalPages());
		pageDataMap.put("number", insuranceResultPage.getNumber());
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("insuranceResults", list);
		returnMap.put("page", pageDataMap);
		return returnMap;
	}

	/**
	 * 组合条件筛选后 保单下载
	 * 
	 * @param response
	 * @param range
	 * @param beginTime
	 * @param endTime
	 */
	@GetMapping("/downloadInsuranceResult")
	public void download(HttpServletResponse response, @RequestParam(required = true) String range, Date beginTime,
			Date endTime) {
		List<InsuranceResult> insResultList = insuranceResultService.findByCreatedAt(range, beginTime, endTime);
		String[] headers = { "出单单号", "商业险保单号", "交强险保单号", "投保公司", "车牌号", "车主", "商业险保费", "交强险保费（交强险+车船税）",
				"合计保费（商业险+交强险）", "出单时间", "出单人电话", "联系人", "联系电话", "配送地址", "联系人(大蜂)", "联系电话(大蜂)", "配送地址(大蜂)" };
		String title = "核保保单记录下载";
		String downloadName = null;
		try {
			downloadName = URLEncoder.encode(title + ".xls", "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		response.reset();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + downloadName);
		OutputStream outs = null;
		try {
			outs = response.getOutputStream();
			ExcelUtil.exportInsResult(title, headers, insResultList, outs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new FailReturnObject(ExceptionResultEnum.FILEDOWNLOAD_ERROR);
		}
		try {
			outs.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 临时方法，更新已有的保单的createdAt，原先createAt字段未设置值， 仅手动执行一次即可,注意，且仅可执行一次
	 * 
	 * @return
	 */
	@RequestMapping("/insuranceResult/updateCreatedAt")
	public ResponseBody updateCreateAt() {
		String msg = insuranceResultService.updateCreatedAt();
		return new ResponseBody(msg);
	}

}
