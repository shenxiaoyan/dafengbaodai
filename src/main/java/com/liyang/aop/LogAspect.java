package com.liyang.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.service.AbstractAuditorService;

/**
 * log记录切面aop
 * 
 * @author huanghengkun
 * @create 2017年12月21日
 */
@Aspect
@Component
public class LogAspect {

	private static final String ACT_CODE_UPDATE = "update";

	private static final String ACT_CODE_CREATE = "create";

	private static final String ACT_CODE_DELETE = "delete";

	@Around("execution(public * com.liyang.service.*.update*(..))")
	public Object updateAround(ProceedingJoinPoint pjd) throws Throwable {
		before(pjd, ACT_CODE_UPDATE);
		Object[] args = pjd.getArgs();
		Object result = pjd.proceed(args);
		after(pjd);
		return result;
	}

	@Around("execution(public * com.liyang.service.*.save*(..))")
	public Object saveAround(ProceedingJoinPoint pjd) throws Throwable {
		before(pjd, ACT_CODE_CREATE);
		Object[] args = pjd.getArgs();
		Object result = pjd.proceed(args);
		after(pjd);
		return result;
	}

	@Around("execution(public * com.liyang.service.*.delete*(..))")
	public Object deleteAround(ProceedingJoinPoint pjd) throws Throwable {
		before(pjd, ACT_CODE_DELETE);
		Object[] args = pjd.getArgs();
		Object result = pjd.proceed(args);
		after(pjd);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void before(ProceedingJoinPoint pjd, String lastActCode) {
		System.out.println("---------------before logAspect---------------");
		if (pjd.getThis() instanceof AbstractAuditorService
				&& pjd.getThis().getClass() != AbstractAuditorService.class) {
			AbstractAuditorService auditorService = (AbstractAuditorService) pjd.getThis();
			Object[] args = pjd.getArgs();
			Arrays.stream(args).filter(e -> e instanceof AbstractAuditorEntity)
					.forEach(e -> auditorService.prepareForBuildLog((AbstractAuditorEntity) e, lastActCode));
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void after(ProceedingJoinPoint pjd) {
		System.out.println("---------------after logAspect---------------");
		if (pjd.getThis() instanceof AbstractAuditorService) {
			AbstractAuditorService auditorService = (AbstractAuditorService) pjd.getThis();
			Object[] args = pjd.getArgs();
			Arrays.stream(args).filter(e -> e instanceof AbstractAuditorEntity)
					.forEach(e -> auditorService.createLog((AbstractAuditorEntity) e));
		}
	}

}
