package com.liyang.domain.department;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.departmenttype.Departmenttype;
import com.liyang.domain.user.User;
import com.liyang.service.AbstractAuditorService;


/**
 * 部门信息
 * @author Administrator
 *
 */
@Entity
@Table(name = "department", indexes = {
		@Index(name = "department_type_label", columnList = "departmenttype_id,label", unique = true) })
@Cacheable
public class Department extends AbstractAuditorEntity<DepartmentState,DepartmentAct,DepartmentLog>{
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 1L;
	
	@Transient
	private static LogRepository logRepository;
	
	@Transient
	private static AbstractAuditorService service;

	@ManyToOne
	@JoinColumn(name = "departmenttype_id")
	@Info(label = "部门类型")
	private Departmenttype type;
	
	@Info(label = "排序")
	private Integer sort;
	
	@OneToMany(mappedBy = "department")
	@JsonIgnore
    private Set<User> employees;
	
//	@Column(length=50)
//	private String departmentName ;
	@Info(label = "部门地址")
	private String address ;
	
	@Column(length=50)
	@Info(label = "联系人")
	private String attactPerson;
	@Column(length=50)
	@Info(label = "联系方式")
	private String attactPhone;
	private String logo;
	@Info(label = "描述")
	private String description;
	

//	private DepartmentState state ;
//	
//	@ManyToOne(fetch=FetchType.EAGER)
//	@JoinColumn(name = "state_id")
//	@Info(label="状态",placeholder="",tip="",help="",secret="")	
//	private S state;
	
//	public String getDepartmentName() {
//		return departmentName;
//	}
//
//
//
//	public void setDepartmentName(String departmentName) {
//		this.departmentName = departmentName;
//	}



//	public DepartmentState getState() {
//		return state;
//	}
//
//
//
//	public void setState(DepartmentState state) {
//		this.state = state;
//	}

	@Transient
	@Info(label = "部门类型id", secret = "只用于数据传输")
	private Integer typeId;

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getAttactPerson() {
		return attactPerson;
	}



	public void setAttactPerson(String attactPerson) {
		this.attactPerson = attactPerson;
	}



	public String getAttactPhone() {
		return attactPhone;
	}



	public void setAttactPhone(String attactPhone) {
		this.attactPhone = attactPhone;
	}



	public String getLogo() {
		return logo;
	}



	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	//	@JsonBackReference(value="departmentTree")
	@ManyToOne(fetch=FetchType.LAZY)
	private Department parent;
	
//	@JsonManagedReference(value="departmentTree")
	@OneToMany(mappedBy = "parent")  
	private Set<Department> children = new HashSet<Department>();
	
	@Transient
	@JsonIgnore
	private Integer parentId;
		

	public Departmenttype getType() {
		return type;
	}



	public void setType(Departmenttype type) {
		this.type = type;
	}



	public Set<User> getEmployees() {
		return employees;
	}



	public void setEmployees(Set<User> employees) {
		this.employees = employees;
	}



	public Department getParent() {
		return parent;
	}



	public void setParent(Department parent) {
		this.parent = parent;
	}



	public Set<Department> getChildren() {
		return children;
	}



	public void setChildren(Set<Department> children) {
		this.children = children;
	}



	public Integer getSort() {
		// TODO Auto-generated method stub
		return sort;
	}



	public void setSort(Integer sort) {
		this.sort = sort;
		
	}



	public Integer getParentId() {
		// TODO Auto-generated method stub
		return parent == null?0:parent.getId();
	}



	public void setParentId(Integer id) {
		this.parentId = id;
		
	}




	@Override
	@JsonIgnore
	@Transient
	public DepartmentLog getLogInstance() {
		// TODO Auto-generated method stub
		return new DepartmentLog();
	}



	@Override
	@JsonIgnore
	@Transient
	public LogRepository getLogRepository() {
		return logRepository;
	}
	@Override
	public void setLogRepository(LogRepository logRepository) {
		Department.logRepository = logRepository;
		
	}


	@JsonIgnore
	@Override
	@Transient
	public AbstractAuditorService getService() {
		// TODO Auto-generated method stub
		return service;
	}



	@Override
	public void setService(AbstractAuditorService service) {
		// TODO Auto-generated method stub
		Department.service = service;
	}



	













}