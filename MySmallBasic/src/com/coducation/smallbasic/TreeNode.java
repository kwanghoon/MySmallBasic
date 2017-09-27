package com.coducation.smallbasic;

import java.util.ArrayList;

public class TreeNode extends TreeV {
	
	private Value v;
	private ArrayList<TreeV> child;
	
	public TreeNode(Value v) {
		this.v = v;
		this.child = new ArrayList<TreeV>();
	}
	
	public TreeNode(Value v, ArrayList<TreeV> child) {
		this.v = v;
		this.child = child;
	}
	
	@Override
	public boolean isNull() {
		return false;
	}

	@Override
	public boolean isTree() {
		return true;
	}

	@Override
	public Value value() {
		return v;
	}

	@Override
	public ArrayList<TreeV> getChilds() {
		return child;
	}
	
	@Override
	public TreeV childAt(int i) {
		return child.get(i);
	}

}
