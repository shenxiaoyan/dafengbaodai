package com.liyang.domain.customer;

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
import com.liyang.domain.team.Team;

//@RepositoryRestResource(excerptProjection=CustomerProjection.class)
/**
 * @author Administrator
 *
 */
public interface CustomerRepository extends WorkflowEntityRepository<Customer>, JpaSpecificationExecutor<Customer> {

	@Transactional
	public Customer save(Customer user);

	public List<Customer> getByPhone(@Param("phone") String phone);

	public Customer getById(@Param("id") Integer id);

	@Query("from Customer u")
	public Page<Customer> query(Pageable p);

	public Customer findByToken(@Param("token") String token);

	@Transactional
	@Modifying
	@Query("update Customer u1 set u1.pushToken=?2 where u1.token=?1")
	@RestResource(exported = true)
	public int modifyByToken(@Param("token") String token, @Param("pushToken") String pushToken);

	@Query("from Customer c where c.state.stateCode = ?1")
	public Page<Customer> getByStateCode(@Param("stateCode") String state, Pageable p);

	@Query("from Customer c where c.myInvite = ?1")
	public List<Customer> getByInvite(@Param("invite") String invite);

	@Query("from Customer c where c.grade = ?1 ")
	public Page<Customer> getByGrade(@Param("grade") Integer grade, Pageable p);

	// from A left join A.B where (b.flag is null or b.flag='true')
	// select a.*,b.name from a left join b on a.bid=b.id and b.flag='1
	@Query(value = "select c.*,a.* from customer c left join account a on c.account_id =a.id where c.grade = ?1 ", nativeQuery = true)
	public List<Customer> findByGrade(@Param("grade") Integer grade);

	@Query("from Customer u")
	public List<Customer> query2();

	@Query("select c.nickname,c.sex,c.headimgurl,c.myInvite,c.grade,c.state,c.createdAt,c.lastModifiedAt from Customer c where c.nickname like ?1")
	public List<Customer> getByNickName(@Param("nickname") String nickname);

	// @Query("select
	// c.nickname,c.sex,c.headimgurl,c.myInvite,c.grade,c.state,c.createdAt,c.lastModifiedAt
	// from Customer c where c.grade = ?1 ")
	// public List<Customer> getByGrade(@Param("grade") Integer grade );

	@Query("from Customer c where c.invite = ?1")
	public Page<Customer> getByInvited(@Param("invite") String invite, Pageable p);

	@Query("from Customer c where c.invite = ?1")
	public List<Customer> getInvitedList(@Param("invite") String invite);

	@Query("from Customer c where c.grade = ?1 and c.state.stateCode = ?2")
	public Page<Customer> getByGradeAndStateCode(@Param("grade") Integer grade, @Param("stateCode") String stateCode,
			Pageable p);

	public Long countByCreatedAtBetween(Date beginDate, Date endDate);

	public List<Customer> findByMyInvite(@Param("myInvite") String myInvite);

	public Customer findPhoneById(Integer customerId);

	public List<Customer> findByTeamIsNullAndAccount_RealNameNotNullAndStateStateCodeAndPhoneLike(String stateCode, String phone);
	
	// 据Id查找有效状态
	public Customer findByIdAndState_StateCode(Integer customerId, String stateCode);
	
	public int countByTeam_Id(Integer teamId);

	@RestResource(path = "/byTeamIdPage")
	public Page<Customer> findByTeam_Id(Integer teamId, Pageable pageable);
	
	public List<Customer> findByTeam_Id(Integer teamId);
	
	public Customer findByPhone(String phone);

	public List<Customer> findByTeamIdAndPhoneLike(Integer teamId, String phone);
	
}
