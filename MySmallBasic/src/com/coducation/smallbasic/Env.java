package com.coducation.smallbasic;

import java.util.HashMap;

public class Env {
	public Env(){
		vars = new HashMap<String,Value>();
		labels = new HashMap<String,BlockStmt>();
	}
	
	public void PutValue(String str, Value val ){
		vars.put(str, val);
	}
	
	public void PutStmt(String str, BlockStmt bstmt){
		labels.put(str, bstmt);
	}
	
	public Value getValue(String str){
		return vars.get(str);
	}
	
	public BlockStmt getStmt(String str){
		return labels.get(str);
	}
	
	//TODO : 생성자 나누는게 맞나..?
	
    private HashMap<String,Value> vars; 		// variables { var |-> Value }
    private HashMap<String,BlockStmt> labels; 	// labels  { label |-> BlockStmt }
}
