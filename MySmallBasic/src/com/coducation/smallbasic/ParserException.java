package com.coducation.smallbasic;

/*
 *  message format : e.g., Line 1 : Char 11 : Parsing error
 */
public class ParserException extends RuntimeException {
	private String message;
	private String parseInfo;
	private int linenum;
	private int colnum;
	public ParserException(String message, String parseInfo, int linenum, int colnum) {
		this.message = message;
		this.parseInfo = parseInfo;
		this.linenum = linenum;
		this.colnum = colnum;
	}
	public String getMessage() {
		return message;
	}
	public String getParseInfo() {
		return parseInfo;
	}
	public int getLinenum() {
		return linenum;
	}
	public int getColnum() {
		return colnum;
	}
}
