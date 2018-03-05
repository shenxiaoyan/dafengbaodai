package com.liyang.domain.insuranceresult;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.journal.Journal;
import com.liyang.domain.platform.Platform;
import com.liyang.domain.submitproposal.SubmitProposal;
import com.liyang.domain.teamobjective.TeamObjective;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;

/**
 * 承保结果推送 保单抓取
 * 
 * @author Administrator
 */
@Entity
@Table(name = "insurance_result")
public class InsuranceResult extends
		AbstractWorkflowEntity<InsuranceResultWorkflow, InsuranceResultState, InsuranceResultAct, InsuranceResultLog, InsuranceResultFile> {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	@Transient
	private static LogRepository logRepository;
	@SuppressWarnings("rawtypes")
	@Transient
	private static AbstractWorkflowService service;

	@OneToOne(cascade = CascadeType.ALL, targetEntity = InsuranceResultErrorMsg.class)
	@JoinColumn(name = "insurance_result_error_msg_id")
	private InsuranceResultErrorMsg errorMsg;

	@OneToOne(cascade = CascadeType.ALL, targetEntity = InsuranceResultData.class)
	@JoinColumn(name = "insurance_result_data_id")
	private InsuranceResultData data;

	private Boolean successful;

	@OneToOne(cascade = CascadeType.ALL, targetEntity = Platform.class)
	@JoinColumn(name = "platform_id")
	private Platform platform;

	@OneToOne
	@JoinColumn(name = "submit_proposal_id")
	private SubmitProposal submitProposal;

	@Info(label = "团队业绩目标")
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "team_objective_id")
	private TeamObjective teamObjective;
	
	@Info(label = "个人流水")
	@OneToOne
	@JoinColumn(name = "journal_id")
	private Journal journal;
	
	public Journal getJournal() {
		return journal;
	}

	public void setJournal(Journal journal) {
		this.journal = journal;
	}

	public SubmitProposal getSubmitProposal() {
		return submitProposal;
	}

	public void setSubmitProposal(SubmitProposal submitProposal) {
		this.submitProposal = submitProposal;
	}

	public InsuranceResultErrorMsg getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(InsuranceResultErrorMsg errorMsg) {
		this.errorMsg = errorMsg;
	}

	public InsuranceResultData getData() {
		return data;
	}

	public void setData(InsuranceResultData data) {
		this.data = data;
	}

	public Boolean getSuccessful() {
		return successful;
	}

	public void setSuccessful(Boolean successful) {
		this.successful = successful;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public TeamObjective getTeamObjective() {
		return teamObjective;
	}

	public void setTeamObjective(TeamObjective teamObjective) {
		this.teamObjective = teamObjective;
	}

	@Override
	@JsonIgnore
	@Transient
	public InsuranceResultLog getLogInstance() {
		return new InsuranceResultLog();
	}

	@SuppressWarnings("rawtypes")
	@Override
	@JsonIgnore
	@Transient
	public LogRepository getLogRepository() {
		return logRepository;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setLogRepository(LogRepository logRepository) {
		InsuranceResult.logRepository = logRepository;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@JsonIgnore
	@Transient
	public AbstractAuditorService getService() {
		return service;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setService(AbstractAuditorService service) {
		InsuranceResult.service = (AbstractWorkflowService) service;

	}

}
