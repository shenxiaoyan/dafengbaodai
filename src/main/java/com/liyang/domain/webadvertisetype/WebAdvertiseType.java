package com.liyang.domain.webadvertisetype;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.role.Role;
import com.liyang.service.AbstractAuditorService;

@Entity
@Table(name="web_adveritse_type", indexes = {
		@Index(name = "web_adveritse_type_label", columnList = "label", unique = true),
		@Index(name = "web_adveritse_type_code", columnList = "typeCode", unique = true)})
public class WebAdvertiseType extends AbstractAuditorEntity<WebAdvertiseTypeState, WebAdvertiseTypeAct, WebAdvertiseTypeLog>{

	private static final long serialVersionUID = -2521403411852150987L;
	
	@Transient
	private static LogRepository logRepository; 
	
	@Transient
	private static AbstractAuditorService service;
	
	//通知角色
	@ManyToMany
	@JoinTable(name = "web_advertise_type_role" , joinColumns = @JoinColumn(name="web_advertise_type_id") , inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	private String typeCode;
	
	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	@JsonIgnore
	@Transient
	public AbstractAuditorService getService() {
		return service;
	}

	@Override
	public void setService(AbstractAuditorService service) {
		WebAdvertiseType.service = service;
	}

	@Override
	@JsonIgnore
	@Transient
	public WebAdvertiseTypeLog getLogInstance() {
		return new WebAdvertiseTypeLog();
	}

	@Override
	@JsonIgnore
	@Transient
	public LogRepository getLogRepository() {
		return logRepository;
	}

	@Override
	public void setLogRepository(LogRepository logRepository) {
		WebAdvertiseType.logRepository = logRepository;
	}
	
	
	
}
