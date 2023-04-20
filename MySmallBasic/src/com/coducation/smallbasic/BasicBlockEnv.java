package com.coducation.smallbasic;

import java.util.HashMap;

public class BasicBlockEnv {
	public BasicBlockEnv() {
		map = new HashMap<>();
	}
	public BasicBlockEnv(HashMap<String, Stmt> map) {
		this.map = map;
	}
	public Stmt get(String arg0) {
		
		return map.get(arg0);
	}
	public void put(String arg0, Stmt arg1) {
		map.put(arg0, arg1);
	}
	public HashMap<String, Stmt> getMap() {
		return map;
	}
	private HashMap<String, Stmt> map;
}
