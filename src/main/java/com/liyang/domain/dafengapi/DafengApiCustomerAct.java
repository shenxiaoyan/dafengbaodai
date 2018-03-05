package com.liyang.domain.dafengapi;
/**  
* 类说明   
* @author lcj 
* @date 2017年9月18日  新建  
*/

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorAct;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.department.DepartmentState;

@Entity
@Table(name = "dafeng_api_customer_act")
public class DafengApiCustomerAct extends AbstractAuditorAct<DafengApiCustomerState> {
	
	public DafengApiCustomerAct(String label, String actCode, Integer sort, ActGroup actGroup) {
		super(label, actCode, sort, actGroup);
		// TODO Auto-generated constructor stub
	}
	public DafengApiCustomerAct(){
		super();
	}


}
