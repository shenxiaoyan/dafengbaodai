package com.liyang.domain.webadvertisetype;

import java.util.List;

import com.liyang.domain.base.AuditorEntityRepository;

public interface WebAdvertiseTypeRepository extends AuditorEntityRepository<WebAdvertiseType>{
	
	public WebAdvertiseType findByTypeCode(String typeCode);
	
	public List<WebAdvertiseType> findByStateStateCode(String stateCode);
	
}
