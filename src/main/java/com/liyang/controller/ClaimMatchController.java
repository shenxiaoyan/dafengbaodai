package com.liyang.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.liyang.domain.claimmatch.ClaimMatch;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.service.ClaimMatchService;
import com.liyang.util.CommonUtil;
import com.liyang.util.ExcelUtil;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/dafeng/claimMatch")
public class ClaimMatchController {
	
	@Autowired
	ClaimMatchService claMatService;
	
	/**
	 * 下载认领匹配模版
	 * @param response
	 */
	@RequestMapping(value="/downloadModule", method=RequestMethod.GET)
	public void downloadPolicyModel(HttpServletResponse response) throws Exception{
		String fileName="认领匹配模版";
		String[] headers = { "保险公司","签单日期","车牌","被保人","商业险费用","交强险费用","车船税费用","归属部门","代理人","商业险返佣","交强险返佣","车船税返佣"};
		ExcelUtil.downloadExcel(fileName, headers, null, null, null, response);
	}
	
	/**
	 * 生成token，放入session， 防止excel重复提交
	 * @return
	 */
	@GetMapping("/getImportToken")
	public ResponseBody getToken(HttpServletRequest request) {
		String excelImportToken = UUID.randomUUID().toString();
		request.getSession().setAttribute("excelImportToken", excelImportToken);
		return new ResponseBody(excelImportToken);
	}
	
	/**
	 * 判断browser提交token与服务器上token是否一致
	 * @param request
	 * @param importToken
	 * @return
	 */
	private boolean isRepeatSubmit(HttpServletRequest request, String importToken) {
		if (importToken == null) {
			return true;
		}
		String serverToken = (String) request.getSession().getAttribute("excelImportToken");
		if (serverToken == null) {
			return true;
		}
		if (!importToken.equals(serverToken)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 批量导入认领匹配表单
	 * @param request
	 * @param file
	 * @return 匹配成功及失败条数
	 */
	@RequestMapping("/import/{excelImportToken}")
	public ResponseBody doBatchMatch(HttpServletRequest request, @RequestParam("file")MultipartFile	file, @PathVariable String excelImportToken) {
		if (file == null) {
			throw new FailReturnObject(ExceptionResultEnum.CLAIMPOLICY_DATA_IMPORT_ERROR);
		}
		boolean isRepeat = isRepeatSubmit(request, excelImportToken);
		if (isRepeat) {
			throw new FailReturnObject(ExceptionResultEnum.CLAIMMATCH_SUBMIT_REPEAT_ERROR);
		}
		request.getSession().removeAttribute("excelImportToken");
		String result = claMatService.inportAndMatch(file);
		return new ResponseBody(result);
	}
	
	/**
	 * 导出当前用户上次导入记录中全部未成功匹配数据
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("/downloadUnmatched")
	public void downloadUnmatched(HttpServletResponse response) throws Exception{
		String title = "未成功匹配数据";
		List<ClaimMatch> dataSet = claMatService.findByCreatedBy(CommonUtil.getPrincipal());
		String[] headers = { "保险公司","签单日期","车牌","被保人","商业险费用","交强险费用","车船税费用","归属部门","代理人","商业险返佣","交强险返佣","车船税返佣","匹配失败原因"};
		String[] codes = { "insuranceCompany", "signDate", "carLicense", "insuredPerson", "commercialFee","compulsoryFee","vehicleFee",
				"departmentName","salesmanName", "commercialCommission","compulsoryCommission", "vehicleCommission","msg"};
		ExcelUtil.downloadExcel(title, headers, codes, ClaimMatch.class, dataSet, response);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}