package com.liyang.domain.offerresult;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.liyang.domain.createenquiry.CreateEnquiry;
import com.liyang.domain.platform.Platform;

/**
 * 报价结果
 * @author Administrator
 *
 */
//@RepositoryRestResource(excerptProjection=OfferResultProjection.class)
public interface OfferResultRepository extends JpaRepository<OfferResult, Integer> {

	@Transactional
	public  OfferResult save(OfferResult offerResult);
	
	@Query("select offres.platform from OfferResult as offres join offres.data.result as results where results.offerId=?1")
	public Platform findPlatformByOfferId(@Param("offerId")String offerId);
	
	@Query("select offres from OfferResult as offres join offres.data.result as results where results.offerId=?1")
	public OfferResult findOfferResultByOfferId(@Param("offerId")String offerId);
	
	
//	@Query("")
//	public OfferResult findOfferResultByOfferId2(@Param("offerId")String offerId);
	
	public OfferResult findById(Integer id);
	
	public List<OfferResult> getByCreateEnquiry(CreateEnquiry ce);
	
	//TODO offerUnique与insuranceCompanyId 应该可以唯一定位，需要使用list???   by------Djh
	public List<OfferResult> findByDataRequestHeaderOfferUniqueAndDataResultInsuranceCompanyId(
			@Param("offerUnique") String offerUnique, @Param("insuranceCompanyId") Integer insuranceCompanyId);
	
	public List<OfferResult> findByDataResultOfferId(@Param("offerId")String offerId);
	
	public List<OfferResult> findByCreateEnquiryCustomerTokenAndSuccessfulIsNullAndCreateEnquiryCreatedAtBefore(String token, Date createdAt);
	
}
