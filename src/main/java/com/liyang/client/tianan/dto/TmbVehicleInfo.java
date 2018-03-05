package com.liyang.client.tianan.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.liyang.client.IDto;
import com.liyang.client.tianan.TypeDate;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * 交管车辆信息
 * 
 * @author Administrator
 *
 */
public class TmbVehicleInfo implements IDto {
	private String searchSequenceNo;

	/**
	 * 车型查询码
	 * 
	 * @return the searchSequenceNo
	 */
	public String getSearchSequenceNo() {
		return searchSequenceNo;
	}

	/**
	 * 车型查询码
	 * 
	 * @param searchSequenceNo
	 *            the searchSequenceNo to set
	 */
	public void setSearchSequenceNo(String searchSequenceNo) {
		this.searchSequenceNo = searchSequenceNo;
	}

	private String licenseNo;

	/**
	 * 车牌号
	 * 
	 * @return the licenseNo
	 */
	public String getLicenseNo() {
		return licenseNo;
	}

	/**
	 * 车牌号
	 * 
	 * @param licenseNo
	 *            the licenseNo to set
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	private String licenseType;

	/**
	 * 车牌类型
	 * 
	 * @return the licenseType
	 */
	public String getLicenseType() {
		return licenseType;
	}

	/**
	 * 车牌类型
	 * 
	 * @param licenseType
	 *            the licenseType to set
	 */
	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	private String frameNo;

	/**
	 * 车架号
	 * 
	 * @return the frameNo
	 */
	public String getFrameNo() {
		return frameNo;
	}

	/**
	 * 车架号
	 * 
	 * @param frameNo
	 *            the frameNo to set
	 */
	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}

	private String enginNo;

	/**
	 * 发动机号
	 * 
	 * @return the enginNo
	 */
	public String getEnginNo() {
		return enginNo;
	}

	/**
	 * 发动机号
	 * 
	 * @param enginNo
	 *            the enginNo to set
	 */
	public void setEnginNo(String enginNo) {
		this.enginNo = enginNo;
	}

	private String carOwner;

	/**
	 * 车主姓名
	 * 
	 * @return the carOwner
	 */
	public String getCarOwner() {
		return carOwner;
	}

	/**
	 * 车主姓名
	 * 
	 * @param carOwner
	 *            the carOwner to set
	 */
	public void setCarOwner(String carOwner) {
		this.carOwner = carOwner;
	}

	private TypeDate enrollDate;

	/**
	 * 初次登记日期
	 * 
	 * @return the enrollDate
	 */
	public String getEnrollDate() {
		return enrollDate == null ? null : enrollDate.getString();
	}

	/**
	 * 初次登记日期
	 * 
	 * @param enrollDate
	 *            the enrollDate to set
	 */
	public void setEnrollDate(TypeDate enrollDate) {
		this.enrollDate = enrollDate;
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {

	}
}
