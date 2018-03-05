package com.liyang.client.tianan.dto;

import com.liyang.client.IDto;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * 险别信息
 * 
 * @author Administrator
 *
 */
public class ItemKindDTO implements IDto {
	private String kindCode;

	/**
	 * 险别代码
	 * 
	 * @return the kindCode
	 */
	public String getKindCode() {
		return kindCode;
	}

	/**
	 * 险别代码
	 * 
	 * @param kindCode
	 *            the kindCode to set
	 */
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	private String kindName;

	/**
	 * 险别名称
	 * 
	 * @return the kindName
	 */
	public String getKindName() {
		return kindName;
	}

	/**
	 * 险别名称
	 * 
	 * @param kindName
	 *            the kindName to set
	 */
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	private String serialNo;

	/**
	 * 险别序号
	 * 
	 * @return the serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * 险别序号
	 * 
	 * @param serialNo
	 *            the serialNo to set
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	private String itemCode;

	/**
	 * 标的项目类别代码
	 * 
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * 标的项目类别代码
	 * 
	 * @param itemCode
	 *            the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	private String unitAmount;

	/**
	 * 单位保额
	 * 
	 * @return the unitAmount
	 */
	public String getUnitAmount() {
		return unitAmount;
	}

	/**
	 * 单位保额
	 * 
	 * @param unitAmount
	 *            the unitAmount to set
	 */
	public void setUnitAmount(String unitAmount) {
		this.unitAmount = unitAmount;
	}

	private Integer quantity;

	/**
	 * 数量
	 * 
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * 数量
	 * 
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	private Double amount;
	

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	private String valueType;

	/**
	 * 玻璃类型
	 * 
	 * @return the valueType
	 */
	public String getValueType() {
		return valueType;
	}

	/**
	 * 玻璃类型
	 * 
	 * @param valueType
	 *            the valueType to set
	 */
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	private String kindInd;

	/**
	 * 主附险标志
	 * 
	 * @return the kindInd
	 */
	public String getKindInd() {
		return kindInd;
	}

	/**
	 * 主附险标志
	 * 
	 * @param kindInd
	 *            the kindInd to set
	 */
	public void setKindInd(String kindInd) {
		this.kindInd = kindInd;
	}

	private String deductableFlag;

	/**
	 * 是否投保不计免赔
	 * 
	 * @return the deductableFlag
	 */
	public String getDeductableFlag() {
		return deductableFlag;
	}

	/**
	 * 是否投保不计免赔
	 * 
	 * @param deductableFlag
	 *            the deductableFlag to set
	 */
	public void setDeductableFlag(String deductableFlag) {
		this.deductableFlag = deductableFlag;
	}

	private Double benchMarkPremium;

	/**
	 * 标准保费
	 * 
	 * @return the benchMarkPremium
	 */
	public Double getBenchMarkPremium() {
		return benchMarkPremium;
	}

	/**
	 * 标准保费
	 * 
	 * @param benchMarkPremium
	 *            the benchMarkPremium to set
	 */
	public void setBenchMarkPremium(Double benchMarkPremium) {
		this.benchMarkPremium = benchMarkPremium;
	}

	private Float discount;

	/**
	 * 折扣系数
	 * 
	 * @return the discount
	 */
	public Float getDiscount() {
		return discount;
	}

	/**
	 * 折扣系数
	 * 
	 * @param discount
	 *            the discount to set
	 */
	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	private Float discountPremium;

	/**
	 * 折扣保费
	 * 
	 * @return the discountPremium
	 */
	public Float getDiscountPremium() {
		return discountPremium;
	}

	/**
	 * 折扣保费
	 * 
	 * @param discountPremium
	 *            the discountPremium to set
	 */
	public void setDiscountPremium(Float discountPremium) {
		this.discountPremium = discountPremium;
	}

	private Double premium;

	/**
	 * 实缴保费
	 * 
	 * @return the premium
	 */
	public Double getPremium() {
		return premium;
	}

	/**
	 * 实缴保费
	 * 
	 * @param premium
	 *            the premium to set
	 */
	public void setPremium(Double premium) {
		this.premium = premium;
	}

	private Double basePremium;

	/**
	 * 基础保费
	 * 
	 * @return the basePremium
	 */
	public Double getBasePremium() {
		return basePremium;
	}

	/**
	 * 基础保费
	 * 
	 * @param basePremium
	 *            the basePremium to set
	 */
	public void setBasePremium(Double basePremium) {
		this.basePremium = basePremium;
	}

	private Float rate;

	/**
	 * 费率
	 * 
	 * @return the rate
	 */
	public Float getRate() {
		return rate;
	}

	/**
	 * 费率
	 * 
	 * @param rate
	 *            the rate to set
	 */
	public void setRate(Float rate) {
		this.rate = rate;
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {

	}
}
