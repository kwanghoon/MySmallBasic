package com.coducation.smallbasic;

public class ArithExpr extends Expr
{
	ArithExpr(Expr oprnd1, int op, Expr oprnd2)
	{
		super();
		this.oprnd1 = oprnd1;
		this.op = op;
		this.oprnd2 = oprnd2;
	} // Builder
	
	ArithExpr(int op, Expr oprnd1)
	{
		super();
		this.op = op;
		this.oprnd1 = oprnd1;
		this.oprnd2 = null; // Thisisok?
	}
	
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

		Result oprnd1 = temp_ex[0].evalExpr(env);
		Result oprnd2 = temp_ex[1].evalExpr(env);
		StrV sv1, sv2;
		
		float parsed_float1, parsed_float2;
		String oprnd1_str, oprnd2_str;
		
		parsed_float1 = parsed_float2 = 0;
		oprnd1_str = oprnd2_str = null;

		
		if(oprnd1.getValue() instanceof StrV){
			sv1 = (StrV)(oprnd1.getValue());
			oprnd1_str = sv1.getValue();
		}
		else {
			//예외 경우 테스트 확인
			System.err.println("ArithExpr : err");
		}
		
		if(oprnd2.getValue() instanceof StrV){
			sv2 = (StrV)(oprnd2.getValue());
			oprnd2_str = sv2.getValue();
		}
		else {
			System.err.println("ArithExpr : err");
		}
		
		try{
			parsed_float1 = Float.parseFloat(oprnd1_str);
		}
		catch(NumberFormatException nfe){
			parsed_float1 =0;
		}
		try{
			parsed_float2 = Float.parseFloat(oprnd2_str);
		}
		catch(NumberFormatException nfe){
			parsed_float2 =0;
		}
		
		if( this.GetOp() ==ArithExpr.PLUS ){			
			if(Interpreter.isNum(oprnd1_str) && Interpreter.isNum(oprnd2_str))
				return new Result(env, new StrV(Float.toString(parsed_float1 + parsed_float2)) );
			else
				return new Result(env, new StrV(oprnd1_str + oprnd2_str));
		}
		else if (this.GetOp() ==ArithExpr.MINUS){
			if(Interpreter.isNum(oprnd1_str) && Interpreter.isNum(oprnd2_str))
				return new Result(env, new StrV(Float.toString(parsed_float1 - parsed_float2)) );
			else
				return new Result(env, new StrV(oprnd1_str + oprnd2_str));
		}
		else if (this.GetOp() ==ArithExpr.MULTIFLY){
			if(Interpreter.isNum(oprnd1_str) && Interpreter.isNum(oprnd2_str))
				return new Result(env, new StrV(Float.toString(parsed_float1 * parsed_float2)) );
			else
				return new Result(env, new StrV("0"));
		}
		else if (this.GetOp() ==ArithExpr.DIVIDE){
			if(parsed_float2 == 0 || oprnd2_str == null){
				System.err.println("Divide : err");
			}
			if(Interpreter.isNum(oprnd1_str) && Interpreter.isNum(oprnd2_str))
				return new Result(env, new StrV(Float.toString(parsed_float1 * parsed_float2)) );
			else
				return new Result(env, new StrV("0"));
		}
		else if (this.GetOp() == ArithExpr.UNARY_MINUS){
			return null;
		}
		else{
			System.err.println("ArithExpr : err");
			return null;
		}
	}

	private int op; // O + O, O ­ O, O * O, O / O, ­O
	private Expr oprnd1, oprnd2;
	
	public static final int PLUS = 1;
	public static final int MINUS = 2;
	public static final int MULTIFLY = 3;
	public static final int DIVIDE = 4;
	public static final int UNARY_MINUS = 5;
}
