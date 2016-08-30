package com.coducation.smallbasic;

import java.util.ArrayList;

public class Interpreter {
	public Interpreter(Nonterminal tree){
		Tree = tree;
		env = new Env();
	}
	
	public void Interpreting(){
		if (Tree.getTree() instanceof BlockStmt){
			BlockStmt stmt = (BlockStmt)Tree.getTree();

			stmt.evalStmt(env);
		}
		else if(Tree.getTree() instanceof Stmt){
			((Stmt)Tree.getTree()).evalStmt(env);
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

	private Nonterminal Tree;
	Env env;
}
