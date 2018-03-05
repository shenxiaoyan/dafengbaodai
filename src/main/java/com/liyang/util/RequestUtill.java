package com.liyang.util;

import javax.servlet.http.HttpServletRequest;

import com.liyang.domain.platform.Platform;
import com.liyang.domain.platform.PlatformRepository;

/**
 * @author Administrator
 *
 */
public class RequestUtill {
	public static Platform getMobileAppPlatform(HttpServletRequest request,PlatformRepository platformRepository) {
		String applicationId = request.getHeader("applicationId");
		Platform platform=platformRepository.findByApplicationId(applicationId);  
		return platform;
	}

}
