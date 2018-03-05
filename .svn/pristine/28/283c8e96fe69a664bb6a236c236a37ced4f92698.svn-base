package com.liyang.domain.claimmatch;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.domain.user.User;

/**
 *
 * @author Administrator
 *
 */
@Entity
@Table(name="claim_match")
public class ClaimMatch{
//public class ClaimMatch extends AbstractAuditorEntity<ClaimMatchState, ClaimMatchAct, ClaimMatchLog>{
//	@Transient
//	private static LogRepository logRepository;
//	@Transient
//	private static AbstractAuditorService service;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "created_by")
	private User createdBy;
	
	private String insuranceCompany;
	private Date signDate;
	private String carLicense;
	private String insuredPerson;
	private Double commercialFee = 0.0;
	private Double compulsoryFee = 0.0;
	private Double vehicleFee = 0.0;
	private String departmentName;
	private String salesmanName;
	private Double commercialCommission = 0.0;
	private Double compulsoryCommission = 0.0;
	private Double vehicleCommission = 0.0;
	//未匹配成功原因
	private String msg;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	public String getInsuranceCompany() {
		return insuranceCompany;
	}
	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public String getCarLicense() {
		return carLicense;
	}
	public void setCarLicense(String carLicense) {
		this.carLicense = carLicense;
	}
	public String getInsuredPerson() {
		return insuredPerson;
	}
	public void setInsuredPerson(String insuredPerson) {
		this.insuredPerson = insuredPerson;
	}
	public Double getCommercialFee() {
		return commercialFee;
	}
	public void setCommercialFee(Double commercialFee) {
		this.commercialFee = commercialFee;
	}
	public Double getCompulsoryFee() {
		return compulsoryFee;
	}
	public void setCompulsoryFee(Double compulsoryFee) {
		this.compulsoryFee = compulsoryFee;
	}
	public Double getVehicleFee() {
		return vehicleFee;
	}
	public void setVehicleFee(Double vehicleFee) {
		this.vehicleFee = vehicleFee;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getSalesmanName() {
		return salesmanName;
	}
	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}
	public Double getCommercialCommission() {
		return commercialCommission;
	}
	public void setCommercialCommission(Double commercialCommission) {
		this.commercialCommission = commercialCommission;
	}
	public Double getCompulsoryCommission() {
		return compulsoryCommission;
	}
	public void setCompulsoryCommission(Double compulsoryCommission) {
		this.compulsoryCommission = compulsoryCommission;
	}
	public Double getVehicleCommission() {
		return vehicleCommission;
	}
	public void setVehicleCommission(Double vehicleCommission) {
		this.vehicleCommission = vehicleCommission;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (! (obj instanceof ClaimMatch)) {
			return false;
		}
		if (getInsuranceCompany()!= null && getCarLicense()!= null) {
			return (getInsuranceCompany()+getCarLicense()).equals(((ClaimMatch)obj).getInsuranceCompany()+((ClaimMatch)obj).getCarLicense());
		}else {
			return false;
		}
	}
	

//	@Override
//	@JsonIgnore
//	public AbstractAuditorService<?, ClaimMatchState, ClaimMatchAct, ClaimMatchLog> getService() {
//		return service;
//	}
//
//	@Override
//	public void setService(AbstractAuditorService service) {
//		this.service = service;
//	}
//
//	@Override
//	public ClaimMatchLog getLogInstance() {
//		return new ClaimMatchLog();
//	}
//
//	@Override
//	public LogRepository getLogRepository() {
//		return logRepository;
//	}
//
//	@Override
//	public void setLogRepository(LogRepository logRepository) {
//		this.logRepository = logRepository;
//	}
	
	
	
	
	
	
	
	


}
