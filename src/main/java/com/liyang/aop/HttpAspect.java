package com.liyang.aop;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.liyang.controller.QueryLatestPolicyController;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.platform.Platform;
import com.liyang.service.AuthorityJudgeService;
import com.liyang.util.FailReturnObject;

/**
 * 
 * @author Administrator
 *
 */

@Aspect
@Component
public class HttpAspect {
	
	@Autowired
	AuthorityJudgeService authorityJudgeService;

	private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);
	
//	/*duplicated code*/
///*	@Before("execution(public * com.sboot.app.controller.GirlController.*(..))")
//	public void doBeforelogin(){
//		System.out.println("u r here...before");
//	}
//	
//	@After("execution(public * com.sboot.app.controller.GirlController.*(..))")
//	public void doAfterlogin(){
//		System.out.println("u r here...after");
//	}*/
//	
//	@Pointcut("execution(public * com.sboot.app.controller.GirlController.*(..))")
//	public void login(){
//		System.out.println("u r here...pointcut login");   // no print out 
//	}
//	
//	@Before("login()")
//	public void doBeforelogin(JoinPoint joinPoint){
//		System.out.println("u r here...before");
//		//url
//		ServletRequestAttributes requestAttributes  = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//		HttpServletRequest httpServletRequest= requestAttributes.getRequest();
//		
//		System.out.println("httpServletRequest.getRequestURL(): " + httpServletRequest.getRequestURL());
//		//method
//		System.out.println("httpServletRequest.getMethod(): " +httpServletRequest.getMethod());
//		//ip
//		System.out.println("httpServletRequest.getRemoteAddr(): "+httpServletRequest.getRemoteAddr());
//		// class method
//		System.out.println("joinPoint.getSignature().getDeclaringTypeName(): " + joinPoint.getSignature().getDeclaringTypeName() 
//				+ ",joinPoint.getSignature().getDeclaringType(): " + joinPoint.getSignature().getDeclaringType() 
//				+",joinPoint.getSignature().getName():"+joinPoint.getSignature().getName());
//		// parameters
//		System.out.println("joinPoint.getArgs()" + joinPoint.getArgs());
//		
//	}
//	@After("login()")
//	public void doAfterlogin(){
//		System.out.println("u r here...after");
//	}
//	
//	/*get the object after done the pointcut(advice) method/*/
//	@AfterReturning(returning="object",pointcut="login()")
//	public void doAfterReturninglogin(Object object){
//		System.out.println("u r here...AfterReturning object:" + object);
//	}
	
	
	
	@Pointcut("execution(public * com.liyang.controller.*.mobile*(..))")

	public void mobileAPI(){
		// no print out
		logger.info("u r here...pointcut mobileAPI");    
	}
	
	@Before("mobileAPI()")
	public void doBeforeMobileAPI(JoinPoint joinPoint) throws Exception{
		logger.info("doBeforeMobileAPI");
		//url
		ServletRequestAttributes requestAttributes  = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest httpServletRequest= requestAttributes.getRequest();
		
		String token = httpServletRequest.getHeader("token");
		String applicationId = httpServletRequest.getHeader("applicationId");
		
		// 判断平台是否具有访问权
		Platform platform = 
				authorityJudgeService.authorityJudge(applicationId);
		
		// 判断token,和用户是否具有访问权
		Customer customer = 
				authorityJudgeService.tokenJudge(token);
		
//		Map<String, Object> retrunMap = new HashMap<String,Object>();
//		retrunMap.put("platform", platform);
//		retrunMap.put("customer", customer);
		
//		return retrunMap;
//		System.out.println("httpServletRequest.getRequestURL(): " + httpServletRequest.getRequestURL());
//		//method
//		System.out.println("httpServletRequest.getMethod(): " +httpServletRequest.getMethod());
//		//ip
//		System.out.println("httpServletRequest.getRemoteAddr(): "+httpServletRequest.getRemoteAddr());
//		// class method
//		System.out.println("joinPoint.getSignature().getDeclaringTypeName(): " + joinPoint.getSignature().getDeclaringTypeName() 
//				+ ",joinPoint.getSignature().getDeclaringType(): " + joinPoint.getSignature().getDeclaringType() 
//				+",joinPoint.getSignature().getName():"+joinPoint.getSignature().getName());
//		// parameters
//		System.out.println("joinPoint.getArgs()" + joinPoint.getArgs());
//		
	}
	
////	/*get the object after done the pointcut(advice) method/*/
//	@AfterReturning(returning="object",pointcut="mobileAPI()")
//	public void doAfterReturninglogin(Object object){
//		System.out.println("u r here...AfterReturning object:" + object);
//	}

}
