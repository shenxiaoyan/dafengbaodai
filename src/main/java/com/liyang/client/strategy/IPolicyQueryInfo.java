package com.liyang.client.strategy;

import java.util.List;

import org.slf4j.Logger;

import com.liyang.client.tianan.TypeDate;
import com.liyang.domain.platform.Platform;

/**
 * @author Administrator
 *
 */
public interface IPolicyQueryInfo {

	/**
	 * 获取城市代码
	 * @return
	 */
	String getCityCode();

	/**
	 * 开始时间
	 * @return
	 */
	String getStartDate();

	/**
	 * 保险类型
	 * @return
	 */
	String getPolicyCategoryType();

	/**
	 * 终止时间
	 * @return
	 */
	String getEndDate();

	/**
	 * 手机号码
	 * @return
	 */
	String getMobilePhone();
	
	/**
	 * 手机号码
	 * @param mobilePhone
	 */
	void setMobilePhone(String mobilePhone);

	/**
	 * 身份证号码
	 * @return
	 */
	String getCarOwnerIdentifyNumber();

	/**
	 * 订单list
	 * @return
	 */
	List<String> getProposalIdList();

	/**
	 * 获取平台标识
	 * @return
	 */
	Platform getPlatform();

}
