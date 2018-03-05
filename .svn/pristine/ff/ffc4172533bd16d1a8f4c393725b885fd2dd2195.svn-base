package com.liyang.domain.submitproposal;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

/**
 * 订单图像信息
 * @author Administrator
 *
 */
@Entity
@Table(name="submit_proposal_images_info")
public class ImagesInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String path;
	//小马接口为desc,数据库的专用字段，前端获取到数据需进行转换
	private String description;   
	
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="submit_proposal_licenseImage_id")
	private SubProParImaJsoLicenseImage licenseImage;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="submit_proposal_idCardImage_id")
	private SubProParImaJsoIdCardImage idCardImage;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="submit_proposal_toubaoImage_id")
	private SubProParImaJsoToubaoImage toubaoImage;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="submit_proposal_toubaoPersonImage_id")
	private SubProParImaJsoToubaoPersonImage toubaoPersonImage;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="submit_proposal_beitoubao_id")
	private SubProParImaJsoBeiToubaoPersonImage beitoubaoPersonImage;
	
	
	
	public SubProParImaJsoToubaoImage getToubaoImage() {
		return toubaoImage;
	}

	public void setToubaoImage(SubProParImaJsoToubaoImage toubaoImage) {
		this.toubaoImage = toubaoImage;
	}

	public SubProParImaJsoToubaoPersonImage getToubaoPersonImage() {
		return toubaoPersonImage;
	}

	public void setToubaoPersonImage(SubProParImaJsoToubaoPersonImage toubaoPersonImage) {
		this.toubaoPersonImage = toubaoPersonImage;
	}

	public SubProParImaJsoBeiToubaoPersonImage getBeitoubaoPersonImage() {
		return beitoubaoPersonImage;
	}

	public void setBeitoubaoPersonImage(SubProParImaJsoBeiToubaoPersonImage beitoubaoPersonImage) {
		this.beitoubaoPersonImage = beitoubaoPersonImage;
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

	public void setDesc(String desc){
		this.description=desc;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

		
}
