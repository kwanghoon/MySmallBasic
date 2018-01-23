package com.coducation.smallbasic;

public abstract class ListV {
	
	public abstract boolean isNull();
	public abstract boolean isPair();
	public abstract Value first();
	public abstract ListV second();
	public abstract int length();
	
	public ListV concat(ListV l) {
		
		if (this.isNull()) {
			return l;
		}
		else {
			return new Pair(this.first(), this.second().concat(l));
		}
		
	}
	
	public void print() {
		System.out.print("[");
		ListV l = this;
		while (l.isPair()) {
			System.out.print(l.first());
			System.out.print(l.second().isNull() ? "" : ",");
			l = l.second();
		}
		System.out.println("]");
	}
	
}


