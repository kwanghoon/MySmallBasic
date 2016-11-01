package com.coducation.smallbasic;

public class DoubleV extends Value {
	public DoubleV(double value) {
		this.value = value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}
	
	@Override
	public boolean equals(Object arg0) {
		if(arg0 instanceof DoubleV)
			return this.value == ((DoubleV) arg0).value;
		else
			return false;
	}
	
	private double value;
}
