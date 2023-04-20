package com.coducation.smallbasic;

public class IfStmt extends Stmt
{
		public IfStmt(Expr cond, Stmt _then, Stmt _else)
		{
			super();
			this.cond = cond;
			this._then = _then;
			this._else = _else;
		} // Builder
		
		public Result evalStmt(Env env) throws Exception{
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
		public Expr getCond() {
			return cond;
		}
		public Stmt getThen() {
			return _then;
		}
		public Stmt getElse() {
			return _else;
		}
		
		private	Expr cond;
		private	Stmt _then, _else;
}
