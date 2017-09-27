package com.coducation.smallbasic.lib;

import java.util.ArrayList;
import java.util.HashMap;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.NullTree;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.TreeNode;
import com.coducation.smallbasic.TreeV;
import com.coducation.smallbasic.Value;

public class Tree {
	
	private static HashMap<String, TreeV> tree_map = new HashMap<>();
	private static int key = 1 ;
	
	public static Value Tree(ArrayList<Value> args) {
		
		if (args.size() == 0) {
			
			TreeV tree = new NullTree();
			tree_map.put("Tree" + key, tree);
			
		} else if (args.size() == 1) {
			
			TreeV tree = new TreeNode(args.get(0));
			tree_map.put("Tree" + key, tree);

		} else if (args.size() > 1) {
			
			String str_arg ;
			
			TreeV tree;
			ArrayList<TreeV> child = new ArrayList<TreeV>();
			
			for ( int i = 1; i < args.size(); i++) {
				
				str_arg = ((StrV) args.get(i)).getValue();
				child.add(tree_map.get(str_arg));
				
			}
			
			tree = new TreeNode(args.get(0), child);
			tree_map.put("Tree" + key, tree);
			
		} else {
			
			throw new InterpretException("Tree : Unexpected # of args : " + args.size());
			
		}
		
		return new StrV("Tree" + key++);
	}
	
	public static Value Root(ArrayList<Value> args) {
		
		String str_arg;
		TreeV tree;
		
		Value v;
		
		if (args.size() == 1) {
			
			str_arg = ((StrV) args.get(0)).getValue();
			tree = tree_map.get(str_arg);

		} else
			
			throw new InterpretException("Root : Unexpected # of args : " + args.size());
		
		try {
			
			v = tree.value();
			
		} catch (Exception e) {
			
			return new StrV("Null");
			
		}
		
		return v;
	}
	
	public static Value ChildAt(ArrayList<Value> args) {
		
		String str_arg;
		double dbl_arg;
		
		TreeV tree;
		TreeV subTree;
		
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

				throw new InterpretException("ChildAt : Unexpected 2nd argument");

			}
			
		} else
			
			throw new InterpretException("ChildAt : Unexpected # of args: " + args.size());
		
		tree = tree_map.get(str_arg);
		subTree = tree.childAt((int)dbl_arg - 1);
		
		tree_map.put("Tree" + key, subTree);
		
		return new StrV("Tree" + key++);
	}
	
	public static Value Print(ArrayList<Value> args) {
		
		String str_arg;
		
		TreeV tree;
		
		if (args.size()==1) {
			
			if (args.size() == 1) {
				
				str_arg = ((StrV) args.get(0)).getValue();
				tree = tree_map.get(str_arg);

			} else
				
				throw new InterpretException("Root : Unexpected # of args : " + args.size());
			
		} else
			
			throw new InterpretException("Root : Unexpected # of args : " + args.size());
		
		tree.print();
		System.out.println();
		
		return null;
	}
	
	public static void notifyFieldAssign(String fieldName) {

	}

	public static void notifyFieldRead(String fieldName) {

	}

}
