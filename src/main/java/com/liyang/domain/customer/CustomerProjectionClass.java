package com.liyang.domain.customer;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.liyang.domain.account.Account;

/**
 * 类说明
 * @author Administrator
 *
 */
@Component
public class CustomerProjectionClass {
		private Integer id ; 
	
		private String nickname;
		
		private String sex;
	
		private String headimgurl;
		
		private String invite;

		private String myInvite;
		
		private String token;   
		
		private String pushToken; 
		
		private String client;

		private int tag ;  
		
		private String phone;
		
		private Integer grade;

		private Date createTime;
		
		private Date updateTime;
		
		private Account account ; 

		
		public Account getAccount() {
			return account;
		}

		public void setAccount(Account account) {
			this.account = account;
		}

		public String getNickname() {
			return nickname;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
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

		public String getMyInvite() {
			return myInvite;
		}

		public void setMyInvite(String myInvite) {
			this.myInvite = myInvite;
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

		public String getClient() {
			return client;
		}

		public void setClient(String client) {
			this.client = client;
		}

		public int getTag() {
			return tag;
		}

		public void setTag(int tag) {
			this.tag = tag;
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
		
}
