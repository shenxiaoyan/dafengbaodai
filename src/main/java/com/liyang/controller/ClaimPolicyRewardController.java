package com.liyang.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.liyang.domain.claimPolicyRewards.ClaimPolicyReward;
import com.liyang.domain.claimPolicyRewards.ClaimPolicyRewardForSearch;
import com.liyang.domain.claimPolicyRewards.ClaimPolicyRewardRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.service.ClaimPolicyRewardService;
import com.liyang.util.CommonUtil;
import com.liyang.util.ExcelUtil;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/dafeng/claimPolicyReward")
public class ClaimPolicyRewardController {

	@Autowired
	ClaimPolicyRewardService claimPolicyRewardService;
	@Autowired
	ClaimPolicyRewardRepository claimPolicyRewardRepository;

	@InitBinder
	public void initData(WebDataBinder wdb) {
		wdb.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	/**
	 * 下载保单导入模板
	 * @param response
	 */
	@RequestMapping(value = "/downloadPolicyModule", method = RequestMethod.GET)
	public void downloadPolicyModel(HttpServletResponse response) throws IOException, Exception {
		String fileName = "保单导入模版";
		String[] headers = { "保险公司", "签单日期", "保单号", "投保人", "被保人", "车主身份证号", "车牌", "险种名称", "含税保费", "不含税保费",
				"手续费率", "已付手续费", "车船税", "附加费"};
		ExcelUtil.downloadExcel(fileName, headers, null, null, null, response);
	}
	
	/**
	 * 生成token，防止excel重复提交（暂未使用）
	 * @return
	 */
	public ResponseBody getToken(HttpServletRequest request) {
		String excelImportToken = UUID.randomUUID().toString();
		request.getSession().setAttribute("excelImportToken", excelImportToken);
		return new ResponseBody(excelImportToken);
	}
	
	/**
	 * 批量导入保险公司保单信息
	 * 
	 * @param request
	 * @param file
	 * @return
	 */
	@PostMapping("/batchImport")
	public ResponseBody batchImport(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
		if (file == null) {
			return new ResponseBody(ExceptionResultEnum.CLAIMPOLICY_DATA_IMPORT_ERROR);
		}
		String msg = claimPolicyRewardService.batchImport(file);
		return new ResponseBody(msg);
	}
	
	/**
	 * 多条件查询
	 * @param claimPolicyRewardForSearch
	 * @param pageable
	 * @return
	 */
	@PostMapping("/search")
	public Map<String, Object> multiConditionSearch(@RequestBody ClaimPolicyRewardForSearch claimPolicyRewardForSearch,
			@PageableDefault(value = 15) Pageable pageable) {
		Page<ClaimPolicyReward> page = claimPolicyRewardService.multiConditionsearch(claimPolicyRewardForSearch,
				pageable);
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("claimPolicyReward", page.getContent());
		returnMap.put("page", getPageData(page));
		return returnMap;
	}
	public  Map<String,Object> mulFindUnclaimed(){
		return  null;
	}

	/**
	 * 从未确认中进行多条件查询
	 * @param cprfs
	 * @param pageable
	 * @return
	 */
	@PostMapping("/searchUnconfirmed")
	public Map<String, Object> mulConSearchUnconfirmed(@RequestBody ClaimPolicyRewardForSearch cprfs,
			@PageableDefault(value = 15) Pageable pageable) {
		Page<ClaimPolicyReward> page = claimPolicyRewardService.multiConditionsearchUnconfirmed(cprfs, pageable);
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("claimPolicyReward", page.getContent());
		returnMap.put("page", getPageData(page));
		return returnMap;
	}


	/**
	 * 未认领列表
	 * @param cprfs
	 * @param pageable
	 * @return
	 */
	@PostMapping("/searchFindUnclaimed")
	public ResponseBody mulFindUnclaimed(@RequestBody ClaimPolicyRewardForSearch cprfs, @PageableDefault(value = 15) Pageable pageable) {
        if (cprfs==null){
        	cprfs=new ClaimPolicyRewardForSearch();
		}
		cprfs.setStateCode("ENABLED");
		cprfs.setClaimFlag(false);
		Map<String, Object> stringObjectMap = claimPolicyRewardService.mulConSearchVehicle(cprfs, pageable);
		return new ResponseBody(stringObjectMap);
	}
	
	/**
	 * 审核附加费
	 * @param cprfs
	 * @param pageable
	 * @return
	 */
	@PostMapping("/findAdditionalFalse")
	public  ResponseBody findAdditionalFalse(@RequestBody(required = false) ClaimPolicyRewardForSearch cprfs,@PageableDefault(15) Pageable pageable){
		if (cprfs==null){
			cprfs=new ClaimPolicyRewardForSearch();
		}
		cprfs.setStateCode("ENABLED");
		cprfs.setAdditionalFeeCheckFlag(false);
		Map<String, Object> stringObjectMap = claimPolicyRewardService.mulConSearchVehicle(cprfs, pageable);
		return new ResponseBody( stringObjectMap);
	}

	/**
	 * 审核车船税列表
	 */
	@RequestMapping("checkAdditionList")
	public  ResponseBody checkAdditionList(@RequestBody(required = false) ClaimPolicyRewardForSearch cprfs,@PageableDefault(15) Pageable pageable){
		if (cprfs==null){
			cprfs=new ClaimPolicyRewardForSearch();
		}
		cprfs.setStateCode("ENABLED");
		cprfs.setReturnVehicleTaxFeeCheckFlag(false);
		Map<String, Object> stringObjectMap = claimPolicyRewardService.mulConSearchVehicle(cprfs, pageable);

		return  new ResponseBody(stringObjectMap);
	}
	/**
	 * 审核附加费
	 * @param cprfs
	 * @return
	 */
	@PostMapping("checkAdditionOrRetVehicle")
	public ResponseBody checkAddition(@RequestBody ClaimPolicyRewardForSearch cprfs,@PageableDefault(15) Pageable pageable) {
		if (cprfs==null){
			cprfs=new ClaimPolicyRewardForSearch();
		}
		cprfs.setStateCode("ENABLED");
		cprfs.setAdditionalFeeCheckFlag(false);
		Map<String, Object> stringObjectMap = claimPolicyRewardService.mulConSearchVehicle(cprfs, pageable);
		return new ResponseBody(stringObjectMap);
	}
	
	/**
	 * 已审核车船税列表（搜索）+已审核车船税金额+车船税返还金额
	 * @param cprfs
	 * @param pageable
	 * @return
	 */
	@PostMapping("/vehicleChecked")
	public ResponseBody vehicleChecked(@RequestBody ClaimPolicyRewardForSearch cprfs,
			@PageableDefault(15) Pageable pageable) {
		cprfs.setReturnVehicleTaxFeeCheckFlag(true);
		cprfs.setInsuraceType("交强险");
		cprfs.setStateCode("ENABLED");
		Map<String, Object> returnMap = claimPolicyRewardService.mulConSearchVehicleChecked(cprfs, pageable);
		return new ResponseBody(returnMap);
	}
	
	/**
	 * 下载已审核车船税列表
	 * @param response
	 * @param cprfs
	 * @throws Exception
	 */
	@GetMapping("/downloadVehicleChecked")
	public void downloadVehicleChecked(HttpServletResponse response, ClaimPolicyRewardForSearch cprfs) throws Exception {
		String title = "已审核车船税表单";
		List<ClaimPolicyReward> dataSet = claimPolicyRewardService.multiConditionSearchVehicle(cprfs);
		String[] headers = { "保险公司", "签单日期", "保单号", "投保人", "被保人", "车主身份证号", "车牌", "险种名称", "含税保费", "不含税保费", "已付手续费",
				"车船税", "车船税返还" };
		String[] codes = { "insuraceCompany", "signDate", "policyNumber", "insurApplicant", "insuredPerson",
				"carOwnerIdCard", "carLicense", "insuraceType", "insuraceFeeIncludeTax", "insuraceFeeExcludeTax",
				"receivedBrokerFee", "vehicleTaxFee", "returnVehicleTaxFee" };
		ExcelUtil.downloadExcel(title, headers, codes, ClaimPolicyReward.class, dataSet, response);
	}
	
	/**
	 * 下载未认领表单，可get提交参数进行筛选
	 * @param response
	 * @param cprfs
	 * @throws Exception
	 */
	@GetMapping("/downloadUnclaimed")
	public void downloadUnClaimed(HttpServletResponse response, ClaimPolicyRewardForSearch cprfs) throws Exception {
		
		String title = "未认领表单";
		cprfs.setStateCode("ENABLED");
		cprfs.setClaimFlag(false);
		cprfs.setReturnVehicleTaxFeeCheckFlag(true);
		cprfs.setAdditionalFeeCheckFlag(true);
		Sort sort = new Sort(Direction.DESC, "uploadTime");
		List<ClaimPolicyReward> dataList = claimPolicyRewardService.multiConditionSearchForDownload(cprfs, sort);
		String[] headers = { "保险公司", "签单日期", "保单号", "投保人", "被保人", "车主身份证号", "车牌", "险种名称", "含税保费", "不含税保费", "已付手续费",
				"车船税", "车船税返还", "上传日期" };
		String[] codes = { "insuraceCompany", "signDate", "policyNumber", "insurApplicant", "insuredPerson",
				"carOwnerIdCard", "carLicense", "insuraceType", "insuraceFeeIncludeTax", "insuraceFeeExcludeTax",
				"receivedBrokerFee", "vehicleTaxFee", "returnVehicleTaxFee", "uploadTime"};
		ExcelUtil.downloadExcel(title, headers, codes, ClaimPolicyReward.class, dataList, response);
	}
	
	/**
	 * 执行 认领操作
	 * @param cprf
	 *            保单Id，代理人Id，险种返佣，车船税返佣
	 * @return
	 */
	@PostMapping("/claim")
	public ResponseBody updateClaim(@RequestBody(required = true) ClaimPolicyRewardForSearch cprf) {
		claimPolicyRewardService.doClaim(cprf);
		try {
		} catch (Exception e) {
			return new ResponseBody(205, e.getMessage());
		}
		return new ResponseBody("认领成功");
	}
	
	/**
	 * 待复核列表（搜索）+数据总计佣金
	 * @param cprfs
	 * @param pageable
	 * @return
	 */
	@PostMapping("/searchClaimUnChecked")
	public Map<String, Object> mulConSearchClaimUnchecked(@RequestBody(required=false) ClaimPolicyRewardForSearch cprfs,
			@PageableDefault(value = 15) Pageable pageable) {
		cprfs.setClaimFlag(true);
		cprfs.setClaimCheckFlag(false);
		cprfs.setStateCode("ENABLED");
		return claimPolicyRewardService.mulConSearchCliamUmchecked(cprfs, pageable);
	}
	
	/**
	 * 待复核列表 导出表单
	 * @param response
	 * @param cprfs
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadClaimUnchecked", method = RequestMethod.GET)
	public void downloadCliamUnchecked(HttpServletResponse response, ClaimPolicyRewardForSearch cprfs) throws Exception {
		String title = "认领待复核表单";
		cprfs.setDepartmentId(null);
		cprfs.setClaimFlag(true);
		cprfs.setClaimCheckFlag(false);
		cprfs.setStateCode("ENABLED");
		List<ClaimPolicyReward> dataSet = claimPolicyRewardService.searchForDownload(cprfs);
		String[] headers = { "保险公司", "签单日期", "保单号", "投保人", "被保人", "车主身份证号", "车牌", "险种名称", "含税保费", "不含税保费", "手续费率",
				"已付手续费", "车船税", "车船税返还", "附加费", "归属部门", "代理人", "管理费用", "收益", "上传时间", "认领时间" };
		String[] codes = { "insuraceCompany", "signDate", "policyNumber", "insurApplicant", "insuredPerson",
				"carOwnerIdCard", "carLicense", "insuraceType", "insuraceFeeIncludeTax", "insuraceFeeExcludeTax",
				"brokerFeeRate", "receivedBrokerFee", "vehicleTaxFee", "returnVehicleTaxFee", "additionalFee",
				"departmentLabel", "salesmanName", "managementFee", "profit", "uploadTime", "claimTime" };
		ExcelUtil.downloadExcel(title, headers, codes, ClaimPolicyReward.class, dataSet, response);
	}
	
	/**
	 * 复核管理费用-通过
	 * @param cprfs
	 * @return
	 */
	@PostMapping("/cliamCheckPass")
	public ResponseBody claimCheckPass(@RequestBody(required = true) ClaimPolicyRewardForSearch cprfs) {
		claimPolicyRewardService.claimCheckPass(cprfs);
		return new ResponseBody("复核成功");
	}

	/**
	 * 复核-修改相关管理费用及代理人
	 * @param cprfs
	 * @return
	 */
	@PostMapping("/cliamCheckModify")
	public ResponseBody claimCheckModify(@RequestBody(required = true) ClaimPolicyRewardForSearch cprfs) {
		claimPolicyRewardService.claimCheckModify(cprfs);
		return new ResponseBody("修改成功");
	}
	
	/**
	 * 营业部已认领列表（搜索）+累计认领佣金
	 * @param cprfs
	 * @param pageable
	 * @return
	 */
	@PostMapping("/searchDepClaimed")
	public Map<String, Object> mulConSearchDepClaimed(@RequestBody ClaimPolicyRewardForSearch cprfs,
			@PageableDefault(value = 15) Pageable pageable) {
		cprfs.setClaimCheckFlag(true);
		cprfs.setStateCode("ENABLED");
		cprfs.setDepartmentId(CommonUtil.getCurrentUserDepartment().getId());
		return claimPolicyRewardService.mulConSearchDepClaimed(cprfs, pageable);
	}
	
	/**
	 * 营业部已认领导出表单
	 * @param response
	 * @param cprfs
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadSalesDepClaim", method = RequestMethod.GET)
	public void downloadSalesDepClaim(HttpServletResponse response, ClaimPolicyRewardForSearch cprfs) throws Exception {
		String title = "结果保单";
		if (cprfs.getDepartmentId() == null) {
			throw new FailReturnObject(ExceptionResultEnum.CLAIMPOLICY_COMMISSION_COUNT_ERROR);
		}
		cprfs.setClaimCheckFlag(true);
		cprfs.setStateCode("ENABLED");
		List<ClaimPolicyReward> dataSet = claimPolicyRewardService.searchForDownload(cprfs);
		String[] headers = { "保险公司", "签单日期", "保单号", "投保人", "被保人", "车主身份证号", "车牌", "险种名称", "含税保费", "不含税保费", "已付手续费",
				"车船税", "归属部门", "代理人", "管理费用", "上传时间", "认领时间" };

		String[] codes = { "insuraceCompany", "signDate", "policyNumber", "insurApplicant", "insuredPerson",
				"carOwnerIdCard", "carLicense", "insuraceType", "insuraceFeeIncludeTax", "insuraceFeeExcludeTax",
				"receivedBrokerFee", "vehicleTaxFee", "departmentLabel", "salesmanName", "managementFee", "uploadTime",
				"claimTime" };
		ExcelUtil.downloadExcel(title, headers, codes, ClaimPolicyReward.class, dataSet, response);
	}
	
	/**
	 * 总部收益列表（搜索）+收入、收益
	 * @param cprfs
	 * @param pageable
	 * @return
	 */
	@PostMapping("/searchClaimChecked")
	public Map<String, Object> multiConditionSearchClaimChecked(@RequestBody(required=false) ClaimPolicyRewardForSearch cprfs,
			@PageableDefault(value = 15) Pageable pageable) {
		cprfs.setClaimCheckFlag(true);
		cprfs.setStateCode("ENABLED");
		return claimPolicyRewardService.multiConditionSearchClaimChecked(cprfs, pageable);
	}
	
	/**
	 * 总部收益导出表单
	 * @param response
	 * @param cprfs
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadHeadquarterClaim", method = RequestMethod.GET)
	public void downloadHeadquarterClaim(HttpServletResponse response, ClaimPolicyRewardForSearch cprfs) throws Exception {
		String title = "结果保单";
		cprfs.setDepartmentId(null);
		cprfs.setClaimCheckFlag(true);
		cprfs.setStateCode("ENABLED");
		List<ClaimPolicyReward> dataSet = claimPolicyRewardService.searchForDownload(cprfs);
		String[] headers = { "保险公司", "签单日期", "保单号", "投保人", "被保人", "车主身份证号", "车牌", "险种名称", "含税保费", "不含税保费", "手续费率",
				"已付手续费", "车船税", "车船税返还", "附加费", "归属部门", "代理人", "管理费用", "收益", "上传时间", "认领时间" };
		String[] codes = { "insuraceCompany", "signDate", "policyNumber", "insurApplicant", "insuredPerson",
				"carOwnerIdCard", "carLicense", "insuraceType", "insuraceFeeIncludeTax", "insuraceFeeExcludeTax",
				"brokerFeeRate", "receivedBrokerFee", "vehicleTaxFee", "returnVehicleTaxFee", "additionalFee",
				"departmentLabel", "salesmanName", "managementFee", "profit", "uploadTime", "claimTime" };
		ExcelUtil.downloadExcel(title, headers, codes, ClaimPolicyReward.class, dataSet, response);
	}

	/** for 仪表盘
	 * 按条件 计算搜索出订单收益/收入
	 * @param cprf
	 * @return
	 */
	@PostMapping("/countProfit")
	public ResponseBody countAccumulatedProfit(@RequestBody ClaimPolicyRewardForSearch cprf) {
		Map<String, Object> returnData = claimPolicyRewardService.countAccumulatedProfit(cprf);
		return new ResponseBody(returnData);
	}
	
	/**
	 * 删除保单数据（更改为删除状态）
	 * @param id
	 * @return
	 */
	@GetMapping("/delete")
	public ResponseBody delete(Integer id) {
		claimPolicyRewardService.delete(id);
		try {
		} catch (Exception e) {
			throw new FailReturnObject(205, "删除失败");
		}
		return new ResponseBody("删除成功");
	}
	
	/**
	 * TODO
	 * 编辑修改接口
	 * @param cprfs
	 * @return
	 */
	public ResponseBody update(ClaimPolicyRewardForSearch cprfs) {
		claimPolicyRewardService.update(cprfs);
		return null;
	}
	
	private Map<String, Integer> getPageData(Page<ClaimPolicyReward> page){
		Map<String, Integer> pageDataMap = new HashMap<>();
		pageDataMap.put("size", page.getSize());
		pageDataMap.put("totalElements", (int) page.getTotalElements());
		pageDataMap.put("totalPages", page.getTotalPages());
		pageDataMap.put("number", page.getNumber());
		return pageDataMap;
	}
	
	
	
	
	
	
//	//replaced with multiConditionSearchClaimChecked
//	/**
//	 * 按条件 计算当前登录人员所属部门 总佣金
//	 * @param cprf
//	 * @return
//	 */
//	@PostMapping("/departmentCommission")
//	public ResponseBody findDepartmentAccumulatedCommission(@RequestBody ClaimPolicyRewardForSearch cprf) {
//		if (cprf.getDepartmentId() == null) {
//			throw new FailReturnObject(ExceptionResultEnum.CLAIMPOLICY_COMMISSION_COUNT_ERROR);
//		}
//		Double departmentCommission = claimPolicyRewardService.findDepartmentAccumulatedCommission(cprf);
//		return new ResponseBody(departmentCommission);
//	}


//	/**
//	 * 累计已审核车船税金额（返还）
//	 * 
//	 * @param cprfs
//	 * @return
//	 */
//	@PostMapping("/totalVehicleFee")
//	public ResponseBody findTotalVehicleFee(@RequestBody(required = false) ClaimPolicyRewardForSearch cprfs) {
//		Map<String, Double> returnMap = claimPolicyRewardService.mulConCalculateVehicle(cprfs);
//		return new ResponseBody(returnMap);
//	}

	
	
	
	
	
   
}








