package com.liyang.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jpa.query.expression.QueryExpSpecificationBuilder;
import com.jpa.query.expression.generic.GenericQueryExpSpecification;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.customer.CustomerRepository;
import com.liyang.domain.otherinsureinterestperson.OtherInsureInterestPerson;
import com.liyang.domain.otherinsureinterestperson.OtherInsureInterestPersonForSearch;
import com.liyang.domain.otherinsureinterestperson.OtherInsureInterestPersonRepository;

/**
 * @author Administrator
 *
 */
@Service
public class OtherInsureInterestService {

	@Autowired
	private CustomerRepository customerRepository ; 
	
	@Autowired
	private OtherInsureInterestPersonRepository otherInsureInterestPersonRepository;
	
	public void clickInterest(OtherInsureInterestPerson p , HttpServletRequest request ){
		String token = request.getHeader("token");
		Customer customer = customerRepository.findByToken(token);
		p.setClickTime(new Date());
		p.setCustomer(customer);
		
		otherInsureInterestPersonRepository.save(p);
		
	}

	public Page<OtherInsureInterestPerson> multiConditionSearch(OtherInsureInterestPersonForSearch otherInsureForSearch, Pageable pageable) {
//		Page<OtherInsureInterestPerson> page = otherInsureInterestPersonRepository.findAll(new Specification<OtherInsureInterestPerson>() {
//
//			@Override
//			public Predicate toPredicate(Root<OtherInsureInterestPerson> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//				List<Predicate> predicateList = new ArrayList<Predicate>();
//				
//				if (otherInsureForSearch != null) {
//					if (!StringUtils.isEmpty(otherInsureForSearch.getPhoneNumber())) {
//						Predicate phoneNumberEqual = cb.equal(root.get("customer").<String>get("phone"), otherInsureForSearch.getPhoneNumber());
//						predicateList.add(phoneNumberEqual);
//					}
//					if (!StringUtils.isEmpty(otherInsureForSearch.getTitle())) {
//						Predicate titleLike = cb.like(root.<String>get("title"), otherInsureForSearch.getTitle());
//						predicateList.add(titleLike);
//					}
//				}
//				
//				query.where(predicateList.toArray(new Predicate[predicateList.size()]));
//				
//				return null;
//			}
//			
//		}, pageable);
		
		Page<OtherInsureInterestPerson> page = null;
		GenericQueryExpSpecification<OtherInsureInterestPerson> queryExpression = new GenericQueryExpSpecification<OtherInsureInterestPerson>();
		queryExpression
				.add(QueryExpSpecificationBuilder.eq("customer.phone", otherInsureForSearch.getPhoneNumber(), true));
		queryExpression.add(QueryExpSpecificationBuilder.like("title", otherInsureForSearch.getTitle(), false));
		page = otherInsureInterestPersonRepository.findAll(queryExpression, pageable);
		
		return page;
	}
}
