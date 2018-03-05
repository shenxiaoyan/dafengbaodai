package com.liyang.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

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
import com.liyang.domain.department.Department;
import com.liyang.domain.department.DepartmentRepository;
import com.liyang.domain.role.RoleRepository;
import com.liyang.domain.salesman.Salesman;
import com.liyang.domain.salesman.SalesmanAct;
import com.liyang.domain.salesman.SalesmanActRepository;
import com.liyang.domain.salesman.SalesmanForSearch;
import com.liyang.domain.salesman.SalesmanForUpdate;
import com.liyang.domain.salesman.SalesmanLog;
import com.liyang.domain.salesman.SalesmanLogRepository;
import com.liyang.domain.salesman.SalesmanRepository;
import com.liyang.domain.salesman.SalesmanState;
import com.liyang.domain.salesman.SalesmanStateRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.util.CommonUtil;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
@Service
public class SalesmanService extends AbstractAuditorService<Salesman, SalesmanState, SalesmanAct, SalesmanLog> {

	@Autowired
	SalesmanRepository salesmanRepository;
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	SalesmanActRepository salesmanActRepository;
	@Autowired
	SalesmanLogRepository salesmanLogRepository;
	@Autowired
	SalesmanStateRepository salesmanStateRepository;
	@Autowired
	RoleRepository roleRepository;

	public List<Salesman> findOwnDepartmentSalesmen() {
		return salesmanRepository.findByDepartmentAndStateStateCode(CommonUtil.getCurrentUserDepartment(), "ENABLED");
	}
	
	@Transactional
	public Salesman saveSalesman(Salesman salesman) {
		Department department = departmentRepository.findOne(salesman.getDepartmentId());
		SalesmanState state = salesmanStateRepository.findByStateCode("ENABLED");
		// Salesman exitOne = salesmanRepository.findByNameAndPhoneNumber(name,
		// phoneNumber);
		Salesman exitOne = salesmanRepository.findByNameAndDepartment_Id(salesman.getName(), salesman.getDepartmentId());
		if (exitOne != null) {
			throw new FailReturnObject(100, "所选部门下已存在相同姓名人员！");
		}
		salesman.setDepartment(department);
		salesman.setState(state);
		return salesmanRepository.save(salesman);
	}

	public Salesman updateSalesman(Salesman salesman,SalesmanForUpdate updateBean) {
		Salesman exitOne = salesmanRepository.findByNameAndDepartment_Id(updateBean.getName(), updateBean.getDepartmentId());
		if (exitOne != null && exitOne.getId() != Integer.valueOf(salesman.getId())) {
			throw new FailReturnObject(100, "部门下已存在相同姓名人员！");
		}
		Department department = departmentRepository.findOne(updateBean.getDepartmentId());
		salesman.setName(updateBean.getName());
		salesman.setPhoneNumber(updateBean.getPhoneNumber());
		salesman.setDepartment(department);
		return salesmanRepository.save(salesman);
	}

	@Override
	@PostConstruct
	public void sqlInit() {
		// List<SalesmanAct> findAll=salesmanActRepository.findAll();
		// if (findAll==null||findAll.isEmpty()) {
		if (salesmanActRepository.count() <= 0) {
			SalesmanAct save1 = salesmanActRepository.save(new SalesmanAct("创建", "create", 10, ActGroup.UPDATE));
			SalesmanAct save2 = salesmanActRepository.save(new SalesmanAct("读取", "read", 20, ActGroup.READ));
			SalesmanAct save3 = salesmanActRepository.save(new SalesmanAct("更新", "update", 30, ActGroup.UPDATE));
			SalesmanAct save4 = salesmanActRepository.save(new SalesmanAct("删除", "delete", 40, ActGroup.UPDATE));
			SalesmanAct save5 = salesmanActRepository.save(new SalesmanAct("自己的列表", "listOwn", 50, ActGroup.READ));
			SalesmanAct save6 = salesmanActRepository
					.save(new SalesmanAct("部门的列表", "listOwnDepartment", 60, ActGroup.READ));
			SalesmanAct save7 = salesmanActRepository
					.save(new SalesmanAct("部门和下属部门的列表", "listOwnDepartmentAndChildren", 70, ActGroup.READ));
			SalesmanAct save8 = salesmanActRepository
					.save(new SalesmanAct("通知其他人", "noticeOther", 80, ActGroup.NOTICE));
			SalesmanAct save9 = salesmanActRepository
					.save(new SalesmanAct("通知操作者", "noticeActUser", 90, ActGroup.NOTICE));
			SalesmanAct save10 = salesmanActRepository
					.save(new SalesmanAct("通知展示人", "noticeShowUser", 100, ActGroup.NOTICE));

			// SalesmanState createState = new SalesmanState("已创建",0,"CREATED");
			// createState.setActs(Arrays.asList(save1).stream().collect(Collectors.toSet()));
			// salesmanStateRepository.save(createState);
			// SalesmanState enableState = new
			// SalesmanState("正常",100,"ENABLED");
			// enableState.setActs(Arrays.asList(save1,save2,save3,save4).stream().collect(Collectors.toSet()));
			// salesmanStateRepository.save(enableState);
			// SalesmanState disableState = new
			// SalesmanState("禁用",200,"DISABLED");
			// disableState.setActs(Arrays.asList(save1,save2,save3,save4).stream().collect(Collectors.toSet()));
			// salesmanStateRepository.save(disableState);
			// salesmanStateRepository.save(new
			// SalesmanState("删除",300,"DELETED"));
			//
			// Role administrator =
			// roleRepository.findByRoleCode("ADMINISTRATOR");//为管理员初始化
			// save1.setRoles(new HashSet<>(Arrays.asList(administrator)));
			// save2.setRoles(new HashSet<>(Arrays.asList(administrator)));
			// save3.setRoles(new HashSet<>(Arrays.asList(administrator)));
			// save4.setRoles(new HashSet<>(Arrays.asList(administrator)));
			// salesmanActRepository.save(save1);
			// salesmanActRepository.save(save2);
			// salesmanActRepository.save(save3);
			// salesmanActRepository.save(save4);
			SalesmanState salesmanState = new SalesmanState("有效", 100, "ENABLED");
			salesmanState.setActs(Arrays.asList(save1, save2, save3, save4, save5, save6, save7).stream()
					.collect(Collectors.toSet()));
			salesmanStateRepository.save(salesmanState);
			salesmanStateRepository.save(new SalesmanState("无效", 200, "DISABLED"));
			salesmanStateRepository.save(new SalesmanState("已删除", 300, "DELETED"));

		}

	}

	/**
	 * 代理人列表查询
	 * @param salesmanForSearch
	 * @param pageable
	 * @return
	 */
	public Page<Salesman> multiConditionSearch(SalesmanForSearch salesmanForSearch, Pageable pageable) {

		Page<Salesman> salesmanPage = salesmanRepository.findAll(new Specification<Salesman>() {

			@Override
			public Predicate toPredicate(Root<Salesman> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();

				if (salesmanForSearch != null) {
					// 部门
					Predicate departmentEqual = null;
					if (!StringUtils.isEmpty(salesmanForSearch.getDepartmentLabel())) {
						departmentEqual = cb.equal(root.get("department").get("label"),
								salesmanForSearch.getDepartmentLabel());
						predicateList.add(departmentEqual);
					}
					// 代理人姓名
					Predicate nameEqual = null;
					if (!StringUtils.isEmpty(salesmanForSearch.getName())) {
						nameEqual = cb.like(root.get("name"), salesmanForSearch.getName());
						predicateList.add(nameEqual);
					}
					// 代理人联系方式
					Predicate phoneNumberEqual = null;
					if (!StringUtils.isEmpty(salesmanForSearch.getPhoneNumber())) {
						phoneNumberEqual = cb.equal(root.get("phoneNumber"), salesmanForSearch.getPhoneNumber());
						predicateList.add(phoneNumberEqual);
					}
					// 代理人状态
					if (!StringUtils.isEmpty(salesmanForSearch.getStateCode())) {
						Predicate stateCodeEqual = cb.equal(root.get("state").get("stateCode"),
								salesmanForSearch.getStateCode());
						predicateList.add(stateCodeEqual);
					} else {
//						Predicate dele = cb.notEqual(root.get("state").get("stateCode"), "DELETED");
						Predicate crea = cb.notEqual(root.get("state").get("stateCode"), "CREATED");
//						predicateList.add(dele);
						predicateList.add(crea);
					}
				}
				query.where(predicateList.toArray(new Predicate[predicateList.size()]));
				return null;
			}
		}, pageable);
		return salesmanPage;
	}

	@Override
	public LogRepository<SalesmanLog> getLogRepository() {
		// TODO Auto-generated method stub
		return salesmanLogRepository;
	}

	@Override
	public SalesmanLog getLogInstance() {
		// TODO Auto-generated method stub
		return new SalesmanLog();
	}

	@Override
	public AuditorEntityRepository<Salesman> getAuditorEntityRepository() {
		// TODO Auto-generated method stub
		return (AuditorEntityRepository<Salesman>) salesmanRepository;
	}

	@Override
	public ActRepository<SalesmanAct> getActRepository() {
		// TODO Auto-generated method stub
		return salesmanActRepository;
	}

	@Override
	@PostConstruct
	public void injectEntityService() {
		// TODO Auto-generated method stub
		new Salesman().setService(this);
	}

	@Override
	@PostConstruct
	public void injectLogRepository() {
		// TODO Auto-generated method stub
		new Salesman().setLogRepository(salesmanLogRepository);
	}

	public void updateState(Salesman salesman) {
		if ("ENABLED".equals(salesman.getState().getStateCode())) {
			SalesmanState state = salesmanStateRepository.findByStateCode("DISABLED");
			salesman.setState(state);
		} else if ("DISABLED".equals(salesman.getState().getStateCode())) {
			SalesmanState state = salesmanStateRepository.findByStateCode("ENABLED");
			salesman.setState(state);
		} else {
			throw new FailReturnObject(ExceptionResultEnum.SALESMAN_STATECODE_ERROR);
		}
		salesmanRepository.save(salesman);
		
	}

	public void delete(Salesman salesman) {
		SalesmanState state = salesmanStateRepository.findByStateCode("DELETED");
		salesman.setState(state);
		salesmanRepository.save(salesman);
	}
}
