package org.jdw.controller.interceptors;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.jdw.controller.OauthController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

/**
 * Global exception handler for all controllers.
 */
@ControllerAdvice
class ControllerExceptionHandler {

	private static final Logger logger = Logger.getLogger(ControllerExceptionHandler.class.getSimpleName());

	@ExceptionHandler(TokenResponseException.class)
	public String handleConflict(HttpServletRequest req, TokenResponseException e) throws IOException {
		if (e.getDetails().getError().contains("invalid_grant")) {
			logger.warning("User disabled OAuth 2.0 access. Attempting to re-authenticate.");
			return "redirect:" + OauthController.OAUTH_CONTROLLER_MAPPING;
		} else {
			throw e;
		}
	}

	@ExceptionHandler(GoogleJsonResponseException.class)
	public String handleConflict(HttpServletRequest req, GoogleJsonResponseException e) throws IOException {
		logger.warning("User needs to log in via Google again. Attempting to re-authenticate.");
		return "redirect:" + OauthController.OAUTH_CONTROLLER_MAPPING;
	}

}