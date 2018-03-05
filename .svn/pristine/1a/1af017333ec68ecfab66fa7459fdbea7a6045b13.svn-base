package com.liyang.domain.moments;

import com.liyang.domain.base.AuditorEntityRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MomentsRepository extends AuditorEntityRepository<Moments>,JpaSpecificationExecutor<Moments> {
public  Moments findByStateStateCode(@Param("stateCode") String stateCode);
}
