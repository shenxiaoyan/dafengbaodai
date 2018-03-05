package com.liyang.domain.base;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.user.User;
/**
 * @author Administrator
 *
 * @param <E>
 * @param <S>
 * @param <A>
 */
@MappedSuperclass
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public abstract class AbstractAuditorLog<E extends AbstractAuditorEntity , S extends AbstractAuditorState , A extends AbstractAuditorAct> extends BaseEntity {
	


	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "entity_id")
	private E entity;
	
	@Lob
	@Info(label="字段变化对比",placeholder="",tip="",help="",secret="old和new")			
	private String difference;
	
	@Lob
	@Info(label="通知",placeholder="",tip="",help="",secret="")			
	private String notice;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "act_id")
	@Info(label="行为ID",placeholder="",tip="",help="",secret="")			
	private A act;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="before_state_id" )
	@Info(label="行为前状态ID",placeholder="",tip="",help="",secret="")			
	private S beforeState;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="after_state_id" )
	@Info(label="行为后状态ID",placeholder="",tip="",help="",secret="")			
	private S afterState;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="notice_to")
	@Info(label="通知给",placeholder="",tip="",help="",secret="")			
	private User noticeTo;
	
	@Enumerated(EnumType.STRING)
	@Column(name="act_group")
	@Info(label="行为分组",placeholder="",tip="",help="",secret="")			
	private ActGroup actGroup;
	
	@Column(name="app_code")
	@Info(label="来源的应用",placeholder="",tip="",help="",secret="")			
	private String appCode;
	
	@Column(name="client")
	@Info(label="来源终端",placeholder="",tip="",help="",secret="")			
	private String client;
	
	@Column(name="imei")
	@Info(label="来源设备",placeholder="",tip="",help="",secret="")			
	private String imei;

	@Column(name="ip")
	@Info(label="来源IP",placeholder="",tip="",help="",secret="如果是 web则只有IP没有imei")			
	private String ip;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public ActGroup getActGroup() {
		return actGroup;
	}

	public void setActGroup(ActGroup actGroup) {
		this.actGroup = actGroup;
	}

	public User getNoticeTo() {
		return noticeTo;
	}

	public void setNoticeTo(User user) {
		this.noticeTo = user;
	}

	public String getDifference() {
		return difference;
	}

	public void setDifference(String difference) {
		this.difference = difference;
	}

	public A getAct() {
		return act;
	}

	public void setAct(A act) {
		this.act = act;
	}

	public E getEntity() {
		return entity;
	}

	public void setEntity(E entity) {
		this.entity = entity;
	}
	public S getAfterState() {
		return afterState;
	}

	public void setAfterState(S afterState) {
		this.afterState = afterState;
	}
	public S getBeforeState() {
		return beforeState;
	}

	public void setBeforeState(S beforeState) {
		this.beforeState = beforeState;
	}

}
