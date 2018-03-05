package com.liyang.domain.address;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorLog;
/**
 * @author Administrator
 *
 */
@Entity
@Table(name="address_log")
public class AddressLog extends AbstractAuditorLog<Address,AddressState,AddressAct>{

}
