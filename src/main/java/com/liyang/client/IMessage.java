package com.liyang.client;

/**
 * 
 * @author Administrator
 *
 */
public interface IMessage extends IValidatable {
	/**
	 * 为消息中的属性设置默认值,特别是非必填字段
	 * 
	 * @throws Exception
	 */
	void initDefaultValue() throws Exception;
}
