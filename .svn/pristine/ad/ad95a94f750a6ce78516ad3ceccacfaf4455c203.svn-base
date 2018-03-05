package com.liyang.domain.submitproposal;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.liyang.domain.base.WorkflowEntityRepository;

//@RepositoryRestResource(excerptProjection = SubmitProposalProjection.class)
/**
 * 投保订单Repository
 * @author Administrator
 *
 */
public interface SubmitProposalRepository extends WorkflowEntityRepository<SubmitProposal>, JpaSpecificationExecutor<SubmitProposal>{
	@Override
	@Transactional
	public SubmitProposal  save(SubmitProposal submitProposal);
	
	@Query("select sub from SubmitProposal as sub join sub.params as results where results.orderId=?1")
	public SubmitProposal findByOrderId(@Param("orderId")String orderId);
	
	@Query("select s from SubmitProposal s where s.customer.token=?1 and s.isShow=0 order by s.lastModifiedAt")
	public List<SubmitProposal> findSubmitProposalByToken(@Param("token")String	token);
	
	@Query("select s from SubmitProposal s where s.customer.token=?1 and s.state.stateCode= ?2 order by s.lastModifiedAt ")
	public List<SubmitProposal> findSubmitProposalByTokenAndStatus(@Param("token")String token , @Param("status") String status);
	
	@Query("select c from SubmitProposal c where c.state.stateCode = ?1 order by c.createTime desc")
	public Page<SubmitProposal> getByStateCode(@Param("stateCode") String state, Pageable p);
	
	@Query("select sub from SubmitProposal as sub join sub.params as results where results.insuredPhone = ?1")
	public Page<SubmitProposal> findByInsuredPhone(@Param("insuredPhone")String phone , Pageable p);
	
	@Query("select orr from SubmitProposal c join c.offerResult orr order by c.createTime desc")
	public Page<SubmitProposal> query(Pageable p);
	
	@Query("select c from SubmitProposal c join c.offerResult orr order by c.createTime desc")
	public Page<SubmitProposal> query2(Pageable p);

	@Query("select c from SubmitProposal c where c.customer.id=:customerId ")
	public  Page<SubmitProposal> findByCustomerId(@Param("customerId")Integer customerId,Pageable pageable);

	public SubmitProposal getById(Integer id);
	
	@Query("select sub from SubmitProposal as sub join sub.params as results where results.orderId=?1")
	public List<SubmitProposal> findByOrderId2(@Param("orderId")String orderId);
	
	@Query("select s from SubmitProposal s where s.customer.token=?1 and (s.state.stateCode= 'CHENGBAO_SUCCESS' or s.state.stateCode= 'CHENGBAO_FAILD' or s.state.stateCode= 'PAYMENT_SUCCESS') order by s.lastModifiedAt ")
	public List<SubmitProposal> getFinishedByToken(@Param("token") String token);
	
	public Long countByCreateTimeBetween(Date beginDate, Date endDate);
	
	public Long countByState_StateCodeAndCreateTimeBetween(String stateCode, Date beginDate, Date endDate);

	public Long countByState_StateCodeInAndCreateTimeBetween(String[] stateCode, Date beginDate, Date endDate);

	@Query("select sum(d.underwritingPriceCent) from SubmitProposal as s join s.underwritingResult as u join u.data as d where s.state.stateCode=?1 and (s.createTime between ?2 and ?3)")
	public Double queryPrice(String stateCode, Date beginDate, Date endDate);

//	@Query("select count(s.offerResult.data.result.insuranceCompanyName) from SubmitProposal as s join s.underwritingResult as u join u.data as d where s.state.stateCode=?1 and s.offerResult.data.result.insuranceCompanyId=?2 and(s.createTime between ?3 and ?4)")
	public Long countByState_StateCodeAndOfferResult_Data_Result_InsuranceCompanyIdAndCreateTimeBetween(String stateCode,Integer insuranceCompanyId, Date beginDate, Date endDate);
	
	public List<SubmitProposal> findByParamsOrderId(@Param("orderId")String orderId);
	
	/**
	 * 删除订单
	 * @param id
	 */
	@Modifying
	@Query("update SubmitProposal s set s.isShow=1 where s.id=?")
	@Transactional
	public  void  changeByIsShow(@Param("id")Integer id);
	
	// 临时方法，获取所有大蜂配送信息是空的数据
	public List<SubmitProposal> findByParams_DafengContactNameIsNull();

	//获取所有承保成功的订单
	public  List<SubmitProposal> findByStateStateCodeAndCreateTimeBetween(@Param("stateCode") String stateCode,@Param("beginDate") Date beginDate,@Param("endDate") Date endDate);
	
}
















 