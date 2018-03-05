package com.liyang.domain.underwritingresult;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liyang.domain.platform.Platform;
import com.liyang.domain.submitproposal.SubmitProposal;

/**
 * 核保结果
 * @author Administrator
 *
 */
@Entity
@Table(name="underwriting_result")
public class UnderwritingResult implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne(cascade=CascadeType.ALL,targetEntity=UnderwritingResultData.class)
	@JoinColumn(name="underwriting_result_data_id")
	private UnderwritingResultData data;
	
	@OneToOne(cascade=CascadeType.ALL,targetEntity=UnderwritingErrorMsg.class)
	@JoinColumn(name="underwriting_result_error_msg_id")
	private UnderwritingErrorMsg errorMsg;
	
	private boolean successful;
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity=Platform.class)
	@JoinColumn(name="platform_id")
	private Platform platform;
	
	@OneToOne(cascade=CascadeType.ALL,targetEntity=SubmitProposal.class,mappedBy="underwritingResult")
	private SubmitProposal submitProposal;
	
	private Long time;   
	
	@CreatedDate
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createdAt;
	

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UnderwritingResultData getData() {
		return data;
	}

	public void setData(UnderwritingResultData data) {
		this.data = data;
	}

	public UnderwritingErrorMsg getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(UnderwritingErrorMsg errorMsg) {
		this.errorMsg = errorMsg;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public SubmitProposal getSubmitProposal() {
		return submitProposal;
	}

	public void setSubmitProposal(SubmitProposal submitProposal) {
		this.submitProposal = submitProposal;
	}


}
