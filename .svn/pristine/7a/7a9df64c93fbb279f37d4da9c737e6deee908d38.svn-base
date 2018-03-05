package com.liyang.domain.productcompany;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.service.AbstractAuditorService;
import com.liyang.util.FailReturnObject;

/**
 * @author Adam
 * @version 创建时间：2018年1月30日 下午3:46:24 类说明：产品公司
 */
@Entity
@Table(name = "product_company", uniqueConstraints = { @UniqueConstraint(columnNames = "label") })
public class ProductCompany extends AbstractAuditorEntity<ProductCompanyState, ProductCompanyAct, ProductCompanyLog> {

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
	public AbstractAuditorService<?, ProductCompanyState, ProductCompanyAct, ProductCompanyLog> getService() {
		return service;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setService(AbstractAuditorService service) {
		ProductCompany.service = service;
	}

	@Override
	@Transient
	@JsonIgnore
	public ProductCompanyLog getLogInstance() {
		return new ProductCompanyLog();
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
		ProductCompany.logRepository = logRepository;
	}

	@Info(label = "名称拼音")
	@Column(unique = true)
	private String pinYin;

	@Info(label = "名称缩写")
	@Column(unique = true)
	private String code;

	@Info(label = "列表icon")
	private String listIcon;

	@Info(label = "详情icon")
	private String detailIcon;

	public String getPinYin() {
		return pinYin;
	}

	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getListIcon() {
		return listIcon;
	}

	public void setListIcon(String listIcon) {
		this.listIcon = listIcon;
	}

	public String getDetailIcon() {
		return detailIcon;
	}

	public void setDetailIcon(String detailIcon) {
		this.detailIcon = detailIcon;
	}

	public void validate() {
		if (StringUtils.isEmpty(getLabel()) || StringUtils.isEmpty(getCode())) {
			throw new FailReturnObject(ExceptionResultEnum.PRODUCT_COMPANY_LABEL_OR_CODE_ENPTY);
		}
	}

}
