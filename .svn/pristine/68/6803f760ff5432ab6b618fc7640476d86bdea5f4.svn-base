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

import org.apache.commons.lang.time.DateUtils;
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
import com.liyang.domain.role.Role;
import com.liyang.domain.role.RoleRepository;
import com.liyang.domain.teamadvertise.TeamAdvertise;
import com.liyang.domain.teamadvertise.TeamAdvertiseAct;
import com.liyang.domain.teamadvertise.TeamAdvertiseActRepository;
import com.liyang.domain.teamadvertise.TeamAdvertiseLog;
import com.liyang.domain.teamadvertise.TeamAdvertiseLogRepository;
import com.liyang.domain.teamadvertise.TeamAdvertiseRepository;
import com.liyang.domain.teamadvertise.TeamAdvertiseState;
import com.liyang.domain.teamadvertise.TeamAdvertiseStateRepository;
import com.liyang.domain.teamadvertise.TeamAdvertiseTO;
import com.liyang.util.DateUtil;

@Service
public class TeamAdvertiseService
		extends AbstractAuditorService<TeamAdvertise, TeamAdvertiseState, TeamAdvertiseAct, TeamAdvertiseLog> {

	@Autowired
	TeamAdvertiseRepository teamAdvRepository;
	@Autowired
	TeamAdvertiseStateRepository teamAdvStateRepository;
	@Autowired
	TeamAdvertiseActRepository teamAdvActRepository;
	@Autowired
	TeamAdvertiseLogRepository teamAdvLogRepository;
	@Autowired
	RoleRepository roleRepository;

	@Override
	@PostConstruct
	public void sqlInit() {
		if (teamAdvActRepository.count() <= 0) {
			TeamAdvertiseAct act1 = new TeamAdvertiseAct("创建", "create", 10, ActGroup.UPDATE);
			TeamAdvertiseAct act2 = new TeamAdvertiseAct("读取", "read", 20, ActGroup.READ);
			TeamAdvertiseAct act3 = new TeamAdvertiseAct("更新", "update", 30, ActGroup.UPDATE);
			TeamAdvertiseAct act4 = new TeamAdvertiseAct("删除", "delete", 40, ActGroup.UPDATE);
			act1 = teamAdvActRepository.save(act1);
			act2 = teamAdvActRepository.save(act2);
			act3 = teamAdvActRepository.save(act3);
			act4 = teamAdvActRepository.save(act4);
			// 为管理员初始化权限
			Role administrator = roleRepository.findByRoleCode("ADMINISTRATOR");
			act1.setRoles(new HashSet<>(Arrays.asList(administrator)));
			act2.setRoles(new HashSet<>(Arrays.asList(administrator)));
			act3.setRoles(new HashSet<>(Arrays.asList(administrator)));
			act4.setRoles(new HashSet<>(Arrays.asList(administrator)));
			act1 = teamAdvActRepository.save(act1);
			act2 = teamAdvActRepository.save(act2);
			act3 = teamAdvActRepository.save(act3);
			act4 = teamAdvActRepository.save(act4);
			// 初始化状态
//			TeamAdvertiseState createdState = new TeamAdvertiseState("已创建", 100, "CREATED");
//			createdState.setActs(Arrays.asList(act1, act2, act3, act4).stream().collect(Collectors.toSet()));
//			teamAdvStateRepository.save(createdState);
			TeamAdvertiseState publishedState = new TeamAdvertiseState("已发布", 100, "PUBLISHED");
			publishedState.setActs(Arrays.asList(act1, act2, act3, act4).stream().collect(Collectors.toSet()));
			teamAdvStateRepository.save(publishedState);
			TeamAdvertiseState deletedState = new TeamAdvertiseState("已删除", 200, "DELETED");
			deletedState.setActs(Arrays.asList(act2).stream().collect(Collectors.toSet()));
			teamAdvStateRepository.save(deletedState);
		}
	}

	@Override
	public LogRepository<TeamAdvertiseLog> getLogRepository() {
		return teamAdvLogRepository;
	}

	@Override
	public TeamAdvertiseLog getLogInstance() {
		return new TeamAdvertiseLog();
	}

	@Override
	public AuditorEntityRepository<TeamAdvertise> getAuditorEntityRepository() {
		return teamAdvRepository;
	}

	@Override
	public ActRepository<TeamAdvertiseAct> getActRepository() {
		return teamAdvActRepository;
	}

	@Override
	@PostConstruct
	public void injectEntityService() {
		new TeamAdvertise().setService(this);
	}

	@Override
	@PostConstruct
	public void injectLogRepository() {
		new TeamAdvertise().setLogRepository(teamAdvLogRepository);
	}

	/**
	 * 新增
	 * 
	 * @param teamAdvertise
	 */
	@Transactional
	public TeamAdvertise save(TeamAdvertise teamAdvertise) {
		TeamAdvertiseState createdState = teamAdvStateRepository.findByStateCode("PUBLISHED");
		teamAdvertise.setState(createdState);
		return teamAdvRepository.save(teamAdvertise);
	}

	/**
	 * 据Id获取
	 * 
	 * @param id
	 * @return
	 */
	public TeamAdvertise findById(Integer id) {
		return teamAdvRepository.findById(id);
	}

	/**
	 * 据Id获取
	 * 
	 * @param id
	 * @return
	 */
	public TeamAdvertise findOne(Integer id) {
		return teamAdvRepository.findById(id);
	}

	/**
	 * 变更状态，未使用，待删除
	 * 
	 * @param teamAdvertise
	 * @param teamAdvTO
	 * @return
	 */
	public TeamAdvertise updateState(TeamAdvertise teamAdvertise, TeamAdvertiseTO teamAdvTO) {
		TeamAdvertiseState state = teamAdvStateRepository.findByStateCode(teamAdvTO.getStateCode());
		if ("PUBLISHED".equals(teamAdvTO.getStateCode())) {
			teamAdvertise.setPublishTime(new Date());
		}
		teamAdvertise.setState(state);
		return teamAdvRepository.save(teamAdvertise);
	}

	/**
	 * 据stateCode搜索
	 * 
	 * @param teamAdvTO
	 * @return
	 */
	public List<TeamAdvertise> findByState(TeamAdvertiseTO teamAdvTO) {
		return teamAdvRepository.findByState_StateCode(teamAdvTO.getStateCode());
	}

	/**
	 * 多条件查询
	 * 
	 * @param teamAdvTO
	 * @return
	 */
	public Page<TeamAdvertise> query(TeamAdvertiseTO teamAdvTO, Pageable pageable) {
		Page<TeamAdvertise> page = null;
		if (null == teamAdvTO) {
			return teamAdvRepository.findAll(pageable);
		} else {
			Date createdAtBegin = DateUtil.getBeginTime(teamAdvTO.getCreatedAtBegin());
			Date createdAtEnd = DateUtil.getEndTime(teamAdvTO.getCreatedAtEnd());
			page = teamAdvRepository.findAll(new Specification<TeamAdvertise>() {
				@Override
				public Predicate toPredicate(Root<TeamAdvertise> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> predicateList = new ArrayList<>();
					Predicate createdAtGreater = cb.greaterThanOrEqualTo(root.get("createdAt"), createdAtBegin);
					predicateList.add(createdAtGreater);
					Predicate createdAtLess = cb.lessThanOrEqualTo(root.get("createdAt"), createdAtEnd);
					predicateList.add(createdAtLess);
					if (!StringUtils.isEmpty(teamAdvTO.getStateCode())) {
						Predicate stateEqual = cb.equal(root.get("state").get("stateCode"), teamAdvTO.getStateCode());
						predicateList.add(stateEqual);
					}
					if (!StringUtils.isEmpty(teamAdvTO.getTeamId())) {
						Predicate teamIdEqual = cb.equal(root.get("team").get("id"), teamAdvTO.getTeamId());
						predicateList.add(teamIdEqual);
					}
					if (!StringUtils.isEmpty(teamAdvTO.getTeamLabel())) {
						Predicate teamLabelLike = cb.like(root.get("team").get("label"),
								"%" + teamAdvTO.getTeamLabel() + "%");
						predicateList.add(teamLabelLike);
					}
					query.where(predicateList.toArray(new Predicate[predicateList.size()]));
					return null;
				}
			}, pageable);
		}
		return page;
	}

	/**
	 * 据Id 置为删除状态
	 * 
	 * @param teamAdv
	 */
	public TeamAdvertise delete(TeamAdvertise teamAdv) {
		TeamAdvertiseState deletedState = teamAdvStateRepository.findByStateCode("DELETED");
		teamAdv.setState(deletedState);
		return teamAdvRepository.save(teamAdv);
	}

}
