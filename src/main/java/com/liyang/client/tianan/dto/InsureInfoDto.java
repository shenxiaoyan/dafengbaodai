package com.liyang.client.tianan.dto;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.liyang.client.IDto;
import com.liyang.client.tianan.TypeDate;
import com.liyang.client.tianan.enums.IdentifyTypeEnums;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * 被保人信息
 * 
 * @author Administrator
 *
 */
public class InsureInfoDto implements IDto {
	private final TypeDate insuredbirthDate;
	private final String insuredName;
	private final String insuredidentifyType;
	private final String insuredidentifyNumber;
	private final String insuredsex;
	private final String insuredmobilePhone;
	private final String insuredAddress;
	private final String insuredemail;
	private final Date certiStartDate;
	private final Date certiEndDate;
	private final String nation;
	private final String issuer;

	/**
	 * 被保人出生日期
	 * 
	 * @return the insuredbirthDate
	 */
	public String getInsuredbirthDate() {
		return insuredbirthDate == null ? null : insuredbirthDate.getString();
	}

	/**
	 * 被保人姓名
	 * 
	 * @return the insuredName
	 */
	public String getInsuredName() {
		return insuredName;
	}

	/**
	 * 被保人证件类型
	 * 
	 * @return the insuredidentifyType
	 */
	public String getInsuredidentifyType() {
		return insuredidentifyType;
	}

	/**
	 * 被保人证件号码
	 * 
	 * @return the insuredidentifyNumber
	 */
	public String getInsuredidentifyNumber() {
		return insuredidentifyNumber;
	}

	/**
	 * 被保人性别
	 * 
	 * @return the insuredsex
	 */
	public String getInsuredsex() {
		return insuredsex;
	}

	/**
	 * 被保人电话
	 * 
	 * @return the insuredmobilePhone
	 */
	public String getInsuredmobilePhone() {
		return insuredmobilePhone;
	}

	/**
	 * 被保人地址
	 * 
	 * @return the insuredAddress
	 */
	public String getInsuredAddress() {
		return insuredAddress;
	}

	/**
	 * 被保人邮箱
	 * 
	 * @return the insuredemail
	 */
	public String getInsuredemail() {
		return insuredemail;
	}

	/**
	 * 身份证有效起期
	 * 
	 * @return the certiStartDate
	 */
	public Date getCertiStartDate() {
		return certiStartDate;
	}

	/**
	 * 身份证有效止期
	 * 
	 * @return the certiEndDate
	 */
	public Date getCertiEndDate() {
		return certiEndDate;
	}

	/**
	 * 民族
	 * 
	 * @return the nation
	 */
	public String getNation() {
		return nation;
	}

	/**
	 * 签发机构
	 * 
	 * @return the issuer
	 */
	public String getIssuer() {
		return issuer;
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {
		if (StringUtils.isEmpty(this.insuredbirthDate)) {
			throw new ExceptionTiananParamInvliad("被保人出生日期不能为空");
		}
		if (StringUtils.isEmpty(this.insuredName)) {
			throw new ExceptionTiananParamInvliad("被保人姓名不能为空");
		}
		if (StringUtils.isEmpty(this.insuredidentifyType)) {
			throw new ExceptionTiananParamInvliad("被保人证件类型不能为空");
		} else {
			if (!IdentifyTypeEnums.isExist(this.insuredidentifyType)) {
				throw new ExceptionTiananParamInvliad("被保人证件类型不匹配");
			}
		}
		if (StringUtils.isEmpty(this.insuredidentifyNumber)) {
			throw new ExceptionTiananParamInvliad("被保人证件号码不能为空");
		}
		if (StringUtils.isEmpty(this.insuredsex)) {
			throw new ExceptionTiananParamInvliad("被保人性别不能为空");
		} else if (!("0".equals(this.insuredsex) || "1".equals(this.insuredsex))) {
			throw new ExceptionTiananParamInvliad("被保人性别不正确");
		}
		if (StringUtils.isEmpty(this.insuredmobilePhone)) {
			throw new ExceptionTiananParamInvliad("被保人电话不能为空");
		}
		if (StringUtils.isEmpty(this.insuredAddress)) {
			throw new ExceptionTiananParamInvliad("被保人地址不能为空");
		}

	}

	public InsureInfoDto(Builder builder) {
		this.insuredbirthDate = builder.insuredbirthDate;
		this.insuredName = builder.insuredName;
		this.insuredidentifyType = builder.insuredidentifyType;
		this.insuredidentifyNumber = builder.insuredidentifyNumber;
		this.insuredsex = builder.insuredsex;
		this.insuredmobilePhone = builder.insuredmobilePhone;
		this.insuredAddress = builder.insuredAddress;
		this.insuredemail = builder.insuredemail;
		this.certiStartDate = builder.certiStartDate;
		this.certiEndDate = builder.certiEndDate;
		this.nation = builder.nation;
		this.issuer = builder.issuer;
	}

	public static class Builder {
		private final TypeDate insuredbirthDate;
		private final String insuredName;
		private final String insuredidentifyType;
		private final String insuredidentifyNumber;
		private final String insuredsex;
		private final String insuredmobilePhone;
		private final String insuredAddress;
		private String insuredemail;
		private Date certiStartDate;
		private Date certiEndDate;
		private String nation;
		private String issuer;

		public Builder(TypeDate insuredbirthDate, String insuredName, String insuredidentifyType,
				String insuredidentifyNumber, String insuredsex, String insuredmobilePhone, String insuredAddress) {
			initDefaultValue();
			this.insuredbirthDate = insuredbirthDate;
			this.insuredName = insuredName;
			this.insuredidentifyType = insuredidentifyType;
			this.insuredidentifyNumber = insuredidentifyNumber;
			this.insuredsex = insuredsex;
			this.insuredmobilePhone = insuredmobilePhone;
			this.insuredAddress = insuredAddress;
		}

		public void initDefaultValue() {

		}

		public Builder insuredemail(String insuredemail) {
			this.insuredemail = insuredemail;
			return this;
		}

		public Builder certiStartDate(Date certiStartDate) {
			this.certiStartDate = certiStartDate;
			return this;
		}

		public Builder certiEndDate(Date certiEndDate) {
			this.certiEndDate = certiEndDate;
			return this;
		}

		public Builder nation(String nation) {
			this.nation = nation;
			return this;
		}

		public Builder issuer(String issuer) {
			this.issuer = issuer;
			return this;
		}

		public InsureInfoDto build() throws ExceptionTiananParamInvliad {
			InsureInfoDto dto = new InsureInfoDto(this);
			dto.validate();
			return dto;
		}
	}

}
