package com.liyang.domain.offerresult;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * 备选车型
 * @author Administrator
 *
 */
@Entity
@Table(name="offer_result_enquiry_car_models")
public class OfferResDatResEnquiryCarModels implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idd;
	private Integer companyId ; 
	private String offerId ;
	@Column(name="nouseid")
	private Integer id ;
	private String vehicleId;
	private String content; 
	
	
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonIgnore
	@ManyToMany(cascade=CascadeType.ALL,mappedBy="enquiryCarModels")
	private Set<OfferResultDataResult> results;

	public Integer getIdd() {
		return idd;
	}

	public void setIdd(Integer idd) {
		this.idd = idd;
	}
	
	public Set<OfferResultDataResult> getResults() {
		return results;
	}

	public void setResults(Set<OfferResultDataResult> results) {
		this.results = results;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
