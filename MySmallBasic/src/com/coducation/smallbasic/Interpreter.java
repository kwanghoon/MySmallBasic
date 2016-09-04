package com.coducation.smallbasic;

import java.util.ArrayList;

public class Interpreter {
	public Interpreter(Nonterminal tree){
		Tree = tree;
		env = new Env();
	}
	
	public void Interpreting(){
		try {
				BlockStmt stmt = (BlockStmt)Tree.getTree();
				stmt.evalStmt(env);
			
		} catch (Exception e) {
			//TODO : 라벨 찾아서 인터프리팅하기.
			String labelname = e.getMessage();
			Env tempEnv = env;
			BlockStmt stmt = (BlockStmt)Tree.getTree();
			ArrayList arr = stmt.getAL();
		}
	}
	
//	private int atoi(String a)
//	{
//		int result = 0;
//		for(int i = 0; i < a.length(); i++)
//		{
//			result = result * 10;
//			result = result + (a.charAt(i) - '0');
//		}
//		return result;	
//	}
	
	public static void push(Stmt stmt){
		stack.add(stmt);
	}
	
	public static Stmt pop(int index){
		Stmt stmt = stack.get(index);
		stack.remove(index);
		return stmt;
	}
	
	public static int getStackSize(){
		return stack.size();
	}
	
	public static void stackInit(){
		stack = new ArrayList<Stmt>();
	}
	
	public static boolean isNum(String str) throws NumberFormatException {
		try{
			double d = Double.parseDouble(str);
		}
		catch(NumberFormatException nfe){
			return false;
		}
		return true;
	}
	
	public static boolean parsetoBoolean(String str){
		String _true = "true";
		
		if ( _true.equals(str.toLowerCase()) ){
			return true;
		}
		else {
			return false;
		}
	}
	private static ArrayList<Stmt> stack;
	private Nonterminal Tree;
	private Env env;
}
