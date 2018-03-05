package com.liyang.domain.querylatestpolicy;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 查询续保结果推送
 * @author Administrator
 *
 */
@Entity
@Table(name="query_latest_policy_result")
public class QueryLatestPolicyResult implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne(cascade=CascadeType.ALL,targetEntity=QueryLatestPolicyResultErrorMsg.class)
	@JoinColumn(name="query_latest_policy_error_msg_id")
	private QueryLatestPolicyResultErrorMsg errorMsg;
	
	@OneToOne(cascade=CascadeType.ALL,targetEntity=QueryLatestPolicyResultData.class)
	@JoinColumn(name="query_latest_policy_data_id")
	private QueryLatestPolicyResultData data;
	
//	@Lob
//	private String data;
	
	private String time;
	
	private Boolean successful;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public QueryLatestPolicyResultErrorMsg getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(QueryLatestPolicyResultErrorMsg errorMsg) {
		this.errorMsg = errorMsg;
	}

	public QueryLatestPolicyResultData getData() {
		return data;
	}

	public void setData(QueryLatestPolicyResultData data) {
		this.data = data;
	}
	

	public String getTime() {
		return time;
	}

//	public String getData() {
//		return data;
//	}
//
//	public void setData(String data) {
//		this.data = data;
//	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Boolean getSuccessful() {
		return successful;
	}

	public void setSuccessful(Boolean successful) {
		this.successful = successful;
	}
}
