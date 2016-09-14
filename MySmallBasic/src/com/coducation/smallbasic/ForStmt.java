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
		
		public Result evalStmt(Env env) throws Exception{
			Result res;
			
			Assign asn = new Assign(var, init);	//initialization.
			asn.evalStmt(env);
			CompExpr cond;
			
			while(true){
				String str_value;
				
				block.evalStmt(env);
				cond = new CompExpr(var, CompExpr.EQUAL ,end);	//조건 확인.
				res = cond.evalExpr(env);
				
				str_value = ((StrV)(res.getValue())).getValue();
				
				if( str_value.equals("true")){
					break;
				}
				else{
					//step.
					asn = new Assign(var, new ArithExpr(var, ArithExpr.PLUS, step));
					asn.evalStmt(env);					
				}
			}
			
			return new Result(env);
		}
		public Var getVar() {
			return var;
		}
		public Expr getInit() {
			return init;
		}
		public Expr getEnd() {
			return end;
		}
		public Expr getStep() {
			return step;
		}
		public Stmt getBlock() {
			return block;
		}
		
		private Var var;
		private Expr init, end, step;
		private Stmt block;
}
