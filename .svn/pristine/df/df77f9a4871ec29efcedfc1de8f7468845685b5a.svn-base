package com.liyang.client.tianan.dto;

import java.util.Date;
import org.springframework.util.StringUtils;

import com.liyang.client.IDto;
import com.liyang.client.tianan.TypeDate;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * 车辆信息
 * 
 * @author Administrator
 *
 */
public class CarInfoDto implements IDto {
	private final String licenseNo;
	private final String engineNo;
	private final String frameNo;
	private final String brandName;
	private final String rbCode;
	private final TypeDate enrollDate;
	private final String ecdemicFlag;
	private final String transferFlag;
	private final TypeDate transferDate;
	private final Integer seatCount;
	private final String carOwner;
	private final String carOwnerIdentifyNumber;
	private final String useNatureCode;
	private final Double actualValue;
	private final Double purchasePrice;
	private final String importFlag;
	private final String fuelType;
	private final String carTypeAlias;
	private final Double tonCount;
	private final Double exhaustScale;
	private final String carName;
	private final String hyModelCode;
	private final String noticeType;
	private final Double wholeWeight;
	private final String certificateType;
	private final String certificateNo;
	private final Date certificateDate;
	private final VehicleJingyouDto vehicleJingyouDto;

	/**
	 * 车牌号
	 * 
	 * @return the licenseNo
	 */
	public String getLicenseNo() {
		return licenseNo;
	}
	
	/**
	 * 发动机号
	 * 
	 * @return the engineNo
	 */
	public String getEngineNo() {
		return engineNo;
	}

	/**
	 * 车架号
	 * 
	 * @return the frameNo
	 */
	public String getFrameNo() {
		return frameNo;
	}

	/**
	 * 车型名称
	 * 
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}

	/**
	 * 车型代码
	 * 
	 * @return the rbCode
	 */
	public String getRbCode() {
		return rbCode;
	}

	/**
	 * 初登日期
	 * 
	 * @return the enrollDate
	 */
	public String getEnrollDate() {
		return enrollDate == null ? null : enrollDate.getString();
	}

	/**
	 * 外地车标识
	 * 
	 * @return the ecdemicFlag
	 */
	public String getEcdemicFlag() {
		return ecdemicFlag;
	}

	/**
	 * 过户车标识
	 * 
	 * @return the transferFlag
	 */
	public String getTransferFlag() {
		return transferFlag;
	}

	/**
	 * 过户日期
	 * 
	 * @return the transferDate
	 */
	public String getTransferDate() {
		return transferDate == null ? null : transferDate.getString();
	}

	/**
	 * 核定载客
	 * 
	 * @return the seatCount
	 */
	public Integer getSeatCount() {
		return seatCount;
	}

	/**
	 * 车主姓名
	 * 
	 * @return the carOwner
	 */
	public String getCarOwner() {
		return carOwner;
	}

	/**
	 * 车主身份证
	 * 
	 * @return the carOwnerIdentifyNumber
	 */
	public String getCarOwnerIdentifyNumber() {
		return carOwnerIdentifyNumber;
	}

	/**
	 * 车辆使用性质
	 * 
	 * @return the useNatureCode
	 */
	public String getUseNatureCode() {
		return useNatureCode;
	}

	/**
	 * 车辆实际价值
	 * 
	 * @return the actualValue
	 */
	public Double getActualValue() {
		return actualValue;
	}

	/**
	 * 新车购置价
	 * 
	 * @return the purchasePrice
	 */
	public Double getPurchasePrice() {
		return purchasePrice;
	}

	/**
	 * 产地种类
	 * 
	 * @return the importFlag
	 */
	public String getImportFlag() {
		return importFlag;
	}

	/**
	 * 能源种类
	 * 
	 * @return the fuelType
	 */
	public String getFuelType() {
		return fuelType;
	}

	/**
	 * 厂牌型号别名
	 * 
	 * @return the carTypeAlias
	 */
	public String getCarTypeAlias() {
		return carTypeAlias;
	}

	/**
	 * 核定载质量
	 * 
	 * @return the tonCount
	 */
	public Double getTonCount() {
		return tonCount;
	}

	/**
	 * 排量
	 * 
	 * @return the exhaustScale
	 */
	public Double getExhaustScale() {
		return exhaustScale;
	}

	/**
	 * 车款名称
	 * 
	 * @return the carName
	 */
	public String getCarName() {
		return carName;
	}

	/**
	 * 行业车型编码
	 * 
	 * @return the hyModelCode
	 */
	public String getHyModelCode() {
		return hyModelCode;
	}

	/**
	 * 公告型号
	 * 
	 * @return the noticeType
	 */
	public String getNoticeType() {
		return noticeType;
	}

	/**
	 * 整备质量
	 * 
	 * @return the wholeWeight
	 */
	public Double getWholeWeight() {
		return wholeWeight;
	}

	/**
	 * 车辆来历凭证种类
	 * 
	 * @return the certificateType
	 */
	public String getCertificateType() {
		return certificateType;
	}

	/**
	 * 车辆来历凭证编号
	 * 
	 * @return the certificateNo
	 */
	public String getCertificateNo() {
		return certificateNo;
	}

	/**
	 * 开具车辆来历凭证所载日期
	 * 
	 * @return the certificateDate
	 */
	public Date getCertificateDate() {
		return certificateDate;
	}

	/**
	 * 精友车库信息
	 * 
	 * @return the vehicleJingyouDto
	 */
	public VehicleJingyouDto getVehicleJingyouDto() {
		return vehicleJingyouDto;
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {
		// 未上牌可传空
		if (this.getLicenseNo() == null) {
			throw new ExceptionTiananParamInvliad("车牌号不能为空");
		}
		if (StringUtils.isEmpty(this.getEngineNo())) {
			throw new ExceptionTiananParamInvliad("发动机号不能为空");
		}
		if (StringUtils.isEmpty(this.getFrameNo())) {
			throw new ExceptionTiananParamInvliad("车架号不能为空");
		}
		if (StringUtils.isEmpty(this.getBrandName())) {
			throw new ExceptionTiananParamInvliad("车型名称不能为空");
		}
		if (StringUtils.isEmpty(this.getRbCode())) {
			throw new ExceptionTiananParamInvliad("车型代码不能为空");
		}
		if (StringUtils.isEmpty(this.getEnrollDate())) {
			throw new ExceptionTiananParamInvliad("初登日期不能为空");
		}
		if (StringUtils.isEmpty(this.getEcdemicFlag())) {
			throw new ExceptionTiananParamInvliad("外地车标识不能为空");
		}
		if (StringUtils.isEmpty(this.getTransferFlag())) {
			throw new ExceptionTiananParamInvliad("过户车标识不能为空");
		} else {
			if (!"1".equals(this.getTransferFlag()) && !"0".equals(this.getTransferFlag())) {
				throw new ExceptionTiananParamInvliad("标识不匹配");
			}
		}
		if ("1".equals(this.getTransferFlag()) && StringUtils.isEmpty(this.getTransferDate())) {
			throw new ExceptionTiananParamInvliad("过户日期不能为空");
		}
		if (StringUtils.isEmpty(this.getSeatCount())) {
			throw new ExceptionTiananParamInvliad("核定载客不能为空");
		}
		if (StringUtils.isEmpty(this.getCarOwnerIdentifyNumber())) {
			throw new ExceptionTiananParamInvliad("车主身份证不能为空");
		}
		if (StringUtils.isEmpty(this.getUseNatureCode())) {
			throw new ExceptionTiananParamInvliad("车辆使用性质不能为空");
		}
		if (this.getActualValue() == null) {
			throw new ExceptionTiananParamInvliad("车辆实际价值不能为空");
		}
		if (this.purchasePrice == null) {
			throw new ExceptionTiananParamInvliad("新车购置价不能为空");
		}
		if (StringUtils.isEmpty(this.getImportFlag())) {
			throw new ExceptionTiananParamInvliad("产地种类不能为空");
		} else {
			if (!("A".equals(this.getImportFlag()) || "B".equals(this.getImportFlag())
					|| "C".equals(this.getImportFlag()))) {
				throw new ExceptionTiananParamInvliad("产地种类不匹配");
			}
		}
		// if (StringUtils.isEmpty(this.getCarName())) {
		// throw new ExceptionTiananParamInvliad("车款名称不能为空");
		// }
		// if (StringUtils.isEmpty(this.getHyModelCode())) {
		// throw new ExceptionTiananParamInvliad("行业车型编码不能为空");
		// }
		// if (StringUtils.isEmpty(this.getNoticeType())) {
		// throw new ExceptionTiananParamInvliad("公告型号不能为空");
		// }
		if (StringUtils.isEmpty(this.getWholeWeight())) {
			throw new ExceptionTiananParamInvliad("整备质量不能为空");
		}
		// if (StringUtils.isEmpty(this.getVehicleJingyouDto())) {
		// throw new ExceptionTiananParamInvliad("精友车库信息不能为空");
		// }
		this.getVehicleJingyouDto().validate();

	}

	public CarInfoDto(Builder builder) {
		this.licenseNo = builder.licenseNo;
		this.engineNo = builder.engineNo;
		this.frameNo = builder.frameNo;
		this.brandName = builder.brandName;
		this.rbCode = builder.rbCode;
		this.enrollDate = builder.enrollDate;
		this.ecdemicFlag = builder.ecdemicFlag;
		this.transferFlag = builder.transferFlag;
		this.transferDate = builder.transferDate;
		this.seatCount = builder.seatCount;
		this.carOwner = builder.carOwner;
		this.carOwnerIdentifyNumber = builder.carOwnerIdentifyNumber;
		this.useNatureCode = builder.useNatureCode;
		this.actualValue = builder.actualValue;
		this.purchasePrice = builder.purchasePrice;
		this.importFlag = builder.importFlag;
		this.fuelType = builder.fuelType;
		this.carTypeAlias = builder.carTypeAlias;
		this.tonCount = builder.tonCount;
		this.exhaustScale = builder.exhaustScale;
		this.carName = builder.carName;
		this.hyModelCode = builder.hyModelCode;
		this.noticeType = builder.noticeType;
		this.wholeWeight = builder.wholeWeight;
		this.certificateType = builder.certificateType;
		this.certificateNo = builder.certificateNo;
		this.certificateDate = builder.certificateDate;
		this.vehicleJingyouDto = builder.vehicleJingyouDto;
	}

	public static class Builder {
		// 未上牌则传空字符串
		private final String licenseNo;
		private final String engineNo;
		private final String frameNo;
		private final String brandName;
		private final String rbCode;
		private final TypeDate enrollDate;
		private final String ecdemicFlag;
		private String transferFlag;
		private TypeDate transferDate;
		private final Integer seatCount;
		private String carOwner;
		private final String carOwnerIdentifyNumber;
		private String useNatureCode;
		private final Double actualValue;
		private final Double purchasePrice;
		private final String importFlag;
		private String fuelType;
		private String carTypeAlias;
		private Double tonCount;
		private Double exhaustScale;
		private String carName;
		private String hyModelCode;
		private String noticeType;
		private Double wholeWeight;
		private String certificateType;
		private String certificateNo;
		private Date certificateDate;
		private final VehicleJingyouDto vehicleJingyouDto;

		/**
		 * 初始化值</br>
		 * transferFlag = 0</br>
		 * useNatureCode = 210</br>
		 * fuelType =0
		 */
		public Builder(String engineNo, String licenseNo, String frameNo, String brandName, String rbCode,
				TypeDate enrollDate, String ecdemicFlag, Integer seatCount, String carOwnerIdentifyNumber,
				Double actualValue, Double purchasePrice, String importFlag, VehicleJingyouDto vehicleJingyouDto) {
			initDefaultValue();
			this.licenseNo = licenseNo;
			this.engineNo = engineNo;
			this.frameNo = frameNo;
			this.brandName = brandName;
			this.rbCode = rbCode;
			this.enrollDate = enrollDate;
			this.ecdemicFlag = ecdemicFlag;
			this.seatCount = seatCount;
			this.carOwnerIdentifyNumber = carOwnerIdentifyNumber;
			this.actualValue = actualValue;
			this.purchasePrice = purchasePrice;
			this.importFlag = importFlag;
			this.vehicleJingyouDto = vehicleJingyouDto;
		}

		public void initDefaultValue() {
			transferFlag("0");
			useNatureCode("210");
			fuelType("0");
		}

		public Builder transferFlag(String transferFlag) {
			this.transferFlag = transferFlag;
			return this;
		}

		public Builder transferDate(TypeDate transferDate) {
			this.transferDate = transferDate;
			return this;
		}

		public Builder carOwner(String carOwner) {
			this.carOwner = carOwner;
			return this;
		}

		public Builder useNatureCode(String useNatureCode) {
			this.useNatureCode = useNatureCode;
			return this;
		}

		public Builder fuelType(String fuelType) {
			this.fuelType = fuelType;
			return this;
		}

		public Builder carTypeAlias(String carTypeAlias) {
			this.carTypeAlias = carTypeAlias;
			return this;
		}

		public Builder tonCount(Double tonCount) {
			this.tonCount = tonCount;
			return this;
		}

		public Builder exhaustScale(Double exhaustScale) {
			this.exhaustScale = exhaustScale;
			return this;
		}

		public Builder carName(String carName) {
			this.carName = carName;
			return this;
		}

		public Builder hyModelCode(String hyModelCode) {
			this.hyModelCode = hyModelCode;
			return this;
		}

		public Builder noticeType(String noticeType) {
			this.noticeType = noticeType;
			return this;
		}

		public Builder wholeWeight(Double wholeWeight) {
			this.wholeWeight = wholeWeight;
			return this;
		}

		public Builder certificateType(String certificateType) {
			this.certificateType = certificateType;
			return this;
		}

		public Builder certificateNo(String certificateNo) {
			this.certificateNo = certificateNo;
			return this;
		}

		public Builder certificateDate(Date certificateDate) {
			this.certificateDate = certificateDate;
			return this;
		}

		public CarInfoDto build() throws ExceptionTiananParamInvliad {
			CarInfoDto dto = new CarInfoDto(this);
			dto.validate();
			return dto;
		}
	}
}
