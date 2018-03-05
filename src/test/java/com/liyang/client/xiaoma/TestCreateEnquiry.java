//package com.liyang.client.xiaoma;
//
//import static org.junit.Assert.assertNotNull;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.domain.Specification;
//
//import com.liyang.client.IClient;
//import com.liyang.client.IServiceObserve;
//import com.liyang.client.strategy.SecurityInfoXiaoma;
//import com.liyang.client.xiaoma.mock.MockSuccessResponseSupplierCreateEnquiry;
//import com.liyang.domain.createenquiry.CreateEnquiryAct;
//import com.liyang.domain.createenquiry.CreateEnquiryActRepository;
//import com.liyang.domain.createenquiry.CreateEnquiryState;
//import com.liyang.domain.createenquiry.CreateEnquiryStateRepository;
//import com.liyang.domain.createenquiry.CreateEnquiryWorkflow;
//import com.liyang.domain.customer.Customer;
//import com.liyang.domain.customer.CustomerRepository;
//import com.liyang.domain.platform.Platform;
//
///**
// * @author Administrator
// *
// */
//public class TestCreateEnquiry {
//
//	@Value("${xmcxapi.apikey}")
//	private String xmcxApiKey;
//
//	private final static Logger logger = LoggerFactory.getLogger(TestCreateEnquiry.class);
//
//	@Test
//	public void test() {
//		IClient client = new ClientXiaoma();
//		MessageCreateEnquiry message = null;
//		IServiceObserve serviceObserve = null;
//		MockSuccessResponseSupplierCreateEnquiry responseSupplier = new MockSuccessResponseSupplierCreateEnquiry();
//		String postToXiaoMaUrl = null;
//		CustomerRepository customerRepository = new CustomerRepository() {
//
//			@Override
//			public Customer findOne(Specification<Customer> spec) {
//				return null;
//			}
//
//			@Override
//			public List<Customer> findAll(Specification<Customer> spec, Sort sort) {
//				return null;
//			}
//
//			@Override
//			public Page<Customer> findAll(Specification<Customer> spec, Pageable pageable) {
//				return null;
//			}
//
//			@Override
//			public List<Customer> findAll(Specification<Customer> spec) {
//				return null;
//			}
//
//			@Override
//			public long count(Specification<Customer> spec) {
//				return 0;
//			}
//
//			@Override
//			public <S extends Customer> S findOne(Example<S> example) {
//				return null;
//			}
//
//			@Override
//			public <S extends Customer> Page<S> findAll(Example<S> example, Pageable pageable) {
//				return null;
//			}
//
//			@Override
//			public <S extends Customer> boolean exists(Example<S> example) {
//				return false;
//			}
//
//			@Override
//			public <S extends Customer> long count(Example<S> example) {
//				return 0;
//			}
//
//			@Override
//			public boolean exists(Integer id) {
//				return false;
//			}
//			@Override
//			public Customer findPhoneById(Integer customerId){return null;}
//			@Override
//			public void deleteAll() {
//
//			}
//
//			@Override
//			public void delete(Iterable<? extends Customer> entities) {
//
//			}
//
//			@Override
//			public void delete(Customer entity) {
//
//			}
//
//			@Override
//			public long count() {
//				return 0;
//			}
//
//			@Override
//			public Page<Customer> findAll(Pageable pageable) {
//				return null;
//			}
//
//			@Override
//			public <S extends Customer> S saveAndFlush(S entity) {
//				return null;
//			}
//
//			@Override
//			public <S extends Customer> List<S> save(Iterable<S> entities) {
//				return null;
//			}
//
//			@Override
//			public Customer getOne(Integer id) {
//				return null;
//			}
//
//			@Override
//			public void flush() {
//
//			}
//
//			@Override
//			public <S extends Customer> List<S> findAll(Example<S> example, Sort sort) {
//				return null;
//			}
//
//			@Override
//			public <S extends Customer> List<S> findAll(Example<S> example) {
//				return null;
//			}
//
//			@Override
//			public List<Customer> findAll(Iterable<Integer> ids) {
//				return null;
//			}
//
//			@Override
//			public List<Customer> findAll(Sort sort) {
//				return null;
//			}
//
//			@Override
//			public List<Customer> findAll() {
//				return null;
//			}
//
//			@Override
//			public void deleteInBatch(Iterable<Customer> entities) {
//
//			}
//
//			@Override
//			public void deleteAllInBatch() {
//
//			}
//
//			@Override
//			public Page<Customer> listStateOwnDepartmentAndChildren(String stateCode, Pageable pageable) {
//				return null;
//			}
//
//			@Override
//			public Page<Customer> listStateOwnDepartment(String stateCode, Pageable pageable) {
//				return null;
//			}
//
//			@Override
//			public Page<Customer> listStateOwn(String stateCode, Pageable pageable) {
//				return null;
//			}
//
//			@Override
//			public Page<Customer> listOwnDepartmentAndChildren(Pageable pageable) {
//				return null;
//			}
//
//			@Override
//			public Page<Customer> listOwnDepartment(Pageable pageable) {
//				return null;
//			}
//
//			@Override
//			public Page<Customer> listOwn(Pageable pageable) {
//				return null;
//			}
//
//			@Override
//			public Customer findOne(Integer id) {
//				return null;
//			}
//
//			@Override
//			public void delete(Integer id) {
//
//			}
//
//			@Override
//			public Customer save(Customer user) {
//				return null;
//			}
//
//			@Override
//			public List<Customer> query2() {
//				return null;
//			}
//
//			@Override
//			public Page<Customer> query(Pageable p) {
//				return null;
//			}
//
//			@Override
//			public int modifyByToken(String token, String pushToken) {
//				return 0;
//			}
//
//			@Override
//			public List<Customer> getInvitedList(String invite) {
//				return null;
//			}
//
//			@Override
//			public Page<Customer> getByStateCode(String state, Pageable p) {
//				return null;
//			}
//
//			@Override
//			public List<Customer> getByPhone(String phone) {
//				return null;
//			}
//
//			@Override
//			public List<Customer> getByNickName(String nickname) {
//				return null;
//			}
//
//			@Override
//			public Page<Customer> getByInvited(String invite, Pageable p) {
//				return null;
//			}
//
//			@Override
//			public List<Customer> getByInvite(String invite) {
//				return null;
//			}
//
//			@Override
//			public Customer getById(Integer id) {
//				return null;
//			}
//
//			@Override
//			public Page<Customer> getByGradeAndStateCode(Integer grade, String stateCode, Pageable p) {
//				return null;
//			}
//
//			@Override
//			public Page<Customer> getByGrade(Integer grade, Pageable p) {
//				return null;
//			}
//
//			@Override
//			public Customer findByToken(String token) {
//				return null;
//			}
//
//			@Override
//			public List<Customer> findByMyInvite(String myInvite) {
//				return null;
//			}
//
//			@Override
//			public List<Customer> findByGrade(Integer grade) {
//				return null;
//			}
//
//			@Override
//			public Long countByCreatedAtBetween(Date beginDate, Date endDate) {
//				return null;
//			}
//
//			@Override
//			public List<Customer> findByPhoneLikeAndTeamIsNullAndAccount_RealNameNotNull(String phone, Pageable p) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//		};
//		CreateEnquiryActRepository createEnquiryActRepository = new CreateEnquiryActRepository() {
//
//			@Override
//			public <S extends CreateEnquiryAct> S findOne(Example<S> example) {
//				return null;
//			}
//
//			@Override
//			public <S extends CreateEnquiryAct> Page<S> findAll(Example<S> example, Pageable pageable) {
//				return null;
//			}
//
//			@Override
//			public <S extends CreateEnquiryAct> boolean exists(Example<S> example) {
//				return false;
//			}
//
//			@Override
//			public <S extends CreateEnquiryAct> long count(Example<S> example) {
//				return 0;
//			}
//
//			@Override
//			public <S extends CreateEnquiryAct> S save(S entity) {
//				return null;
//			}
//
//			@Override
//			public CreateEnquiryAct findOne(Integer id) {
//				return null;
//			}
//
//			@Override
//			public boolean exists(Integer id) {
//				return false;
//			}
//
//			@Override
//			public void deleteAll() {
//
//			}
//
//			@Override
//			public void delete(Iterable<? extends CreateEnquiryAct> entities) {
//
//			}
//
//			@Override
//			public void delete(CreateEnquiryAct entity) {
//
//			}
//
//			@Override
//			public void delete(Integer id) {
//
//			}
//
//			@Override
//			public long count() {
//				return 0;
//			}
//
//			@Override
//			public Page<CreateEnquiryAct> findAll(Pageable pageable) {
//				return null;
//			}
//
//			@Override
//			public <S extends CreateEnquiryAct> S saveAndFlush(S entity) {
//				return null;
//			}
//
//			@Override
//			public <S extends CreateEnquiryAct> List<S> save(Iterable<S> entities) {
//				return null;
//			}
//
//			@Override
//			public CreateEnquiryAct getOne(Integer id) {
//				return null;
//			}
//
//			@Override
//			public void flush() {
//
//			}
//
//			@Override
//			public <S extends CreateEnquiryAct> List<S> findAll(Example<S> example, Sort sort) {
//				return null;
//			}
//
//			@Override
//			public <S extends CreateEnquiryAct> List<S> findAll(Example<S> example) {
//				return null;
//			}
//
//			@Override
//			public List<CreateEnquiryAct> findAll(Iterable<Integer> ids) {
//				return null;
//			}
//
//			@Override
//			public List<CreateEnquiryAct> findAll(Sort sort) {
//				return null;
//			}
//
//			@Override
//			public List<CreateEnquiryAct> findAll() {
//				return null;
//			}
//
//			@Override
//			public void deleteInBatch(Iterable<CreateEnquiryAct> entities) {
//
//			}
//
//			@Override
//			public void deleteAllInBatch() {
//
//			}
//
//			@Override
//			public List<CreateEnquiryAct> findByIdInAndRolesRoleCode(List<Integer> ids, String roleCode) {
//				return null;
//			}
//
//			@Override
//			public CreateEnquiryAct findByActCode(String actCode) {
//				CreateEnquiryAct createEnquiryAct = new CreateEnquiryAct();
//				CreateEnquiryWorkflow createEnquiryWorkFlow = new CreateEnquiryWorkflow();
//				if (createEnquiryWorkFlow == null) {
//					createEnquiryAct.setStartWorkflow(null);
//				}
//				createEnquiryAct.setStartWorkflow(null);
//				return new CreateEnquiryAct();
//			}
//		};
//		CreateEnquiryStateRepository createEnquiryStateRepository = new CreateEnquiryStateRepository() {
//
//			@Override
//			public <S extends CreateEnquiryState> S findOne(Example<S> example) {
//				return null;
//			}
//
//			@Override
//			public <S extends CreateEnquiryState> Page<S> findAll(Example<S> example, Pageable pageable) {
//				return null;
//			}
//
//			@Override
//			public <S extends CreateEnquiryState> boolean exists(Example<S> example) {
//				return false;
//			}
//
//			@Override
//			public <S extends CreateEnquiryState> long count(Example<S> example) {
//				return 0;
//			}
//
//			@Override
//			public <S extends CreateEnquiryState> S save(S entity) {
//				return null;
//			}
//
//			@Override
//			public CreateEnquiryState findOne(Integer id) {
//				return null;
//			}
//
//			@Override
//			public boolean exists(Integer id) {
//				return false;
//			}
//
//			@Override
//			public void deleteAll() {
//
//			}
//
//			@Override
//			public void delete(Iterable<? extends CreateEnquiryState> entities) {
//
//			}
//
//			@Override
//			public void delete(CreateEnquiryState entity) {
//
//			}
//
//			@Override
//			public void delete(Integer id) {
//
//			}
//
//			@Override
//			public long count() {
//				return 0;
//			}
//
//			@Override
//			public Page<CreateEnquiryState> findAll(Pageable pageable) {
//				return null;
//			}
//
//			@Override
//			public <S extends CreateEnquiryState> S saveAndFlush(S entity) {
//				return null;
//			}
//
//			@Override
//			public <S extends CreateEnquiryState> List<S> save(Iterable<S> entities) {
//				return null;
//			}
//
//			@Override
//			public CreateEnquiryState getOne(Integer id) {
//				return null;
//			}
//
//			@Override
//			public void flush() {
//
//			}
//
//			@Override
//			public <S extends CreateEnquiryState> List<S> findAll(Example<S> example, Sort sort) {
//				return null;
//			}
//
//			@Override
//			public <S extends CreateEnquiryState> List<S> findAll(Example<S> example) {
//				return null;
//			}
//
//			@Override
//			public List<CreateEnquiryState> findAll(Iterable<Integer> ids) {
//				return null;
//			}
//
//			@Override
//			public List<CreateEnquiryState> findAll(Sort sort) {
//				return null;
//			}
//
//			@Override
//			public List<CreateEnquiryState> findAll() {
//				return null;
//			}
//
//			@Override
//			public void deleteInBatch(Iterable<CreateEnquiryState> entities) {
//
//			}
//
//			@Override
//			public void deleteAllInBatch() {
//
//			}
//
//			@Override
//			public List<CreateEnquiryState> findForShow() {
//				return null;
//			}
//
//			@Override
//			public CreateEnquiryState findByStateCode(String stateCode) {
//				return null;
//			}
//		};
//		SecurityInfoXiaoma securityInfo = new SecurityInfoXiaoma(xmcxApiKey);
//		ServiceCreateEnquiry service = new ServiceCreateEnquiry(securityInfo, client, serviceObserve, responseSupplier,
//				customerRepository, createEnquiryActRepository, createEnquiryStateRepository);
//		try {
//			String token = "5ff7be41-2167-43bf-af29-3600996717b0";
//			Platform platform = new Platform();
//			platform.setApplicationId("fengxianwuyou_android");
//			// String createEnquiryParams =
//			// "{\"createEnquiryParams\":\"{\"licenseNumber\":\"浙G786VL\",\"idCard\":\"33072219880329281X\",\"ownerName\":\"方辉\",\"insuranceStartTime\":1499914422,\"forceInsuranceStartTime\":1499914422,\"carTypeCode\":\"02\",\"cityName\":\"阿坝州\",\"cityCode\":\"513200\",\"insuranceCompanyName\":\"1\",\"requestHeader\":\"111\",\"insurancesList\":[{\"schemeName\":\"方案1\",\"forcePremium\":{\"isToubao\":\"1\"},\"insurances\":[{\"insuranceId\":1,\"compensation\":true,\"price\":\"1\",\"isToubao\":1},{\"insuranceId\":2,\"compensation\":true,\"price\":\"1000000\",\"isToubao\":1}]}],\"transferDate\":0}";
//			String mobilePhone = "18900000000";
//			Map<String, Object> creEnqMap = new HashMap<>();
//			Map<String, Object> createEnquiryParams1 = new HashMap<>();
//			createEnquiryParams1.put("licenseNumber", "浙 G786VL");
//			createEnquiryParams1.put("idCard", "33072219880329281X");
//			createEnquiryParams1.put("ownerName", "方辉");
//			createEnquiryParams1.put("insuranceStartTime", 1499914422);
//			createEnquiryParams1.put("forceInsuranceStartTime", 1499914422);
//			createEnquiryParams1.put("carTypeCode", "02");
//			createEnquiryParams1.put("cityName", "阿坝州");
//			createEnquiryParams1.put("cityCode", "513200");
//			createEnquiryParams1.put("insuranceCompanyName", "1");
//			createEnquiryParams1.put("requestHeader", "111");
//
//			Map<String, Object> insuranceMap = new HashMap<>();
//			insuranceMap.put("schemeName", "方案 1");
//
//			Map<String, Object> forcePremiumMap = new HashMap<>();
//			forcePremiumMap.put("isToubao", "1");
//			insuranceMap.put("forcePremium", forcePremiumMap);
//
//			Map<String, Object> insurancesMap = new HashMap<>();
//			List<Map<String, Object>> insurances = new ArrayList<>();
//			insurances.add(insurancesMap);
//			insurancesMap.put("insuranceId", 1);
//			insurancesMap.put("compensation", true);
//			insurancesMap.put("price", "1");
//			insurancesMap.put("isToubao", 1);
//			insuranceMap.put("insurances", insurances);
//			List<Map<String, Object>> insurancesList = new ArrayList<>();
//			insurancesList.add(insuranceMap);
//			createEnquiryParams1.put("insurancesList", insurancesList);
//
//			creEnqMap.put("createEnquiryParams", createEnquiryParams1);
//			creEnqMap.put("mobilePhone", mobilePhone);
//			// creEnqMap.put("licenseNumber", "浙 G786VL");
//			// creEnqMap.put("idCard", "33072219880329281X");
//			// creEnqMap.put("ownerName", "方辉");
//			// creEnqMap.put("insuranceStartTime ", 1499914422);
//			// creEnqMap.put("forceInsuranceStartTime ", 1499914422);
//			// creEnqMap.put("carTypeCode", "02");
//			// creEnqMap.put("cityName", "阿坝州");
//			// creEnqMap.put("cityCode", "513200");
//			// creEnqMap.put("insuranceCompanyName", "1");
//			// creEnqMap.put("requestHeader", "111");
//			//
//			// List<Map<String,Object>> insurancesList = new ArrayList<>();
//			// Map<String,Object> insuranceMap = new HashMap<>();
//			// insuranceMap.put("schemeName", "方案 1");
//			//
//			// Map<String,Object> forcePremiumMap = new HashMap<>();
//			// forcePremiumMap.put("isToubao", "1");
//			// insuranceMap.put("forcePremium",forcePremiumMap);
//			//
//			// List<Map<String,Object>> insurances = new ArrayList<>();
//			// Map<String,Object> insurancesMap = new HashMap<>();
//			// insurancesMap.put("insuranceId",1);
//			// insurancesMap.put("compensation",true);
//			// insurancesMap.put("price","1");
//			// insurancesMap.put("isToubao",1);
//			// insuranceMap.put("insurances", insurances);
//			// insurancesList.add(insuranceMap);
//			// creEnqMap.put("insurancesList", insurancesList);
//			// creEnqMap.put("transferDate", 0);
//			// creEnqMap.put("mobilePhone", mobilePhone);
//
//			message = new MessageCreateEnquiry(logger, token, platform, creEnqMap);
//			ResultCreateEnquiry result = null;
//			try {
//				result = (ResultCreateEnquiry) service.callService(message);
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			assertNotNull(result);
//			assertNotNull(result.getCreateEnquiry());
//			System.out.println(result.getCreateEnquiry());
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//}
