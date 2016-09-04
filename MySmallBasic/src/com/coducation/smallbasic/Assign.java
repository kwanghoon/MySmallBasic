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
		
		public Expr getLSide(){
			return lhs;
		}
		
		public Expr getRSide(){
			return rhs;
		}
		
		public Result evalStmt(Env env){
			Result temp_res;
			String temp_str ="";
			
			if( lhs instanceof Var){
				temp_str = ((Var)lhs).getVarName();
			}
			else if (lhs.getClass() == Array.class){
				//TODO : 배열 할당..? 
			}
			else if ( lhs instanceof PropertyExpr){

			}

			temp_res = rhs.evalExpr(env);	//Right Side Result.
			
			env.PutValue(temp_str, temp_res.getValue());//Left Side에 Right Side Value를 맵핑.
		
			return new Result(env);
		}
		
		
		private Expr lhs; // Var, or PropertyExpr
		private Expr rhs;
}
