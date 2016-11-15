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
	
	public String toString() {
		return "*** Array ***";
	}
	
	

//	@Override
//	public boolean equals(Object obj) {	// 자바 Object의 equals 메소드로 배열 비교
//										// 배열의 주소 비교
//		return super.equals(obj);
//	}



	private HashMap<String,Value> arrmap;


}
