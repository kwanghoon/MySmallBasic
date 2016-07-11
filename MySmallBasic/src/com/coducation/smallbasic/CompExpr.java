package com.coducation.smallbasic;

public class CompExpr extends CondExpr
{
	public CompExpr(int op, Expr oprnd1, Expr oprnd2)
	{
		super();
		this.op = op;
		this.oprnd1 = oprnd1;
		this.oprnd2 = oprnd2;
	} // Builder
	
	private int op;  // > < >= <= = <>
	private Expr oprnd1, oprnd2;
	
	private static final int LESS = 1;
	private static final int LESS_EQUAL = 2;
	private static final int GREATER = 3;
	private static final int GREATER_EQUAL = 4;
	private static final int EQUAL = 5;
	private static final int NOT_EQUAL = 6;
}
