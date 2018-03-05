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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
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
import com.liyang.domain.department.DepartmentAct;
import com.liyang.domain.department.DepartmentActRepository;
import com.liyang.domain.department.DepartmentForSearch;
import com.liyang.domain.department.DepartmentForUpdate;
import com.liyang.domain.department.DepartmentLog;
import com.liyang.domain.department.DepartmentLogRepository;
import com.liyang.domain.department.DepartmentRepository;
import com.liyang.domain.department.DepartmentState;
import com.liyang.domain.department.DepartmentStateRepository;
import com.liyang.domain.departmenttype.Departmenttype;
import com.liyang.domain.departmenttype.DepartmenttypeRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
@Service
@Order(10000)
public class DepartmentService
		extends AbstractAuditorService<Department, DepartmentState, DepartmentAct, DepartmentLog> {

	@Autowired
	DepartmentActRepository departmentActRepository;

	@Autowired
	DepartmentStateRepository departmentStateRepository;

	@Autowired
	DepartmentLogRepository departmentLogRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	DepartmenttypeRepository departmenttypeRepository;

	@Override
	@PostConstruct
	public void sqlInit() {
		// List<DepartmentAct> findAll = departmentActRepository.findAll();
		// if (findAll == null || findAll.isEmpty()) {
		if (departmentActRepository.count() <= 0) {

			DepartmentAct save1 = departmentActRepository.save(new DepartmentAct("创建", "create", 10, ActGroup.UPDATE));
			DepartmentAct save2 = departmentActRepository.save(new DepartmentAct("读取", "read", 20, ActGroup.READ));
			DepartmentAct save3 = departmentActRepository.save(new DepartmentAct("更新", "update", 30, ActGroup.UPDATE));
			DepartmentAct save4 = departmentActRepository.save(new DepartmentAct("删除", "delete", 40, ActGroup.UPDATE));
			DepartmentAct save5 = departmentActRepository
					.save(new DepartmentAct("自己的列表", "listOwn", 50, ActGroup.READ));
			DepartmentAct save6 = departmentActRepository
					.save(new DepartmentAct("部门的列表", "listOwnDepartment", 60, ActGroup.READ));
			DepartmentAct save7 = departmentActRepository
					.save(new DepartmentAct("部门和下属部门的列表", "listOwnDepartmentAndChildren", 70, ActGroup.READ));
			DepartmentAct save8 = departmentActRepository
					.save(new DepartmentAct("通知其他人", "noticeOther", 80, ActGroup.NOTICE));
			DepartmentAct save9 = departmentActRepository
					.save(new DepartmentAct("通知给操作者", "noticeActUser", 90, ActGroup.NOTICE));
			DepartmentAct save10 = departmentActRepository
					.save(new DepartmentAct("通知给展示人", "noticeShowUser", 100, ActGroup.NOTICE));

			departmentStateRepository.save(new DepartmentState("已创建", 0, "CREATED"));
			DepartmentState departmentState = new DepartmentState("有效", 100, "ENABLED");
			departmentState.setActs(Arrays.asList(save1, save2, save3, save4, save5, save6, save7).stream()
					.collect(Collectors.toSet()));
			departmentStateRepository.save(departmentState);
			departmentStateRepository.save(new DepartmentState("无效", 200, "DISABLED"));
			departmentStateRepository.save(new DepartmentState("已删除", 300, "DELETED"));

		}
		if (departmentStateRepository.findByStateCode("DELETED") == null) {
			DepartmentState enabledState = departmentStateRepository.findByStateCode("CREATED");
			enabledState.setStateCode("ENABLED");
			departmentStateRepository.save(enabledState);
			departmentStateRepository.save(new DepartmentState("已创建", 0, "CREATED"));
			departmentStateRepository.save(new DepartmentState("已删除", 300, "DELETED"));
		}
	}

	@Override
	public LogRepository<DepartmentLog> getLogRepository() {
		// TODO Auto-generated method stub
		return departmentLogRepository;
	}

	@Override
	public AuditorEntityRepository<Department> getAuditorEntityRepository() {
		// TODO Auto-generated method stub
		return departmentRepository;
	}

	@Override
	@PostConstruct
	public void injectLogRepository() {
		new Department().setLogRepository(departmentLogRepository);

	}

	@Override
	public DepartmentLog getLogInstance() {
		// TODO Auto-generated method stub
		return new DepartmentLog();
	}

	@Override
	public ActRepository<DepartmentAct> getActRepository() {
		// TODO Auto-generated method stub
		return departmentActRepository;
	}

	@Override
	@PostConstruct
	public void injectEntityService() {
		new Department().setService(this);

	}

	public Page<Department> search(final DepartmentForSearch search, Pageable pageable) {
		Page<Department> page = departmentRepository.findAll(new Specification<Department>() {

			@Override
			public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (search != null) {
					if (!StringUtils.isEmpty(search.getLabel())) {
						Predicate labelEq = cb.equal(root.get("label"), search.getLabel());
						predicates.add(labelEq);
					}
					if (!StringUtils.isEmpty(search.getAttactPerson())) {
						Predicate attactPersonEq = cb.equal(root.get("attactPerson"), search.getAttactPerson());
						predicates.add(attactPersonEq);
					}
					if (!StringUtils.isEmpty(search.getAttactPhone())) {
						Predicate attactPhoneEq = cb.equal(root.get("attactPhone"), search.getAttactPhone());
						predicates.add(attactPhoneEq);
					}
					if (!StringUtils.isEmpty(search.getTypeCode())) {
						Predicate typeCodeEq = cb.equal(root.get("type").get("id"), search.getTypeCode());
						predicates.add(typeCodeEq);
					}
					if (!StringUtils.isEmpty(search.getStateCode())) {
						Predicate stateCodeEq = cb.equal(root.get("state").get("stateCode"), search.getStateCode());
						predicates.add(stateCodeEq);
					} else {
						Predicate stateCodeNe = cb.notEqual(root.get("state").get("stateCode"), "DELETED");
						predicates.add(stateCodeNe);
					}
					if (!StringUtils.isEmpty(search.getDepartmentTypeCode())) {
						Predicate depTypCodeEq = cb.equal(root.get("type").get("code"), search.getDepartmentTypeCode());
						predicates.add(depTypCodeEq);
					}
				}
				query.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		}, pageable);
		return page;

	}

	public Department updateState(Department department) {
		if ("ENABLED".equals(department.getState().getStateCode())) {
			DepartmentState disableState = departmentStateRepository.findByStateCode("DISABLED");
			department.setState(disableState);
		} else if ("DISABLED".equals(department.getState().getStateCode())) {
			DepartmentState enableState = departmentStateRepository.findByStateCode("ENABLED");
			department.setState(enableState);
		} else {
			throw new FailReturnObject(ExceptionResultEnum.DEPARTMENTTYPE_STATE_ERROR);
		}
		return departmentRepository.save(department);
	}

	public Department findOne(Integer id) {
		return departmentRepository.findOne(id);
	}

	public Department save(Department department) {
		DepartmentState enableState = departmentStateRepository.findByStateCode("ENABLED");
		department.setState(enableState);
		return departmentRepository.save(department);
	}

	public Department update(Department department, DepartmentForUpdate updateBean) {
		Departmenttype departmenttype = departmenttypeRepository.findOne(updateBean.getTypeId());
		department.setType(departmenttype);
		department.setLabel(updateBean.getLabel());
		department.setAddress(updateBean.getAddress());
		department.setAttactPerson(updateBean.getAttactPerson());
		department.setAttactPhone(updateBean.getAttactPhone());
		// Department parent =
		// departmentRepository.findOne(updateBean.getParentId());
		// department.setParent(parent);
		department.setDescription(updateBean.getDescription());
		return departmentRepository.save(department);
	}
}
