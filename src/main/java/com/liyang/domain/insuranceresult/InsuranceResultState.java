package com.liyang.domain.insuranceresult;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowState;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserAct;
import com.liyang.domain.user.UserWorkflow;

/**
 * 承保结果状态，是否正常
 * @author Administrator
 *
 */
@Entity
@Table(name = "insurance_result_state")
@Cacheable
public class InsuranceResultState extends AbstractWorkflowState<InsuranceResult, InsuranceResultWorkflow, InsuranceResultAct>{
	public InsuranceResultState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public InsuranceResultState() {
		super();
	}
	
}
