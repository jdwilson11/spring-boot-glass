package org.jdw.domain;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class Message {

	public final static int MAX_TEXT_LENGTH = 30;

	@NotBlank
	@Size(max = MAX_TEXT_LENGTH)
	private String text;

	public Message() {
	}

	public Message(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
