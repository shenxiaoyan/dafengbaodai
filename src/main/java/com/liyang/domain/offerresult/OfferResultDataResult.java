package com.liyang.domain.offerresult;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.liyang.annotation.Info;
import com.liyang.util.JsonParserUtils;
import net.sf.json.JSONObject;

/**
 * 报价结果数据
 * @author Administrator
 *
 */
@Entity
@Table(name = "offer_result_result")
public class OfferResultDataResult implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "record_id")
	@Info(label = "报价结果记录编号", placeholder = "", tip = "", help = "", secret = "")
	private String recordId; // 记录编号
	@Column(name = "branch_id")
	@Info(label = "报价结果机构编号", placeholder = "", tip = "", help = "", secret = "")
	private Integer branchId; // 机构编号
	@Column(name = "branch_name")
	@Info(label = "报价结果机构名字", placeholder = "", tip = "", help = "", secret = "")
	private String branchName; // 机构名字
	@Column(name = "insurance_company_id")
	@Info(label = "报价结果投保公司编号", placeholder = "", tip = "", help = "", secret = "")
	private Integer insuranceCompanyId; // 投保公司编号
	@Column(name = "insurance_company_name")
	@Info(label = "报价结果投保公司名称", placeholder = "", tip = "", help = "", secret = "")
	private String insuranceCompanyName; // 投保公司名称
	@Column(name = "insurance_company_logo")
	@Info(label = "报价结果与投保公司logo", placeholder = "", tip = "", help = "", secret = "")
	private String insuranceCompanyLogo; // 投保公司logo
	@Column(name = "city_code")
	@Info(label = "报价结果城市编码", placeholder = "", tip = "", help = "", secret = "")
	private String cityCode; // 城市编码
	@Column(name = "city_name")
	@Info(label = "报价结果城市名字", placeholder = "", tip = "", help = "", secret = "")
	private String cityName;
	@Column(name = "customer_id")
	@Info(label = "", placeholder = "", tip = "", help = "", secret = "")
	private int customerId;
	@Column(name = "offer_detail", length = 1500)
	@Info(label = "报价方案结果详情", placeholder = "", tip = "", help = "", secret = "")
	private String offerDetail; // 报价方案结果详情
	@Column(name = "tag_json")
	@Info(label = "报价结果赠送服务", placeholder = "", tip = "", help = "", secret = "")
	private String tagJson; // 赠送服务
	private String remark;
	@Column(name = "model_json", length = 1500)
	@Info(label = "报价结果投保车型信息", placeholder = "", tip = "", help = "", secret = "")
	private String modelJson; // 投保s车型信息(部分公司未对应)
	private Short state;
	@Column(name = "offer_id")
	@Info(label = "报价编号", placeholder = "", tip = "", help = "", secret = "")
	private String offerId; // 报价编号
	@Column(name = "create_by")
	@Info(label = "报价结果发布报价人的名称", placeholder = "", tip = "", help = "", secret = "")
	private String createBy;// 发布报价人的名称
	@Column(name = "current_price")
	@Info(label = "报价结果商业险底价(单位：分)", placeholder = "", tip = "", help = "", secret = "")
	private Long currentPrice; // 商业险底价(单位：分)
	@Column(name = "originl_price")
	@Info(label = "报价结果商业险出单价(单位：分)", placeholder = "", tip = "", help = "", secret = "")
	private Long originalPrice; // 商业险出单价(单位：分)
	@Column(name = "force_premium")
	@Info(label = "报价结果交强险出单价(单位：分)", placeholder = "", tip = "", help = "", secret = "")
	private Long forcePremium; // 交强险出单价(单位：分)
	@Column(name = "commercial_discount")
	@Info(label = "报价结果报价折扣", placeholder = "", tip = "", help = "", secret = "")
	private String commercialDiscount; // 折扣
	@Column(name = "tax_price")
	@Info(label = "报价结果车船税(单位：分)", placeholder = "", tip = "", help = "", secret = "")
	private Long taxPrice; // 车船税(单位：分)
	private Integer source;
	@Column(name = "insurance_start_time")
	@Info(label = "报价结果商业险起期", placeholder = "", tip = "", help = "", secret = "")
	private Long insuranceStartTime; // 商业险起期
	@Column(name = "force_insurance_startTime")
	@Info(label = "报价结果交强险起期", placeholder = "", tip = "", help = "", secret = "")
	private Long forceInsuranceStartTime; // 交强险起期
	@Column(name = "error_msg", length = 1500)
	@Info(label = "报价失败原因", placeholder = "", tip = "", help = "", secret = "")
	private String errorMsg; // 报价失败原因
	@Column(name = "ration_josn", length = 1500)
	@Info(label = "报价结果定价因子(部分公司未对应)", placeholder = "", tip = "", help = "", secret = "")
	private String ratioJson; // 定价因子(部分公司未对应)
	@Column(name = "additional_info", length = 1500)
	@Info(label = "报价结果附加信息", placeholder = "", tip = "", help = "", secret = "")
	private String additionalInfo; // 附加信息
	@Info(label = "报价结果方案名称", placeholder = "", tip = "", help = "", secret = "")
	private String schemeName; // 方案名称
	@Column(name = "enquiry_id")
	@Info(label = "报价结果方案编号", placeholder = "", tip = "", help = "", secret = "")
	private String enquiryId; // 方案编号
	@Column(name = "ci_base_price")
	@Info(label = "报价结果强制险底价(分)", placeholder = "", tip = "", help = "", secret = "")
	private Long ciBasePrice; // 强制险底价(分)
	@Column(name = "record_state")
	@Info(label = "报价结果记录状态", placeholder = "", tip = "", help = "", secret = "")
	private String recordState; // 记录状态
	@Column(name = "rebate_json", length = 1500)
	private String rebateJson; //
	@Column(name = "rebate_flag")
	private Boolean rebateFlag; // ?
	@Column(name = "multi_enquiry_id")
	private String multiEnquiryId; // 多方案统一编号
	@Column(name = "account_name")
	private String accountName;
	private boolean carLossPriceFlag;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(joinColumns = { @JoinColumn(name = "offer_result_result_id") }, inverseJoinColumns = {
			@JoinColumn(name = "offer_result_enquiry_car_illegal_list_id") })
	@Info(label = "报价结果违章记录(部分公司未对应)", placeholder = "", tip = "", help = "", secret = "")
	private Set<OfferResDatResEnquiryCarIllegalList> enquiryCarIllegalList; // 违章记录(部分公司未对应)
																			// //可能有问题

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(joinColumns = { @JoinColumn(name = "offer_result_result_id") }, inverseJoinColumns = {
			@JoinColumn(name = "offer_result_enquiry_car_settlement_list") })
	@Info(label = "报价结果理赔信息", placeholder = "", tip = "", help = "", secret = "")
	private Set<OfferResDatResEnquiryCarSettlementList> enquiryCarSettlementList; // 理赔信息

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(joinColumns = { @JoinColumn(name = "offer_result_result_id") }, inverseJoinColumns = {
			@JoinColumn(name = "offer_result_enquiry_car_models") })
	@Info(label = "报价结果备选车型", placeholder = "", tip = "", help = "", secret = "")
	private Set<OfferResDatResEnquiryCarModels> enquiryCarModels; // 备选车型

	/**
	 * 天安新增参数
	 */
	@Column(name = "actual_value")
	private Double actualValue;
	@Column(name = "commercial_num")
	private Integer commercialNum;
	@Column(name = "traffic_insurance_num")
	private Integer trafficInsuranceNum;
	@Column(name = "force_insurance_end_time")
	private Long forceInsuranceEndTime; // 匹配小马的格式，time/1000
	@Column(name = "insurance_end_time")
	private Long insuranceEndTime;// 匹配小马的格式，time/1000
	@Column(name = "re_premium_message")
	private String rePremiumMessage;
	@Column(name = "insured_status")
	private String insuredStatus;
	@OneToMany(mappedBy = "result")
	private Set<OfferResultDataResultCheckList> checkList;
	@Column(name = "car_premium_caculate_no")
	private String carPremiumCaculateNo;

	public String getCarPremiumCaculateNo() {
		return carPremiumCaculateNo;
	}

	public void setCarPremiumCaculateNo(String carPremiumCaculateNo) {
		this.carPremiumCaculateNo = carPremiumCaculateNo;
	}

	public Double getActualValue() {
		return actualValue;
	}

	public void setActualValue(Double actualValue) {
		this.actualValue = actualValue;
	}

	public Integer getCommercialNum() {
		return commercialNum;
	}

	public void setCommercialNum(Integer commercialNum) {
		this.commercialNum = commercialNum;
	}

	public Integer getTrafficInsuranceNum() {
		return trafficInsuranceNum;
	}

	public void setTrafficInsuranceNum(Integer trafficInsuranceNum) {
		this.trafficInsuranceNum = trafficInsuranceNum;
	}

	public Long getForceInsuranceEndTime() {
		return forceInsuranceEndTime;
	}

	public void setForceInsuranceEndTime(Long forceInsuranceEndTime) {
		forceInsuranceEndTime = forceInsuranceEndTime;
	}

	public Long getInsuranceEndTime() {
		return insuranceEndTime;
	}

	public void setInsuranceEndTime(Long insuranceEndTime) {
		insuranceEndTime = insuranceEndTime;
	}

	public String getRePremiumMessage() {
		return rePremiumMessage;
	}

	public void setRePremiumMessage(String rePremiumMessage) {
		this.rePremiumMessage = rePremiumMessage;
	}

	public String getInsuredStatus() {
		return insuredStatus;
	}

	public void setInsuredStatus(String insuredStatus) {
		this.insuredStatus = insuredStatus;
	}

	public Set<OfferResultDataResultCheckList> getCheckList() {
		return checkList;
	}

	public void setCheckList(Set<OfferResultDataResultCheckList> checkList) {
		this.checkList = checkList;
	}

	public boolean isCarLossPriceFlag() {
		return carLossPriceFlag;
	}

	public void setCarLossPriceFlag(boolean carLossPriceFlag) {
		this.carLossPriceFlag = carLossPriceFlag;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Integer getInsuranceCompanyId() {
		return insuranceCompanyId;
	}

	public void setInsuranceCompanyId(Integer insuranceCompanyId) {
		this.insuranceCompanyId = insuranceCompanyId;
	}

	public String getInsuranceCompanyName() {
		return insuranceCompanyName;
	}

	public void setInsuranceCompanyName(String insuranceCompanyName) {
		this.insuranceCompanyName = insuranceCompanyName;
	}

	public String getInsuranceCompanyLogo() {
		return insuranceCompanyLogo;
	}

	public void setInsuranceCompanyLogo(String insuranceCompanyLogo) {
		this.insuranceCompanyLogo = insuranceCompanyLogo;
	}

	public JSONObject getOfferDetail() {
		return JsonParserUtils.jsonTrans(offerDetail);
	}

	public void setOfferDetail(String offerDetail) {
		this.offerDetail = offerDetail;
	}

	public String getTagJson() {
		return tagJson;
	}

	public void setTagJson(String tagJson) {
		this.tagJson = tagJson;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public JSONObject getModelJson() {
		return JsonParserUtils.jsonTrans(modelJson);
	}

	public void setModelJson(String modelJson) {
		this.modelJson = modelJson;
	}

	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Long getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Long currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Long getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Long originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Long getForcePremium() {
		return forcePremium;
	}

	public void setForcePremium(Long forcePremium) {
		this.forcePremium = forcePremium;
	}

	public String getCommercialDiscount() {
		return commercialDiscount;
	}

	public void setCommercialDiscount(String commercialDiscount) {
		this.commercialDiscount = commercialDiscount;
	}

	public Long getTaxPrice() {
		return taxPrice;
	}

	public void setTaxPrice(Long taxPrice) {
		this.taxPrice = taxPrice;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Long getInsuranceStartTime() {
		return insuranceStartTime;
	}

	public void setInsuranceStartTime(Long insuranceStartTime) {
		this.insuranceStartTime = insuranceStartTime;
	}

	public Long getForceInsuranceStartTime() {
		return forceInsuranceStartTime;
	}

	public void setForceInsuranceStartTime(Long forceInsuranceStartTime) {
		this.forceInsuranceStartTime = forceInsuranceStartTime;
	}

	public JSONObject getErrorMsg() {
		return JsonParserUtils.jsonTrans(errorMsg);
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public JSONObject getRatioJson() {
		return JsonParserUtils.jsonTrans(ratioJson);

	}

	public void setRatioJson(String ratioJson) {
		this.ratioJson = ratioJson;
	}

	public JSONObject getAdditionalInfo() {
		return JsonParserUtils.jsonTrans(additionalInfo);
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getEnquiryId() {
		return enquiryId;
	}

	public void setEnquiryId(String enquiryId) {
		this.enquiryId = enquiryId;
	}

	public Long getCiBasePrice() {
		return ciBasePrice;
	}

	public void setCiBasePrice(Long ciBasePrice) {
		this.ciBasePrice = ciBasePrice;
	}

	public Set<OfferResDatResEnquiryCarIllegalList> getEnquiryCarIllegalList() {
		return enquiryCarIllegalList;
	}

	public void setEnquiryCarIllegalList(Set<OfferResDatResEnquiryCarIllegalList> enquiryCarIllegalList) {
		this.enquiryCarIllegalList = enquiryCarIllegalList;
	}

	public Set<OfferResDatResEnquiryCarSettlementList> getEnquiryCarSettlementList() {
		return enquiryCarSettlementList;
	}

	public void setEnquiryCarSettlementList(Set<OfferResDatResEnquiryCarSettlementList> enquiryCarSettlementList) {
		this.enquiryCarSettlementList = enquiryCarSettlementList;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getRecordState() {
		return recordState;
	}

	public void setRecordState(String recordState) {
		this.recordState = recordState;
	}

	public JSONObject getRebateJson() {
		return JsonParserUtils.jsonTrans(rebateJson);
	}

	public void setRebateJson(String rebateJson) {
		this.rebateJson = rebateJson;
	}

	public Boolean getRebateFlag() {
		return rebateFlag;
	}

	public void setRebateFlag(Boolean rebateFlag) {
		this.rebateFlag = rebateFlag;
	}

	public String getMultiEnquiryId() {
		return multiEnquiryId;
	}

	public void setMultiEnquiryId(String multiEnquiryId) {
		this.multiEnquiryId = multiEnquiryId;
	}

	public Set<OfferResDatResEnquiryCarModels> getEnquiryCarModels() {
		return enquiryCarModels;
	}

	public void setEnquiryCarModels(Set<OfferResDatResEnquiryCarModels> enquiryCarModels) {
		this.enquiryCarModels = enquiryCarModels;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
}
