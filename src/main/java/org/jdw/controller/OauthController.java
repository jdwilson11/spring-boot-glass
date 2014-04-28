package org.jdw.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jdw.service.OAuthSession;
import org.jdw.service.google.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;

@Controller
@RequestMapping(OauthController.OAUTH_CONTROLLER_MAPPING)
public class OauthController {

	private static final Logger logger = Logger.getLogger(OauthController.class.getSimpleName());

	public static final String OAUTH_CONTROLLER_MAPPING = "/oauth2callback";

	@Autowired
	private AuthUtil authUtil;

	@Autowired
	private OAuthSession session;

	@RequestMapping("")
	public String index(HttpServletRequest req, String code, String state, String error) throws IOException {
		String currentControllerUrl = req.getRequestURL().toString();

		// If something went wrong, log the error message.
		if (error != null) {
			logger.severe("Something went wrong during auth: " + error);
			return "Something went wrong during auth. Please check your log for details";
		}

		if (StringUtils.isBlank(code)) {
			// Initiate a new flow.
			logger.info("No auth context found. Kicking off a new auth flow.");

			// Redirect to Google's OAuth permission grant page, passing this
			// server's URL as a parameter
			AuthorizationCodeFlow flow = authUtil.newAuthorizationCodeFlow();
			GenericUrl url = flow.newAuthorizationUrl().setRedirectUri(currentControllerUrl);
			// the user will only see the consent page the first time they go
			// through the sequence
			url.set("approval_prompt", "auto");
			return "redirect:" + url.build();

		} else {
			// If we have a code, finish the OAuth 2.0 dance
			logger.info("Got a code. Attempting to exchange for access token.");

			AuthorizationCodeFlow flow = authUtil.newAuthorizationCodeFlow();
			TokenResponse tokenResponse = flow.newTokenRequest(code).setRedirectUri(currentControllerUrl).execute();

			// Extract the Google User ID from the ID token in the auth response
			String userId = ((GoogleTokenResponse) tokenResponse).parseIdToken().getPayload().getSubject();

			logger.info("Code exchange worked. User " + userId + " logged in.");

			// Set it into the session
			session.setUserId(userId);
			flow.createAndStoreCredential(tokenResponse, userId);

			// Redirect back to index
			return "redirect:" + IndexController.INDEX_CONTROLLER_MAPPING;
		}

	}
}