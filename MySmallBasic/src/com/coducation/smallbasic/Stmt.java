package com.coducation.smallbasic;

class Stmt
{
	public Stmt()
	{
	} // Builder
	
	public Result evalStmt(Env env){
		return new Result(env , null);
	}
	
    public void prettyprint()
	{
//		if(this instanceof ExprStmt)
//		{
//			ExprStmt temp = (ExprStmt) this;
//			temp.expr.prettyPrint();
//		}
//		else if(this instanceof Assign)
//		{
//			Assign temp = (Assign) this;
//			
//			temp.lhs.prettyPrint();
//			System.out.print("=");
//			temp.rhs.prettyPrint();
//		}
//		else if(this instanceof IfStmt)
//		{
//			IfStmt temp = (IfStmt) this;
//			System.out.print("If ");
//			temp.cond.prettyPrint();
//			System.out.println(" Then");
//			temp._then.prettyprint();
//			
//			if(temp._else != null)
//			{
//				System.out.println("\n" + "Else");
//				temp._else.prettyprint();
//			}
//			System.out.print("\n" + "EndIf");
//		}
//		else if(this instanceof WhileStmt)
//		{
//			WhileStmt temp = (WhileStmt) this;
//			System.out.print("While(");
//			temp.cond.prettyPrint();
//			System.out.println(")");
//			temp.block.prettyprint();
//			System.out.print("\n" + "EndWhile");
//		}
//		else if(this instanceof BlockStmt){
//			BlockStmt temp = (BlockStmt) this;
//			
//			System.out.println("{");
//			for(int i =0; i < temp.stmts.length; i++)
//			{
//				temp.stmts[i].prettyprint();
//				System.out.println();
//			}
//			System.out.print("}");
//		}
//		else if(this instanceof Label)
//		{
//			Label temp = (Label)this;
//			System.out.print(temp.label +":");
//		}
//		else if(this instanceof GotoStmt)
//		{
//			GotoStmt temp = (GotoStmt) this;
//			System.out.print("Goto " + temp.targetLabel);			
//		}
//		else if(this instanceof ForStmt)
//		{
//			ForStmt temp = (ForStmt) this;
//			
//			System.out.print("For ");
//			temp.var.prettyPrint();
//			// For Var = init To End [Step]
//			System.out.print("=");
//			temp.init.prettyPrint();
//			System.out.print(" To ");
//			temp.end.prettyPrint();
//			if(temp.isStep)
//			{
//				System.out.print(" Step " );
//				temp.step.prettyPrint();
//			}
//			System.out.println();
//			temp.block.prettyprint();
//			System.out.print("\n" + "EndFor");
//		}
//		else if(this instanceof SubDef)
//		{
//			SubDef temp = (SubDef) this;
//			System.out.println("Sub " + temp.name);
//			temp.block.prettyprint();
//			System.out.print("\n" + "EndSub");
//		}
//		else
//		{
//			System.err.printf("Something Error....");
//		}
	}

}