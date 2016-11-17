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
	
	@Override
	public boolean equals(Object arg0) {
		if(arg0 instanceof StrV)
			return this.v == ((StrV) arg0).v;
		else
			return false;
	}
	
	public boolean isNumber() {
		try {
			Double.parseDouble(v);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	public double parseDouble() {
		return Double.parseDouble(v);
	}
	
	public String toString() {
		return v;
	}
	
	private String v;
}
