package com.liyang.domain.createenquiry;

import java.util.Date;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.client.tianan.enums.ApiSupplierEnums;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.offerresult.OfferResult;
import com.liyang.domain.platform.Platform;
import com.liyang.service.AbstractAuditorService;
import com.liyang.service.AbstractWorkflowService;
import com.liyang.util.JsonParserUtils;

import net.sf.json.JSONObject;

/**
 * 询价
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "create_enquiry")
@Cacheable
public class CreateEnquiry extends
		AbstractWorkflowEntity<CreateEnquiryWorkflow, CreateEnquiryState, CreateEnquiryAct, CreateEnquiryLog, CreateEnquiryFile> {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("rawtypes")
	@Transient
	private static AbstractWorkflowService service;
	@SuppressWarnings("rawtypes")
	@Transient
	private static ActRepository actRepository;
	@SuppressWarnings("rawtypes")
	@Transient
	private static LogRepository logRepository;

	// 注册手机号
	@Column(name = "mobile_phone")
	private String mobilePhone;

	// 创建询价参数
	@Column(name = "create_enquiry_params")
	@Lob
	private String createEnquiryParams;

	// 车主姓名
	@Column(name = "owner_name")
	private String ownerName;

	// 车牌号
	@Column(name = "license_number")
	private String licenseNumber;

	@Column(name = "response_result")
	@Lob
	private String responseResult;

	// 报价唯一标识
	@Column(name = "offer_unique")
	private String offerUnique;

	@OneToOne(cascade = CascadeType.ALL, targetEntity = Platform.class)
	@JoinColumn(name = "platform_id")
	private Platform platform;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, targetEntity = OfferResult.class, mappedBy = "createEnquiry")
	private Set<OfferResult> offerResult;

	@OneToOne(cascade = CascadeType.ALL, targetEntity = Customer.class)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	// 用户端是否显示，0为显示，1为不显示
	private int isShow;

	// 外部接口提供者类型比如tianan,xiaoma
	@Column(name = "api_supplier")
	@Enumerated(EnumType.STRING)
	ApiSupplierEnums apiSupplier;

	// 续保信息Id
	@Column(name = "latest_policy_Data_id")
	private Integer latestPolicyDataId;

	private String rbCode;

	public CreateEnquiry() {
	}

	public CreateEnquiry(String mobilePhone, String licenseNumber) {
		setMobilePhone(mobilePhone);
		setLicenseNumber(licenseNumber);
	}

	// public CreateEnquiry(Integer id,String offerUnique,String
	// ownerName,String
	// licenseNumber,String mobilePhone,CreateEnquiryState state,Integer
	// isShow,Date
	// createdAt,Date lastModifiAt){
	// super();
	// this.setId(id);
	// this.offerUnique = offerUnique;
	// this.ownerName = ownerName;
	// this.licenseNumber = licenseNumber;
	// this.mobilePhone = mobilePhone;
	// this.setState(state);
	// this.isShow = isShow;
	// this.setLastModifiedAt(lastModifiAt);
	// this.setCreatedAt(createdAt);
	// }

	public CreateEnquiry(Integer id, Customer customer, String offerUnique, String ownerName, String licenseNumber,
			String mobilePhone, CreateEnquiryState state, Integer isShow, Date createdAt, Date lastModifiAt) {
		super();
		this.setId(id);
		this.setCustomer(customer);
		this.offerUnique = offerUnique;
		this.ownerName = ownerName;
		this.licenseNumber = licenseNumber;
		this.mobilePhone = mobilePhone;
		this.setState(state);
		this.isShow = isShow;
		this.setLastModifiedAt(lastModifiAt);
		this.setCreatedAt(createdAt);
	}

	public CreateEnquiry(Customer customer, String offerUnique, String ownerName, String licenseNumber,
			String mobilePhone, CreateEnquiryState state, String createEnquiryParams, Set<OfferResult> offerResult,
			Integer isShow, Date createdAt, Date lastModifiAt) {
		super();
		this.setCustomer(customer);
		this.offerUnique = offerUnique;
		this.ownerName = ownerName;
		this.licenseNumber = licenseNumber;
		this.mobilePhone = mobilePhone;
		this.setState(state);
		this.setCreateEnquiryParams(createEnquiryParams);
		this.setOfferResult(offerResult);
		this.isShow = isShow;
		this.setLastModifiedAt(lastModifiAt);
		this.setCreatedAt(createdAt);
	}

	public ApiSupplierEnums getApiSupplier() {
		return apiSupplier;
	}

	public void setApiSupplier(ApiSupplierEnums apiSupplier) {
		this.apiSupplier = apiSupplier;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set<OfferResult> getOfferResult() {
		return offerResult;
	}

	public void setOfferResult(Set<OfferResult> offerResult) {
		this.offerResult = offerResult;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public JSONObject getCreateEnquiryParams() {
		return JsonParserUtils.jsonTrans(createEnquiryParams);
	}

	public void setCreateEnquiryParams(String createEnquiryParams) {
		this.createEnquiryParams = createEnquiryParams;
	}

	public String getResponseResult() {
		return responseResult;
	}

	public void setResponseResult(String responseResult) {
		this.responseResult = responseResult;
	}

	public String getOfferUnique() {
		return offerUnique;
	}

	public void setOfferUnique(String offerUnique) {
		this.offerUnique = offerUnique;
	}

	public Integer getLatestPolicyDataId() {
		return latestPolicyDataId;
	}

	public void setLatestPolicyDataId(Integer latestPolicyDataId) {
		this.latestPolicyDataId = latestPolicyDataId;
	}
	
	public String getRbCode() {
		return rbCode;
	}

	public void setRbCode(String rbCode) {
		this.rbCode = rbCode;
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
		CreateEnquiry.service = (AbstractWorkflowService) service;
	}

	@Override
	@JsonIgnore
	@Transient
	public CreateEnquiryLog getLogInstance() {
		return new CreateEnquiryLog();
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
		CreateEnquiry.logRepository = logRepository;
	}

	public String getStateCode() {
		if (getState() != null) {
			return getState().getStateCode();
		} else {
			return null;
		}
	}
}
