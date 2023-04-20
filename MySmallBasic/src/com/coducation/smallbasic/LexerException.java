package com.coducation.smallbasic;

/*
 *  message format : e.g., Line 1 : Char 11 : Lexing error
 */

public class LexerException extends RuntimeException {
	private String message;
	private int linenum;
	private int colnum;
	
	public LexerException(String message, int linenum, int colnum) {
		this.message = message;
		this.linenum = linenum;
		this.colnum = colnum;
	}
	public String getMessage() {
		return message;
	}
	public int getLinenum() {
		return linenum;
	}
	public int getColnum() {
		return colnum;
	}
}
