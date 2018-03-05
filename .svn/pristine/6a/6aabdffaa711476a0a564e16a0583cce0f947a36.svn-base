package com.liyang.util;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.Assert;

/**
 * @author Administrator
 *
 * @param <ENTITY>
 * @param <ID>
 */
public class QueryExpressionSimpleJpaRepository<ENTITY, ID extends Serializable> extends SimpleJpaRepository<ENTITY, ID> {

	private final EntityManager em;

	public QueryExpressionSimpleJpaRepository(Class<ENTITY> entityClass, EntityManager em) {
		super(entityClass, em);
		this.em = em;
	}

	public QueryExpressionSimpleJpaRepository(JpaEntityInformation<ENTITY, ID> entityInformation, EntityManager em) {
		super(entityInformation, em);
		this.em = em;
	}

	@Override
	protected <S extends ENTITY> TypedQuery<S> getQuery(Specification<S> spec, Class<S> domainClass,
			org.springframework.data.domain.Sort sort) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<S> query = builder.createQuery(domainClass);

		Root<S> root = applySpecificationToCriteria(spec, domainClass, query);
		if (null == query.getSelection()) {
			query.select(root);
		}

		if (sort != null) {
			query.orderBy(toOrders(sort, root, builder));
		}

		return applyRepositoryMethodMetadata(em.createQuery(query));
	}

	private <S, U extends ENTITY> Root<U> applySpecificationToCriteria(Specification<U> spec, Class<U> domainClass,
			CriteriaQuery<S> query) {

		//Assert.notNull(query);
		//Assert.notNull(domainClass);
		Root<U> root = query.from(domainClass);

		if (spec == null) {
			return root;
		}

		CriteriaBuilder builder = em.getCriteriaBuilder();
		Predicate predicate = spec.toPredicate(root, query, builder);

		if (predicate != null) {
			query.where(predicate);
		}

		return root;
	}

	private <S> TypedQuery<S> applyRepositoryMethodMetadata(TypedQuery<S> query) {

		if (getRepositoryMethodMetadata() == null) {
			return query;
		}

		LockModeType type = getRepositoryMethodMetadata().getLockModeType();
		TypedQuery<S> toReturn = type == null ? query : query.setLockMode(type);

		applyQueryHints(toReturn);

		return toReturn;
	}

	private void applyQueryHints(Query query) {

		for (Entry<String, Object> hint : getQueryHints().entrySet()) {
			query.setHint(hint.getKey(), hint.getValue());
		}
	}

	@Override
	protected <S extends ENTITY> Page<S> readPage(TypedQuery<S> query, Class<S> domainClass, Pageable pageable,
			Specification<S> spec) {
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Long total = executeCountQuery(getCountQuery(spec, domainClass));
		List<S> content = total > pageable.getOffset() ? query.getResultList() : Collections.<S>emptyList();

		return new PageImpl<S>(content, pageable, total);
	}
	
	/**
	 * Executes a count query and transparently sums up all values returned.
	 * 
	 * @param query must not be {@literal null}.
	 * @return
	 */
	private static Long executeCountQuery(TypedQuery<Long> query) {

		Assert.notNull(query, "TypedQuery must not be null!");

		List<Long> totals = query.getResultList();
		Long total = 0L;

		for (Long element : totals) {
			total += element == null ? 0 : element;
		}

		return total;
	}
}