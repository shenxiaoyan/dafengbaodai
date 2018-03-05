package com.liyang.domain.teamobjective;

import java.util.Date;

public class TeamObjectiveTO {

	private Integer id;
	private String stateCode;
	private Double autoObjective;
	private Double lifeObjective;
	private Double autoCompletion;
	private Double lifeCompletion;
	private Integer aotoNum;
	private Integer lifeNum;
	// private Boolean autoFinished;
	// private Boolean lifeFinished;
	private String illustration;
	private String period;
	private Date periodTime;
	private Integer teamId;
	private String teamLabel;
	// private TeamBean team;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

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

	// public Boolean getAutoFinished() {
	// return autoFinished;
	// }
	// public void setAutoFinished(Boolean autoFinished) {
	// this.autoFinished = autoFinished;
	// }
	// public Boolean getLifeFinished() {
	// return lifeFinished;
	// }
	// public void setLifeFinished(Boolean lifeFinished) {
	// this.lifeFinished = lifeFinished;
	// }
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

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public Date getPeriodTime() {
		return periodTime;
	}

	public void setPeriodTime(Date periodTime) {
		this.periodTime = periodTime;
	}

	public String getTeamLabel() {
		return teamLabel;
	}

	public void setTeamLabel(String teamLabel) {
		this.teamLabel = teamLabel;
	}

	public Integer getAotoNum() {
		return aotoNum;
	}

	public void setAotoNum(Integer aotoNum) {
		this.aotoNum = aotoNum;
	}

	public Integer getLifeNum() {
		return lifeNum;
	}

	public void setLifeNum(Integer lifeNum) {
		this.lifeNum = lifeNum;
	}

	// public TeamBean getTeam() {
	// return team;
	// }
	// public void setTeam(TeamBean team) {
	// this.team = team;
	// }

}
