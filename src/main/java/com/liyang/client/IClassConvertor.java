package com.liyang.client;

/**
 * 
 * @author Administrator
 *
 */
public interface IClassConvertor {
	/**
	 * 将多个数据转换成一个结果
	 * 
	 * @param objects
	 * @return
	 */
	Object convert(Object... objects);
}
