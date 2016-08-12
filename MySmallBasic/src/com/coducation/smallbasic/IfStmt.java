package com.coducation.smallbasic;

public class IfStmt extends Stmt
{
		public IfStmt(CondExpr cond, Stmt _then, Stmt _else)
		{
			super();
			this.cond = cond;
			this._then = _then;
			this._else = _else;
		} // Builder
		
		private	CondExpr cond;
		private	Stmt _then, _else;
}
