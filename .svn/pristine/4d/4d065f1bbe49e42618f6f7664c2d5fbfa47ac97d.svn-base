package com.liyang.handle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ReturnObjectImpl;

/**
 * @author Administrator
 *
 */
public class GlobalAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		ReturnObjectImpl returnObjectImpl = new ReturnObjectImpl();
		returnObjectImpl.setActionStatus("FAIL");
		returnObjectImpl.setErrorCode(500);
		returnObjectImpl.setErrorInfo("没有权限");
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(mapper.writeValueAsString(returnObjectImpl));

	}

}
