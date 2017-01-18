package com.coducation.smallbasic;

import java.util.HashMap;

public class Env {
	public Env() {
		map = new HashMap<String, Value>();
		labels = new HashMap<String,String>();
	}

	public void put(String varName, Value val) {
		map.put(varName, val);
	}

	public void put(String arrName, String index, Value val) { // arr[idx] =
																// expr

		if (map.containsKey(arrName)) {
			if (map.get(arrName) instanceof StrV)
				System.out.println(arrName + "는 일반변수입니다. 다른이름을 사용");
			else {
				ArrayV a = (ArrayV) map.get(arrName);
				a.put(index, val);
			}
		} else {
			ArrayV a = new ArrayV();
			map.put(arrName, a);
			a.put(index, val);
		}
	}

	public Value get(String varName) {
		return map.get(varName);
	}

	public Value get(String arrName, String index) {
		if (map.get(arrName) instanceof ArrayV) {
			ArrayV a = (ArrayV) map.get(arrName);
			return (StrV) a.get(index);
		}
		else {
			throw new InterpretException(arrName + "는 배열변수가 아닙니다.");
		}
	}

	// TODO : 생성자 나누는게 맞나..?

	private HashMap<String, Value> map; // variables { var |-> Value }
	// private HashMap<String,BlockStmt> labels; // labels { label |-> BlockStmt
	// }
	
	
	// Multiple Labels for Multiple Threads 
	private HashMap<String,String> labels;
	
	private static final String label = "$label";
	
	public String label() {
		return labels.get(label + Thread.currentThread().getId());
	}
	
	public void label(String _label) {
		labels.put(label + Thread.currentThread().getId(), _label);
	}
}
