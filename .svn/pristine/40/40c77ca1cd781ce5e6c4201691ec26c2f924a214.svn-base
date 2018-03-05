//package com.liyang.filter;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.charset.Charset;
//import java.util.Arrays;
//import java.util.Locale;
//
//import javax.servlet.FilterChain;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.ServletInputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequestWrapper;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mock.web.DelegatingServletInputStream;
//import org.springframework.util.Assert;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.web.util.WebUtils;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.liyang.domain.base.AbstractWorkflowEntity;
//import com.liyang.message.Message;
//
//public class MessageDispatchFilter extends OncePerRequestFilter {
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//
//		HttpServletRequest	methodRequest = request;
//		
//		if(request.getMethod().equals("POST") && request.getRequestURI().equals("/message/dispatch") && request.getAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE) == null) {
//
//			ObjectMapper mapper = new ObjectMapper();
//			Message bodyValue = mapper.readValue(request.getReader(), Message.class);
//			
//			
//			String header = bodyValue.getActCustomContent().getExt().substring(4).trim();
//			String[] split = header.split(" ");
//			String method = split[0].trim();
//			methodRequest = new MethodRequestWrapper(request,method);
//			
//			
//			String uri = split[1].trim();
//			methodRequest = new RequestDispatcherWrapper(methodRequest,uri);
//			
//			Object data = bodyValue.getActCustomContent().getData();
//			String writeValueAsBytes = mapper.writeValueAsString(data);
//			InputStream is = new ByteArrayInputStream(writeValueAsBytes.getBytes(Charset.forName("utf-8")));
//			methodRequest = new BodyRequestWrapper(methodRequest, is);
//			
//		}
//
//		filterChain.doFilter(methodRequest, response);
//		
//	}
//
//
//	/**
//	 * Simple {@link HttpServletRequest} wrapper that returns the supplied method for
//	 * {@link HttpServletRequest#getMethod()}.
//	 */
//	private static class BodyRequestWrapper extends HttpServletRequestWrapper {
//
//		private final ServletInputStream stream;
//
//		public BodyRequestWrapper(HttpServletRequest request, InputStream is) {
//			super(request);
//			this.stream = new DelegatingServletInputStream(is);
//		}
//
//		@Override
//	    public ServletInputStream getInputStream() throws IOException {
//	        return this.stream;
//	    }
//	}
//	private static class MethodRequestWrapper extends HttpServletRequestWrapper {
//
//		private final String method;
//
//		public MethodRequestWrapper(HttpServletRequest request, String method) {
//			super(request);
//			this.method = method.toUpperCase(Locale.ENGLISH);
//		}
//
//		@Override
//		public String getMethod() {
//			return this.method;
//		}
//		
//	}
//		
//	
//	private static class RequestDispatcherWrapper extends HttpServletRequestWrapper {
//
//		private final String uri;
//
//		public RequestDispatcherWrapper(HttpServletRequest request, String uri) {
//			super(request);
//			this.uri = uri;
//		}
//
//		@Override
//		public String getRequestURI() {
//			return this.uri;
//		}
//	
//	}
//
//}
