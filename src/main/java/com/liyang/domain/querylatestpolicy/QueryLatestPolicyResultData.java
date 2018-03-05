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

import org.hibernate.annotations.Type;

/**
 * 查询续保结果数据
 * @author Administrator
 *
 */
@Entity
@Table(name="query_latest_policy_data")
public class QueryLatestPolicyResultData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	//  商业险续保信息
	@OneToOne(cascade=CascadeType.ALL,targetEntity=QueryLatestPolicyResultBiInfo.class)
	@JoinColumn(name="query_latest_policy_bi_info_id")
	private QueryLatestPolicyResultBiInfo biInfo;   
	//交强险续保信息
	@OneToOne(cascade=CascadeType.ALL,targetEntity=QueryLatestPolicyResultCiInfo.class)
	@JoinColumn(name="query_latest_policy_ci_info_id")
	private QueryLatestPolicyResultCiInfo ciInfo;     
	//车辆信息
	@OneToOne(cascade=CascadeType.ALL,targetEntity=QueryLatestPolicyResultCarInfo.class)
	@JoinColumn(name="query_latest_policy_car_info_id")
	private QueryLatestPolicyResultCarInfo carInfo;  
	
	@Type(type="yes_no")
	private boolean needCompleteCarInfo;
	
	

	public boolean isNeedCompleteCarInfo() {
		return needCompleteCarInfo;
	}

	public void setNeedCompleteCarInfo(boolean needCompleteCarInfo) {
		this.needCompleteCarInfo = needCompleteCarInfo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public QueryLatestPolicyResultBiInfo getBiInfo() {
		return biInfo;
	}

	public void setBiInfo(QueryLatestPolicyResultBiInfo biInfo) {
		this.biInfo = biInfo;
	}

	public QueryLatestPolicyResultCiInfo getCiInfo() {
		return ciInfo;
	}

	public void setCiInfo(QueryLatestPolicyResultCiInfo ciInfo) {
		this.ciInfo = ciInfo;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public QueryLatestPolicyResultCarInfo getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(QueryLatestPolicyResultCarInfo carInfo) {
		this.carInfo = carInfo;
	}
	
}
