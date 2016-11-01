package com.coducation.smallbasic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Eval {
	BlockStmt tree;
	int numberOfIndent;

	public Eval() {
	}

	public Eval(BlockStmt tree) {
		this.tree = tree;
	}

	public void eval() {
		String label = "$main";
		while (label != null) {
			label = evalBlock(label);
		}
		// End
	}

	public String evalBlock(String label) {
		// 1. Get a stmt block of label
		// 2. Execute it until either Goto l or the empty stmt
		// 3. Return l or null
		return null;
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

		switch (compExpr.GetOp()) {
		case CompExpr.EQUAL:
			Expr oprnd1 = compExpr.GetOperand()[0];
			Expr oprnd2 = compExpr.GetOperand()[1];

			Value v1 = eval(env, oprnd1);
			Value v2 = eval(env, oprnd2);

			if (v1 == v2)
				return new StrV("true"); // v1.equals(v2);
			else
				return new StrV("false");
		case CompExpr.GREATER_EQUAL:
			break;
		case CompExpr.GREATER_THAN:
			break;
		case CompExpr.LESS_EQUAL:
			break;
		case CompExpr.LESS_THAN:
			break;
		case CompExpr.NOT_EQUAL:
			break;
		default:
			break;
		}

		return null;
	}

	public Value eval(Env env, Lit litExpr) {
		return null;
	}

	public Value eval(Env env, LogicalExpr logicalExpr) {
		return null;
	}

	public Value eval(Env env, MethodCallExpr methodCallExpr) {
		String mthName = methodCallExpr.getName();
		String clzName = methodCallExpr.getObj();
		ArrayList<Expr> args = methodCallExpr.getArgs();

		ArrayList<Value> argValues = new ArrayList<Value>();

		for (Expr arg : args) {
			Value v = eval(env, arg);
			argValues.add(v);
		}
		try {
			Class c = clzName.getClass();
			Method m = c.getMethod(mthName, null);
			m.invoke(null, argValues);

		} catch (NoSuchMethodException e) {
			throw new InterpretException(e.toString());
		} catch (IllegalAccessException e) {
			throw new InterpretException(e.toString());
		} catch (IllegalArgumentException e) {
			throw new InterpretException(e.toString());
		} catch (InvocationTargetException e) {
			throw new InterpretException(e.toString());
		}
		return null;
	}

	public Value eval(Env env, ParenExpr parenExpr) {
		return null;
	}

	public Value eval(Env env, PropertyExpr propertyExpr) {
		return null;
	}

	public Value eval(Env env, SubCallExpr subCallExpr) {
		// 1. Let label be the label of the Sub call
		// 2. evalBlock(label)
		return null;
	}

	public double toDouble(String strDouble) {
		try {
			return Double.parseDouble(strDouble);
		} catch (NumberFormatException e) {
			throw new InterpretException(e.toString());
		}
	}

	public Value eval(Env env, Var var) {
		return env.get(var.getVarName());
		// System.out.print(var.getVarName());
	}

	public Value eval(Env env, Expr expr) {
		if (expr instanceof ArithExpr)
			return eval(env, (ArithExpr) expr);
		else if (expr instanceof Array)
			return eval(env, (Array) expr);
		else if (expr instanceof CompExpr)
			return eval(env, (CompExpr) expr);
		else if (expr instanceof Lit)
			return eval(env, (Lit) expr);
		else if (expr instanceof LogicalExpr)
			return eval(env, (LogicalExpr) expr);
		else if (expr instanceof MethodCallExpr)
			return eval(env, (MethodCallExpr) expr);
		else if (expr instanceof ParenExpr)
			return eval(env, (ParenExpr) expr);
		else if (expr instanceof PropertyExpr)
			return eval(env, (PropertyExpr) expr);
		else if (expr instanceof SubCallExpr)
			return eval(env, (SubCallExpr) expr);
		else if (expr instanceof Var)
			return eval(env, (Var) expr);
		else
			throw new InterpretException("Syntax Error! " + expr.getClass());

	}
}
