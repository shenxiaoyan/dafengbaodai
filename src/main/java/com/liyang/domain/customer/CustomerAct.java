package com.liyang.domain.customer;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowAct;


/**
 * 对用户操作
 * @author Administrator
 *
 */
@Entity
@Table(name="customer_act")
public class CustomerAct  extends AbstractWorkflowAct<CustomerState,CustomerWorkflow>{
	
	public CustomerAct(String label, String actCode, Integer sort, ActGroup actGroup) {
		super(label, actCode, sort, actGroup);
		// TODO Auto-generated constructor stub
	}
	
	public CustomerAct(){
		super();
	}
}
