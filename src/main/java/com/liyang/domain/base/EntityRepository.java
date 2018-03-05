package com.liyang.domain.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.rest.core.annotation.RestResource;

import com.liyang.domain.department.Department;
import com.liyang.domain.user.User;

/**
 * @author Administrator
 *
 * @param <T>
 */
@NoRepositoryBean
public interface EntityRepository<T extends BaseEntity> extends JpaRepository<T, Integer> {
	

	

	
}
