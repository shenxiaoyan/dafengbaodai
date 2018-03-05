package com.liyang.domain.carModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author huanghengkun
 * @create 2017-11-26-下午3:38
 */
@Entity
@Table(name = "car_model_Result")
public class CarModelResult {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "result_code")
	private String resultCode;
	@Column(name = "result_mess")
	private String resultMess;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMess() {
		return resultMess;
	}

	public void setResultMess(String resultMess) {
		this.resultMess = resultMess;
	}
}
