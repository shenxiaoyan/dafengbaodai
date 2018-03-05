package com.liyang.domain.journal;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.insuranceresult.InsuranceResult;
import com.liyang.domain.product.Product;
import com.liyang.enums.InsuranceType;
import com.liyang.service.AbstractAuditorService;

/**
 * @author Adam
 * @version 创建时间：2018年1月31日 下午2:21:37 类说明：customer账户流水记录
 */
@Entity
public class Journal extends AbstractAuditorEntity<JournalState, JournalAct, JournalLog> {

	private static final long serialVersionUID = 8917571540218573572L;

	@Transient
	private static AbstractAuditorService<Journal, JournalState, JournalAct, JournalLog> service;
	@Transient
	private static LogRepository<JournalLog> logRepository;

	@Override
	@JsonIgnore
	public AbstractAuditorService<Journal, JournalState, JournalAct, JournalLog> getService() {
		return service;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setService(AbstractAuditorService service) {
		Journal.service = service;
	}

	@Override
	@JsonIgnore
	public JournalLog getLogInstance() {
		return new JournalLog();
	}

	@Override
	@JsonIgnore
	public LogRepository<JournalLog> getLogRepository() {
		return logRepository;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLogRepository(LogRepository logRepository) {
		Journal.logRepository = logRepository;
	}

	@Info(label = "车船税返佣")
	@Column(columnDefinition = "decimal(10,2)")
	private Double vehicleCommission = 0.0;

	@Info(label = "车船税返佣比例")
	@Column(columnDefinition = "decimal(5,2)")
	private Double vehicleCommissionRate;

	@Info(label = "交强险返佣")
	@Column(columnDefinition = "decimal(10,2)")
	private Double compulsoryCommission = 0.0;

	@Info(label = "交强险返佣比例")
	@Column(columnDefinition = "decimal(5,2)")
	private Double compulsoryCommissionRate;

	@Info(label = "商业险返佣")
	@Column(columnDefinition = "decimal(10,2)")
	private Double commercialCommission = 0.0;

	@Info(label = "商业险返佣比例")
	@Column(columnDefinition = "decimal(5,2)")
	private Double commercialCommissionRate;

	@Info(label = "返佣总金额")
	@Column(columnDefinition = "decimal(10,2)")
	private Double commission;

	@Info(label = "返佣比例")
	@Column(columnDefinition = "decimal(5,2)")
	private Double commissionRate;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "customer_id", nullable = false)
	@Info(label = "所属用户")
	private Customer customer;

	@Enumerated(EnumType.STRING)
	@Info(label = "险种类型")
	private InsuranceType insuranceType;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "product_id", nullable = false)
	@Info(label = "保险产品", help = "含 险种名称、保险公司、返佣比例")
	private Product product;

	@Info(label = "订单号")
	@Column(nullable = false)
	private String orderId;

	@Info(label = "承保车牌")
	private String carLicense;

	@Info(label = "被保人")
	private String insuredName;

	@Info(label = "承保时间")
	private Date insureTime;

	@Info(label = "提现拒绝理由")
	private String rejectReason;

	@Info(label = "承保结果")
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "journal")
	private InsuranceResult insuranceResult;

	public Journal() {
		super();
	}

	// public Journal(Double vehicleCommission, Double compulsoryCommission, Double
	// commercialCommission,
	// Double commission, Customer customer, InsuranceType insuranceType, Product
	// product, String orderId,
	// String carLicense, String insuredName, Date insureTime) {
	// super();
	// this.vehicleCommission = vehicleCommission;
	// this.compulsoryCommission = compulsoryCommission;
	// this.commercialCommission = commercialCommission;
	// this.commission = commission;
	// this.customer = customer;
	// this.insuranceType = insuranceType;
	// this.product = product;
	// this.orderId = orderId;
	// this.carLicense = carLicense;
	// this.insuredName = insuredName;
	// this.insureTime = insureTime;
	// }
	public Journal(Double vehicleCommission, Double vehicleCommissionRate, Double compulsoryCommission,
			Double compulsoryCommissionRate, Double commercialCommission, Double commercialCommissionRate,
			Double commission, Double commissionRate, Customer customer, InsuranceType insuranceType, Product product,
			String orderId, String carLicense, String insuredName, Date insureTime, String rejectReason,
			InsuranceResult insruanceResult) {
		super();
		this.vehicleCommission = vehicleCommission;
		this.vehicleCommissionRate = vehicleCommissionRate;
		this.compulsoryCommission = compulsoryCommission;
		this.compulsoryCommissionRate = compulsoryCommissionRate;
		this.commercialCommission = commercialCommission;
		this.commercialCommissionRate = commercialCommissionRate;
		this.commission = commission;
		this.commissionRate = commissionRate;
		this.customer = customer;
		this.insuranceType = insuranceType;
		this.product = product;
		this.orderId = orderId;
		this.carLicense = carLicense;
		this.insuredName = insuredName;
		this.insureTime = insureTime;
		this.rejectReason = rejectReason;
		this.insuranceResult = insruanceResult;
	}

	public Double getVehicleCommission() {
		return vehicleCommission;
	}

	public void setVehicleCommission(Double vehicleCommission) {
		this.vehicleCommission = vehicleCommission;
	}

	public Double getCompulsoryCommission() {
		return compulsoryCommission;
	}

	public void setCompulsoryCommission(Double compulsoryCommission) {
		this.compulsoryCommission = compulsoryCommission;
	}

	public Double getCommercialCommission() {
		return commercialCommission;
	}

	public void setCommercialCommission(Double commercialCommission) {
		this.commercialCommission = commercialCommission;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public InsuranceType getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCarLicense() {
		return carLicense;
	}

	public void setCarLicense(String carLicense) {
		this.carLicense = carLicense;
	}

	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public Date getInsureTime() {
		return insureTime;
	}

	public void setInsureTime(Date insureTime) {
		this.insureTime = insureTime;
	}

	public Double getVehicleCommissionRate() {
		return vehicleCommissionRate;
	}

	public void setVehicleCommissionRate(Double vehicleCommissionRate) {
		this.vehicleCommissionRate = vehicleCommissionRate;
	}

	public Double getCompulsoryCommissionRate() {
		return compulsoryCommissionRate;
	}

	public void setCompulsoryCommissionRate(Double compulsoryCommissionRate) {
		this.compulsoryCommissionRate = compulsoryCommissionRate;
	}

	public Double getCommercialCommissionRate() {
		return commercialCommissionRate;
	}

	public void setCommercialCommissionRate(Double commercialCommissionRate) {
		this.commercialCommissionRate = commercialCommissionRate;
	}

	public Double getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(Double commissionRate) {
		this.commissionRate = commissionRate;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public InsuranceResult getInsuranceResult() {
		return insuranceResult;
	}

	public void setInsuranceResult(InsuranceResult insuranceResult) {
		this.insuranceResult = insuranceResult;
	}

}
