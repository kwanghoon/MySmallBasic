package com.coducation.smallbasic;

import java.util.ArrayList;

public class MethodCallExpr extends Expr
{
	public MethodCallExpr(String obj, String name, ArrayList<Expr> args) // Expr[] -> ArrayList<Expr> Change.
	{
		super();
		this.obj = obj;
		this.name = name;
		this.args = args;
	} // Builder
	
	public Result evalExpr(Env env){
		
		if(obj.equals("TextWindow") && name.equals("WriteLine")){
			int size =args.size();
			
			for(int i = 0; i < size; i++){
				Expr expr = args.get(i);
				
				if(expr instanceof Lit){
					System.out.println(((Lit) expr).gets());
				}
				else if( expr instanceof Var){
					Var v = (Var)expr;
					Value val = env.getValue( v.getVarName() );
					
					System.out.println( ((StrV)val).getValue() );
				}
				else if (expr instanceof ArithExpr){
					ArithExpr v = (ArithExpr)expr;
					Result res = v.evalExpr(env);
					
					System.out.println( ((StrV)(res.getValue())).getValue() );
				}
			}
		}
		
		return new Result(env);
	}

	private String obj, name;
	private ArrayList<Expr> args;
}
