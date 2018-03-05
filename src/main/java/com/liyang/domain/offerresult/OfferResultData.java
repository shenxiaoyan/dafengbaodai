package com.liyang.domain.offerresult;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.liyang.annotation.Info;

/**
 * 报价结果数据
 * @author Administrator
 *
 */
@Entity
@Table(name="offer_result_data")
public class OfferResultData implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne(cascade=CascadeType.ALL,targetEntity=OfferResultDataRequestHeader.class)
	@JoinColumn(name="offer_result_request_header_id")
	@Info(label="报价结果调用方信息",placeholder="",tip="",help="",secret="")
	private OfferResultDataRequestHeader requestHeader;    
	//推送结果
	@OneToOne(cascade=CascadeType.ALL,targetEntity=OfferResultDataResult.class)
	@JoinColumn(name="offer_result_result_id")
	@Info(label="报价结果推送结果",placeholder="",tip="",help="",secret="")
	private OfferResultDataResult result;    

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public OfferResultDataRequestHeader getRequestHeader() {
		return requestHeader;
	}

	public void setRequestHeader(OfferResultDataRequestHeader requestHeader) {
		this.requestHeader = requestHeader;
	}

	public OfferResultDataResult getResult() {
		return result;
	}

	public void setResult(OfferResultDataResult result) {
		this.result = result;
	}
}
