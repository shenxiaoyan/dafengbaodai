//package com.liyang.domain.underwritingresult;
//
//import java.io.Serializable;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
//@Entity
//@Table(name="underwriting_result_pay_json")
//public class PayJson implements Serializable {
//	
//	private static final long serialVersionUID = 1L;
//	
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	private Integer id;
//	
//	@OneToOne(cascade=CascadeType.ALL,targetEntity=PayQrcode.class)
//	@JoinColumn(name="underwriting_result_pay_qrcode_id")
//	private PayQrcode payQrcode;   //支付码信息
//
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	public PayQrcode getPayQrcode() {
//		return payQrcode;
//	}
//
//	public void setPayQrcode(PayQrcode payQrcode) {
//		this.payQrcode = payQrcode;
//	}
//
//	public static long getSerialversionuid() {
//		return serialVersionUID;
//	}
//}
