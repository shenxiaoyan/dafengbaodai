package com.liyang.client.tianan;

import java.util.List;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.client.BaseRequestHeader;
import com.liyang.client.IMessage;
import com.liyang.client.tianan.dto.CarInfoDto;
import com.liyang.client.tianan.dto.CheckDto;
import com.liyang.client.tianan.dto.CombosDataDto;
import com.liyang.client.tianan.dto.ExtendInfoDTO;
import com.liyang.client.tianan.enums.QuotationTypeEnums;
import com.liyang.client.tianan.exception.ExceptionTiananInitFailed;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.platform.Platform;

/**
 * @author Administrator
 *
 */
public class MessagePremiumAccurate implements IMessage {

	// public MessagePremiumAccurate() throws ExceptionTiananInitFailed {
	// BaseRequestHeader requestHeader =
	// FacadeService.getInstance().getDefaultRequestHeader();
	// setRequestHead(requestHeader);
	// initDefaultValue();
	// }

	private final BaseRequestHeader requestHead;

	private final String quotationType;

	private final String cityCode;

	private final String businessNature;

	private final String checkNo;

	private final String checkCode;

	private final TypeDate startDate;

	private final TypeDate endDate;

	private final TypeDate startDateBus;

	private final TypeDate endDateBus;

	private final CarInfoDto carInfoDto;

	private final List<CombosDataDto> combosList;

	private final List<ExtendInfoDTO> extendInfoList;

	private final List<CheckDto> checkList;

	private final String thirdOperatorCode;

	private final String thirdOperatorName;

	private final String isLoanCar;

	private final String beneficiaryName;

	private final String paymentMode;

	/**
	 * // fastJson设置不序列化，与@JsonIgnore类似
	 */
	@JsonIgnore
	@JSONField(serialize = false, deserialize = false) 
	private final String mobilePhone;

	/**
	 * // fastJson设置不序列化，与@JsonIgnore类似
	 */
	@JsonIgnore
	@JSONField(serialize = false, deserialize = false) 
	private final Customer customer;

	/**
	 * // fastJson设置不序列化，与@JsonIgnore类似
	 */
	@JsonIgnore
	@JSONField(serialize = false, deserialize = false) 
	private final Platform platform;

	public String getMobilePhone() {
		return mobilePhone;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Platform getPlatform() {
		return platform;
	}

	/**
	 * @return the requestHeader
	 */
	public BaseRequestHeader getRequestHead() {
		return requestHead;
	}

	/**
	 * 计算类型
	 * 
	 * @return the quotationType
	 */
	public String getQuotationType() {
		return quotationType;
	}

	/**
	 * 城市代码
	 * 天安传递的行政区划为国标加前缀"3".所以不用该方法序列号，使用getTACityCode方法序列化
	 * @return the cityCode
	 */
	@JSONField(serialize = false) 
	public String getCityCode() {
		return cityCode;
	}

	@JSONField(name = "cityCode")
	public String getTACityCode() {
		return "3" + cityCode;
	}

	/**
	 * 业务来源
	 * 
	 * @return the businessNature
	 */
	public String getBusinessNature() {
		return businessNature;
	}

	/**
	 * 交管车辆查询码
	 * 
	 * @return the checkNo
	 */
	public String getCheckNo() {
		return checkNo;
	}

	/**
	 * 校验码
	 * 
	 * @return the checkCode
	 */
	public String getCheckCode() {
		return checkCode;
	}

	/**
	 * 起保日期
	 * 
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate == null ? null : startDate.getString();
	}

	/**
	 * 终保日期
	 * 
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate == null ? null : endDate.getString();
	}

	/**
	 * 起保日期
	 * 
	 * @return the startDateBus
	 */
	public String getStartDateBus() {
		return startDateBus == null ? null : startDateBus.getString();
	}

	/**
	 * 终保日期
	 * 
	 * @return the endDateBus
	 */
	public String getEndDateBus() {
		return endDateBus == null ? null : endDateBus.getString();
	}

	/**
	 * 车辆信息
	 * 
	 * @return the carInfoDto
	 */
	public CarInfoDto getCarInfoDto() {
		return carInfoDto;
	}

	/**
	 * 商业险套餐信息
	 * 
	 * @return the combosList
	 */
	public List<CombosDataDto> getCombosList() {
		return combosList;
	}

	/**
	 * 扩展信息
	 * 
	 * @return the extendInfoList
	 */
	public List<ExtendInfoDTO> getExtendInfoList() {
		return extendInfoList;
	}

	/**
	 * 转保校验信息
	 * 
	 * @return the checkList
	 */
	public List<CheckDto> getCheckList() {
		return checkList;
	}

	/**
	 * 第三方经办人代码
	 * 
	 * @return the thirdOperatorCode
	 */
	public String getThirdOperatorCode() {
		return thirdOperatorCode;
	}

	/**
	 * 第三方经办人姓名
	 * 
	 * @return the thirdOperatorName
	 */
	public String getThirdOperatorName() {
		return thirdOperatorName;
	}

	/**
	 * 是否按揭车
	 * 
	 * @return the isLoanCar
	 */
	public String getIsLoanCar() {
		return isLoanCar;
	}

	/**
	 * 第一受益人名称
	 * 
	 * @return the beneficiaryName
	 */
	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	/**
	 * 即时生效标志
	 * 
	 * @return the paymentMode
	 */
	public String getPaymentMode() {
		return paymentMode;
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {
		if (StringUtils.isEmpty(this.quotationType)) {
			throw new ExceptionTiananParamInvliad("计算类型不能为空");
		} else {
			if (!QuotationTypeEnums.isExist(this.quotationType)) {
				throw new ExceptionTiananParamInvliad("计算类型不匹配");
			}
		}

		if (StringUtils.isEmpty(this.cityCode)) {
			throw new ExceptionTiananParamInvliad("城市代码不能为空");
		}
		// TODO checkNo 江苏地区必传
		// TODO checkCode 校验码
		if (StringUtils.isEmpty(this.startDate)) {
			throw new ExceptionTiananParamInvliad("起保日期不能为空");
		}
		if (StringUtils.isEmpty(this.endDate)) {
			throw new ExceptionTiananParamInvliad("终保日期不能为空");
		}
		// TODO startDateBus startDateBus 交商起保日期不一致时必传
		// TODO endDateBus 交商起保日期不一致时必传
		if (StringUtils.isEmpty(this.carInfoDto)) {
			throw new ExceptionTiananParamInvliad("车辆信息不能为空");
		}
		if (this.combosList.size() == 0) {
			throw new ExceptionTiananParamInvliad("商业险套餐信息不能为空");
		} else {
			for (CombosDataDto cd : this.combosList) {
				cd.validate();
			}
		}
		// TODO extendInfoList 必传
		// TODO beneficiaryName 是否按揭车选择是，则必填
		// TODO paymentMode 如果即时生效为是，则起保日期必须到时分秒
	}

	@Override
	public void initDefaultValue() {

	}

	public MessagePremiumAccurate(Builder builder) {
		this.requestHead = builder.requestHead;
		this.quotationType = builder.quotationType;
		this.cityCode = builder.cityCode;
		this.businessNature = builder.businessNature;
		this.checkNo = builder.checkNo;
		this.checkCode = builder.checkCode;
		this.startDate = builder.startDate;
		this.endDate = builder.endDate;
		this.startDateBus = builder.startDateBus;
		this.endDateBus = builder.endDateBus;
		this.carInfoDto = builder.carInfoDto;
		this.combosList = builder.combosList;
		this.extendInfoList = builder.extendInfoList;
		this.checkList = builder.checkList;
		this.thirdOperatorCode = builder.thirdOperatorCode;
		this.thirdOperatorName = builder.thirdOperatorName;
		this.isLoanCar = builder.isLoanCar;
		this.beneficiaryName = builder.beneficiaryName;
		this.paymentMode = builder.paymentMode;
		this.mobilePhone = builder.mobilePhone;
		this.customer = builder.customer;
		this.platform = builder.platform;
	}

	public static class Builder {
		private final BaseRequestHeader requestHead;

		private final String quotationType;

		private final String cityCode;

		private String businessNature;

		private String checkNo;

		private String checkCode;

		private final TypeDate startDate;

		private final TypeDate endDate;

		private TypeDate startDateBus;

		private TypeDate endDateBus;

		private final CarInfoDto carInfoDto;

		private final List<CombosDataDto> combosList;

		private final List<ExtendInfoDTO> extendInfoList;

		private List<CheckDto> checkList;

		private String thirdOperatorCode;

		private String thirdOperatorName;

		private String isLoanCar;

		private String beneficiaryName;

		private String paymentMode;

		private final String mobilePhone;

		private final Customer customer;

		private final Platform platform;

		/**
		 * 初始化值</br>
		 * isLoanCar = 0</br>
		 * paymentMode = 2</br>
		 * businessNature = 50
		 * 
		 * @throws ExceptionTiananInitFailed
		 */
		public Builder(String baseUrl, String tradeNo, String quotationType, String cityCode, TypeDate startDate, TypeDate endDate,
				CarInfoDto carInfoDto, List<CombosDataDto> combosList, List<ExtendInfoDTO> extendInfoList,
				String mobilePhone, Customer customer, Platform platform) throws ExceptionTiananInitFailed {
			if (StringUtils.isEmpty(tradeNo)) {
				throw new ExceptionTiananInitFailed("tradeNo为空");
			}
			BaseRequestHeader requestHeader = FacadeService.getInstance(baseUrl).getDefaultRequestHeader(tradeNo);
			this.requestHead = requestHeader;
			initDefaultValue();
			this.quotationType = quotationType;
			this.cityCode = cityCode;
			this.startDate = startDate;
			this.endDate = endDate;
			this.carInfoDto = carInfoDto;
			this.combosList = combosList;
			this.extendInfoList = extendInfoList;
			this.mobilePhone = mobilePhone;
			this.customer = customer;
			this.platform = platform;
		}

		public void initDefaultValue() {
			// isLoanCar("0");
			// paymentMode("2");
			businessNature("50");

		}

		public Builder checkNo(String checkNo) {
			this.checkNo = checkNo;
			return this;
		}

		public Builder businessNature(String businessNature) {
			this.businessNature = businessNature;
			return this;
		}

		public Builder checkCode(String checkCode) {
			this.checkCode = checkCode;
			return this;
		}

		public Builder startDateBus(TypeDate startDateBus) {
			this.startDateBus = startDateBus;
			return this;
		}

		public Builder endDateBus(TypeDate endDateBus) {
			this.endDateBus = endDateBus;
			return this;
		}

		public Builder checkList(List<CheckDto> checkList) {
			this.checkList = checkList;
			return this;
		}

		public Builder thirdOperatorCode(String thirdOperatorCode) {
			this.thirdOperatorCode = thirdOperatorCode;
			return this;
		}

		public Builder thirdOperatorName(String thirdOperatorName) {
			this.thirdOperatorName = thirdOperatorName;
			return this;
		}

		public Builder isLoanCar(String isLoanCar) {
			this.isLoanCar = isLoanCar;
			return this;
		}

		public Builder beneficiaryName(String beneficiaryName) {
			this.beneficiaryName = beneficiaryName;
			return this;
		}

		public Builder paymentMode(String paymentMode) {
			this.paymentMode = paymentMode;
			return this;
		}

		public MessagePremiumAccurate build() throws ExceptionTiananParamInvliad {
			MessagePremiumAccurate message = new MessagePremiumAccurate(this);
			message.validate();
			return message;
		}
	}

}
