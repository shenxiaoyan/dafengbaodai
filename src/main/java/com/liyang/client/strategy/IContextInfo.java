package com.liyang.client.strategy;

import org.slf4j.Logger;

import com.liyang.domain.createenquiry.CreateEnquiryActRepository;
import com.liyang.domain.createenquiry.CreateEnquiryRepository;
import com.liyang.domain.createenquiry.CreateEnquiryStateRepository;
import com.liyang.domain.customer.CustomerRepository;
import com.liyang.domain.offerresult.OfferResultRepository;
import com.liyang.domain.submitproposal.SubmitProposalActRepository;
import com.liyang.domain.submitproposal.SubmitProposalRepository;
import com.liyang.domain.submitproposal.SubmitProposalStateRepository;

/**
 * @author Administrator
 *
 */
public interface IContextInfo {

	/**
	 * 获取logger
	 * @return
	 */
	Logger getLogger();

	/**
	 * 获取CustomerRepository
	 * 
	 * @return
	 */
	CustomerRepository getCustomerRepository();

	/**
	 * CreateEnquiryActRepository
	 * @return
	 */
	CreateEnquiryActRepository getCreateEnquiryActRepository();

	/**
	 * CreateEnquiryStateRepository
	 * 
	 * @return
	 */
	CreateEnquiryStateRepository getCreateEnquiryStateRepository();

	/**
	 * OfferResultRepository
	 * 
	 * @return
	 */
	OfferResultRepository getOfferResultRepository();

	/**
	 * CreateEnquiryRepository
	 * 
	 * @return
	 */
	CreateEnquiryRepository getCreateEnquiryRepository();

	/**
	 * SubmitProposalStateRepository
	 * @return
	 */
	SubmitProposalStateRepository getSubmitProposalStateRepository();

	/**
	 * SubmitProposalActRepository
	 * @return
	 */
	SubmitProposalActRepository getSubmitProposalActRepository();

	/**
	 * SubmitProposalRepository
	 * @return
	 */
	SubmitProposalRepository getSubmitProposalRepository();

}
