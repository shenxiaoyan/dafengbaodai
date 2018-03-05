package com.liyang.client.tianan.dto;

import com.liyang.client.IDto;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * 平台车型信息
 * 
 * @author Administrator
 *
 */
public class ISCICarModelDTO implements IDto {
	private String tradeName;

	/**
	 * 厂商名称
	 * 
	 * @return the tradeName
	 */
	public String getTradeName() {
		return tradeName;
	}

	/**
	 * 厂商名称
	 * 
	 * @param tradeName
	 *            the tradeName to set
	 */
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	private String tradeCode;

	/**
	 * 厂商编码
	 * 
	 * @return the tradeCode
	 */
	public String getTradeCode() {
		return tradeCode;
	}

	/**
	 * 厂商编码
	 * 
	 * @param tradeCode
	 *            the tradeCode to set
	 */
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	private String brand;

	/**
	 * 品牌名称
	 * 
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * 品牌名称
	 * 
	 * @param brand
	 *            the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	private String brandCode;

	/**
	 * 品牌编码
	 * 
	 * @return the brandCode
	 */
	public String getBrandCode() {
		return brandCode;
	}

	/**
	 * 品牌编码
	 * 
	 * @param brandCode
	 *            the brandCode to set
	 */
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	private String series;

	/**
	 * 车系名称
	 * 
	 * @return the series
	 */
	public String getSeries() {
		return series;
	}

	/**
	 * 车系名称
	 * 
	 * @param series
	 *            the series to set
	 */
	public void setSeries(String series) {
		this.series = series;
	}

	private String seriesCode;

	/**
	 * 车系编码
	 * 
	 * @return the seriesCode
	 */
	public String getSeriesCode() {
		return seriesCode;
	}

	/**
	 * 车系编码
	 * 
	 * @param seriesCode
	 *            the seriesCode to set
	 */
	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}

	private String carName;

	/**
	 * 车款名称
	 * 
	 * @return the carName
	 */
	public String getCarName() {
		return carName;
	}

	/**
	 * 车款名称
	 * 
	 * @param carName
	 *            the carName to set
	 */
	public void setCarName(String carName) {
		this.carName = carName;
	}

	private String noticeType;

	/**
	 * 公告型号
	 * 
	 * @return the noticeType
	 */
	public String getNoticeType() {
		return noticeType;
	}

	/**
	 * 公告型号
	 * 
	 * @param noticeType
	 *            the noticeType to set
	 */
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	private String configType;

	/**
	 * 配置款型编码
	 * 
	 * @return the configType
	 */
	public String getConfigType() {
		return configType;
	}

	/**
	 * 配置款型编码
	 * 
	 * @param configType
	 *            the configType to set
	 */
	public void setConfigType(String configType) {
		this.configType = configType;
	}

	private String categoryName;

	/**
	 * 类别名称
	 * 
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * 类别名称
	 * 
	 * @param categoryName
	 *            the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	private String categoryCode;

	/**
	 * 类别编码
	 * 
	 * @return the categoryCode
	 */
	public String getCategoryCode() {
		return categoryCode;
	}

	/**
	 * 类别编码
	 * 
	 * @param categoryCode
	 *            the categoryCode to set
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	private String deptName;

	/**
	 * 系别名称
	 * 
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * 系别名称
	 * 
	 * @param deptName
	 *            the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	private String deptCode;

	/**
	 * 系别名称编码
	 * 
	 * @return the deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * 系别名称编码
	 * 
	 * @param deptCode
	 *            the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	private String remark;

	/**
	 * 备注
	 * 
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 备注
	 * 
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {

	}
}
