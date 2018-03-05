package com.liyang.domain.createenquiry;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.liyang.domain.base.WorkflowEntityRepository;

//@RepositoryRestResource(excerptProjection=CreateEnquiryProjection.class)
//@RepositoryRestResource(path="CreateEnquiryApi")
/**
 * @author Administrator
 *
 */
public interface CreateEnquiryRepository extends  WorkflowEntityRepository<CreateEnquiry>, JpaSpecificationExecutor<CreateEnquiry>{
	
	@Override
	@Transactional
	public CreateEnquiry save(CreateEnquiry createEnquiry);
	
	public CreateEnquiry findByOfferUnique(String offerUnique);
	
	@Query("select c from CreateEnquiry as c where c.customer.token=?1 and c.isShow = 0")
	public List<CreateEnquiry> findCreateEnquiryListByToken(@Param("token")String token);
	
	@RestResource(path="tokenAndStateCode",rel="tokenAndStateCode")
	@Query("select c from CreateEnquiry as c where c.customer.token=?1 and c.state.stateCode=?2" )
	public List<CreateEnquiry> findCreateEnquiryListByTokenAndState(@Param("token")String token, @Param("stateCode")String stateCode);
	
	@Query("select c from CreateEnquiry as c where c.customer.token=?1 and c.state.stateCode=?2 and c.createdAt <=?3 " )
	public List<CreateEnquiry> findCreateEnquiryListByTokenAndStateAndCreateTime(String token, String stateCode,Date beginTime);
//	public List<CreateEnquiry> findCreateEnquiryListByTokenAndStateAndCreateTime(@Param("token")String token, @Param("stateCode")String stateCode,Date beginTime);
	
	public CreateEnquiry findById(Integer id); 
	
	@Query("from CreateEnquiry c where c.state.stateCode = ?1 order by c.createdAt desc")
	public Page<CreateEnquiry> getByStateCode(@Param("stateCode") String state, Pageable p);
	
	@Query("from CreateEnquiry c order by created_at desc")
	public Page<CreateEnquiry> query(Pageable p);
	
	public long countByCreatedAtBetween(Date beginDate, Date endDate);
	
	/**
	 * 更改数据状态为作废
	 * @param id
	 */
	@Modifying
	@Query(value="update CreateEnquiry c set c.isShow = '1' where c.id = ?1")
	@Transactional
	public void updateCreateEnquiryStateById(@Param("id") Integer id);
	
	
	public List<CreateEnquiry> findByOwnerNameIsNullAndLicenseNumberIsNull();
	
	
	//根据相关要求对数据进行查询
//	@Query(value="select c from CreateEnquiry c where c.isShow = 0 and c.customer.token=?")
//	public List<CreateEnquiry> findCreateEnquiryListByStateAndToken(@Param("token")String token); 
//	
//	@Query(value="select c from CreateEnquiry c where c.isShow = 0")
//	public List<CreateEnquiry> findCreateEnquiryListByStateAndToken(); 
	
	
}
