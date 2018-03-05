package com.liyang.domain.insurercompany;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.platform.Platform;
import com.liyang.service.AbstractAuditorService;

/**
 * 保险公司
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "insure_company", indexes = {
		@Index(columnList = "insurer_company_id", name = "index_insurecompany_insurercompanyid", unique = true),
		@Index(columnList = "name", name = "index_insurecompany_name", unique = true) })

public class InsureCompany extends AbstractAuditorEntity<InsureCompanyState, InsureCompanyAct, InsureCompanyLog> {
	
	private static final long serialVersionUID = 12L;

	@SuppressWarnings("rawtypes")
	@Transient
	private static LogRepository logRepository;

	@SuppressWarnings("rawtypes")
	@Transient
	private static AbstractAuditorService service;

	@Column(name = "insurer_company_id")
	@Info(label = "保险公司Id")
	private int insurerCompanyId;

	@Info(label = "保险公司名称")
	private String name;
	
	private String account;
	
	private String password;

	@Max(value = 1, message = "状态只能是1或0")
	@Min(value = 0, message = "状态只能是1或0")
	@Info(label = "状态", secret = "1是启用 0是禁用")
	private int status;

	@Info(label = "属性", secret = "1是多选 0是单选")
	@Max(value = 1, message = "属性只能是1或0")
	@Min(value = 0, message = "属性只能是1或0")
	private int property;

	@Info(label = "列表icon")
	private String listIcon;

	@Info(label = "详情icon")
	private String detailIcon;

	@ManyToOne
	@JoinColumn(name = "platform_id")
	private Platform platform;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getStatus() {
		return this.getState().getId();
	}

	public int getProperty() {
		return property;
	}

	public void setProperty(int property) {
		this.property = property;
	}

	public String getListIcon() {
		return listIcon;
	}

	public void setListIcon(String listIcon) {
		this.listIcon = listIcon;
	}

	public String getDetailIcon() {
		return detailIcon;
	}

	public void setDetailIcon(String detailIcon) {
		this.detailIcon = detailIcon;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public int getInsurerCompanyId() {
		return insurerCompanyId;
	}

	public void setInsurerCompanyId(int insurerCompanyId) {
		this.insurerCompanyId = insurerCompanyId;
	}

	@Override
	@JsonIgnore
	@Transient
	public InsureCompanyLog getLogInstance() {
		return new InsureCompanyLog();
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
		InsureCompany.logRepository = logRepository;
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
		InsureCompany.service = service;

	}

	public InsureCompany(int insurerCompanyId, String name, int status, int property, String listIcon,
			String detailIcon, Platform platform) {
		super();
		this.insurerCompanyId = insurerCompanyId;
		this.name = name;
		this.status = status;
		this.property = property;
		this.listIcon = listIcon;
		this.detailIcon = detailIcon;
		this.platform = platform;
	}

	public InsureCompany(int id, String label, int insurerCompanyId, String name, InsureCompanyState state, int status,
			String listIcon, String detailIcon, Date createdAt, Date lastModifiedAt) {
		super();
		this.setId(id);
		this.setLabel(label);
		this.insurerCompanyId = insurerCompanyId;
		this.name = name;
		this.setState(state);
		this.status = status;
		this.setListIcon(listIcon);
		this.setDetailIcon(detailIcon);
		this.setCreatedAt(createdAt);
		this.setLastModifiedAt(lastModifiedAt);
	}

	public InsureCompany() {
	}

}
