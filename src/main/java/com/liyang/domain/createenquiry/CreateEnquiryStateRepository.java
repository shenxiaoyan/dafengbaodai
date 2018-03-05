package com.liyang.domain.createenquiry;

import org.springframework.stereotype.Repository;

import com.liyang.domain.base.StateRepository;

/**
 * @author Administrator
 *
 */
//@RepositoryRestResource(excerptProjection = AbstractWorkflowStateProjection.class)
public interface CreateEnquiryStateRepository extends StateRepository<CreateEnquiryState> {

}
