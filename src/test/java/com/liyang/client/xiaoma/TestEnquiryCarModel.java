package com.liyang.client.xiaoma;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.liyang.client.IClient;
import com.liyang.client.IServiceObserve;
import com.liyang.client.strategy.SecurityInfoXiaoma;
import com.liyang.client.xiaoma.mock.MockSuccessResponseSupplierEnquiryCarModel;
import com.liyang.domain.createenquiry.CreateEnquiry;
import com.liyang.domain.offerresult.OfferResult;
import com.liyang.domain.offerresult.OfferResultRepository;
import com.liyang.domain.platform.Platform;
import com.mysql.jdbc.Security;

/**
 * @author Administrator
 *
 */
public class TestEnquiryCarModel {

	@Value("${xmcxapi.apikey}")
	private String xmcxApiKey;

	@Test
	public void test() {
		IClient client = new ClientXiaoma();
		MessageEnquiryCarModel message = null;
		String xiaomaUrl = null;
		IServiceObserve serviceObserve = null;
		MockSuccessResponseSupplierEnquiryCarModel responseSupplier = new MockSuccessResponseSupplierEnquiryCarModel();
		SecurityInfoXiaoma securityInfo = new SecurityInfoXiaoma(xmcxApiKey);
		ServiceEnquiryCarModel service = new ServiceEnquiryCarModel(securityInfo, client, serviceObserve,
				responseSupplier);
		OfferResultRepository offResRespository = new OfferResultRepository() {
			
			@Override
			public <S extends OfferResult> S findOne(Example<S> example) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <S extends OfferResult> Page<S> findAll(Example<S> example, Pageable pageable) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <S extends OfferResult> boolean exists(Example<S> example) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public <S extends OfferResult> long count(Example<S> example) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public OfferResult findOne(Integer id) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean exists(Integer id) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void deleteAll() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void delete(Iterable<? extends OfferResult> entities) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void delete(OfferResult entity) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void delete(Integer id) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public long count() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Page<OfferResult> findAll(Pageable pageable) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <S extends OfferResult> S saveAndFlush(S entity) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <S extends OfferResult> List<S> save(Iterable<S> entities) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public OfferResult getOne(Integer id) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void flush() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public <S extends OfferResult> List<S> findAll(Example<S> example, Sort sort) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <S extends OfferResult> List<S> findAll(Example<S> example) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<OfferResult> findAll(Iterable<Integer> ids) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<OfferResult> findAll(Sort sort) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<OfferResult> findAll() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void deleteInBatch(Iterable<OfferResult> entities) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void deleteAllInBatch() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public OfferResult save(OfferResult offerResult) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<OfferResult> getByCreateEnquiry(CreateEnquiry ce) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Platform findPlatformByOfferId(String offerId) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public OfferResult findOfferResultByOfferId(String offerId) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public OfferResult findById(Integer id) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<OfferResult> findByDataResultOfferId(String offerId) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<OfferResult> findByDataRequestHeaderOfferUniqueAndDataResultInsuranceCompanyId(String offerUnique,
					Integer insuranceCompanyId) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<OfferResult> findByCreateEnquiryCustomerTokenAndSuccessfulIsNullAndCreateEnquiryCreatedAtBefore(
					String token, Date createdAt) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		try {
			String orderId = "200-20171020214016-317505";
			Map<String, Object> modenqMap = new HashMap<>();
			modenqMap.put("multiEnquiryId", "109-20161129170215-70d41");
			modenqMap.put("offerId", "102-20161129170215-70d41");
			Map<String, Object> modelParam = new HashMap<>();
			modelParam.put("companyId", 2);
			modelParam.put("type", "vehicleId");
			modelParam.put("vehicleId", "XXXX");

			modenqMap.put("modelParam", modelParam);
			message = new MessageEnquiryCarModel(modenqMap, offResRespository);
			ResultEnquiryCarModel result = null;
			try {
				result = (ResultEnquiryCarModel) service.callService(message);

			} catch (Exception e) {
				e.printStackTrace();
			}
			assertNotNull(result);
			assertNotNull(result.getParmas());
			// System.out.println(result.getParmas());
			// System.out.println(result.getRawResponse());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
