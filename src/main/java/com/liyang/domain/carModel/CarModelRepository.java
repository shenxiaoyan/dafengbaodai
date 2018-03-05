package com.liyang.domain.carModel;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 车型
 * @author Administrator
 *
 */
public interface CarModelRepository extends JpaRepository<CarModel, Integer> {
	
	public CarModel findByTradeNo(String tradeNo);

}
