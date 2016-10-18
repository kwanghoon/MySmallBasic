package com.coducation.smallbasic;

import java.util.ArrayList;
import java.util.HashMap;

public class ArrayV extends Value {
	public ArrayV(){
		map = new HashMap<String,Value>();
	}
	
	Value get(String index) {

		return map.get(index);

	}

	void put(String index, Value v) {

		map.put(index, v);

	}

	private HashMap<String,Value> map;


}
