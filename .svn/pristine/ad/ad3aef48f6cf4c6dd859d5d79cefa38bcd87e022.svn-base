package com.liyang.domain.insurercompany;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorState;

/**
 * 保险公司状态
 * @author Administrator
 *
 */
@Entity
@Table(name = "insure_company_state")
@Cacheable
public class InsureCompanyState extends AbstractAuditorState<InsureCompany, InsureCompanyAct> {
	
	public InsureCompanyState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
	}

	public InsureCompanyState() {
		super();
	}
	
}
