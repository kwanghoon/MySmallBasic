package com.coducation.smallbasic.lib;

import java.util.ArrayList;

import com.coducation.smallbasic.ArrayV;
import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Array {

	//배열에 index 존재 여부
	public static Value ContainsIndex(ArrayList<Value> args){
		if(args.size()== 2){
			Value arg1 = args.get(0); // array name
			Value arg2 = args.get(1); // index
			//String arrayName;
			double index;
			if(arg1 instanceof StrV){
				//StrV str_arg1 = (StrV) arg1;
				//arrayName= str_arg1.getValue();
			}
			else 
				throw new InterpretException("Not String Value for ArrayName" + arg1);
			if(arg2 instanceof DoubleV){
				DoubleV d_arg2 = (DoubleV)arg2;
				index = d_arg2.getValue();
			}
			else if(arg2 instanceof StrV && ((StrV)arg2).isNumber()){
				StrV str_arg2 = (StrV) arg2;
				index = str_arg2.parseDouble();

			}
			else 
				throw new InterpretException("Not Value for index" + arg2);
			ArrayV arr = (ArrayV)arg1;
			String index_s = Double.toString(index);
			if(arr.get(index_s)==null)
				return new StrV("false");
			else
				return new StrV("true");
		} else 
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}

	//배열에 value 여부
	public static Value ContainsValue(ArrayList<Value> args){
		if(args.size()== 2){
			Value arg1 = args.get(0); // array name
			Value arg2 = args.get(1); // value
			//String s;
			//double value;
			if(arg1 instanceof StrV){
				//StrV str_arg1 = (StrV) arg1;
				//s= str_arg1.getValue();
			}
			else 
				throw new InterpretException("Not String Value for ArrayName" + arg1);
			if(arg2 instanceof DoubleV){
				//DoubleV d_arg2 = (DoubleV)arg2;
				//value = d_arg2.getValue();
			}
			else if(arg2 instanceof StrV && ((StrV)arg2).isNumber()){
				//StrV str_arg2 = (StrV)arg2;
				//value = str_arg2.parseDouble();
			}
			else 
				throw new InterpretException("Not Value for value" + arg2);
			ArrayV arr = (ArrayV)arg1;
			if(arr.containsV(arg2))
				return new StrV("true");
			else
				return new StrV("false");
		} else 
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}

	//배열의 index 값으로 새로운 배열 생성
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

	//배열에 들어있는 index의 개수!
	public static Value GetItemCount(ArrayList<Value> args){
		if(args.size()==1){
			Value arg = args.get(0);
			if(arg instanceof StrV){
				//StrV str_arg = (StrV) arg;
				//String s = str_arg.getValue();
			}
			else 
				throw new InterpretException("Not String Value for ArrayName" + arg);
			ArrayV arr = (ArrayV)arg;
			return new DoubleV(arr.size());
		}
		else
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}

	
	//배열 여부
	public static Value IsArray(ArrayList<Value> args){
		if(args.size()==1){
			Value arg = args.get(0);
			if(arg instanceof StrV){
				StrV str_arg = (StrV) arg;
				String s = str_arg.getValue();
			}
			else 
				throw new InterpretException("Not String Value for ArrayName" + arg);
			if(arg instanceof ArrayV)
				return new StrV("true");
			else
				return new StrV("false");
		}
		else
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}

	//배열 설정
	public static Value SetValue(ArrayList<Value> args){
		if(args.size()== 3){
			Value arg1 = args.get(0); // array name
			Value arg2 = args.get(1); // index
			Value arg3 = args.get(2); // value
			double index;
			if(arg1 instanceof StrV){
				//StrV str_arg1 = (StrV) arg1;
				//String s= str_arg1.getValue();
			}
			else 
				throw new InterpretException("Not String Value for ArrayName" + arg1);
			if(arg2 instanceof DoubleV){
				DoubleV d_arg2 = (DoubleV)arg2;
				index = d_arg2.getValue();
			}
			else if(arg2 instanceof StrV && ((StrV)arg2).isNumber()){
				StrV str_arg2 = (StrV) arg2;
				index = str_arg2.parseDouble();
			}
			else 
				throw new InterpretException("Not Value for index" + arg2);
			if(arg3 instanceof DoubleV){
				//DoubleV d_arg3 = (DoubleV)arg3;
				//double d = d_arg3.getValue();
			}
			else if(arg3 instanceof StrV && ((StrV)arg3).isNumber()){
				//StrV str_arg3 = (StrV) arg3;
				//value = str_arg3.parseDouble();
			}
			else 
				throw new InterpretException("Not Value for value" + arg3);
			ArrayV arr = (ArrayV)arg1;
			String index_s = Double.toString(index);
			arr.put(index_s, arg3);
			return null;
		} else 
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}

	//배열 값 얻기
	public static Value GetValue(ArrayList<Value> args){
		if(args.size()== 2){
			Value arg1 = args.get(0); // array name
			Value arg2 = args.get(1); // index
			double index;
			if(arg1 instanceof StrV){
				//StrV str_arg1 = (StrV) arg1;
				//String s= str_arg1.getValue();
			}
			else 
				throw new InterpretException("Not String Value for ArrayName" + arg1);
			if(arg2 instanceof DoubleV){
				DoubleV d_arg2 = (DoubleV)arg2;
				index = d_arg2.getValue();
			}
			else if(arg2 instanceof StrV && ((StrV)arg2).isNumber()){
				StrV str_arg2 = (StrV) arg2;
				index = str_arg2.parseDouble();
			}
			else 
				throw new InterpretException("Not Value for index" + arg2);
			ArrayV arr = (ArrayV)arg1;
			String index_s = Double.toString(index);
			return (StrV)arr.get(index_s);
		} else 
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}

	//배열 제거
	public static Value RemoveValue(ArrayList<Value> args){
		if(args.size()== 2){
			Value arg1 = args.get(0); // array name
			Value arg2 = args.get(1); // index
			double index;
			if(arg1 instanceof StrV){
				//StrV str_arg1 = (StrV) arg1;
				//String s= str_arg1.getValue();
			}
			else 
				throw new InterpretException("Not String Value for ArrayName" + arg1);
			if(arg2 instanceof DoubleV){
				DoubleV d_arg2 = (DoubleV)arg2;
				index = d_arg2.getValue();
			}
			else if(arg2 instanceof StrV && ((StrV)arg2).isNumber()){
				StrV str_arg2 = (StrV) arg2;
				index = str_arg2.parseDouble();
			}
			else 
				throw new InterpretException("Not Value for index" + arg2);
			ArrayV arr = (ArrayV)arg1;
			String index_s = Double.toString(index);
			arr.remove(index_s);
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
