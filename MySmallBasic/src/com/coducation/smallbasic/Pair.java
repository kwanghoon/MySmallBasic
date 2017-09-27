package com.coducation.smallbasic;

public class Pair extends ListV {
	
	private Value firstElem;	
	private ListV secondElem;

	public Pair(Value firstElem, ListV secondElem) {
		this.firstElem = firstElem;
		this.secondElem = secondElem;
	}
	
	@Override
	public boolean isNull() {
		return false;
	}

	@Override
	public boolean isPair() {
		return true;
	}

	@Override
	public Value first() {
		return firstElem;
	}

	@Override
	public ListV second() {
		return secondElem;
	}

	@Override
	public int length() {
		
		int length = 0;
		ListV list = this;
		
		while (list.isPair()) {
			length++;
			list = list.second();
		}
		
		return length;
	}
}
