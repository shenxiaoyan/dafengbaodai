package com.liyang.domain.insuranceresult;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.liyang.domain.base.WorkflowEntityRepository;
import com.liyang.domain.submitproposal.SubmitProposal;

/**
 * @author Administrator
 *
 */
@RepositoryRestResource
public interface InsuranceResultRepository
		extends WorkflowEntityRepository<InsuranceResult>, JpaSpecificationExecutor<InsuranceResult> {

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public InsuranceResult save(InsuranceResult insuranceResult);

	@Query("select ir from InsuranceResult ir where ir.state.stateCode = ?1 ")
	public Page<InsuranceResult> findByState(@Param("state") String state, Pageable p);

	@Query("select ir from InsuranceResult ir join ir.data data where data.orderId = ?1")
	public InsuranceResult findByOrderId(String orderId);

	@Query("select ir from InsuranceResult ir order by ir.createdAt desc")
	public Page<InsuranceResult> query(Pageable p);

	public List<InsuranceResult> findByDataOrderId(String orderId);

	public List<InsuranceResult> findByCreatedAtBetween(Date beginTime, Date endTime);

	public List<InsuranceResult> findByCreatedAtBefore(Date endTime);

	public SubmitProposal findBySubmitProposalId(Integer submitProposalId);

	// 根据customerId，获取所传时间段内承保成功的数据集合
	public List<InsuranceResult> findByData_StateAndSubmitProposal_Customer_IdAndCreatedAtBetween(Integer state,
			Integer customerId, Date startDate, Date endDate);
}
