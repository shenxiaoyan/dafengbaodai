package com.liyang.service;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jpa.query.expression.QueryExpSpecificationBuilder;
import com.jpa.query.expression.generic.GenericQueryExpSpecification;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.insurercompany.InsureCompany;
import com.liyang.domain.insurercompany.InsureCompanyAct;
import com.liyang.domain.insurercompany.InsureCompanyActRepository;
import com.liyang.domain.insurercompany.InsureCompanyForSearch;
import com.liyang.domain.insurercompany.InsureCompanyForUpdate;
import com.liyang.domain.insurercompany.InsureCompanyLog;
import com.liyang.domain.insurercompany.InsureCompanyLogRepository;
import com.liyang.domain.insurercompany.InsureCompanyRepository;
import com.liyang.domain.insurercompany.InsureCompanyState;
import com.liyang.domain.insurercompany.InsureCompanyStateRepository;
import com.liyang.domain.platform.Platform;
import com.liyang.domain.platform.PlatformRepository;
import com.liyang.domain.role.RoleRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
@Service
public class InsureCompanyService
		extends AbstractAuditorService<InsureCompany, InsureCompanyState, InsureCompanyAct, InsureCompanyLog> {

	@Autowired
	InsureCompanyActRepository insureCompanyActRepository;

	@Autowired
	InsureCompanyStateRepository insureCompanyStateRepository;

	@Autowired
	InsureCompanyLogRepository insureCompanyLogRepository;

	@Autowired
	InsureCompanyRepository insureCompanyRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PlatformRepository platformRepository;

	@Override
	public LogRepository<InsureCompanyLog> getLogRepository() {
		// TODO Auto-generated method stub
		return insureCompanyLogRepository;
	}

	@Override
	public AuditorEntityRepository<InsureCompany> getAuditorEntityRepository() {

		return insureCompanyRepository;
	}

	@Override
	@PostConstruct
	public void injectLogRepository() {
		new InsureCompany().setLogRepository(insureCompanyLogRepository);
	}

	@Override
	public InsureCompanyLog getLogInstance() {
		// TODO Auto-generated method stub
		return new InsureCompanyLog();
	}

	@Override
	public ActRepository<InsureCompanyAct> getActRepository() {
		// TODO Auto-generated method stub
		return insureCompanyActRepository;
	}

	@Override
	@PostConstruct
	public void injectEntityService() {
		new InsureCompany().setService(this);
	}

	@Override
	@PostConstruct
	public void sqlInit() {
		// List<InsureCompany> list = insureCompanyRepository.getAlll();
		// if (list.size() == 0) {
		if (insureCompanyRepository.count() <= 0) {
			Platform platform = platformRepository.findById(1);
			insureCompanyRepository.save(new InsureCompany(2, "人保保险", 1, 1,
					"http://fengxianwuyou.oss-cn-hangzhou.aliyuncs.com/%E4%BF%9D%E9%99%A9%E5%85%AC%E5%8F%B8%E5%9B%BE%E6%A0%87/renbao-icn%402x.png",
					"http://fengxianwuyou.oss-cn-hangzhou.aliyuncs.com/%E4%BF%9D%E9%99%A9%E5%85%AC%E5%8F%B8%E5%9B%BE%E6%A0%87/renbao-icon%402x.png",
					platform));
			insureCompanyRepository.save(new InsureCompany(24, "太平洋保险", 1, 1,
					"http://fengxianwuyou.oss-cn-hangzhou.aliyuncs.com/%E4%BF%9D%E9%99%A9%E5%85%AC%E5%8F%B8%E5%9B%BE%E6%A0%87/taiping-icn%402x.png",
					"http://fengxianwuyou.oss-cn-hangzhou.aliyuncs.com/%E4%BF%9D%E9%99%A9%E5%85%AC%E5%8F%B8%E5%9B%BE%E6%A0%87/tpy-icon%402x.png",
					platform));
			insureCompanyRepository.save(new InsureCompany(1, "中国平安保险", 1, 1,
					"http://fengxianwuyou.oss-cn-hangzhou.aliyuncs.com/%E4%BF%9D%E9%99%A9%E5%85%AC%E5%8F%B8%E5%9B%BE%E6%A0%87/pingan-icn%402x.png",
					"http://fengxianwuyou.oss-cn-hangzhou.aliyuncs.com/%E4%BF%9D%E9%99%A9%E5%85%AC%E5%8F%B8%E5%9B%BE%E6%A0%87/pingan-icon%402x.png",
					platform));
			insureCompanyRepository.save(new InsureCompany(7, "安盛天平", 1, 1,
					"http://fengxianwuyou.oss-cn-hangzhou.aliyuncs.com/%E4%BF%9D%E9%99%A9%E5%85%AC%E5%8F%B8%E5%9B%BE%E6%A0%87/astp.icn%402x.png",
					"http://fengxianwuyou.oss-cn-hangzhou.aliyuncs.com/%E4%BF%9D%E9%99%A9%E5%85%AC%E5%8F%B8%E5%9B%BE%E6%A0%87/ansheng-icon%402x.png",
					platform));
			insureCompanyRepository.save(new InsureCompany(9, "天安保险", 1, 1,
					"http://fengxianwuyou.oss-cn-hangzhou.aliyuncs.com/%E4%BF%9D%E9%99%A9%E5%85%AC%E5%8F%B8%E5%9B%BE%E6%A0%87/tianan-icn%402x.png",
					"http://fengxianwuyou.oss-cn-hangzhou.aliyuncs.com/%E4%BF%9D%E9%99%A9%E5%85%AC%E5%8F%B8%E5%9B%BE%E6%A0%87/tianan-icon%402x.png",
					platform));
			insureCompanyRepository.save(new InsureCompany(17, "国寿财险", 1, 1,
					"http://fengxianwuyou.oss-cn-hangzhou.aliyuncs.com/%E4%BF%9D%E9%99%A9%E5%85%AC%E5%8F%B8%E5%9B%BE%E6%A0%87/guoshou_detail.png",
					"http://fengxianwuyou.oss-cn-hangzhou.aliyuncs.com/%E4%BF%9D%E9%99%A9%E5%85%AC%E5%8F%B8%E5%9B%BE%E6%A0%87/guoshoucaixian_list.png",
					platform));
		}

		// List<InsureCompanyAct> findAll =insureCompanyActRepository.findAll();
		// if (findAll == null || findAll.isEmpty()) {
		if (insureCompanyActRepository.count() <= 0) {

			InsureCompanyAct save1 = insureCompanyActRepository
					.save(new InsureCompanyAct("创建", "create", 10, ActGroup.UPDATE));
			InsureCompanyAct save2 = insureCompanyActRepository
					.save(new InsureCompanyAct("读取", "read", 20, ActGroup.READ));
			InsureCompanyAct save3 = insureCompanyActRepository
					.save(new InsureCompanyAct("更新", "update", 30, ActGroup.UPDATE));
			InsureCompanyAct save4 = insureCompanyActRepository
					.save(new InsureCompanyAct("删除", "delete", 40, ActGroup.UPDATE));

			insureCompanyStateRepository.save(new InsureCompanyState("已创建", 0, "CREATED"));
			InsureCompanyState insureCompanyState = new InsureCompanyState("有效", 100, "ENABLED");
			insureCompanyState.setActs(Arrays.asList(save1, save2, save3, save4).stream().collect(Collectors.toSet()));
			insureCompanyStateRepository.save(insureCompanyState);
			insureCompanyStateRepository.save(new InsureCompanyState("无效", 200, "DISABLED"));
			insureCompanyStateRepository.save(new InsureCompanyState("已删除", 300, "DELETED"));
		}

	}

	public Page<InsureCompany> multiComditionSearch(InsureCompanyForSearch insureCompanyForSearch, Pageable pageable) {
		// return insureCompanyRepository.findAll(new
		// Specification<InsureCompany>() {
		//
		//// @Override
		//// public Predicate toPredicate(Root<InsureCompany> root,
		// CriteriaQuery<?> query, CriteriaBuilder cb) {
		//// List<Predicate> predicateList = new ArrayList<>();
		////
		//// if (insureCompanyForSearch != null) {
		//// if
		// (!StringUtils.isEmpty(insureCompanyForSearch.getInsurerCompanyId()))
		// {
		//// Predicate insureCompanyIdEqual =
		// cb.equal(root.<Integer>get("insurerCompanyId"),
		//// insureCompanyForSearch.getInsurerCompanyId());
		//// predicateList.add(insureCompanyIdEqual);
		//// }
		//// if (!StringUtils.isEmpty(insureCompanyForSearch.getName())) {
		//// Predicate nameLile = cb.like(root.get("name"),
		// insureCompanyForSearch.getName());
		//// predicateList.add(nameLile);
		//// }
		//// if (!StringUtils.isEmpty(insureCompanyForSearch.getStateCode())) {
		//// Predicate stateCodeEqual =
		// cb.equal(root.get("state").get("stateCode"),
		//// insureCompanyForSearch.getStateCode());
		//// predicateList.add(stateCodeEqual);
		//// }
		//// }
		////
		//// query.where(predicateList.toArray(new
		// Predicate[predicateList.size()]));
		//// query.multiselect(root.get("id"), root.get("label"),
		// root.get("insurerCompanyId"), root.get("name"),
		//// root.get("state"), root.get("status"), root.get("listIcon"),
		// root.get("detailIcon"),
		//// root.get("createdAt"), root.get("lastModifiedAt"));
		////
		//// return null;
		//// }
		//// }, pageable);

		GenericQueryExpSpecification<InsureCompany> queryExpression = new GenericQueryExpSpecification<InsureCompany>(
				new String[] { "id", "label", "insurerCompanyId", "name", "state", "status", "listIcon", "detailIcon",
						"createdAt", "lastModifiedAt" });
		if(insureCompanyForSearch != null){
			queryExpression
			.add(QueryExpSpecificationBuilder.eq("insurerCompanyId", insureCompanyForSearch.getInsurerCompanyId(),
					true))
			.add(QueryExpSpecificationBuilder.eq("name", insureCompanyForSearch.getName(), false))
			.add(QueryExpSpecificationBuilder.eq("state.stateCode", insureCompanyForSearch.getStateCode(), true));
			
		}

		Page<InsureCompany> pages = insureCompanyRepository.findAll(queryExpression, pageable);
		return pages;
	}

	public void update(InsureCompany company, InsureCompanyForUpdate updateBean) {
		if (!StringUtils.isEmpty(updateBean.getDetailIcon())) {
			company.setDetailIcon(updateBean.getDetailIcon());
		}
		if (updateBean.getInsurerCompanyId() != null) {
			company.setInsurerCompanyId(updateBean.getInsurerCompanyId());
		}
		if (!StringUtils.isEmpty(updateBean.getListIcon())) {
			company.setListIcon(updateBean.getListIcon());
		}
		if (!StringUtils.isEmpty(updateBean.getName())) {
			company.setName(updateBean.getName());
		}
		insureCompanyRepository.save(company);
	}

	public void updateState(InsureCompany company) {
		if ("ENABLED".equals(company.getState().getStateCode())) {
			InsureCompanyState state = insureCompanyStateRepository.findByStateCode("DISABLED");
			company.setState(state);
		} else if ("DISABLED".equals(company.getState().getStateCode())) {
			InsureCompanyState state = insureCompanyStateRepository.findByStateCode("ENABLED");
			company.setState(state);
		} else {
			throw new FailReturnObject(ExceptionResultEnum.INSURECOMPANY_STATECODE_ERROR);
		}
		insureCompanyRepository.save(company);
	}

	public void save(InsureCompany company) {
		InsureCompanyState state = insureCompanyStateRepository.findByStateCode("ENABLED");
		company.setState(state);
		insureCompanyRepository.save(company);
	}
}
