package com.liyang.config;

import com.liyang.service.UserService;
import com.liyang.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.liyang.domain.user.UserRepository;
import com.liyang.filter.WechatAuthenticationFilter;
import com.liyang.handle.ErrorAuthenticationFailureHandler;
import com.liyang.handle.GlobalAccessDeniedHandler;

/**
 * @author Administrator
 *
 */
@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public WechatAuthenticationFilter wechatAuthenticationFilter() {
        WechatAuthenticationFilter upaf = new WechatAuthenticationFilter();
        upaf.setAuthenticationManager(new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return authentication;
            }
        });
        return upaf;
    }


//	@Bean
//	public MessageDispatchFilter dispatchFilter() {
//		MessageDispatchFilter dispatch = new MessageDispatchFilter();
//		return dispatch;
//	}


//    @Bean
//    public AuthenticationManager authenticationManager() {
//        return new AuthenticationManager() {
//
//            @Override
//            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//
//                return authentication;
//            }
//        };
//    }
//
    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();

    }

    /**
     * 主过滤器
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(wechatAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//		http.addFilterBefore(dispatchFilter(), UsernamePasswordAuthenticationFilter.class);
        http.headers().frameOptions().disable();
        http
                .csrf().disable().anonymous().authorities("USER")
                .and()
                .authorizeRequests().antMatchers("/wechatLogin", "/login","/rest", "/rest/*", "/rest/*/", "/rest/*States",
                		"/rest/*Acts", "/rest/*Logs", "/rest/*Files", "/rest/*Workflows","/css/**",
                		"/dafeng/*","/dafeng/**","/dafeng/user/*","/rest/**", "/employeeApply","/rest/***",
                		"/invite/**","/share/**","/teamInvite/**","/question/**", "/businessCard/**")
                .permitAll()
                .and()
//		.authorizeRequests().antMatchers("/rest","/rest/*","/rest/*/")
////	.hasAuthority("ADMINISTRATOR")
//		.and()
//		.exceptionHandling().accessDeniedHandler(new GlobalAccessDeniedHandler())
//		.and()
//		.authorizeRequests().antMatchers( "/connect", "/login").permitAll()
//		.anyRequest().authenticated()
//		.and().formLogin().loginPage("/connect").permitAll()
//		.and().logout().permitAll();
//                .authorizeRequests().antMatchers("/rest", "/rest/*", "/rest/*/", "/rest/*States", "/rest/*Acts", "/rest/*Logs", "/rest/*Files", "/rest/*Workflows")
//                .hasAuthority("ADMINISTRATOR")
//                .and()
                .authorizeRequests().anyRequest().authenticated()   //拦截
                .and()
                .exceptionHandling().accessDeniedHandler(new GlobalAccessDeniedHandler())
                .and()
//                .authorizeRequests().antMatchers().permitAll()
//                .and()
                .formLogin().failureHandler(new ErrorAuthenticationFailureHandler()).defaultSuccessUrl("/#/dashboard", true)
                .loginPage("/login").permitAll()
                .and()
                .rememberMe().tokenValiditySeconds(60 * 60 * 24 * 30)
                .and()
                .sessionManagement().invalidSessionUrl("/login?expired").maximumSessions(1).expiredUrl("/login?expired").sessionRegistry(sessionRegistry())//检测超时，注入sessionRegistry
                .and().and()
                .logout().deleteCookies("JESSIONID").permitAll();//注意，如果你使用了检测会话超时的机制， 它可能在用户注销后没有关闭浏览器又再次登录时发出错误报告。 这时因为会话cookie没有被清除，当你销毁会话时， 并将重新提交，如果用户已经注销。 你可能需要在注销时特别删除JSESSIONID cookie
    }

    @Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

    @Bean
    UserDetailsService customUserService() {
        return new UserService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService()).passwordEncoder(new PasswordEncoder() {

            @Override
            public String encode(CharSequence rawPassword) {
                return MD5Util.encode((String) rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(MD5Util.encode((String) rawPassword));
            }
        });
    }
}
