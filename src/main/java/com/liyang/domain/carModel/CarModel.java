package com.liyang.domain.carModel;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 车型
 * @author Administrator
 *
 */
@Entity
@Table(name = "car_model")
public class CarModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	// 返回结果
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "car_model_result_id")
	private CarModelResult resultDTO;
	// 处理结果标志
	@Column(name = "deal_flag")
	private String dealFlag;
	// 处理结果信息
	@Column(name = "deal_message")
	private String dealMessage;
	// 交管车辆查询码
	@Column(name = "check_no")
	private String checkNo;
	// 校验码/图片字符串
	@Column(name = "check_code")
	private String checkCode;
	// 验证码标识
	@Column(name = "check_flag")
	private String checkFlag;
	
	@Column
	private Integer total;
	// 车型信息列表
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "carModel", targetEntity = CarModelVehicleModel.class)
	private Set<CarModelVehicleModel> vehicleModelList;
	// 交管车辆信息
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "tmb_vehicle_info_id")
	private CarModelTmbVehicleInfo tmbVehicleInfo;
	// 请求的报文
	@Lob
	@Column(name = "request_message")
	private String requestMessage;
	
	@Column(name = "trade_no")
	private String tradeNo;

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
	 * @return the resultDTO
	 */
	public CarModelResult getResultDTO() {
		return resultDTO;
	}

	/**
	 * @param resultDTO
	 *            the resultDTO to set
	 */
	public void setResultDTO(CarModelResult resultDTO) {
		this.resultDTO = resultDTO;
	}

	/**
	 * @return the dealFlag
	 */
	public String getDealFlag() {
		return dealFlag;
	}

	/**
	 * @param dealFlag
	 *            the dealFlag to set
	 */
	public void setDealFlag(String dealFlag) {
		this.dealFlag = dealFlag;
	}

	/**
	 * @return the dealMessage
	 */
	public String getDealMessage() {
		return dealMessage;
	}

	/**
	 * @param dealMessage
	 *            the dealMessage to set
	 */
	public void setDealMessage(String dealMessage) {
		this.dealMessage = dealMessage;
	}

	/**
	 * @return the checkNo
	 */
	public String getCheckNo() {
		return checkNo;
	}

	/**
	 * @param checkNo
	 *            the checkNo to set
	 */
	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	/**
	 * @return the checkCode
	 */
	public String getCheckCode() {
		return checkCode;
	}

	/**
	 * @param checkCode
	 *            the checkCode to set
	 */
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	/**
	 * @return the checkFlag
	 */
	public String getCheckFlag() {
		return checkFlag;
	}

	/**
	 * @param checkFlag
	 *            the checkFlag to set
	 */
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * @return the vehicleModelList
	 */
	public Set<CarModelVehicleModel> getVehicleModelList() {
		return vehicleModelList;
	}

	/**
	 * @param vehicleModelList
	 *            the vehicleModelList to set
	 */
	public void setVehicleModelList(Set<CarModelVehicleModel> vehicleModelList) {
		this.vehicleModelList = vehicleModelList;
	}

	/**
	 * @return the tmbVehicleInfo
	 */
	public CarModelTmbVehicleInfo getTmbVehicleInfo() {
		return tmbVehicleInfo;
	}

	/**
	 * @param tmbVehicleInfo
	 *            the tmbVehicleInfo to set
	 */
	public void setTmbVehicleInfo(CarModelTmbVehicleInfo tmbVehicleInfo) {
		this.tmbVehicleInfo = tmbVehicleInfo;
	}

	/**
	 * @return the requestMessage
	 */
	public String getRequestMessage() {
		return requestMessage;
	}

	/**
	 * @param requestMessage
	 *            the requestMessage to set
	 */
	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
	

}
