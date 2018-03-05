package com.liyang.domain.poster;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.banner.Banner;
import com.liyang.domain.banner.BannerAct;
import com.liyang.domain.banner.BannerLog;
import com.liyang.domain.banner.BannerState;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.image.Images;
import com.liyang.enums.ThemeType;
import com.liyang.service.AbstractAuditorService;

@Entity
@Table(name = "poster")
public class Poster extends AbstractAuditorEntity<PosterState, PosterAct, PosterLog>{

	private static final long serialVersionUID = 1L;
	@Transient
	private static LogRepository logRepository;

	@Transient
	private static AbstractAuditorService service;

	@Column(name = "name")
	@Info(label = "海报名称")
	private String name;
	@Column(name = "type")
	@Info(label = "图片主题")
	@Enumerated(EnumType.STRING)
	private ThemeType type;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "imagesId")
	private Images images;

	public Images getImages() {
		return images;
	}

	public void setImages(Images images) {
		this.images = images;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ThemeType getType() {
		return type;
	}

	public void setType(ThemeType type) {
		this.type = type;
	}






	@Override
	@JsonIgnore
	@Transient
	public AbstractAuditorService<?, PosterState, PosterAct, PosterLog> getService() {
		return service;
	}

	@Override
	public void setService(AbstractAuditorService service) {
		Poster.service = service;
	}

	@Override
	@JsonIgnore
	@Transient
	public PosterLog getLogInstance() {
		return new PosterLog();
	}

	@Override
	@JsonIgnore
	@Transient
	public LogRepository getLogRepository() {
		return logRepository;
	}

	@Override
	public void setLogRepository(LogRepository logRepository) {
		Poster.logRepository = logRepository;
	}
}
