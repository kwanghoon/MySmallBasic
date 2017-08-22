package com.coducation.smallbasic.lib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class List {

	private static HashMap<String, java.util.List<Value>> list_map = new HashMap<>();
	
	private static int key = 1 ;
	
	public static Value Empty(ArrayList<Value> args) {
		
		if (args.size() == 0) {
			
			java.util.List<Value> list = new ArrayList<Value>();
			
			list_map.put("List" + key, list);

		} else

			throw new InterpretException("Empty : Unexpected # of args : " + args.size());	
		
		return new StrV("List" + key++);
	}
	
	public static Value Single(ArrayList<Value> args) {
		
		if (args.size() == 1 ) {
			
			java.util.List<Value> list = new ArrayList<Value>();
			list.add(args.get(0));
			
			list_map.put("List" + key, list);
			
		} else
			
			throw new InterpretException("Single : Unexpected # of args : " + args.size());
		
		return new StrV("List" + key++);
	}
	
	public static Value Add(ArrayList<Value> args) {
		
		java.util.List<Value> list;
		String str_arg;
		
		if (args.size() == 2) {
			
			if (args.get(0) instanceof StrV) {

				str_arg = ((StrV) args.get(0)).getValue();
				list = list_map.get(str_arg);

			} else {

				throw new InterpretException("Concat : Unexpected 1st argument");

			}
			
		} else
			
			throw new InterpretException("Concat : Unexpected # of args : " + args.size());
		
		list.add(args.get(1));
		
		return null;
	}
	
	public static Value Concat(ArrayList<Value> args) {
		
		java.util.List<Value> list;
		java.util.List<Value> list_add;
		
		String str_arg0;
		String str_arg1;
		
		if (args.size() == 2) {
			
			if (args.get(0) instanceof StrV) {

				str_arg0 = ((StrV) args.get(0)).getValue();
				list = list_map.get(str_arg0);

			} else {

				throw new InterpretException("Concat : Unexpected 1st argument");

			}
			
			if (args.get(1) instanceof StrV) {

				str_arg1 = ((StrV) args.get(1)).getValue();
				list_add = list_map.get(str_arg1);

			} else {

				throw new InterpretException("Concat : Unexpected 2nd argument");

			}
			
		} else
			
			throw new InterpretException("Concat : Unexpected # of args : " + args.size());
		
		for (int i = 0 ; i < list_add.size() ; i++ ) {
			
			list.add(list_add.get(i));
			
		}
		
		list_map.remove(str_arg0);
		list_map.put(str_arg0, list);
		
		return new StrV(str_arg0);
	}
	
	public static Value Head(ArrayList<Value> args) {
		
		String str_arg;
		java.util.List<Value> list;
		
		Value v;
		
		if (args.size() == 1) {
			
			if (args.get(0) instanceof StrV) {

				str_arg = ((StrV) args.get(0)).getValue();
				list = list_map.get(str_arg);

			} else {

				throw new InterpretException("Head : Unexpected arg");

			}
			
		} else
			
			throw new InterpretException("Head : Unexpected # of args : " + args.size());
		
		try {
			
			v = list.get(0);
			
		} catch (IndexOutOfBoundsException e) {
			
			return new StrV("Empty");
			
		}
		
		return v;
	}
	
	public static Value Tail(ArrayList<Value> args) {
		
		String str_arg;
		java.util.List<Value> list;
		
		if (args.size() == 1) {
			
			if (args.get(0) instanceof StrV) {

				str_arg = ((StrV) args.get(0)).getValue();
				list = list_map.get(str_arg);

			} else {

				throw new InterpretException("Tail : Unexpected arg");

			}
			
		} else
			
			throw new InterpretException("Tail : Unexpected # of args : " + args.size());
		
		list.remove(0);
		
		list_map.remove(str_arg);
		list_map.put(str_arg, list);
		
		return new StrV(str_arg);
	}
	
	public static Value IndexAt(ArrayList<Value> args) {
		
		String str_arg;
		double dbl_arg;
		
		java.util.List<Value> list;
		int index;
		
		if (args.size() == 2) {

			if (args.get(0) instanceof StrV) {
				
				str_arg = ((StrV) args.get(0)).getValue();

			} else {
			
				throw new InterpretException("IndexAt : Unexpected 1st argument");
				
			}

			if (args.get(1) instanceof DoubleV) {

				dbl_arg = ((DoubleV) args.get(1)).getValue();

			} else if (args.get(1) instanceof StrV) {

				String arg = ((StrV) args.get(1)).getValue();

				try {

					dbl_arg = new StrV(arg).parseDouble();

				} catch (NumberFormatException e) {

					throw new InterpretException("IndexAt : Unexpected StrV 2nd argument : " + arg);

				}

			} else {

				throw new InterpretException("IndexAt : Unexpected 2nd argument");

			}

		} else
			
			throw new InterpretException("IndexAt : Unexpected # of args: " + args.size());
		
		list = list_map.get(str_arg);
		index = (int)dbl_arg - 1;
		
		if (index < 0) index = 0;
		
		Value v = list.get(index);
		
		return v;
	}
	
	public static Value IsEmpty(ArrayList<Value> args) {
		
		String str_arg;
		java.util.List<Value> list;
		
		if (args.size() == 1) {
			
			if (args.get(0) instanceof StrV) {

				str_arg = ((StrV) args.get(0)).getValue();
				list = list_map.get(str_arg);

			} else {

				throw new InterpretException("IsEmpty : Unexpected arg");

			}
			
		} else
			
			throw new InterpretException("IsEmpty : Unexpected # of args : " + args.size());
		
		if (list.size() == 0) 
			
			return new StrV("True"); 

		else 
			
			return new StrV("False");
	}
	
	public static void notifyFieldAssign(String fieldName) {

	}

	public static void notifyFieldRead(String fieldName) {

	}

}
