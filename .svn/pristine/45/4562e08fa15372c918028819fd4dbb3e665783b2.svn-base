package com.liyang.domain.otherinsureinterestperson;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jpa.query.expression.generic.SpecificationQueryRepository;

/**
 * 人员保险
 * @author Administrator
 *
 */
public interface OtherInsureInterestPersonRepository extends JpaRepository<OtherInsureInterestPerson, Integer>,
		SpecificationQueryRepository<OtherInsureInterestPerson> {
	@Query("from OtherInsureInterestPerson o order by o.clickTime desc")
	public Page<OtherInsureInterestPerson> query(Pageable p);
}
