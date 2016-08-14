package com.coducation.smallbasic;

public class CompExpr extends CondExpr
{
	public CompExpr(Expr oprnd1, int op, Expr oprnd2) // Order Change and static const name change.
	{
		super();
		this.oprnd1 = oprnd1;
		this.op = op;
		this.oprnd2 = oprnd2;
	} // Builder
	
	private int op;  // > < >= <= = <>
	private Expr oprnd1, oprnd2;
	
	public static final int LESS_THAN = 1;
	public static final int LESS_EQUAL = 2;
	public static final int GREATER_THAN = 3;
	public static final int GREATER_EQUAL = 4;
	public static final int EQUAL = 5;
	public static final int NOT_EQUAL = 6;
}
