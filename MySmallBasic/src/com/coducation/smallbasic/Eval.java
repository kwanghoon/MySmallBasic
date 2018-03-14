package com.coducation.smallbasic;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Eval {
	static boolean debug;
	static boolean debugexpr = false;
	static BasicBlockEnv bbEnv;
	static Env env;
	static final String lib = "com.coducation.smallbasic.lib.";
	static final String notifyFieldAssign = "notifyFieldAssign";
	static final String notifyFieldRead = "notifyFieldRead";
	static Eval eval;

	public Eval() {
	}

	public Eval(BasicBlockEnv bbEnv) {
		this.bbEnv = bbEnv;
		this.eval = this;
	}

	// For GetArgument, ArgumentCount in Program Library:
	private static String[] programArgs;

	public static String[] getProgramArgs() {
		return programArgs;
	}

	public void eval(String[] args) {
		debug = Config.getDebug();

		programArgs = args;
		eval();
	}

	public void eval() {
		env = new Env();
		env.label("$main");

		while (env.label() != null) {
			Stmt stmt = bbEnv.get(env.label());
			env.label(null);
			eval(bbEnv, env, stmt);
		}
		// End
	}

	public void eval(BasicBlockEnv bbEnv, Env env, Assign assignStmt) {
		Expr lhs = assignStmt.getLSide();
		Expr rhs = assignStmt.getRSide();

		// Value v1 = eval(env, lhs);
		Value v2 = eval(env, rhs);

		// Assigning an array is done by copy.
		if (v2 instanceof ArrayV) {
			v2 = ((ArrayV)v2).copy();
		}

		if (lhs instanceof Var) {
			env.put(((Var) lhs).getVarName(), v2);
		} else if (lhs instanceof PropertyExpr) {
			try {
				String clzName = ((PropertyExpr) lhs).getObj();
				Class clz = getClass(clzName);
				Field fld = clz.getField(((PropertyExpr) lhs).getName());
				fld.set(null, v2);

				// After any field assignment, invoke notifyFieldAssign for
				// Library to know
				// the change of the field value.
				Method mth = clz.getMethod(notifyFieldAssign, String.class);
				mth.invoke(null, ((PropertyExpr) lhs).getName());
			} catch (NoSuchFieldException e) {
				throw new InterpretException("Assign : " + e.toString(), ((PropertyExpr) lhs).getName(), lhs.lineno(), lhs.charat());
			} catch (SecurityException e) { 
				throw new InterpretException("Assign : " + e.toString());
			} catch (IllegalArgumentException e) {
				throw new InterpretException("Assign : " + e.toString());
			} catch (IllegalAccessException e) {
				throw new InterpretException("Assign : " + e.toString());
			} catch (ClassNotFoundException | NoClassDefFoundError e) {
				throw new InterpretException("Class Not Found " + e.toString(), ((PropertyExpr) lhs).getObj(), lhs.lineno(), lhs.charat());
			} catch (NoSuchMethodException e) {
				throw new InterpretException("Method Not Found " + e.toString());
			} catch (InvocationTargetException e) {
				throw new InterpretException("Target Not Found " + e.toString() + ": ");
			}
		} else if (lhs instanceof Array) {
			Array arr = (Array) lhs;
			Value arrValue = env.get(arr.getVar());
			ArrayV elem;
			if (arrValue == null)
				elem = null;
			else if (arrValue instanceof ArrayV)
				elem = (ArrayV) arrValue;
			else {
				elem = null;
			}

			if (elem == null) {
				elem = new ArrayV();
				env.put(arr.getVar(), elem);
			}

			for (int i = 0; i < arr.getDim(); i++) {
				Expr idx = arr.getIndex(i);
				Value v = eval(env, idx);
				String idx_s;

				if (v == null || v.toString().trim().equals(""))
					idx_s = "0";
				else if (v instanceof StrV || v instanceof DoubleV) {
					idx_s = v.toString();
				} else {
					throw new InterpretException("Unexpected Index" + v);
				}

				if (i < arr.getDim() - 1) {
					ArrayV elem_elem = (ArrayV) elem.get(idx_s);
					if (elem_elem == null) {
						elem_elem = new ArrayV();
						elem.put(idx_s, elem_elem);
					}
					elem = elem_elem;
				} else {
					elem.put(idx_s, v2);
				}
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
		env.label(gotoStmt.getTargetLabel());
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

	public void eval(BasicBlockEnv bbEnv, Env env, SubCallExpr subCallExpr) {
		// 1. Let label be the label of the Sub call
		// 2. evalBlock(label)
		env.label(subCallExpr.getName());
		while (env.label() != null) {
			Stmt stmt = bbEnv.get(env.label());
			env.label(null);
			eval(bbEnv, env, stmt);
		}
	}

	public void eval(BasicBlockEnv bbEnv, Env env, WhileStmt whileStmt) {
		throw new InterpretException("WhileStmt : Unexpected");
	}

	public void eval(BasicBlockEnv bbEnv, Env env, Stmt stmt) {
		if (debug)
			printLog(stmt);

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
		// Unary 연산자
		if (arithExpr.GetOp() == ArithExpr.UNARY_MINUS) {
			Expr oprnd1 = arithExpr.GetOperand()[0];
			Value v1 = eval(env, oprnd1);
			if (v1 instanceof DoubleV) {
				DoubleV d1 = new DoubleV(v1.getNumber());
				return new DoubleV(-d1.getValue());
			}
			else if(v1 instanceof StrV && ((StrV) v1).isNumber())
				return new DoubleV(((StrV) v1).parseDouble());
			else if(v1 instanceof StrV)
				return new DoubleV(0);
			else
				throw new InterpretException("UNARY_MINUS 1st operand unexpected " + v1);
			// if (v1 instanceof DoubleV) {
			// d1 = (DoubleV) v1;
			// return new DoubleV(-d1.getValue());
			// } else
			// throw new InterpretException("Syntax Error!");
		} else { // Binary 연산자
			Expr oprnd1 = arithExpr.GetOperand()[0];
			Expr oprnd2 = arithExpr.GetOperand()[1];

			Value v1 = eval(env, oprnd1);
			Value v2 = eval(env, oprnd2);

			StrV s1, s2;
			DoubleV d1, d2;
			Double dv1 = 0.0, dv2 = 0.0;

			switch (arithExpr.GetOp()) {
			case ArithExpr.PLUS:
				boolean numplus = true; // numplus == false => concatenation

				if (v1 == null)
					dv1 = 0.0;
				else if (v1 instanceof DoubleV)
					dv1 = ((DoubleV) v1).getValue();
				else if(v1 instanceof StrV && v1.toString().equals(""))
					numplus = false;
				else if (v1 instanceof StrV && ((StrV) v1).isNumber())
					dv1 = ((StrV) v1).parseDouble();
				else if (v1 instanceof StrV)
					numplus = false;
				else if (v1 instanceof ArrayV) // Added
					numplus = false;
				else
					throw new InterpretException("PLUS 1st operand unexpected" + v1);

				if (v2 == null)
					dv2 = 0.0;
				else if (v2 instanceof DoubleV)
					dv2 = ((DoubleV) v2).getValue();
				else if (v2 instanceof StrV && ((StrV) v2).isNumber())
					dv2 = ((StrV) v2).parseDouble();
				else if (v2 instanceof StrV)
					numplus = false;
				else if (v2 instanceof ArrayV) // Added
					numplus = false;
				else
					throw new InterpretException("PLUS 2nd operand unexpected" + v2);

				if (numplus == true)
					return new DoubleV(dv1 + dv2);
				else
					return new StrV(v1.toString() + v2.toString());

			case ArithExpr.MINUS:
				if (v1 == null)
					dv1 = 0.0;
				else if (v1 instanceof DoubleV)
					dv1 = ((DoubleV) v1).getValue();
				else if (v1 instanceof StrV && ((StrV) v1).isNumber())
					dv1 = ((StrV) v1).parseDouble();
				else if (v1 instanceof StrV)
					dv1 = 0.0;
				else
					throw new InterpretException("MINUS 1st operand unexpected" + v1);

				if (v2 == null)
					dv2 = 0.0;
				else if (v2 instanceof DoubleV)
					dv2 = ((DoubleV) v2).getValue();
				else if (v2 instanceof StrV && ((StrV) v2).isNumber())
					dv2 = ((StrV) v2).parseDouble();
				else if (v2 instanceof StrV)
					dv2 = 0.0;
				else
					throw new InterpretException("MINUS 2nd operand unexpected" + v2);

				return new DoubleV(dv1 - dv2);

			case ArithExpr.MULTIFLY:
				if (v1 == null)
					dv1 = 0.0;
				else if (v1 instanceof DoubleV)
					dv1 = ((DoubleV) v1).getValue();
				else if (v1 instanceof StrV && ((StrV) v1).isNumber())
					dv1 = ((StrV) v1).parseDouble();
				else if (v1 instanceof StrV)
					dv1 = 0.0;
				else
					throw new InterpretException("MULTIFLY 1st operand unexpected" + v1);

				if (v2 == null)
					dv2 = 0.0;
				else if (v2 instanceof DoubleV)
					dv2 = ((DoubleV) v2).getValue();
				else if (v2 instanceof StrV && ((StrV) v2).isNumber())
					dv2 = ((StrV) v2).parseDouble();
				else if (v2 instanceof StrV)
					dv2 = 0.0;
				else
					throw new InterpretException("MULTIFLY 2nd operand unexpected" + v2);

				return new DoubleV(dv1 * dv2);
			case ArithExpr.DIVIDE:
				if (v1 == null)
					dv1 = 0.0;
				else if (v1 instanceof DoubleV)
					dv1 = ((DoubleV) v1).getValue();
				else if (v1 instanceof StrV && ((StrV) v1).isNumber())
					dv1 = ((StrV) v1).parseDouble();
				else if (v1 instanceof StrV)
					dv1 = 0.0;
				else
					throw new InterpretException("DIVIDE 1st operand unexpected" + v1);

				if (v2 == null)
					dv2 = 1.0;
				else if (v2 instanceof DoubleV)
					dv2 = ((DoubleV) v2).getValue();
				else if (v2 instanceof StrV && ((StrV) v2).isNumber())
					dv2 = ((StrV) v2).parseDouble();
				else if (v2 instanceof StrV)
					dv2 = 0.0;
				else
					throw new InterpretException("DIVIDE 2nd operand unexpected" + v2);

				if (dv2 == 0)
					throw new InterpretException("DIVIDE 2nd operand is 0");

				return new DoubleV(dv1 / dv2);
			case ArithExpr.UNARY_MINUS:
			default:
				throw new InterpretException("Unexpected Op Code " + arithExpr.GetOp());
			}
		}
	}

	public Value eval(Env env, Array arrayExpr) {
		// arr[3]
		ArrayV arrV;
		Value elem = null;

		if (env.get(arrayExpr.getVar()) instanceof ArrayV) {
			arrV = (ArrayV) env.get(arrayExpr.getVar());
			elem = arrV;
		}

		for (int i = 0; i < arrayExpr.getDim(); i++) {
			Expr idx = arrayExpr.getIndex(i);
			Value v = eval(env, idx);
			String idx_s;

			if (v == null || v.toString().trim().equals(""))
				idx_s = "0";
			else if (v instanceof StrV || v instanceof DoubleV) {
				idx_s = v.toString();
			} else {
				throw new InterpretException("Unexpected Index" + v);
			}

			if (elem == null)
				break;
			else if (elem instanceof StrV) {
			} else
				elem = ((ArrayV) elem).get(idx_s);
		}

		if (elem == null)
			return new StrV("");
		else
			return elem;
	}

	public Value eval(Env env, CompExpr compExpr) {
		Expr oprnd1 = compExpr.GetOperand()[0];
		Expr oprnd2 = compExpr.GetOperand()[1];

		Value v1 = eval(env, oprnd1);
		Value v2 = eval(env, oprnd2);

		switch (compExpr.GetOp()) {
		case CompExpr.EQUAL:
			if (v1 instanceof StrV && v2 instanceof StrV) {
				StrV s1 = (StrV) v1;
				StrV s2 = (StrV) v2;
				if (s1.getValue().equals(s2.getValue()))
					return new StrV("true"); // v1.equals(v2);
			} else if (v1 instanceof DoubleV && v2 instanceof DoubleV) {
				DoubleV d1 = (DoubleV) v1;
				DoubleV d2 = (DoubleV) v2;
				if (d1.getValue() == d2.getValue())
					return new StrV("true");
			} else if (v1 instanceof StrV && v2 instanceof DoubleV) {
				String s1 = ((StrV) v1).getValue();
				String s2 = ((DoubleV) v2).toString();

				if (s1.equals(s2))
					return new StrV("true");
			} else if (v1 instanceof DoubleV && v2 instanceof StrV) {
				String s1 = ((DoubleV) v1).toString();
				String s2 = ((StrV) v2).getValue();

				if (s1.equals(s2))
					return new StrV("true");
			}
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
			throw new InterpretException("Unexpected CompExpr Op Code " + compExpr.GetOp());
		}
	}

	public Value eval(Env env, Lit litExpr) {
		switch (litExpr.type()) {
		case Lit.NUM:
			return new DoubleV(litExpr.getD());
		case Lit.STRING:
			return new StrV(litExpr.toString());
		default:
			throw new InterpretException("eval " + litExpr.gets() + " : Unknown type " + litExpr.type());
		}
	}

	public Value eval(Env env, LogicalExpr logicalExpr) {
		Expr oprnd1 = logicalExpr.GetOperand()[0];
		Expr oprnd2 = logicalExpr.GetOperand()[1];

		Value v1 = eval(env, oprnd1);
		Value v2 = eval(env, oprnd2);

		StrV s1, s2;

		boolean ret = false;

		switch (logicalExpr.GetOp()) {
		case LogicalExpr.AND:
			if (v1 instanceof StrV && v2 instanceof StrV) {
				s1 = (StrV) v1;
				s2 = (StrV) v2;

				if (s1.getValue().equalsIgnoreCase("true") && s2.getValue().equalsIgnoreCase("true"))
					ret = true;
			}
			break;
		case LogicalExpr.OR:
			if (v1 instanceof StrV && v2 instanceof StrV) {
				s1 = (StrV) v1;
				s2 = (StrV) v2;

				if (s1.getValue().equalsIgnoreCase("true") || s2.getValue().equalsIgnoreCase("true"))
					ret = true;
			}
			break;
		default:
			throw new InterpretException("Unexpected Logical Expr Op Code " + logicalExpr.GetOp());
		}
		return ret ? new StrV("true") : new StrV("false");
	}

	public Value eval(Env env, MethodCallExpr methodCallExpr) {
		String mthName = methodCallExpr.getName();
		String clzName = methodCallExpr.getObj();
		ArrayList<Expr> args = methodCallExpr.getArgs();

		ArrayList<Value> argValues = new ArrayList<Value>();

		if (args != null) {
			for (Expr arg : args) {
				Value v = eval(env, arg);
				argValues.add(v);
			}
		}
		try {
			Class c = getClass(clzName);
			Method m = c.getMethod(mthName, ArrayList.class);
			String retTypeName = m.getReturnType().getName();
			if ("void".equals(retTypeName) == false)
				return (Value) m.invoke(null, argValues);
			else {
				m.invoke(null, argValues);
				return null;
			}
		} catch (NoSuchMethodException e) {
			throw new InterpretException(e.toString() + mthName, mthName, methodCallExpr.lineno(), methodCallExpr.charat());
		} catch (IllegalAccessException e) {
			throw new InterpretException(e.toString());
		} catch (IllegalArgumentException e) {
			throw new InterpretException(e.toString());
		} catch (InvocationTargetException e) {
			Throwable exn = e.getCause();
			if (exn instanceof InterpretException) {
				InterpretException ie = (InterpretException) exn;
				if (ie.getProgramEnd())
					throw ie;
				else {
					ie.printStackTrace();
					throw new InterpretException(
							e.toString() + clzName + ", " + mthName + "\n" + "Caused By\n" + ie.getStackTrace());
				}
			}
			exn.printStackTrace();
			throw new InterpretException(e.toString() + clzName + ", " + mthName);
		} catch (ClassNotFoundException | NoClassDefFoundError e) {
			throw new InterpretException("Class Not Found " + e.toString(), clzName, methodCallExpr.lineno(), methodCallExpr.charat());
		}

	}

	public Value eval(Env env, ParenExpr parenExpr) {
		return eval(env, parenExpr.get());
	}

	public Value eval(Env env, PropertyExpr propertyExpr) {
		try {
			String clzName = propertyExpr.getObj();
			Class clz = getClass(clzName);
			Field fld = clz.getField(propertyExpr.getName());

			// Before any field reading, invoke notifyFieldRead for Library to
			// prepare
			// the field value to read if necessary.
			Method mth = clz.getMethod(notifyFieldRead, String.class);
			mth.invoke(null, propertyExpr.getName());

			return (Value) fld.get(null);
		} catch (NoSuchFieldException e) {
			throw new InterpretException("PropertyExpr : " + e.toString(), propertyExpr.getName(), propertyExpr.lineno(), propertyExpr.charat());
		} catch (SecurityException e) {
			throw new InterpretException("PropertyExpr : " + e.toString());
		} catch (IllegalArgumentException e) {
			throw new InterpretException("PropertyExpr : " + e.toString());
		} catch (IllegalAccessException e) {
			throw new InterpretException("PropertyExpr : " + e.toString());
		} catch (ClassNotFoundException | NoClassDefFoundError e) {
			throw new InterpretException("Class Not Found " + e.toString(), propertyExpr.getObj(), propertyExpr.lineno(), propertyExpr.charat());
		} catch (NoSuchMethodException e) {
			throw new InterpretException("Method Not Found " + e.toString());
		} catch (InvocationTargetException e) {
			throw new InterpretException("Target Not Found " + e.toString() + ": ");
		}
	}

	public double toDouble(String strDouble) {
		try {
			return new StrV(strDouble).parseDouble();
		} catch (NumberFormatException e) {
			throw new InterpretException(e.toString());
		}
	}

	public Value eval(Env env, Var var) {
		if (bbEnv.get(var.getVarName()) != null) // Subroutine name !!
			return new StrV(var.getVarName());
		else
			return env.get(var.getVarName());

	}

	public Value eval(Env env, Expr expr) {
		if (debug && debugexpr)
			printLog(expr);

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

	public static void eval(Value labelV) {
		env.label(((StrV) labelV).getValue());
		while (env.label() != null) {
			Stmt stmt = bbEnv.get(env.label());
			env.label(null);
			eval.eval(bbEnv, env, stmt);
		}
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
		if (v1 == null || v1.toString().equals("") || (v1 instanceof StrV && !((StrV) v1).isNumber()))
			v1 = new StrV("");
		if (v2 == null || v2.toString().equals("") || (v2 instanceof StrV && !((StrV) v2).isNumber()))
			v2 = new StrV("");

		if (v1 instanceof StrV && v2 instanceof StrV) {
			if (((StrV) v1).isNumber() && ((StrV) v2).isNumber()) {
				String strV1 = ((StrV) v1).getValue();
				String strV2 = ((StrV) v2).getValue();

				if (strV1.compareTo(strV2) >= 0) // compareTo 결과 양수가 나오면 앞의 문자열이
													// 뒤의 문자열보다 크다는 것을 의미
					return true;
			} else if (((StrV) v1).isNumber())
				return true;
			else if (v1.toString().equals("") && v2.toString().equals(""))
				return true;
		} else if (v1 instanceof DoubleV && v2 instanceof DoubleV) {
			double doubleV1 = ((DoubleV) v1).getValue();
			double doubleV2 = ((DoubleV) v2).getValue();

			if (doubleV1 >= doubleV2)
				return true;
		} else if (v1 instanceof DoubleV && v2 instanceof StrV) {
			if (((StrV) v2).isNumber()) {
				String strV1 = ((DoubleV) v1).toString();
				String strV2 = ((StrV) v2).getValue();

				if (strV1.compareTo(strV2) >= 0)
					return true;
			} else
				return true;
		} else if (v1 instanceof StrV && v2 instanceof DoubleV) {
			if (((StrV) v1).isNumber()) {
				String strV1 = ((StrV) v1).getValue();
				String strV2 = ((DoubleV) v2).toString();

				if (strV1.compareTo(strV2) >= 0)
					return true;
			}
		} else
			throw new InterpretException("Different Value is not comparable.");

		return false;
	}

	public static boolean greaterThan(Value v1, Value v2) {
		if (v1 == null || v1.toString().equals("") || (v1 instanceof StrV && !((StrV) v1).isNumber()))
			v1 = new StrV("");
		if (v2 == null || v2.toString().equals("") || (v2 instanceof StrV && !((StrV) v2).isNumber()))
			v2 = new StrV("");

		if (v1 instanceof StrV && v2 instanceof StrV) {
			if (((StrV) v1).isNumber() && ((StrV) v2).isNumber()) {
				String strV1 = ((StrV) v1).getValue();
				String strV2 = ((StrV) v2).getValue();

				if (strV1.compareTo(strV2) > 0)
					return true;
			} else if (((StrV) v1).isNumber())
				return true;
		} else if (v1 instanceof DoubleV && v2 instanceof DoubleV) {
			double doubleV1 = ((DoubleV) v1).getValue();
			double doubleV2 = ((DoubleV) v2).getValue();

			if (doubleV1 > doubleV2)
				return true;
		} else if (v1 instanceof DoubleV && v2 instanceof StrV) {
			if (((StrV) v2).isNumber()) {
				String strV1 = ((DoubleV) v1).toString();
				String strV2 = ((StrV) v2).getValue();

				if (strV1.compareTo(strV2) > 0)
					return true;
			} else
				return true;
		} else if (v1 instanceof StrV && v2 instanceof DoubleV) {
			if (((StrV) v1).isNumber()) {
				String strV1 = ((StrV) v1).getValue();
				String strV2 = ((DoubleV) v2).toString();

				if (strV1.compareTo(strV2) > 0)
					return true;
			}
		} else
			throw new InterpretException("Different Value is not comparable.");

		return false;
	}

	public static boolean lessEqual(Value v1, Value v2) {
		if (v1 == null || v1.toString().equals("") || (v1 instanceof StrV && !((StrV) v1).isNumber()))
			v1 = new StrV("");
		if (v2 == null || v2.toString().equals("") || (v2 instanceof StrV && !((StrV) v2).isNumber()))
			v2 = new StrV("");

		if (v1 instanceof StrV && v2 instanceof StrV) {
			if (((StrV) v1).isNumber() && ((StrV) v2).isNumber()) {
				String strV1 = ((StrV) v1).getValue().toString();
				String strV2 = ((StrV) v2).getValue().toString();

				if (strV1.compareTo(strV2) <= 0)
					return true;
			} else if (((StrV) v2).isNumber())
				return true;
			else if (v1.toString().equals("") && v2.toString().equals(""))
				return true;
		} else if (v1 instanceof DoubleV && v2 instanceof DoubleV) {
			double doubleV1 = ((DoubleV) v1).getValue();
			double doubleV2 = ((DoubleV) v2).getValue();

			if (doubleV1 <= doubleV2)
				return true;
		} else if (v1 instanceof DoubleV && v2 instanceof StrV) {
			if (((StrV) v2).isNumber()) {
				String strV1 = ((DoubleV) v1).toString();
				String strV2 = ((StrV) v2).getValue();

				if (strV1.compareTo(strV2) <= 0)
					return true;
			}
		} else if (v1 instanceof StrV && v2 instanceof DoubleV) {
			if (((StrV) v1).isNumber()) {
				String strV1 = ((StrV) v1).getValue();
				String strV2 = ((DoubleV) v2).toString();

				if (strV1.compareTo(strV2) <= 0)
					return true;
			} else
				return true;
		} else
			throw new InterpretException("Different Value is not comparable.");

		return false;
	}

	public static boolean lessThan(Value v1, Value v2) {
		if (v1 == null || v1.toString().equals("") || (v1 instanceof StrV && !((StrV) v1).isNumber()))
			v1 = new StrV("");
		if (v2 == null || v2.toString().equals("") || (v2 instanceof StrV && !((StrV) v2).isNumber()))
			v2 = new StrV("");

		if (v1 instanceof StrV && v2 instanceof StrV) {
			if (((StrV) v1).isNumber() && ((StrV) v2).isNumber()) {
				String strV1 = ((StrV) v1).getValue().toString();
				String strV2 = ((StrV) v2).getValue().toString();

				if (strV1.compareTo(strV2) < 0)
					return true;
			} else if (((StrV) v2).isNumber())
				return true;
		} else if (v1 instanceof DoubleV && v2 instanceof DoubleV) {
			double doubleV1 = ((DoubleV) v1).getValue();
			double doubleV2 = ((DoubleV) v2).getValue();

			if (doubleV1 < doubleV2)
				return true;
		} else if (v1 instanceof DoubleV && v2 instanceof StrV) {
			if (((StrV) v2).isNumber()) {
				String strV1 = ((DoubleV) v1).toString();
				String strV2 = ((StrV) v2).getValue();

				if (strV1.compareTo(strV2) < 0)
					return true;
			}
		} else if (v1 instanceof StrV && v2 instanceof DoubleV) {
			if (((StrV) v1).isNumber()) {
				String strV1 = ((StrV) v1).getValue();
				String strV2 = ((DoubleV) v2).toString();

				if (strV1.compareTo(strV2) < 0)
					return true;
			} else
				return true;
		} else
			throw new InterpretException("Different Value is not comparable.");

		return false;
	}

	public static boolean notEqual(Value v1, Value v2) {
		if (v1 == null && v2 == null)
			return false;

		if (v1 == null || v1.toString().equals(""))
			v1 = new StrV("");
		if (v2 == null || v2.toString().equals(""))
			v2 = new StrV("");

		if (v1 instanceof StrV && v2 instanceof StrV) {
			String strV1 = ((StrV) v1).getValue();
			String strV2 = ((StrV) v2).getValue();

			if (strV1.equals(strV2) == false)
				return true;
		} else if (v1 instanceof DoubleV && v2 instanceof DoubleV) {
			double doubleV1 = ((DoubleV) v1).getValue();
			double doubleV2 = ((DoubleV) v2).getValue();

			if (doubleV1 != doubleV2)
				return true;
		} else if (v1 instanceof DoubleV && v2 instanceof StrV) {
			String strV1 = ((DoubleV) v1).toString();
			String strV2 = ((StrV) v2).getValue();

			if (strV1.equals(strV2) == false)
				return true;
		} else if (v1 instanceof StrV && v2 instanceof DoubleV) {
			String strV1 = ((StrV) v1).getValue();
			String strV2 = ((DoubleV) v2).toString();

			if (strV1.equals(strV2) == false)
				return true;
		} else
			throw new InterpretException("Different Value is not comparable.");

		return false;
	}

	public static Class getClass(String name) throws ClassNotFoundException, NoClassDefFoundError {
		return Class.forName(lib + name);
	}

	// Log
	public static void printLog(Stmt stmt) {
		int lineno = stmt.lineno();
		int charat = stmt.charat();
		if (lineno != 0 && charat != 0)
			System.out.println("[Stmt] Line " + lineno + " : " + charat);
	}

	public static void printLog(Expr expr) {
		int lineno = expr.lineno();
		int charat = expr.charat();
		if (lineno != 0 && charat != 0)
			System.out.println("[Expr] Line " + lineno + " : " + charat);
	}
}
