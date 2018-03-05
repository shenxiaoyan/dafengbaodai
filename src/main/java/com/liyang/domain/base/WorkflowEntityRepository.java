package com.liyang.domain.base;

import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author Administrator
 *
 * @param <T>
 */
@NoRepositoryBean
public interface WorkflowEntityRepository<T extends AbstractWorkflowEntity> extends AuditorEntityRepository<T> {

}
