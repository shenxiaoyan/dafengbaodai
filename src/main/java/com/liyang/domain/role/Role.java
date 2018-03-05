package com.liyang.domain.role;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.authority.Authority;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.departmenttype.Departmenttype;
import com.liyang.domain.menu.Menu;
import com.liyang.service.AbstractAuditorService;
import com.liyang.util.CommonUtil;

/**
 * 角色
 * 
 * @author Administrator
 */
@Entity
@Table(name = "role")
public class Role extends AbstractAuditorEntity<RoleState, RoleAct, RoleLog>{

	@Transient
	private static final long serialVersionUID = 1L;
	
	@Transient
	private static LogRepository logRepository;
	
	@Transient
	private static AbstractAuditorService service;
	

//	@Enumerated(EnumType.STRING)
	@Column(name="role_code")
	@Info(label = "角色代码")
	private String roleCode;

	@ManyToMany(mappedBy="visibleRoles")
	private Set<Menu> visibleMenus;

	@ManyToOne
	@JoinColumn(name="departmenttype_id")
	private Departmenttype departmenttype;
	
	@Transient
	private List<?> visibleMenuTree;
	
	@JoinTable(name = "role_authority" , joinColumns = @JoinColumn(name="role_id") , inverseJoinColumns = @JoinColumn(name = "authority_id"))
	@ManyToMany
	private Set<Authority> authorities = new HashSet<Authority>();

	public Role() {
	}

	public Role(Integer id,String roleCode,String label,RoleState state,Date createdAt,Date lastModifiedAt) {
		this.setId(id);
		this.setState(state);
		this.setLabel(label);
		this.setCreatedAt(createdAt);
		this.setLastModifiedAt(lastModifiedAt);
		this.roleCode = roleCode;
	}

	public enum DefaultCode {
		ADMINISTRATOR,
		USER,
		SUPERADMIN
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	@Transient
	public  List<?> getVisibleMenuTree() {
		return  this.visibleMenuTree;
	}
	
	public void setupVisibleMenuTree(){
		this.visibleMenuTree = CommonUtil.listToTree(getVisibleMenus());
	}

	public Set<Menu> getVisibleMenus() {
		return visibleMenus;
	}

	public void setVisibleMenus(Set<Menu> visibleMenus) {
		this.visibleMenus = visibleMenus;
	}

	public Departmenttype getDepartmenttype() {
		return departmenttype;
	}

	public void setDepartmenttype(Departmenttype departmenttype) {
		this.departmenttype = departmenttype;
	}

	@Override
	@JsonIgnore
	@Transient
	public RoleLog getLogInstance() {
		return new RoleLog();
	}

	@Override
	@JsonIgnore
	@Transient
	public LogRepository getLogRepository() {
		return logRepository;
	}

	@Override
	public void setLogRepository(LogRepository logRepository) {
		Role.logRepository = logRepository;
		
	}

	@Override
	@JsonIgnore
	@Transient
	public AbstractAuditorService getService() {
		return service;
	}

	@Override
	public void setService(AbstractAuditorService service) {
		Role.service = service;
	}



	

}