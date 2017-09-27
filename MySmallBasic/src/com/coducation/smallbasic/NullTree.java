package com.coducation.smallbasic;

import java.util.ArrayList;

public class NullTree extends TreeV {
	
	@Override
	public boolean isNull() {
		return true;
	}

	@Override
	public boolean isTree() {
		return false;
	}

	@Override
	public Value value() {
		return null;
	}
	
	@Override
	public ArrayList<TreeV> getChilds() {
		return null;
	}

	@Override
	public TreeV childAt(int i) {
		return null;
	}
	
}
