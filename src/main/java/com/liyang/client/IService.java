package com.liyang.client;

/**
 * 
 * @author Administrator
 *
 */
public interface IService {
	/**
	 * 获取url
	 * 
	 * @return the partUrl
	 */
	String getPartUrl();

	/**
	 * 设置url
	 * 
	 * @param partUrl
	 */
	void setPartUrl(String partUrl);

	/**
	 * 获取接口
	 * @return the client
	 */
	IClient getClient();

	/**
	 * 得到请求的地址
	 * 
	 * @return
	 */
	String getUrl();

	/**
	 * 设置接口
	 * 
	 * @param client
	 */
	void setClient(IClient client);

	/**
	 * 调用service方法
	 * @param generalMessage
	 * @return
	 * @throws Exception
	 */
	IResult callService(IMessage generalMessage) throws Exception;

	/**
	 * 数据处理
	 * @param response
	 * @param generalMessage
	 * @return
	 * @throws Exception
	 */
	IResult parseResult(Object response, IMessage generalMessage) throws Exception;

	/**
	 * 数据处理
	 * @param message
	 * @return
	 * @throws Exception
	 */
	Object parseParameters(IMessage message) throws Exception;

	/**
	 * 数据处理
	 * @param message
	 * @return
	 * @throws Exception
	 */
	Object parseHeaders(IMessage message) throws Exception;
}
