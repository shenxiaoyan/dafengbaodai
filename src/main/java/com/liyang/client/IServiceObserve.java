package com.liyang.client;

import java.util.List;

/**
 * 
 * @author Administrator
 *
 */
public interface IServiceObserve {
	/**
	 * 服务执行前通知
	 * 
	 * @param message
	 * @param result
	 * @return
	 */
	IMessage notifyBeforeCall(IMessage message);

	/**
	 * 服务执行后通知
	 * 
	 * @param message
	 * @param result
	 * @param exception
	 * @return
	 */
	IResult notifyAfterCall(IMessage message, IResult result);

	/**
	 * 异常发生时通知
	 * 
	 * @param message
	 * @param result
	 * @param exception
	 */
	void notifyException(IMessage message, IResult result, Exception exception);

	/**
	 * 添加观察者
	 * 
	 * @param observe
	 */
	void addObserve(IServiceObserve observe);

	/**
	 * 清空所有观察者
	 */
	void clearObserves();

	/**
	 * 已添加观察者数量
	 * 
	 * @return
	 */
	int countObserves();

	/**
	 * 所有的观察者列表
	 * 
	 * @return
	 */
	List<IServiceObserve> getObserves();
}
