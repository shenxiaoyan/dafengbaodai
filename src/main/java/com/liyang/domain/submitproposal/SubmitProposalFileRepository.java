package com.liyang.domain.submitproposal;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.liyang.domain.base.FileRepository;
import com.liyang.domain.user.UserFile;
/**
 * 投保订单FileRepository
 * @author Administrator
 *
 */
//@RepositoryRestResource(excerptProjection = AbstractWorkflowLogProjection.class)
public interface SubmitProposalFileRepository  extends FileRepository<SubmitProposalFile> {
	
	@Override
	@Transactional
	public  SubmitProposalFile save(SubmitProposalFile submitproposalFile);
}
