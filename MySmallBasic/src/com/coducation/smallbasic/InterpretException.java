package com.coducation.smallbasic;

public class InterpretException extends RuntimeException {
	public InterpretException(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	
	private String message;
}
