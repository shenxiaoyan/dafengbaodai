package com.liyang.aop;

import org.hamcrest.core.IsInstanceOf;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.liyang.domain.user.User;
import com.liyang.util.CommonUtil;

/**
 * 
 * @author Administrator
 *
 */
public class SpringSecurityAuditorAware implements AuditorAware<User> {

	@Override
	public User getCurrentAuditor() {
		return CommonUtil.getPrincipal();
	}
}