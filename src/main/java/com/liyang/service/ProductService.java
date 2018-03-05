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
import com.liyang.domain.product.Product;
import com.liyang.domain.product.ProductAct;
import com.liyang.domain.product.ProductActRepository;
import com.liyang.domain.product.ProductLog;
import com.liyang.domain.product.ProductLogRepository;
import com.liyang.domain.product.ProductRepository;
import com.liyang.domain.product.ProductState;
import com.liyang.domain.product.ProductStateRepository;
import com.liyang.domain.product.ProductTO;
import com.liyang.domain.productcompany.ProductCompany;
import com.liyang.domain.productcompany.ProductCompanyRepository;
import com.liyang.domain.role.Role;
import com.liyang.domain.role.RoleRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.util.CommonUtil;
import com.liyang.util.FailReturnObject;

/**
 * @author Adam
 * @version 创建时间：2018年1月31日 上午9:59:17 类说明
 */
@Service
public class ProductService extends AbstractAuditorService<Product, ProductState, ProductAct, ProductLog> {

	@Autowired
	ProductRepository productRepository;
	@Autowired
	ProductStateRepository proStateRepository;
	@Autowired
	ProductActRepository proActRepository;
	@Autowired
	ProductLogRepository proLogRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	ProductCompanyRepository proCompanyRepository;

	@Override
	@PostConstruct
	public void sqlInit() {
		if (proActRepository.count() <= 0) {
			ProductAct act1 = new ProductAct("创建", "create", 10, ActGroup.UPDATE);
			ProductAct act2 = new ProductAct("读取", "read", 20, ActGroup.READ);
			ProductAct act3 = new ProductAct("更新", "update", 30, ActGroup.UPDATE);
			ProductAct act4 = new ProductAct("删除", "delete", 40, ActGroup.UPDATE);
			// 需将CascadeType.ALL改为REFRESH， 否则此处需保存
			act1 = proActRepository.save(act1);
			act2 = proActRepository.save(act2);
			act3 = proActRepository.save(act3);
			act4 = proActRepository.save(act4);
			// 为管理员初始化权限
			Role administrator = roleRepository.findByRoleCode("ADMINISTRATOR");
			act1.setRoles(new HashSet<>(Arrays.asList(administrator)));
			act2.setRoles(new HashSet<>(Arrays.asList(administrator)));
			act3.setRoles(new HashSet<>(Arrays.asList(administrator)));
			act4.setRoles(new HashSet<>(Arrays.asList(administrator)));
			act1 = proActRepository.save(act1);
			act2 = proActRepository.save(act2);
			act3 = proActRepository.save(act3);
			act4 = proActRepository.save(act4);
			// 初始化状态
			ProductState enableState = new ProductState("启用", 100, "ENABLED");
			enableState.setActs(Arrays.asList(act1, act2, act3, act4).stream().collect(Collectors.toSet()));
			proStateRepository.save(enableState);
			ProductState disabledState = new ProductState("禁用", 200, "DISABLED");
			disabledState.setActs(Arrays.asList(act2, act3).stream().collect(Collectors.toSet()));
			proStateRepository.save(disabledState);
		}
	}

	@Override
	public LogRepository<ProductLog> getLogRepository() {
		return proLogRepository;
	}

	@Override
	public ProductLog getLogInstance() {
		return new ProductLog();
	}

	@Override
	public AuditorEntityRepository<Product> getAuditorEntityRepository() {
		return productRepository;
	}

	@Override
	public ActRepository<ProductAct> getActRepository() {
		return proActRepository;
	}

	@Override
	@PostConstruct
	public void injectEntityService() {
		new Product().setService(this);
	}

	@Override
	@PostConstruct
	public void injectLogRepository() {
		new Product().setLogRepository(proLogRepository);
	}

	/**
	 * 新增产品
	 * 
	 * @param product
	 */
	@Transactional
	public Product save(Product product) {
		ProductState enabledState = proStateRepository.findByStateCode("ENABLED");
		product.setState(enabledState);
		return productRepository.save(product);
	}

	/**
	 * 产品列表、查询
	 * 
	 * @param productTO
	 * @param pageable
	 * @return
	 */
	public Page<Product> query(ProductTO productTO, Pageable pageable) {
		Page<Product> page = null;
		if (productTO == null) {
			page = productRepository.findAll(pageable);
		} else {
			page = productRepository.findAll(new Specification<Product>() {
				@Override
				public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> predicateList = new ArrayList<>();
					if (!StringUtils.isEmpty(productTO.getId())) {
						Predicate IdEqual = cb.equal(root.get("id"), productTO.getId());
						predicateList.add(IdEqual);
					}
					if (!StringUtils.isEmpty(productTO.getLabel())) {
						Predicate labelLike = cb.like(root.get("label"), "%" + productTO.getLabel() + "%");
						predicateList.add(labelLike);
					}
					if (!StringUtils.isEmpty(productTO.getProductCompanyId())) {
						Predicate proCompanyEqual = cb.equal(root.get("productCompany").get("id"),
								productTO.getProductCompanyId());
						predicateList.add(proCompanyEqual);
					}
					if (!StringUtils.isEmpty(productTO.getType())) {
						Predicate typeEqual = cb.equal(root.get("type"), productTO.getType());
						predicateList.add(typeEqual);
					}
					if (!StringUtils.isEmpty(productTO.getStateCode())) {
						Predicate stataEqual = cb.equal(root.get("state").get("stateCode"), productTO.getStateCode());
						predicateList.add(stataEqual);
					}
					query.where(predicateList.toArray(new Predicate[predicateList.size()]));
					return null;
				}
			}, pageable);
		}
		return page;
	}

	/**
	 * 复制product集合中对象属性
	 * 
	 * @param productList
	 * @return
	 */
	public List<ProductTO> copyListProperties(List<Product> productList) {
		List<ProductTO> TOList = new ArrayList<>();
		for (Product product : productList) {
			ProductTO productTO = new ProductTO();
			BeanUtils.copyProperties(product, productTO);
			productTO.setStateCode(product.getState().getStateCode());
			productTO.setProductCompanyLabel(product.getProductCompany().getLabel());
			productTO.setProductCompanyId(product.getProductCompany().getId());
			TOList.add(productTO);
		}
		return TOList;
	}

	/**
	 * 据Id查找
	 * 
	 * @param id
	 * @return
	 */
	public Product findOne(Integer id) {
		return productRepository.findOne(id);
	}

	/**
	 * 更新
	 * 
	 * @param product
	 * @param productTO
	 * @return
	 */
	@Transactional
	public Product update(Product product, ProductTO productTO) {
		BeanUtils.copyProperties(productTO, product, CommonUtil.getNullPropertyNames(productTO));
		if (null != productTO.getProductCompanyId()) {
			ProductCompany proCompany = proCompanyRepository.findOne(productTO.getProductCompanyId());
			if (null == proCompany) {
				throw new FailReturnObject(ExceptionResultEnum.PRODUCT_COMPANY_ID_ERROR);
			}
			product.setProductCompany(proCompany);
		}
		return productRepository.save(product);
	}

	/**
	 * 状态切换
	 * 
	 * @param product
	 * @return
	 */
	@Transactional
	public Product updateState(Product product) {
		if ("ENABLED".equals(product.getState().getStateCode())) {
			product.setState(proStateRepository.findByStateCode("DISABLED"));
		} else {
			product.setState(proStateRepository.findByStateCode("ENABLED"));
		}
		return productRepository.save(product);
	}

}
