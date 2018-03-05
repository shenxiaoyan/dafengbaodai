package com.liyang.domain.salesman;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.department.Department;
import com.liyang.service.AbstractAuditorService;

/**
 * 代理人
 * @author Administrator
 *
 */
@Entity
@Table(name="salesman",indexes = {@Index(name="salesman_name_department",columnList="name,department_id",unique=true)})
public class Salesman extends AbstractAuditorEntity<SalesmanState, SalesmanAct, SalesmanLog>{
	@Transient
	private static final long serialVersionUID = 1L;
	@Transient
	private static LogRepository logRepository;
	@Transient
	private static AbstractAuditorService service;
	 
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Integer id;
	@Info(label = "营业人员姓名")
	private String name; 

	@Info(label = "联系方式")
	private String phoneNumber;

	@Info(label = "启用状态")
	private Boolean enabled = true;

	@ManyToOne
	@JoinColumn(name = "department_id")
	@Info(label = "所属部门")
	private Department department;
	
	@Transient
	private Integer departmentId;
	
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
//	@JsonIgnore
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public String getDepartmentLabel() {
		if (this.department!=null && this.getDepartment().getLabel()!=null) {
			return this.department.getLabel();
		}else {
			return null;
		}
	}


	@Override
	@JsonIgnore
	@Transient
	public AbstractAuditorService getService() {
		return service;
	}

	@Override
	public void setService(AbstractAuditorService service) {
		Salesman.service=service;
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
		Salesman.logRepository=logRepository;
		
	}

	@Override
	@JsonIgnore
	@Transient
	public SalesmanLog getLogInstance() {
		return new SalesmanLog();
	}
	
	@Transient
	public String getStateCode() {
		if (null != getState()) {
			return getState().getStateCode();
		}
		return null;
	}
	
}







