package com.coducation.smallbasic;

public class ArithExpr extends Expr
{
	ArithExpr(int op, Expr oprnd1, Expr oprnd2)
	{
		super();
		this.op = op;
		this.oprnd1 = oprnd1;
		this.oprnd2 = oprnd2;
	} // Builder

	private int op; // O + O, O ¡© O, O * O, O / O, ¡©O
	private Expr oprnd1, oprnd2;
	
	public static final int PLUS = 1;
	public static final int MINUS = 2;
	public static final int MULTIFLY = 3;
	public static final int DIVIDE = 4;
	public static final int UNARY_MINUS = 5;
}
