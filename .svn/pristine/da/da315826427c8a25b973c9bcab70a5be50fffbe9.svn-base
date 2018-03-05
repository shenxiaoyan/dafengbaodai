package com.liyang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyang.domain.platform.Platform;
import com.liyang.domain.platform.PlatformRepository;

/**
 * platform相关服务
 * 
 * @author huanghengkun
 * @create 2017年10月16日
 */
@Service
public class PlatformService {
	@Autowired
	PlatformRepository platformRepository;

	public String getPlatformVersion(String applicationId) {
		Platform platform = platformRepository.findByApplicationId(applicationId);
		if (platform == null) {
			return "";
		}
		return platform.getAppVersion();
	}
}
