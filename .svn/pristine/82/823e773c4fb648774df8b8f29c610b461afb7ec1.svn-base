package com.liyang.domain.submitproposal;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.client.tianan.enums.ApiSupplierEnums;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.insuranceresult.InsuranceResult;
import com.liyang.domain.offerresult.OfferResult;
import com.liyang.domain.platform.Platform;
import com.liyang.domain.underwritingresult.UnderwritingResult;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;

/**
 * 投保订单
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "submit_proposal")
@Cacheable
public class SubmitProposal extends
		AbstractWorkflowEntity<SubmitProposalWorkflow, SubmitProposalState, SubmitProposalAct, SubmitProposalLog, SubmitProposalFile> {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	@Transient
	private static LogRepository logRepository;

	@SuppressWarnings("rawtypes")
	@Transient
	private static AbstractWorkflowService service;

	@SuppressWarnings("rawtypes")
	@Transient
	private static ActRepository actRepository;

	@OneToOne(cascade = CascadeType.ALL, targetEntity = SubmitProposalParams.class)
	@JoinColumn(name = "submit_proposal_params_id")
	private SubmitProposalParams params;

	@OneToOne(cascade = CascadeType.ALL, targetEntity = Platform.class)
	@JoinColumn(name = "platform_id")
	private Platform platform;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@Info(label = "订单号")
	private String orderId;

	@Info(label = "API提供商")
	@Enumerated(EnumType.STRING)
	private ApiSupplierEnums supplier;

	@Column(name = "response_result")
	@Lob
	private String responseResult;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "underwriting_result_id")
	private UnderwritingResult underwritingResult;

	@OneToOne(cascade = CascadeType.ALL, targetEntity = OfferResult.class)
	@JoinColumn(name = "offer_result_id")
	private OfferResult offerResult;

	@CreatedDate
	@Column(name = "created_time")
	@Info(label = "创建时间", placeholder = "", tip = "", help = "", secret = "")
	private Date createTime;

	@Info(label = "是否显示", help = "0显示，1不显示")
	private int isShow;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "submitProposal")
	private InsuranceResult insuranceResult;

	public SubmitProposal() {
	}

	public SubmitProposal(Integer id, SubmitProposalParams params, OfferResult offerResult, SubmitProposalState state,
			Integer isShow, Date createTime, Date createdAt, Date lastModifiAt) {
		super();
		this.setId(id);
		this.setParams(params);
		this.setOfferResult(offerResult);
		this.isShow = isShow;
		this.setState(state);
		this.setCreateTime(createTime);
		this.setCreatedAt(createdAt);
		this.setLastModifiedAt(lastModifiAt);
	}

	public InsuranceResult getInsuranceResult() {
		return insuranceResult;
	}

	public void setInsuranceResult(InsuranceResult insuranceResult) {
		this.insuranceResult = insuranceResult;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	public SubmitProposalParams getParams() {
		return params;
	}

	public void setParams(SubmitProposalParams params) {
		this.params = params;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public String getResponseResult() {
		return responseResult;
	}

	public void setResponseResult(String responseResult) {
		this.responseResult = responseResult;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public UnderwritingResult getUnderwritingResult() {
		return underwritingResult;
	}

	public void setUnderwritingResult(UnderwritingResult underwritingResult) {
		this.underwritingResult = underwritingResult;
	}

	public OfferResult getOfferResult() {
		return offerResult;
	}

	public void setOfferResult(OfferResult offerResult) {
		this.offerResult = offerResult;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public ApiSupplierEnums getSupplier() {
		return supplier;
	}

	public void setSupplier(ApiSupplierEnums supplier) {
		this.supplier = supplier;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@JsonIgnore
	@Override
	public AbstractAuditorService getService() {
		return service;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setService(AbstractAuditorService service) {
		SubmitProposal.service = (AbstractWorkflowService) service;
	}

	@Override
	@JsonIgnore
	@Transient
	public SubmitProposalLog getLogInstance() {
		return new SubmitProposalLog();
	}

	@SuppressWarnings("rawtypes")
	@Override
	@JsonIgnore
	@Transient
	public LogRepository getLogRepository() {
		return logRepository;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setLogRepository(LogRepository logRepository) {
		SubmitProposal.logRepository = logRepository;
	}

}
