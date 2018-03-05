package com.liyang.domain.banner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.service.AbstractAuditorService;

/**
 * 上架权重
 * @author Administrator
 *
 */
@Entity
@Table(name = "banner")
public class Banner extends AbstractAuditorEntity<BannerState, BannerAct, BannerLog> {

	/**
	 * 权重1-6，数值越小显示时越靠前
	 */
	public final static int WEIGHT_MIN = 1;
	public final static int WEIGHT_MAX = 6;
	//最多上架数
	public final static int ONSHELF_MAX = 6;

	private static final long serialVersionUID = 1L;
	@Transient
	private static LogRepository logRepository;

	@Transient
	private static AbstractAuditorService service;

	@Column(name = "name")
	@Info(label = "图片名")
	private String name;

	@Column(name = "img_url")
	@Info(label = "图片地址")
	private String imgURL;
	// 上架显示的权重
	@Column(name = "weight")
	@Min(value = WEIGHT_MIN, message = "权重值只能在" + WEIGHT_MIN + "到" + WEIGHT_MAX + "之间")
	@Max(value = WEIGHT_MAX, message = "权重值只能在" + WEIGHT_MIN + "到" + WEIGHT_MAX + "之间")
	@Info(label = "权重")
	private Integer weight;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@Override
	@JsonIgnore
	@Transient
	public AbstractAuditorService<?, BannerState, BannerAct, BannerLog> getService() {
		return service;
	}

	@Override
	public void setService(AbstractAuditorService service) {
		Banner.service = service;
	}

	@Override
	@JsonIgnore
	@Transient
	public BannerLog getLogInstance() {
		return new BannerLog();
	}

	@Override
	@JsonIgnore
	@Transient
	public LogRepository getLogRepository() {
		return logRepository;
	}

	@Override
	public void setLogRepository(LogRepository logRepository) {
		Banner.logRepository = logRepository;
	}

}
