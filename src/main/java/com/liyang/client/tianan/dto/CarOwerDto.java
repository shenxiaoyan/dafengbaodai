package com.liyang.client.tianan.dto;

import org.apache.commons.lang.StringUtils;

import com.liyang.client.IDto;
import com.liyang.client.tianan.enums.IdentifyTypeEnums;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * 车主信息
 * 
 * @author Administrator
 *
 */
public class CarOwerDto implements IDto {
	private final String carOwnerIdentifyType;

	/**
	 * 车主证件类型
	 * 
	 * @return the carOwnerIdentifyType
	 */
	public String getCarOwnerIdentifyType() {
		return carOwnerIdentifyType;
	}

	private final String carOwnerIdentifyNumber;

	/**
	 * 车主证件号码
	 * 
	 * @return the carOwnerIdentifyNumber
	 */
	public String getCarOwnerIdentifyNumber() {
		return carOwnerIdentifyNumber;
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {
		if (StringUtils.isEmpty(this.carOwnerIdentifyType)) {
			throw new ExceptionTiananParamInvliad("车主证件类型不能为空");
		} else {
			if(!IdentifyTypeEnums.isExist(this.carOwnerIdentifyType)){
				throw new ExceptionTiananParamInvliad("车主证件类型不匹配");
			}
		}
		if (StringUtils.isEmpty(this.carOwnerIdentifyNumber)) {
			throw new ExceptionTiananParamInvliad("车主证件号码不能为空");
		}

	}

	public CarOwerDto(String carOwnerIdentifyType, String carOwnerIdentifyNumber) throws ExceptionTiananParamInvliad {
		this.carOwnerIdentifyType = carOwnerIdentifyType;
		this.carOwnerIdentifyNumber = carOwnerIdentifyNumber;
		validate();
	}

}
