//package com.coducation.smallbasic;

import java.lang.reflect.*;
import java.util.*;

import com.coducation.smallbasic.*;
import com.coducation.smallbasic.codegen.*;

public class Util {

	public static Env env;
	static final String lib = "com.coducation.smallbasic.lib.";
	static final String notifyFieldAssign = "notifyFieldAssign";
	static final String notifyFieldRead = "notifyFieldRead";
	static Class GenJava_C;
	static String className;
	
	public static void setClassName(String className) {
		Util.className = className;
	}

	public static class Env extends com.coducation.smallbasic.Env {
		private HashMap<String,Method> labels;
		private static final String label = "$label";

		public Env() {
			labels = new HashMap<String,Method>();
		}

		public Method label_M() {
			return labels.get(label + Thread.currentThread().getId());
		}

		public void label_M(Method _label) {
			labels.put(label + Thread.currentThread().getId(), _label);
		}
	}

	public static void main(String CLASS) {
		className = CLASS;
		GenJava_C = getClass(CLASS);
		try {
			env = new Env();
			Method m = GenJava_C.getMethod("main");
			env.label_M(m);

			while (env.label_M() != null) {
				m = env.label_M();
				env.label_M(null);
				m.invoke(null);
			}
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public static void assignVar(String varName, Value rhsValue) {
		if (rhsValue instanceof ArrayV) {
			rhsValue = ((ArrayV)rhsValue).copy();
		}

		env.put(varName, rhsValue);
	}

	public static Value getVar(String varName) {
		return env.get(varName);
	}

	public static void assignPropertyExpr(String lhsObj, String lhsName, Value rhsValue) {
		if (rhsValue instanceof ArrayV) {
			rhsValue = ((ArrayV)rhsValue).copy();
		}

		try {
			String clzName = lhsObj;
			Class clz = getClass(clzName);
			Field fld = clz.getField(lhsName);
			fld.set(null, rhsValue);
			Method mth = clz.getMethod(notifyFieldAssign, String.class);
			mth.invoke(null, lhsName);
		} catch (NoSuchFieldException | SecurityException e) {
			throw new CodeGenException("Assign : " + e.toString());
		} catch (IllegalArgumentException e) {
			throw new CodeGenException("Assign : " + e.toString());
		} catch (IllegalAccessException e) {
			throw new CodeGenException("Assign : " + e.toString());
		} catch (NoSuchMethodException e) {
			throw new CodeGenException("Method Not Found " + e.toString());
		} catch (InvocationTargetException e) {
			throw new CodeGenException("Target Not Found " + e.toString() + ": ");
		}
	}

	public static Value getPropertyExpr(String obj, String name) {
		try {
			String clzName = obj;
			Class clz = getClass(clzName);
			Field fld = clz.getField(name);
			Method mth = clz.getMethod(notifyFieldRead, String.class);
			mth.invoke(null, name);
			return (Value) fld.get(null);
		} catch (NoSuchFieldException | SecurityException e) {
			throw new CodeGenException("PropertyExpr : " + e.toString());
		} catch (IllegalArgumentException e) {
			throw new CodeGenException("PropertyExpr : " + e.toString());
		} catch (IllegalAccessException e) {
			throw new CodeGenException("PropertyExpr : " + e.toString());
		} catch (NoSuchMethodException e) {
			throw new CodeGenException("Method Not Found " + e.toString());
		} catch (InvocationTargetException e) {
			throw new CodeGenException("Target Not Found " + e.toString() + ": ");
		}
	}

	public static void assignArray(String arrayName, Value rhsValue, Value... idx_s) {
		if (rhsValue instanceof ArrayV) {
			rhsValue = ((ArrayV)rhsValue).copy();
		}

		Value arrValue = env.get(arrayName);
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
			env.put(arrayName, elem);
		}

		for (int i = 0; i < idx_s.length; i++) {
			if (i < idx_s.length - 1) {
				ArrayV elem_elem = (ArrayV) elem.get(idx_s[i].toString());
				if (elem_elem == null) {
					elem_elem = new ArrayV();
					elem.put(idx_s[i].toString(), elem_elem);
				}
				elem = elem_elem;
			} else {
				elem.put(idx_s[i].toString(), rhsValue);
			}
		}
	}

	public static Value getArray(String arrayName, Value... idx_s) {
		ArrayV arrV;
		Value elem = null;

		if (env.get(arrayName) instanceof ArrayV) {
			arrV = (ArrayV) env.get(arrayName);
			elem = arrV;
		}

		for (int i = 0; i < idx_s.length; i++) {
			if (elem == null)
				break;
			else if (elem instanceof StrV) {
			} else
				elem = ((ArrayV) elem).get(idx_s[i].toString());
		}
		if (elem == null)
			return new StrV("");
		else
			return elem;
	}

	public static Class getClass(String name) {
		try {
			if(name.equals(className))
				return Class.forName(name);
			else return Class.forName(lib + name);
		} catch (ClassNotFoundException e) {
			throw new CodeGenException("Class Not Found " + e.toString());
		}
	}

	public static ArrayList<Value> getList(Value... args) {
		ArrayList<Value> list = new ArrayList<Value>();
		Collections.addAll(list, args);
		return list;
	}

	public static Value plus(Value v1, Value v2) {
		Double dv1 = 0.0, dv2 = 0.0;
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
			throw new CodeGenException("PLUS 1st operand unexpected" + v1);

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
			throw new CodeGenException("PLUS 2nd operand unexpected" + v2);

		if (numplus == true)
			return new DoubleV(dv1 + dv2);
		else
			return new StrV(v1.toString() + v2.toString());
	}

	public static Value minus(Value v1, Value v2) {
		Double dv1 = 0.0, dv2 = 0.0;

		if (v1 == null)
			dv1 = 0.0;
		else if (v1 instanceof DoubleV)
			dv1 = ((DoubleV) v1).getValue();
		else if (v1 instanceof StrV && ((StrV) v1).isNumber())
			dv1 = ((StrV) v1).parseDouble();
		else if (v1 instanceof StrV)
			dv1 = 0.0;
		else
			throw new CodeGenException("MINUS 1st operand unexpected" + v1);

		if (v2 == null)
			dv2 = 0.0;
		else if (v2 instanceof DoubleV)
			dv2 = ((DoubleV) v2).getValue();
		else if (v2 instanceof StrV && ((StrV) v2).isNumber())
			dv2 = ((StrV) v2).parseDouble();
		else if (v2 instanceof StrV)
			dv2 = 0.0;
		else
			throw new CodeGenException("MINUS 2nd operand unexpected" + v2);

		return new DoubleV(dv1 - dv2);
	}

	public static Value multiply(Value v1, Value v2) {
		Double dv1 = 0.0, dv2 = 0.0;

		if (v1 == null)
			dv1 = 0.0;
		else if (v1 instanceof DoubleV)
			dv1 = ((DoubleV) v1).getValue();
		else if (v1 instanceof StrV && ((StrV) v1).isNumber())
			dv1 = ((StrV) v1).parseDouble();
		else if (v1 instanceof StrV)
			dv1 = 0.0;
		else
			throw new CodeGenException("MULTIFLY 1st operand unexpected" + v1);

		if (v2 == null)
			dv2 = 0.0;
		else if (v2 instanceof DoubleV)
			dv2 = ((DoubleV) v2).getValue();
		else if (v2 instanceof StrV && ((StrV) v2).isNumber())
			dv2 = ((StrV) v2).parseDouble();
		else if (v2 instanceof StrV)
			dv2 = 0.0;
		else
			throw new CodeGenException("MULTIFLY 2nd operand unexpected" + v2);

		return new DoubleV(dv1 * dv2);
	}

	public static Value divide(Value v1, Value v2) {
		Double dv1 = 0.0, dv2 = 0.0;

		if (v1 == null)
			dv1 = 0.0;
		else if (v1 instanceof DoubleV)
			dv1 = ((DoubleV) v1).getValue();
		else if (v1 instanceof StrV && ((StrV) v1).isNumber())
			dv1 = ((StrV) v1).parseDouble();
		else if (v1 instanceof StrV)
			dv1 = 0.0;
		else
			throw new CodeGenException("DIVIDE 1st operand unexpected" + v1);

		if (v2 == null)
			dv2 = 1.0;
		else if (v2 instanceof DoubleV)
			dv2 = ((DoubleV) v2).getValue();
		else if (v2 instanceof StrV && ((StrV) v2).isNumber())
			dv2 = ((StrV) v2).parseDouble();
		else if (v2 instanceof StrV)
			dv2 = 0.0;
		else
			throw new CodeGenException("DIVIDE 2nd operand unexpected" + v2);

		if (dv2 == 0)
			throw new CodeGenException("DIVIDE 2nd operand is 0");

		return new DoubleV(dv1 / dv2);
	}

	public static Value unary_Minus(Value v1) {
		if (v1 instanceof DoubleV) {
			DoubleV d1 = new DoubleV(v1.getNumber());
			return new DoubleV(-d1.getValue());
		}
		else if(v1 instanceof StrV && ((StrV) v1).isNumber())
			return new DoubleV(((StrV) v1).parseDouble());
		else if(v1 instanceof StrV)
			return new DoubleV(0);
		else
			throw new CodeGenException("UNARY_MINUS 1st operand unexpected " + v1);
	}

	public static Value equal(Value v1, Value v2) {
		if (v1 instanceof StrV && v2 instanceof StrV) {
			StrV s1 = (StrV) v1;
			StrV s2 = (StrV) v2;
			if (s1.getValue().equals(s2.getValue()))
				return new StrV("true");
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
	}

	public static Value greaterEqual(Value v1, Value v2) {
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
					return new StrV("true");
			} else if (((StrV) v1).isNumber())
				return new StrV("true");
			else if (v1.toString().equals("") && v2.toString().equals(""))
				return new StrV("true");
		} else if (v1 instanceof DoubleV && v2 instanceof DoubleV) {
			double doubleV1 = ((DoubleV) v1).getValue();
			double doubleV2 = ((DoubleV) v2).getValue();

			if (doubleV1 >= doubleV2)
				return new StrV("true");
		} else if (v1 instanceof DoubleV && v2 instanceof StrV) {
			if (((StrV) v2).isNumber()) {
				String strV1 = ((DoubleV) v1).toString();
				String strV2 = ((StrV) v2).getValue();

				if (strV1.compareTo(strV2) >= 0)
					return new StrV("true");
			} else
				return new StrV("true");
		} else if (v1 instanceof StrV && v2 instanceof DoubleV) {
			if (((StrV) v1).isNumber()) {
				String strV1 = ((StrV) v1).getValue();
				String strV2 = ((DoubleV) v2).toString();

				if (strV1.compareTo(strV2) >= 0)
					return new StrV("true");
			}
		} else
			throw new CodeGenException("Different Value is not comparable.");

		return new StrV("false");
	}

	public static Value greaterThan(Value v1, Value v2) {
		if (v1 == null || v1.toString().equals("") || (v1 instanceof StrV && !((StrV) v1).isNumber()))
			v1 = new StrV("");
		if (v2 == null || v2.toString().equals("") || (v2 instanceof StrV && !((StrV) v2).isNumber()))
			v2 = new StrV("");

		if (v1 instanceof StrV && v2 instanceof StrV) {
			if (((StrV) v1).isNumber() && ((StrV) v2).isNumber()) {
				String strV1 = ((StrV) v1).getValue();
				String strV2 = ((StrV) v2).getValue();

				if (strV1.compareTo(strV2) > 0)
					return new StrV("true");
			} else if (((StrV) v1).isNumber())
				return new StrV("true");
		} else if (v1 instanceof DoubleV && v2 instanceof DoubleV) {
			double doubleV1 = ((DoubleV) v1).getValue();
			double doubleV2 = ((DoubleV) v2).getValue();

			if (doubleV1 > doubleV2)
				return new StrV("true");
		} else if (v1 instanceof DoubleV && v2 instanceof StrV) {
			if (((StrV) v2).isNumber()) {
				String strV1 = ((DoubleV) v1).toString();
				String strV2 = ((StrV) v2).getValue();

				if (strV1.compareTo(strV2) > 0)
					return new StrV("true");
			} else
				return new StrV("true");
		} else if (v1 instanceof StrV && v2 instanceof DoubleV) {
			if (((StrV) v1).isNumber()) {
				String strV1 = ((StrV) v1).getValue();
				String strV2 = ((DoubleV) v2).toString();

				if (strV1.compareTo(strV2) > 0)
					return new StrV("true");
			}
		} else
			throw new CodeGenException("Different Value is not comparable.");

		return new StrV("false");
	}

	public static Value lessEqual(Value v1, Value v2) {
		if (v1 == null || v1.toString().equals("") || (v1 instanceof StrV && !((StrV) v1).isNumber()))
			v1 = new StrV("");
		if (v2 == null || v2.toString().equals("") || (v2 instanceof StrV && !((StrV) v2).isNumber()))
			v2 = new StrV("");

		if (v1 instanceof StrV && v2 instanceof StrV) {
			if (((StrV) v1).isNumber() && ((StrV) v2).isNumber()) {
				String strV1 = ((StrV) v1).getValue().toString();
				String strV2 = ((StrV) v2).getValue().toString();

				if (strV1.compareTo(strV2) <= 0)
					return new StrV("true");
			} else if (((StrV) v2).isNumber())
				return new StrV("true");
			else if (v1.toString().equals("") && v2.toString().equals(""))
				return new StrV("true");
		} else if (v1 instanceof DoubleV && v2 instanceof DoubleV) {
			double doubleV1 = ((DoubleV) v1).getValue();
			double doubleV2 = ((DoubleV) v2).getValue();

			if (doubleV1 <= doubleV2)
				return new StrV("true");
		} else if (v1 instanceof DoubleV && v2 instanceof StrV) {
			if (((StrV) v2).isNumber()) {
				String strV1 = ((DoubleV) v1).toString();
				String strV2 = ((StrV) v2).getValue();

				if (strV1.compareTo(strV2) <= 0)
					return new StrV("true");
			}
		} else if (v1 instanceof StrV && v2 instanceof DoubleV) {
			if (((StrV) v1).isNumber()) {
				String strV1 = ((StrV) v1).getValue();
				String strV2 = ((DoubleV) v2).toString();

				if (strV1.compareTo(strV2) <= 0)
					return new StrV("true");
			} else
				return new StrV("true");
		} else
			throw new CodeGenException("Different Value is not comparable.");

		return new StrV("false");
	}

	public static Value lessThan(Value v1, Value v2) {
		if (v1 == null || v1.toString().equals("") || (v1 instanceof StrV && !((StrV) v1).isNumber()))
			v1 = new StrV("");
		if (v2 == null || v2.toString().equals("") || (v2 instanceof StrV && !((StrV) v2).isNumber()))
			v2 = new StrV("");

		if (v1 instanceof StrV && v2 instanceof StrV) {
			if (((StrV) v1).isNumber() && ((StrV) v2).isNumber()) {
				String strV1 = ((StrV) v1).getValue().toString();
				String strV2 = ((StrV) v2).getValue().toString();

				if (strV1.compareTo(strV2) < 0)
					return new StrV("true");
			} else if (((StrV) v2).isNumber())
				return new StrV("true");
		} else if (v1 instanceof DoubleV && v2 instanceof DoubleV) {
			double doubleV1 = ((DoubleV) v1).getValue();
			double doubleV2 = ((DoubleV) v2).getValue();

			if (doubleV1 < doubleV2)
				return new StrV("true");
		} else if (v1 instanceof DoubleV && v2 instanceof StrV) {
			if (((StrV) v2).isNumber()) {
				String strV1 = ((DoubleV) v1).toString();
				String strV2 = ((StrV) v2).getValue();

				if (strV1.compareTo(strV2) < 0)
					return new StrV("true");
			}
		} else if (v1 instanceof StrV && v2 instanceof DoubleV) {
			if (((StrV) v1).isNumber()) {
				String strV1 = ((StrV) v1).getValue();
				String strV2 = ((DoubleV) v2).toString();

				if (strV1.compareTo(strV2) < 0)
					return new StrV("true");
			} else
				return new StrV("true");
		} else
			throw new CodeGenException("Different Value is not comparable.");

		return new StrV("false");
	}

	public static Value notEqual(Value v1, Value v2) {
		if (v1 == null && v2 == null)
			return new StrV("false");

		if (v1 == null || v1.toString().equals(""))
			v1 = new StrV("");
		if (v2 == null || v2.toString().equals(""))
			v2 = new StrV("");

		if (v1 instanceof StrV && v2 instanceof StrV) {
			String strV1 = ((StrV) v1).getValue();
			String strV2 = ((StrV) v2).getValue();

			if (strV1.equals(strV2) == false)
				return new StrV("true");
		} else if (v1 instanceof DoubleV && v2 instanceof DoubleV) {
			double doubleV1 = ((DoubleV) v1).getValue();
			double doubleV2 = ((DoubleV) v2).getValue();

			if (doubleV1 != doubleV2)
				return new StrV("true");
		} else if (v1 instanceof DoubleV && v2 instanceof StrV) {
			String strV1 = ((DoubleV) v1).toString();
			String strV2 = ((StrV) v2).getValue();

			if (strV1.equals(strV2) == false)
				return new StrV("true");
		} else if (v1 instanceof StrV && v2 instanceof DoubleV) {
			String strV1 = ((StrV) v1).getValue();
			String strV2 = ((DoubleV) v2).toString();

			if (strV1.equals(strV2) == false)
				return new StrV("true");
		} else
			throw new CodeGenException("Different Value is not comparable.");

		return new StrV("false");
	}

	public static Value AND(Value v1, Value v2) {
		StrV s1, s2;

		if (v1 instanceof StrV && v2 instanceof StrV) {
			s1 = (StrV) v1;
			s2 = (StrV) v2;

			if (s1.getValue().equalsIgnoreCase("true") && s2.getValue().equalsIgnoreCase("true"))
				return new StrV("true");
		}
		return new StrV("false");
	}

	public static Value OR(Value v1, Value v2) {
		StrV s1, s2;

		if (v1 instanceof StrV && v2 instanceof StrV) {
			s1 = (StrV) v1;
			s2 = (StrV) v2;

			if (s1.getValue().equalsIgnoreCase("true") || s2.getValue().equalsIgnoreCase("true"))
				return new StrV("true");
		}
		return new StrV("false");
	}

	public static boolean isTrue(Value v) {
		if (v instanceof StrV) {
			StrV str = (StrV) v;
			return str.getValue().equalsIgnoreCase("True");
		} else
			return false;
	}

}
