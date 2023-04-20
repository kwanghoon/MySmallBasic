package com.coducation.smallbasic;

public class LogicalExpr extends CondExpr
{
	public LogicalExpr(Expr oprnd1, int op, Expr oprnd2)
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

		Result oprnd1 = temp_ex[0].evalExpr(env);
		Result oprnd2 = temp_ex[1].evalExpr(env);
		StrV sv1, sv2;
		
		boolean parsedBool1,parsedBool2;
		String oprnd1_str, oprnd2_str;
		oprnd1_str = oprnd2_str = null;
		parsedBool1 = parsedBool2 = false;

		
		if(oprnd1.getValue() instanceof StrV){
			sv1 = (StrV)(oprnd1.getValue());
			oprnd1_str = sv1.getValue();
		}
		else {
			//예외 경우 테스트 확인
			System.err.println("LogicalExpr : err");
		}

		if(oprnd2.getValue() instanceof StrV){
			sv2 = (StrV)(oprnd2.getValue());
			oprnd2_str = sv2.getValue();
		}
		else {
			System.err.println("LogicalExpr : err");
		}

		parsedBool1 = Interpreter.parsetoBoolean(oprnd1_str);
		parsedBool2 = Interpreter.parsetoBoolean(oprnd2_str);
	
		if( this.GetOp() == LogicalExpr.AND ){
				if(parsedBool1 && parsedBool2){
					return new Result(env, new StrV("true"));
				}
		}
		else if( this.GetOp() == LogicalExpr.OR ){
				if(parsedBool1 || parsedBool2){
					return new Result(env, new StrV("true"));
				}
		}
		else{
			//TODO : 예외 추가
			return null;
		}
		
		return new Result(env, new StrV("false"));
	}
	
	private int op;  // "And", "Or"
	private Expr oprnd1, oprnd2;
	
	public static final int AND = 1;
	public static final int OR = 2;
}
