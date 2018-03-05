package com.liyang.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.liyang.domain.claimPolicyRewards.ClaimPolicyReward;
import com.liyang.domain.claimPolicyRewards.ClaimPolicyRewardRepository;
import com.liyang.domain.claimmatch.ClaimMatch;
import com.liyang.domain.claimmatch.ClaimMatchRepository;
import com.liyang.domain.department.DepartmentRepository;
import com.liyang.domain.salesman.Salesman;
import com.liyang.domain.salesman.SalesmanRepository;
import com.liyang.domain.user.User;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.util.CommonUtil;
import com.liyang.util.ExcelUtil;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
@Service
public class ClaimMatchService {
	
	@Autowired
	ClaimPolicyRewardRepository claPloRewRepository;
	@Autowired
	SalesmanRepository salManRepository;
	@Autowired
	ClaimMatchRepository claMatchRepository;
	@Autowired
	DepartmentRepository depRepository;
	
	@Transactional
	public String inportAndMatch(MultipartFile excelFile){
//		List<ClaimMatch> claMatchList = ExcelUtil.readExcel(excelFile, ClaimMatch.class);
		List<ClaimMatch> claMatchList = readExcel(excelFile);
		//判断导入表单中重复数据
		List<ClaimMatch> tempList = new ArrayList<ClaimMatch>();
		for (ClaimMatch claimMatch : claMatchList) {
			if (tempList.contains(claimMatch)) {
				throw new FailReturnObject(208,"匹配失败，表单中存在保险公司（"+claimMatch.getInsuranceCompany()+"）与车牌（"+claimMatch.getCarLicense()+"）完全一致的记录，请检查");
			}else {
				tempList.add(claimMatch);
			}
		}
		
		//获取所有未认领的保单
		Page<ClaimPolicyReward> unClaimedPage = claPloRewRepository.findByReturnVehicleTaxFeeCheckFlagIsTrueAndAdditionalFeeCheckFlagIsTrueAndClaimFlagIsFalseAndClaimCheckFlagIsFalseAndStateStateCode("ENABLED", null);
		List<ClaimPolicyReward> unCliamedList = unClaimedPage.getContent();
		Map<String, ClaimPolicyReward> unClaimedMap = new HashMap<>();
		//未认领中重复的数据
		Map<String, ClaimPolicyReward> repeatMap = new HashMap<>();
		for (ClaimPolicyReward claimPolicyReward : unCliamedList) {
			if (unClaimedMap.get(claimPolicyReward.getInsuraceCompany()+claimPolicyReward.getCarLicense()+claimPolicyReward.getInsuraceType()) != null) {
				repeatMap.put(claimPolicyReward.getInsuraceCompany()+claimPolicyReward.getCarLicense()+claimPolicyReward.getInsuraceType(), claimPolicyReward);
			}
			System.out.println(claimPolicyReward.getInsuraceCompany()+claimPolicyReward.getCarLicense()+claimPolicyReward.getInsuraceType());
			unClaimedMap.put(claimPolicyReward.getInsuraceCompany()+claimPolicyReward.getCarLicense()+claimPolicyReward.getInsuraceType(), claimPolicyReward);
		}
		
		//获取所有有效状态的代理人
		List<Salesman> findAll = salManRepository.findByStateStateCode("ENABLED");
		Map<String, Salesman> salmanMap = new HashMap<>();
		for (Salesman salesman : findAll) {
			salmanMap.put(salesman.getDepartmentLabel()+salesman.getName(), salesman);
		}
		
		List<ClaimMatch> claMatchListForSave = new ArrayList<>();
		User createdBy = CommonUtil.getPrincipal();
		for (ClaimMatch claimMatch : claMatchList) {
//				if (StringUtils.isEmpty(claimMatch.getInsuranceCompany()) || StringUtils.isEmpty(claimMatch.getCarLicense())) {
//					claimMatch.setMsg("车牌或保险公司为空");
//					claMatchListForSave.add(claimMatch);
//					continue;
//				}
//				Salesman salesman = salManRepository.findByNameAndDepartment(claimMatch.getSalesmanName(), dep);
			Salesman salesman = salmanMap.get(claimMatch.getDepartmentName()+claimMatch.getSalesmanName());
			if (null == salesman) {
				claimMatch.setMsg("未成功匹配部门下代理人");
				claMatchListForSave.add(claimMatch);
				continue;
			}
			if (claimMatch.getCommercialFee() == 0 && claimMatch.getCompulsoryFee() == 0) {
				claimMatch.setMsg("险种费用均为0");
				claMatchListForSave.add(claimMatch);
				continue;
			}
			if (claimMatch.getCommercialFee() != 0 && claimMatch.getCompulsoryFee() == 0) {
//					List<ClaimPolicyReward> commercialList = claPloRewRepository.findByInsuraceCompanyAndCarLicenseAndInsuraceTypeContainsAndAdditionalFeeCheckFlagIsTrueAndClaimFlagIsFalse(claimMatch.getInsuranceCompany(),claimMatch.getCarLicense(),"商业险");
//					if (commercialList.size() == 1) {
//						ClaimPolicyReward claPolReward = commercialList.get(0);
//						claPolReward = claimCommercial(claPolReward, salesman, claimMatch);
//						claPloRewRepository.save(claPolReward);
//					}else if (commercialList.size() > 1) {
//						claimMatch.setMsg("未认领商业险中存在多条相关数据");
//						claMatchListForSave.add(claimMatch);
//						continue;
//					}else {
//						claimMatch.setMsg("未认领商业险中未匹配到相关记录");
//						claMatchListForSave.add(claimMatch);
//						continue;
//					}
				ClaimPolicyReward claPolReward = unClaimedMap.get(claimMatch.getInsuranceCompany()+claimMatch.getCarLicense()+"商业险");
				if (claPolReward == null) {
					claimMatch.setMsg("未认领商业险中未匹配到相关记录");
					claMatchListForSave.add(claimMatch);
					continue;
				}else {
					if (repeatMap.get(claimMatch.getInsuranceCompany()+claimMatch.getCarLicense()+"商业险") != null) {
						claimMatch.setMsg("未认领商业险中存在多条相关数据");
						claMatchListForSave.add(claimMatch);
						continue;
					}else {
						claPolReward = claimCommercial(claPolReward, salesman, claimMatch);
						claPloRewRepository.save(claPolReward);
					}
				}
			}
			if (claimMatch.getCompulsoryFee() != 0 && claimMatch.getCommercialFee() == 0) {
//					List<ClaimPolicyReward> compulsoryList = claPloRewRepository.findByInsuraceCompanyAndCarLicenseAndInsuraceTypeContainsAndReturnVehicleTaxFeeCheckFlagIsTrueAndClaimFlagIsFalse(claimMatch.getInsuranceCompany(),claimMatch.getCarLicense(),"交强险");
//					if (compulsoryList.size() == 1) {
//						ClaimPolicyReward claPolReward = compulsoryList.get(0);
//						claPolReward = claimCompulsory(claPolReward, salesman, claimMatch);
//						claPloRewRepository.save(claPolReward);
//					}else if (compulsoryList.size() > 1) {
//						claimMatch.setMsg("未认领交强险存在多条相关数据");
//						claMatchListForSave.add(claimMatch);
//						continue;
//					}else {
//						claimMatch.setMsg("未认领交强险中未匹配到相关记录");
//						claMatchListForSave.add(claimMatch);
//						continue;
//					}
				ClaimPolicyReward claPolReward = unClaimedMap.get(claimMatch.getInsuranceCompany()+claimMatch.getCarLicense()+"交强险");
				if (claPolReward == null) {
					claimMatch.setMsg("未认领交强险中未匹配到相关记录");
					claMatchListForSave.add(claimMatch);
					continue;
				}else {
					if (repeatMap.get(claimMatch.getInsuranceCompany()+claimMatch.getCarLicense()+"交强险") != null) {
						claimMatch.setMsg("未认领交强险中存在多条相关数据");
						claMatchListForSave.add(claimMatch);
						continue;
					}else {
						claPolReward = claimCompulsory(claPolReward, salesman, claimMatch);
						claPloRewRepository.save(claPolReward);
					}
				}
			}
			if (claimMatch.getCompulsoryFee() != 0 && claimMatch.getCommercialFee() != 0) {
//					List<ClaimPolicyReward> commercialList = claPloRewRepository.findByInsuraceCompanyAndCarLicenseAndInsuraceTypeContainsAndAdditionalFeeCheckFlagIsTrueAndClaimFlagIsFalse(claimMatch.getInsuranceCompany(),claimMatch.getCarLicense(),"商业险");
//					if (commercialList.size() == 1) {
//						ClaimPolicyReward commercialClaPolReward = commercialList.get(0);
//						commercialClaPolReward = claimCommercial(commercialClaPolReward, salesman, claimMatch);
//						List<ClaimPolicyReward> compulsoryList = claPloRewRepository.findByInsuraceCompanyAndCarLicenseAndInsuraceTypeContainsAndReturnVehicleTaxFeeCheckFlagIsTrueAndClaimFlagIsFalse(claimMatch.getInsuranceCompany(),claimMatch.getCarLicense(),"交强险");
//						if (compulsoryList.size() == 1) {
//							ClaimPolicyReward claPolReward = compulsoryList.get(0);
//							claPolReward = claimCompulsory(claPolReward, salesman, claimMatch);
//							claPloRewRepository.save(claPolReward);
//						}else if (compulsoryList.size() > 1) {
//							claimMatch.setMsg("未认领交强险存在多条相关数据");
//							claMatchListForSave.add(claimMatch);
//							continue;
//						}else {
//							claimMatch.setMsg("未认领交强险中未匹配到相关记录");
//							claMatchListForSave.add(claimMatch);
//							continue;
//						}
//						claPloRewRepository.save(commercialClaPolReward);
//						
//					}else if (commercialList.size() > 1) {
//						claimMatch.setMsg("未认领商业险中存在多条相关数据");
//						claMatchListForSave.add(claimMatch);
//						continue;
//					}else {
//						claimMatch.setMsg("未认领商业险中未匹配到相关记录");
//						claMatchListForSave.add(claimMatch);
//						continue;
//					}
				ClaimPolicyReward claPolReward = unClaimedMap.get(claimMatch.getInsuranceCompany()+claimMatch.getCarLicense()+"商业险");
				if (claPolReward == null) {
					claimMatch.setMsg("未认领商业险中未匹配到相关记录");
					claMatchListForSave.add(claimMatch);
					continue;
				}else {
					if (repeatMap.get(claimMatch.getInsuranceCompany()+claimMatch.getCarLicense()+"商业险") != null) {
						claimMatch.setMsg("未认领商业险中存在多条相关数据");
						claMatchListForSave.add(claimMatch);
						continue;
					}else {
						ClaimPolicyReward vehClaPolReward = unClaimedMap.get(claimMatch.getInsuranceCompany()+claimMatch.getCarLicense()+"交强险");
						if (vehClaPolReward == null) {
							claimMatch.setMsg("未认领交强险中未匹配到相关记录");
							claMatchListForSave.add(claimMatch);
							continue;
						}else {
							if (repeatMap.get(claimMatch.getInsuranceCompany()+claimMatch.getCarLicense()+"交强险") != null) {
								claimMatch.setMsg("未认领交强险中存在多条相关数据");
								claMatchListForSave.add(claimMatch);
								continue;
							}else {
								vehClaPolReward = claimCompulsory(vehClaPolReward, salesman, claimMatch);
								claPloRewRepository.save(vehClaPolReward);
							}
						}
						claPolReward = claimCommercial(claPolReward, salesman, claimMatch);
						claPloRewRepository.save(claPolReward);
					}
				}
			}
		}
		//删除库中已有数据
		List<ClaimMatch> existList = claMatchRepository.findByCreatedBy(createdBy);
		claMatchRepository.delete(existList);
		//入库未匹配数据
		for (ClaimMatch claimMatch : claMatchListForSave) {
			claimMatch.setCreatedBy(createdBy);
		}
		claMatchRepository.save(claMatchListForSave);
		return "认领匹配完成，成功:"+(claMatchList.size()-claMatchListForSave.size())+"条,失败:"+claMatchListForSave.size()+"条。";
//		try {
//		} catch (Exception e) {
//			throw new FailReturnObject(e);
//		}
		
	}
	
	
	private List<ClaimMatch> readExcel(MultipartFile file){
		String fileName = file.getOriginalFilename();
		InputStream ins = null;
		Workbook wb = null;
		FormulaEvaluator formulaEvaluator = null;
		try {
			ins = file.getInputStream();
			wb = ExcelUtil.createWorkbook(ins, fileName);
			formulaEvaluator = ExcelUtil.createFormulaEvaluator(wb);
		} catch (IOException e) {
			e.printStackTrace();
			throw new FailReturnObject(ExceptionResultEnum.EXCEL_WORKBOOK_ANALIZE_ERROR);
		}
		if (wb.getNumberOfSheets() <1 ) {
			throw new FailReturnObject(ExceptionResultEnum.EXCEL_SHEET_ANALIZE_ERROR);
		}
		int totalNum = 0;
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			totalNum += wb.getSheetAt(i).getPhysicalNumberOfRows();
		}
		if ((totalNum-wb.getNumberOfSheets())>10000) {
			throw new FailReturnObject(ExceptionResultEnum.EXCEL_UPPER_LIMIT_ERROR);
		}
		List<ClaimMatch> returnList = new ArrayList<>();
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			Sheet sheet = wb.getSheetAt(i);
			if (null != sheet) {
				int totalRows = sheet.getPhysicalNumberOfRows();
				int totalCells = 0;
				if (totalRows >= 2 && sheet.getRow(1) != null) {
					totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
				}else {
					continue;
				}
				String insuranceCompany;
				Date signDate;
				String carLicense;
				String insuredPerson;
				Double commercialFee = 0.0;
				Double compulsoryFee = 0.0;
				Double vehicleFee = 0.0;
				String departmentName;
				String salesmanName;
				Double commercialCommission = 0.0;
				Double compulsoryCommission = 0.0;
				Double vehicleCommission = 0.0;
				for (int r = 1; r < totalRows; r++) {
					ClaimMatch claMatch = new ClaimMatch();
					Row row = sheet.getRow(r);
					if (row == null) {
						continue;
					}
					for (int c = 0; c < totalCells; c++) {
						Cell cell = row.getCell(c);
						switch (c) {
						case 0:
							try {
								insuranceCompany = ExcelUtil.getNotBlankStringCellValue(cell);
							} catch (Exception e) {
								throw new FailReturnObject(421, sheet.getSheetName() + "第" + (r + 1) + "行，第"+(c+1)+"列，"+e.getMessage());
							}
							claMatch.setInsuranceCompany(insuranceCompany);
							break;
						case 1:
							signDate = ExcelUtil.getDateCellValue(cell);
							claMatch.setSignDate(signDate);
							break;
						case 2:
							try {
								carLicense = ExcelUtil.getNotBlankStringCellValue(cell);
							} catch (Exception e) {
								throw new FailReturnObject(421, sheet.getSheetName() + "第" + (r + 1) + "行，第"+(c+1)+"列，"+e.getMessage());
							}
							claMatch.setCarLicense(carLicense);
							break;
						case 3:
							insuredPerson = ExcelUtil.getStringCellValue(cell);
							claMatch.setInsuredPerson(insuredPerson);
							break;
						case 4:
							commercialFee = ExcelUtil.getDoubleCellValue(cell, formulaEvaluator);
							claMatch.setCommercialFee(commercialFee);
							break;
						case 5:
							compulsoryFee = ExcelUtil.getDoubleCellValue(cell, formulaEvaluator);
							claMatch.setCompulsoryFee(compulsoryFee);
							break;
						case 6:
							vehicleFee = ExcelUtil.getDoubleCellValue(cell, formulaEvaluator);
							claMatch.setVehicleFee(vehicleFee);
							break;
						case 7:
							try {
								departmentName = ExcelUtil.getNotBlankStringCellValue(cell);
							} catch (Exception e) {
								throw new FailReturnObject(421, sheet.getSheetName() + "第" + (r + 1) + "行，第"+(c+1)+"列，"+e.getMessage());
							}
							claMatch.setDepartmentName(departmentName);
							break;
						case 8:
							try {
								salesmanName = ExcelUtil.getNotBlankStringCellValue(cell);
							} catch (Exception e) {
								throw new FailReturnObject(421, sheet.getSheetName() + "第" + (r + 1) + "行，第"+(c+1)+"列，"+e.getMessage());
							}
							claMatch.setSalesmanName(salesmanName);
							break;
						case 9:
							commercialCommission = ExcelUtil.getDoubleCellValue(cell, formulaEvaluator);
							claMatch.setCommercialCommission(commercialCommission);
							break;
						case 10:
							compulsoryCommission = ExcelUtil.getDoubleCellValue(cell, formulaEvaluator);
							claMatch.setCompulsoryCommission(compulsoryCommission);
							break;
						case 11:
							vehicleCommission = ExcelUtil.getDoubleCellValue(cell, formulaEvaluator);
							claMatch.setVehicleCommission(vehicleCommission);
							break;
						default:
							break;
						}
					}
					returnList.add(claMatch);
				}
			}
		}
		return returnList;
	}
	
	
	public ClaimPolicyReward claimCommercial(ClaimPolicyReward claPolReward, Salesman salesman, ClaimMatch claimMatch) {
		claPolReward.setSalesman(salesman);
		claPolReward.setDepartment(salesman.getDepartment());
		claPolReward.setInsuranceCommission(claimMatch.getCommercialCommission());
		claPolReward.setClaimFlag(true);
		claPolReward.setClaimTime(new Date());
		claPolReward.setManagementFee(claimMatch.getCommercialCommission());
		claPolReward.setProfit(claPolReward.getReceivedBrokerFee()+claPolReward.getAdditionalFee()-claPolReward.getManagementFee());
		return claPolReward;
	}
	
	public ClaimPolicyReward claimCompulsory(ClaimPolicyReward claPolReward, Salesman salesman, ClaimMatch claimMatch) {
		claPolReward.setSalesman(salesman);
		claPolReward.setDepartment(salesman.getDepartment());
		claPolReward.setInsuranceCommission(claimMatch.getCompulsoryCommission());
		claPolReward.setVehicleCommission(claimMatch.getVehicleCommission());
		claPolReward.setClaimFlag(true);
		claPolReward.setClaimTime(new Date());
		claPolReward.setManagementFee(claimMatch.getCompulsoryCommission()+claimMatch.getVehicleCommission());
		claPolReward.setProfit(claPolReward.getReceivedBrokerFee()+claPolReward.getReturnVehicleTaxFee()-claPolReward.getManagementFee());
		return claPolReward;
	}

	public List<ClaimMatch> findByCreatedBy(User createdBy) {
		return claMatchRepository.findByCreatedBy(createdBy);
	}
	
	
}
