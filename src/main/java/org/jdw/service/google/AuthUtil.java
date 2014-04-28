/*
 * Copyright (C) 2013 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.jdw.service.google;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.jdw.util.OAuthResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.MemoryDataStoreFactory;

/**
 * A collection of utility functions that simplify common authentication and
 * user identity tasks
 * <p>
 * Modified from https://developers.google.com/glass/quickstart/java
 * 
 * @author Jenny Murphy - http://google.com/+JennyMurphy
 */
@Component
public class AuthUtil {

	@Autowired
	private OAuthResources resources;

	private DataStore<StoredCredential> store;

	@PostConstruct
	public void initializeDataStore() throws IOException {
		store = MemoryDataStoreFactory.getDefaultInstance().getDataStore("glass");
	}

	/**
	 * Creates and returns a new {@link AuthorizationCodeFlow} for this app.
	 */
	public AuthorizationCodeFlow newAuthorizationCodeFlow() throws IOException {

		String clientId = resources.getClientId();
		String clientSecret = resources.getClientSecret();

		return new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(), new JacksonFactory(), clientId, clientSecret,
				resources.getScope()).setAccessType("offline").setCredentialDataStore(store).build();
	}

	public Credential getCredential(String userId) throws IOException {
		if (userId == null) {
			return null;
		} else {
			return newAuthorizationCodeFlow().loadCredential(userId);
		}
	}

}
