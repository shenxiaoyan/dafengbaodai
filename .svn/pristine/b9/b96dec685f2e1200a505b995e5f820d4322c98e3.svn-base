package com.liyang.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.insuranceresult.InsuranceResult;
import com.liyang.domain.journal.Journal;
import com.liyang.domain.journal.JournalAct;
import com.liyang.domain.journal.JournalActRepository;
import com.liyang.domain.journal.JournalLog;
import com.liyang.domain.journal.JournalLogRepository;
import com.liyang.domain.journal.JournalRepository;
import com.liyang.domain.journal.JournalState;
import com.liyang.domain.journal.JournalStateRepository;
import com.liyang.domain.journal.JournalTO;
import com.liyang.domain.offerresult.OfferResultDataResult;
import com.liyang.domain.product.Product;
import com.liyang.domain.product.ProductRepository;
import com.liyang.domain.role.Role;
import com.liyang.domain.role.RoleRepository;
import com.liyang.domain.submitproposal.SubmitProposal;
import com.liyang.domain.team.Team;
import com.liyang.enums.InsuranceType;
import com.liyang.handle.GlobalExceptionHandler;

/**
 * @author Adam
 * @version 创建时间：2018年1月31日 下午2:48:16 类说明
 */
@Service
public class JournalService extends AbstractAuditorService<Journal, JournalState, JournalAct, JournalLog> {

	@Autowired
	JournalRepository journalRepository;
	@Autowired
	JournalStateRepository jouStateRepository;
	@Autowired
	JournalActRepository jouActRepository;
	@Autowired
	JournalLogRepository jouLogRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	ProductRepository productRepository;

	@Override
	@PostConstruct
	public void sqlInit() {
		if (jouActRepository.count() <= 0) {
			JournalAct act1 = new JournalAct("创建", "create", 10, ActGroup.UPDATE);
			JournalAct act2 = new JournalAct("读取", "read", 20, ActGroup.READ);
			JournalAct act3 = new JournalAct("更新", "update", 30, ActGroup.UPDATE);
			JournalAct act4 = new JournalAct("删除", "delete", 40, ActGroup.UPDATE);
			// 为管理员初始化权限
			Role administrator = roleRepository.findByRoleCode("ADMINISTRATOR");
			act1.setRoles(new HashSet<>(Arrays.asList(administrator)));
			act2.setRoles(new HashSet<>(Arrays.asList(administrator)));
			act3.setRoles(new HashSet<>(Arrays.asList(administrator)));
			act4.setRoles(new HashSet<>(Arrays.asList(administrator)));
			act1 = jouActRepository.save(act1);
			act2 = jouActRepository.save(act2);
			act3 = jouActRepository.save(act3);
			act4 = jouActRepository.save(act4);
			// 初始化状态
			JournalState settlingState = new JournalState("结算中", 100, "SETTLING");
			settlingState.setActs(Arrays.asList(act2, act3, act4).stream().collect(Collectors.toSet()));
			jouStateRepository.save(settlingState);
			JournalState settledState = new JournalState("已结算", 200, "SETTLED");
			settledState.setActs(Arrays.asList(act1, act2, act3, act4).stream().collect(Collectors.toSet()));
			jouStateRepository.save(settledState);
			JournalState surrenderedState = new JournalState("退保取消", 300, "SURRENDERED");
			surrenderedState.setActs(Arrays.asList(act2, act3, act4).stream().collect(Collectors.toSet()));
			jouStateRepository.save(surrenderedState);
			JournalState withdrawingState = new JournalState("提现中", 400, "WITHDRAWING");
			withdrawingState.setActs(Arrays.asList(act2, act3, act4).stream().collect(Collectors.toSet()));
			jouStateRepository.save(withdrawingState);
			JournalState withdrawSuccessState = new JournalState("提现成功", 500, "WITHDRAW_SUCCESS");
			withdrawSuccessState.setActs(Arrays.asList(act2, act3, act4).stream().collect(Collectors.toSet()));
			jouStateRepository.save(withdrawSuccessState);
			JournalState withdrawFailedState = new JournalState("提现失败", 600, "WITHDRAW_FAILED");
			withdrawFailedState.setActs(Arrays.asList(act2, act3, act4).stream().collect(Collectors.toSet()));
			jouStateRepository.save(withdrawFailedState);
		}
	}

	@Override
	public LogRepository<JournalLog> getLogRepository() {
		return jouLogRepository;
	}

	@Override
	public JournalLog getLogInstance() {
		return new JournalLog();
	}

	@Override
	public AuditorEntityRepository<Journal> getAuditorEntityRepository() {
		return journalRepository;
	}

	@Override
	public ActRepository<JournalAct> getActRepository() {
		return jouActRepository;
	}

	@Override
	@PostConstruct
	public void injectEntityService() {
		new Journal().setService(this);
	}

	@Override
	@PostConstruct
	public void injectLogRepository() {
		new Journal().setLogRepository(jouLogRepository);
	}

	/**
	 * 多条件查询显示自身流水列表 web端 前端用户姓名、电话、险种类型、险种名称、险种保险公司、承保时间
	 * 
	 * @param pageable
	 * @param journalTO
	 * @return
	 */
	public Page<Journal> query(Pageable pageable, JournalTO journalTO) {
		Page<Journal> journalPage = null;
		if (null == journalTO) {
			journalPage = journalRepository.findAll(pageable);
		} else {
			journalPage = journalRepository.findAll(new Specification<Journal>() {
				@Override
				public Predicate toPredicate(Root<Journal> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> predicates = new ArrayList<Predicate>();
					if (!StringUtils.isEmpty(journalTO.getToken())) {
						Predicate tokenEq = cb.equal(root.get("customer").get("token"), journalTO.getToken());
						predicates.add(tokenEq);
					}
					if (!StringUtils.isEmpty(journalTO.getCustomerRealname())) {
						Predicate realNameEqual = cb.equal(root.get("customer").get("account").get("realName"),
								journalTO.getCustomerRealname());
						predicates.add(realNameEqual);
					}
					if (!StringUtils.isEmpty(journalTO.getCustomerPhone())) {
						Predicate phoneEqual = cb.equal(root.get("customer").get("phone"),
								journalTO.getCustomerPhone());
						predicates.add(phoneEqual);
					}
					if (null != journalTO.getInsuranceType()) {
						Predicate insuranceTypeEqual = cb.equal(root.get("insuranceType"),
								journalTO.getInsuranceType());
						predicates.add(insuranceTypeEqual);
					}
					if (!StringUtils.isEmpty(journalTO.getProductLabel())) {
						Predicate productLabelEqual = cb.equal(root.get("product").get("label"),
								journalTO.getProductLabel());
						predicates.add(productLabelEqual);
					}
					if (!StringUtils.isEmpty(journalTO.getProductCompanyLabel())) {
						Predicate productCompanyEqual = cb.equal(root.get("product").get("productCompany").get("label"),
								journalTO.getProductCompanyLabel());
						predicates.add(productCompanyEqual);
					}
					// if (null != journalTO.getInsureTime()) {
					// Date endTime = DateUtil.getDayEnd(journalTO.getInsureTime());
					// Predicate insureTimeEqual = cb.between(root.get("insureTime"),
					// journalTO.getInsureTime(),
					// endTime);
					// predicates.add(insureTimeEqual);
					// }
					query.where(predicates.toArray(new Predicate[predicates.size()]));
					return null;
				}
			}, pageable);

		}
		return journalPage;
	}

	/**
	 * 某用户所有结算中金额
	 * 
	 * @param token
	 * @return
	 */
	public double sumSettlingByToken(String token) {
		Double num = journalRepository.sumCommissionByTokenAndState(token, "SETTLING");
		return num != null ? num : 0;
	}

	/**
	 * 某用户所有已结算金额
	 * 
	 * @return
	 */
	public double sumSettledByToken(String token) {
		Double num = journalRepository.sumCommissionByTokenAndState(token, "SETTLED");
		return num != null ? num : 0;
	}

	/**
	 * 承保成功设置账户流水
	 * 
	 * @param submitProposal
	 */
	@Transactional
	public void generateJournalAndSave(InsuranceResult insuranceResult, SubmitProposal submitProposal,
			String insuranceCompanyName, String orderId, Date insureTime, Logger occerLogger) {
		try {
			Product product = productRepository.findByTypeAndProductCompany_Label(1, insuranceCompanyName);
			Customer customer = submitProposal.getCustomer();
			OfferResultDataResult dataResult = submitProposal.getOfferResult().getData().getResult();
			Double originalPrice = dataResult.getOriginalPrice().doubleValue() / 100;
			Double forcePremium = dataResult.getForcePremium().doubleValue() / 100;
			Double taxPrice = dataResult.getTaxPrice().doubleValue() / 100;
			String carLicense = insuranceResult.getData().getLicenseNumber();
			String insuredName = insuranceResult.getSubmitProposal().getParams().getInsuredName();
			if (null != product) {
				Double vehicleCommission = product.getVehicleCommissionRate() * taxPrice / 100;
				Double compulsoryCommission = product.getCompulsoryCommissionRate() * forcePremium /100;
				Double commercialCommission = product.getCommercialCommissionRate() * originalPrice /100;
				Double commission = vehicleCommission + compulsoryCommission + commercialCommission /100;
				Journal journal = new Journal(vehicleCommission, product.getVehicleCommissionRate(),
						compulsoryCommission, product.getCompulsoryCommissionRate(), commercialCommission,
						product.getCommercialCommissionRate(), commission, product.getCommissionRate(), customer,
						InsuranceType.AUTO_INSURANCE, product, orderId, carLicense, insuredName, insureTime, "",
						insuranceResult);
				JournalState settlingState = jouStateRepository.findByStateCode("SETTLING");
				Journal exitOne = journalRepository.findByOrderId(orderId);
				if (exitOne != null) {
					journal.setId(exitOne.getId());
				}
				journal.setState(settlingState);
				journalRepository.save(journal);
				occerLogger.info("【--------------用户流水建立成功------------】");
				return;
			}
			occerLogger.error("【！！！！！！----------承保成功，用户佣金流水建立失败，未找到相关返佣产品！！！-----------！！！！！！】");
		} catch (Exception e) {
			// TODO 异常时，此处需做相应处理，防止用户流水不能正常建立导致不能正常获得返佣
			occerLogger.error("【！！！！！！----------承保成功，用户佣金流水建立发生异常！！！-----------！！！！！！】");
			occerLogger.error(GlobalExceptionHandler.getTraceInfo(e));
		}
	}

	/**
	 * 结算中状态转换为已结算
	 */
	public String autoChangeState() {
		List<Journal> settlingList = journalRepository.findByState_StateCode("SETTLING");
		JournalState settledState = jouStateRepository.findByStateCode("SETTLED");
		StringBuffer buffer = new StringBuffer();
		for (Journal journal : settlingList) {
			journal.setState(settledState);
			buffer.append(journal.getId() + "、");
		}
		journalRepository.save(settlingList);
		return buffer.toString();
	}

	// /**
	// * 团队业绩
	// * @param insuranceType
	// * @param team
	// * @param begin
	// * @param end
	// * @param code1
	// * @param code2
	// * @return
	// */
	// public double sumTeamCommissionByDate(InsuranceType insuranceType, Team team,
	// Date begin, Date end , String code1, String code2) {
	// Double num = journalRepository.sumTeamCommission(insuranceType, team, begin,
	// end, code1, code2);
	// return num != null ? num : 0;
	// }

}
