package com.liyang.domain.perbusinesscard;


import com.liyang.domain.image.Images;

public class PerBusinessCardBean {
    private Integer id;
    private Integer customerId;
    private Images headImg;//头像
    private String realName;//真实姓名
    private String industry;//行业
    private String companyDesc;//公司介绍
    private String phone;//联系电话
    private PerDetails perDetails;//个人详情

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Images getHeadImg() {
        return headImg;
    }

    public void setHeadImg(Images headImg) {
        this.headImg = headImg;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCompanyDesc() {
        return companyDesc;
    }

    public void setCompanyDesc(String companyDesc) {
        this.companyDesc = companyDesc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public PerDetails getPerDetails() {
        return perDetails;
    }

    public void setPerDetails(PerDetails perDetails) {
        this.perDetails = perDetails;
    }
}
