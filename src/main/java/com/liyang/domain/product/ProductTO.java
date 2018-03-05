package com.liyang.domain.product;

import java.util.Date;

/**
 * @author Adam
 * @version 创建时间：2018年2月1日 下午4:39:30 类说明 ：product transfer object
 */
public class ProductTO {

	private Integer id;

	private String label;

	private Integer type;

	private String content;

	private String images;

	private String linkUrl;

	private Integer productCompanyId;

	private String productCompanyLabel;

	private Double vehicleCommissionRate = 0.0;

	private Double compulsoryCommissionRate = 0.0;

	private Double commercialCommissionRate = 0.0;

	private Double commissionRate = 0.0;

	private String stateCode;

	private Date lastModifiedAt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Integer getProductCompanyId() {
		return productCompanyId;
	}

	public void setProductCompanyId(Integer productCompanyId) {
		this.productCompanyId = productCompanyId;
	}

	public Double getVehicleCommissionRate() {
		return vehicleCommissionRate;
	}

	public void setVehicleCommissionRate(Double vehicleCommissionRate) {
		this.vehicleCommissionRate = vehicleCommissionRate;
	}

	public Double getCompulsoryCommissionRate() {
		return compulsoryCommissionRate;
	}

	public void setCompulsoryCommissionRate(Double complysoryCommissionRate) {
		this.compulsoryCommissionRate = complysoryCommissionRate;
	}

	public Double getCommercialCommissionRate() {
		return commercialCommissionRate;
	}

	public void setCommercialCommissionRate(Double commercialCommissionRate) {
		this.commercialCommissionRate = commercialCommissionRate;
	}

	public Double getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(Double commissionRate) {
		this.commissionRate = commissionRate;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getProductCompanyLabel() {
		return productCompanyLabel;
	}

	public void setProductCompanyLabel(String productCompanyLabel) {
		this.productCompanyLabel = productCompanyLabel;
	}

	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

}
