package com.liyang.schedule;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.liyang.domain.journal.Journal;
import com.liyang.domain.team.Team;
import com.liyang.domain.teamobjective.TeamObjective;
import com.liyang.service.JournalService;
import com.liyang.service.TeamObjectiveService;
import com.liyang.service.TeamService;
import com.liyang.util.DateUtil;

/**
 * @author Adam
 * @version 创建时间：2018年1月26日 上午11:45:00 类说明:定时任务类
 */
@Component
public class Jobs {

	@Autowired
	TeamService teamService;
	@Autowired
	TeamObjectiveService teamObjService;
	@Autowired
	JournalService journalService;
	
	private static final Logger logger = LoggerFactory.getLogger(Jobs.class);

	/**
	 * 每月初创建当月&下月团队业绩目标
	 */
	@Scheduled(cron = "1 0 0 1 * ?")
	@Transactional
	public void createObjective() {
		String currentPeriod = DateUtil.format2YearMonthStr(new Date());
		String nextPeriod = DateUtil.format2NextYearMonthStr(new Date());
		List<Team> teamList = teamService.findByStateCode("ENABLED");
		for (Team team : teamList) {
			TeamObjective current = teamObjService.findByTeamAndPeriod(team, currentPeriod);
			TeamObjective next = teamObjService.findByTeamAndPeriod(team, nextPeriod);
			if (null == current) {
				TeamObjective currentObjective = new TeamObjective();
				currentObjective.setTeam(team);
				currentObjective.setPeriod(currentPeriod);
				teamObjService.autoCreate(currentObjective);
				logger.info("自动创建teamid:" + team.getId() + " 当月业绩");
			}
			if (null == next) {
				TeamObjective nextObjective = new TeamObjective();
				nextObjective.setTeam(team);
				nextObjective.setPeriod(nextPeriod);
				teamObjService.autoCreate(nextObjective);
				logger.info("【自动创建teamid:" + team.getId() + " 下月业绩】");
			}
		}
	}
	
	/**
	 * 每日0点，改变流水状态
	 */
	@Scheduled(cron = "1 0 0 * * ?")
	public void autoChangeJournalState() {
		String changedId = journalService.autoChangeState();
		logger.info("【自动改变结算中状态为已结算，Id:" + changedId);
	}
	
	
	
	
}
