package com.coducation.smallbasic;

public class Var extends Expr
{
	public Var(String name)
	{
		super();
		
		if((name.charAt(0) >= 'A' && name.charAt(0) <= 'Z') || (name.charAt(0) >= 'a' && name.charAt(0) <= 'z'))
			if(name.length() < 41)
				this.name = name;
			else
				System.err.println("Var length over 40");
		else
			System.err.println("First character can Only Alphabet");
	} // Builder
	
	public String getVarName(){
		return name;
	}
	
	public Result evalExpr(Env env){
		Value temp_v = env.get( this.getVarName() );
		return new Result(env, temp_v);
	}
	
	/* Notice 
	 * Var ::= [a­zA­Z]+[a­zA­Z0­9_]*  
	 * ­ No more than 40 characters
	 * �� 변�도 �용 가�함. // �중고려...
	 */

	private String name;
}
