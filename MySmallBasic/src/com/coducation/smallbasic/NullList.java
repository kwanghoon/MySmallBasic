package com.coducation.smallbasic;

public class NullList extends ListV {

	@Override
	public boolean isNull() {
		return true;
	}

	@Override
	public boolean isPair() {
		return false;
	}

	@Override
	public Value first() {
		return null;
	}

	@Override
	public ListV second() {
		return null;
	}

	@Override
	public int length() {
		return 0;
	}
	
	
	
}
