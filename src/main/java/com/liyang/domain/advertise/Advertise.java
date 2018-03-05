package com.liyang.domain.advertise;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.offerresult.OfferResult;
import com.liyang.domain.user.User;
import com.liyang.domain.webadvertisetype.WebAdvertiseType;
import com.liyang.service.AbstractAuditorService;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "advertise", indexes = {
		@Index(name = "advertise_type_token", columnList = "type,token", unique = false) })
public class Advertise extends AbstractAuditorEntity<AdvertiseState, AdvertiseAct, AdvertiseLog> {
	private static final long serialVersionUID = 12L;

	@SuppressWarnings("rawtypes")
	@Transient
	private static LogRepository logRepository;

	@SuppressWarnings("rawtypes")
	@Transient
	private static AbstractAuditorService service;

	// @Max(value=18 , message="标题长不能超过18")
	@Info(label = "标题")
	private String title;

	@Column(length = 1000)
	@Info(label = "内容")
	private String content;

	@Info(label = "发布时间")
	private Date publishTime;

	@Info(label = "站内信类型", help = "1:offerResult,2:UnderWriting,3:insuranceResult,4:web insuranceResult,5:web offerResult")
	private Integer znxType;

	@OneToOne(cascade = CascadeType.ALL, targetEntity = OfferResult.class)
	@JoinColumn(name = "offer_result_id")
	@Info(label = "报价")
	private OfferResult offerResult;

	@Info(label = "类型标识", secret = "1是公告 ，2是站内信，3是web端消息")
	private Integer type;

	@Info(label = "是否已读", secret = "0是未读 1是已读")
	private Integer isRead;

	private String token;

	@Column(name = "offerId")
	private String offerId;

	private String createEnqId;

	@ManyToOne
	@JoinColumn(name = "web_advertise_type_id")
	private WebAdvertiseType webAdvertiseType;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	private String linkUrl;

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public WebAdvertiseType getWebAdvertiseType() {
		return webAdvertiseType;
	}

	public void setWebAdvertiseType(WebAdvertiseType webAdvertiseType) {
		this.webAdvertiseType = webAdvertiseType;
	}

	public String getCreateEnqId() {
		return createEnqId;
	}

	public void setCreateEnqId(String createEnqId) {
		this.createEnqId = createEnqId;
	}

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}

	public OfferResult getOfferResult() {
		return offerResult;
	}

	public void setOfferResult(OfferResult offerResult) {
		this.offerResult = offerResult;
	}

	public Integer getZnxType() {
		return znxType;
	}

	public void setZnxType(Integer znxType) {
		this.znxType = znxType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	@Override
	@JsonIgnore
	@Transient
	public AdvertiseLog getLogInstance() {
		return new AdvertiseLog();
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
		Advertise.logRepository = logRepository;
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
		Advertise.service = service;
	}

	public Advertise() {
	}

}
