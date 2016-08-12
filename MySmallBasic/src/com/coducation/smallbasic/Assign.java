package com.coducation.smallbasic;

public class Assign extends Stmt
{
		public Assign(Expr lhs, Expr rhs)
		{
			super();
			
			if(lhs instanceof Var || lhs instanceof PropertyExpr || lhs instanceof Array)
			{
				this.lhs = lhs;
			}
			else
			{
				System.err.println("Initializing Error...");
			}
			
			this.rhs = rhs;
		} // Builder
		
		private Expr lhs; // Var, or PropertyExpr
		private Expr rhs;
}
