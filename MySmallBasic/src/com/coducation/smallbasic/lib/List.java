package com.coducation.smallbasic.lib;

import java.util.ArrayList;
import java.util.HashMap;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.ListV;
import com.coducation.smallbasic.NullList;
import com.coducation.smallbasic.Pair;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class List {
	
	private static HashMap<String, ListV> list_map = new HashMap<>();
	private static int key = 1;
	
	public static Value List(ArrayList<Value> args) {
		
		ListV new_list = new NullList();
		
		for ( int i = args.size() -1 ; i >= 0 ; i-- ) {
			
			new_list = new Pair(args.get(i), new_list);
			
		}
		
		list_map.put("List" + key, new_list);
		
		return new StrV("List" + key++);
	}
	
	public static Value Concat(ArrayList<Value> args) {
		
		String str_arg0;
		String str_arg1;
		
		ListV list0;
		ListV list1;
		
		ListV new_list;
		
		if (args.size() == 2) {
			
			if (args.get(0) instanceof StrV) {

				str_arg0 = ((StrV) args.get(0)).getValue();
				list0 = list_map.get(str_arg0);

			} else {

				throw new InterpretException("Concat : Unexpected 1st argument");

			}
			
			if (args.get(1) instanceof StrV) {

				str_arg1 = ((StrV) args.get(1)).getValue();
				list1 = list_map.get(str_arg1);

			} else {

				throw new InterpretException("Concat : Unexpected 2nd argument");

			}
			
		} else
			
			throw new InterpretException("Concat : Unexpected # of args : " + args.size());
		
		new_list = list0.concat(list1);
		
		list_map.put("List" + key, new_list);
		
		return new StrV("List" + key++);
	}
	
	public static Value Head(ArrayList<Value> args) {
		
		String str_arg;
		
		ListV list;
		
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
		
		if( list.isNull() )
			
			return new StrV("Null");
			
		v = list.first();
			
		return v;	
	}
	
	public static Value Tail(ArrayList<Value> args) {
		
		String str_arg;
		
		ListV list;
		ListV new_list;
		
		if (args.size() == 1) {
			
			if (args.get(0) instanceof StrV) {

				str_arg = ((StrV) args.get(0)).getValue();
				list = list_map.get(str_arg);

			} else {

				throw new InterpretException("Tail : Unexpected arg");

			}
			
		} else
			
			throw new InterpretException("Tail : Unexpected # of args : " + args.size());

		new_list = list.second();
		list_map.put("List" + key, new_list);
		
		return new StrV("List" + key++);
	}
	
	public static Value IndexAt(ArrayList<Value> args) {
		
		String str_arg;
		double dbl_arg;
		
		ListV list;
		
		Value v;
		
		if (args.size() == 2) {

			if (args.get(0) instanceof StrV) {
				
				str_arg = ((StrV) args.get(0)).getValue();
				list = list_map.get(str_arg);

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
		
		if ( (int)dbl_arg > 0 && (int)dbl_arg <= list.length() ) {
			
			for ( int i = 1; i < (int)dbl_arg; i++ ) {
				list = list.second();
			}
			
			v = list.first();

			return v;
			
		} else {
			
			return new StrV("Null");
			
		}
	}
	
	public static Value IsEmpty(ArrayList<Value> args) {
		
		String str_arg;
		ListV list;
		
		if (args.size() == 1) {
			
			if (args.get(0) instanceof StrV) {

				str_arg = ((StrV) args.get(0)).getValue();
				list = list_map.get(str_arg);

			} else {

				throw new InterpretException("IsEmpty : Unexpected arg");

			}
			
		} else
			
			throw new InterpretException("IsEmpty : Unexpected # of args : " + args.size());
		
		if ( list.isNull() )
			return new StrV("True"); 
		else 
			return new StrV("False");
	}
	
	public static Value Print(ArrayList<Value> args) {
		
		String str_arg = ((StrV) args.get(0)).getValue();
		ListV list = list_map.get(str_arg);
		
		list.print();
		
		return null;

	}
	
	public static void notifyFieldAssign(String fieldName) {

	}

	public static void notifyFieldRead(String fieldName) {

	}
	
}
