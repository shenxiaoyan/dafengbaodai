package com.liyang.domain.createenquiry;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowLog;


/**
 * 询价日志
 * @author Administrator
 *
 */
@Entity
@Table(name="create_enquiry_log")
@Cacheable
public class CreateEnquiryLog extends AbstractWorkflowLog<CreateEnquiry,CreateEnquiryWorkflow,CreateEnquiryState,CreateEnquiryAct,CreateEnquiryFile> {

}
