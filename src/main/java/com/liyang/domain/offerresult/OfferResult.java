package com.liyang.domain.offerresult;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.liyang.annotation.Info;
import com.liyang.domain.createenquiry.CreateEnquiry;
import com.liyang.domain.platform.Platform;



/**
 * 报价结果
 * @author Administrator
 *
 */
@Entity
@Table(name="offer_result")
public class OfferResult implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne(cascade=CascadeType.ALL,targetEntity=OfferResultErrorMsg.class)
	@JoinColumn(name="offer_result_error_msg_id")
	@Info(label="报价结果错误正确记录",placeholder="",tip="",help="",secret="")
	private OfferResultErrorMsg  errorMsg;   
	
	@OneToOne(cascade=CascadeType.ALL,targetEntity=OfferResultData.class)
	@JoinColumn(name="offer_result_data_id")
	@Info(label="报价结果数据",placeholder="",tip="",help="",secret="")
	private OfferResultData data;    

	private Boolean successful;
	
	private Long time;
	//关联平台信息
	@OneToOne(cascade=CascadeType.ALL,targetEntity=Platform.class)
	@JoinColumn(name="platform_id")
	private Platform platform;    
	//报价结果关联到讯价记录
	@ManyToOne(cascade=CascadeType.ALL,targetEntity=CreateEnquiry.class)
	@JoinColumn(name="create_enquiry_id")
	private CreateEnquiry createEnquiry;   
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public OfferResultErrorMsg getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(OfferResultErrorMsg errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public OfferResultData getData() {
		return data;
	}

	public void setData(OfferResultData data) {
		this.data = data;
	}

	public Boolean getSuccessful() {
		return successful;
	}

	public void setSuccessful(Boolean successful) {
		this.successful = successful;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CreateEnquiry getCreateEnquiry() {
		return createEnquiry;
	}

	public void setCreateEnquiry(CreateEnquiry createEnquiry) {
		this.createEnquiry = createEnquiry;
	}

	
}
