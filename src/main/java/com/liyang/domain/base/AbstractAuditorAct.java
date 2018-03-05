package com.liyang.domain.base;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.liyang.annotation.Info;
import com.liyang.annotation.Info.FLAG;
import com.liyang.domain.departmenttype.Departmenttype;
import com.liyang.domain.role.Role;
import com.liyang.domain.user.User;
import com.liyang.message.EnumOperationMessageType;
/**
 * @author Administrator
 *
 * @param <S>
 */
@MappedSuperclass
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class AbstractAuditorAct<S extends AbstractAuditorState> extends BaseEntity implements Comparable<AbstractAuditorAct>{
	
	public AbstractAuditorAct(String label, String actCode, Integer sort, ActGroup actGroup) {
		super();
		this.actCode = actCode;
		this.sort = sort;
		this.actGroup = actGroup;
		this.setLabel(label);
	}

	public AbstractAuditorAct(){
		super();
	}
	
	@Lob
	@Column(name="notice_self_template")
	@Info(label="消息模板（给自己）",placeholder="消息模板（给自己）",tip="",help="",secret="注意里面可以有变量")		
	private String noticeSelfTemplate;
	
	@Lob
	@Column(name="notice_show_template")
	@Info(label="消息模板（给引导人）",placeholder="消息模板（给引导人）",tip="",help="",secret="注意里面可以有变量")		
	private String noticeShowTemplate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="notice_departmenttype_id")
	@Info(label="通知给某类部门",placeholder="通知给某类部门",tip="",help="",secret="关联到departmenttype")		
	private Departmenttype noticeDepartmentType;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="notice_role_id")
	@Info(label="通知给访部门的某种角色",placeholder="通知给访部门的某种角色",tip="",help="",secret="关联到role")		
	private Role noticeRole;
	
	@Info(flag=FLAG.True,label="是",placeholder="",tip="",help="",secret="")
	@Info(flag=FLAG.False,label="否",placeholder="",tip="",help="",secret="")
	@Info(flag=FLAG.self,label="是否可以手动通知",placeholder="",tip="",help="",secret="界面上需要做通知")	
	private Boolean handNotice=false;
	
	@Lob
	@Column(name="notice_other_template")
	@Info(label="消息模板（给其它人）",placeholder="消息模板（给其它人）",tip="",help="",secret="注意里面可以有变量")		
	private String noticeOtherTemplate;
	
	/**
	 * 增加字段 
	 */
	@Column(name = "icon_class")
	@Info(label="按钮前的小图标Class",placeholder="",tip="",help="",secret="")		
	private String iconClass;
	
	@Column(name = "btn_class")
	@Info(label="按钮本身的Class",placeholder="",tip="",help="",secret="")		
	private String btnClass;
	
	@Info(label="行为短名",placeholder="",tip="",help="",secret="按钮上显示是这个名称")		
	private String alias;
	
	@Lob
	@Info(label="内部备注",placeholder="",tip="",help="",secret="")		
	private String secret;
	
	@Lob
	@Info(label="使用帮助",placeholder="",tip="",help="",secret="暂时还不知道按钮哪里需要帮助")		
	private String help;
	
	@Info(label="按钮提示",placeholder="",tip="",help="悬停的时候提示的信息",secret="")		
	private String tip;

	@JsonIgnore
	@ManyToMany(mappedBy="acts")
	private Set<S> states;
	
	@Column(name="act_code")
	@Info(label="行业标识code",placeholder="",tip="",help="小驼峰式写法",secret="")		
	private String actCode;
	
	@Info(label="排序",placeholder="",tip="",help="",secret="")		
	private Integer sort=0;
	
	@Info(flag=FLAG.True,label="是",placeholder="",tip="",help="",secret="")
	@Info(flag=FLAG.False,label="否",placeholder="",tip="",help="",secret="")
	@Info(flag=FLAG.self,label="该行为是否有弹窗视图",placeholder="",tip="",help="目前设计：引用该实体下的act_CODENAME.html视图",secret="实际上可能用不到")		
	private Boolean view=false;

	@Info(flag=FLAG.True,label="是",placeholder="",tip="",help="",secret="")
	@Info(flag=FLAG.False,label="否",placeholder="",tip="",help="",secret="")
	@Info(flag=FLAG.self,label="该行为是否重要",placeholder="",tip="",help="",secret="")		
	private Boolean importance=false;
	
	@Enumerated(EnumType.STRING)
	@Column(name="message_type")
	@Info(label="显示类型",placeholder="",tip="",help="在行为流转记录里用什么样的模板来解析",secret="")		
	private EnumOperationMessageType messageType;
	
	@ManyToMany(cascade=CascadeType.REFRESH)
	@JoinTable(joinColumns = { @JoinColumn(name = "act_id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id") })
	private Set<Role> roles = new HashSet<Role>();

	
	@Enumerated(EnumType.STRING)
	@Column(name="act_group")
	@Info(label="行为分组",placeholder="",tip="",help="主要用于分类读取或显示",secret="")		
	private ActGroup actGroup;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "message_sender")
	@Info(label="消息发送账号",placeholder="",tip="",help="",secret="")
	private User messageSender;


	public User getMessageSender() {
		return messageSender;
	}

	public void setMessageSender(User messageSender) {
		this.messageSender = messageSender;
	}


	public EnumOperationMessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(EnumOperationMessageType messageType) {
		this.messageType = messageType;
	}
	public Boolean getImportance() {
		return importance;
	}
	public void setImportance(Boolean importance) {
		this.importance = importance;
	}
	
	public Boolean getView() {
		return view;
	}
	public void setView(Boolean view) {
		this.view = view;
	}
	public ActGroup getActGroup() {
		return actGroup;
	}
	public void setActGroup(ActGroup actGroup) {
		this.actGroup = actGroup;
	}
	


	/* (non-Javadoc)
	 * @see com.liyang.domain.a#getSort()
	 */
	public Integer getSort() {
		return sort;
	}


	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public String getBtnClass() {
		return btnClass;
	}

	public void setBtnClass(String btnClass) {
		this.btnClass = btnClass;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}



	public String getActCode() {
		return actCode;
	}


	public void setActCode(String actCode) {
		this.actCode = actCode;
	}


	public Set<Role> getRoles() {
		return roles;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


	public Set<S> getStates() {
		return states;
	}


	public void setStates(Set<S> states) {
		this.states = states;
	}


	/* (non-Javadoc)
	 * @see com.liyang.domain.AbstractWorkflowActProjection#getHandNotice()
	 */
	/* (non-Javadoc)
	 * @see com.liyang.domain.a#getHandNotice()
	 */
	public Boolean getHandNotice() {
		return handNotice;
	}

	public void setHandNotice(Boolean handNotice) {
		this.handNotice = handNotice;
	}
	

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	
	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getNoticeSelfTemplate() {
		return noticeSelfTemplate;
	}

	public void setNoticeSelfTemplate(String noticeSelfTemplate) {
		this.noticeSelfTemplate = noticeSelfTemplate;
	}

	public String getNoticeShowTemplate() {
		return noticeShowTemplate;
	}

	public void setNoticeShowTemplate(String noticeShowTemplate) {
		this.noticeShowTemplate = noticeShowTemplate;
	}

	public Departmenttype getNoticeDepartmenttype() {
		return noticeDepartmentType;
	}

	public void setNoticeDepartmenttype(Departmenttype noticeDepartmenttype) {
		this.noticeDepartmentType = noticeDepartmenttype;
	}

	public Role getNoticeRole() {
		return noticeRole;
	}

	public void setNoticeRole(Role noticeRole) {
		this.noticeRole = noticeRole;
	}

	public String getNoticeOtherTemplate() {
		return noticeOtherTemplate;
	}

	public void setNoticeOtherTemplate(String noticeOtherTemplate) {
		this.noticeOtherTemplate = noticeOtherTemplate;
	}

	@Override
	public int compareTo(AbstractAuditorAct o) {
		// TODO Auto-generated method stub
		if(getSort() > o.getSort()){
			return 1;
		}else if(getSort() == o.getSort()){
			return 0;
		}else{
			return -1;
		}
	}



	public static enum ActGroup{
		@Info(label="读取类行为",placeholder="",tip="",help="listOwn等那些读取类行为",secret="")		
		READ,
		@Info(label="更新类行为",placeholder="",tip="",help="改某个字段或改关联",secret="")		
		UPDATE,
		@Info(label="文件类行为",placeholder="",tip="",help="上传、下载文件等",secret="")		
		FILE_OPERATE,
		@Info(label="普通操作",placeholder="",tip="",help="除了文件和增删改查之外的操作",secret="")
		OPERATE,
		@Info(label="通知",placeholder="",tip="",help="通知类行为",secret="")		
		NOTICE,
		@Info(label="文件打包",placeholder="",tip="",help="历史上传文件打包",secret="")	
		FILE_PACKAGE,
		
	}

}
