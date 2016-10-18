package com.coducation.smallbasic;

import java.util.HashMap;

public class Env {
	public Env(){
		map = new HashMap<String,Value>();
	}

	public void put(String varName, Value val ){
		map.put(varName, val);
	}

	public void put(String arrName, String index, Value val){ // arr[idx] = expr	

		if(map.containsKey(arrName)) {
			if(map.get(arrName) instanceof StrV)
				System.out.println(arrName+"는 일반변수입니다. 다른이름을 사용");
			else {
				ArrayV a = (ArrayV) map.get(arrName); 
				a.put(index, val);
			}
		}
		else { 
			ArrayV a = new ArrayV();
			map.put(arrName, a);
			a.put(index, val);
		}
	}

	public StrV get(String varName){
		return (StrV)map.get(varName);
	}

	public StrV get(String arrName, String index){
		if(map.get(arrName) instanceof StrV)
			return new StrV(arrName+"는 배열변수가 아닙니다.");
		else {
			ArrayV a = (ArrayV) map.get(arrName);
			return (StrV)a.get(index);
		}
	}

	//TODO : 생성자 나누는게 맞나..?

	private HashMap<String, Value> map; 		// variables { var |-> Value }
	//private HashMap<String,BlockStmt> labels; 	// labels  { label |-> BlockStmt }

}

