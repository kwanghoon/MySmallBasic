package com.coducation.smallbasic;

public class LogicalExpr extends CondExpr
{
	public LogicalExpr(int op, Expr oprnd1, Expr oprnd2)
	{
		super();
		this.op = op;
		this.oprnd1 = oprnd1;
		this.oprnd2 = oprnd2;
	} // Builder
	
	private int op;  // "And", "Or"
	private Expr oprnd1, oprnd2;
	
	private static final int AND = 1;
	private static final int OR = 2;
}
