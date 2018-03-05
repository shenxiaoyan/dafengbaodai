package com.liyang.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
//将wdsjsh 中有关person的字段保存在person中
@Target({METHOD, FIELD,ElementType.TYPE})
@Retention(RUNTIME)

public @interface Infos {
	Info[] value();  
}
