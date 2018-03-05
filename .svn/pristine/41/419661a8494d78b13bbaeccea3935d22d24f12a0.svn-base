package com.liyang.domain.timgroup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.BaseEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.menu.Menu;
import com.liyang.domain.menu.MenuAct;
import com.liyang.domain.menu.MenuLog;
import com.liyang.domain.menu.MenuState;
import com.liyang.service.AbstractAuditorService;
/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "tim_group")
public class TIMGroup extends AbstractAuditorEntity<TIMGroupState,TIMGroupAct,TIMGroupLog>  {

	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 1L;
	
	
	@Transient
	private static LogRepository logRepository;
	
	@Transient
	private static AbstractAuditorService service;
	@Column(name="owner_account")
	private String ownerAccount;
	private String name;
	@Enumerated(EnumType.STRING)
	private Type type;
	@Column(name="group_id")
	private String groupId;
	private String introduction;
	private String notification;
	@Column(name="face_url")
	private String faceUrl;
	@Column(name="max_member_count")
	private Integer maxMemberCount;
	@Enumerated(EnumType.STRING)
	@Column(name="apply_join_option")
	private Option applyJoinOption;
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	public String getFaceUrl() {
		return faceUrl;
	}

	public void setFaceUrl(String faceUrl) {
		this.faceUrl = faceUrl;
	}

	public Integer getMaxMemberCount() {
		return maxMemberCount;
	}

	public void setMaxMemberCount(Integer maxMemberCount) {
		this.maxMemberCount = maxMemberCount;
	}

	public Option getApplyJoinOption() {
		return applyJoinOption;
	}

	public void setApplyJoinOption(Option applyJoinOption) {
		this.applyJoinOption = applyJoinOption;
	}

	public String getOwnerAccount() {
		return ownerAccount;
	}

	public void setOwnerAccount(String ownerAccount) {
		this.ownerAccount = ownerAccount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public enum Type{
		Private,Public,ChatRoom,AVChatRoom,BChatRoom
	}
	public enum Option{
		FreeAccess,NeedPermission,DisableApply
	}


	@Override
	@JsonIgnore
	@Transient
	public TIMGroupLog getLogInstance() {
		// TODO Auto-generated method stub
		return new TIMGroupLog();
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
		TIMGroup.logRepository = logRepository;
		
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
		TIMGroup.service = service;
		
	}
	
}
