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
		
		public Result evalStmt(Env env){
			Result res = new Result(env);
			Value bool_val = (cond.evalExpr(env)).getValue();

			if(((StrV)bool_val).getValue() == "true"){
				res = _then.evalStmt(env);
			}
			else if(_else != null){
				res = _else.evalStmt(env);
			}
			
			return res;
		}
		
		private	CondExpr cond;
		private	Stmt _then, _else;
}
