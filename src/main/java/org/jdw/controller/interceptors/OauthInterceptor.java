package org.jdw.controller.interceptors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jdw.controller.OauthController;
import org.jdw.service.OAuthSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class OauthInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private ServletContext context;

	@Autowired
	private OAuthSession session;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		// Redirect to OAuth authentication if not signed in.
		// WebSecurityConfig prevents this interceptor from being applied to
		// the OAuth authentication URL to prevent an infinite loop.

		String currentUserId = session.getUserId();
		if (StringUtils.isBlank(currentUserId)) {
			// Stop the chain and return a redirect
			String contextPath = context.getContextPath();
			response.sendRedirect(contextPath + OauthController.OAUTH_CONTROLLER_MAPPING);
			return false;
		}
		// Proceed normally
		return true;
	}
}