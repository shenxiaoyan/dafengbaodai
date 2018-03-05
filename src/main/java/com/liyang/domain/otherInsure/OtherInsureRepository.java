package com.liyang.domain.otherInsure;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.liyang.domain.base.AuditorEntityRepository;

/**
 * 其他保险
 * @author Administrator
 *
 */
public interface OtherInsureRepository extends AuditorEntityRepository<OtherInsure>{
	
}
