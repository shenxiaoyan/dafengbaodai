package com.liyang.domain.menu;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.IVisibility;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.role.Role;
import com.liyang.service.AbstractAuditorService;
import com.liyang.util.UpdateValidGroup;

/**
 * 菜单
 * @author Administrator
 *
 */
@Entity
@Table(name = "menu")
@Info(label = "模块", placeholder = "", tip = "", help = "", secret = "")
public class Menu extends AbstractAuditorEntity<MenuState, MenuAct, MenuLog> implements IVisibility {

	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 1L;

	@Transient
	private static LogRepository logRepository;

	@Transient
	private static AbstractAuditorService service;

	@ManyToMany
	@JoinTable(name = "menu_visible_roles", joinColumns = { @JoinColumn(name = "menu_id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id") })
	private Set<Role> visibleRoles;

	@NotNull(groups = { UpdateValidGroup.class })
	@Info(label = "排序", placeholder = "", tip = "", help = "", secret = "")
	private Integer sort;

	@Info(label = "等级", placeholder = "", tip = "", help = "", secret = "")
	private Integer level;

	@Column(name = "icon_class")
	@Info(label = "小图标class", placeholder = "", tip = "", help = "", secret = "")
	private String iconClass;

	@Info(label = "路由", placeholder = "", tip = "", help = "", secret = "")
	private String router;

	@Info(label = "链接", placeholder = "", tip = "", help = "", secret = "暂时无用")
	private String href;

	@Info(label = "实体名称", placeholder = "", tip = "", help = "", secret = "")
	private String entityName;

	@Column(name = "menu_group")
	@Info(label = "分组", placeholder = "", tip = "", help = "", secret = "也许未来有用")
	private String menuGroup;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@Info(label = "系统", placeholder = "", tip = "", help = "", secret = "")
	private Menu parent;

	@OneToMany(mappedBy = "parent")
	private Set<Menu> children = new HashSet<Menu>();

	@Transient
	@JsonIgnore
	private Integer parentId;

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public String getRouter() {
		return router;
	}

	public void setRouter(String router) {
		this.router = router;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getMenuGroup() {
		return menuGroup;
	}

	public void setMenuGroup(String menuGroup) {
		this.menuGroup = menuGroup;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public Set<Menu> getChildren() {
		return children;
	}

	public void setChildren(Set<Menu> children) {
		this.children = children;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Transient
	public Integer getParentId() {
		return parent == null ? 0 : parent.getId();
	}

	@Override
	public Set<Role> getVisibleRoles() {
		// TODO Auto-generated method stub
		return visibleRoles;
	}

	@Override
	public void setVisibleRoles(Set<Role> visibleRoles) {
		this.visibleRoles = visibleRoles;

	}

	@Override
	@JsonIgnore
	@Transient
	public MenuLog getLogInstance() {
		// TODO Auto-generated method stub
		return new MenuLog();
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
		Menu.logRepository = logRepository;

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
		Menu.service = service;

	}

}