package com.liyang.domain.submitproposal;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractWorkflowFile;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.platform.Platform;

/**
 * 投保订单
 * @author Administrator
 *
 */
@Entity
@Table(name="submit_proposal_file")
@Cacheable
public class SubmitProposalFile extends AbstractWorkflowFile<SubmitProposal, SubmitProposalAct, SubmitProposalLog>{

	
	private static final long serialVersionUID = 1L;

	@ManyToOne(cascade=CascadeType.ALL,targetEntity=Customer.class)
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	@ManyToOne(cascade=CascadeType.ALL,targetEntity=Platform.class)
	@JoinColumn(name="platform_id")
	private Platform platform;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Customer getCustomer() {
		return customer;
	}



	public void setCustomer(Customer customer) {
		this.customer = customer;
	}



	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}	
}
