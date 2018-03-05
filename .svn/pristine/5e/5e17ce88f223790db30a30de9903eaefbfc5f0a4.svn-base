package com.liyang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.liyang.aop.SpringSecurityAuditorAware;

/**
 * @author Administrator
 *
 */
@Configuration
@EnableJpaAuditing
public class AuditorConfig {
	
	@Bean
	  public SpringSecurityAuditorAware auditorProvider() {
	    return new SpringSecurityAuditorAware();
	  }

}
