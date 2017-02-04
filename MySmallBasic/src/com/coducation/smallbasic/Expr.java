package com.coducation.smallbasic;


public class Expr
{
	public Expr(){}
	
	public Result evalExpr(Env env){
		System.out.println("Expr");
		
		return null;
	}
	
	public void prettyPrint()
	{
	}
	
	private int lineno;
	private int charat;
	
	public void at(int lineno, int charat) {
		this.lineno = lineno;
		this.charat = charat;
	}
	
	public int lineno() {
		return lineno;
	}
	
	public int charat() {
		return charat;
	}
	
}


