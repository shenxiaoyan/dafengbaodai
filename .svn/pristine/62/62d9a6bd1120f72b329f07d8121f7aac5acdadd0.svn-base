package com.liyang.domain.queryProposal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 平台
 * @author Administrator
 *
 */
@Entity
@Table(name = "query_proposal_Result")
public class QueryPaoposalResult {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "result_code")
	private String resultCode;
	@Column(name = "result_mess")
	private String resultMess;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMess() {
		return resultMess;
	}
	public void setResultMess(String resultMess) {
		this.resultMess = resultMess;
	}
	
	

}
