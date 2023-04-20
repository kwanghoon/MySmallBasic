package com.coducation.smallbasic.util;

public class Pair<K, V> {
	public Pair(K fst, V sec) {
		this.fst = fst;
		this.sec = sec;
	}
	
	public boolean equals(Object o) {
		
		return true;
	}
	
	public K getFst() {
		return fst;
	}
	
	public V getSec() {
		return sec;
	}
	
	public String toString() {
		return null;
	}
	
	private K fst;
	private V sec;
}
