package com.liyang.domain.otherInsure;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.AbstractWorkflowLog;

/**
 * 其他保险log
 * @author Administrator
 *
 */
@Entity
@Table(name="other_insure_log")
public class OtherInsureLog extends AbstractAuditorLog<OtherInsure, OtherInsureState, OtherInsureAct>{

}
