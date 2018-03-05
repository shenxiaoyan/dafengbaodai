package com.liyang.domain.journal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.liyang.domain.base.AuditorEntityRepository;

/**
 * @author Adam
 * @version 创建时间：2018年1月31日 下午2:50:18 类说明
 */
public interface JournalRepository extends AuditorEntityRepository<Journal>, JpaSpecificationExecutor<Journal> {

	Journal findByOrderId(String orderId);

	@Query("select  sum(j.commission) from Journal j where j.customer.token=?1 and j.state.stateCode=?2")
	Double sumCommissionByTokenAndState(@Param("token") String token, @Param("stateCode") String stateCode);

	List<Journal> findByState_StateCode(String stateCode);

}
