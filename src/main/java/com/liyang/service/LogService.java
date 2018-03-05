package com.liyang.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jpa.query.expression.QueryExpSpecificationBuilder;
import com.jpa.query.expression.generic.GenericQueryExpSpecification;
import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.LogForSearch;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.createenquiry.CreateEnquiry;

@Service
public class LogService {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page<AbstractAuditorLog> multiComditionSearch(final LogForSearch searchBean, Pageable page,
			LogRepository logRepository) {

		GenericQueryExpSpecification<CreateEnquiry> queryExpression = new GenericQueryExpSpecification<CreateEnquiry>();
		queryExpression.add(QueryExpSpecificationBuilder.eq("act.actCode", searchBean.getActCode(), true));
		if (searchBean.getEntityId() != null) {
			queryExpression.add(QueryExpSpecificationBuilder.eq("entity.id", searchBean.getEntityId(), true));
		} else {
			List<Integer> maxIds = logRepository.findMaxIdGroupByEntityId();
			queryExpression.add(QueryExpSpecificationBuilder.in("id", maxIds, true));
		}
		Page<AbstractAuditorLog> pages = logRepository.findAll(queryExpression, page);
		return pages;
	}
}
