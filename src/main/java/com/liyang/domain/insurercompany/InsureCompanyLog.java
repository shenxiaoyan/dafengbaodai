package com.liyang.domain.insurercompany;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.AbstractWorkflowLog;

/**
 * 保险公司日志
 * @author Administrator
 *
 */
@Entity
@Table(name="insure_company_log")
public class InsureCompanyLog extends AbstractAuditorLog<InsureCompany, InsureCompanyState, InsureCompanyAct>{

}
