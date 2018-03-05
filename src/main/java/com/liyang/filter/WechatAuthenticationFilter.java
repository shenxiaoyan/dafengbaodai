package com.liyang.filter;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyang.domain.user.User;
import com.liyang.service.WechatLoginService;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
public class WechatAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	// ~ Static fields/initializers
	// =====================================================================================
	private RememberMeServices rememberMeServices = new NullRememberMeServices();
	@Autowired
	private WechatLoginService wechatLoginService;
	@Autowired
	private CacheManager cacheManager;
	private boolean postOnly = false;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	// ~ Constructors
	// ===================================================================================================

	public WechatAuthenticationFilter() {
		super(new AntPathRequestMatcher("/wechatLogin"));
	}

	// ~ Methods
	// ========================================================================================================
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> hashMap = new HashMap<>();

		try {
			if (request.getParameter("code") == null || request.getParameter("state") == null) {
				throw new FailReturnObject(170, "格式不符合");
			}
			Authentication authRequest = wechatLoginService.authorize(request.getParameter("code"),
					request.getParameter("state"));
			// applicationContext.publishEvent(new
			// UserLoginSuccessEvent(authRequest));

			if (authRequest != null) {
				if (authRequest.getCredentials()!=null) {
					if (authRequest instanceof UsernamePasswordAuthenticationToken) {
						UsernamePasswordAuthenticationToken token=(UsernamePasswordAuthenticationToken) authRequest;
						
						User user=(User) token.getPrincipal();
						hashMap.put("message", user.getId());
						System.out.println(user);
						response.getWriter().write(mapper.writeValueAsString(hashMap));
					}
				}
				return authRequest;
			} else {
				hashMap.put("ActionStatus", "OK");
				hashMap.put("ErrorCode", 0);
				hashMap.put("ErrorInfo", "");
				String json = mapper.writeValueAsString(hashMap);
				response.setContentType("application/json;charset=utf-8");
				response.setCharacterEncoding("UTF-8");
				response.sendRedirect("/#/workflowEntity/users/state/APPLY");
				response.getWriter().write(json);
				return null;
			}

		} catch (FailReturnObject failReturnObject) {

			hashMap.put("ActionStatus", failReturnObject.getActionStatus());
			hashMap.put("ErrorCode", failReturnObject.getErrorCode());
			hashMap.put("ErrorInfo", failReturnObject.getErrorInfo());

			String json = mapper.writeValueAsString(hashMap);
			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			return null;
		}

	}

	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	/**
	 * Defines whether only HTTP POST requests will be allowed by this filter.
	 * If set to true, and an authentication request is received which is not a
	 * POST request, an exception will be raised immediately and authentication
	 * will not be attempted. The <tt>unsuccessfulAuthentication()</tt> method
	 * will be called as if handling a failed authentication.
	 * <p>
	 * Defaults to <tt>true</tt> but may be overridden by subclasses.
	 */
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// TODO Auto-generated method stub

		if (logger.isDebugEnabled()) {
			logger.debug("Authentication success. Updating SecurityContextHolder to contain: "
					+ authResult);
		}

		SecurityContextHolder.getContext().setAuthentication(authResult);

		rememberMeServices.loginSuccess(request, response, authResult);

		// Fire event
		if (this.eventPublisher != null) {
			eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(
					authResult, this.getClass()));
		}
		String str = cacheManager.getCache("wechatLogin").get(request.getParameter("state"), String.class);
		if(str!=null && str.contains("wechat")){
			return;
		}
		getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
	}
}
