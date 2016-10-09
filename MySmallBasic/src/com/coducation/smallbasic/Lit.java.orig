package com.coducation.smallbasic;

public class Lit extends CondExpr // extends Expr -> extends CondExpr
{								  // Because, Lit := "true", "false" <= String, num...
	public Lit(String lit)
	{
		super();
		this.lit = lit;
	} // Builder
	
	/* Notice 
	 * 123  
	 * 3.14
	 * 'a', '한' // “ ... “ 형식으로 사용해야 함
	 * Unicode.
	 */
	
	public String gets()
	{
		return lit;
	}
	
	public Result evalExpr(Env env){
		return new Result(env, new StrV(this.gets()) );
	}
	
	private String lit;
}
