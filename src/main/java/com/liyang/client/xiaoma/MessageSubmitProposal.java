package com.liyang.client.xiaoma;

import java.util.HashMap;
import java.util.Map;

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
public class MessageSubmitProposal implements IMessage {
	private Platform platform;
	private Map<String, Object> subProMap;
	private String token;
	private Map<String, String> formParams;
	private HashMap<String, Object> reslutMap;

	public MessageSubmitProposal(Platform platform, Map<String, Object> subProMap, String token) throws Exception {
		this.platform = platform;
		this.subProMap = subProMap;
		this.setToken(token);
		this.initDefaultValue();
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {

	}

	@Override
	public void initDefaultValue() throws Exception {
		prepareValues(this);
	}
	
	private void prepareValues(MessageSubmitProposal message) {
		// 将原数据转换成小马接口需要的标准数据
		reslutMap = (HashMap<String, Object>) message.getSubProMap().get("params");

		String ownerName = null;
		if (null == reslutMap || reslutMap.isEmpty()) {
			throw new FailReturnObject(ExceptionResultEnum.SUBPROPOSAL_NULLINFO_ERROR);
		}
		formParams = new HashMap<String, String>();
		formParams.put("params", JSONObject.fromObject(reslutMap).toString());
	}

	/**
	 * @return the platform
	 */
	public Platform getPlatform() {
		return platform;
	}

	/**
	 * @param platform
	 *            the platform to set
	 */
	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	/**
	 * @return the subProMap
	 */
	public Map<String, Object> getSubProMap() {
		return subProMap;
	}

	/**
	 * @param subProMap
	 *            the subProMap to set
	 */
	public void setSubProMap(Map<String, Object> subProMap) {
		this.subProMap = subProMap;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the formParams
	 */
	public Map<String, String> getFormParams() {
		return formParams;
	}

	/**
	 * @param formParams the formParams to set
	 */
	public void setFormParams(Map<String, String> formParams) {
		this.formParams = formParams;
	}

	/**
	 * @return the reslutMap
	 */
	public HashMap<String, Object> getReslutMap() {
		return reslutMap;
	}

	/**
	 * @param reslutMap the reslutMap to set
	 */
	public void setReslutMap(HashMap<String, Object> reslutMap) {
		this.reslutMap = reslutMap;
	}

}
