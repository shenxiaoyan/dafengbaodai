//package com.liyang.domain.underwritingresult;
//
//import java.io.Serializable;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
//import org.springframework.data.annotation.TypeAlias;
//
//@Entity
//@Table(name="underwriting_result_underwriting_json")
//public class UnderwritingJson implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	private Integer id;
//	
//	@Column(name="error_msg")
//	private String errorMsg;    //核保失败的原因
//	
//	@OneToOne(cascade=CascadeType.ALL,targetEntity=BiNo.class)
//	@JoinColumn(name="underwriting_result_bi_no_id")
//	private BiNo biNo;   //商业险保单名和值
//	
//	@OneToOne(cascade=CascadeType.ALL,targetEntity=CiNo.class)
//	@JoinColumn(name="underwriting_result_ci_no_id")
//	private CiNo ciNo;    //交强险保单名和值
//	
//	@OneToOne(cascade=CascadeType.ALL,targetEntity=Circpaymentno.class)
//	@JoinColumn(name="underwriting_result_circpaymentno_id")
//	private Circpaymentno circpaymentno;  //平台交易名和值
//	
//	@OneToOne(cascade=CascadeType.ALL,targetEntity=PayJson.class)
//	@JoinColumn(name="underwriting_result_pay_json_id")
//	private PayJson payJson;     //支付信息
//
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	public String getErrorMsg() {
//		return errorMsg;
//	}
//
//	public void setErrorMsg(String errorMsg) {
//		this.errorMsg = errorMsg;
//	}
//
//	public BiNo getBiNo() {
//		return biNo;
//	}
//
//	public void setBiNo(BiNo biNo) {
//		this.biNo = biNo;
//	}
//
//	public CiNo getCiNo() {
//		return ciNo;
//	}
//
//	public void setCiNo(CiNo ciNo) {
//		this.ciNo = ciNo;
//	}
//
//	public Circpaymentno getCircpaymentno() {
//		return circpaymentno;
//	}
//
//	public void setCircpaymentno(Circpaymentno circpaymentno) {
//		this.circpaymentno = circpaymentno;
//	}
//
//	public PayJson getPayJson() {
//		return payJson;
//	}
//
//	public void setPayJson(PayJson payJson) {
//		this.payJson = payJson;
//	}
//}
