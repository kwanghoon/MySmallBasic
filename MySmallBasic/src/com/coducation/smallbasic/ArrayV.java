package com.coducation.smallbasic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class ArrayV extends Value {
	public ArrayV(){
		arrmap = new HashMap<String,Value>();
	}
	
	
	public Value get(String index) {

		return arrmap.get(index);

	}

	public void put(String index, Value v) {

		arrmap.put(index, v);

	}
	
	
	
	
	public String toString() {
		return "*** Array ***";
	}
	
	//추가
	public int size(){
		return arrmap.size();
	}
	
	//추가
	public boolean containsV(String v){
		Iterator iterator = arrmap.entrySet().iterator();
		  while (iterator.hasNext()) {
		   Entry entry = (Entry)iterator.next();
		  // System.out.println(entry.getValue());
		   String sk = entry.getValue().toString();
		   if(sk.equals(v))
			   return true;
		  }
		return false;
	}
	
	//추가
	public void remove(String index){
		arrmap.remove(index);
	}

	//추가(미완성)
	public ArrayV getKey(){
		Iterator iterator = arrmap.entrySet().iterator();
		int i = 1;
		ArrayV arr = new ArrayV();
		  while (iterator.hasNext()) {
		   Entry entry = (Entry)iterator.next();
			String i_s = Integer.toString(i);
			Object key = entry.getKey();
			Value value = (Value)key;
			System.out.println(value);
			arr.put(i_s, (Value) key);
			i++;
		}
		return arr;
		
	}
//	@Override
//	public boolean equals(Object obj) {	// 자바 Object의 equals 메소드로 배열 비교
//										// 배열의 주소 비교
//		return super.equals(obj);
//	}



	private HashMap<String,Value> arrmap;
	
}
