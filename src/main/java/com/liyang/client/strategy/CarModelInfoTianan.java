package com.liyang.client.strategy;
/**
 * 
 * @author Administrator
 *
 */
public class CarModelInfoTianan implements ICarModelInfo {

	private String carModelCode;

	private String ecdemicFlag;

	private Integer seatCount;

	private Double actualValue;

	private Double purchasePrice;

	private String zoneFlag;

	/**
	 * @return the carModelCode
	 */
	public String getCarModelCode() {
		return carModelCode;
	}

	/**
	 * @param carModelCode
	 *            the carModelCode to set
	 */
	public void setCarModelCode(String carModelCode) {
		this.carModelCode = carModelCode;
	}

	/**
	 * @return the ecdemicFlag
	 */
	public String getEcdemicFlag() {
		return ecdemicFlag;
	}

	/**
	 * @param ecdemicFlag
	 *            the ecdemicFlag to set
	 */
	public void setEcdemicFlag(String ecdemicFlag) {
		this.ecdemicFlag = ecdemicFlag;
	}

	/**
	 * @return the seatCount
	 */
	public Integer getSeatCount() {
		return seatCount;
	}

	/**
	 * @param seatCount
	 *            the seatCount to set
	 */
	public void setSeatCount(Integer seatCount) {
		this.seatCount = seatCount;
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
	 * @return the zoneFlag
	 */
	public String getZoneFlag() {
		return zoneFlag;
	}

	/**
	 * @param zoneFlag
	 *            the zoneFlag to set
	 */
	public void setZoneFlag(String zoneFlag) {
		this.zoneFlag = zoneFlag;
	}

}
