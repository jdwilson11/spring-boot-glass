package org.jdw.service;

import java.io.IOException;

import org.jdw.service.google.AuthUtil;
import org.jdw.service.google.MirrorClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.mirror.model.TimelineItem;

@Service
public class TimelineService {

	@Autowired
	private AuthUtil authUtil;

	@Autowired
	private OAuthSession session;

	/**
	 * @return The OAuth {@link Credential} for the current user.
	 */
	private Credential credential() throws IOException {
		String currentUserId = session.getUserId();
		Credential credential = authUtil.getCredential(currentUserId);
		return credential;
	}

	public void submitText(String text) throws IOException {
		TimelineItem timelineItem = new TimelineItem();
		timelineItem.setText(text);
		MirrorClient.addDeleteMenuItem(timelineItem);
		MirrorClient.insertTimelineItem(credential(), timelineItem);
	}
}
