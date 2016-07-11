package com.coducation.smallbasic;

public class WhileStmt extends Stmt
{
		public WhileStmt(CondExpr cond, Stmt block)
		{
			super();
			this.cond = cond;
			this.block = block;
		} // Builder
		
		private CondExpr cond;
		private Stmt block;
}
