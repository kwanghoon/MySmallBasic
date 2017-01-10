package com.coducation.smallbasic.lib;

import java.util.ArrayList;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Array {
	
	public static Value ContainsIndex(ArrayList<Value> args){
		if(args.size()== 2){
			Value arg1 = args.get(0); // array name
			Value arg2 = args.get(1); // index
			String s;
			double d;
			if(arg1 instanceof StrV){
				StrV str_arg1 = (StrV) arg1;
				s= str_arg1.getValue();
			}
			else 
				throw new InterpretException("Not String Value for ArrayName" + arg1);
			if(arg2 instanceof DoubleV){
				DoubleV d_arg2 = (DoubleV)arg2;
				d = d_arg2.getValue();
			}
			else if(arg2 instanceof StrV && ((StrV)arg2).isNumber()){
				StrV str_arg2 = (StrV) arg2;
			
				d = str_arg2.parseDouble();
			
			}
			else 
				throw new InterpretException("Not Double Value for ArrayName" + arg2);
			
			
			//
			return null;
		} else 
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	public static Value ContainsValue(ArrayList<Value> args){
		if(args.size()== 2){
			Value arg1 = args.get(0); // array name
			Value arg2 = args.get(1); // value
			if(arg1 instanceof StrV){
				StrV str_arg1 = (StrV) arg1;
				String s= str_arg1.getValue();
			}
			else 
				throw new InterpretException("Not String Value for ArrayName" + arg1);
			if(arg2 instanceof DoubleV){
				DoubleV d_arg2 = (DoubleV)arg2;
				double d = d_arg2.getValue();
			}
			else 
				throw new InterpretException("Not Double Value for ArrayName" + arg2);
			return null;
		} else 
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	public static Value GetAllIndices(ArrayList<Value> args){
		if(args.size()==1){
			Value arg = args.get(0);
			if(arg instanceof StrV){
				StrV str_arg = (StrV) arg;
				String s = str_arg.getValue();
			}
			else 
				throw new InterpretException("Not String Value for ArrayName" + arg);
		return null;
		}
		else
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	public static Value GetItemCount(ArrayList<Value> args){
		if(args.size()==1){
			Value arg = args.get(0);
			if(arg instanceof StrV){
				StrV str_arg = (StrV) arg;
				String s = str_arg.getValue();
			}
			else 
				throw new InterpretException("Not String Value for ArrayName" + arg);
		return null;
		}
		else
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	public static Value IsArray(ArrayList<Value> args){
		if(args.size()==1){
			Value arg = args.get(0);
			if(arg instanceof StrV){
				StrV str_arg = (StrV) arg;
				String s = str_arg.getValue();
			}
			else 
				throw new InterpretException("Not String Value for ArrayName" + arg);
		return null;
		}
		else
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	public static Value SetValue(ArrayList<Value> args){
		if(args.size()== 3){
			Value arg1 = args.get(0); // array name
			Value arg2 = args.get(1); // index
			Value arg3 = args.get(2); // value
			if(arg1 instanceof StrV){
				StrV str_arg1 = (StrV) arg1;
				String s= str_arg1.getValue();
			}
			else 
				throw new InterpretException("Not String Value for ArrayName" + arg1);
			if(arg2 instanceof DoubleV){
				DoubleV d_arg2 = (DoubleV)arg2;
				double d = d_arg2.getValue();
			}
			else 
				throw new InterpretException("Not Double Value for ArrayName" + arg2);
			if(arg3 instanceof DoubleV){
				DoubleV d_arg3 = (DoubleV)arg3;
				double d = d_arg3.getValue();
			}
			else 
				throw new InterpretException("Not Double Value for ArrayName" + arg3);
			return null;
		} else 
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	public static Value GetValue(ArrayList<Value> args){
		if(args.size()== 2){
			Value arg1 = args.get(0); // array name
			Value arg2 = args.get(1); // index
			if(arg1 instanceof StrV){
				StrV str_arg1 = (StrV) arg1;
				String s= str_arg1.getValue();
			}
			else 
				throw new InterpretException("Not String Value for ArrayName" + arg1);
			if(arg2 instanceof DoubleV){
				DoubleV d_arg2 = (DoubleV)arg2;
				double d = d_arg2.getValue();
			}
			else 
				throw new InterpretException("Not Double Value for ArrayName" + arg2);
			return null;
		} else 
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	public static Value RemoveValue(ArrayList<Value> args){
		if(args.size()== 2){
			Value arg1 = args.get(0); // array name
			Value arg2 = args.get(1); // index
			if(arg1 instanceof StrV){
				StrV str_arg1 = (StrV) arg1;
				String s= str_arg1.getValue();
			}
			else 
				throw new InterpretException("Not String Value for ArrayName" + arg1);
			if(arg2 instanceof DoubleV){
				DoubleV d_arg2 = (DoubleV)arg2;
				double d = d_arg2.getValue();
			}
			else 
				throw new InterpretException("Not Double Value for ArrayName" + arg2);
			return null;
		} else 
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	public static void notifyFieldAssign(String fieldName) {
		//비어두기 
	}

	public static void notifyFieldRead(String fieldName) {
		//비어두기 
	}
}
