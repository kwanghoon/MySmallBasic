package com.coducation.smallbasic;

public class CompExpr extends CondExpr
{
	public CompExpr(Expr oprnd1, int op, Expr oprnd2) // Order Change and static const name change.
	{
		super();
		this.oprnd1 = oprnd1;
		this.op = op;
		this.oprnd2 = oprnd2;
	} // Builder
	
	public int GetOp(){
		return op;
	}
	
	public Expr[] GetOperand(){
		Expr[] ret = new Expr[2];
		ret[0]=oprnd1;
		ret[1]=oprnd2;
		
		return ret;
	}
	
	public Result evalExpr(Env env){
		Expr[] temp_ex = this.GetOperand();
		StrV sv1, sv2;
		
		Result oprnd1 = temp_ex[0].evalExpr(env);
		Result oprnd2 = temp_ex[1].evalExpr(env);
		
		float parsed_float1, parsed_float2;
		String oprnd1_str, oprnd2_str;
		oprnd1_str = oprnd2_str = null;
		
		if(oprnd1.getValue() instanceof StrV){
			sv1 = (StrV)(oprnd1.getValue());
			oprnd1_str = sv1.getValue();
		}
		else {
			//다른 경우 테스트 확인
			System.err.println("CompExpr : err");
		}

		if(oprnd2.getValue() instanceof StrV){
			sv2 = (StrV)(oprnd2.getValue());
			oprnd2_str = sv2.getValue();
		}
		else {
			System.err.println("ComphExpr : err");
		}
		
		try {
			parsed_float1 = Float.parseFloat(oprnd1_str);	
		}
		catch (NumberFormatException nfe) {
			parsed_float1 = 0;
		}
		try {
			parsed_float2 = Float.parseFloat(oprnd2_str);
		}
		catch(NumberFormatException n) {
			parsed_float2 = 0;
		}
		
		if( this.GetOp() == CompExpr.EQUAL ){			
			if( oprnd1_str.equals(oprnd2_str))
				return new Result(env, new StrV("true"));
		}
		else if( this.GetOp() == CompExpr.NOT_EQUAL ){
			if( !oprnd1_str.equals(oprnd2_str))
				return new Result(env, new StrV("true"));
		}
		else if( this.GetOp() == CompExpr.GREATER_EQUAL ){
			if(parsed_float1 >= parsed_float2)
				return new Result(env, new StrV("true"));
		}
		else if( this.GetOp() == CompExpr.GREATER_THAN ){
			if(parsed_float1 > parsed_float2)
				return new Result(env, new StrV("true"));
		}
		else if( this.GetOp() == CompExpr.LESS_EQUAL ){
			if(parsed_float1 <= parsed_float2)
				return new Result(env, new StrV("true"));
		}
		else if( this.GetOp() == CompExpr.LESS_THAN ){
			if(parsed_float1 < parsed_float2)
				return new Result(env, new StrV("true"));
		}
		return new Result(env, new StrV("false"));		
	}
	
	private int op;  // > < >= <= = <>
	private Expr oprnd1, oprnd2;
	
	public static final int LESS_THAN = 1;
	public static final int LESS_EQUAL = 2;
	public static final int GREATER_THAN = 3;
	public static final int GREATER_EQUAL = 4;
	public static final int EQUAL = 5;
	public static final int NOT_EQUAL = 6;
}
