package com.liyang.domain.departmenttype;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.core.annotation.Order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.department.Department;
import com.liyang.domain.role.Role;
import com.liyang.service.AbstractAuditorService;


/**
 * 部门类型
 * @author Administrator
 *
 */
@Entity
@Table(name = "departmenttype", indexes = { @Index(name = "departmenttype_label", columnList = "label", unique = true),
		@Index(name = "departmenttype_code", columnList = "code", unique = true) })
@Order(11111111)
public class Departmenttype extends AbstractAuditorEntity<DepartmenttypeState,DepartmenttypeAct,DepartmenttypeLog>{

	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 1L;
	
	@Transient
	private static LogRepository logRepository;
	
	@Transient
	private static AbstractAuditorService service;
	

	@Column(name="contact_name")
	@Info(label = "联系人")
	private String contactName;
	
	@Column(name="contact_phone")
	@Info(label = "联系方式")
	private String contactPhone;
	
	@Column(name="contact_address")
	@Info(label = "联系地址")
	private String contactAddress;

	@Info(label = "部门类型代码")
	private String code;
	
//	@Enumerated(EnumType.STRING)
//	@Column(name="departmenttype_code")
//	private DepartmenttypeCode departmenttypeCode;

	
	
	
	@OneToMany(mappedBy = "type")
	private Set<Department> departments;
	
	
	@OneToMany(mappedBy = "departmenttype")
	private Set<Role> roles;


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getContactName() {
		return contactName;
	}


	public void setContactName(String contactName) {
		this.contactName = contactName;
	}


	public String getContactPhone() {
		return contactPhone;
	}


	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}


	public String getContactAddress() {
		return contactAddress;
	}


	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	
	public Set<Department> getDepartments() {
		return departments;
	}


	public void setDepartments(Set<Department> departments) {
		this.departments = departments;
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
	public AbstractAuditorService<?, DepartmenttypeState, DepartmenttypeAct, DepartmenttypeLog> getService() {
		return service;
	}


	@Override
	public void setService(AbstractAuditorService service) {
		Departmenttype.service = service;
	}


	@Override
	@JsonIgnore
	@Transient
	public DepartmenttypeLog getLogInstance() {
		return new DepartmenttypeLog();
	}


	@Override
	@JsonIgnore
	@Transient
	public LogRepository getLogRepository() {
		return logRepository;
	}


	@Override
	public void setLogRepository(LogRepository logRepository) {
		Departmenttype.logRepository = logRepository;
	}
	
//	public static enum DepartmenttypeCode{
//		STORE, 
//		GROUP, 
//		WULIU_COMPANY, 
//		CREDITOR, 
//		DEBTOR, 
//		LAW, 
//		DISTRIBUTOR
//	}
	








}