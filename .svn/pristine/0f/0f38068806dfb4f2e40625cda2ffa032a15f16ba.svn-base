package com.liyang.controller;

import javax.servlet.http.HttpServletRequest;

import com.liyang.client.IMessage;
import com.liyang.client.IResult;
import com.liyang.domain.api.tianan.IApiParams;
import com.liyang.helper.ResponseBody;

/**
 * 调用api服务的控制器
 * 
 * @author huanghengkun
 * @create 2017年11月24日
 */
public interface IApiController {

	
	/**
	 * 根据前端提交的参数构造message
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public IMessage buildMessage(IApiParams params, HttpServletRequest request) throws Exception;

	
	/**
	 * 将返回的result封装在ResponseBody里
	 * @param result
	 * @return
	 * @throws Exception
	 */
	public ResponseBody response(IResult result) throws Exception;
}
