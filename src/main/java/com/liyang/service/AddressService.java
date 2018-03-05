package com.liyang.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.liyang.domain.address.Address;
import com.liyang.domain.address.AddressAct;
import com.liyang.domain.address.AddressActRepository;
import com.liyang.domain.address.AddressForSearch;
import com.liyang.domain.address.AddressLog;
import com.liyang.domain.address.AddressLogRepository;
import com.liyang.domain.address.AddressRepository;
import com.liyang.domain.address.AddressState;
import com.liyang.domain.address.AddressStateRepository;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.role.Role;
import com.liyang.domain.role.RoleRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
@Service
public class AddressService extends AbstractAuditorService<Address, AddressState, AddressAct, AddressLog> {

	@Autowired
	private AddressStateRepository stateRepository;
	@Autowired
	private AddressActRepository actRepository;
	@Autowired
	private AddressLogRepository logRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private RoleRepository roleRepository;



	public void save(Address address) {
		AddressState state = stateRepository.findByStateCode("DISABLED");
		address.setState(state);
		addressRepository.save(address);
	}

	public void updateState(Address address) {
		if ("ENABLED".equals(address.getState().getStateCode())) {
			AddressState state = stateRepository.findByStateCode("DISABLED");
			address.setState(state);
		} else if ("DISABLED".equals(address.getState().getStateCode())) {
			AddressState state = stateRepository.findByStateCode("ENABLED");
			address.setState(state);
		} else {
			throw new FailReturnObject(ExceptionResultEnum.ADDRESS_STATECODE_ERROR);
		}
		addressRepository.save(address);
	}

	public Page<Address> search(final AddressForSearch search, Pageable pageable) {
		Page<Address> resultPage = addressRepository.findAll(new Specification<Address>() {

			@Override
			public Predicate toPredicate(Root<Address> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				if (search != null) {
					if (!StringUtils.isEmpty(search.getStateCode())) {
						Predicate stateCodeEQ = cb.equal(root.<String>get("state").get("stateCode"),
								search.getStateCode());
						predicateList.add(stateCodeEQ);
					} else {
						Predicate stateCodeNQ1 = cb.notEqual(root.get("state").get("stateCode"), "DELETED");
						Predicate stateCodeNQ2 = cb.notEqual(root.get("state").get("stateCode"), "CREATED");
						predicateList.add(stateCodeNQ1);
						predicateList.add(stateCodeNQ2);
					}
					if (!StringUtils.isEmpty(search.getProvince())) {
						Predicate provinceEQ = cb.equal(root.<String>get("province"), search.getProvince());
						predicateList.add(provinceEQ);
					}
					if (!StringUtils.isEmpty(search.getCity())) {
						Predicate cityEQ = cb.equal(root.<String>get("city"), search.getCity());
						predicateList.add(cityEQ);
					}
				}
				query.where(predicateList.toArray(new Predicate[predicateList.size()]));
				return null;
			}
		}, pageable);
		return resultPage;
	}

	public ResponseBody findAll() {
		ResponseBody body = new ResponseBody(0, "success");
		List<String> provinceList = addressRepository.findAllProvinces();
		Map<String, List<Map<String, String>>> provinceAndCity = new HashMap<String, List<Map<String, String>>>();
		for (String province : provinceList) {
			List<Address> cityList = addressRepository.findCitiesByProvince(province);
			List<Map<String, String>> citiesAndCodes = new ArrayList<Map<String, String>>();
			for (Address city : cityList) {
				Map<String, String> cityAndCode = new HashMap<String, String>();
				cityAndCode.put(city.getCity(), city.getCityCode());
				citiesAndCodes.add(cityAndCode);
			}
			provinceAndCity.put(province, citiesAndCodes);
		}
		body.setData(provinceAndCity);
		return body;
	}

	public Address findOne(Integer id) {
		return addressRepository.findById(id);
	}

	@Override
	@PostConstruct
	public void sqlInit() {
		List<AddressAct> list = actRepository.findAll();
		if (list == null || list.size() == 0) {
			AddressAct save1 = actRepository.save(new AddressAct("创建", "create", 10, ActGroup.UPDATE));
			AddressAct save2 = actRepository.save(new AddressAct("读取", "read", 20, ActGroup.READ));
			AddressAct save3 = actRepository.save(new AddressAct("更新", "update", 30, ActGroup.UPDATE));
			AddressAct save4 = actRepository.save(new AddressAct("删除", "delete", 40, ActGroup.UPDATE));

			AddressState stateEnabled = new AddressState("启用", 0, "ENABLED");
			stateEnabled.setActs(Arrays.asList(save1, save2, save3, save4).stream().collect(Collectors.toSet()));
			stateRepository.save(stateEnabled);
			AddressState stateDisabled = new AddressState("禁用", 100, "DISABLED");
			stateDisabled.setActs(Arrays.asList(save1, save2, save3, save4).stream().collect(Collectors.toSet()));
			stateRepository.save(stateDisabled);
		}
		AddressState createdState = stateRepository.findByStateCode("CREATED");
		if (createdState == null) {
			AddressAct save1 = actRepository.findByActCode("create");
			AddressAct save2 = actRepository.findByActCode("read");
			AddressAct save3 = actRepository.findByActCode("update");
			AddressAct save4 = actRepository.findByActCode("delete");
			// 新增两个状态
			createdState = new AddressState("已创建", 200, "CREATED");
			createdState.setActs(Arrays.asList(save1).stream().collect(Collectors.toSet()));
			stateRepository.save(createdState);
			stateRepository.save(new AddressState("已删除", 300, "DELETED"));
			// 为管理员初始化赋权
			Role administrator = roleRepository.findByRoleCode("ADMINISTRATOR");
			save1.setRoles(new HashSet<>(Arrays.asList(administrator)));
			save2.setRoles(new HashSet<>(Arrays.asList(administrator)));
			save3.setRoles(new HashSet<>(Arrays.asList(administrator)));
			save4.setRoles(new HashSet<>(Arrays.asList(administrator)));
			actRepository.save(save1);
			actRepository.save(save2);
			actRepository.save(save3);
			actRepository.save(save4);
		}
	}

	@Override
	public LogRepository<AddressLog> getLogRepository() {
		return logRepository;
	}

	@Override
	public AddressLog getLogInstance() {
		return new AddressLog();
	}

	@Override
	public AuditorEntityRepository<Address> getAuditorEntityRepository() {
		return addressRepository;
	}

	@Override
	public ActRepository<AddressAct> getActRepository() {
		return actRepository;
	}

	@Override
	@PostConstruct
	public void injectEntityService() {
		new Address().setService(this);

	}

	@Override
	@PostConstruct
	public void injectLogRepository() {
		new Address().setLogRepository(logRepository);
	}
}
