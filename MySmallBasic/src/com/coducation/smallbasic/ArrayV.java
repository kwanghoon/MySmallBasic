package com.coducation.smallbasic;

import java.util.HashMap;

public class ArrayV extends Value {
	public ArrayV(){
		arrmap = new HashMap<String,Value>();
	}
	
	Value get(String index) {

		return arrmap.get(index);

	}

	void put(String index, Value v) {

		arrmap.put(index, v);

	}

	private HashMap<String,Value> arrmap;


}
