package com.liyang.util;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * @author Administrator
 *
 * @param <ENTITY>
 * @param <ID>
 * @param <REPO>
 */
public class QueryExpressionRepositoryFactoryBean<ENTITY, ID extends Serializable, REPO extends JpaRepository<ENTITY, ID>>
		extends JpaRepositoryFactoryBean<REPO, ENTITY, ID> {

	@Override
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
		return new MyRepositoryFactory(em);
	}

	private static class MyRepositoryFactory<ENTITY, ID extends Serializable> extends JpaRepositoryFactory {

		private final EntityManager em;

		public MyRepositoryFactory(EntityManager em) {
			super(em);
			this.em = em;
		}

		protected Object getTargetRepository(RepositoryMetadata metadata) {
			return new QueryExpressionSimpleJpaRepository<ENTITY, ID>((Class<ENTITY>) metadata.getDomainType(), em);
		}

		@Override
		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return QueryExpressionSimpleJpaRepository.class;
		}
	}

}
