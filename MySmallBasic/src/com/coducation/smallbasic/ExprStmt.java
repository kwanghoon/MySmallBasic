package com.coducation.smallbasic;

public class ExprStmt extends Stmt
{
		ExprStmt(Expr expr)
		{
			super();
			this.expr = expr;
		} // Builder
		
		public Result evalStmt(Env env){
			Result res;
			res = expr.evalExpr(env);
			
			return res;
		}
		public Expr getExpr() {
			return expr;
		}
		
		private Expr expr;
		
}

  