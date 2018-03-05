package com.liyang.domain.api.tianan;

import java.util.Date;

/**
 * 接受移动端传递过来的参数
 * 
 * @author huanghengkun
 * @create 2017年12月9日
 */
public class CarModelApiParmas extends BaseApiParmas implements IApiParams {
	//城市代碼
	private String cityCode;
	//车辆型号
	private String brandName;
	//发动机号
	private String enginNo;
	//初次登记日期
	private Date enrollDate;
	//起保日期
	private Date startDate;
	//车辆识别代码
	private String frameNo;
	//车牌号码
	private String licenseNo;
	//分页用，不处理，保留
	private Integer page;
	//分页用，不处理，保留
	private Integer rows;
	//车辆购置日期,可选
	private Date purchaseDate;

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getEnginNo() {
		return enginNo;
	}

	public void setEnginNo(String enginNo) {
		this.enginNo = enginNo;
	}

	public Date getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getFrameNo() {
		return frameNo;
	}

	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

}
