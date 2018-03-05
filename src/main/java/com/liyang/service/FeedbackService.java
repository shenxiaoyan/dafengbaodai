package com.liyang.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.liyang.domain.feedback.Feedback;
import com.liyang.domain.feedback.FeedbackRepository;

/**
 * @author Administrator
 *
 */
@Service
public class FeedbackService {
	@Autowired
	FeedbackRepository feedbackRepository;

	public Feedback save(Feedback feedback) {
		return feedbackRepository.save(feedback);
	}

	public Page<Feedback> search(Date beginDate, Date endDate, Pageable p) {
		return feedbackRepository.findAll(new Specification<Feedback>() {

			@Override
			public Predicate toPredicate(Root<Feedback> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				if (beginDate != null) {
					Predicate submitTimeGTOE = cb.greaterThanOrEqualTo(root.<Date>get("submitTime"), beginDate);
					predicateList.add(submitTimeGTOE);
				}
				if (endDate != null) {
					Predicate submitTimeLT = cb.lessThan(root.<Date>get("submitTime"), endDate);
					predicateList.add(submitTimeLT);
				}
				query.where(predicateList.toArray(new Predicate[predicateList.size()]));
				return null;
			}
		}, p);
	}
}
