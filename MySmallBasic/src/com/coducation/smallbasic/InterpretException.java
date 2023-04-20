package com.coducation.smallbasic;

public class InterpretException extends RuntimeException {
	public InterpretException(String message) {
		this(message, "", -1, -1);
	}
	public InterpretException(String message, boolean programend) {
		this(message, programend, "", -1, -1);
	}
	public InterpretException(String message, String culprit, int linenum, int colnum) {
		this.message = message;
		this.linenum = linenum;
		this.colnum = colnum;
		this.culprit = culprit;
		this.ProgramEnd = false;
	}
	public InterpretException(String message, boolean programend, String culprit, int linenum, int colnum) {
		this.message = message;
		this.linenum = linenum;
		this.colnum = colnum;
		this.culprit = culprit;
		this.ProgramEnd = programend;
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
	public String getCulprit() {
		return culprit;
	}
	
	public boolean getProgramEnd() {
		return ProgramEnd;
	}
	
	private String message;
	private int linenum;
	private int colnum;
	private String culprit;
	private boolean ProgramEnd;
}
