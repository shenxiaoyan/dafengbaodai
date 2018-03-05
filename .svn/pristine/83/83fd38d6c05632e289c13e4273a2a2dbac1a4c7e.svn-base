package com.liyang.domain.otherInsure;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.account.Account;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.platform.Platform;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;

/**
 * 其他保险
 * @author Administrator
 *
 */
@Entity
@Table(name="other_insure")
public class OtherInsure extends AbstractAuditorEntity<OtherInsureState, OtherInsureAct, OtherInsureLog>{
	private static final long serialVersionUID = 12L;

	@Transient
	private static LogRepository logRepository;
	
	@Transient
	private static AbstractAuditorService service;
	
	
	

	@Override
	@JsonIgnore
	@Transient
	public OtherInsureLog getLogInstance() {
		// TODO Auto-generated method stub
		return new OtherInsureLog();
	}

	@Override
	@JsonIgnore
	@Transient
	public LogRepository getLogRepository() {
		// TODO Auto-generated method stub
		return logRepository;
	}

	@Override
	public void setLogRepository(LogRepository logRepository) {
		OtherInsure.logRepository = logRepository;
	}

	@Override
	@JsonIgnore
	@Transient
	public AbstractAuditorService getService() {
		// TODO Auto-generated method stub
		return service;
	}

	@Override
	public void setService(AbstractAuditorService service) {
		OtherInsure.service = service;
		
	}
	
	public OtherInsure(){		
	}
	
}
