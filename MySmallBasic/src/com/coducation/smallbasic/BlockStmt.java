package com.coducation.smallbasic;

public class BlockStmt extends Stmt
{
		public BlockStmt(Stmt[] stmts)
		{
			super();
			this.stmts = stmts;
		} // Builder
		
		private Stmt[] stmts;
}
