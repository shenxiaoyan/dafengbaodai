package com.liyang.domain.base;

import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author Administrator
 *
 * @param <T>
 */
@NoRepositoryBean
public interface WorkflowRepository<T extends AbstractWorkflow> extends EntityRepository<T> {
}
