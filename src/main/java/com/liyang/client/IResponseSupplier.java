package com.liyang.client;

/**
 * 
 * @author Administrator
 *
 */
public interface IResponseSupplier {

	/**
	 * 得到响应结果
	 * 
	 * @param generalMessage
	 * @return
	 * @throws Exception
	 */
	Object getResponse(IMessage generalMessage) throws Exception;

	/**
	 * 获取使用的请求的服务
	 * 
	 * @return
	 */
	IService getService();

	/**
	 * 设置使用的请求服务
	 * 
	 * @param service
	 * @return
	 */
	IResponseSupplier setService(IService service);

}