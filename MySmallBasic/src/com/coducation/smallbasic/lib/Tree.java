package com.coducation.smallbasic.lib;

import java.util.ArrayList;
import java.util.HashMap;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Tree {
	
	private static HashMap<String, Node> tree_map = new HashMap<>();
	
	private static int key = 1 ;
	
	public static Value Tree(ArrayList<Value> args) {
		
		if (args.size() == 1) {
			
			Node node = new Node(args.get(0));
			tree_map.put("Tree" + key, node);

		} else if (args.size() > 1) {
			
			Node node = new Node(args.get(0));
			
			for ( int i = 1 ; i < args.size() ; i++ ) {
				
				node.child.add(new Node(args.get(i)));
				
			}
			
			tree_map.put("Tree" + key, node);
			
		} else {
			
			throw new InterpretException("Tree : Unexpected # of args : " + args.size());
			
		}
		
		return new StrV("Tree" + key++);
	}
	
	public static Value Root(ArrayList<Value> args) {
		
		String str_arg;
		Node node;
		
		Value v;
		
		if (args.size() == 1) {
			
			str_arg = ((StrV) args.get(0)).getValue();
			node = tree_map.get(str_arg);

		} else
			
			throw new InterpretException("Root : Unexpected # of args : " + args.size());
		
		try {
			
			v = node.v;
			
		} catch (Exception e) {
			
			return new StrV("Null");
			
		}
		
		return v;
	}
	
	public static Value ChildAt(ArrayList<Value> args) {
		
		String str_arg;
		double dbl_arg;
		
		Node node;
		int index;
		
		if (args.size() == 2) {
			
			if (args.get(0) instanceof StrV) {
				
				str_arg = ((StrV) args.get(0)).getValue();

			} else {
			
				throw new InterpretException("ChildAt : Unexpected 1st argument");
				
			}

			if (args.get(1) instanceof DoubleV) {

				dbl_arg = ((DoubleV) args.get(1)).getValue();

			} else if (args.get(1) instanceof StrV) {

				String arg = ((StrV) args.get(1)).getValue();

				try {

					dbl_arg = new StrV(arg).parseDouble();

				} catch (NumberFormatException e) {

					throw new InterpretException("ChildAt : Unexpected StrV 2nd argument : " + arg);

				}
				
			} else {

				throw new InterpretException("IndexAt : Unexpected 2nd argument");

			}
			
		} else
			
			throw new InterpretException("ChildAt : Unexpected # of args: " + args.size());
		
		node = tree_map.get(str_arg);
		index = (int)dbl_arg - 1;
		
		if (index < 0) index = 0;
		
		Node sub_tree = node.child.get(index);
		
		tree_map.put("Tree" + key, sub_tree);
		
		return new StrV("Tree" + key++);
	}
	
	public static void notifyFieldAssign(String fieldName) {

	}

	public static void notifyFieldRead(String fieldName) {

	}

}

class Node {
	
	Value v;
	ArrayList<Node> child;
	
	Node (Value v) {
		this.v = v;
		this.child = new ArrayList<Node>();
	}
	
}
