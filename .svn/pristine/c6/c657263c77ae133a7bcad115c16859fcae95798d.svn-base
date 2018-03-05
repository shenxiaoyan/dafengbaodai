package com.liyang.domain.customer;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.account.Account;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.dafengapi.DafengApiCustomer;
import com.liyang.domain.perbusinesscard.PerBusinessCard;
import com.liyang.domain.submitproposal.SubmitProposal;
import com.liyang.domain.submitproposal.SubmitProposalFile;
import com.liyang.domain.team.Team;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;

/**
 * 用户
 * 
 * @author Administrator
 */
@Entity
@Table(name = "customer", indexes = { @Index(name = "role_type", columnList = "label", unique = true) })
public class Customer
		extends AbstractWorkflowEntity<CustomerWorkflow, CustomerState, CustomerAct, CustomerLog, CustomerFile> {
	private static final long serialVersionUID = 12L;

	@SuppressWarnings("rawtypes")
	@Transient
	private static LogRepository logRepository;

	@SuppressWarnings("rawtypes")
	@Transient
	private static AbstractWorkflowService service;

	@Column(length = 40)
	@Info(label = "用户昵称")
	private String nickname;

	@Pattern(regexp = "man|female", message = "性别只能是man或者female")
	@Column(length = 7)
	@Info(label = "性别", secret = "只能是man或者female")
	private String sex;

	@Info(label = "头像")
	private String headimgurl;

	@Info(label = "被邀请码")
	private String invite;

	@Column(length = 50)
	@Info(label = "邀请码")
	private String myInvite;

	@Info(label = "用户唯一标识", placeholder = "", tip = "", help = "", secret = "")
	private String token;

	@Column(name = "push_token")
	@Info(label = "信鸽推送的token", placeholder = "", tip = "", help = "", secret = "")
	private String pushToken;

	@Info(label = "设备来源ios或者安卓", placeholder = "", tip = "", help = "", secret = "")
	private String client;

	@Info(label = "资料是否完整")
	private int tag;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	private Set<SubmitProposal> submitProposal;

	@OneToMany(cascade = CascadeType.ALL, targetEntity = SubmitProposalFile.class, mappedBy = "customer")
	private Set<SubmitProposalFile> submitProposalFile;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "dafeng_api_customer_id")
	private DafengApiCustomer dafengApiCustomer;

	// 手机号
	@Pattern(regexp = "1\\d{10}", message = "手机号码格式不正确")
	@Column(length = 13)
	private String phone;

	// 用户等级 粉丝或者普通用户 1是普通用户，2是粉丝
	@Max(value = 2, message = "用户等级只能是1或者2")
	@Min(value = 1, message = "用户等级只能是1或者2")
	private Integer grade;

	// 创建时间
	private Date createTime;

	// 更新时间
	private Date updateTime;

	@JoinColumn(name = "account_id", unique = true)
	@OneToOne
	@Info(label = "账户")
	private Account account;

	// 归属团队
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "team_id")
	@Info(label = "团队")
	private Team team;
	
	@Info(label = "加入团队时间")
	private Date joinTime;

	//个人名片
	@OneToOne
	@JoinColumn(name = "perBusinessCard_id")
	@Info(label = "个人名片")
	private PerBusinessCard perBusinessCard;

	public PerBusinessCard getPerBusinessCard() {
		return perBusinessCard;
	}

	public void setPerBusinessCard(PerBusinessCard perBusinessCard) {
		this.perBusinessCard = perBusinessCard;
	}

	/**
	 * 添加json获取状态
	 * 
	 * @return
	 */
	public String getStateCode() {
		return super.getState().getStateCode();
	}

	public DafengApiCustomer getDafengApiCustomer() {
		return dafengApiCustomer;
	}

	public void setDafengApiCustomer(DafengApiCustomer dafengApiCustomer) {
		this.dafengApiCustomer = dafengApiCustomer;
	}

	public String getMyInvite() {
		return myInvite;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public void setMyInvite(String myInvite) {
		this.myInvite = myInvite;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getInvite() {
		return invite;
	}

	public void setInvite(String invite) {
		this.invite = invite;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPushToken() {
		return pushToken;
	}

	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Set<SubmitProposal> getSubmitProposal() {
		return submitProposal;
	}

	public void setSubmitProposal(Set<SubmitProposal> submitProposal) {
		this.submitProposal = submitProposal;
	}

	public Set<SubmitProposalFile> getSubmitProposalFile() {
		return submitProposalFile;
	}

	public void setSubmitProposalFile(Set<SubmitProposalFile> submitProposalFile) {
		this.submitProposalFile = submitProposalFile;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	
	public Date getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	@Override
	@JsonIgnore
	@Transient
	public CustomerLog getLogInstance() {
		return new CustomerLog();
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
		Customer.logRepository = logRepository;
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
		Customer.service = (AbstractWorkflowService) service;
	}

}
