package com.liyang.domain.temporaryresult;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 暂存结果json
 * @author Administrator
 *
 */
@Entity
@Table(name="temporary_result_proposal_json")
public class ProposalJson implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	//商业险保单号  或者是错误原因
	@Column(name="bi_no")
	private String biNo;  
	// 交强险保单号或者是错误原因
	@Column(name="ci_no")
	private String ciNo;   

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBiNo() {
		return biNo;
	}

	public void setBiNo(String biNo) {
		this.biNo = biNo;
	}

	public String getCiNo() {
		return ciNo;
	}

	public void setCiNo(String ciNo) {
		this.ciNo = ciNo;
	}
}
