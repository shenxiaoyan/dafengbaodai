package com.liyang.domain.customer;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowState;

/**
 * 用户状态，启用，禁用
 * @author Administrator
 *
 */
@Entity
@Table(name = "customer_state")
@Cacheable
public class CustomerState extends AbstractWorkflowState<Customer, CustomerWorkflow, CustomerAct>{
	public CustomerState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public CustomerState() {
		super();
	}
	
	public CustomerState(int id , String label , String stateCode){
		this.setId(id);
		this.setLabel(label);
		this.setStateCode(stateCode);
	}
}
