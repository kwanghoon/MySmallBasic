package com.coducation.smallbasic;

public class Result {
	
	public Result(Env e, Value v){
		env = e;
		val = v;
	}
	
	public Result(Env e) {
		env = e;
		val =null;
	}

	public Value getValue(){
		return val;
	}
	
	public Env getEnv(){
		return env;
	}
	
	private Value val;
	private Env env;
}
