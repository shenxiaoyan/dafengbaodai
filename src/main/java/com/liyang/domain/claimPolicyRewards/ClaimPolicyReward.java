package com.liyang.domain.claimPolicyRewards;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.department.Department;
import com.liyang.domain.salesman.Salesman;
import com.liyang.service.AbstractAuditorService;
import com.liyang.util.CustomDoubleSerialize;

/**
 * 理赔
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "claim_policy_reward")
public class ClaimPolicyReward
		extends AbstractAuditorEntity<ClaimPolicyRewardState, ClaimPolicyRewardAct, ClaimPolicyRewardLog> {

	@Transient
	private static LogRepository logRepository;

	@Transient
	private static AbstractAuditorService service;
	@Column(name = "upload_time")
	@Info(label = "上传日期")
	private Date uploadTime;

	@Column(name = "insurace_company")
	@NotBlank(message = "保险公司名未填")
	@Info(label = "保险公司")
	private String insuraceCompany;

	@Column(name = "sign_date")
	@NotNull(message = "签单日期不能为空")
	@Past
	@Info(label = "签单日期")
	private Date signDate;

	@Column(name = "policy_number")
	@NotBlank(message = "保单号不能为空")
	@Info(label = "保单号")
	private String policyNumber;

	@Column(name = "insur_applicant")
	@Info(label = "投保人")
	private String insurApplicant;

	@Column(name = "insured_person")
	@Info(label = "被保人")
	private String insuredPerson;

	@Column(name = "car_owner_idcard")
	// @Pattern(regexp="(^\\d{18}$)|(^\\d{15}$)")
	@Info(label = "车主身份证")
	private String carOwnerIdCard;

	@Column(name = "car_license")
	@NotBlank(message = "车牌不能为空")
	// @Pattern(regexp="^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$")
	@Info(label = "车牌")
	private String carLicense;

	@Column(name = "insurace_ype")
	@NotBlank(message = "险种不能为空")
	@Info(label = "险种")
	private String insuraceType;

	@Column(name = "insurace_fee_include_tax")
	@JsonSerialize(using = CustomDoubleSerialize.class)
	@Info(label = "含税保费")
	private Double insuraceFeeIncludeTax;

	@Column(name = "insurace_fee_exclude_tax")
	@JsonSerialize(using = CustomDoubleSerialize.class)
	@Info(label = "不含税保费")
	private Double insuraceFeeExcludeTax;

	@Column(name = "broker_fee_rate")
	@Info(label = "手续费率")
	private Double brokerFeeRate;

	@Column(name = "received_broker_fee")
	@NotNull(message = "已付手续费不能为空")
	@JsonSerialize(using = CustomDoubleSerialize.class)
	@Info(label = "已收手续费", secret = "L")
	private Double receivedBrokerFee;

	@Column(name = "vehicle_tax_fee", columnDefinition = "decimal(10,2)")
	@JsonSerialize(using = CustomDoubleSerialize.class)
	@Info(label = "车船税")
	private Double vehicleTaxFee;

	@Column(name = "return_vehicle_tax_fee")
	@JsonSerialize(using = CustomDoubleSerialize.class)
	@Info(label = "车船税返还", secret = "N")
	private Double returnVehicleTaxFee;

	@Column(name = "return_vehicle_tax_fee_check_time")
	@Info(label = "车船税确认时间")
	private Date returnVehicleTaxFeeCheckTime;

	@Column(name = "return_vehicle_tax_fee_check_flag")
	@Type(type = "yes_no")
	@Info(label = "车船税返回确认标识")
	private Boolean returnVehicleTaxFeeCheckFlag = false;

	@Column(name = "additional_fee")
	@NotNull(message = "请填入具体附加费或0")
	@Info(label = "附加费用", secret = "O")
	private Double additionalFee;

	@Column(name = "additional_fee_check_time")
	@Info(label = "附加费用确认时间")
	private Date additionalFeeCheckTime;

	@Column(name = "additional_fee_check_flag")
	@Type(type = "yes_no")
	@Info(label = "附加费用确认标识")
	private Boolean additionalFeeCheckFlag = false;

	@Column(name = "vehicle_commission")
	@JsonSerialize(using = CustomDoubleSerialize.class)
	@Info(label = "车船税佣金", secret = "Q1")
	private Double vehicleCommission = 0.0;

	@Column(name = "insurance_commision")
	@JsonSerialize(using = CustomDoubleSerialize.class)
	@Info(label = "险种佣金", secret = "Q2")
	private Double insuranceCommission = 0.0;

	@Column(name = "management_fee")
	@JsonSerialize(using = CustomDoubleSerialize.class)
	@Info(label = "管理费用", secret = "Q1+Q2")
	private Double managementFee = 0.0;

	@ManyToOne
	@JoinColumn(name = "salesman_id")
	@Info(label = "保单归属营业员")
	private Salesman salesman;

	@ManyToOne
	@JoinColumn(name = "department_id")
	// @JsonIgnore
	@Info(label = "归属营业部门")
	private Department department;

	@Column(name = "claim_time")
	@Info(label = "认领时间")
	private Date claimTime;

	@Column(name = "claim_flag")
	@Type(type = "yes_no")
	@Info(label = "管理费用认领标识")
	private Boolean claimFlag = false;

	@Column(name = "claim_check_flag")
	@Type(type = "yes_no")
	@Info(label = "认领确认标识")
	private Boolean claimCheckFlag = false;

	@Column(name = "claim_check_time")
	@Info(label = "认领复合时间")
	private Date claimCheckTime;

	@Column(name = "profit")
	@JsonSerialize(using = CustomDoubleSerialize.class)
	@Info(label = "收益", secret = "L+N+O-Q")
	private Double profit = 0.0;

	public ClaimPolicyReward() {
	}

	
	//	添加构造方法
	public ClaimPolicyReward(Integer id, String carLicense,String policyNumber, String insurApplicant,String insuredPerson, String insuraceCompany, ClaimPolicyRewardState state,
			Boolean returnVehicleTaxFeeCheckFlag,Boolean claimFlag, Boolean additionalFeeCheckFlag,Date createdAt,Date lastModifiAt) {
		super();
		this.setId(id);
		this.setCarLicense(carLicense);
		this.setPolicyNumber(policyNumber);
		this.setInsurApplicant(insurApplicant);
		this.setInsuredPerson(insuredPerson);
		this.setInsuraceCompany(insuraceCompany);
		this.setReturnVehicleTaxFeeCheckFlag(returnVehicleTaxFeeCheckFlag);
		this.setClaimFlag(claimFlag);
		this.setAdditionalFeeCheckFlag(additionalFeeCheckFlag);
		this.setCreatedAt(createdAt);
		this.setLastModifiedAt(lastModifiAt);
		
	}



	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getInsuraceCompany() {
		return insuraceCompany;
	}

	public void setInsuraceCompany(String insuraceCompany) {
		this.insuraceCompany = insuraceCompany;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getSignDate() {
		return signDate;
	}


	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getInsurApplicant() {
		return insurApplicant;
	}

	public void setInsurApplicant(String insurApplicant) {
		this.insurApplicant = insurApplicant;
	}

	public String getInsuredPerson() {
		return insuredPerson;
	}

	public void setInsuredPerson(String insuredPerson) {
		this.insuredPerson = insuredPerson;
	}

	public String getCarOwnerIdCard() {
		return carOwnerIdCard;
	}

	public void setCarOwnerIdCard(String carOwnerIdCard) {
		this.carOwnerIdCard = carOwnerIdCard;
	}

	public String getCarLicense() {
		return carLicense;
	}

	public void setCarLicense(String carLicense) {
		this.carLicense = carLicense;
	}

	public String getInsuraceType() {
		return insuraceType;
	}

	public void setInsuraceType(String insuraceType) {
		this.insuraceType = insuraceType;
	}

	public Double getInsuraceFeeIncludeTax() {
		return insuraceFeeIncludeTax;
	}

	public void setInsuraceFeeIncludeTax(Double insuraceFeeIncludeTax) {
		this.insuraceFeeIncludeTax = insuraceFeeIncludeTax;
	}

	public Double getInsuraceFeeExcludeTax() {
		return insuraceFeeExcludeTax;
	}

	public void setInsuraceFeeExcludeTax(Double insuraceFeeExcludeTax) {
		this.insuraceFeeExcludeTax = insuraceFeeExcludeTax;
	}

	public Double getBrokerFeeRate() {
		return brokerFeeRate;
	}

	public void setBrokerFeeRate(Double brokerFeeRate) {
		this.brokerFeeRate = brokerFeeRate;
	}

	public Double getReceivedBrokerFee() {
		return receivedBrokerFee;
	}

	public void setReceivedBrokerFee(Double receivedBrokerFee) {
		this.receivedBrokerFee = receivedBrokerFee;
	}

	public Double getVehicleTaxFee() {
		return vehicleTaxFee;
	}

	public void setVehicleTaxFee(Double vehicleTaxFee) {
		this.vehicleTaxFee = vehicleTaxFee;
	}

	public Double getReturnVehicleTaxFee() {
		return returnVehicleTaxFee;
	}

	public void setReturnVehicleTaxFee(Double returnVehicleTaxFee) {
		this.returnVehicleTaxFee = returnVehicleTaxFee;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getReturnVehicleTaxFeeCheckTime() {
		return returnVehicleTaxFeeCheckTime;
	}

	public void setReturnVehicleTaxFeeCheckTime(Date returnVehicleTaxFeeCheckTime) {
		this.returnVehicleTaxFeeCheckTime = returnVehicleTaxFeeCheckTime;
	}

	public Boolean getReturnVehicleTaxFeeCheckFlag() {
		return returnVehicleTaxFeeCheckFlag;
	}

	public void setReturnVehicleTaxFeeCheckFlag(Boolean returnVehicleTaxFeeCheckFlag) {
		this.returnVehicleTaxFeeCheckFlag = returnVehicleTaxFeeCheckFlag;
	}

	public Double getAdditionalFee() {
		return additionalFee;
	}

	public void setAdditionalFee(Double additionalFee) {
		this.additionalFee = additionalFee;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getAdditionalFeeCheckTime() {
		return additionalFeeCheckTime;
	}

	public void setAdditionalFeeCheckTime(Date additionalFeeCheckTime) {
		this.additionalFeeCheckTime = additionalFeeCheckTime;
	}

	public Boolean getAdditionalFeeCheckFlag() {
		return additionalFeeCheckFlag;
	}

	public void setAdditionalFeeCheckFlag(Boolean additionalFeeCheckFlag) {
		this.additionalFeeCheckFlag = additionalFeeCheckFlag;
	}

	public Double getVehicleCommission() {
		return vehicleCommission;
	}

	public void setVehicleCommission(Double vehicleCommission) {
		this.vehicleCommission = vehicleCommission;
	}

	public Double getInsuranceCommission() {
		return insuranceCommission;
	}

	public void setInsuranceCommission(Double insuranceCommission) {
		this.insuranceCommission = insuranceCommission;
	}

	public Salesman getSalesman() {
		return salesman;
	}

	public void setSalesman(Salesman salesman) {
		this.salesman = salesman;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getClaimTime() {
		return claimTime;
	}

	public void setClaimTime(Date claimTime) {
		this.claimTime = claimTime;
	}

	public Boolean getClaimFlag() {
		return claimFlag;
	}

	public void setClaimFlag(Boolean claimFlag) {
		this.claimFlag = claimFlag;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public Double getManagementFee() {
		return managementFee;
	}

	public void setManagementFee(Double managementFee) {
		this.managementFee = managementFee;
	}

	public String getDepartmentLabel() {
		if (this.department != null) {
			return department.getLabel();
		} else {
			return null;
		}
	}

	public String getSalesmanName() {
		if (this.salesman != null) {
			return salesman.getName();
		} else {
			return null;
		}
	}

	public Integer getBelongDepartmentId() {
		if (this.department != null) {
			return department.getId();
		} else {
			return null;
		}
	}

	public Integer getSalesmanId() {
		if (this.salesman != null) {
			return salesman.getId();
		} else {
			return null;
		}
	}

	public Boolean getClaimCheckFlag() {
		return claimCheckFlag;
	}

	public void setClaimCheckFlag(Boolean claimCheckFlag) {
		this.claimCheckFlag = claimCheckFlag;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getClaimCheckTime() {
		return claimCheckTime;
	}

	public void setClaimCheckTime(Date claimCheckTime) {
		this.claimCheckTime = claimCheckTime;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ClaimPolicyReward)) {
			return false;
		}
		if (getPolicyNumber() != null && getReceivedBrokerFee() != null) {
			return (getPolicyNumber() + getReceivedBrokerFee()).equals(
					((ClaimPolicyReward) obj).getPolicyNumber() + ((ClaimPolicyReward) obj).getReceivedBrokerFee());
		} else {
			return false;
		}
	}

	@Override
	@JsonIgnore
	@Transient
	public AbstractAuditorService<?, ClaimPolicyRewardState, ClaimPolicyRewardAct, ClaimPolicyRewardLog> getService() {
		return service;
	}

	@Override
	public void setService(AbstractAuditorService service) {
		this.service = service;
	}

	@Override
	@Transient
	@JsonIgnore
	public ClaimPolicyRewardLog getLogInstance() {
		return new ClaimPolicyRewardLog();
	}

	@Override
	@Transient
	@JsonIgnore
	public LogRepository getLogRepository() {
		return logRepository;
	}

	@Override
	public void setLogRepository(LogRepository logRepository) {
		this.logRepository = logRepository;
	}

	public boolean validate() {
		return true;
	}

}