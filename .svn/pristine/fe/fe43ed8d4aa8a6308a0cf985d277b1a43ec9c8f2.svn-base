package com.liyang.service;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.jpa.query.expression.QueryExpSpecificationBuilder;
import com.jpa.query.expression.generic.GenericQueryExpSpecification;
import com.liyang.domain.claimPolicyRewards.ClaimPolicyReward;
import com.liyang.domain.department.Department;
import com.liyang.domain.role.*;
import com.liyang.domain.salesman.Salesman;
import com.liyang.domain.salesman.SalesmanState;
import com.liyang.domain.user.User;
import com.liyang.helper.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.user.UserAct;
import com.liyang.domain.user.UserActRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
@Service
@Order(1)
public class RoleService extends AbstractAuditorService<Role, RoleState, RoleAct, RoleLog> {

	@Autowired
	RoleActRepository roleActRepository;

	@Autowired
	RoleStateRepository roleStateRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	RoleLogRepository roleLogRepository;

	@Autowired
	UserActRepository userActRepository;

	// 添加角色
	public Role saveRole(String label, String roleCode) {
		Role role = new Role();
		RoleState enabledState = roleStateRepository.findByStateCode("ENABLED");
		existOne(label, roleCode);

		role.setState(enabledState);
		role.setLabel(label);
		role.setRoleCode(roleCode);
		return roleRepository.save(role);
	}

	/**
	 * 重复性判断
	 *
	 * @return
	 */
	public Boolean existOne(String label, String roleCode) {
		String[] stateCodes = new String[] { "ENABLED", "DISABLED" };
		Role existOne = roleRepository.findByLabelAndStateStateCodeIn(label, stateCodes);
		if (existOne!=null) {
			throw new FailReturnObject(668, "角色名称不能重复");
		}
		Role existO =roleRepository.findByRoleCodeAndStateStateCodeIn(roleCode, stateCodes);
		if (existO!=null) {
			throw new FailReturnObject(669, "角色名称代码不能重复");
		}
		return false;
	}
	/**
	 * 更新重复性判断
	 * @param label
	 * @param roleCode
	 * @param roleId
	 * @return
	 */
	public Boolean updateExistOne(String label, String roleCode,Integer roleId) {
		String[] stateCodes = new String[] { "ENABLED", "DISABLED" };
		Role one = roleRepository.findByRoleCodeAndIdNotAndStateStateCodeIn(roleCode, roleId,stateCodes);
		System.out.println(one);
		if (one!=null) {
			throw new FailReturnObject(669, "角色名称代码不能重复");
		}
		Role two = roleRepository.findByLabelAndIdNotAndStateStateCodeIn(label, roleId,stateCodes);
		System.out.println(two);
		if (two!=null) {
			throw new FailReturnObject(669, "角色名称不能重复");
		}
		return true;
	}
	public Role update(Role role, RoleForUpdate updateBean,Integer roleId) {
		updateExistOne(updateBean.getLabel(), updateBean.getRoleCode(), roleId);
		role.setLabel(updateBean.getLabel());
		role.setRoleCode(updateBean.getRoleCode());
		return roleRepository.save(role);
	}
  public Role updateStateCode(Integer id){
	  Role one = roleRepository.getOne(id);
	  if (one == null) {
		  throw new FailReturnObject(ExceptionResultEnum.ROLE_ID_ERROR);
	  }
	  if (one.getState().getStateCode().equals("DISABLED")){
	  RoleState state=roleStateRepository.findByStateCode("ENABLED");
	  one.setState(state);
	  }else if (one.getState().getStateCode().equals("ENABLED")){
		  RoleState state=roleStateRepository.findByStateCode("DISABLED");
		  one.setState(state);
	  }
	return  roleRepository.save(one);
  }
	/**
	 * 根据状态显示相关页面数据
	 * @return
	 */
	public Map<String, Object> mulByStateCodePage(RoleForSearch rfs, Pageable pageable){

		GenericQueryExpSpecification<Role> queryExpression=new GenericQueryExpSpecification<>(
				new String[]{"id","label","roleCode","state","createdAt","lastModifiedAt"});
		queryExpression.add(QueryExpSpecificationBuilder.eq("id",rfs.getId(),true))
				.add(QueryExpSpecificationBuilder.eq("label",rfs.getRoleName(),true))
				.add(QueryExpSpecificationBuilder.eq("roleCode",rfs.getRoleCode(),true))
				.add(QueryExpSpecificationBuilder.eq("state.stateCode",rfs.getStateCode(),true));
				Page<Role> rolePage=roleRepository.findAll(queryExpression,pageable);
//				cutOffCycle(rolePage);

		Map<String,Object> returnMap=new HashMap<>();
		List<Map<String,Object>> roleList=new ArrayList<>();
		for (Role role:rolePage.getContent()) {
			Map<String,Object> roleMap=new HashMap<>();
			roleMap.put("id",role.getId());
			roleMap.put("roleCode",role.getRoleCode());
			roleMap.put("label",role.getLabel());
			roleMap.put("stateCode",role.getState().getStateCode());
			roleMap.put("createdAt",role.getCreatedAt());
			roleMap.put("lastModifiedAt",role.getLastModifiedAt());
			roleList.add(roleMap);
		}

		returnMap.put("roleContent", roleList);
		returnMap.put("page", getPageData(rolePage));
		return  returnMap;
	}

	/**
	 * 获取page分页信息
	 *
	 * @param page
	 * @return
	 */
	private Map<String, Integer> getPageData(Page<Role> page) {
		Map<String, Integer> pageDataMap = new HashMap<>();
		pageDataMap.put("size", page.getSize());
		pageDataMap.put("totalElements", (int) page.getTotalElements());
		pageDataMap.put("totalPages", page.getTotalPages());
		pageDataMap.put("number", page.getNumber());
		return pageDataMap;
	}
	/**
	 * 设置null，暂解决死循环
	 *
	 * @param page
	 * @return
	 */
	private Page<Role> cutOffCycle(Page<Role> page) {
		for (Role cpr : page) {
			if (cpr.getState() != null) {
				String label= cpr.getState().getLabel();
				Integer id = cpr.getState().getId();
				cpr.setState(null);
				RoleState state = new RoleState();
				state.setId(id);
				state.setLabel(label);
				cpr.setState(state);
			}
			cpr.setState(null);
			cpr.setCreatedByDepartment(null);

		}
		return page;
	}

	public Role findOne(Integer id) {
		return roleRepository.findOne(id);
	}

	public Role delete(Role role) {
		RoleState deletedState = roleStateRepository.findByStateCode("DELETED");
		role.setState(deletedState);
		return roleRepository.save(role);
	}

	@Override
	@PostConstruct
	public void sqlInit() {
		if (roleActRepository.count() <= 0) {
			RoleAct save1 = roleActRepository.save(new RoleAct("创建", "create", 10, ActGroup.UPDATE));
			RoleAct save2 = roleActRepository.save(new RoleAct("读取", "read", 20, ActGroup.READ));
			RoleAct save3 = roleActRepository.save(new RoleAct("更新", "update", 30, ActGroup.UPDATE));
			RoleAct save4 = roleActRepository.save(new RoleAct("删除", "delete", 40, ActGroup.UPDATE));
			RoleAct save5 = roleActRepository.save(new RoleAct("自己的列表", "listOwn", 50, ActGroup.READ));
			RoleAct save6 = roleActRepository.save(new RoleAct("部门的列表", "listOwnDepartment", 60, ActGroup.READ));
			RoleAct save7 = roleActRepository
					.save(new RoleAct("部门和下属部门的列表", "listOwnDepartmentAndChildren", 70, ActGroup.READ));
			RoleAct save8 = roleActRepository.save(new RoleAct("通知其他人", "noticeOther", 80, ActGroup.NOTICE));
			RoleAct save9 = roleActRepository.save(new RoleAct("通知操作者", "noticeActUser", 90, ActGroup.NOTICE));
			RoleAct save10 = roleActRepository.save(new RoleAct("通知展示人", "noticeShowUser", 100, ActGroup.NOTICE));

//			roleStateRepository.save(new RoleState("已创建", 0, "CREATED"));
			RoleState roleState = new RoleState("有效", 100, "ENABLED");
			roleState.setActs(Arrays.asList(save1, save2, save3, save4, save5, save6, save7).stream()
					.collect(Collectors.toSet()));
			RoleState enableState = roleStateRepository.save(roleState);
			roleStateRepository.save(new RoleState("无效", 200, "DISABLED"));
			roleStateRepository.save(new RoleState("已删除", 300, "DELETED"));

			for (Role.DefaultCode roleCode : Role.DefaultCode.values()) {
				Role role = new Role();
				role.setRoleCode(roleCode.toString());
				role.setState(enableState);
				Role save = roleRepository.save(role);
				if ("ADMINISTRATOR".equals(roleCode.toString())) {

					save1.setRoles(new HashSet<Role>(Arrays.asList(save)));
					save2.setRoles(new HashSet<Role>(Arrays.asList(save)));
					save3.setRoles(new HashSet<Role>(Arrays.asList(save)));
					save4.setRoles(new HashSet<Role>(Arrays.asList(save)));
					roleActRepository.save(save1);
					roleActRepository.save(save2);
					roleActRepository.save(save3);
					roleActRepository.save(save4);

				}
			}
			UserAct applyActCode = userActRepository.findByActCode("apply");
			// Role role =
			// roleRepository.findByRoleCode(Role.RoleCode.valueOf("USER"));
			Role role = roleRepository.findByRoleCode("USER");
			if (applyActCode != null) {
				applyActCode.setRoles(new HashSet<Role>(Arrays.asList(role)));
				userActRepository.save(applyActCode);
			}

		}
		
		RoleState createdState = roleStateRepository.findByStateCode("CREATED");
		if (null != createdState) {
			createdState.setLabel("无效");
			createdState.setStateCode("DISABLED");
			roleStateRepository.save(createdState);
		}
		
		
		
//		//修改role表的state为已创建的 2017/12/8
//		Role findOne=roleRepository.getOne(12);
//		RoleState rs = roleStateRepository.findOne(2);
//		findOne.setState(rs);
//		roleRepository.save(findOne);
//		//删除角色为已创建的状态 
//		RoleState StateCode=roleStateRepository.findByStateCode("CREATED");
//		if (StateCode!=null) {
//			StateCode.getActs().clear();
//			roleStateRepository.save(StateCode);	
//			roleStateRepository.delete(StateCode);
//		}

	}

	@Override
	public LogRepository<RoleLog> getLogRepository() {
		// TODO Auto-generated method stub
		return roleLogRepository;
	}

	@Override
	public AuditorEntityRepository<Role> getAuditorEntityRepository() {
		// TODO Auto-generated method stub
		return roleRepository;
	}

	@Override
	@PostConstruct
	public void injectLogRepository() {
		new Role().setLogRepository(roleLogRepository);

	}

	@Override
	public RoleLog getLogInstance() {
		// TODO Auto-generated method stub
		return new RoleLog();
	}

	@Override
	public ActRepository<RoleAct> getActRepository() {
		// TODO Auto-generated method stub
		return roleActRepository;
	}

	@Override
	@PostConstruct
	public void injectEntityService() {
		new Role().setService(this);

	}

}
