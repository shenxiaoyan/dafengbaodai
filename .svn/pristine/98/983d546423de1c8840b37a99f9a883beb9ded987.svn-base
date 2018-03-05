package com.liyang.domain.submitproposal;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;


/**
 * 证件信息
 * @author Administrator
 *
 */
@Entity
@Table(name="submit_proposal_image_json")
public class SubProParamsImageJson implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	//行驶证照片
	@OneToOne(targetEntity=SubProParImaJsoLicenseImage.class,cascade=CascadeType.ALL)
	@JoinColumn(name="submit_proposal_licenseImage_id")
	private SubProParImaJsoLicenseImage licenseImage;   
	//身份证照片
	@OneToOne(targetEntity=SubProParImaJsoIdCardImage.class,cascade=CascadeType.ALL)
	@JoinColumn(name="submit_proposal_idCardImage_id")
	private SubProParImaJsoIdCardImage idCardImage;      
	//投保单照片
	@OneToOne(targetEntity=SubProParImaJsoToubaoImage.class,cascade=CascadeType.ALL)
	@JoinColumn(name="submit_proposal_toubaoImage_id")
	private SubProParImaJsoToubaoImage toubaoImage;   
	//投保人照片
	@OneToOne(targetEntity=SubProParImaJsoToubaoPersonImage.class,cascade=CascadeType.ALL)
	@JoinColumn(name="submit_proposal_toubaoPersonImage_id")
	private SubProParImaJsoToubaoPersonImage toubaorenImage;   
	//被投保人照片
	@OneToOne(targetEntity=SubProParImaJsoBeiToubaoPersonImage.class,cascade=CascadeType.ALL)
	@JoinColumn(name="submit_proposal_beiToubaoPersonImage_id")
	private SubProParImaJsoBeiToubaoPersonImage insuredImage;   
	
	
	public SubProParImaJsoToubaoPersonImage getToubaorenImage() {
		return toubaorenImage;
	}

	public void setToubaorenImage(SubProParImaJsoToubaoPersonImage toubaorenImage) {
		this.toubaorenImage = toubaorenImage;
	}

	public SubProParImaJsoBeiToubaoPersonImage getInsuredImage() {
		return insuredImage;
	}

	public void setInsuredImage(SubProParImaJsoBeiToubaoPersonImage insuredImage) {
		this.insuredImage = insuredImage;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SubProParImaJsoLicenseImage getLicenseImage() {
		return licenseImage;
	}

	public void setLicenseImage(SubProParImaJsoLicenseImage licenseImage) {
		this.licenseImage = licenseImage;
	}

	public SubProParImaJsoIdCardImage getIdCardImage() {
		return idCardImage;
	}

	public void setIdCardImage(SubProParImaJsoIdCardImage idCardImage) {
		this.idCardImage = idCardImage;
	}

	public SubProParImaJsoToubaoImage getToubaoImage() {
		return toubaoImage;
	}

	public void setToubaoImage(SubProParImaJsoToubaoImage toubaoImage) {
		this.toubaoImage = toubaoImage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
