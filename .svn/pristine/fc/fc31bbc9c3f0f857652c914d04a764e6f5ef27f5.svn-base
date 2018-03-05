package com.liyang.domain.querylatestpolicy;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 交强险险续保信息
 * @author Administrator
 *
 */
@Entity
@Table(name="query_latest_policy_car_info")
public class QueryLatestPolicyResultCarInfo implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	//发动机号
	@Column(name="engine_no")
	private String engineNo;
	//厂牌型号
	@Column(name="standard_name")
	private String standardName;  
	private int flag;
	//车架号
	@Column(name="vehicle_frame_no")
	private String vehicleFrameNo;  
	//初登日期
	@Column(name="bought_time")
	private String boughtTime;  
	//车辆其他信息{"syxz":"使用性质","zbzl":"整备质量","pl":"排量","rlzl":"燃料种类","cllx":"车辆类型","hdzk":"核定载客","hdzzl":"核定载质量"}
	@OneToOne(cascade=CascadeType.ALL,targetEntity=QueryLatestPolicyResultCarInfoCarTypeJson.class)
	@JoinColumn(name="query_latest_policy_car_type_json_id")
	private QueryLatestPolicyResultCarInfoCarTypeJson carTypeJson;  
	//所有人
	@Column(name="owne_name")
	private String ownerName;  
	
	@Column(name="car_type_code")
	private String carTypeCode;
	//车牌号
	@Column(name="license_number")
	private String licenseNumber; 
	//品牌
	@Column(name="brand_chn_name")
	private String brandChnName;   
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEngineNo() {
		return engineNo;
	}
	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}
	public String getStandardName() {
		return standardName;
	}
	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}
	public String getVehicleFrameNo() {
		return vehicleFrameNo;
	}
	public void setVehicleFrameNo(String vehicleFrameNo) {
		this.vehicleFrameNo = vehicleFrameNo;
	}
	public String getBoughtTime() {
		return boughtTime;
	}
	public void setBoughtTime(String boughtTime) {
		this.boughtTime = boughtTime;
	}
	
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public QueryLatestPolicyResultCarInfoCarTypeJson getCarTypeJson() {
		return carTypeJson;
	}
	public void setCarTypeJson(QueryLatestPolicyResultCarInfoCarTypeJson carTypeJson) {
		this.carTypeJson = carTypeJson;
	}
	public String getCarTypeCode() {
		return carTypeCode;
	}
	public void setCarTypeCode(String carTypeCode) {
		this.carTypeCode = carTypeCode;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getLicenseNumber() {
		return licenseNumber;
	}
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	public String getBrandChnName() {
		return brandChnName;
	}
	public void setBrandChnName(String brandChnName) {
		this.brandChnName = brandChnName;
	}
	
}
