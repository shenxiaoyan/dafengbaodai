package com.liyang.client.xiaoma;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;

import java.util.Map.Entry;

import com.liyang.client.IMessage;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;
import com.liyang.domain.platform.Platform;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.util.FailReturnObject;

import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 */
public class MessageCreateEnquiry implements IMessage {

	private String token;
	private Platform platform;
	private Map<String, Object> creEnqMap;
	private Map<String, String> reqHeaderMap;
	private Map<String, String> handerCreEnqMap;
	private Logger logger;
	private String UUIDString;
	private String[] insurComArray;

	public MessageCreateEnquiry(Logger logger, String token, Platform platform, Map<String, Object> creEnqMap)
			throws Exception {
		this.setLogger(logger);
		this.setToken(token);
		this.setPlatform(platform);
		this.setCreEnqMap(creEnqMap);
		initDefaultValue();
		validate();
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {
		//TODO
	}

	/**
	 * // 生成唯一键 格式 101-年月日时分秒毫秒-4位随机数
	 * @return
	 */
	private String createUnique() {
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = String.format("%02d", calendar.get(Calendar.MONTH) + 1);
		String day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
		String hour = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY));
		String minute = String.format("%02d", calendar.get(Calendar.MINUTE));
		String second = String.format("%02d", calendar.get(Calendar.SECOND));
		String milliSecond = String.format("%03d", calendar.get(Calendar.MILLISECOND));
		String time = year + month + day + hour + minute + second + milliSecond;
		UUID id = UUID.randomUUID();
		String[] idd = id.toString().split("-");
		return "101-" + time + "-" + idd[1];
	}

	@Override
	public void initDefaultValue() throws Exception {
		// String UUIDString = UUID.randomUUID().toString();
		// String UUIDString = createUnique();
		setUUIDString(createUnique());

		// 在原数据中增加applicationId与报价唯一标识offerUnique
		@SuppressWarnings("unchecked")
		Map<String, Object> createEnquiryParamsMap = (HashMap<String, Object>) getCreEnqMap().get("createEnquiryParams");
		if (null == createEnquiryParamsMap || createEnquiryParamsMap.isEmpty()) {
			throw new FailReturnObject(ExceptionResultEnum.CREQUERY_DATA_ERROR);
		}
		// 前台requestHeader格式:{"requestHeader":"{}"},
		// DAFENG API has data;
		// Map<String, String> reqHeaderMap = new HashMap<String, String>();
		reqHeaderMap = new HashMap<String, String>();

		// (Map<String, Object>) createEnquiryParamsMap.get("requestHeader");

		String insurComName = (String) createEnquiryParamsMap.get("insuranceCompanyName");
		if (null == insurComName || insurComName.trim().equalsIgnoreCase("")) {
			throw new FailReturnObject(ExceptionResultEnum.CREQUERY_DATA_INSURECOM_ERROR);
		}
		String oriReqHeaderStr = (String) createEnquiryParamsMap.get("requestHeader");

		// String[] insurComArray = insurComName.split(",");
		insurComArray = insurComName.split(",");
		// requestHeader中带入唯一标识与报价结果关联
		reqHeaderMap.put("offerUnique", getUUIDString());
		reqHeaderMap.put("applicationId", getPlatform().getApplicationId());
		if (oriReqHeaderStr != null) {
			reqHeaderMap.put("oriReqHeaderStr", oriReqHeaderStr);
		}

		createEnquiryParamsMap.put("requestHeader", JSONObject.fromObject(reqHeaderMap).toString());
		getCreEnqMap().put("createEnquiryParams", createEnquiryParamsMap);

		// 询价与成单量规则
		enquiryRule(getPlatform());

		// Map<String, String> handerCreEnqMap = new HashMap<String, String>();
		setHanderCreEnqMap(new HashMap<String, String>());
		// 原数据转换成小马接口需要的Map格式
		getHanderCreEnqMap().put("createEnquiryParams",
				JSONObject.fromObject(getCreEnqMap().get("createEnquiryParams")).toString());
		getHanderCreEnqMap().put("mobilePhone", (String) getCreEnqMap().get("mobilePhone"));
		// 将消息发送给小马

		getLogger().info("【询价发送给小马数据 handerCreEnqMap 如下】:" + getHanderCreEnqMap() + "\n");
		for (Entry<String, String> maps : getHanderCreEnqMap().entrySet()) {
			getLogger().info(maps.getKey() + ":" + maps.getValue());
		}
	}

	public void enquiryRule(Platform platform) throws Exception {
		// 询价次数
		int quiryFrequency = platform.getQuiryFrequency(); 
		// 成交量次数
		int clinchFrequency = platform.getClinchFrequency(); 
		// 查询比与成交量到一定成比例时，关闭平台功能,该规则自定义
		if (quiryFrequency / (clinchFrequency + 1) > 1000) {
			platform.setEnable("0");
			throw new FailReturnObject(ExceptionResultEnum.PLATFORM_FREQ_DISABLE_ERROR);
		}
		platform.setQuiryFrequency(quiryFrequency + 1);
	}

	/**
	 * @return the handerCreEnqMap
	 */
	public Map<String, String> getHanderCreEnqMap() {
		return handerCreEnqMap;
	}

	/**
	 * @param handerCreEnqMap
	 *            the handerCreEnqMap to set
	 */
	public void setHanderCreEnqMap(Map<String, String> handerCreEnqMap) {
		this.handerCreEnqMap = handerCreEnqMap;
	}

	/**
	 * @return the creEnqMap
	 */
	public Map<String, Object> getCreEnqMap() {
		return creEnqMap;
	}

	/**
	 * @param creEnqMap
	 *            the creEnqMap to set
	 */
	public void setCreEnqMap(Map<String, Object> creEnqMap) {
		this.creEnqMap = creEnqMap;
	}

	/**
	 * @return the reqHeaderMap
	 */
	public Map<String, String> getReqHeaderMap() {
		return reqHeaderMap;
	}

	/**
	 * @param reqHeaderMap
	 *            the reqHeaderMap to set
	 */
	public void setReqHeaderMap(Map<String, String> reqHeaderMap) {
		this.reqHeaderMap = reqHeaderMap;
	}

	/**
	 * @return the uUIDString
	 */
	public String getUUIDString() {
		return UUIDString;
	}

	/**
	 * @param uUIDString
	 *            the uUIDString to set
	 */
	public void setUUIDString(String uUIDString) {
		UUIDString = uUIDString;
	}

	/**
	 * @return the insurComArray
	 */
	public String[] getInsurComArray() {
		return insurComArray;
	}

	/**
	 * @param insurComArray
	 *            the insurComArray to set
	 */
	public void setInsurComArray(String[] insurComArray) {
		this.insurComArray = insurComArray;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the platform
	 */
	public Platform getPlatform() {
		return platform;
	}

	/**
	 * @param platform the platform to set
	 */
	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * @param logger the logger to set
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

}
