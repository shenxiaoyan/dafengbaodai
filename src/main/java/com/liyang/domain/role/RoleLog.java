package com.liyang.domain.role;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorLog;
/**
 * 规则log
 * @author Administrator
 *
 */
@Entity
@Table(name = "role_log")
public class RoleLog extends AbstractAuditorLog<Role,RoleState,RoleAct> {

}
