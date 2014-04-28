package org.jdw.config;

import org.jdw.controller.OauthController;
import org.jdw.controller.interceptors.OauthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private OauthInterceptor oauthInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(oauthInterceptor).addPathPatterns("/**")
				.excludePathPatterns(OauthController.OAUTH_CONTROLLER_MAPPING + "/**");
	}

}