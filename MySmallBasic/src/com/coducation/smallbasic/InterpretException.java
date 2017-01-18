package com.coducation.smallbasic;

public class InterpretException extends RuntimeException {
	public InterpretException(String message) {
		this.message = message;
		this.ProgramEnd = false;
	}
	public InterpretException(String message, boolean programend) {
		this.message = message;
		this.ProgramEnd = programend;
	}
	public String getMessage() {
		return message;
	}
	
	public boolean getProgramEnd() {
		return ProgramEnd;
	}
	
	private String message;
	private boolean ProgramEnd;
}
