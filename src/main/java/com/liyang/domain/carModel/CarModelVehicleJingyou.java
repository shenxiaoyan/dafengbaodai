package com.liyang.domain.carModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author huanghengkun
 * @create 2017-11-26-下午3:42
 */
@Entity
@Table(name = "car_model_vehicle_jingyou")
public class CarModelVehicleJingyou {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	// 车辆编码
	@Column(name = "vehicle_code")
	private String vehicleCode;
	// 车型名称
	@Column(name = "vehicle_name")
	private String vehicleName;
	// 车辆品牌
	@Column(name = "brand_name")
	private String brandName;
	// 精友车价
	@Column
	private Double price;
	// 车系
	@Column(name = "family_name")
	private String familyName;
	// 价格类型
	@Column(name = "price_type")
	private String priceType;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the vehicleCode
	 */
	public String getVehicleCode() {
		return vehicleCode;
	}

	/**
	 * @param vehicleCode
	 *            the vehicleCode to set
	 */
	public void setVehicleCode(String vehicleCode) {
		this.vehicleCode = vehicleCode;
	}

	/**
	 * @return the vehicleName
	 */
	public String getVehicleName() {
		return vehicleName;
	}

	/**
	 * @param vehicleName
	 *            the vehicleName to set
	 */
	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	/**
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}

	/**
	 * @param brandName
	 *            the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @return the familyName
	 */
	public String getFamilyName() {
		return familyName;
	}

	/**
	 * @param familyName
	 *            the familyName to set
	 */
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	/**
	 * @return the priceType
	 */
	public String getPriceType() {
		return priceType;
	}

	/**
	 * @param priceType
	 *            the priceType to set
	 */
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

}
