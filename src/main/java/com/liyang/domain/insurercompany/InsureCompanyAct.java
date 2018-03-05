package com.liyang.domain.insurercompany;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorAct;
import com.liyang.domain.base.AbstractWorkflowAct;

/**
 * 保险公司
 * @author Administrator
 *
 */
@Entity
@Table(name="insure_company_act")
public class InsureCompanyAct  extends AbstractAuditorAct<InsureCompanyState>{
	
	public InsureCompanyAct(String label, String actCode, Integer sort, ActGroup actGroup) {
		super(label, actCode, sort, actGroup);
		// TODO Auto-generated constructor stub
	}
	
	public InsureCompanyAct(){
		super();
	}
}
