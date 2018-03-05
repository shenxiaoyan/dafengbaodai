package com.liyang.domain.address;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.service.AbstractAuditorService;

/**
 * 地址：精确到市
 * @author Administrator
 *
 */
@Entity
@Table(name = "Address",indexes = {@Index(name="address_province_city",columnList="province,city",unique=true)})
public class Address extends AbstractAuditorEntity<AddressState, AddressAct, AddressLog> {

	private static final long serialVersionUID = 1L;
	@Transient
	private static LogRepository logRepository;

	@Transient
	private static AbstractAuditorService service;
	@Column(name = "province")
	@Info(label = "省")
	private String province;

	@Column(name = "city")
	@Info(label = "市")
	private String city;

	@Column(name = "city_code")
	@Info(label = "城市代码")
	private String cityCode;

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@Override
	@JsonIgnore
	@Transient
	public AbstractAuditorService<?, AddressState, AddressAct, AddressLog> getService() {
		return service;
	}

	@Override
	public void setService(AbstractAuditorService service) {
		Address.service = service;
	}

	@Override
	@JsonIgnore
	@Transient
	public AddressLog getLogInstance() {
		return new AddressLog();
	}

	@Override
	@JsonIgnore
	@Transient
	public LogRepository getLogRepository() {
		return logRepository;
	}

	@Override
	public void setLogRepository(LogRepository logRepository) {
		Address.logRepository = logRepository;
	}

	public Address(String province, String city) {
		super();
		this.province = province;
		this.city = city;
	}

	public Address() {
		super();
	}

}
