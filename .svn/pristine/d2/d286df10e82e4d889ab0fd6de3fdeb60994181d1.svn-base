package com.liyang.client.tianan.dto;

import com.liyang.client.IDto;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * 随车行李险销售信息
 * 
 * @author Administrator
 *
 */
public class ScxlSalesDto implements IDto {
	private Double scxlAmount;

	/**
	 * 联动随车行李险保额
	 * 
	 * @return the scxlAmount
	 */
	public Double getScxlAmount() {
		return scxlAmount;
	}

	/**
	 * 联动随车行李险保额
	 * 
	 * @param scxlAmount
	 *            the scxlAmount to set
	 */
	public void setScxlAmount(Double scxlAmount) {
		this.scxlAmount = scxlAmount;
	}

	private Double scxlPremium;

	/**
	 * 联动随车行李险保费
	 * 
	 * @return the scxlPremium
	 */
	public Double getScxlPremium() {
		return scxlPremium;
	}

	/**
	 * 联动随车行李险保费
	 * 
	 * @param scxlPremium
	 *            the scxlPremium to set
	 */
	public void setScxlPremium(Double scxlPremium) {
		this.scxlPremium = scxlPremium;
	}

	private Double scxlDeductible;

	/**
	 * 联动随车行李险免赔
	 * 
	 * @return the scxlDeductible
	 */
	public Double getScxlDeductible() {
		return scxlDeductible;
	}

	/**
	 * 联动随车行李险免赔
	 * 
	 * @param scxlDeductible
	 *            the scxlDeductible to set
	 */
	public void setScxlDeductible(Double scxlDeductible) {
		this.scxlDeductible = scxlDeductible;
	}

	private String scxlUwCount;

	/**
	 * 联动随车行李险份数
	 * 
	 * @return the scxlUwCount
	 */
	public String getScxlUwCount() {
		return scxlUwCount;
	}

	/**
	 * 联动随车行李险份数
	 * 
	 * @param scxlUwCount
	 *            the scxlUwCount to set
	 */
	public void setScxlUwCount(String scxlUwCount) {
		this.scxlUwCount = scxlUwCount;
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {
		if(this.scxlAmount == null){
			throw new ExceptionTiananParamInvliad("联动随车行李险保额不能为空");
		}
		if(this.scxlPremium == null){
			throw new ExceptionTiananParamInvliad("联动随车行李险保费不能为空");
		}
		if(this.scxlDeductible == null){
			throw new ExceptionTiananParamInvliad("联动随车行李险免赔不能为空");
		}
		if(this.scxlUwCount == null){
			throw new ExceptionTiananParamInvliad("联动随车行李险份数不能为空");
		}
		

	}
}
