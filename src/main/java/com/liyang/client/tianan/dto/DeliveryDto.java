package com.liyang.client.tianan.dto;

import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.liyang.client.IDto;
import com.liyang.client.tianan.TypeDate;
import com.liyang.client.tianan.enums.DeliveryTypeEnums;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;
import com.liyang.util.PatternUtil;

/**
 * 配送信息
 * 
 * @author Administrator
 *
 */
public class DeliveryDto implements IDto {
	private final String acceptName;
	private final String acceptTelephone;
	private final String acceptProvince;
	private final String acceptCity;
	private final String acceptTown;
	private final String acceptAddress;
	private final String invoiceTitle;
	private final String licenceNo;
	private final String deliveryType;
	private final TypeDate appointmentTime;
	private final String backUrl;

	/**
	 * 收件人姓名
	 * 
	 * @return the acceptName
	 */
	public String getAcceptName() {
		return acceptName;
	}

	/**
	 * 收件人联系方式
	 * 
	 * @return the acceptTelephone
	 */
	public String getAcceptTelephone() {
		return acceptTelephone;
	}

	/**
	 * 收件人所在省
	 * 
	 * @return the acceptProvince
	 */
	public String getAcceptProvince() {
		return acceptProvince;
	}

	/**
	 * 收件人所在市
	 * 
	 * @return the acceptCity
	 */
	public String getAcceptCity() {
		return acceptCity;
	}

	/**
	 * 收件人所在县区
	 * 
	 * @return the acceptTown
	 */
	public String getAcceptTown() {
		return acceptTown;
	}

	/**
	 * 收件人具体地址
	 * 
	 * @return the acceptAddress
	 */
	public String getAcceptAddress() {
		return acceptAddress;
	}

	/**
	 * 发票抬头
	 * 
	 * @return the invoiceTitle
	 */
	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	/**
	 * 车牌号
	 * 
	 * @return the licenceNo
	 */
	public String getLicenceNo() {
		return licenceNo;
	}

	/**
	 * 支付方式
	 * 
	 * @return the deliveryType
	 */
	public String getDeliveryType() {
		return deliveryType;
	}

	/**
	 * 配送预约日期
	 * 
	 * @return the appointmentTime
	 */
	public String getAppointmentTime() {
		return appointmentTime == null ? null : appointmentTime.getString();
	}

	/**
	 * 回调地址
	 * 
	 * @return the backUrl
	 */
	public String getBackUrl() {
		return backUrl;
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {
		if (StringUtils.isEmpty(this.acceptName)) {
			throw new ExceptionTiananParamInvliad("收件人姓名不能为空");
		}
		if (StringUtils.isEmpty(this.acceptTelephone)) {
			throw new ExceptionTiananParamInvliad("收件人联系方式不能为空");
		} else {
			Pattern p1 = PatternUtil.MOBILEPHONE_PATTERN;
			Pattern p2 = PatternUtil.TELEPHONE_PATTERN;
			if (p1.matcher(this.acceptTelephone).matches()) {
			} else if (p2.matcher(this.acceptTelephone).matches()) {
			} else {
				throw new ExceptionTiananParamInvliad("收件人固定电话格式不正确，应为XXXX-XXXXXXXX");
			}
		}

		if (StringUtils.isEmpty(this.acceptProvince)) {
			throw new ExceptionTiananParamInvliad("收件人所在省不能为空");
		}
		if (StringUtils.isEmpty(this.acceptCity)) {
			throw new ExceptionTiananParamInvliad("收件人所在市不能为空");
		}
		if (StringUtils.isEmpty(this.acceptTown)) {
			throw new ExceptionTiananParamInvliad("收件人所在县区不能为空");
		}
		if (StringUtils.isEmpty(this.acceptAddress)) {
			throw new ExceptionTiananParamInvliad("收件人具体地址不能为空");
		}
		if (StringUtils.isEmpty(this.licenceNo)) {
			throw new ExceptionTiananParamInvliad("车牌号不能为空");
		}
		if (StringUtils.isEmpty(this.deliveryType)) {
			throw new ExceptionTiananParamInvliad("支付方式不能为空");
		} else {
			if (!DeliveryTypeEnums.isExist(this.deliveryType)) {
				throw new ExceptionTiananParamInvliad("支付方式不正确");
			}
		}

		this.appointmentTime.validate();
	}

	public DeliveryDto(Builder builder) {
		this.acceptName = builder.acceptName;
		this.acceptTelephone = builder.acceptTelephone;
		this.acceptProvince = builder.acceptProvince;
		this.acceptCity = builder.acceptCity;
		this.acceptTown = builder.acceptTown;
		this.acceptAddress = builder.acceptAddress;
		this.invoiceTitle = builder.invoiceTitle;
		this.licenceNo = builder.licenceNo;
		this.deliveryType = builder.deliveryType;
		this.appointmentTime = builder.appointmentTime;
		this.backUrl = builder.backUrl;
	}

	public static class Builder {
		private final String acceptName;
		private final String acceptTelephone;
		private final String acceptProvince;
		private final String acceptCity;
		private final String acceptTown;
		private final String acceptAddress;
		private String invoiceTitle;
		private final String licenceNo;
		private final String deliveryType;
		private final TypeDate appointmentTime;
		private String backUrl;

		public Builder(String acceptName, String acceptTelephone, String acceptProvince, String acceptCity,
				String acceptTown, String acceptAddress, String licenceNo, String deliveryType,
				TypeDate appointmentTime) {
			initDefaultValue();
			this.acceptName = acceptName;
			this.acceptTelephone = acceptTelephone;
			this.acceptProvince = acceptProvince;
			this.acceptCity = acceptCity;
			this.acceptTown = acceptTown;
			this.acceptAddress = acceptAddress;
			this.licenceNo = licenceNo;
			this.deliveryType = deliveryType;
			this.appointmentTime = appointmentTime;
		}

		public void initDefaultValue() {

		}

		public Builder invoiceTitle(String invoiceTitle) {
			this.invoiceTitle = invoiceTitle;
			return this;
		}

		public Builder backUrl(String backUrl) {
			this.backUrl = backUrl;
			return this;
		}

		public DeliveryDto build() throws ExceptionTiananParamInvliad {
			DeliveryDto dto = new DeliveryDto(this);
			dto.validate();
			return dto;
		}
	}
}
