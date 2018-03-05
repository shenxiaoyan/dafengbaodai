package com.liyang.domain.querylatestpolicy;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.liyang.domain.platform.Platform;

/**
 * 查询续保
 * @author Administrator
 *
 */
@Entity
@Table(name="query_latest_policy")
public class QueryLatestPolicy implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	//车牌号码
	@Column(name="license_number")	
	private String licenseNumber;   
	//车主姓名
	@Column(name="owner_name")
	private String ownerName;   
	//号牌种类(01大型车、02小型车)如果不填默认02
	@Column(name="car_type_code",columnDefinition="varchar(256) default 02 ")
	private String carTypeCode;
	//平台来源
	@OneToOne(cascade=CascadeType.ALL,targetEntity=Platform.class)
	@JoinColumn(name="platform_id")
	private Platform platform;    
	//查询结果集
	@OneToOne(cascade=CascadeType.ALL,targetEntity=QueryLatestPolicyResult.class)
	@JoinColumn(name="query_latest_policy_result_id")
	private QueryLatestPolicyResult queryLatestPolicyResult;   

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getCarTypeCode() {
		return carTypeCode;
	}

	public void setCarTypeCode(String carTypeCode) {
		this.carTypeCode = carTypeCode;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public QueryLatestPolicyResult getQueryLatestPolicyResult() {
		return queryLatestPolicyResult;
	}

	public void setQueryLatestPolicyResult(QueryLatestPolicyResult queryLatestPolicyResult) {
		this.queryLatestPolicyResult = queryLatestPolicyResult;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
