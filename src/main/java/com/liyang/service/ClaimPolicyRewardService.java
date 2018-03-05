package com.liyang.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jpa.query.expression.QueryExpSpecificationBuilder;
import com.jpa.query.expression.generic.GenericQueryExpSpecification;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.claimPolicyRewards.ClaimPolicyReward;
import com.liyang.domain.claimPolicyRewards.ClaimPolicyRewardAct;
import com.liyang.domain.claimPolicyRewards.ClaimPolicyRewardActRepository;
import com.liyang.domain.claimPolicyRewards.ClaimPolicyRewardForSearch;
import com.liyang.domain.claimPolicyRewards.ClaimPolicyRewardLog;
import com.liyang.domain.claimPolicyRewards.ClaimPolicyRewardLogRepository;
import com.liyang.domain.claimPolicyRewards.ClaimPolicyRewardRepository;
import com.liyang.domain.claimPolicyRewards.ClaimPolicyRewardState;
import com.liyang.domain.claimPolicyRewards.ClaimPolicyRewardStateRepository;
import com.liyang.domain.department.Department;
import com.liyang.domain.department.DepartmentRepository;
import com.liyang.domain.jiandenonlocalidcard.NonlocalIdcardRepository;
import com.liyang.domain.salesman.Salesman;
import com.liyang.domain.salesman.SalesmanRepository;
import com.liyang.domain.salesman.SalesmanState;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.util.CommonUtil;
import com.liyang.util.DateUtil;
import com.liyang.util.ExcelUtil;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
@Service
public class ClaimPolicyRewardService extends
		AbstractAuditorService<ClaimPolicyReward, ClaimPolicyRewardState, ClaimPolicyRewardAct, ClaimPolicyRewardLog> {

	@Autowired
	private ClaimPolicyRewardRepository claPolRewRepository;
	@Autowired
	private ClaimPolicyRewardStateRepository stateRepository;
	@Autowired
	private ClaimPolicyRewardActRepository actRepository;
	@Autowired
	ClaimPolicyRewardStateRepository cprStateRepository;
	@Autowired
	private ClaimPolicyRewardLogRepository logRepository;
	@Autowired
	private SalesmanRepository salesmanRepository;
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	NonlocalIdcardRepository nonlocIdcardRepository;
	
	/*
	 * 多条件查询，支持分页
	 */
	public Page<ClaimPolicyReward> multiConditionsearch(ClaimPolicyRewardForSearch cprfs, Pageable pageable) {
		if (cprfs == null) {
			cprfs = new ClaimPolicyRewardForSearch();
		}
		if (cprfs.getSalesmanName() != null && cprfs.getDepartmentId() == null) {
			throw new FailReturnObject(209, "代理人部门为空，请先选择代理人部门");
		}
		// 设置签单日期筛选值
		if (cprfs.getSignBegin() != null || cprfs.getSignEnd() != null) {
			if (cprfs.getSignBegin() == null) {
				cprfs.setSignBegin(DateUtil.getLongBeginTime());
			}
			if (cprfs.getSignEnd() == null) {
				cprfs.setSignEnd(DateUtil.getLongEndTime());
			} else {
				cprfs.setSignEnd(DateUtil.getDayEnd(cprfs.getSignEnd()));
			}
		}
		// 设置认领日期筛选值
		if (cprfs.getClaimBegin() != null || cprfs.getClaimEnd() != null) {
			if (cprfs.getClaimBegin() == null) {
				cprfs.setClaimBegin(DateUtil.getLongBeginTime());
			}
			if (cprfs.getClaimEnd() == null) {
				cprfs.setClaimEnd(DateUtil.getLongEndTime());
			} else {
				cprfs.setClaimEnd(DateUtil.getDayEnd(cprfs.getClaimEnd()));
			}
		}
		GenericQueryExpSpecification<ClaimPolicyReward> queryExpression = new GenericQueryExpSpecification<ClaimPolicyReward>();
		queryExpression.add(QueryExpSpecificationBuilder.eq("carLicense", cprfs.getCarLicense(), true))// 车牌
				.add(QueryExpSpecificationBuilder.eq("policyNumber", cprfs.getPolicyNumber(), true))// 保单号
				.add(QueryExpSpecificationBuilder.eq("insurApplicant", cprfs.getInsurApplicant(), true))// 投保人
				.add(QueryExpSpecificationBuilder.eq("insuredPerson", cprfs.getInsuredPerson(), true))// 被保人
				.add(QueryExpSpecificationBuilder.like("insuraceCompany", cprfs.getInsuraceCompany(), true))// 保险公司名
				.add(QueryExpSpecificationBuilder.like("insuraceType", cprfs.getInsuraceType(), true))// 险种名
				.add(QueryExpSpecificationBuilder.gte("signDate", cprfs.getSignBegin(), true))// 签单日期起始
				.add(QueryExpSpecificationBuilder.lte("signDate", cprfs.getSignEnd(), true))// 签单日期终止
				.add(QueryExpSpecificationBuilder.eq("returnVehicleTaxFeeCheckFlag",
						cprfs.getReturnVehicleTaxFeeCheckFlag(), true))// 车船税返还确认flag
				.add(QueryExpSpecificationBuilder.eq("additionalFeeCheckFlag", cprfs.getAdditionalFeeCheckFlag(), true))// 附加费确认flag
				.add(QueryExpSpecificationBuilder.eq("claimFlag", cprfs.getClaimFlag(), true))// 认领flag
				.add(QueryExpSpecificationBuilder.gte("claimTime", cprfs.getClaimBegin(), true))// 认领时间起始
				.add(QueryExpSpecificationBuilder.lte("claimTime", cprfs.getClaimEnd(), true))// 认领时间终止
				.add(QueryExpSpecificationBuilder.eq("department.id", cprfs.getDepartmentId(), true))// 认领部门Id
				.add(QueryExpSpecificationBuilder.eq("salesman.name", cprfs.getSalesmanName(), true))// 代理人姓名
				.add(QueryExpSpecificationBuilder.eq("claimCheckFlag", cprfs.getClaimCheckFlag(), true))// 认领复核flag
				.add(QueryExpSpecificationBuilder.eq("state.stateCode", cprfs.getStateCode(), true));// 保单状态
		Page<ClaimPolicyReward> cprPage = claPolRewRepository.findAll(queryExpression, pageable);
		cutOffCycle(cprPage);
		return cprPage;
	}
	
	/*
	 * 多条件查询，for download
	 */
	public List<ClaimPolicyReward> multiConditionSearchForDownload(ClaimPolicyRewardForSearch cprfs, Sort sort) {
		// 设置签单日期筛选值
		if (cprfs.getSignBegin() != null || cprfs.getSignEnd() != null) {
			if (cprfs.getSignBegin() == null) {
				cprfs.setSignBegin(DateUtil.getLongBeginTime());
			}
			if (cprfs.getSignEnd() == null) {
				cprfs.setSignEnd(DateUtil.getLongEndTime());
			} else {
				cprfs.setSignEnd(DateUtil.getDayEnd(cprfs.getSignEnd()));
			}
		}
		// 设置认领日期筛选值
		if (cprfs.getClaimBegin() != null || cprfs.getClaimEnd() != null) {
			if (cprfs.getClaimBegin() == null) {
				cprfs.setClaimBegin(DateUtil.getLongBeginTime());
			}
			if (cprfs.getClaimEnd() == null) {
				cprfs.setClaimEnd(DateUtil.getLongEndTime());
			} else {
				cprfs.setClaimEnd(DateUtil.getDayEnd(cprfs.getClaimEnd()));
			}
		}
		GenericQueryExpSpecification<ClaimPolicyReward> queryExpression = new GenericQueryExpSpecification<ClaimPolicyReward>();
		queryExpression.add(QueryExpSpecificationBuilder.eq("carLicense", cprfs.getCarLicense(), true))// 车牌
				.add(QueryExpSpecificationBuilder.eq("policyNumber", cprfs.getPolicyNumber(), true))// 保单号
				.add(QueryExpSpecificationBuilder.eq("insurApplicant", cprfs.getInsurApplicant(), true))// 投保人
				.add(QueryExpSpecificationBuilder.eq("insuredPerson", cprfs.getInsuredPerson(), true))// 被保人
				.add(QueryExpSpecificationBuilder.like("insuraceCompany", cprfs.getInsuraceCompany(), true))// 保险公司名
				.add(QueryExpSpecificationBuilder.like("insuraceType", cprfs.getInsuraceType(), true))// 险种名
				.add(QueryExpSpecificationBuilder.gte("signDate", cprfs.getSignBegin(), true))// 签单日期起始
				.add(QueryExpSpecificationBuilder.lte("signDate", cprfs.getSignEnd(), true))// 签单日期终止
				.add(QueryExpSpecificationBuilder.eq("returnVehicleTaxFeeCheckFlag",
						cprfs.getReturnVehicleTaxFeeCheckFlag(), true))// 车船税返还确认flag
				.add(QueryExpSpecificationBuilder.eq("additionalFeeCheckFlag", cprfs.getAdditionalFeeCheckFlag(), true))// 附加费确认flag
				.add(QueryExpSpecificationBuilder.eq("claimFlag", cprfs.getClaimFlag(), true))// 认领flag
				.add(QueryExpSpecificationBuilder.gte("claimTime", cprfs.getClaimBegin(), true))// 认领时间起始
				.add(QueryExpSpecificationBuilder.lte("claimTime", cprfs.getClaimEnd(), true))// 认领时间终止
				.add(QueryExpSpecificationBuilder.eq("department.id", cprfs.getDepartmentId(), true))// 认领部门Id
				.add(QueryExpSpecificationBuilder.eq("salesman.name", cprfs.getSalesmanName(), true))// 代理人姓名
				.add(QueryExpSpecificationBuilder.eq("claimCheckFlag", cprfs.getClaimCheckFlag(), true))// 认领复核flag
				.add(QueryExpSpecificationBuilder.eq("state.stateCode", cprfs.getStateCode(), true));// 保单状态
		List<ClaimPolicyReward> claPolList = claPolRewRepository.findAll(queryExpression, sort);
		return claPolList;
	}

	/**
	 * 设置null，暂解决死循环
	 * 
	 * @param page
	 * @return
	 */
	private Page<ClaimPolicyReward> cutOffCycle(Page<ClaimPolicyReward> page) {
		for (ClaimPolicyReward cpr : page) {
			if (cpr.getDepartment() != null) {
				String label = cpr.getDepartment().getLabel();
				Integer id = cpr.getDepartment().getId();
				cpr.setDepartment(null);
				Department dept = new Department();
				dept.setId(id);
				dept.setLabel(label);
				cpr.setDepartment(dept);
			}
			cpr.setState(null);
			cpr.setCreatedByDepartment(null);
			if (cpr.getSalesman() != null) {
				cpr.getSalesman().setDepartment(null);
				if (cpr.getSalesman().getState() != null) {
					SalesmanState state = new SalesmanState();
					state.setStateCode(cpr.getSalesman().getStateCode());
					cpr.getSalesman().setState(state);
				}
				cpr.getSalesman().setCreatedByDepartment(null);
			}
		}
		return page;
	}

	/**
	 * 获取page分页信息
	 * 
	 * @param page
	 * @return
	 */
	private Map<String, Integer> getPageData(Page<ClaimPolicyReward> page) {
		Map<String, Integer> pageDataMap = new HashMap<>();
		pageDataMap.put("size", page.getSize());
		pageDataMap.put("totalElements", (int) page.getTotalElements());
		pageDataMap.put("totalPages", page.getTotalPages());
		pageDataMap.put("number", page.getNumber());
		return pageDataMap;
	}

	public List<ClaimPolicyReward> searchForDownload(ClaimPolicyRewardForSearch cprfs) {
		Sort sort = new Sort(Direction.DESC, "claimTime");
		// TODO 暂设置1000万条，待寻找更好方法
//		Pageable pageable = new PageRequest(0, 10000000, sort);
		List<ClaimPolicyReward> list = multiConditionSearchForDownload(cprfs, sort);
		return list;
	}
	// List<ClaimPolicyReward> data = claPolRewRepository.findAll(new
	// Specification<ClaimPolicyReward>() {
	// @Override
	// public Predicate toPredicate(Root<ClaimPolicyReward> root,
	// CriteriaQuery<?> query, CriteriaBuilder cb) {
	// List<Predicate> predicatesList = new ArrayList<Predicate>();
	// if (cprfs != null) {
	// if (cprfs.getDepartmentId() != null) {
	// Predicate departmentIdEq = cb.equal(root.get("department").get("id"),
	// cprfs.getDepartmentId());
	// predicatesList.add(departmentIdEq);
	// }
	// //认领时间筛选
	// if ((cprfs.getClaimBegin()!= null) && (cprfs.getClaimEnd() != null)) {
	// Date endTime = new Date(cprfs.getClaimEnd().getTime()+24*3600*1000);
	// Predicate claimTimeBetween = cb.between(root.<Date>get("claimTime"),
	// cprfs.getClaimBegin(), endTime);
	// predicatesList.add(claimTimeBetween);
	// }
	// //签单时间筛选
	// if ((cprfs.getSignBegin()!= null) && (cprfs.getSignEnd() != null)) {
	// Date endTime = new Date(cprfs.getSignEnd().getTime()+24*3600*1000-1);
	// Predicate signDateBetween = cb.between(root.<Date>get("signDate"),
	// cprfs.getSignBegin(), endTime);
	// predicatesList.add(signDateBetween);
	// }
	// if (!StringUtils.isEmpty(cprfs.getInsuraceCompany())) {
	// Predicate insuraceCompanyEq = cb.equal(root.get("insuraceCompany"),
	// cprfs.getInsuraceCompany());
	// predicatesList.add(insuraceCompanyEq);
	// }
	// if (cprfs.getClaimFlag() != null) {
	// Predicate claimFlagEq = cb.equal(root.get("claimFlag"),
	// cprfs.getClaimFlag());
	// predicatesList.add(claimFlagEq);
	// }
	// if (cprfs.getClaimCheckFlag() != null) {
	// Predicate claimCheckFlagEq = cb.equal(root.get("claimCheckFlag"),
	// cprfs.getClaimCheckFlag());
	// predicatesList.add(claimCheckFlagEq);
	// }
	// }
	// query.where(predicatesList.toArray(new
	// Predicate[predicatesList.size()]));
	// return null;
	// }
	// }, sort);

	public Page<ClaimPolicyReward> multiConditionsearchUnconfirmed(ClaimPolicyRewardForSearch cprfs,
			Pageable pageable) {
		// Page<ClaimPolicyReward> cprPage = claPolRewRepository.findAll(new
		// Specification<ClaimPolicyReward>() {
		// @Override
		// public Predicate toPredicate(Root<ClaimPolicyReward> root,
		// CriteriaQuery<?> query, CriteriaBuilder cb) {
		// List<Predicate> predicatesList = new ArrayList<Predicate>();
		// // 车牌
		// Predicate carLicenseEqual = null;
		// if (null != cprfs && !StringUtils.isEmpty(cprfs.getCarLicense())) {
		// carLicenseEqual = cb.equal(root.<String>get("carLicense"),
		// cprfs.getCarLicense());
		// }
		// if (null != carLicenseEqual) {
		// predicatesList.add(carLicenseEqual);
		// }
		// // 保单号
		// Predicate policyNumberEqual = null;
		// if (null != cprfs && !StringUtils.isEmpty(cprfs.getPolicyNumber())) {
		// policyNumberEqual = cb.equal(root.<String>get("policyNumber"),
		// cprfs.getPolicyNumber());
		// }
		// if (null != policyNumberEqual) {
		// predicatesList.add(policyNumberEqual);
		// }
		// // 投保人
		// Predicate insurApplicantEqual = null;
		// if (null != cprfs && !StringUtils.isEmpty(cprfs.getInsurApplicant()))
		// {
		// insurApplicantEqual = cb.equal(root.<String>get("insurApplicant"),
		// cprfs.getInsurApplicant());
		// }
		// if (null != insurApplicantEqual) {
		// predicatesList.add(insurApplicantEqual);
		// }
		// // 被保人
		// Predicate insuredPersonEqual = null;
		// if (null != cprfs && !StringUtils.isEmpty(cprfs.getInsuredPerson()))
		// {
		// insuredPersonEqual = cb.equal(root.<String>get("insuredPerson"),
		// cprfs.getInsuredPerson());
		// }
		// if (null != insuredPersonEqual) {
		// predicatesList.add(insuredPersonEqual);
		// }
		// // 保险公司
		// Predicate insuraceCompanyLike = null;
		// if (null != cprfs &&
		// !StringUtils.isEmpty(cprfs.getInsuraceCompany())) {
		// insuraceCompanyLike = cb.like(root.<String>get("insuraceCompany"),
		// "%" + cprfs.getInsuraceCompany() + "%");
		// }
		// if (null != insuraceCompanyLike) {
		// predicatesList.add(insuraceCompanyLike);
		// }
		// // 有效状态中搜索
		// Predicate stateCodeEq = cb.equal(root.get("state").get("stateCode"),
		// "ENABLED");
		// predicatesList.add(stateCodeEq);
		// // 从未确认中搜索
		// Predicate vehicalCheckFlagFalse =
		// cb.isFalse(root.<Boolean>get("returnVehicleTaxFeeCheckFlag"));
		// Predicate additionalCheckFlag =
		// cb.isFalse(root.get("additionalFeeCheckFlag"));
		// Predicate unconfirmed = cb.or(vehicalCheckFlagFalse,
		// additionalCheckFlag);
		// predicatesList.add(unconfirmed);
		// query.where(predicatesList.toArray(new
		// Predicate[predicatesList.size()]));
		// return null;
		// }
		// }, pageable);
		// cutOffCycle(cprPage);
		// return cprPage;

		GenericQueryExpSpecification<ClaimPolicyReward> queryExpression = new GenericQueryExpSpecification<>(
				new String[] { "id", "carLicense", "policyNumber", "insurApplicant", "insuredPerson", "insuraceCompany",
						"state", "returnVehicleTaxFeeCheckFlag","claimFlag", "additionalFeeCheckFlag",
						"createdAt", "lastModifiedAt" });
		queryExpression.add(QueryExpSpecificationBuilder.eq("id", cprfs.getId(), true))
				.add(QueryExpSpecificationBuilder.eq("carLicense", cprfs.getCarLicense(), true))
				.add(QueryExpSpecificationBuilder.eq("policyNumber", cprfs.getPolicyNumber(), true))
				.add(QueryExpSpecificationBuilder.eq("insurApplicant", cprfs.getInsurApplicant(), true))
				.add(QueryExpSpecificationBuilder.eq("insuredPerson", cprfs.getInsuredPerson(), true))
				.add(QueryExpSpecificationBuilder.eq("insuraceCompany", cprfs.getInsuraceCompany(), true))
				.add(QueryExpSpecificationBuilder.eq("state.stateCode", cprfs.getStateCode(), true))
				.add(QueryExpSpecificationBuilder.eq("returnVehicleTaxFeeCheckFlag",
						cprfs.getReturnVehicleTaxFeeCheckFlag(), true))
				.add(QueryExpSpecificationBuilder.eq("claimFlag", cprfs.getClaimFlag(), true))
				.add(QueryExpSpecificationBuilder.eq("additionalFeeCheckFlag", cprfs.getAdditionalFeeCheckFlag(), true));


		Page<ClaimPolicyReward> claimPolicyRewardPage = claPolRewRepository.findAll(queryExpression, pageable);

		return claimPolicyRewardPage;
	}

	public String batchImport(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		InputStream is = null;
		String errorMsg = "";
		try {
			is = file.getInputStream();
			Workbook wb = ExcelUtil.createWorkbook(is, fileName);
			FormulaEvaluator formulaEvaluator = ExcelUtil.createFormulaEvaluator(wb);
			errorMsg = readExcelValue(wb, formulaEvaluator);
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new FailReturnObject(ExceptionResultEnum.EXCEL_WORKBOOK_ANALIZE_ERROR);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
					is = null;
				}
			}
		}
		return errorMsg;
	}

	private String readExcelValue(Workbook wb, FormulaEvaluator formulaEvaluator) {
		Date uploadTime = new Date();
		List<ClaimPolicyReward> claimPolicyRewardList = new ArrayList<ClaimPolicyReward>();
		List<String> nonlocalList = nonlocIdcardRepository.findAllIdcardNumber();
		if (wb.getNumberOfSheets() < 1) {
			throw new FailReturnObject(ExceptionResultEnum.EXCEL_SHEET_ANALIZE_ERROR);
		}
		int totalNum = 0;
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			totalNum += wb.getSheetAt(i).getPhysicalNumberOfRows();
		}
		if ((totalNum - wb.getNumberOfSheets()) > 10000) {
			throw new FailReturnObject(ExceptionResultEnum.EXCEL_UPPER_LIMIT_ERROR);
		}
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			Sheet sheet = wb.getSheetAt(i);
			if (sheet != null) {
				int totalRows = sheet.getPhysicalNumberOfRows();
				int totalCells = 0;
				if (totalRows >= 2 && sheet.getRow(1) != null) {
					totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
					if (totalCells != 14) {
						throw new FailReturnObject(ExceptionResultEnum.EXCEL_MODULE_ANALIZE_ERROR);
					}
				}
				ClaimPolicyReward claimPolicyReward;
				// 保险公司
				String insuraceCompany;
				// 签单日期
				Date signDate;
				// 保单号
				String policyNumber = null;
				// 投保人
				String insurApplicant = null;
				// 被保人
				String insuredPerson = null;
				// 车主身份证
				String carOwnerIdCard = null;
				// 车牌
				String carLicense = null;
				// 险种名称
				String insuraceType = null;
				// 含税保费
				Double insuraceFeeIncludeTax;
				// 不含税保费
				Double insuraceFeeExcludeTax;
				// 手续费率
				Double brokerFeeRate;
				// 已收手续费L
				Double receivedBrokerFee = null;
				// 车船税
				Double vehicleTaxFee = null;
				// 附加费
				Double additionalFee;
				// 循环行数,从第二行开始。标题不入库
				for (int r = 1; r < totalRows; r++) {
					Row row = sheet.getRow(r);
					if (row == null) {
						continue;
					}
					claimPolicyReward = new ClaimPolicyReward();
					claimPolicyReward.setUploadTime(uploadTime);
					for (int c = 0; c <= totalCells; c++) {
						Cell cell = row.getCell(c);
						// if (null != cell) {
						// }
						if (c == 0) {
							try {
								insuraceCompany = ExcelUtil.getNotBlankStringCellValue(cell);
								claimPolicyReward.setInsuraceCompany(insuraceCompany);
							} catch (Exception e) {
								throw new FailReturnObject(421, sheet.getSheetName() + "第" + (r + 1) + "行，第" + (c + 1) + "列，" + e.getMessage());
							}
						} else if (c == 1) {
							signDate = ExcelUtil.getDateCellValue(cell);
							if (null == signDate) {
								throw new FailReturnObject(100, sheet.getSheetName() + "第" + (r + 1) + "行'签单日期'为空");
							}
							claimPolicyReward.setSignDate(signDate);
						} else if (c == 2) {
							try {
								policyNumber = ExcelUtil.getNotBlankStringCellValue(cell);
							} catch (Exception e) {
								throw new FailReturnObject(421, sheet.getSheetName() + "第" + (r + 1) + "行，第" + (c + 1) + "列，" + e.getMessage());
							}
							claimPolicyReward.setPolicyNumber(policyNumber);
						} else if (c == 3) {
							insurApplicant = ExcelUtil.getStringCellValue(cell);
							claimPolicyReward.setInsurApplicant(insurApplicant);
						} else if (c == 4) {
							insuredPerson = ExcelUtil.getStringCellValue(cell);
							claimPolicyReward.setInsuredPerson(insuredPerson);
						} else if (c == 5) {
							carOwnerIdCard = ExcelUtil.getStringCellValue(cell);
							claimPolicyReward.setCarOwnerIdCard(carOwnerIdCard);
						} else if (c == 6) {
							try {
								carLicense = ExcelUtil.getNotBlankStringCellValue(cell);
							} catch (Exception e) {
								throw new FailReturnObject(421, sheet.getSheetName() + "第" + (r + 1) + "行，第" + (c + 1) + "列，" + e.getMessage());
							}
							claimPolicyReward.setCarLicense(carLicense);
						} else if (c == 7) {
							try {
								insuraceType = ExcelUtil.getNotBlankStringCellValue(cell);
							} catch (Exception e) {
								throw new FailReturnObject(421, sheet.getSheetName() + "第" + (r + 1) + "行，第" + (c + 1) + "列，" + e.getMessage());
							}
							claimPolicyReward.setInsuraceType(insuraceType);
						} else if (c == 8) {
							insuraceFeeIncludeTax = ExcelUtil.getDoubleCellValue(cell, formulaEvaluator);
							claimPolicyReward.setInsuraceFeeIncludeTax(insuraceFeeIncludeTax);
						} else if (c == 9) {
							insuraceFeeExcludeTax = ExcelUtil.getDoubleCellValue(cell, formulaEvaluator);
							claimPolicyReward.setInsuraceFeeExcludeTax(insuraceFeeExcludeTax);
						} else if (c == 10) {
							brokerFeeRate = ExcelUtil.getDoubleCellValue(cell, formulaEvaluator);
							claimPolicyReward.setBrokerFeeRate(brokerFeeRate);
						} else if (c == 11) {
							if (CellType.BLANK.equals(cell.getCellTypeEnum())) {
								throw new FailReturnObject(421, "导入失败，第" + (r + 1) + "行手续费为空，请检查！");
							}
							receivedBrokerFee = ExcelUtil.getDoubleCellValue(cell, formulaEvaluator);
							claimPolicyReward.setReceivedBrokerFee(receivedBrokerFee);
						} else if (c == 12) {
							vehicleTaxFee = ExcelUtil.getDoubleCellValue(cell, formulaEvaluator);
							claimPolicyReward.setVehicleTaxFee(vehicleTaxFee);
						} else if (c == 13) {
							additionalFee = ExcelUtil.getDoubleCellValue(cell, formulaEvaluator);
							claimPolicyReward.setAdditionalFee(additionalFee);
						}
					}
					if ("商业险".equals(insuraceType)) {
						claimPolicyReward.setReturnVehicleTaxFee(new Double(0));
						claimPolicyReward.setReturnVehicleTaxFeeCheckFlag(true);
						claimPolicyReward.setVehicleCommission(new Double(0));
						claimPolicyReward.setReturnVehicleTaxFeeCheckTime(uploadTime);
					} else {
						if ((carOwnerIdCard != null && (carOwnerIdCard.startsWith("330182") || carOwnerIdCard.startsWith("330126")))
								|| nonlocalList.contains(carOwnerIdCard) || carLicense.startsWith("浙AE")) {
							claimPolicyReward.setReturnVehicleTaxFee(new Double(0));
						} else {
							BigDecimal b = new BigDecimal(vehicleTaxFee * 0.8);
							claimPolicyReward.setReturnVehicleTaxFee(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
						}
						claimPolicyReward.setAdditionalFee(new Double(0));
						claimPolicyReward.setAdditionalFeeCheckFlag(true);
						claimPolicyReward.setAdditionalFeeCheckTime(uploadTime);
					}
					if (policyNumber != null && receivedBrokerFee != null) {
						//TODO 不要每次打开数据库连接查询，直接最终用list查询，提高效率
						ClaimPolicyReward claPolReward = claPolRewRepository.findByPolicyNumberAndReceivedBrokerFee(policyNumber, receivedBrokerFee);
						if (claPolReward != null) {
							throw new FailReturnObject(100, "相同手续费的保单号(" + policyNumber + ")已存在，请检查");
						}
						claimPolicyRewardList.add(claimPolicyReward);
					}
				}
			}
		}
		ClaimPolicyRewardState enableState = stateRepository.findByStateCode("ENABLED");
		List<ClaimPolicyReward> tempList = new ArrayList<>();
		for (ClaimPolicyReward cpr : claimPolicyRewardList) {
			if (tempList.contains(cpr)) {
				throw new FailReturnObject(100, "导入失败，导入表单中存在保单号（" + cpr.getPolicyNumber() + "）与手续费（"
						+ cpr.getReceivedBrokerFee() + "）完全的记录，请检查");
			}
			tempList.add(cpr);
			cpr.setState(enableState);
		}
		claPolRewRepository.save(claimPolicyRewardList);
		return "成功导入" + claimPolicyRewardList.size() + "条数据";
	}

	
	@Transactional
	public void checkAdditionOrRetVehicle(ClaimPolicyRewardForSearch cprfs) {
		ClaimPolicyReward cpr = claPolRewRepository.findOne(cprfs.getId());
		if (cprfs.getAdditionalFee() != null) {
			cpr.setAdditionalFee(cprfs.getAdditionalFee());
			cpr.setAdditionalFeeCheckFlag(true);
			cpr.setAdditionalFeeCheckTime(new Date());
		} else if (cprfs.getReturnVehicleTaxFee() != null) {
			cpr.setVehicleTaxFee(cprfs.getReturnVehicleTaxFee());
			cpr.setReturnVehicleTaxFeeCheckFlag(true);
			cpr.setReturnVehicleTaxFeeCheckTime(new Date());
		} else {
			throw new FailReturnObject(100, ExceptionResultEnum.CLAIMPOLICY_CLAIM_CHECK_ERROR);
		}
		claPolRewRepository.save(cpr);
	}

	@Transactional
	public ClaimPolicyReward updateClaimState(Integer id, Double vehicleCommission, Double insuranceCommission,
			Integer salesmanId) {
		if (null == salesmanId) {
			throw new FailReturnObject(100, "请选择代理人");
		}
		ClaimPolicyReward cpr = claPolRewRepository.findOne(id);
		cpr.setDepartment(CommonUtil.getCurrentUserDepartment());
		cpr.setSalesman(salesmanRepository.findOne(salesmanId));
		cpr.setVehicleCommission(vehicleCommission);
		cpr.setInsuranceCommission(insuranceCommission);
		cpr.setManagementFee(insuranceCommission + vehicleCommission);
		cpr.setClaimFlag(true);
		cpr.setClaimTime(new Date());
		// 计算收益
		cpr.setProfit(cpr.getReceivedBrokerFee() + cpr.getReturnVehicleTaxFee() + cpr.getAdditionalFee()
				- cpr.getVehicleCommission() - cpr.getInsuranceCommission());
		return claPolRewRepository.save(cpr);
	}

	@Transactional
	public ClaimPolicyReward doClaim(ClaimPolicyRewardForSearch cprfs) {
		if (null == cprfs.getSalesmanId()) {
			throw new FailReturnObject(100, "请选择代理人");
		}
		ClaimPolicyReward cpr = claPolRewRepository.findOne(cprfs.getId());
		cpr.setDepartment(CommonUtil.getCurrentUserDepartment());
		cpr.setSalesman(salesmanRepository.findOne(cprfs.getSalesmanId()));
		if (cprfs.getVehicleCommission() == null || cprfs.getInsuranceCommission() == null) {
			throw new FailReturnObject(101, "请传入相关管理费用");
		}
		cpr.setVehicleCommission(cprfs.getVehicleCommission());
		cpr.setInsuranceCommission(cprfs.getInsuranceCommission());
		cpr.setManagementFee(cprfs.getVehicleCommission() + cprfs.getInsuranceCommission());
		cpr.setClaimFlag(true);
		cpr.setClaimTime(new Date());
		// 计算收益
		cpr.setProfit(cpr.getReceivedBrokerFee() + cpr.getReturnVehicleTaxFee() + cpr.getAdditionalFee()
				- cpr.getVehicleCommission() - cpr.getInsuranceCommission());
		return claPolRewRepository.save(cpr);
	}

	/**
	 * 计算总部收益 for仪表盘调用
	 * 
	 * @param cprfs
	 * @return
	 */
	public Map<String, Object> countAccumulatedProfit(ClaimPolicyRewardForSearch cprfs) {
		Map<String, Object> returnMap = new HashMap<>();
		Map<String, Double> headquartersMap = new HashMap<String, Double>();
		List<Map<String, Object>> departmentCommissionList = new ArrayList<>();
		List<Department> salesDepartmentList = departmentRepository.findByTypeLabel("营业");

		if (cprfs.getBeginTime() == null && cprfs.getEndTime() == null) {
			headquartersMap.put("headquartersAccumulatedIncome",
					claPolRewRepository.findHeadquartersAccumulatedIncome());
			headquartersMap.put("headquartersAccumulatedProfit",
					claPolRewRepository.findHeadquartersAccumulatedProfit());
			returnMap.put("headquarter", headquartersMap);
			for (Department department : salesDepartmentList) {
				Map<String, Object> departmentCommissionMap = new HashMap<>();
				departmentCommissionMap.put("departmentLabel", department.getLabel());
				departmentCommissionMap.put("accumulatedCommission",
						claPolRewRepository.findDepartmentAccumulatedCommission(department.getId()));
				departmentCommissionList.add(departmentCommissionMap);
			}
			returnMap.put("departmentsCommission", departmentCommissionList);
			return returnMap;

		} else if (cprfs.getBeginTime() != null && cprfs.getEndTime() != null) {
			headquartersMap.put("headquartersAccumulatedIncome", claPolRewRepository
					.findHeadquartersAccumulatedIncomeBetween(cprfs.getClaimBegin(), cprfs.getClaimEnd()));
			headquartersMap.put("headquartersAccumulatedProfit", claPolRewRepository
					.findHeadquartersAccumulatedProfitBetween(cprfs.getClaimBegin(), cprfs.getClaimEnd()));
			returnMap.put("headquarter", headquartersMap);
			for (Department department : salesDepartmentList) {
				Map<String, Object> departmentCommissionMap = new HashMap<>();
				departmentCommissionMap.put("departmentLabel", department.getLabel());
				departmentCommissionMap.put("accumulatedCommission",
						claPolRewRepository.findDepartmentAccumulatedCommissionBetween(department.getId(),
								cprfs.getClaimBegin(), cprfs.getClaimEnd()));
				departmentCommissionList.add(departmentCommissionMap);
			}
			returnMap.put("departmentsCommission", departmentCommissionList);
			return returnMap;
		} else {
			return null;
		}
	}
	/**
	 *待认领列表
	 *
	 */
	public  Map<String,Object> mulFindUnclaimed(ClaimPolicyRewardForSearch cprfs, Pageable pageable){
		if (cprfs==null){
			cprfs=new ClaimPolicyRewardForSearch();
		}
		Map<String,Object> returnMap=new HashMap<>();
		Page<ClaimPolicyReward> page=multiConditionsearch(cprfs,pageable);
		cutOffCycle(page);
		returnMap.put("claimPolicyReward", page.getContent());
		returnMap.put("page", getPageData(page));

		return  returnMap;
	}

	/**
	 * 审核车船税列表+审核附加费
	 * @param cprfs
	 * @param pageable
	 * @return
	 */
	public Map<String, Object> mulConSearchVehicle(ClaimPolicyRewardForSearch cprfs, Pageable pageable) {
		Map<String,Object> returnMap=new HashMap<>();
		Page<ClaimPolicyReward> page=multiConditionsearch(cprfs,pageable);
		cutOffCycle(page);
		returnMap.put("claimPolicyReward", page.getContent());
		returnMap.put("page", getPageData(page));
		return  returnMap ;
	}

	/**
	 * 已审核车船税列表（搜索）+已审核车船税金额+车船税返还金额
	 * 
	 * @param cprfs
	 * @param pageable
	 * @return
	 */
	public Map<String, Object> mulConSearchVehicleChecked(ClaimPolicyRewardForSearch cprfs, Pageable pageable) {
		Map<String, Object> returnMap = new HashMap<>();
		Page<ClaimPolicyReward> page = multiConditionsearch(cprfs, pageable);
		cutOffCycle(page);
		returnMap.put("claimPolicyReward", page.getContent());
		returnMap.put("page", getPageData(page));
		// 获取所有已审核车船税数据，计算金额
		Page<ClaimPolicyReward> countPage = multiConditionsearch(cprfs, null);
		double totalVehicle = 0;
		double totalReturnVehicle = 0;
		for (ClaimPolicyReward claPloReward : countPage) {
			totalVehicle += claPloReward.getVehicleTaxFee();
			totalReturnVehicle += claPloReward.getReturnVehicleTaxFee();
		}
		returnMap.put("totalVehicle", totalVehicle);
		returnMap.put("totalReturnVehicle", totalReturnVehicle);
		return returnMap;
	}

	/**
	 * 待复核列表+所有待复核
	 * 
	 * @param cprfs
	 * @param pageable
	 * @return
	 */
	public Map<String, Object> mulConSearchCliamUmchecked(ClaimPolicyRewardForSearch cprfs, Pageable pageable) {
		Map<String, Object> returnMap = new HashMap<>();
		Page<ClaimPolicyReward> page = multiConditionsearch(cprfs, pageable);
		cutOffCycle(page);
		returnMap.put("claimPolicyReward", page.getContent());
		returnMap.put("page", getPageData(page));
		Page<ClaimPolicyReward> countPage = multiConditionsearch(cprfs, null);
		double totalCommission = 0;
		for (ClaimPolicyReward cpr : countPage) {
			totalCommission += cpr.getManagementFee();
		}
		returnMap.put("totalCommission", totalCommission);
		return returnMap;
	}

	/**
	 * 营业部已认领列表（搜索）+累计认领佣金
	 * 
	 * @param cprfs
	 * @param pageable
	 * @return
	 */
	public Map<String, Object> mulConSearchDepClaimed(ClaimPolicyRewardForSearch cprfs, Pageable pageable) {
		Map<String, Object> returnMap = new HashMap<>();
		Page<ClaimPolicyReward> page = multiConditionsearch(cprfs, pageable);
		returnMap.put("claimPolicyReward", page.getContent());
		returnMap.put("page", getPageData(page));
		// 获取部门所有认领数据，计算累计佣金
		Page<ClaimPolicyReward> countPage = multiConditionsearch(cprfs, null);
		double departmentCommission = 0;
		for (ClaimPolicyReward cpr : countPage) {
			departmentCommission += cpr.getManagementFee();
		}
		returnMap.put("departmentCommission", departmentCommission);
		return returnMap;
	}

	/**
	 * 总部收益列表（搜索）+收入、收益
	 * 
	 * @param cprfs
	 * @param pageable
	 * @return
	 */
	public Map<String, Object> multiConditionSearchClaimChecked(ClaimPolicyRewardForSearch cprfs, Pageable pageable) {
		Map<String, Object> returnMap = new HashMap<>();
		Page<ClaimPolicyReward> page = multiConditionsearch(cprfs, pageable);
		cutOffCycle(page);
		returnMap.put("claimPolicyReward", page.getContent());
		returnMap.put("page", getPageData(page));
		// 获取所有数据，计算收入收益
		Page<ClaimPolicyReward> countPage = multiConditionsearch(cprfs, null);
		double headquartersAccumulatedIncome = 0;
		double headquartersAccumulatedProfit = 0;
		for (ClaimPolicyReward cpr : countPage) {
			headquartersAccumulatedIncome += (cpr.getReceivedBrokerFee() + cpr.getReturnVehicleTaxFee()
					+ cpr.getAdditionalFee());
			headquartersAccumulatedProfit += cpr.getProfit();
		}
		returnMap.put("headquartersAccumulatedIncome", headquartersAccumulatedIncome);
		returnMap.put("headquartersAccumulatedProfit", headquartersAccumulatedProfit);
		return returnMap;
	}

	// replaced with mulConSearchDepClaimed
	// public Double
	// findDepartmentAccumulatedCommission(ClaimPolicyRewardForSearch cprfs) {
	// if (cprfs.getBeginTime() == null && cprfs.getEndTime() == null &&
	// cprfs.getDepartmentId() != null) {
	// return
	// claPolRewRepository.findDepartmentAccumulatedCommission(cprfs.getDepartmentId());
	// } else if (cprfs.getBeginTime() != null && cprfs.getEndTime() != null &&
	// cprfs.getDepartmentId() != null) {
	// return
	// claPolRewRepository.findDepartmentAccumulatedCommissionBetween(cprfs.getDepartmentId(),
	// cprfs.getClaimBegin(), cprfs.getClaimEnd());
	// } else {
	// return null;
	// }
	// }

	public List<ClaimPolicyReward> findConfirmed() {
		Sort sort = new Sort(Direction.DESC, "signDate");
		Pageable pageable = new PageRequest(0, 100000, sort);
		Page<ClaimPolicyReward> page = claPolRewRepository.findByClaimFlagIsTrueAndClaimCheckFlagIsFalse(pageable);
		return page.getContent();
	}

	// public Page<ClaimPolicyReward>
	// mulConSearchVehicleChecked(ClaimPolicyRewardForSearch cprfs, Pageable
	// pageable) {
	// Page<ClaimPolicyReward> page = claPolRewRepository.findAll(new
	// Specification<ClaimPolicyReward>() {
	//
	// @Override
	// public Predicate toPredicate(Root<ClaimPolicyReward> root,
	// CriteriaQuery<?> query, CriteriaBuilder cb) {
	// List<Predicate> predicateList = new ArrayList<>();
	// Predicate insuranceTypeEqual = cb.notEqual(root.get("insuraceType"),
	// "商业险");
	// Predicate vehicleChecked =
	// cb.isTrue(root.get("returnVehicleTaxFeeCheckFlag"));
	// predicateList.add(insuranceTypeEqual);
	// predicateList.add(vehicleChecked);
	// if (null != cprfs) {
	// Predicate signDatePredicate = null;
	// if (cprfs.getSignEnd() != null) {
	// Date endTime = DateUtil.getDayEnd(cprfs.getSignEnd());
	// if (cprfs.getSignBegin() != null) {
	// Date beginTime = DateUtil.getYesterdayEnd(cprfs.getSignBegin());
	// signDatePredicate = cb.between(root.get("signDate"), beginTime, endTime);
	// }else {
	// signDatePredicate = cb.lessThan(root.get("signDate"), endTime);
	// }
	// }else {
	// if (cprfs.getSignBegin() != null) {
	// signDatePredicate = cb.greaterThan(root.get("signDate"),
	// DateUtil.getYesterdayEnd(cprfs.getSignBegin()));
	// }
	// }
	// if (signDatePredicate != null) {
	// predicateList.add(signDatePredicate);
	// }
	// if (!StringUtils.isEmpty(cprfs.getInsuraceCompany())) {
	// Predicate insCompanyEqual = cb.equal(root.get("insuraceCompany"),
	// cprfs.getInsuraceCompany());
	// predicateList.add(insCompanyEqual);
	// }
	// }
	// query.where(predicateList.toArray(new Predicate[predicateList.size()]));
	// return null;
	// }
	// }, pageable);
	// for (ClaimPolicyReward cpr : page) {
	// if (cpr.getDepartment() != null) {
	// String label = cpr.getDepartment().getLabel();
	// cpr.setDepartment(null);
	// Department dept = new Department();
	// dept.setLabel(label);
	// cpr.setDepartment(dept);
	// }
	// cpr.setState(null);
	// cpr.setCreatedByDepartment(null);
	// if (cpr.getSalesman() != null) {
	// Salesman sales = new Salesman();
	// sales.setName(cpr.getSalesman().getName());
	// cpr.setSalesman(sales);
	// }
	// }
	//
	// return page;
	// }

	public List<ClaimPolicyReward> multiConditionSearchVehicle(ClaimPolicyRewardForSearch cprfs) {
		Sort sort = new Sort(Direction.DESC, "returnVehicleTaxFeeCheckTime");
		List<ClaimPolicyReward> claPolRewardList = claPolRewRepository.findAll(new Specification<ClaimPolicyReward>() {
			@Override
			public Predicate toPredicate(Root<ClaimPolicyReward> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<>();
				Predicate insuranceTypeEqual = cb.notEqual(root.get("insuraceType"), "商业险");
				Predicate vehicleChecked = cb.isTrue(root.get("returnVehicleTaxFeeCheckFlag"));
				predicateList.add(insuranceTypeEqual);
				predicateList.add(vehicleChecked);
				Predicate stateCodeEq = cb.equal(root.get("state").get("stateCode"), "ENABLED");
				predicateList.add(stateCodeEq);
				if (null != cprfs) {
					Predicate signDatePredicate = null;
					if (cprfs.getSignEnd() != null) {
						Date endTime = DateUtil.getDayEnd(cprfs.getSignEnd());
						if (cprfs.getSignBegin() != null) {
							Date beginTime = DateUtil.getYesterdayEnd(cprfs.getSignBegin());
							signDatePredicate = cb.between(root.get("signDate"), beginTime, endTime);
						} else {
							signDatePredicate = cb.lessThan(root.get("signDate"), endTime);
						}
					} else {
						if (cprfs.getSignBegin() != null) {
							signDatePredicate = cb.greaterThan(root.get("signDate"),
									DateUtil.getYesterdayEnd(cprfs.getSignBegin()));
						}
					}
					if (signDatePredicate != null) {
						predicateList.add(signDatePredicate);
					}
					if (!StringUtils.isEmpty(cprfs.getInsuraceCompany())) {
						Predicate insCompanyEqual = cb.equal(root.get("insuraceCompany"), cprfs.getInsuraceCompany());
						predicateList.add(insCompanyEqual);
					}
				}
				query.where(predicateList.toArray(new Predicate[predicateList.size()]));
				return null;
			}
		}, sort);
		return claPolRewardList;
	}

	public Map<String, Double> mulConCalculateVehicle(ClaimPolicyRewardForSearch cprfs) {
		List<ClaimPolicyReward> claPolRewardList = multiConditionSearchVehicle(cprfs);
		double totalVehicle = 0;
		double totalReturnVehicle = 0;
		for (ClaimPolicyReward claPloReward : claPolRewardList) {
			totalVehicle += claPloReward.getVehicleTaxFee();
			totalReturnVehicle += claPloReward.getReturnVehicleTaxFee();
		}
		Map<String, Double> returnMap = new HashMap<>();
		returnMap.put("totalVehicle", totalVehicle);
		returnMap.put("totalReturnVehicle", totalReturnVehicle);
		return returnMap;
	}

	@Transactional
	public void claimCheckPass(ClaimPolicyRewardForSearch cprfs) {
		if (cprfs.getId() == null) {
			throw new FailReturnObject(201, "需传入保单Id");
		}
		ClaimPolicyReward cpr = claPolRewRepository.findOne(cprfs.getId());
		if (cpr != null) {
			cpr.setClaimCheckFlag(true);
			cpr.setClaimCheckTime(new Date());
			claPolRewRepository.save(cpr);
		} else {
			throw new FailReturnObject(205, "未找到相关保单记录");
		}
	}

	@Transactional
	public void claimCheckModify(ClaimPolicyRewardForSearch cprfs) {
		ClaimPolicyReward cpr = claPolRewRepository.findOne(cprfs.getId());
		if (cpr == null) {
			throw new FailReturnObject(205, "未找到相关保单记录");
		}
		if (cprfs.getInsuranceCommission() != null) {
			cpr.setInsuranceCommission(cprfs.getInsuranceCommission());
		}
		if (cprfs.getVehicleCommission() != null) {
			cpr.setVehicleCommission(cprfs.getVehicleCommission());
		}
		cpr.setManagementFee(cpr.getInsuranceCommission() + cpr.getVehicleCommission());
		cpr.setProfit(cpr.getReceivedBrokerFee() + cpr.getReturnVehicleTaxFee() + cpr.getAdditionalFee()
				- cpr.getInsuranceCommission() - cpr.getVehicleCommission());
		if (cprfs.getSalesmanId() != null) {
			Salesman salesman = salesmanRepository.findOne(cprfs.getSalesmanId());
			if (salesman == null) {
				throw new FailReturnObject(205, "未找到代理人");
			}
			cpr.setSalesman(salesman);
			cpr.setDepartment(salesman.getDepartment());
		}
		cpr.setClaimCheckTime(new Date());
		claPolRewRepository.save(cpr);
	}

	/**
	 * 更改保单状态为无效（删除）
	 * 
	 * @param id
	 */
	@Transactional
	public void delete(Integer id) {
		ClaimPolicyReward cpr = claPolRewRepository.findOne(id);
		ClaimPolicyRewardState disableState = cprStateRepository.findByStateCode("DISABLED");
		cpr.setState(disableState);
		claPolRewRepository.save(cpr);
	}

	public void update(ClaimPolicyRewardForSearch cprfs) {
		ClaimPolicyReward claPolReward = claPolRewRepository.findOne(cprfs.getId());
		claPolReward.setInsuraceCompany(cprfs.getInsuraceCompany());
		claPolReward.setSignDate(cprfs.getSignDate());
		claPolReward.setPolicyNumber(cprfs.getPolicyNumber());
		claPolReward.setInsurApplicant(cprfs.getInsurApplicant());
		claPolReward.setInsuredPerson(cprfs.getInsuredPerson());
		claPolReward.setCarOwnerIdCard(cprfs.getCarOwnerIdCard());
		claPolReward.setCarLicense(cprfs.getCarLicense());
		claPolReward.setInsuraceFeeIncludeTax(cprfs.getInsuraceFeeIncludeTax());
		claPolReward.setInsuraceFeeExcludeTax(cprfs.getInsuraceFeeExcludeTax());
		claPolReward.setBrokerFeeRate(cprfs.getBrokerFeeRate());
		claPolReward.setReceivedBrokerFee(cprfs.getReceivedBrokerFee());
		claPolReward.setVehicleTaxFee(cprfs.getVehicleTaxFee());
		claPolReward.setReturnVehicleTaxFee(cprfs.getReturnVehicleTaxFee());
		claPolReward.setAdditionalFee(cprfs.getAdditionalFee());
		claPolReward.setVehicleCommission(cprfs.getVehicleCommission());
		claPolReward.setInsuranceCommission(cprfs.getInsuranceCommission());
		claPolReward.setManagementFee(claPolReward.getInsuranceCommission() + claPolReward.getVehicleCommission());
		claPolReward.setProfit(claPolReward.getReceivedBrokerFee() + claPolReward.getReturnVehicleTaxFee()
				+ claPolReward.getAdditionalFee() - claPolReward.getInsuranceCommission()
				- claPolReward.getVehicleCommission());
		if (cprfs.getSalesmanId() != null) {
			Salesman salesman = salesmanRepository.findOne(cprfs.getSalesmanId());
			claPolReward.setSalesman(salesman);
			claPolReward.setDepartment(salesman.getDepartment());
		}
		claPolRewRepository.save(claPolReward);
	}

	@Override
	@PostConstruct
	public void sqlInit() {
		List<ClaimPolicyRewardAct> list = actRepository.findAll();
		if (list == null || list.size() == 0) {
			ClaimPolicyRewardAct act1 = actRepository
					.save(new ClaimPolicyRewardAct("创建", "create", 10, ActGroup.UPDATE));
			ClaimPolicyRewardAct act2 = actRepository.save(new ClaimPolicyRewardAct("读取", "read", 20, ActGroup.READ));
			ClaimPolicyRewardAct act3 = actRepository
					.save(new ClaimPolicyRewardAct("更新", "update", 30, ActGroup.UPDATE));
			ClaimPolicyRewardAct act4 = actRepository
					.save(new ClaimPolicyRewardAct("删除", "delete", 40, ActGroup.UPDATE));

			ClaimPolicyRewardState stateEnabled = new ClaimPolicyRewardState("有效", 0, "ENABLED");
			stateEnabled.setActs(Arrays.asList(act1, act2, act3, act4).stream().collect(Collectors.toSet()));
			stateRepository.save(stateEnabled);
			ClaimPolicyRewardState stateDisabled = new ClaimPolicyRewardState("无效", 100, "DISABLED");
			stateRepository.save(stateDisabled);
		}
	}

	@Override
	public LogRepository<ClaimPolicyRewardLog> getLogRepository() {
		return logRepository;
	}

	@Override
	public ClaimPolicyRewardLog getLogInstance() {
		return new ClaimPolicyRewardLog();
	}

	@Override
	public AuditorEntityRepository<ClaimPolicyReward> getAuditorEntityRepository() {
		return claPolRewRepository;
	}

	@Override
	public ActRepository<ClaimPolicyRewardAct> getActRepository() {
		return actRepository;
	}

	@Override
	@PostConstruct
	public void injectEntityService() {
		new ClaimPolicyReward().setService(this);
	}

	@Override
	@PostConstruct
	public void injectLogRepository() {
		new ClaimPolicyReward().setLogRepository(logRepository);
	}

}
