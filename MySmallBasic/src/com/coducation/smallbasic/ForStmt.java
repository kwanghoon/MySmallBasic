package com.coducation.smallbasic;

public class ForStmt extends Stmt
{
		public ForStmt(Var var, Expr init, Expr end, Expr step, Stmt block)
		{
			super();
			this.var = var;
			this.init = init;
			this.end = end;
			this.step = step;
			this.block = block;
		} // Builder
		
		private Var var;
		private Expr init, end, step;
		private Stmt block;
}
