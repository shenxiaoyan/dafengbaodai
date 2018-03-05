package com.liyang.client.tianan;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.liyang.client.BaseResult;
import com.liyang.client.BaseResultDto;
import com.liyang.client.IResult;
import com.liyang.client.strategy.ICarModelInfo;
import com.liyang.client.tianan.dto.TmbVehicleInfo;
import com.liyang.client.tianan.dto.VehicleModelDTO;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * @author Administrator
 *
 */
public class ResultQueryCarModel extends BaseResult implements IResult, ICarModelInfo {
	private BaseResultDto resultDTO;

	/**
	 * 返回结果
	 * 
	 * @return the resultDTO
	 */
	public BaseResultDto getResultDTO() {
		return resultDTO;
	}

	/**
	 * 返回结果
	 * 
	 * @param resultDTO
	 *            the resultDTO to set
	 */
	public void setResultDTO(BaseResultDto resultDTO) {
		this.resultDTO = resultDTO;
	}

	private String dealFlag;

	/**
	 * 处理结果标志
	 * 
	 * @return the dealFlag
	 */
	public String getDealFlag() {
		return dealFlag;
	}

	/**
	 * 处理结果标志
	 * 
	 * @param dealFlag
	 *            the dealFlag to set
	 */
	public void setDealFlag(String dealFlag) {
		this.dealFlag = dealFlag;
	}

	private String dealMessage;

	/**
	 * 处理结果信息
	 * 
	 * @return the dealMessage
	 */
	public String getDealMessage() {
		return dealMessage;
	}

	/**
	 * 处理结果信息
	 * 
	 * @param dealMessage
	 *            the dealMessage to set
	 */
	public void setDealMessage(String dealMessage) {
		this.dealMessage = dealMessage;
	}

	private String checkNo;

	/**
	 * 交管车辆查询码
	 * 
	 * @return the checkNo
	 */
	public String getCheckNo() {
		return checkNo;
	}

	/**
	 * 交管车辆查询码
	 * 
	 * @param checkNo
	 *            the checkNo to set
	 */
	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	private String checkCode;

	/**
	 * 校验码
	 * 
	 * @return the checkCode
	 */
	public String getCheckCode() {
		return checkCode;
	}

	/**
	 * 校验码
	 * 
	 * @param checkCode
	 *            the checkCode to set
	 */
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	private String checkFlag;

	/**
	 * 验证码标识
	 * 
	 * @return the checkFlag
	 */
	public String getCheckFlag() {
		return checkFlag;
	}

	/**
	 * 验证码标识
	 * 
	 * @param checkFlag
	 *            the checkFlag to set
	 */
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	private List<VehicleModelDTO> vehicleModelList;

	/**
	 * 车型信息列表
	 * 
	 * @return the vehicleModelList
	 */
	public List<VehicleModelDTO> getVehicleModelList() {
		return vehicleModelList;
	}

	/**
	 * 车型信息列表
	 * 
	 * @param vehicleModelList
	 *            the vehicleModelList to set
	 */
	public void setVehicleModelList(List<VehicleModelDTO> vehicleModelList) {
		this.vehicleModelList = vehicleModelList;
	}

	private TmbVehicleInfo tmbVehicleInfo;

	/**
	 * 交管车辆信息
	 * 
	 * @return the tmbVehicleInfo
	 */
	public TmbVehicleInfo getTmbVehicleInfo() {
		return tmbVehicleInfo;
	}

	/**
	 * 交管车辆信息
	 * 
	 * @param tmbVehicleInfo
	 *            the tmbVehicleInfo to set
	 */
	public void setTmbVehicleInfo(TmbVehicleInfo tmbVehicleInfo) {
		this.tmbVehicleInfo = tmbVehicleInfo;
	}

	private Integer total;

	/**
	 * 总条数
	 * 
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * 总条数
	 * 
	 * @param total
	 *            the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {

	}

	@JSONField(serialize = false)
	public boolean isSuccess() {
		return resultDTO != null && resultDTO.isSuccess() && "1".equals(dealFlag);
	}

	@JSONField(serialize = false)
	public String getErrorMess() {
		String errorMess = "";
		// 首先确认没有成功
		if (!isSuccess()) { 
			// 优先返回resultDTO里的错误
			if (resultDTO != null && !resultDTO.isSuccess()) { 
				errorMess = resultDTO.getResultMess();
				// 再返回dealMessage的错误信息
			} else if (!"1".equals(dealFlag)) {
				errorMess = dealMessage;
			} else {
				// resultDTO为null
				errorMess = "系统异常";
			}
		}
		return errorMess;
	}
}
