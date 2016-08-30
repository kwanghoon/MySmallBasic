package com.coducation.smallbasic;

public class StrV extends Value {

	public StrV(String str){
		v = str;
	}
	
	public String getValue(){
		return v;
	}
	
	private String v;
}
