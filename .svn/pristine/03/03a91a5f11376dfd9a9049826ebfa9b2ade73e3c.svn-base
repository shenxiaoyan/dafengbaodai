package com.liyang.domain.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.department.Department;
import com.liyang.domain.platform.Platform;
import com.liyang.domain.role.Role;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;

/**
 * web端用户
 * 
 * @author Administrator
 */
@Entity
@Table(name = "user")
@Info(label="user")
public class User extends AbstractWorkflowEntity<UserWorkflow, UserState, UserAct, UserLog, UserFile> implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Transient
	private static LogRepository logRepository;
	
	@Transient
	private static AbstractWorkflowService service;

//	@Column(unique=true)
	@Info(label = "用户名")
	private String username;

	@Info(label = "密码")
	private String password;
	@Transient
	private String passwordAgain;

	private Integer money;
	@Info(label="昵称",placeholder="微信来的昵称",tip="昵称",help="",secret="")
	private String nickname;
	
	@Info(label="性别",placeholder="",tip="",help="",secret="")
	private Integer sex;
	
	@Info(label="省份",placeholder="",tip="",help="",secret="")
	private String province;
	
	@Info(label="城市",placeholder="",tip="",help="",secret="")
	private String city;

	@Info(label="国家",placeholder="",tip="",help="",secret="")
	private String country;

	@Info(label="头像URL",placeholder="",tip="",help="",secret="")
	private String headimgurl;

	@Column(unique=true)
	@Info(label="OpenID",placeholder="",tip="",help="",secret="")
	private String openid;
	
	
	@JoinColumn(name="platform_id")
	@ManyToOne
	private Platform platform ; 
	
	public Platform getPlatform() {
		return platform;
	}


	public void setPlatform(Platform platform) {
		this.platform = platform;
	}



	@Column(length = 50, unique = true)
	@Info(label="微信unoinid",placeholder="",tip="多微信公从号唯一标识",help="",secret="")
	private String unionid;

	@Column(length = 500)
	@Info(label="TIM登录标识",placeholder="",tip="TIM登录标识",help="",secret="")
	private String sig;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "department_id")
	@Info(label="所属部门",placeholder="",tip="所属部门",help="",secret="")
	private Department department;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "role_id")
	@Info(label="所属角色",placeholder="",tip="所属角色",help="",secret="")
	private Role role;

	@JsonIgnore
	@Transient
	@Info(label="子部门",placeholder="",tip="子部门",help="",secret="")
	public List<Department> childrenDepartments;


	@Info(label="纬度",placeholder="",tip="",help="",secret="")
	private Double latitude;

	@Info(label="经度",placeholder="",tip="",help="",secret="")
	private Double longitude;

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public String getNickname() {
		return nickname;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}


	public String getSig() {
		return sig;
	}

	public void setSig(String sig) {
		this.sig = sig;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}


	@Override
	@JsonIgnore
	@Transient
	public UserLog getLogInstance() {
		// TODO Auto-generated method stub
		return new UserLog();
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
		User.logRepository = logRepository;
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
		User.service = (AbstractWorkflowService) service;
		
	}


	public void setChildrenDepartments(List<Department> ownAndChildrenDepartments) {
		this.childrenDepartments = ownAndChildrenDepartments;
		
	}

	/**
	 * 用户是否处于禁用状态
	 * @return
	 */
	public boolean isUserStateDisabled(){
		return "DISABLED".equals(getState().getStateCode());
	}

	/**
	 * 用户是否处于已删除状态
	 * @return
	 */
	public boolean isUserStateDeleted(){
		return "DELETED".equals(getState().getStateCode());
	}

	/**
	 * 用户是否处于审核中状态
	 * @return
	 */
	public boolean isUserStateApplied(){
		return "APPLIED".equals(getState().getStateCode());
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> auths = new ArrayList<>();
		Role role = this.getRole();
		auths.add(new SimpleGrantedAuthority(role.getRoleCode().toString()));
		return auths;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public String getPasswordAgain() {
		return passwordAgain;
	}

	public void setPasswordAgain(String passwordAgain) {
		this.passwordAgain = passwordAgain;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@Transient
	public boolean isEnabled() {
		return this.getState() == null ? false : "ENABLED".equals(this.getState().getStateCode());
	}

	@Override
	public int hashCode() {
		return username.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			return username.equals(((User) obj).getUsername());
		} else {
			return false;
		}
	}

}