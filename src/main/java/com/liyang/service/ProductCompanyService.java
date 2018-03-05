package com.liyang.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.productcompany.ProductCompany;
import com.liyang.domain.productcompany.ProductCompanyAct;
import com.liyang.domain.productcompany.ProductCompanyActRepository;
import com.liyang.domain.productcompany.ProductCompanyLog;
import com.liyang.domain.productcompany.ProductCompanyLogRepository;
import com.liyang.domain.productcompany.ProductCompanyRepository;
import com.liyang.domain.productcompany.ProductCompanyState;
import com.liyang.domain.productcompany.ProductCompanyStateRepository;
import com.liyang.domain.productcompany.ProductCompanyTO;
import com.liyang.domain.role.Role;
import com.liyang.domain.role.RoleRepository;

/**
 * @author Adam
 * @version 创建时间：2018年1月31日 上午11:53:54 类说明
 */
@Service
public class ProductCompanyService
		extends AbstractAuditorService<ProductCompany, ProductCompanyState, ProductCompanyAct, ProductCompanyLog> {

	@Autowired
	ProductCompanyRepository proCompanyRepository;
	@Autowired
	ProductCompanyStateRepository proComStateRepository;
	@Autowired
	ProductCompanyActRepository proComActRepository;
	@Autowired
	ProductCompanyLogRepository logRepository;
	@Autowired
	RoleRepository roleRepository;

	@Override
	@PostConstruct
	public void sqlInit() {
		if (proComActRepository.count() <= 0) {
			ProductCompanyAct act1 = new ProductCompanyAct("创建", "create", 1, ActGroup.UPDATE);
			ProductCompanyAct act2 = new ProductCompanyAct("读取", "read", 20, ActGroup.READ);
			ProductCompanyAct act3 = new ProductCompanyAct("更新", "update", 30, ActGroup.UPDATE);
			ProductCompanyAct act4 = new ProductCompanyAct("删除", "delete", 40, ActGroup.UPDATE);
			act1 = proComActRepository.save(act1);
			act2 = proComActRepository.save(act2);
			act3 = proComActRepository.save(act3);
			act4 = proComActRepository.save(act4);
			// 为管理员初始化权限
			Role administrator = roleRepository.findByRoleCode("ADMINISTRATOR");
			act1.setRoles(new HashSet<>(Arrays.asList(administrator)));
			act2.setRoles(new HashSet<>(Arrays.asList(administrator)));
			act3.setRoles(new HashSet<>(Arrays.asList(administrator)));
			act4.setRoles(new HashSet<>(Arrays.asList(administrator)));
			act1 = proComActRepository.save(act1);
			act2 = proComActRepository.save(act2);
			act3 = proComActRepository.save(act3);
			act4 = proComActRepository.save(act4);
			// 初始化状态
			ProductCompanyState enabledState = new ProductCompanyState("有效", 100, "ENABLED");
			enabledState.setActs(Arrays.asList(act1, act2, act3, act4).stream().collect(Collectors.toSet()));
			proComStateRepository.save(enabledState);
			ProductCompanyState disabledState = new ProductCompanyState("无效", 200, "DISABLED");
			disabledState.setActs(Arrays.asList(act1, act2, act3, act4).stream().collect(Collectors.toSet()));
			proComStateRepository.save(disabledState);
//			ProductCompanyState deletedState = new ProductCompanyState("已删除", 300, "DELETED");
//			deletedState.setActs(Arrays.asList(act2).stream().collect(Collectors.toSet()));
//			proComStateRepository.save(deletedState);
		}

	}

	@Override
	public LogRepository<ProductCompanyLog> getLogRepository() {
		return logRepository;
	}

	@Override
	public ProductCompanyLog getLogInstance() {
		return new ProductCompanyLog();
	}

	@Override
	public AuditorEntityRepository<ProductCompany> getAuditorEntityRepository() {
		return proCompanyRepository;
	}

	@Override
	public ActRepository<ProductCompanyAct> getActRepository() {
		return proComActRepository;
	}

	@Override
	@PostConstruct
	public void injectEntityService() {
		new ProductCompany().setService(this);
	}

	@Override
	@PostConstruct
	public void injectLogRepository() {
		new ProductCompany().setLogRepository(logRepository);
	}
	
	/**
	 * 新增产品公司
	 * @param proCompany
	 */
	@Transactional
	public ProductCompany save(ProductCompany proCompany) {
		ProductCompanyState enabledState = proComStateRepository.findByStateCode("ENABLED");
		proCompany.setState(enabledState);
		return proCompanyRepository.save(proCompany);
	}
	
	/**
	 * 产品公司查询
	 * @param proCompany
	 * @return
	 */
	public Page<ProductCompany> query(ProductCompanyTO proCompanyTO, Pageable pageable) {
		Page<ProductCompany> page = null;
		if (proCompanyTO == null) {
			page = proCompanyRepository.findAll(pageable);
		}else {
			page = proCompanyRepository.findAll(new Specification<ProductCompany>() {
				@Override
				public Predicate toPredicate(Root<ProductCompany> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> predicateList = new ArrayList<>();
					if (!StringUtils.isEmpty(proCompanyTO.getStateCode())) {
						Predicate stateEqual = cb.equal(root.get("state").get("stateCode"), proCompanyTO.getStateCode());
						predicateList.add(stateEqual);
					}
					if (!StringUtils.isEmpty(proCompanyTO.getLabel())) {
						Predicate labelLike = cb.like(root.get("label"), "%" + proCompanyTO.getLabel() + "%");
						predicateList.add(labelLike);
					}
					query.where(predicateList.toArray(new Predicate[predicateList.size()]));
					return null;
				}
			} , pageable);
		}
		return page;
	}
	
	/**
	 * 据Id查找
	 * @param id
	 * @return
	 */
	public ProductCompany findOne(Integer id) {
		return proCompanyRepository.findOne(id);
	}
	
	/**
	 * 切换状态
	 * @param proCompany
	 */
	@Transactional
	public ProductCompany updateState(ProductCompany proCompany) {
		if ("ENABLED".equals(proCompany.getState().getStateCode())) {
			proCompany.setState(proComStateRepository.findByStateCode("DISABLED"));
			return proCompanyRepository.save(proCompany);
		}else {
			proCompany.setState(proComStateRepository.findByStateCode("ENABLED"));
			return proCompanyRepository.save(proCompany);
		}
	}
	
	/**
	 * 集合拷贝
	 * @param content
	 * @return
	 */
	public List<ProductCompanyTO> copyListToProductCompanyTO(List<ProductCompany> content) {
		List<ProductCompanyTO> list = new ArrayList<>();
		for (ProductCompany productCompany : content) {
			ProductCompanyTO proCompanyTO = new ProductCompanyTO();
			BeanUtils.copyProperties(productCompany, proCompanyTO);
			proCompanyTO.setStateCode(productCompany.getState().getStateCode());
			list.add(proCompanyTO);
		}
		return list;
	}

}
