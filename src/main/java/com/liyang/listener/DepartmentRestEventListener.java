package com.liyang.listener;

import org.springframework.stereotype.Component;

import com.liyang.domain.department.Department;
import com.liyang.domain.department.DepartmentAct;
import com.liyang.domain.department.DepartmentLog;
import com.liyang.domain.department.DepartmentState;
/**
 * @author Administrator
 *
 */
@Component
public class DepartmentRestEventListener extends AuditorRestEventListener<Department,DepartmentState,DepartmentAct,DepartmentLog> {

}
