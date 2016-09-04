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
		Result res;
		Expr[] temp_ex = this.GetOperand();
		StrV sv1, sv2;

		float parsed_float1, parsed_float2;
		String oprnd1_str, oprnd2_str;
		
		parsed_float1 = parsed_float2 = 0;
		oprnd1_str = oprnd2_str = null;

		
		
		
		Result oprnd1 = temp_ex[0].evalExpr(env);
		
		if(oprnd1.getValue() instanceof StrV){
			sv1 = (StrV)(oprnd1.getValue());
			oprnd1_str = sv1.getValue();
		}
		else {
			//다른 경우 테스트 확인
			System.err.println("ArithExpr : err");
		}
		
		//Unary case : temp_ex[1] is null.
		if(temp_ex[1] != null){
			Result oprnd2 = temp_ex[1].evalExpr(env);
			
			if(oprnd2.getValue() instanceof StrV){
				sv2 = (StrV)(oprnd2.getValue());
				oprnd2_str = sv2.getValue();
			}
			else {
				System.err.println("ArithExpr : err");
			}
		}
		

		try{
			parsed_float1 = Float.parseFloat(oprnd1_str);
		}
		catch(Exception e){
			parsed_float1 =0;
		}
		try{
			parsed_float2 = Float.parseFloat(oprnd2_str);
		}
		catch(Exception e){
			parsed_float2 =0;
		}
		
		
		
		if( this.GetOp() ==ArithExpr.PLUS ){
			//numeric case :
			if(Interpreter.isNum(oprnd1_str) && Interpreter.isNum(oprnd2_str))
				res = new Result(env, new StrV(Float.toString(parsed_float1 + parsed_float2)) );
			//Character case :
			else
				res = new Result(env, new StrV(oprnd1_str + oprnd2_str));
		}
		else if (this.GetOp() ==ArithExpr.MINUS){
			if(Interpreter.isNum(oprnd1_str) && Interpreter.isNum(oprnd2_str))
				res = new Result(env, new StrV(Float.toString(parsed_float1 - parsed_float2)) );
			else
				res = new Result(env, new StrV("0"));
		}
		else if (this.GetOp() ==ArithExpr.MULTIFLY){
			if(Interpreter.isNum(oprnd1_str) && Interpreter.isNum(oprnd2_str))
				res = new Result(env, new StrV(Float.toString(parsed_float1 * parsed_float2)) );
			else
				res = new Result(env, new StrV("0"));
		}
		else if (this.GetOp() ==ArithExpr.DIVIDE){
			if(parsed_float2 == 0 || oprnd2_str == null){
				System.err.println("Divide : err");
				return null;
			}
			if(Interpreter.isNum(oprnd1_str) && Interpreter.isNum(oprnd2_str))
				res = new Result(env, new StrV(Float.toString(parsed_float1 / parsed_float2)) );
			else
				res = new Result(env, new StrV("0"));
		}
		else if (this.GetOp() == ArithExpr.UNARY_MINUS){
			res = new Result(env, new StrV(Float.toString(-parsed_float1)) );
		}
		else{
			System.err.println("ArithExpr : err");
			return null;
		}
		
		//Check .0 
		try{
			String str = ((StrV)(res.getValue())).getValue();	//res->String value.
			
			float temp_float =Float.parseFloat(str);
			int temp_int = (int)temp_float;
			
			if( temp_float - temp_int == 0 ){
				res = new Result(env, new StrV(Integer.toString(temp_int)));
			}
		}
		catch(Exception e){
			//Alphabetic value case : pass
			;
		}
		
		return res;
	}

	private int op; // O + O, O ­ O, O * O, O / O, ­O
	private Expr oprnd1, oprnd2;
	
	public static final int PLUS = 1;
	public static final int MINUS = 2;
	public static final int MULTIFLY = 3;
	public static final int DIVIDE = 4;
	public static final int UNARY_MINUS = 5;
}
