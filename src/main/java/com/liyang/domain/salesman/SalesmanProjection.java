package com.liyang.domain.salesman;

import com.liyang.domain.department.Department;
import org.springframework.data.rest.core.config.Projection;

/**
 * 代理人
 * @author Administrator
 *
 */
@Projection(name = "salesmanProjection",types = {Salesman.class})
public interface SalesmanProjection {
    Integer getId();
    String getName();
    String getPhoneNumber();
    Boolean getEnabled();
    Department getDepartment();
}
