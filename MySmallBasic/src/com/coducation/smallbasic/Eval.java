package com.coducation.smallbasic;

public class Eval {
	BlockStmt tree;
	int numberOfIndent;

	
	public Eval() {
	}

	public Eval(BlockStmt tree) {
		
	}

	public void eval() {
		
	}

	public Env eval(BasicBlockEnv bbEnv, Env env, Assign assignStmt) {
		return null;
	}

	public Env eval(BasicBlockEnv bbEnv, Env env, BlockStmt blockStmt) {
		return null;
	}

	public Env eval(BasicBlockEnv bbEnv, Env env, ExprStmt exprStmt) {
		return null;
	}

	public Env eval(BasicBlockEnv bbEnv, Env env, ForStmt forStmt) {
		return null;
	}

	public Env eval(BasicBlockEnv bbEnv, Env env, GotoStmt gotoStmt) {
		return null;
	}

	public Env eval(BasicBlockEnv bbEnv, Env env, IfStmt ifStmt) {
		return null;
	}

	public Env eval(BasicBlockEnv bbEnv, Env env, Label labelStmt) {
		return null;
	}

	public Env eval(BasicBlockEnv bbEnv, Env env, SubDef subDefStmt) {
		return null;
	}

	public Env eval(BasicBlockEnv bbEnv, Env env, WhileStmt whileStmt) {
		return null;
	}

	public void eval(BasicBlockEnv bbEnv, Env env, Stmt stmt) {
		if (stmt instanceof Assign)
			eval(bbEnv, env, (Assign) stmt);
		else if (stmt instanceof BlockStmt)
			eval(bbEnv, env, (BlockStmt) stmt);
		else if (stmt instanceof ExprStmt)
			eval(bbEnv, env, (ExprStmt) stmt);
		else if (stmt instanceof ForStmt)
			eval(bbEnv, env, (ForStmt) stmt);
		else if (stmt instanceof GotoStmt)
			eval(bbEnv, env, (GotoStmt) stmt);
		else if (stmt instanceof IfStmt)
			eval(bbEnv, env, (IfStmt) stmt);
		else if (stmt instanceof Label)
			eval(bbEnv, env, (Label) stmt);
		else if (stmt instanceof SubDef)
			eval(bbEnv, env, (SubDef) stmt);
		else if (stmt instanceof WhileStmt)
			eval(bbEnv, env, (WhileStmt) stmt);
		else
			System.err.println("Syntax Error!" + stmt.getClass());
	}

	public Value eval(Env env, ArithExpr arithExpr) {
		return null;
	}

	public Value eval(Env env, Array arrayExpr) {
		return null;
	}

	public Value eval(Env env, CompExpr compExpr) {
		return null;
	}

	public Value eval(Env env, Lit litExpr) {
		return null;
	}

	public Value eval(Env env, LogicalExpr logicalExpr) {
		return null;
	}

	public Value eval(Env env, MethodCallExpr methodCallExpr) {
		return null;
	}
	
	public Value eval(Env env, ParenExpr parenExpr) {
		return null;
	}
	
	public Value eval(Env env, PropertyExpr propertyExpr) {
		return null;
	}

	public Value eval(Env env, SubCallExpr subCallExpr) {
		return null;
	}

	public void eval(Var var) {
		System.out.print(var.getVarName());
	}

	public void eval(Env env, Expr expr) {
		if (expr instanceof ArithExpr)
			eval(env, (ArithExpr) expr);
		else if (expr instanceof Array)
			eval(env, (Array) expr);
		else if (expr instanceof CompExpr)
			eval(env, (CompExpr) expr);
		else if (expr instanceof Lit)
			eval(env, (Lit) expr);
		else if (expr instanceof LogicalExpr)
			eval(env, (LogicalExpr) expr);
		else if (expr instanceof MethodCallExpr)
			eval(env, (MethodCallExpr) expr);
		else if (expr instanceof ParenExpr)
			eval(env, (ParenExpr) expr);
		else if (expr instanceof PropertyExpr)
			eval(env, (PropertyExpr) expr);
		else if (expr instanceof SubCallExpr)
			eval(env, (SubCallExpr) expr);
		else if (expr instanceof Var)
			eval((Var) expr);
		else
			System.err.println("Syntax Error! " + expr.getClass());
	}
}
