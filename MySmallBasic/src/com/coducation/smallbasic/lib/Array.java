package com.coducation.smallbasic.lib;

import java.util.ArrayList;
import java.util.HashMap;

import com.coducation.smallbasic.ArrayV;
import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Array {

	//배열에 index 여부
	public static Value ContainsIndex(ArrayList<Value> args){
		
		if(args.size()== 2){
			Value arg1 = args.get(0); // array name
			Value arg2 = args.get(1); // index
			String index;
			ArrayV arr;
			
			if(arg1 == null){
				return new StrV("false");
			}
			else if(arg1 instanceof ArrayV){
				arr = (ArrayV)arg1;
			}
			 else if(arg1 instanceof StrV){
				 return new StrV("false");
			 }
			else
				throw new InterpretException("Not String Value for ArrayName" + arg1);
			
			index = arg2.toString();
	
		 if(arr.get(index) == null)
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
			String value;
			ArrayV arr ;
			if(arg1 == null){
				return new StrV("false");
			}
			else if(arg1 instanceof ArrayV){
				arr = (ArrayV)arg1;
			}
			else if(arg1 instanceof StrV){
				return new StrV("false");
			}
			else 
				throw new InterpretException("Not Array Value for ArrayName" + arg1);
			
			value = arg2.toString();
			
			if(arr.containsV(value))
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
			ArrayV arr;
			if(arg == null){
				return null;
			}
			else if(arg instanceof ArrayV){
				arr = (ArrayV)arg;
			}
			else if(arg instanceof StrV){
				return null;
			}
			else 
				throw new InterpretException("Not String Value for ArrayName" + arg);
			return arr.getKey();
			
		}
		else
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}

	//배열에 들어있는 index의 개수
	public static Value GetItemCount(ArrayList<Value> args){
		
		if(args.size()==1){
			
			Value arg = args.get(0);
			ArrayV arr;
			if(arg == null){
				return new DoubleV(0);
			}
			else if(arg instanceof ArrayV){
				arr = (ArrayV)arg;
			}
			else if(arg instanceof StrV){
				return new DoubleV(0);
			}
			else 
				throw new InterpretException("Not String Value for ArrayName" + arg);

			return new DoubleV(arr.size());
			
		}
		else
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}


	//배열 여부
	public static Value IsArray(ArrayList<Value> args){
		
		if(args.size()==1){
			Value arg = args.get(0);
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
			String index;
			String arrname = arg1.toString();
			ArrayV arr;
			if(arg1 instanceof StrV){
				arr = hashmap.get(arrname);
				if(arr == null){
					arr = new ArrayV();
					hashmap.put(arrname, arr);
				}
			}
			else if(arg1 instanceof ArrayV){
				return null;
			}
			else 
				throw new InterpretException("Not Value for ArrayName" + arg1);
			index = arg2.toString();
			arr.put(index, arg3);
			return null;
		} else 
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
	}

	//배열 값 얻기
	public static Value GetValue(ArrayList<Value> args){
		
		if(args.size()== 2){
			Value arg1 = args.get(0); // array name
			Value arg2 = args.get(1); // index
			String index;
			String result;
			String arrname = arg1.toString();
			ArrayV arr;
			if(arg1 instanceof StrV){
				arr = hashmap.get(arrname);
				if(arr == null)
					return new StrV("");
			}
			else if(arg1 instanceof ArrayV){
				return new StrV("");
			}
			else 
				throw new InterpretException("Not Value for ArrayName" + arg1);
			
			index = arg2.toString();
			if(arr.get(index)==null)
				return new StrV("");
			else
				result = arr.get(index).toString();
			return new StrV(result);
		} else 
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
	}

	//배열 제거
	public static Value RemoveValue(ArrayList<Value> args){
		
		if(args.size()== 2){
			Value arg1 = args.get(0); // array name
			Value arg2 = args.get(1); // index
			String index;
			String arrname = arg1.toString();
			ArrayV arr;
			if(arg1 instanceof StrV){
				arr = hashmap.get(arrname);
				if(arr == null)
					return null;
			}
			else if(arg1 instanceof ArrayV){
				return null;
			}
			else 
				throw new InterpretException("Not Value for ArrayName" + arg1);
			index = arg2.toString();
			arr.remove(index);
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

	private static HashMap<String,ArrayV> hashmap = new HashMap<String,ArrayV>();
}
