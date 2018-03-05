package com.liyang.domain.temporaryresult;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.liyang.domain.platform.Platform;

/**
 * 暂存结果
 * @author Administrator
 *
 */
@Entity
@Table(name="temporary_result")
public class TemporaryResult implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne(cascade=CascadeType.ALL,targetEntity=ErrorMsg.class)
	@JoinColumn(name="temporary_result_error_msg")
	private ErrorMsg errorMsg;
	
	@OneToOne(cascade=CascadeType.ALL,targetEntity=Data.class)
	@JoinColumn(name="temporary_result_data_id")
	private Data data;
	
	private Boolean successful;
	
	@OneToOne(cascade=CascadeType.ALL,targetEntity=Platform.class)
	@JoinColumn(name="platform_id")
	private Platform platform;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ErrorMsg getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(ErrorMsg errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
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
}
