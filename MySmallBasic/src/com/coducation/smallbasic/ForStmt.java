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
			isStep = true;
		} // Builder
		
		public ForStmt(Var var, Expr init, Expr end, Stmt block)
		{
			super();
			this.var = var;
			this.init = init;
			this.end = end;
			this.block = block;
			this.step = new Lit("1");
			isStep = false;
		}
		private Var var;
		private Expr init, end, step;
		private Stmt block;
		private boolean isStep;
}
