package com.liyang.domain.createenquiry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.domain.base.BaseEntityVO;
import com.liyang.util.JsonParserUtils;

import net.sf.json.JSONObject;

public class CreateEnquiryVO extends BaseEntityVO {

	private String mobilePhone;

	private String createEnquiryParams;

	private String ownerName;

	private String licenseNumber;
	
	@JsonIgnore
	private String responseResult;

	private String offerUnique;

//	private Platform platform;

//	private Set<OfferResult> offerResult;

//	private Customer customer;

	private int isShow;

//	ApiSupplierEnums apiSupplier;

	private Integer latestPolicyDataId;

	private String rbCode;

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
	
	public String getStateCode() {
		if (getState() != null) {
			return getState().getStateCode();
		} else {
			return null;
		}
	}

}
