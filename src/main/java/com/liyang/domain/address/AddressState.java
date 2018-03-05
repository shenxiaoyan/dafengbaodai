package com.liyang.domain.address;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorState;
/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "address_state")
@Cacheable
public class AddressState extends AbstractAuditorState<Address, AddressAct> {
	
	public AddressState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public AddressState() {
		super();
	}
}
