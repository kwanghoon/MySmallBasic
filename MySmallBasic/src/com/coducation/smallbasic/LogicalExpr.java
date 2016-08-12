package com.coducation.smallbasic;

public class LogicalExpr extends CondExpr
{
	public LogicalExpr(Expr oprnd1, int op, Expr oprnd2)
	{
		super();
		this.oprnd1 = oprnd1;
		this.op = op;
		this.oprnd2 = oprnd2;
	} // Builder
	
	private int op;  // "And", "Or"
	private Expr oprnd1, oprnd2;
	
	public static final int AND = 1;
	public static final int OR = 2;
}
