package com.coducation.smallbasic.codegen;

public class CodeGenException extends RuntimeException {
	
	private String message;
	private boolean ProgramEnd;

	public CodeGenException(String message) {
		this.message = message;
		this.ProgramEnd = false;
	}
	
	public String getMessage() {
		return message;
	}

}
