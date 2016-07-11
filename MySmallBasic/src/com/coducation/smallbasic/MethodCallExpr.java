package com.coducation.smallbasic;

public class MethodCallExpr extends Expr
{
	public MethodCallExpr(String obj, String name, Expr[] args)
	{
		super();
		this.obj = obj;
		this.name = name;
		this.args = args;
	} // Builder
	
	private String obj, name;
	private Expr[] args;
}
