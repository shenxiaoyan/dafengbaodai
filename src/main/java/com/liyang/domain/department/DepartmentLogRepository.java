package com.liyang.domain.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.liyang.domain.base.LogRepository;
/**
 * @author Administrator
 *
 */
//@RepositoryRestResource(excerptProjection = DepartmentLogProjection.class)
public interface DepartmentLogRepository extends LogRepository<DepartmentLog> {

}