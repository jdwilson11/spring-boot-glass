package org.jdw.util;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OAuthResources {

	/**
	 * Set this up at https://code.google.com/apis/console/
	 */
	@Value("${oauth.google.clientid}")
	private String clientId;

	/**
	 * Set this up at https://code.google.com/apis/console/
	 */
	@Value("${oauth.google.clientsecret}")
	private String clientSecret;

	private final String oauthUrl = "https://www.googleapis.com/oauth2/v1/userinfo";

	public final List<String> scope = Arrays.asList("https://www.googleapis.com/auth/userinfo.profile",
			"https://www.googleapis.com/auth/userinfo.email", "https://www.googleapis.com/auth/glass.timeline");

	public String getClientId() {
		return clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public String getOauthUrl() {
		return oauthUrl;
	}

	public List<String> getScope() {
		return scope;
	}

}
