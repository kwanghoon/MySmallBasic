package com.coducation.smallbasic;

import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class ArrayV extends Value {
	public ArrayV(){
		arrmap = new LinkedHashMap<String, Pair<String,Value>>();
	}
	
	
	public Value get(String index) {
		Pair<String,Value> psv = arrmap.get(index.toUpperCase());
		return psv==null ? null : psv.b;
	}

	public void put(String index, Value v) {

		arrmap.put(index.toUpperCase(), new Pair<String,Value>(index,v));

	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Set<String> indices = arrmap.keySet();
		for (String index : indices) {
			Pair<String,Value> psv = arrmap.get(index.toUpperCase());
			Value v = psv.b;
			String str = v.toString();
			StringBuilder sbs = new StringBuilder();
			for (int i = 0; i<str.length(); i++) {
				char ch = str.charAt(i);
				if (ch == ';' || ch == '=' || ch =='\\')
					sbs.append('\\');
				sbs.append(ch);
			}
			sb.append(psv.a);
			sb.append('=');
			sb.append(sbs.toString());
			sb.append(';');
		}
		return sb.toString();
	}
	
	// An usable implementation but it has not been finished yet.
	// For example, the following cases should be considered. 
	//  - idx=val is allowed to end without ;. 
	//  - idx val; means "idx val" is an index and "" is a value; 
	public static ArrayV from(String s) {
		ArrayV arr = new ArrayV();
		int i;
		
		i = 0;

		try {
			while (i < s.length()) {
				StringBuilder sb_idx = new StringBuilder();
				StringBuilder sb_value = new StringBuilder();
				while ( s.charAt(i) != '=' ) {
					if (s.charAt(i) == '\\') {
						i = i + 1;
						if (i >= s.length()) throw new Throwable();
					}
					sb_idx.append(s.charAt(i));
					i = i + 1;
					if (i>=s.length()) throw new Throwable();
				}
				String idx = sb_idx.toString();
				if (idx.length() == 0) throw new Throwable();
				
				i = i + 1;
				if (i>=s.length()) throw new Throwable();
				
				while ( s.charAt(i) != ';' ) {
					if (s.charAt(i) == '\\') {
						i = i + 1;
						if (i >= s.length()) throw new Throwable();
					}
					sb_value.append(s.charAt(i));
					i = i + 1;
					if (i>=s.length()) throw new Throwable();
				}
				String val = sb_value.toString();
				if (val.length() == 0) throw new Throwable();
				
				i = i + 1;
				
				ArrayV trial = from(val);
				if (trial.size() == 0)
					arr.put(idx, new StrV(val));
				else
					arr.put(idx, trial);
			}
		}
		catch(Throwable t) {
			
		}
		
		return arr;
	}
	
	public ArrayV copy() {
		ArrayV arr = new ArrayV();
		Iterator<Entry<String, Pair<String,Value>>> iterator = arrmap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Pair<String,Value>> entry = (Entry<String, Pair<String,Value>>)iterator.next();
			String key = entry.getKey();
			Value value = entry.getValue().b;
			if (value instanceof ArrayV) {
				arr.put(key, ((ArrayV)value).copy());
			}
			else
				arr.put(key, value);
		}
		return arr;
	}
	
	//추가
	public int size(){
		return arrmap.size();
	}
	
	//추가
	public boolean containsV(String v){
		Iterator<Entry<String, Pair<String,Value>>> iterator = arrmap.entrySet().iterator();
		  while (iterator.hasNext()) {
		   Entry<String, Pair<String,Value>> entry = (Entry<String, Pair<String,Value>>)iterator.next();
		   String sk = entry.getValue().b.toString();
		   if(sk.equals(v))
			   return true;
		  }
		return false;
	}
	
	//추가
	public void remove(String index){
		arrmap.remove(index);
	}

	//추가
	public ArrayV getKey(){
		Iterator<Entry<String, Pair<String,Value>>> iterator = arrmap.entrySet().iterator();
		int i = 1;
		ArrayV arr = new ArrayV();
		  while (iterator.hasNext()) {
		   Entry<String, Pair<String,Value>> entry = (Entry<String, Pair<String,Value>>)iterator.next();
			String i_s = Integer.toString(i);
			//String key = entry.getKey().toString();
			String key = entry.getValue().a.toString(); // The original key before the case change.
			StrV key_s = new StrV(key);
			arr.put(i_s, key_s);
			i++;
		}
		return arr;
		
	}
//	@Override
//	public boolean equals(Object obj) {	// 자바 Object의 equals 메소드로 배열 비교
//										// 배열의 주소 비교
//		return super.equals(obj);
//	}


	// The order of inserting pairs of key and value does matter 
	// when ArrayV is represented in the string format. 
	// So, we replace HashMap with LinkedHashMap.
	private LinkedHashMap<String, Pair<String,Value>> arrmap;



	@Override
	public double getNumber() {
		throw new InterpretException("getNumber(Array value)");
	}
	
	private static class Pair<A,B> {
		public A a;
		public B b;
		public Pair(A a, B b) {
			this.a = a;
			this.b = b;
		}
	}
}
