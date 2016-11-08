package com.coducation.smallbasic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class Eval {
	int numberOfIndent;
	String label;
	BasicBlockEnv bbEnv;
	Env env;

	public Eval() {
	}

	public Eval(BasicBlockEnv bbEnv) {
		this.bbEnv = bbEnv;
	}

	public void eval() {
		label = "$main";
		env = new Env();

		while (label != null) {
			Stmt stmt = bbEnv.get(label);
			label = null;
			eval(bbEnv, env, stmt);
		}
		// End
	}

	public void eval(BasicBlockEnv bbEnv, Env env, Assign assignStmt) {
		Expr lhs = assignStmt.getLSide();
		Expr rhs = assignStmt.getRSide();

		// Value v1 = eval(env, lhs);
		Value v2 = eval(env, rhs);

		if (lhs instanceof Var) {
			env.put(((Var) lhs).getVarName(), v2);
		} else if (lhs instanceof PropertyExpr) {
			try {
				String clzName = ((PropertyExpr) lhs).getObj();
				Class clz = clzName.getClass();
				Field fld = clz.getField(((PropertyExpr) lhs).getName());
				fld.set(null, v2);
			} catch (NoSuchFieldException | SecurityException e) {
				throw new InterpretException("Assign : " + e.toString());
			} catch (IllegalArgumentException e) {
				throw new InterpretException("Assign : " + e.toString());
			} catch (IllegalAccessException e) {
				throw new InterpretException("Assign : " + e.toString());
			}
		} else {
			throw new InterpretException("Assign : Unknown lhs " + lhs);
		}
	}

	public void eval(BasicBlockEnv bbEnv, Env env, BlockStmt blockStmt) {
		for (Stmt stmt : blockStmt.getAL()) {
			eval(bbEnv, env, stmt);
		}
	}

	public void eval(BasicBlockEnv bbEnv, Env env, ExprStmt exprStmt) {
		Expr expr = exprStmt.getExpr();

		Value v = eval(env, expr);

	}

	public void eval(BasicBlockEnv bbEnv, Env env, ForStmt forStmt) {
		throw new InterpretException("ForStmt : Unexpected");
	}

	public void eval(BasicBlockEnv bbEnv, Env env, GotoStmt gotoStmt) {
		label = gotoStmt.getTargetLabel();
	}

	public void eval(BasicBlockEnv bbEnv, Env env, IfStmt ifStmt) {
		Expr cond = ifStmt.getCond();

		Value v1 = eval(env, cond);

		if (isTrue(v1)) {
			Stmt thenStmt = ifStmt.getThen();
			eval(bbEnv, env, thenStmt);
		} else {
			Stmt elseStmt = ifStmt.getElse();
			if (elseStmt != null)
				eval(bbEnv, env, elseStmt);
		}
	}

	public void eval(BasicBlockEnv bbEnv, Env env, Label labelStmt) {
		throw new InterpretException("Label : Unexpected");
	}

	public void eval(BasicBlockEnv bbEnv, Env env, SubDef subDefStmt) {
		throw new InterpretException("SubDef : Unexpected");
	}

	public void eval(Env env, SubCallExpr subCallExpr) {
		// 1. Let label be the label of the Sub call
		// 2. evalBlock(label)
		label = subCallExpr.getName();
		while (label != null) {
			Stmt stmt = bbEnv.get(label);
			label = null;
			eval(bbEnv, env, stmt);
		}
	}

	public void eval(BasicBlockEnv bbEnv, Env env, WhileStmt whileStmt) {
		throw new InterpretException("WhileStmt : Unexpected");
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
		else if (stmt instanceof SubCallExpr)
			eval(bbEnv, env, (SubCallExpr) stmt);
		else if (stmt instanceof WhileStmt)
			eval(bbEnv, env, (WhileStmt) stmt);
		else
			throw new InterpretException("Syntax Error!" + stmt.getClass());
	}

	public Value eval(Env env, ArithExpr arithExpr) {
		return null;
	}

	public Value eval(Env env, Array arrayExpr) {
		return null;
	}

	public static boolean isTrue(Value v) {
		if (v instanceof StrV) {
			StrV str = (StrV) v;
			return str.getValue().equalsIgnoreCase("True");
		} else
			return false;
	}

	public static boolean greaterEqual(Value v1, Value v2) {
		// 1) StrV >= StrV
		// 2) DoubleV >= DoubleV
		// 3) error
		if (v1 instanceof StrV && v2 instanceof StrV) {
			String strV1 = ((StrV) v1).getValue().toString();
			String strV2 = ((StrV) v2).getValue().toString();

			if (strV1.compareTo(strV2) >= 0) // compareTo 결과 양수가 나오면 앞의 문자열이 뒤의
												// 문자열보다 크다는 것을 의미
				return true;
		} else if (v1 instanceof DoubleV && v2 instanceof DoubleV) {
			double doubleV1 = ((DoubleV) v1).getValue();
			double doubleV2 = ((DoubleV) v2).getValue();

			if (doubleV1 >= doubleV2)
				return true;
		} else
			throw new InterpretException("Different Value is not comparable.");

		return false;
	}

	public static boolean greaterThan(Value v1, Value v2) {
		if (v1 instanceof StrV && v2 instanceof StrV) {
			String strV1 = ((StrV) v1).getValue().toString();
			String strV2 = ((StrV) v2).getValue().toString();

			if (strV1.compareTo(strV2) > 0)
				return true;
		} else if (v1 instanceof DoubleV && v2 instanceof DoubleV) {
			double doubleV1 = ((DoubleV) v1).getValue();
			double doubleV2 = ((DoubleV) v2).getValue();

			if (doubleV1 > doubleV2)
				return true;
		} else
			throw new InterpretException("Different Value is not comparable.");

		return false;
	}

	public static boolean lessEqual(Value v1, Value v2) {
		if (v1 instanceof StrV && v2 instanceof StrV) {
			String strV1 = ((StrV) v1).getValue().toString();
			String strV2 = ((StrV) v2).getValue().toString();

			if (strV1.compareTo(strV2) <= 0)
				return true;
		} else if (v1 instanceof DoubleV && v2 instanceof DoubleV) {
			double doubleV1 = ((DoubleV) v1).getValue();
			double doubleV2 = ((DoubleV) v2).getValue();

			if (doubleV1 <= doubleV2)
				return true;
		} else
			throw new InterpretException("Different Value is not comparable.");

		return false;
	}

	public static boolean lessThan(Value v1, Value v2) {
		if (v1 instanceof StrV && v2 instanceof StrV) {
			String strV1 = ((StrV) v1).getValue().toString();
			String strV2 = ((StrV) v2).getValue().toString();

			if (strV1.compareTo(strV2) < 0)
				return true;
		} else if (v1 instanceof DoubleV && v2 instanceof DoubleV) {
			double doubleV1 = ((DoubleV) v1).getValue();
			double doubleV2 = ((DoubleV) v2).getValue();

			if (doubleV1 < doubleV2)
				return true;
		} else
			throw new InterpretException("Different Value is not comparable.");

		return false;
	}

	public static boolean notEqual(Value v1, Value v2) {
		if (v1 instanceof StrV && v2 instanceof StrV) {
			String strV1 = ((StrV) v1).getValue().toString();
			String strV2 = ((StrV) v2).getValue().toString();

			if (strV1 != strV2)
				return true;
			else
				return false;
		} else if (v1 instanceof DoubleV && v2 instanceof DoubleV) {
			double doubleV1 = ((DoubleV) v1).getValue();
			double doubleV2 = ((DoubleV) v2).getValue();

			if (doubleV1 != doubleV2)
				return true;
		} else
			throw new InterpretException("Different Value is not comparable.");

		return false;
	}

	public Value eval(Env env, CompExpr compExpr) {
		Expr oprnd1 = compExpr.GetOperand()[0];
		Expr oprnd2 = compExpr.GetOperand()[1];

		Value v1 = eval(env, oprnd1);
		Value v2 = eval(env, oprnd2);

		switch (compExpr.GetOp()) {
		case CompExpr.EQUAL:
			if (v1 == v2)
				return new StrV("true"); // v1.equals(v2);
			else
				return new StrV("false");
		case CompExpr.GREATER_EQUAL:
			return new StrV(Boolean.toString(greaterEqual(v1, v2)));
		case CompExpr.GREATER_THAN:
			return new StrV(Boolean.toString(greaterThan(v1, v2)));
		case CompExpr.LESS_EQUAL:
			return new StrV(Boolean.toString(lessEqual(v1, v2)));
		case CompExpr.LESS_THAN:
			return new StrV(Boolean.toString(lessThan(v1, v2)));
		case CompExpr.NOT_EQUAL:
			return new StrV(Boolean.toString(notEqual(v1, v2)));
		default:
			break;
		}
		return null;
	}

	public Value eval(Env env, Lit litExpr) {
		switch (litExpr.type()) {
		case Lit.NUM:
			return new DoubleV(litExpr.getD());
		case Lit.STRING:
			return new StrV(litExpr.gets());
		default:
			throw new InterpretException("eval " + litExpr.gets() + " : Unknown type " + litExpr.type());
		}
	}

	public Value eval(Env env, LogicalExpr logicalExpr) {
		Expr oprnd1 = logicalExpr.GetOperand()[0];
		Expr oprnd2 = logicalExpr.GetOperand()[1];

		Value v1 = eval(env, oprnd1);
		Value v2 = eval(env, oprnd2);

		switch (logicalExpr.GetOp()) {
		case LogicalExpr.AND:

		case LogicalExpr.OR:

		default:
			break;
		}
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
		try {
			String clzName = propertyExpr.getObj();
			Class clz = clzName.getClass();
			Field fld = clz.getField(propertyExpr.getName());
			return (Value) fld.get(null);
		} catch (NoSuchFieldException | SecurityException e) {
			throw new InterpretException("PropertyExpr : " + e.toString());
		} catch (IllegalArgumentException e) {
			throw new InterpretException("PropertyExpr : " + e.toString());
		} catch (IllegalAccessException e) {
			throw new InterpretException("PropertyExpr : " + e.toString());
		}
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
		else if (expr instanceof Var)
			return eval(env, (Var) expr);
		else
			throw new InterpretException("Syntax Error! " + expr.getClass());

	}
}
