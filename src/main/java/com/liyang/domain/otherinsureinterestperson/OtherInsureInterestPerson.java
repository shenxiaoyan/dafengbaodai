package com.liyang.domain.otherinsureinterestperson;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.liyang.domain.customer.Customer;

/**
 * 人员保险
 * @author Administrator
 *
 */
@Entity
@Table(name="other_insure_interest_person")
public class OtherInsureInterestPerson {
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private int id ; 
	
	@JoinColumn(name="customer_id")
	@OneToOne
	private Customer customer;
	
	private String title ; 
	
	private Date clickTime ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getClickTime() {
		return clickTime;
	}

	public void setClickTime(Date clickTime) {
		this.clickTime = clickTime;
	}
	
}
