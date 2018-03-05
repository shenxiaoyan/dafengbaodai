package com.liyang.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 *
 */
@Component
@ConfigurationProperties(prefix = "spring.wechat")
public class WechatProperties {
	private String appid;
	private String scope;
	private String redirectUri;
	private String secret;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	@Override
	public String toString() {
//		return "WechatProperties [appid=" + appid + ", scope=" + scope + ", redirect_uri=" + redirect_uri + ", secret="
//				+ secret + "]";
		StringBuilder sb = new StringBuilder();
		return sb.append("WechatProperties").append("[appid=")
				.append(appid) .append(",").append("scope=")  .append(scope)  .append(",").append("redirect_uri=")  
				.append(redirectUri)  .append(",").append("secret=")  .append(secret)  .append("]").toString();
		
	}

}
