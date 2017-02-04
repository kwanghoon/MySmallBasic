package com.coducation.smallbasic;

/*
 *  message format : e.g., Line 1 : Char 11 : Lexing error
 */

public class LexerException extends RuntimeException {
	private String message;
	public LexerException(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
