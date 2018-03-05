package com.liyang.domain.productcompany;

/** 
* @author Adam 
* @version 创建时间：2018年2月1日 上午10:11:09 
* 类说明 ：保险公司
*/
public class ProductCompanyTO {
	
	private Integer id;
	
	private String label;
	
	private String pinYin;

	private String code;

	private String listIcon;

	private String detailIcon;
	
	private String stateCode;

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

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	
	
	
}
