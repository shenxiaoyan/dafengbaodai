package com.liyang.client.tianan;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.liyang.client.BaseRequestHeader;
import com.liyang.client.IMessage;
import com.liyang.client.tianan.exception.ExceptionTiananInitFailed;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * @author Administrator
 *
 */
public class MessageQueryCarModel implements IMessage {
	// public MessageQueryCarModel() throws ExceptionTiananInitFailed {
	// BaseRequestHeader requestHeader =
	// FacadeService.getInstance().getDefaultRequestHeader();
	// setRequestHead(requestHeader);
	// initDefaultValue();
	// }

	private final String cityCode;

	private final String brandName;

	private final String enginNo;

	private final TypeDate enrollDate;

	private final TypeDate startDate;

	private final String frameNo;

	private final String licenseNo;

	private final BaseRequestHeader requestHead;

	private final Integer page;

	private final Integer rows;

	private final TypeDate purchaseDate;

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
	 * 车辆型号
	 * 
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}

	/**
	 * 发动机号
	 * 
	 * @return the enginNo
	 */
	public String getEnginNo() {
		return enginNo;
	}

	/**
	 * 初次登记日期
	 * 
	 * @return the enrollDate
	 */
	public String getEnrollDate() {
		return enrollDate == null ? null : enrollDate.getString();
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
	 * 车辆识别代码
	 * 
	 * @return the frameNo
	 */
	public String getFrameNo() {
		return frameNo;
	}

	/**
	 * 车牌号码
	 * 
	 * @return the licenseNo
	 */
	public String getLicenseNo() {
		return licenseNo;
	}

	/**
	 * 请求头信息
	 * 
	 * @return the requestHead
	 */
	public BaseRequestHeader getRequestHead() {
		return requestHead;
	}

	/**
	 * 起始页
	 * 
	 * @return the page
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * 页大小
	 * 
	 * @return the rows
	 */
	public Integer getRows() {
		return rows;
	}

	/**
	 * 车辆购置日期
	 * 
	 * @return the purchaseDate
	 */
	public String getPurchaseDate() {
		return purchaseDate == null ? null : purchaseDate.getString();
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {
		if (StringUtils.isEmpty(this.cityCode)) {
			throw new ExceptionTiananParamInvliad("城市代码不能为空");
		}
		if (StringUtils.isEmpty(this.brandName)) {
			throw new ExceptionTiananParamInvliad("车辆型号不能为空");
		}
		if (StringUtils.isEmpty(this.enginNo)) {
			throw new ExceptionTiananParamInvliad("发动机号不能为空");
		}
		if (StringUtils.isEmpty(this.enrollDate.getString())) {
			throw new ExceptionTiananParamInvliad("初次登记日期不能为空");
		}
		if (StringUtils.isEmpty(this.startDate.getString())) {
			throw new ExceptionTiananParamInvliad("起保日期不能为空");
		}
		if (StringUtils.isEmpty(this.frameNo)) {
			throw new ExceptionTiananParamInvliad("车辆识别代码不能为空");
		}
		if (StringUtils.isEmpty(this.licenseNo)) {
			throw new ExceptionTiananParamInvliad("车牌号码不能为空");
		}
	}

	@Override
	public void initDefaultValue() {
		// this.setPage("1");
		// this.setRows("20");

	}

	// public MessageQueryCarModel(String cityCode, String brandName, String
	// enginNo, TypeDate enrollDate,
	// TypeDate startDate, String frameNo, String licenseNo) throws
	// ExceptionTiananInitFailed {
	// BaseRequestHeader requestHeader =
	// FacadeService.getInstance().getDefaultRequestHeader();
	// //setRequestHead(requestHeader);
	// initDefaultValue();
	// this.cityCode = cityCode;
	// this.brandName = brandName;
	// this.enginNo = enginNo;
	// this.enrollDate = enrollDate;
	// this.startDate = startDate;
	// this.frameNo = frameNo;
	// this.licenseNo = licenseNo;
	// }

	public MessageQueryCarModel(Builder builder) {
		this.cityCode = builder.cityCode;
		this.brandName = builder.brandName;
		this.enginNo = builder.enginNo;
		this.enrollDate = builder.enrollDate;
		this.startDate = builder.startDate;
		this.frameNo = builder.frameNo;
		this.licenseNo = builder.licenseNo;
		this.requestHead = builder.requestHead;
		this.page = builder.page;
		this.rows = builder.rows;
		this.purchaseDate = builder.purchaseDate;
	}

	public static class Builder {
		private final String cityCode;

		private final String brandName;

		private final String enginNo;

		private final TypeDate enrollDate;

		private final TypeDate startDate;

		private final String frameNo;

		private final String licenseNo;

		private final BaseRequestHeader requestHead;

		private Integer page;

		private Integer rows;

		private TypeDate purchaseDate;

		/**
		 * 初始化值</br>
		 * page = 1</br>
		 * rows = 100
		 */
		public Builder(String baseUrl, String cityCode, String brandName, String enginNo, TypeDate enrollDate, TypeDate startDate,
				String frameNo, String licenseNo) throws ExceptionTiananInitFailed {
			BaseRequestHeader requestHeader = FacadeService.getInstance(baseUrl).getDefaultRequestHeader("");
			this.requestHead = requestHeader;
			initDefaultValue();
			this.cityCode = cityCode;
			this.brandName = brandName;
			this.enginNo = enginNo;
			this.enrollDate = enrollDate;
			this.startDate = startDate;
			this.frameNo = frameNo;
			this.licenseNo = licenseNo;
		}

		private void initDefaultValue() {
			page(1);
			// 设置大点一次性全取出来
			rows(100);
		}

		public Builder page(Integer page) {
			this.page = page;
			return this;
		}

		public Builder rows(Integer rows) {
			this.rows = rows;
			return this;
		}

		public Builder purchaseDate(TypeDate purchaseDate) {
			this.purchaseDate = purchaseDate;
			return this;
		}

		public MessageQueryCarModel build() throws ExceptionTiananParamInvliad {
			MessageQueryCarModel message = new MessageQueryCarModel(this);
			message.validate();
			return message;
		}
	}

}
