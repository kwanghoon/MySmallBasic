package com.coducation.smallbasic;

public class Lit extends Expr
{
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
	
	private String lit;
}
