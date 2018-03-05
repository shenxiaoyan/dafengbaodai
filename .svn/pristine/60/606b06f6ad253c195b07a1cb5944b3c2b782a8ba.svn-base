package com.liyang.domain.submitproposal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;

import com.liyang.annotation.Info;

/**
 * 地址参数
 * @author Administrator
 *
 */
@Entity
@Table(name = "submit_proposal_contact_address")
public class SubProParamsContactAddress implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "accept_province")
	private String acceptProvince;

	@Column(name = "accept_provinceName")
	private String acceptProvinceName;

	@Lob
	@Column(name = "address")
	private String address;

	@Lob
	@Column(name = "contact_addressDetail")
	private String contactAddressDetail;

	@Column(name = "city_code")
	private String cityCode;
	
	@Column(name = "province_name")
	@Info(label="省信息")
	private String provinceName;
	
	@Info(label="市信息")
	@Column(name = "city_name")
	private String cityName;
	
	@Info(label="县区信息")
	@Column(name = "area_name")
	private String areaName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAcceptProvince() {
		return acceptProvince;
	}

	public void setAcceptProvince(String acceptProvince) {
		this.acceptProvince = acceptProvince;
	}

	public String getAcceptProvinceName() {
		return acceptProvinceName;
	}

	public void setAcceptProvinceName(String acceptProvinceName) {
		this.acceptProvinceName = acceptProvinceName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactAddressDetail() {
		return contactAddressDetail;
	}

	public void setContactAddressDetail(String contactAddressDetail) {
		this.contactAddressDetail = contactAddressDetail;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Override
	public String toString() {
		return "ContactAddress [acceptProvince=" + acceptProvince + ", acceptProvinceName=" + acceptProvinceName
				+ ", address=" + address + ", contactAddressDetail=" + contactAddressDetail + "]";
	}
}
