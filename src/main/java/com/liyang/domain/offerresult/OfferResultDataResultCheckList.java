package com.liyang.domain.offerresult;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.client.tianan.dto.CheckDto;

/**
 * 报价结果数据核对
 * @author Administrator
 *
 */
@Entity
@Table(name = "offer_result_result_checklist")
public class OfferResultDataResultCheckList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "check_flag")
	private String checkFlag;
	@Column(name = "query_sequence_no")
	private String querySequenceNo;
	@Lob
	@Column(name = "check_code")
	private String checkCode;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "offer_result_result_id")
	private OfferResultDataResult result;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getQuerySequenceNo() {
		return querySequenceNo;
	}

	public void setQuerySequenceNo(String querySequenceNo) {
		this.querySequenceNo = querySequenceNo;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public OfferResultDataResult getResult() {
		return result;
	}

	public void setResult(OfferResultDataResult result) {
		this.result = result;
	}

	public OfferResultDataResultCheckList(CheckDto checkDto) {
		this.checkFlag = checkDto.getCheckFlag();
		this.querySequenceNo = checkDto.getQuerySequenceNo();
		this.checkCode = checkDto.getCheckCode();
	}

	public OfferResultDataResultCheckList() {
		super();
	}

}
