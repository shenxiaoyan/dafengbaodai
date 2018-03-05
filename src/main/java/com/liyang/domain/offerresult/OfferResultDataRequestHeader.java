package com.liyang.domain.offerresult;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.liyang.annotation.Info;

/**
 * 报价结果请求头
 * @author Administrator
 *
 */
@Entity
@Table(name="offer_result_request_header")
public class OfferResultDataRequestHeader implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="offer_unique")
	@Info(label="报价唯一标识，关联询价接口")
	private String offerUnique;  
	//第三方平台标识
	@Column(name="applicationId")
	private String applicationId;    
	
	@Column(name="ori_req_header_str")
	private String oriReqHeaderStr;

	
	public String getOriReqHeaderStr() {
		return oriReqHeaderStr;
	}

	public void setOriReqHeaderStr(String oriReqHeaderStr) {
		this.oriReqHeaderStr = oriReqHeaderStr;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

//	public String getRequested() {
//		return requested;
//	}
//
//	public void setRequested(String requested) {
//		this.requested = requested;
//	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOfferUnique() {
		return offerUnique;
	}

	public void setOfferUnique(String offerUnique) {
		this.offerUnique = offerUnique;
	}

	
	
	
}
