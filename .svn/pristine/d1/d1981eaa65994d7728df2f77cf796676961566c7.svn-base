package com.liyang.domain.teamadvertise;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.team.Team;
import com.liyang.service.AbstractAuditorService;

/**
 * 团队公告
 * 
 * @author Adam
 */
@Entity
@Table(name = "team_advertise")
public class TeamAdvertise extends AbstractAuditorEntity<TeamAdvertiseState, TeamAdvertiseAct, TeamAdvertiseLog> {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	@Transient
	private static LogRepository logRepository;

	@SuppressWarnings("rawtypes")
	@Transient
	private static AbstractAuditorService service;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "team_id")
	@Info(label = "团队")
	private Team team;

	@Column(length = 20)
	@Info(label = "标题")
	@Length(min = 1, max = 20, message = "标题长度要求1~20个字符")
	private String title;

	@Info(label = "内容")
	@NotBlank(message = "内容不能空")
	private String content;

	@Info(label = "发布时间")
	private Date publishTime;

	@Info(label = "公告类型", secret = "预留字段")
	private Integer type;

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transient
	@JsonIgnore
	public AbstractAuditorService getService() {
		return service;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public void setService(AbstractAuditorService service) {
		TeamAdvertise.service = service;
	}

	@Override
	@Transient
	@JsonIgnore
	public TeamAdvertiseLog getLogInstance() {
		return new TeamAdvertiseLog();
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transient
	@JsonIgnore
	public LogRepository getLogRepository() {
		return logRepository;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public void setLogRepository(LogRepository logRepository) {
		TeamAdvertise.logRepository = logRepository;
	}

}
