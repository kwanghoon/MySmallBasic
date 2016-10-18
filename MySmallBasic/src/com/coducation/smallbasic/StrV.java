package com.coducation.smallbasic;

public class StrV extends Value {
	
	public StrV(){	
		v = "";
	}
	
	public StrV(double d){	
		v = d+"";
	}

	public StrV(String str){	
		v = str;
	}
	
	public String getValue(){
		return v;
	}
	
	private String v;
}
