package com.coducation.smallbasic;

import java.util.ArrayList;

public abstract class TreeV {
	
	abstract public boolean isNull();
	abstract public boolean isTree();
	abstract public Value value();
	abstract public ArrayList<TreeV> getChilds();
	abstract public TreeV childAt(int i);
	
	public void print() {
		
		TreeV t = this;
		
		if ( t.isTree() ) {
			
			System.out.print( "(" + t.value() );
			
			ArrayList<TreeV> child = t.getChilds();
			
			for (int i = 0; i < child.size(); i++) {
				
				if ( i == 0 ) {
					System.out.print(":");
				}
				
				child.get(i).print();
				
			}
			
			System.out.print(")");
			
		}

	}
	
}
