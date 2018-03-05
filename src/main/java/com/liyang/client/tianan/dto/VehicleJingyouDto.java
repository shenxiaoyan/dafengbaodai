package com.liyang.client.tianan.dto;

import org.springframework.util.StringUtils;

import com.liyang.client.IDto;
import com.liyang.client.tianan.enums.PriceTypeEnums;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * 精友整车库信息
 * 
 * @author Administrator
 *
 */
public class VehicleJingyouDto implements IDto {
	private String vehicleCode;

	/**
	 * 车辆编码
	 * 
	 * @return the vehicleCode
	 */
	public String getVehicleCode() {
		return vehicleCode;
	}

	/**
	 * 车辆编码
	 * 
	 * @param vehicleCode
	 *            the vehicleCode to set
	 */
	public void setVehicleCode(String vehicleCode) {
		this.vehicleCode = vehicleCode;
	}

	private String vehicleName;

	/**
	 * 车型名称
	 * 
	 * @return the vehicleName
	 */
	public String getVehicleName() {
		return vehicleName;
	}

	/**
	 * 车型名称
	 * 
	 * @param vehicleName
	 *            the vehicleName to set
	 */
	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	private String brandName;

	/**
	 * 车辆品牌
	 * 
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}

	/**
	 * 车辆品牌
	 * 
	 * @param brandName
	 *            the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	private Double price;

	/**
	 * 精友车价
	 * 
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * 精友车价
	 * 
	 * @param price
	 *            the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	private String familyName;

	/**
	 * 车系
	 * 
	 * @return the familyName
	 */
	public String getFamilyName() {
		return familyName;
	}

	/**
	 * 车系
	 * 
	 * @param familyName
	 *            the familyName to set
	 */
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	private String priceType;

	/**
	 * 价格类型
	 * 
	 * @return the priceType
	 */
	public String getPriceType() {
		return priceType;
	}

	/**
	 * 价格类型
	 * 
	 * @param priceType
	 *            the priceType to set
	 */
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {
		if (StringUtils.isEmpty(this.getVehicleCode())) {
			throw new ExceptionTiananParamInvliad("车辆编码不能为空");
		}
		if (StringUtils.isEmpty(this.getVehicleName())) {
			throw new ExceptionTiananParamInvliad("车型名称不能为空");
		}
		if (this.getPrice() == null) {
			throw new ExceptionTiananParamInvliad("精友车价不能为空");
		}
		if (StringUtils.isEmpty(this.getFamilyName())) {
			throw new ExceptionTiananParamInvliad("车系不能为空");
		}
		if (StringUtils.isEmpty(this.getPriceType())) {
			throw new ExceptionTiananParamInvliad("价格类型不能为空");
		} else {
			if (!PriceTypeEnums.isExist(this.getPriceType())) {
				throw new ExceptionTiananParamInvliad("价格类型不匹配");
			}
		}

	}

	public VehicleJingyouDto(String vehicleCode, String vehicleName, String brandName, Double price, String familyName,
			String priceType) {
		this.vehicleCode = vehicleCode;
		this.vehicleName = vehicleName;
		this.brandName = brandName;
		this.price = price;
		this.familyName = familyName;
		this.priceType = priceType;
	}

	public VehicleJingyouDto() {
	}

}
