package com.liyang.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.liyang.domain.advertise.Advertise;
import com.liyang.domain.advertise.AdvertiseRepository;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.dafengapi.DafengApiCustomer;
import com.liyang.domain.dafengapi.DafengApiCustomerRepository;
import com.liyang.domain.insuranceresult.InsuranceResult;
import com.liyang.domain.insuranceresult.InsuranceResultAct;
import com.liyang.domain.insuranceresult.InsuranceResultActRepository;
import com.liyang.domain.insuranceresult.InsuranceResultFile;
import com.liyang.domain.insuranceresult.InsuranceResultForSearch;
import com.liyang.domain.insuranceresult.InsuranceResultLog;
import com.liyang.domain.insuranceresult.InsuranceResultLogRepository;
import com.liyang.domain.insuranceresult.InsuranceResultRepository;
import com.liyang.domain.insuranceresult.InsuranceResultState;
import com.liyang.domain.insuranceresult.InsuranceResultStateRepository;
import com.liyang.domain.insuranceresult.InsuranceResultWorkflow;
import com.liyang.domain.insuranceresult.InsuranceResultWorkflowRepository;
import com.liyang.domain.offerresult.OfferResult;
import com.liyang.domain.offerresult.OfferResultRepository;
import com.liyang.domain.platform.Platform;
import com.liyang.domain.role.RoleRepository;
import com.liyang.domain.submitproposal.SubmitProposal;
import com.liyang.domain.submitproposal.SubmitProposalRepository;
import com.liyang.domain.submitproposal.SubmitProposalState;
import com.liyang.domain.submitproposal.SubmitProposalStateRepository;
import com.liyang.domain.team.Team;
import com.liyang.domain.teamobjective.TeamObjective;
import com.liyang.domain.teamobjective.TeamObjectiveRepository;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.util.DateUtil;
import com.liyang.util.FailReturnObject;
import com.liyang.util.HttpUtil;
import com.liyang.util.PushAdvertiseUtil;
import com.liyang.util.TransUtils;

import net.sf.json.JSONObject;

@Service
public class InsuranceResultService extends
		AbstractWorkflowService<InsuranceResult, InsuranceResultWorkflow, InsuranceResultAct, InsuranceResultState, InsuranceResultLog, InsuranceResultFile> {

	@Autowired
	InsuranceResultRepository insuranceResultRepository;
	@Autowired
	OfferResultRepository offerResultRepository;
	@Autowired
	InsuranceResultActRepository insuranceResultActRepository;
	@Autowired
	InsuranceResultStateRepository insuranceResultStateRepository;
	@Autowired
	InsuranceResultLogRepository insuranceResultLogRepository;
	@Autowired
	SubmitProposalRepository submitProposalRepository;
	@Autowired
	AdvertiseRepository advertiseRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	SubmitProposalStateRepository submitProposalStateRepository;
	@Autowired
	InsuranceResultWorkflowRepository insuranceResultWorkflowRepository;
	@Autowired
	XinGeService xinGeService;
	@Autowired
	DafengApiCustomerRepository dafengApiCustomerRepository;
	@Autowired
	WebAdvertiseTypeService webAdvTypeService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	SmsService smsService;
	@Autowired
	OfferResultService offerResultService;
	@Autowired
	JournalService journalService;
	@Autowired
	TeamObjectiveService teamObjService;
	@Autowired
	TeamObjectiveRepository teamObjRepository;

	private final static Logger logger = LoggerFactory.getLogger(InsuranceResultService.class);

	@Override
	@PostConstruct
	public void sqlInit() {
	}

	@Override
	public LogRepository<InsuranceResultLog> getLogRepository() {
		return insuranceResultLogRepository;
	}

	@Override
	public AuditorEntityRepository<InsuranceResult> getAuditorEntityRepository() {
		return insuranceResultRepository;
	}

	@Override
	@PostConstruct
	public void injectLogRepository() {
		new User().setLogRepository(insuranceResultLogRepository);
	}

	@Override
	public InsuranceResultLog getLogInstance() {
		return new InsuranceResultLog();
	}

	@Override
	public ActRepository<InsuranceResultAct> getActRepository() {
		return insuranceResultActRepository;
	}

	@Override
	@PostConstruct
	public void injectEntityService() {
		new User().setService(this);
	}

	@Override
	public InsuranceResultFile getFileLogInstance() {
		return new InsuranceResultFile();
	}

	/**
	 * 保存承保结果
	 * 
	 * @param insResMap
	 * @return
	 */
	@Transactional
	public String saveInsRes(Map<String, Object> insResMap) {
		InsuranceResult insuranceResult = null;
		try {
			insuranceResult = TransUtils.mapTransObject(insResMap, InsuranceResult.class);
		} catch (Exception e) {
			logger.error(e.toString());
			throw new FailReturnObject(ExceptionResultEnum.INSURANCERES_DATA_TRANS_ERROR);
		}
		String orderId = insuranceResult.getData().getOrderId();
		if (null == orderId || orderId.trim().equalsIgnoreCase("")) {
			throw new FailReturnObject(ExceptionResultEnum.INSURANCERES_DATA_MIS_ERROR);
		}
		List<SubmitProposal> subProposalList = submitProposalRepository.findByParamsOrderId(orderId);
		if (subProposalList == null || subProposalList.isEmpty()) {
			throw new FailReturnObject(ExceptionResultEnum.COMFIRM_PAYRESULT_NOORDERINFO_ERROR);
		}
		// TODO 可能有重新核保情况，所以是List,get(0)需重写，或在提交核保时重写相关逻辑------Adam
		SubmitProposal submitProposal = subProposalList.get(0);
		Platform platform = submitProposal.getPlatform();
		if (platform != null) {
			platform.setClinchFrequency(platform.getClinchFrequency() + 1);
		}
		insuranceResult.setPlatform(platform);
		insuranceResult.setSubmitProposal(submitProposal);
		Date insureTime = new Date();
		insuranceResult.setCreatedAt(insureTime);
		// TODO 重新提交核保，导致有多条数据，相关逻辑需处理------Adam
		List<InsuranceResult> insurResList = insuranceResultRepository.findByDataOrderId(orderId);
		if (insurResList != null && !(insurResList.isEmpty())) {
			insuranceResult.setId(insurResList.get(0).getId());
		}
		Integer insuranceState = insuranceResult.getData().getState();
		SubmitProposalState state = null; // change subProposal state
		double sumPrice = 0;
		if (4 == insuranceState) {
			int underwritingPriceCent = insuranceResult.getSubmitProposal().getUnderwritingResult().getData()
					.getUnderwritingPriceCent();
			sumPrice = Double.valueOf(underwritingPriceCent) / 100;
			// 关联团队业绩
			Team team = submitProposal.getCustomer().getTeam();
			if (null != team) {
				TeamObjective teamObjective = teamObjRepository.findByTeamAndPeriod(team, DateUtil.format2YearMonthStr(new Date()));
				if (insurResList.isEmpty()) {
					teamObjective.setAutoCompletion(teamObjective.getAutoCompletion() + sumPrice);
				}
				insuranceResult.setTeamObjective(teamObjective);
			}
			
			state = submitProposalStateRepository.findByStateCode("CHENGBAO_SUCCESS");
			submitProposal.getParams().setAddressExpireTime(DateUtil.get24HLater(new Date())); // 设置地址可修改时间
		} else {
			state = submitProposalStateRepository.findByStateCode("CHENGBAO_FAILD");
		}
		insuranceResult = insuranceResultRepository.save(insuranceResult);
		submitProposal.setState(state);
		submitProposalRepository.save(submitProposal);

		if (4 == insuranceState) {
			String insuranceCompanyName = insuranceResult.getSubmitProposal().getOfferResult().getData().getResult()
					.getInsuranceCompanyName();
			// 后台消息通知
			webAdvTypeService.sendWebAdvertise(insuranceResult, sumPrice);
			// 建立账户流水
			journalService.generateJournalAndSave(insuranceResult, submitProposal, insuranceCompanyName, orderId,
					insureTime, logger);
			// 承保成功，发送短信通知
			// try {
			// DecimalFormat df = new DecimalFormat("#0.00");
			// smsService.sendMessage(insuranceResult.getSubmitProposal().getParams().getInsuredName(),
			// insuranceResult.getData().getLicenseNumber(), df.format(sumPrice),
			// insuranceCompanyName,
			// insuranceResult.getData().getBiPolicyNo(),
			// insuranceResult.getData().getCiPolicyNo(),
			// insuranceResult.getSubmitProposal().getCustomer().getId());
			// logger.error("\\n【------承保短信发送成功------】");
			// } catch (Exception e) {
			// logger.error("【！！！！！！--------承保短信发送失败-------】");
			// e.printStackTrace();
			// }
			smsService.sendMessage(insuranceResult, sumPrice, insuranceCompanyName,
					insuranceResult.getData().getBiPolicyNo(), insuranceResult.getData().getCiPolicyNo(),
					submitProposal.getCustomer(), logger);
		}
		// forward to Dafeng API customer
		List<DafengApiCustomer> dafengApiCustomers = dafengApiCustomerRepository.findByPlatform(platform);
		if (dafengApiCustomers != null && (!dafengApiCustomers.isEmpty())) {
			String dfApiUndWriResURL = platform.getPlatformUnderwritingResultURL();
			for (int i = 0; i < 3; i++) {
				String dfApiInsurResResponse = HttpUtil.postRawJsonBody(dfApiUndWriResURL,
						JSONObject.fromObject(insResMap).toString());
				// check response
				if (dfApiInsurResResponse != null) {
					logger.info("dfApiInsurResResponse 转发OL 成功:" + dfApiInsurResResponse);
					break;
				}
			}
			return "{\"data\":{\"orderId\":\"" + orderId
					+ "\"},\"errorMsg\":{\"code\":\"success\",\"message\":\"操作成功\"},\"successful\":true}";
		}
		// push advertise for MobileApp
		Customer customer = submitProposal.getCustomer();
		Map<String, Object> handerDataMap = new HashMap<String, Object>();
		handerDataMap.put("orderId", orderId);
		handerDataMap.put("name", "insuranceResult");
		handerDataMap.put("biPolicyNo", insuranceResult.getData().getBiPolicyNo());
		handerDataMap.put("ciPolicyNo", insuranceResult.getData().getCiPolicyNo());
		List<Advertise> advertiseList = advertiseRepository.findByOfferIdAndZnxType(orderId, 3);
		if (null == advertiseList || advertiseList.isEmpty()) {
			Advertise advertis = generateInsurResultAdvertise(insuranceResult, orderId);
			PushAdvertiseUtil.pushAdvertToAppPlatform(xinGeService, customer, handerDataMap, advertis.getTitle());
		}
		return "{\"data\":{\"orderId\":\"" + orderId
				+ "\"},\"errorMsg\":{\"code\":\"success\",\"message\":\"操作成功\"},\"successful\":true}";
	}

	/**
	 * 移动端消息推动
	 * 
	 * @param insuranceResult
	 * @param orderId
	 * @return
	 */
	private Advertise generateInsurResultAdvertise(InsuranceResult insuranceResult, String orderId) {
		SubmitProposal submitProposal = insuranceResult.getSubmitProposal();
		OfferResult offerResult = submitProposal.getOfferResult();
		String token = submitProposal.getCustomer().getToken();
		String licenseNumber = submitProposal.getUnderwritingResult().getData().getLicenseNumber();
		String insuredName = submitProposal.getParams().getInsuredName();
		String biPolicyNo = insuranceResult.getData().getBiPolicyNo();
		String ciPolicyNo = insuranceResult.getData().getCiPolicyNo();
		StringBuffer titileBuf = new StringBuffer(); // titile
		titileBuf.append("【承保通知】").append(licenseNumber).append("有新的承保结果");
		StringBuffer contentBuf = new StringBuffer(); // content
		contentBuf.append("车牌：").append(licenseNumber).append("\n被保人：").append(insuredName);
		contentBuf.append("\n保险公司：").append(offerResult.getData().getResult().getInsuranceCompanyName());
		contentBuf.append("\n商业险保单号:").append(biPolicyNo).append("\n强制险保单号：").append(ciPolicyNo);
		Advertise advertise = new Advertise();
		advertise.setTitle(titileBuf.toString());
		advertise.setContent(contentBuf.toString());
		advertise.setIsRead(0);
		advertise.setZnxType(3);
		advertise.setType(2);
		advertise.setToken(token);
		advertise.setOfferId(orderId);
		advertise = advertiseRepository.save(advertise);
		return advertise;
	}

	/**
	 * 根据客户id查询承保列表
	 * 
	 * @param irfs
	 * @param pageable
	 * @param customerId
	 * @return
	 */
	public Page<InsuranceResult> multiConditionSearchById(InsuranceResultForSearch irfs, Pageable pageable,
			Integer customerId) {
		Page<InsuranceResult> insuranceResultPage = insuranceResultRepository
				.findAll(new Specification<InsuranceResult>() {
					@Override
					public Predicate toPredicate(Root<InsuranceResult> root, CriteriaQuery<?> query,
							CriteriaBuilder cb) {
						List<Predicate> predicateList = new ArrayList<Predicate>();
						Predicate stateEqual = cb.equal(root.get("submitProposal").get("state").get("stateCode"),
								"CHENGBAO_SUCCESS");
						predicateList.add(stateEqual);
						if (irfs != null) {
							// 客户编号
							if (!StringUtils.isEmpty(irfs.getCustomerId())) {
								Predicate customerIdEqual = cb.equal(
										root.get("submitProposal").get("customer").get("id"), irfs.getCustomerId());
								predicateList.add(customerIdEqual);
							}
							// 出单单号
							if (!StringUtils.isEmpty(irfs.getOrderId())) {
								Predicate orderIdEqual = cb.equal(root.get("data").get("orderId"), irfs.getOrderId());
								predicateList.add(orderIdEqual);
							}
							// 保险公司
							if (!StringUtils.isEmpty(irfs.getInsuranceCompanyName())) {
								Predicate insuranceCompanyNameLike = cb.like(root.get("submitProposal")
										.get("offerResult").get("data").get("result").get("insuranceCompanyName"),
										irfs.getInsuranceCompanyName());
								predicateList.add(insuranceCompanyNameLike);
							}
							// 投保人电话
							if (!StringUtils.isEmpty(irfs.getCustomerPhone())) {
								Predicate customerPhoneLike = cb.equal(
										root.get("submitProposal").get("params").get("customerPhone"),
										irfs.getCustomerPhone());
								predicateList.add(customerPhoneLike);
							}
							// 车牌号
							if (!StringUtils.isEmpty(irfs.getLicenseNumber())) {
								Predicate licenseNumber = cb.equal(root.get("data").get("licenseNumber"),
										irfs.getLicenseNumber());
								predicateList.add(licenseNumber);
							}
							// 车主
							if (!StringUtils.isEmpty(irfs.getOwnerName())) {
								Predicate ownerNameEqual = cb.equal(
										root.get("submitProposal").get("params").get("ownerName"), irfs.getOwnerName());
								predicateList.add(ownerNameEqual);
							}
						}
						query.where(predicateList.toArray(new Predicate[predicateList.size()]));
						return null;
					}
				}, pageable);
		return insuranceResultPage;
	}

	public Page<InsuranceResult> multiConditionSearch(InsuranceResultForSearch irfs, Pageable pageable) {
		Page<InsuranceResult> insuranceResultPage = insuranceResultRepository
				.findAll(new Specification<InsuranceResult>() {

					@Override
					public Predicate toPredicate(Root<InsuranceResult> root, CriteriaQuery<?> query,
							CriteriaBuilder cb) {
						List<Predicate> predicateList = new ArrayList<Predicate>();
						Predicate stateEqual = cb.equal(root.get("submitProposal").get("state").get("stateCode"),
								"CHENGBAO_SUCCESS");
						predicateList.add(stateEqual);
						if (irfs != null) {
							// 出单单号
							if (!StringUtils.isEmpty(irfs.getOrderId())) {
								Predicate orderIdEqual = cb.equal(root.get("data").get("orderId"), irfs.getOrderId());
								predicateList.add(orderIdEqual);
							}
							// 保险公司
							if (!StringUtils.isEmpty(irfs.getInsuranceCompanyName())) {
								Predicate insuranceCompanyNameLike = cb.like(root.get("submitProposal")
										.get("offerResult").get("data").get("result").get("insuranceCompanyName"),
										irfs.getInsuranceCompanyName());
								predicateList.add(insuranceCompanyNameLike);
							}
							// 投保人电话
							if (!StringUtils.isEmpty(irfs.getCustomerPhone())) {
								Predicate customerPhoneLike = cb.equal(
										root.get("submitProposal").get("params").get("customerPhone"),
										irfs.getCustomerPhone());
								predicateList.add(customerPhoneLike);
							}
							// 车牌号
							if (!StringUtils.isEmpty(irfs.getLicenseNumber())) {
								Predicate licenseNumber = cb.equal(root.get("data").get("licenseNumber"),
										irfs.getLicenseNumber());
								predicateList.add(licenseNumber);
							}
							// 车主
							if (!StringUtils.isEmpty(irfs.getInsuredName())) {
								Predicate insuredNameEqual = cb.equal(
										root.get("submitProposal").get("params").get("ownerName"), irfs.getOwnerName());
								predicateList.add(insuredNameEqual);
							}
						}
						query.where(predicateList.toArray(new Predicate[predicateList.size()]));
						return null;
					}
				}, pageable);
		return insuranceResultPage;
	}

	/**
	 * 根据出单日期筛选保单
	 * 
	 * @param range
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List<InsuranceResult> findByCreatedAt(String range, Date beginTime, Date endTime) {
		if (beginTime == null) {
			if (endTime == null) {
				beginTime = DateUtil.getBeginTimeAccordingSign(range);
				return insuranceResultRepository.findByCreatedAtBetween(beginTime, new Date());
			} else {
				return insuranceResultRepository.findByCreatedAtBefore(endTime);
			}
		} else {
			if (endTime == null) {
				endTime = new Date();
			} else if (beginTime.getTime() > endTime.getTime()) {
				throw new FailReturnObject(100, "日期区间选择错误，请检查");
			}
			return insuranceResultRepository.findByCreatedAtBetween(beginTime, endTime);
		}
	}

	/**
	 * 临时方法，更新createdAt字段，原先该字段没值
	 * 
	 * @return
	 */
	@Transactional
	public String updateCreatedAt() {
		List<InsuranceResult> insResList = insuranceResultRepository.findAll();
		for (InsuranceResult insResult : insResList) {
			insResult.setCreatedAt(insResult.getLastModifiedAt());
		}
		return "成功更新" + insResList.size() + "条数据";
	}
}
