package com.coducation.smallbasic;

/*
 * This is solely for PrettyPrinter to express ( expr ).
 */
public class ParenExpr extends Expr {
	private Expr expr;
	
	public ParenExpr(Expr expr) {
		this.expr = expr;
	}
	
	public Expr get() { return expr; }
}
