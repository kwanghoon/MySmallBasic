package com.coducation.smallbasic;


public class Expr
{
	public Expr(){}
	public void prettyPrint()
	{
//		if(this instanceof Lit)
//		{
//			Lit temp = (Lit) this;
//			System.out.print(temp.lit);	
//		}
//		else if(this instanceof Var)
//		{
//			Var temp = (Var) this;
//			System.out.print(temp.name);
//		}
//		else if(this instanceof ArithExpr)
//		{
//			ArithExpr temp = (ArithExpr) this;
//			temp.oprnd1.prettyPrint();
//			System.out.print(" " + temp.op + " ");
//			temp.oprnd2.prettyPrint();
//		}
//		
//		else if(this instanceof LogicalExpr)
//		{
//			LogicalExpr temp = (LogicalExpr) this;
//			temp.oprnd1.prettyPrint();
//			System.out.print(" " + temp.op + " ");
//			temp.oprnd2.prettyPrint();
//		}
//		else if(this instanceof CompExpr)
//		{
//			CompExpr temp = (CompExpr) this;
//			temp.oprnd1.prettyPrint();
//			System.out.print(" " + temp.op + " ");
//			temp.oprnd2.prettyPrint();
//		}
//		else if(this instanceof CondExpr)
//		{
//			this.prettyPrint();
//		}
//		else if(this instanceof PropertyExpr)
//		{
//			PropertyExpr temp = (PropertyExpr) this;
//			System.out.print(temp.obj + "." + temp.name);
//		}
//		else if(this instanceof MethodCallExpr)
//		{
//			MethodCallExpr temp = (MethodCallExpr) this;
//			System.out.print(temp.obj + "." + temp.name + "(");
//			for(int i = 0; i < temp.args.length; i++)
//			{
//				temp.args[i].prettyPrint();
//				if(i < temp.args.length - 1)
//					System.out.print(",");
//			}
//			System.out.print(")");
//		}
//		else if(this instanceof SubCallExpr)
//		{
//			SubCallExpr temp = (SubCallExpr) this;
//			System.out.print(temp.name + "()");
//		}
//		else
//			System.err.println("Something Error...");
	}
}


