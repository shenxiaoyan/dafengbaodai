package com.liyang.client.tianan.dto;

import org.springframework.util.StringUtils;

import com.liyang.client.IDto;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * 险别信息
 * 
 * @author Administrator
 *
 */
public class ItemKindDataDto implements IDto {
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

	private Long quantity;

	/**
	 * 数量
	 * 
	 * @return the quantity
	 */
	public Long getQuantity() {
		return quantity;
	}

	/**
	 * 数量
	 * 
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	private Double unitAmount;

	/**
	 * 单位保额
	 * 
	 * @return the unitAmount
	 */
	public Double getUnitAmount() {
		return unitAmount;
	}

	/**
	 * 单位保额
	 * 
	 * @param unitAmount
	 *            the unitAmount to set
	 */
	public void setUnitAmount(Double unitAmount) {
		this.unitAmount = unitAmount;
	}

	private Double amount;

	/**
	 * 保额
	 * 
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * 保额
	 * 
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
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

	private Double deductibleRate;

	/**
	 * 费率
	 * 
	 * @return the deductibleRate
	 */
	public Double getDeductibleRate() {
		return deductibleRate;
	}

	/**
	 * 费率
	 * 
	 * @param deductibleRate
	 *            the deductibleRate to set
	 */
	public void setDeductibleRate(Double deductibleRate) {
		this.deductibleRate = deductibleRate;
	}

	private String valueType;

	/**
	 * 险别附加类型
	 * 
	 * @return the valueType
	 */
	public String getValueType() {
		return valueType;
	}

	/**
	 * 险别附加类型
	 * 
	 * @param valueType
	 *            the valueType to set
	 */
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	// 险别序号
	private String serialNo;
	// 标的项目类别代码
	private String itemCode;
	// 主附险标志
	private String kindInd;
	// 标准保费
	private Double benchMarkPremium;
	// 折扣系数
	private Float discount;
	// 折扣保费
	private Float discountPremium;
	// 实缴保费
	private Double premium;
	// 基础保费
	private Double basePremium;
	// 费率
	private Float rate;

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getKindInd() {
		return kindInd;
	}

	public void setKindInd(String kindInd) {
		this.kindInd = kindInd;
	}

	public Double getBenchMarkPremium() {
		return benchMarkPremium;
	}

	public void setBenchMarkPremium(Double benchMarkPremium) {
		this.benchMarkPremium = benchMarkPremium;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public Float getDiscountPremium() {
		return discountPremium;
	}

	public void setDiscountPremium(Float discountPremium) {
		this.discountPremium = discountPremium;
	}

	public Double getPremium() {
		return premium;
	}

	public void setPremium(Double premium) {
		this.premium = premium;
	}

	public Double getBasePremium() {
		return basePremium;
	}

	public void setBasePremium(Double basePremium) {
		this.basePremium = basePremium;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {
		if (StringUtils.isEmpty(kindName)) {
			throw new ExceptionTiananParamInvliad("险别名称不能为空");
		}
		if (StringUtils.isEmpty(kindCode)) {
			throw new ExceptionTiananParamInvliad(kindName + "险别代码不能为空");
		}
		// if (this.getAmount() == null) {
		// throw new ExceptionTiananParamInvliad("保额不能为空");
		// } else {
		// if (this.getAmount() != 1.00 || this.getAmount() != 2.00) {
		// throw new ExceptionTiananParamInvliad("保额车型不匹配");
		//
		// }
		// }
		if (StringUtils.isEmpty(deductableFlag)) {
			throw new ExceptionTiananParamInvliad("是否投保不计免赔不能为空");
		} else {
			if (!"1".equals(deductableFlag) && !"0".equals(deductableFlag)) {
				throw new ExceptionTiananParamInvliad("是否投保不计免赔判断错误");
			}
		}

	}
}
