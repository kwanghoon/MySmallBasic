package com.coducation.smallbasic;

public class ArithExpr extends Expr
{
	ArithExpr(Expr oprnd1, int op, Expr oprnd2)
	{
		super();
		this.oprnd1 = oprnd1;
		this.op = op;
		this.oprnd2 = oprnd2;
	} // Builder
	
	ArithExpr(int op, Expr oprnd1)
	{
		super();
		this.op = op;
		this.oprnd1 = oprnd1;
		this.oprnd2 = null; // Thisisok?
	}

	private int op; // O + O, O ¡© O, O * O, O / O, ¡©O
	private Expr oprnd1, oprnd2;
	
	public static final int PLUS = 1;
	public static final int MINUS = 2;
	public static final int MULTIFLY = 3;
	public static final int DIVIDE = 4;
	public static final int UNARY_MINUS = 5;
}
