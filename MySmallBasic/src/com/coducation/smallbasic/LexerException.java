package com.coducation.smallbasic;

public class LexerException extends RuntimeException {
	private String message;
	public LexerException(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
