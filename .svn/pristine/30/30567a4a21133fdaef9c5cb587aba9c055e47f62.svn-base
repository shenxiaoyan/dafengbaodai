package com.liyang.domain.querylatestpolicy;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 查询续保结果推送 险种  商业险
 * @author Administrator
 *
 */
@Entity
@Table(name="query_latest_policy_insurances")
public class QueryLatestPolicyResultBInfoInsurances implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@JsonIgnore
	@ManyToMany(cascade=CascadeType.ALL,mappedBy="insurances")
	private Set<QueryLatestPolicyResultBiInfo>  biInfos;
	//险种编号
	@Column(name="insurance_id")
	private int insuranceId;  
	//险种中文名称
	@Column(name="insurance_name")
	private String 	insuranceName;  
	private int type;     
	//是否投保
	@Column(name="is_Toubao")
	private String isToubao;  
	@Column(name="is_hot")
	private Boolean isHot;    
	//是否不计免赔
	private Boolean compensation;  
	private int price;    
	//保费
	@Column(name="quotes_price")
	private Float quotesPrice;   
	@Column(name="english_name")
	private String englishName;  
	private int state;  
	@Column(name="ret_code")
	private int retCode;   
	private String options;   
	
	
	
//	@OneToOne(cascade=CascadeType.ALL,targetEntity=Remark.class)
//	@JoinColumn(name="query_latest_policy_remark_id")
//	@Column(nullable=true)
//	private Remark remark;   //评论
	//保额(车损险显示车损定价,其余显示保额)
	@Column(name="amount_str")
	private String amountStr;   
	@Column(name="is_supported")	
	private Boolean isSupported;  
	//威信来源
	@Column(name="weixin_source")
	private String weixinSource;   
	//?数据库无该字段,如若需要,在set中保存到实体类中
	@Transient
	private Object dependRuleJson;   
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Set<QueryLatestPolicyResultBiInfo> getBiInfos() {
		return biInfos;
	}
	public void setBiInfos(Set<QueryLatestPolicyResultBiInfo> biInfos) {
		this.biInfos = biInfos;
	}
	public int getInsuranceId() {
		return insuranceId;
	}
	public void setInsuranceId(int insuranceId) {
		this.insuranceId = insuranceId;
	}
	public String getInsuranceName() {
		return insuranceName;
	}
	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}
	public Float getQuotesPrice() {
		return quotesPrice;
	}
	public void setQuotesPrice(Float quotesPrice) {
		this.quotesPrice = quotesPrice;
	}
	public String getAmountStr() {
		return amountStr;
	}
	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getIsToubao() {
		return isToubao;
	}
	public void setIsToubao(String isToubao) {
		this.isToubao = isToubao;
	}
	public Boolean getIsHot() {
		return isHot;
	}
	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}
	public Boolean getCompensation() {
		return compensation;
	}
	public void setCompensation(Boolean compensation) {
		this.compensation = compensation;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
//	public Remark getRemark() {
//		return remark;
//	}
//	public void setRemark(Remark remark) {
//		this.remark = remark;
//	}
//	
	public Boolean getIsSupported() {
		return isSupported;
	}
	public void setIsSupported(Boolean isSupported) {
		this.isSupported = isSupported;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getRetCode() {
		return retCode;
	}
	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public String getWeixinSource() {
		return weixinSource;
	}
	public void setWeixinSource(String weixinSource) {
		this.weixinSource = weixinSource;
	}
	public Object getDependRuleJson() {
		return dependRuleJson;
	}
	public void setDependRuleJson(Object dependRuleJson) {
		
		this.dependRuleJson = dependRuleJson;
	}
	
	
}
