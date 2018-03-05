package com.liyang.client.tianan.dto;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.liyang.client.IDto;
import com.liyang.client.tianan.TypeDate;
import com.liyang.client.tianan.enums.GroupIdentifyTypeEnums;
import com.liyang.client.tianan.enums.IdentifyTypeEnums;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * 投保人信息
 * 
 * @author Administrator
 *
 */
public class ApplyInfoDto implements IDto {
	private final String insuredType;
	private final TypeDate applybirthDate;
	private final String applyName;
	private final String applyidentifyType;
	private final String applyidentifyNumber;
	private final String applysex;
	private final String applymobilePhone;
	private final String applyAddress;
	private final String applyemail;
	private final Date certiStartDate;
	private final Date certiEndDate;
	private final String nation;
	private final String issuer;

	/**
	 * 投保人类型
	 * 
	 * @return the insuredType
	 */
	public String getInsuredType() {
		return insuredType;
	}

	/**
	 * 投保人出生日期
	 * 
	 * @return the applybirthDate
	 */
	public String getApplybirthDate() {
		return applybirthDate == null ? null : applybirthDate.getString();
	}

	/**
	 * 投保人姓名
	 * 
	 * @return the applyName
	 */
	public String getApplyName() {
		return applyName;
	}

	/**
	 * 投保人证件类型
	 * 
	 * @return the applyidentifyType
	 */
	public String getApplyidentifyType() {
		return applyidentifyType;
	}

	/**
	 * 投保人证件号码
	 * 
	 * @return the applyidentifyNumber
	 */
	public String getApplyidentifyNumber() {
		return applyidentifyNumber;
	}

	/**
	 * 投保人性别
	 * 
	 * @return the applysex
	 */
	public String getApplysex() {
		return applysex;
	}

	/**
	 * 投保人电话
	 * 
	 * @return the applymobilePhone
	 */
	public String getApplymobilePhone() {
		return applymobilePhone;
	}

	/**
	 * 投保人地址
	 * 
	 * @return the applyAddress
	 */
	public String getApplyAddress() {
		return applyAddress;
	}

	/**
	 * 投保人邮箱
	 * 
	 * @return the applyemail
	 */
	public String getApplyemail() {
		return applyemail;
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
		if ("1".equals(this.insuredType) || this.insuredType == null || this.insuredType.trim().equals("")) {
			if (StringUtils.isEmpty(this.applybirthDate)) {
				throw new ExceptionTiananParamInvliad("投保人出生日期不能为空");
			}
			if (StringUtils.isEmpty(this.applysex)) {
				throw new ExceptionTiananParamInvliad("投保人性别不能为空");
			} else if (!("0".equals(this.applysex) || "1".equals(this.applysex))) {
				throw new ExceptionTiananParamInvliad("投保人性别不正确");
			}
			if (StringUtils.isEmpty(this.applymobilePhone)) {
				throw new ExceptionTiananParamInvliad("投保人电话不能为空");
			}
			if (StringUtils.isEmpty(this.applyAddress)) {
				throw new ExceptionTiananParamInvliad("投保人地址不能为空");
			}
			if (StringUtils.isEmpty(this.applyidentifyType)) {
				throw new ExceptionTiananParamInvliad("投保人证件类型不能为空");
			} else {
				if (!IdentifyTypeEnums.isExist(this.applyidentifyType)) {
					throw new ExceptionTiananParamInvliad("投保人证件类型不匹配");
				}
			}
		} else {
			if (StringUtils.isEmpty(this.applyidentifyType)) {
				throw new ExceptionTiananParamInvliad("投保人证件类型不能为空");
			} else {
				if (!GroupIdentifyTypeEnums.isExist(this.applyidentifyType)) {
					throw new ExceptionTiananParamInvliad("投保人证件类型不匹配");
				}

			}
		}
		if (StringUtils.isEmpty(this.applyName)) {
			throw new ExceptionTiananParamInvliad("投保人姓名不能为空");
		}

		if (StringUtils.isEmpty(this.applyidentifyNumber)) {
			throw new ExceptionTiananParamInvliad("投保人证件号码不能为空");
		}
	}

	public ApplyInfoDto(Builder builder) {
		super();
		this.insuredType = builder.insuredType;
		this.applybirthDate = builder.applybirthDate;
		this.applyName = builder.applyName;
		this.applyidentifyType = builder.applyidentifyType;
		this.applyidentifyNumber = builder.applyidentifyNumber;
		this.applysex = builder.applysex;
		this.applymobilePhone = builder.applymobilePhone;
		this.applyAddress = builder.applyAddress;
		this.applyemail = builder.applyemail;
		this.certiStartDate = builder.certiStartDate;
		this.certiEndDate = builder.certiEndDate;
		this.nation = builder.nation;
		this.issuer = builder.issuer;
	}

	public static class Builder {
		private String insuredType;
		private TypeDate applybirthDate;
		private final String applyName;
		private final String applyidentifyType;
		private final String applyidentifyNumber;
		private String applysex;
		private String applymobilePhone;
		private String applyAddress;
		private String applyemail;
		private Date certiStartDate;
		private Date certiEndDate;
		private String nation;
		private String issuer;

		/**
		 * 初始化值</br>
		 * insuredType = 1
		 */
		public Builder(String applyName, String applyidentifyType, String applyidentifyNumber) {
			initDefaultValue();
			this.applyName = applyName;
			this.applyidentifyType = applyidentifyType;
			this.applyidentifyNumber = applyidentifyNumber;
		}

		public void initDefaultValue() {
			insuredType("1");
		}

		public Builder insuredType(String insuredType) {
			this.insuredType = insuredType;
			return this;
		}

		public Builder applybirthDate(TypeDate applybirthDate) {
			this.applybirthDate = applybirthDate;
			return this;
		}

		public Builder applysex(String applysex) {
			this.applysex = applysex;
			return this;
		}

		public Builder applymobilePhone(String applymobilePhone) {
			this.applymobilePhone = applymobilePhone;
			return this;
		}

		public Builder applyAddress(String applyAddress) {
			this.applyAddress = applyAddress;
			return this;
		}

		public Builder applyemail(String applyemail) {
			this.applyemail = applyemail;
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

		public ApplyInfoDto build() throws ExceptionTiananParamInvliad {
			ApplyInfoDto dto = new ApplyInfoDto(this);
			dto.validate();
			return dto;
		}
	}

}
