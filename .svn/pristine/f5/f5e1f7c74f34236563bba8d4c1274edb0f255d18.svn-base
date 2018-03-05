package com.liyang.domain.product;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.Range;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.productcompany.ProductCompany;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.service.AbstractAuditorService;
import com.liyang.util.FailReturnObject;

/**
 * @author Adam
 * @version 创建时间：2018年1月30日 下午1:26:15 类说明：产品类
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "product_company_id", "label" }))
public class Product extends AbstractAuditorEntity<ProductState, ProductAct, ProductLog> {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	@Transient
	private static LogRepository logRepository;

	@SuppressWarnings("rawtypes")
	@Transient
	private static AbstractAuditorService service;

	@SuppressWarnings("unchecked")
	@Override
	@Transient
	@JsonIgnore
	public AbstractAuditorService<?, ProductState, ProductAct, ProductLog> getService() {
		return service;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setService(AbstractAuditorService service) {
		Product.service = service;
	}

	@Override
	@Transient
	@JsonIgnore
	public ProductLog getLogInstance() {
		return new ProductLog();
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transient
	@JsonIgnore
	public LogRepository getLogRepository() {
		return logRepository;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setLogRepository(LogRepository logRepository) {
		Product.logRepository = logRepository;
	}

	@Info(label = "产品种类", secret = "1：车险，2：非车险，3：寿险")
	@Range(min = 1, max = 3, message = "产品种类暂只支持1~3，1：车险，2：非车险，3：寿险")
	@Column(nullable = false)
	private Integer type;

	@Info(label = "内容描述")
	private String content;

	@Info(label = "产品图片(链接)")
	private String images;

	@Info(label = "链接")
	private String linkUrl;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_company_id", nullable = false)
	@Info(label = "产品公司")
	private ProductCompany productCompany;

	@Info(label = "车船税返佣比例")
	@Column(columnDefinition = "decimal(5,2)")
	@Range(min = 0, max = 100, message = "比例需在0~100之间")
	private Double vehicleCommissionRate = 0.0;

	@Info(label = "交强险返佣比例")
	@Column(columnDefinition = "decimal(5,2)")
	@Range(min = 0, max = 100, message = "比例需在0~100之间")
	private Double compulsoryCommissionRate = 0.0;

	@Info(label = "商业险返佣比例")
	@Column(columnDefinition = "decimal(5,2)")
	@Range(min = 0, max = 100, message = "比例需在0~100之间")
	private Double commercialCommissionRate = 0.0;

	@Info(label = "普通返佣比例")
	@Column(columnDefinition = "decimal(5,2)")
	@Range(min = 0, max = 100, message = "比例需在0~100之间")
	private Double commissionRate = 0.0;

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

	public ProductCompany getProductCompany() {
		return productCompany;
	}

	public void setProductCompany(ProductCompany productCompany) {
		this.productCompany = productCompany;
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

	public void validate() {
		if (StringUtils.isEmpty(getLabel())) {
			throw new FailReturnObject(ExceptionResultEnum.PRODUCT_LABEL_ENPTY);
		}
		if (StringUtils.isEmpty(type)) {
			throw new FailReturnObject(ExceptionResultEnum.PRODUCT_TYPE_ENPTY);
		}
		if (null == productCompany) {
			throw new FailReturnObject(ExceptionResultEnum.PRODUCT_PRODUCTCOMPANY_ENPTY);
		}
	}

}
