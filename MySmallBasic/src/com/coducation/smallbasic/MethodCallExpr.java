package com.coducation.smallbasic;

import java.util.ArrayList;

public class MethodCallExpr extends Expr
{
	public MethodCallExpr(String obj, String name, ArrayList<Expr> args) // Expr[] -> ArrayList<Expr> Change.
	{
		super();
		this.obj = obj;
		this.name = name;
		this.args = args;
	} // Builder

	private String obj, name;
	private ArrayList<Expr> args;
}
