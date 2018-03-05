package com.liyang.domain.carModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 交管所车辆信息,上海地区有值，非上海地区为null
 * 
 * @author huanghengkun
 * @create 2017-11-26-下午3:45
 */
@Entity
@Table(name = "car_model_tmb_vehicle_info")
public class CarModelTmbVehicleInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	// 车型查询码
	@Column(name = "search_sequence_no")
	private String searchSequenceNo;
	// 车牌号
	@Column(name = "license_no")
	private String licenseNo;
	// 车牌类型
	@Column(name = "license_type")
	private String licenseType;
	// 车架号
	@Column(name = "frame_no")
	private String frameNo;
	// 发动机号
	@Column(name = "engin_no")
	private String enginNo;
	// 车主姓名
	@Column(name = "car_owner")
	private String carOwner;
	// 初次登记日期
	@Column(name = "enroll_date")
	private String enrollDate;

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
	 * @return the searchSequenceNo
	 */
	public String getSearchSequenceNo() {
		return searchSequenceNo;
	}

	/**
	 * @param searchSequenceNo
	 *            the searchSequenceNo to set
	 */
	public void setSearchSequenceNo(String searchSequenceNo) {
		this.searchSequenceNo = searchSequenceNo;
	}

	/**
	 * @return the licenseNo
	 */
	public String getLicenseNo() {
		return licenseNo;
	}

	/**
	 * @param licenseNo
	 *            the licenseNo to set
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	/**
	 * @return the licenseType
	 */
	public String getLicenseType() {
		return licenseType;
	}

	/**
	 * @param licenseType
	 *            the licenseType to set
	 */
	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	/**
	 * @return the frameNo
	 */
	public String getFrameNo() {
		return frameNo;
	}

	/**
	 * @param frameNo
	 *            the frameNo to set
	 */
	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}

	/**
	 * @return the enginNo
	 */
	public String getEnginNo() {
		return enginNo;
	}

	/**
	 * @param enginNo
	 *            the enginNo to set
	 */
	public void setEnginNo(String enginNo) {
		this.enginNo = enginNo;
	}

	/**
	 * @return the carOwner
	 */
	public String getCarOwner() {
		return carOwner;
	}

	/**
	 * @param carOwner
	 *            the carOwner to set
	 */
	public void setCarOwner(String carOwner) {
		this.carOwner = carOwner;
	}

	/**
	 * @return the enrollDate
	 */
	public String getEnrollDate() {
		return enrollDate;
	}

	/**
	 * @param enrollDate
	 *            the enrollDate to set
	 */
	public void setEnrollDate(String enrollDate) {
		this.enrollDate = enrollDate;
	}

}
