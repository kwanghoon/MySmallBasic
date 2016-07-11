package com.coducation.smallbasic;

public class ExprStmt extends Stmt
{
		ExprStmt(Expr expr)
		{
			super();
			this.expr = expr;
		} // Builder
		
		private Expr expr;
}