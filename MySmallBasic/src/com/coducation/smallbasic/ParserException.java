package com.coducation.smallbasic;

/*
 *  message format : e.g., Line 1 : Char 11 : Parsing error
 */
public class ParserException extends RuntimeException {
	private String message;
	private String parseInfo;
	public ParserException(String message, String parseInfo) {
		this.message = message;
		this.parseInfo = parseInfo;
	}
	public String getMessage() {
		return message;
	}
	public String getParseInfo() {
		return parseInfo;
	}
}
