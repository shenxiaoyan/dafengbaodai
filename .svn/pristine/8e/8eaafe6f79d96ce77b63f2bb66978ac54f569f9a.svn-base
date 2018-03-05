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
import com.liyang.domain.role.Role;
import com.liyang.domain.role.RoleRepository;
import com.liyang.domain.team.Team;
import com.liyang.domain.teamobjective.TeamObjective;
import com.liyang.domain.teamobjective.TeamObjectiveAct;
import com.liyang.domain.teamobjective.TeamObjectiveActRepository;
import com.liyang.domain.teamobjective.TeamObjectiveLog;
import com.liyang.domain.teamobjective.TeamObjectiveLogRepository;
import com.liyang.domain.teamobjective.TeamObjectiveRepository;
import com.liyang.domain.teamobjective.TeamObjectiveState;
import com.liyang.domain.teamobjective.TeamObjectiveStateRepository;
import com.liyang.domain.teamobjective.TeamObjectiveTO;
import com.liyang.util.CommonUtil;
import com.liyang.util.DateUtil;

@Service
public class TeamObjectiveService extends AbstractAuditorService<TeamObjective, TeamObjectiveState, TeamObjectiveAct, TeamObjectiveLog>{
	
	@Autowired
	TeamObjectiveRepository teamObjRepository;
	@Autowired
	TeamObjectiveActRepository teamObjActRepository;
	@Autowired
	TeamObjectiveStateRepository teamObjStateRepository;
	@Autowired
	TeamObjectiveLogRepository teamObjLogRepository;
	@Autowired
	RoleRepository roleRepository;
	
//	private static final Logger logger = LoggerFactory.getLogger(TeamObjectiveService.class);
	
	@Override
	@PostConstruct
	public void sqlInit() {
		if (teamObjActRepository.count() <= 0) {
			TeamObjectiveAct act1 = new TeamObjectiveAct("创建", "create", 10, ActGroup.UPDATE);
			TeamObjectiveAct act2 = new TeamObjectiveAct("读取", "read", 20, ActGroup.READ);
			TeamObjectiveAct act3 = new TeamObjectiveAct("更新", "update", 30, ActGroup.UPDATE);
			TeamObjectiveAct act4 = new TeamObjectiveAct("删除", "delete", 40, ActGroup.UPDATE);
			act1 = teamObjActRepository.save(act1);
			act2 = teamObjActRepository.save(act2);
			act3 = teamObjActRepository.save(act3);
			act4 = teamObjActRepository.save(act4);
			// 为管理员初始化权限
			Role administrator = roleRepository.findByRoleCode("ADMINISTRATOR");
			act1.setRoles(new HashSet<>(Arrays.asList(administrator)));
			act2.setRoles(new HashSet<>(Arrays.asList(administrator)));
			act3.setRoles(new HashSet<>(Arrays.asList(administrator)));
			act4.setRoles(new HashSet<>(Arrays.asList(administrator)));
			act1 = teamObjActRepository.save(act1);
			act2 = teamObjActRepository.save(act2);
			act3 = teamObjActRepository.save(act3);
			act4 = teamObjActRepository.save(act4);
			// 初始化状态
			TeamObjectiveState createdState = new TeamObjectiveState("已创建", 100, "CREATED");
			createdState.setActs(Arrays.asList(act1, act2, act3, act4).stream().collect(Collectors.toSet()));
			teamObjStateRepository.save(createdState);
			TeamObjectiveState settedState = new TeamObjectiveState("已设定", 200, "SETTED");
			settedState.setActs(Arrays.asList(act1, act2, act3, act4).stream().collect(Collectors.toSet()));
			teamObjStateRepository.save(settedState);
			TeamObjectiveState autoFinishState = new TeamObjectiveState("车险已完成", 300, "AUTOFINISH");
			autoFinishState.setActs(Arrays.asList(act1, act2, act3, act4).stream().collect(Collectors.toSet()));
			teamObjStateRepository.save(autoFinishState);
			TeamObjectiveState lifeFinishState = new TeamObjectiveState("寿险已完成", 400, "LIFEFINISH");
			lifeFinishState.setActs(Arrays.asList(act1, act2, act3, act4).stream().collect(Collectors.toSet()));
			teamObjStateRepository.save(lifeFinishState);
			TeamObjectiveState unfinishedState = new TeamObjectiveState("未完成", 500, "UNFINISHED");
			unfinishedState.setActs(Arrays.asList(act1, act2, act3, act4).stream().collect(Collectors.toSet()));
			teamObjStateRepository.save(unfinishedState);
			TeamObjectiveState finishedState = new TeamObjectiveState("已完成", 600, "FINISHED");
			finishedState.setActs(Arrays.asList(act1, act2, act3, act4).stream().collect(Collectors.toSet()));
			teamObjStateRepository.save(finishedState);
			TeamObjectiveState deletedState = new TeamObjectiveState("已删除", 700, "DELETED");
			deletedState.setActs(Arrays.asList(act2).stream().collect(Collectors.toSet()));
			teamObjStateRepository.save(deletedState);
		}
	}

	@Override
	public LogRepository<TeamObjectiveLog> getLogRepository() {
		return teamObjLogRepository;
	}

	@Override
	public TeamObjectiveLog getLogInstance() {
		return new TeamObjectiveLog();
	}

	@Override
	public AuditorEntityRepository<TeamObjective> getAuditorEntityRepository() {
		return teamObjRepository;
	}

	@Override
	public ActRepository<TeamObjectiveAct> getActRepository() {
		return teamObjActRepository;
	}

	@Override
	@PostConstruct
	public void injectEntityService() {
		new TeamObjective().setService(this);
	}

	@Override
	@PostConstruct
	public void injectLogRepository() {
		new TeamObjective().setLogRepository(teamObjLogRepository);
	}
	
	// -------------------Web Methods ----------------------------
	
	/**
	 * 新增
	 * @param teamObjective
	 * @return
	 */
	@Transactional
	public TeamObjective save(TeamObjective teamObjective) {
		TeamObjectiveState createdState = teamObjStateRepository.findByStateCode("CREATED");
		teamObjective.setState(createdState);
		return teamObjRepository.save(teamObjective);
	}
	
	/**
	 * 自动创建业绩方法，避免save切面报错
	 * @param teamObjective
	 */
	@Transactional
	public void autoCreate(TeamObjective teamObjective) {
		TeamObjectiveState createdState = teamObjStateRepository.findByStateCode("CREATED");
		teamObjective.setState(createdState);
		teamObjRepository.save(teamObjective);
	}
	
	/**
	 * 据id 获取
	 * @param id
	 * @return
	 */
	public TeamObjective findOne(Integer id) {
		return teamObjRepository.findOne(id);
	}
	
	/**
	 * 更新
	 * @param teamObjective
	 */
	@Transactional
	public TeamObjective update(TeamObjective teamObjective, TeamObjectiveTO teamObjTO) {
		BeanUtils.copyProperties(teamObjTO, teamObjective, CommonUtil.getNullPropertyNames(teamObjTO));
		return teamObjRepository.save(teamObjective);
	}
	
	/**
	 * 团队业绩信息查询
	 * @param teamObjTO
	 * @param pageable
	 * @return
	 */
	public Page<TeamObjective> query(TeamObjectiveTO teamObjTO, Pageable pageable) {
		Page<TeamObjective> page = null;
		if ( teamObjTO == null ) {
			page = teamObjRepository.findAll(pageable);
		}else {
			page = teamObjRepository.findAll(new Specification<TeamObjective>() {
				@Override
				public Predicate toPredicate(Root<TeamObjective> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> predicateList = new ArrayList<>();
					if (null != teamObjTO.getId()) {
						Predicate idEqusl = cb.equal(root.get("id"), teamObjTO.getId());
						predicateList.add(idEqusl);
					}
					if (null != teamObjTO.getTeamId()) {
						Predicate teamEqual = cb.equal(root.get("team").get("id"), teamObjTO.getTeamId());
						predicateList.add(teamEqual);
					}
					if (null != teamObjTO.getPeriodTime()) {
						String period = DateUtil.format2YearMonthStr(teamObjTO.getPeriodTime());
						Predicate periodEqual = cb.equal(root.get("period"), period);
						predicateList.add(periodEqual);
					}
					if (!StringUtils.isEmpty(teamObjTO.getTeamLabel())) {
						Predicate teamLabelEqual = cb.equal(root.get("team").get("label"), teamObjTO.getTeamLabel());
						predicateList.add(teamLabelEqual);
					}
					if (!StringUtils.isEmpty(teamObjTO.getStateCode())) {
						Predicate stateEqual = cb.equal(root.get("state").get("stateCode"), teamObjTO.getStateCode());
						predicateList.add(stateEqual);
					}
					
					query.where(predicateList.toArray(new Predicate[predicateList.size()]));
					return null;
				}
			}, pageable);
			
		}
		return page;
	}
	
	/**
	 * 判断团队当月业绩是否存在
	 * @param team
	 * @param period
	 * @return
	 */
	public TeamObjective findByTeamAndPeriod(Team team, String period) {
		return teamObjRepository.findByTeamAndPeriod(team, period);
	}
	
	/**
	 * 删除团队业绩
	 * @param teamObj
	 */
	@Transactional
	public TeamObjective deleteTeamObjective(TeamObjective teamObj) {
		TeamObjectiveState deletedState = teamObjStateRepository.findByStateCode("DELETED");
		teamObj.setState(deletedState);
		return teamObjRepository.save(teamObj);
	}
	
	/**
	 * 据团队查找
	 * @param team
	 * @return
	 */
	public List<TeamObjective> findByTeam(Team team) {
		return teamObjRepository.findByTeam(team);
	}
	
	// -------------------Mobile Methods ----------------------------
	
	
	
	/**
//	 * 更新车险完成金额及单数
//	 */
//	public void changeAutoComplete(Customer customer, double sumPrice) {
//		try {
//			if (null != customer.getTeam()) {
//				TeamObjective teamObjective = teamObjRepository.findByTeamAndPeriod(customer.getTeam(), DateUtil.format2YearMonthStr(new Date()));
//				teamObjective.setAutoCompletion(teamObjective.getAutoCompletion() + sumPrice);
//				teamObjective.setAutoNum(teamObjective.getAutoNum() + 1);
//				teamObjRepository.save(teamObjective);
//				logger.info("【团队车险业绩更新成功，teamObjectiveId:" + teamObjective.getId() + "】");
//			}
//		} catch (Exception e) {
//			logger.error("【!!!------团队车险完成业绩更新异常------!!!】");
//			logger.error(GlobalExceptionHandler.getTraceInfo(e));
//		}
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
