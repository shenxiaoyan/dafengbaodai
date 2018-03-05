package com.liyang.domain.insuranceresult;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflow;

/**
 * 承保结果推送相关
 * @author Administrator
 *
 */
@Entity
@Table(name="insurance_result_workflow")
@Cacheable
public class InsuranceResultWorkflow extends AbstractWorkflow<InsuranceResult, InsuranceResultState> {

}
