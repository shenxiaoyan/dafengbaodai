package com.liyang.domain.address;


import org.springframework.data.repository.query.Param;

import com.liyang.domain.base.StateRepository;

/**
 * @author Administrator
 *
 */
public interface AddressStateRepository extends StateRepository<AddressState>{

	public AddressState findByStateCode(@Param("stateCode")String stateCode);
}
