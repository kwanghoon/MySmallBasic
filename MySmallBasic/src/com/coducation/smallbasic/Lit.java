package com.coducation.smallbasic;

public class Lit extends CondExpr // extends Expr -> extends CondExpr
{								  // Because, Lit := "true", "false" <= String, num...
	public Lit(double lit) {
		this.lit = lit + "";
		this.type = NUM;
	}
	public Lit(String lit, int type)
	{
		this.lit = lit;
		this.type = type;
	} // Builder
	
	/* Notice 
	 * 123  
	 * 3.14
	 * 'a', '한' // “ ... “ 형식으로 사용해야 함
	 * Unicode.
	 */
	
	public String gets()
	{
		if (type == NUM) return lit;
		else if(type == STRING) return "\"" + lit + "\"";
		else throw new InterpretException("Lit " + lit + " has invalid type: " + type);
	}
	
	public double getD() {
		if (type == NUM)
			return Double.parseDouble(lit);
		else 
			throw new InterpretException("Lit " + lit + " is not NUM");
	}
	
	public int type() { return type; }
	
	public String toString() {
		return lit;
	}
	
	public static final int NUM = 1;
	public static final int STRING = 2;
	private String lit;
	private int type;
}
