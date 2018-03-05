//package com.liyang.domain.insurerate;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import com.liyang.domain.department.Department;
//import com.liyang.domain.insurercompany.InsureCompany;
//import com.liyang.domain.salesman.Salesman;
//
///**
// * @author Adam
// * @version 创建时间：2018年1月30日 下午6:46:24 类说明：产品返佣率
// */
//public class InsureRate{
//
//
//
//	@ManyToOne
//	@JoinColumn(name = "insurecompany_id")
//	private InsureCompany insureCompany;
//	// 车船税佣金率
//	@Column(name = "vehicle_commission_rate")
//	private Double vehicleCommissionRate;
//	// 交强险佣金率
//	@Column(name = "complusory_commission_rate")
//	private Double complysoryCommissionRate;
//	// 商业险佣金率
//	@Column(name = "commercial_commission_rate")
//	private Double commercialCommissionRate;
//
//
//}
