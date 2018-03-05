package com.liyang.domain.dafengapi;

import java.io.Serializable;
import java.util.Set;

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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.platform.Platform;
import com.liyang.service.AbstractAuditorService;

/**
 * 类说明
 * 
 * @author lcj
 * @date 2017年9月13日 新建
 */

@Entity
@Table(name = "dafeng_api_customer")
public class DafengApiCustomer
		extends AbstractAuditorEntity<DafengApiCustomerState, DafengApiCustomerAct, DafengApiCustomerLog>
		implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Transient
	private static LogRepository logRepository;

	@Transient
	private static AbstractAuditorService service;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	// apiKey Value, unique
	@Column(name = "apikey", unique = true)
	private String apiKey; 
	// 公网ip地址
	@Column(name = "ipaddress")
	private String ipAddress; 
	// 平台来源
	@OneToOne(cascade = CascadeType.ALL, targetEntity = Platform.class)
	@JoinColumn(name = "platform_id")
	private Platform platform; 

	@OneToMany(cascade = CascadeType.ALL, targetEntity = Customer.class, mappedBy = "dafengApiCustomer")
	private Set<Customer> customerSet;

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Set<Customer> getCustomerSet() {
		return customerSet;
	}

	public void setCustomerSet(Set<Customer> customerSet) {
		this.customerSet = customerSet;
	}

	@JsonIgnore
	@Override
	@Transient
	public AbstractAuditorService<?, DafengApiCustomerState, DafengApiCustomerAct, DafengApiCustomerLog> getService() {
		// TODO Auto-generated method stub
		return service;
	}

	@Override
	public void setService(AbstractAuditorService service) {
		// TODO Auto-generated method stub
		DafengApiCustomer.service = service;
	}

	@Override
	@JsonIgnore
	@Transient
	public DafengApiCustomerLog getLogInstance() {
		// TODO Auto-generated method stub
		return new DafengApiCustomerLog();
	}

	@Override
	@JsonIgnore
	@Transient
	public LogRepository getLogRepository() {
		return logRepository;
	}

	@Override
	public void setLogRepository(LogRepository logRepository) {
		DafengApiCustomer.logRepository = logRepository;

	}

	// @Column(name="car_type_code",columnDefinition="varchar(256) default 02 ")
	// private String carTypeCode;//号牌种类(01大型车、02小型车)如果不填默认02
	//
	// @OneToOne(cascade=CascadeType.ALL,targetEntity=Platform.class)
	// @JoinColumn(name="platform_id")
	// private Platform platform; //平台来源
	//
	// @OneToOne(cascade=CascadeType.ALL,targetEntity=QueryLatestPolicyResult.class)
	// @JoinColumn(name="query_latest_policy_result_id")
	// private QueryLatestPolicyResult queryLatestPolicyResult; //查询结果集

}
