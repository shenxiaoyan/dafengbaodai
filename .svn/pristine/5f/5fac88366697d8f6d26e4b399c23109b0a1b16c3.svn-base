package com.liyang.domain.carModel;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author huanghengkun
 * @create 2017-11-26-下午3:40
 */
@Entity
@Table(name = "car_model_vehicle_model")
public class CarModelVehicleModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	// 车辆型号
	@Column(name = "brand_name")
	private String brandName;
	// 车辆类型描述
	@Column(name = "vehicle_style_desc")
	private String vehicleStyleDesc;
	// 核定载客
	@Column(name = "seat_count")
	private String seatCount;
	// 新车购置价
	@Column(name = "purchase_price")
	private Double purchasePrice;
	// 产地种类
	@Column(name = "import_flag")
	private String importFlag;
	// 年款(上市年份)
	@Column(name = "market_date")
	private String marketDate;
	// 载重量
	@Column(name = "vehicle_tonnage")
	private Float vehicleTonnage;
	// 排量
	@Column(name = "exhaust_capacity")
	private Double exhaustCapacity;
	// 整车质量
	@Column(name = "vehicle_weight")
	private Double vehicleWeight;
	// 精友整车库信息
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "vehicle_jingyou_id")
	private CarModelVehicleJingyou vehicleJingyouDto;
	// 车型代码
	@Column
	private String rbcode;
	// 实际价值
	@Column(name = "actualvalue")
	private Double actualValue;
	// 车款名称
	@Column(name = "car_name")
	private String carName;
	// 行业车型编码
	@Column(name = "hyModel_code")
	private String hyModelCode;
	// 公告型号
	@Column(name = "notice_type")
	private String noticeType;
	// 外地车标志
	@Column(name = "ecdemic_vehicle_flag")
	private String ecdemicVehicleFlag;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = CarModel.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "car_model_id", referencedColumnName = "id")
	private CarModel carModel;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}

	/**
	 * @param brandName
	 *            the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * @return the vehicleStyleDesc
	 */
	public String getVehicleStyleDesc() {
		return vehicleStyleDesc;
	}

	/**
	 * @param vehicleStyleDesc
	 *            the vehicleStyleDesc to set
	 */
	public void setVehicleStyleDesc(String vehicleStyleDesc) {
		this.vehicleStyleDesc = vehicleStyleDesc;
	}

	/**
	 * @return the seatCount
	 */
	public String getSeatCount() {
		return seatCount;
	}

	/**
	 * @param seatCount
	 *            the seatCount to set
	 */
	public void setSeatCount(String seatCount) {
		this.seatCount = seatCount;
	}

	/**
	 * @return the purchasePrice
	 */
	public Double getPurchasePrice() {
		return purchasePrice;
	}

	/**
	 * @param purchasePrice
	 *            the purchasePrice to set
	 */
	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	/**
	 * @return the importFlag
	 */
	public String getImportFlag() {
		return importFlag;
	}

	/**
	 * @param importFlag
	 *            the importFlag to set
	 */
	public void setImportFlag(String importFlag) {
		this.importFlag = importFlag;
	}

	/**
	 * @return the marketDate
	 */
	public String getMarketDate() {
		return marketDate;
	}

	/**
	 * @param marketDate
	 *            the marketDate to set
	 */
	public void setMarketDate(String marketDate) {
		this.marketDate = marketDate;
	}

	/**
	 * @return the vehicleTonnage
	 */
	public Float getVehicleTonnage() {
		return vehicleTonnage;
	}

	/**
	 * @param vehicleTonnage
	 *            the vehicleTonnage to set
	 */
	public void setVehicleTonnage(Float vehicleTonnage) {
		this.vehicleTonnage = vehicleTonnage;
	}

	/**
	 * @return the exhaustCapacity
	 */
	public Double getExhaustCapacity() {
		return exhaustCapacity;
	}

	/**
	 * @param exhaustCapacity
	 *            the exhaustCapacity to set
	 */
	public void setExhaustCapacity(Double exhaustCapacity) {
		this.exhaustCapacity = exhaustCapacity;
	}

	/**
	 * @return the vehicleWeight
	 */
	public Double getVehicleWeight() {
		return vehicleWeight;
	}

	/**
	 * @param vehicleWeight
	 *            the vehicleWeight to set
	 */
	public void setVehicleWeight(Double vehicleWeight) {
		this.vehicleWeight = vehicleWeight;
	}

	/**
	 * @return the vehicleJingyouDto
	 */
	public CarModelVehicleJingyou getVehicleJingyouDto() {
		return vehicleJingyouDto;
	}

	/**
	 * @param vehicleJingyouDto
	 *            the vehicleJingyouDto to set
	 */
	public void setVehicleJingyouDto(CarModelVehicleJingyou vehicleJingyouDto) {
		this.vehicleJingyouDto = vehicleJingyouDto;
	}

	/**
	 * @return the rbcode
	 */
	public String getRbcode() {
		return rbcode;
	}

	/**
	 * @param rbcode
	 *            the rbcode to set
	 */
	public void setRbcode(String rbcode) {
		this.rbcode = rbcode;
	}

	/**
	 * @return the actualValue
	 */
	public Double getActualValue() {
		return actualValue;
	}

	/**
	 * @param actualValue
	 *            the actualValue to set
	 */
	public void setActualValue(Double actualValue) {
		this.actualValue = actualValue;
	}

	/**
	 * @return the carName
	 */
	public String getCarName() {
		return carName;
	}

	/**
	 * @param carName
	 *            the carName to set
	 */
	public void setCarName(String carName) {
		this.carName = carName;
	}

	/**
	 * @return the hyModelCode
	 */
	public String getHyModelCode() {
		return hyModelCode;
	}

	/**
	 * @param hyModelCode
	 *            the hyModelCode to set
	 */
	public void setHyModelCode(String hyModelCode) {
		this.hyModelCode = hyModelCode;
	}

	/**
	 * @return the noticeType
	 */
	public String getNoticeType() {
		return noticeType;
	}

	/**
	 * @param noticeType
	 *            the noticeType to set
	 */
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	/**
	 * @return the ecdemicVehicleFlag
	 */
	public String getEcdemicVehicleFlag() {
		return ecdemicVehicleFlag;
	}

	/**
	 * @param ecdemicVehicleFlag
	 *            the ecdemicVehicleFlag to set
	 */
	public void setEcdemicVehicleFlag(String ecdemicVehicleFlag) {
		this.ecdemicVehicleFlag = ecdemicVehicleFlag;
	}

	/**
	 * @return the carModel
	 */
	public CarModel getCarModel() {
		return carModel;
	}

	/**
	 * @param carModel
	 *            the carModel to set
	 */
	public void setCarModel(CarModel carModel) {
		this.carModel = carModel;
	}

}
