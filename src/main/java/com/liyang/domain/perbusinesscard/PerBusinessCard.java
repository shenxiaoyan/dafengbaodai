package com.liyang.domain.perbusinesscard;



import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.image.Images;
import com.liyang.service.AbstractAuditorService;

@Entity
@Table(name = "per_buscard")

public class PerBusinessCard extends AbstractAuditorEntity<PerBusinessCardState,PerBusinessCardAct,PerBusinessCardLog>{
    @Transient
    private static final long serialVersionUID = 1L;
    @Transient
    private static LogRepository logRepository;
    @Transient
	private static AbstractAuditorService service;
	//头像
    @Info(label="头像")

    private String headImg;
    //真实姓名
    @Info(label="真实姓名")
    private  String realName;
    //行业(金融保险)
    @Info(label="行业(金融保险)")
    private  String industry;
    //公司介绍（杭州大蜂公司介绍）
    @Info(label="行业(金融保险)")
    private String companyDesc;
    //联系电话
    @Info(label="联系电话")
    private String phone;
    // 个人详情。个人详情（可编辑）包括：个人介绍、个人荣誉、个人风采（图片，4张）；
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="per_details_id")
    @Info(label="个人详情")
    private PerDetails details;

    //关联的客户信息，关联关系为一对一
    @OneToOne
    @JoinColumn(name="customer_id",unique = true)
    private Customer customer;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
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

    public PerDetails getDetails() {
        return details;
    }

    public void setDetails(PerDetails details) {
        this.details = details;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
	@JsonIgnore
	@Transient
	public AbstractAuditorService getService() {
		return service;
	}


	@Override
	public void setService(AbstractAuditorService service) {
		PerBusinessCard.service= service;
	}

	@Override
	@JsonIgnore
	@Transient
	public PerBusinessCardLog getLogInstance() {
		return new PerBusinessCardLog();
	}

	@Override
	@JsonIgnore
	@Transient
	public LogRepository getLogRepository() {
		// TODO Auto-generated method stub
		return logRepository;
	}
	@Override
	public void setLogRepository(LogRepository logRepository) {
		PerBusinessCard.logRepository=logRepository;
		
	}


	


   

}
