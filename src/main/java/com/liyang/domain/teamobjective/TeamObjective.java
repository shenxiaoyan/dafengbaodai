package com.liyang.domain.teamobjective;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.insuranceresult.InsuranceResult;
import com.liyang.domain.team.Team;
import com.liyang.service.AbstractAuditorService;

@Entity
@Table(name = "team_objective", indexes = {
		@Index(name = "team_objective_period", columnList = "period, team_id", unique = true) })
public class TeamObjective extends AbstractAuditorEntity<TeamObjectiveState, TeamObjectiveAct, TeamObjectiveLog> {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	@Transient
	private static LogRepository logRepository;

	@SuppressWarnings("rawtypes")
	@Transient
	private static AbstractAuditorService service;

	// 车险月业绩目标，万为单位，保留两位小数
	@Column(name = "auto_objective", columnDefinition = "decimal(10,2)")
	@Info(label = "车险目标业绩（万）")
	private Double autoObjective = 0.0;

	// 寿险月业绩目标，万为单位，保留两位小数
	@Column(name = "life_objective", columnDefinition = "decimal(10,2)")
	@Info(label = "寿险目标业绩（万）")
	private Double lifeObjective = 0.0;

	// 车险当月完成量，保存时以元为单位，两位小数，计算时换算为万
	@Column(name = "auto_completion", columnDefinition = "decimal(10,2)")
	@Info(label = "车险当月完成金额（元）")
	private Double autoCompletion = 0.0;
	
	@Column(name = "auto_num")
	@Info(label = "车险单数")
	private Integer autoNum = 0;
	
	// 寿险当月完成量，保存时以元为单位，两位小数，计算时换算为万
	@Column(name = "life_completion", columnDefinition = "decimal(10,2)")
	@Info(label = "寿险当月完成金额（元）")
	private Double lifeCompletion = 0.0;

	@Column(name = "life_num")
	@Info(label = "寿险单数")
	private Integer lifeNum = 0;
	
	@Type(type = "yes_no")
	@Info(label="车险目标完成")
	private Boolean autoFinished;
	
	@Type(type = "yes_no")
	@Info(label="寿险目标完成")
	private Boolean lifeFinished;
	

	@Column(length = 100)
	@Info(label = "目标备注说明")
	private String illustration;

	// 目标区间（年月），格式如：201801
	@Info(label = "目标区间（年月）")
	private String period;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "team_id")
	@Info(label = "团队")
	private Team team;
	
	@OneToMany(cascade = CascadeType.PERSIST, targetEntity = InsuranceResult.class, mappedBy = "teamObjective")
	private Set<InsuranceResult> insuranceResults;
	
	public Double getAutoObjective() {
		return autoObjective;
	}

	public void setAutoObjective(Double autoObjective) {
		this.autoObjective = autoObjective;
	}

	public Double getLifeObjective() {
		return lifeObjective;
	}

	public void setLifeObjective(Double lifeObjective) {
		this.lifeObjective = lifeObjective;
	}

	public Double getAutoCompletion() {
		return autoCompletion;
	}

	public void setAutoCompletion(Double autoCompletion) {
		this.autoCompletion = autoCompletion;
	}

	public Double getLifeCompletion() {
		return lifeCompletion;
	}

	public void setLifeCompletion(Double lifeCompletion) {
		this.lifeCompletion = lifeCompletion;
	}

	public Boolean getAutoFinished() {
		return autoFinished;
	}

	public void setAutoFinished(Boolean autoFinished) {
		this.autoFinished = autoFinished;
	}

	public Boolean getLifeFinished() {
		return lifeFinished;
	}

	public void setLifeFinished(Boolean lifeFinished) {
		this.lifeFinished = lifeFinished;
	}

	public String getIllustration() {
		return illustration;
	}

	public void setIllustration(String illustration) {
		this.illustration = illustration;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	
	public Integer getAutoNum() {
		return autoNum;
	}

	public void setAutoNum(Integer autoNum) {
		this.autoNum = autoNum;
	}

	public Integer getLifeNum() {
		return lifeNum;
	}

	public void setLifeNum(Integer lifeNum) {
		this.lifeNum = lifeNum;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transient
	@JsonIgnore
	public AbstractAuditorService<?, TeamObjectiveState, TeamObjectiveAct, TeamObjectiveLog> getService() {
		return service;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setService(AbstractAuditorService service) {
		TeamObjective.service = service;
	}

	@Override
	@Transient
	@JsonIgnore
	public TeamObjectiveLog getLogInstance() {
		return new TeamObjectiveLog();
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transient
	@JsonIgnore
	public LogRepository getLogRepository() {
		return logRepository;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setLogRepository(LogRepository logRepository) {
		TeamObjective.logRepository = logRepository;
	}

}
