package com.liyang.domain.feedback;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.liyang.domain.customer.Customer;

/**
 * 意见反馈实体类
 * 
 * @author huanghengkun
 * @create 2017年10月18日
 */
@Entity
@Table(name = "feedback", indexes = { @Index(name = "feedback_submit_time", columnList = "submit_time") })
public class Feedback {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	// 提交时间
	@CreatedDate
	@Column(name = "submit_time")
	private Date submitTime;
	// 提交用户
	@ManyToOne()
	@JoinColumn(name = "submit_customer_id")
	private Customer submitCustomer;
	// 提交图片
	@Column(name = "img_url", columnDefinition="text")
	private String imgURL;
	// 提交内容
	@Column(name = "content")
	private String content;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public Customer getSubmitCustomer() {
		return submitCustomer;
	}

	public void setSubmitCustomer(Customer submitCustomer) {
		this.submitCustomer = submitCustomer;
	}

	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
