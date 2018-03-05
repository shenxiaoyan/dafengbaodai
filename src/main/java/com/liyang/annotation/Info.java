package com.liyang.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * @author Administrator
 *
 */
//将wdsjsh 中有关person的字段保存在person中
@Target({ METHOD, FIELD, ElementType.TYPE })
@Retention(RUNTIME)
@Repeatable(Infos.class)
public @interface Info {

	String label() default ""; //字段或本身的名称

	String tip() default "";   //悬停提示

	String placeholder() default "";  //input里的默认字符

	String help() default ""; //字段help
	
	String secret() default ""; //开发者备注


	FLAG flag() default FLAG.self;// 区分boolean中的true false self

	public enum FLAG {
		True, False, self
	}

}
