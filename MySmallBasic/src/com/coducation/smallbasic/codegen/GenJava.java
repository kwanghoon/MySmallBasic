package com.coducation.smallbasic.codegen;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.coducation.smallbasic.ArithExpr;
import com.coducation.smallbasic.Array;
import com.coducation.smallbasic.Assign;
import com.coducation.smallbasic.BasicBlockEnv;
import com.coducation.smallbasic.BlockStmt;
import com.coducation.smallbasic.CompExpr;
import com.coducation.smallbasic.Expr;
import com.coducation.smallbasic.ExprStmt;
import com.coducation.smallbasic.ForStmt;
import com.coducation.smallbasic.GotoStmt;
import com.coducation.smallbasic.IfStmt;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.Label;
import com.coducation.smallbasic.Lit;
import com.coducation.smallbasic.LogicalExpr;
import com.coducation.smallbasic.MethodCallExpr;
import com.coducation.smallbasic.ParenExpr;
import com.coducation.smallbasic.PropertyExpr;
import com.coducation.smallbasic.Stmt;
import com.coducation.smallbasic.SubCallExpr;
import com.coducation.smallbasic.SubDef;
import com.coducation.smallbasic.Value;
import com.coducation.smallbasic.Var;
import com.coducation.smallbasic.WhileStmt;

public class GenJava {

	StringBuilder globalVar;
	StringBuilder topLevel;
	//ArrayList<Pair<String, StringBuilder>> method;
	LinkedHashMap<String, StringBuilder> methods;
	String currentMethod;
	StringBuilder currentMethodBody;
	int numberOfIndent;
	static String className;
	static final String lib = "com.coducation.smallbasic.lib.";

	public GenJava(BasicBlockEnv bbenv, String[] args) {
		this();
	}

	public GenJava() {
		globalVar = new StringBuilder("");
		topLevel = new StringBuilder("");
		methods = new LinkedHashMap<String, StringBuilder>();
	}

	public String printIndent() {
		StringBuilder javaIndent = new StringBuilder("");

		for (int i = 0; i <= numberOfIndent; i++) {
			javaIndent.append("    ");
		}

		return javaIndent.toString();
	}

	public static void main(String[] args) {
		GenJava g = new GenJava();
	}

	//args[0] : Smallbasic file name
	//stmt : 스몰베이직의 AST
	public void codeGen(String[] args, Stmt stmt) {
		String fileName = args[0].split("/")[args[0].split("/").length - 1];
		className = fileName.substring(0, fileName.length()-3);

		codeGen(true, stmt);

		OutputStreamWriter osw;
		try {
			//스몰베이직파일명과 동일한 .java 파일을 오픈

			osw = new OutputStreamWriter(new FileOutputStream(args[0].substring(0, args[0].length()-3) + ".java"), "UTF-8");
			System.out.println(className);

			//1~9번까지 출력
			osw.write("class " + className + " {\r\n");
			osw.write("\r\n");
			osw.write("    static Env env;\r\n");
			osw.write("\r\n");
			osw.write("    public " + className +"() {\r\n");
			osw.write("        env = new Env();\r\n");
			osw.write("    }\r\n");
			osw.write("\r\n");
			osw.write("    public static void main(String[] args) {\r\n");

			//탑레벨 스몰베이직 문장으로부터 생성된 자바문장을 출력
			osw.write(topLevel.toString());
			//11번 출력
			osw.write("    }\r\n");
			osw.write("\r\n");
			//스몰베이직의 각 서브루틴으로부터 생성된 자바메소드들을 출력
			Iterator<Entry<String, StringBuilder>> it = methods.entrySet().iterator();
			while(it.hasNext()) {
				osw.write(it.next().getValue().toString());
			}
			osw.write(assignPropertyExprGen("    "));
			osw.write(getPropertyExprGen("	"));
			//13번 출력
			osw.write("}\r\n");
			osw.flush();

			//파일을 닫기
			osw.close();

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	//스몰베이직 문장을 받아 자바문장에 대한 문자열을 만들고
	//topLevel 또는 methods에 추가
	public void codeGen(boolean isTopLevel, Stmt stmt) {
		if (stmt instanceof Assign)
			codeGen(isTopLevel, (Assign) stmt);
		else if (stmt instanceof BlockStmt)
			codeGen(isTopLevel, (BlockStmt) stmt);
		else if (stmt instanceof ExprStmt)
			codeGen(isTopLevel, (ExprStmt) stmt);
		else if (stmt instanceof ForStmt)
			codeGen(isTopLevel, (ForStmt) stmt);
		else if (stmt instanceof GotoStmt)
			codeGen(isTopLevel, (GotoStmt) stmt);
		else if (stmt instanceof IfStmt)
			codeGen(isTopLevel, (IfStmt) stmt);
		else if (stmt instanceof Label)
			codeGen(isTopLevel, (Label) stmt);
		else if (stmt instanceof SubDef)
			codeGen(isTopLevel, (SubDef) stmt);
		else if (stmt instanceof SubCallExpr)
			codeGen(isTopLevel, (SubCallExpr) stmt);
		else if (stmt instanceof WhileStmt)
			codeGen(isTopLevel, (WhileStmt) stmt);
		else
			throw new CodeGenException("Syntax Error!" + stmt.getClass());

	}

	public void codeGen(boolean isTopLevel, Assign assignStmt) {
		Expr lhs = assignStmt.getLSide();
		Expr rhs = assignStmt.getRSide();

		StringBuilder javaStmt = new StringBuilder("");
		javaStmt.append(printIndent());

		javaStmt.append(codeGen(lhs));
		javaStmt.append(" = ");
		javaStmt.append(codeGen(rhs));
		javaStmt.append(";\r\n");

		if(lhs instanceof Var) {
			Var v = (Var)lhs;
			javaStmt.append(v.getVarName() + " = " + codeGen(rhs) + ";\r\n");

		}
		else if(lhs instanceof PropertyExpr) {
			PropertyExpr propertyExpr = (PropertyExpr)lhs;
			javaStmt.append("assignPropertyExpr(" + propertyExpr.getObj() + ", " + propertyExpr.getName() + ", " + codeGen(rhs) + ");\r\n");

		}
		else if(lhs instanceof Array) {
			Array arr = (Array) lhs;

			for (int i = 0; i < arr.getDim(); i++) {
			}
		}
		else {
			throw new CodeGenException("Assign : Unknown lhs " + lhs);
		}

		if(isTopLevel) {
			topLevel.append(javaStmt);
		}
		else {
			if(methods.get(currentMethod) != null) 
				methods.put(currentMethod, methods.get(currentMethod).append(javaStmt));
			else methods.put(currentMethod, javaStmt);
		}

		/*if(isTopLevel) {
			if(lhs instanceof Var || lhs instanceof Array) {
				topLevel.append(javaStmt);
			}
			else {
				topLevel.append(javaStmt);
			}

		}
		else {
			if(methods.get(currentMethod) != null) 
				methods.put(currentMethod, methods.get(currentMethod).append(javaStmt));
			else methods.put(currentMethod, javaStmt);
		}*/
	}

	public void codeGen(boolean isTopLevel, BlockStmt blockStmt) {
		numberOfIndent++;

		for (int i = 0; i < blockStmt.getAL().size(); i++) {
			codeGen(isTopLevel, blockStmt.getAL().get(i));
		}

		numberOfIndent--;
	}

	public void codeGen(boolean isTopLevel, ExprStmt exprStmt) {
		StringBuilder javaStmt = new StringBuilder("");
		javaStmt.append(printIndent());

		javaStmt.append(codeGen(exprStmt.getExpr()));
		javaStmt.append(";\r\n");

		if(isTopLevel) {
			topLevel.append(javaStmt);
		}
		else {
			if(methods.get(currentMethod) != null) 
				methods.put(currentMethod, methods.get(currentMethod).append(javaStmt));
			else methods.put(currentMethod, javaStmt);
		}

	}

	public void codeGen(boolean isTopLevel, ForStmt forStmt) {

		StringBuilder javaStmt = new StringBuilder(printIndent());

		javaStmt.append("for(int ");
		javaStmt.append(codeGen(forStmt.getVar()));
		javaStmt.append(" = ");
		javaStmt.append(codeGen(forStmt.getInit()));
		javaStmt.append(" ; ");
		javaStmt.append(codeGen(forStmt.getVar()));
		javaStmt.append(" <= ");
		javaStmt.append(codeGen(forStmt.getEnd()));

		if (forStmt.getStep() != null) {
			javaStmt.append(codeGen(forStmt.getVar()));
			javaStmt.append(" = ");
			javaStmt.append(codeGen(forStmt.getVar()));
			javaStmt.append(codeGen(forStmt.getStep()));
		}
		else {
			javaStmt.append(codeGen(forStmt.getVar()));
			javaStmt.append("++");
		}
		javaStmt.append(") {\r\n");

		if(isTopLevel) {
			topLevel.append(javaStmt);
		}
		else {
			if(methods.get(currentMethod) != null) 
				methods.put(currentMethod, methods.get(currentMethod).append(javaStmt));
			else methods.put(currentMethod, javaStmt);
		}
		javaStmt = new StringBuilder(printIndent());

		codeGen(isTopLevel, forStmt.getBlock());


		javaStmt.append("}\r\n");

		if(isTopLevel) {
			topLevel.append(javaStmt);
		}
		else {
			if(methods.get(currentMethod) != null) 
				methods.put(currentMethod, methods.get(currentMethod).append(javaStmt));
			else methods.put(currentMethod, javaStmt);
		}
	}

	public void codeGen(boolean isTopLevel, GotoStmt gotoStmt) {

		System.out.println(printIndent() + "Goto " + gotoStmt.getTargetLabel());
	}

	public void codeGen(boolean isTopLevel, IfStmt ifStmt) {

		StringBuilder javaStmt = new StringBuilder(printIndent());

		javaStmt.append("if(");
		javaStmt.append(codeGen(ifStmt.getCond()));
		javaStmt.append(") {\r\n");

		if(isTopLevel) {
			topLevel.append(javaStmt);
		}
		else {
			if(methods.get(currentMethod) != null) 
				methods.put(currentMethod, methods.get(currentMethod).append(javaStmt));
			else methods.put(currentMethod, javaStmt);
		}
		javaStmt = new StringBuilder(printIndent());

		codeGen(isTopLevel, ifStmt.getThen());
		javaStmt.append("}\r\n");

		if(isTopLevel) {
			topLevel.append(javaStmt);
		}
		else {
			if(methods.get(currentMethod) != null) 
				methods.put(currentMethod, methods.get(currentMethod).append(javaStmt));
			else methods.put(currentMethod, javaStmt);
		}
		javaStmt = new StringBuilder(printIndent());

		if (ifStmt.getElse() != null) {
			printIndent();

			javaStmt.append("else {\r\n");

			if(isTopLevel) {
				topLevel.append(javaStmt);
			}
			else {
				if(methods.get(currentMethod) != null) 
					methods.put(currentMethod, methods.get(currentMethod).append(javaStmt));
				else methods.put(currentMethod, javaStmt);
			}
			javaStmt = new StringBuilder(printIndent());

			codeGen(isTopLevel, ifStmt.getElse());
			javaStmt.append("}\r\n");

			if(isTopLevel) {
				topLevel.append(javaStmt);
			}
			else {
				if(methods.get(currentMethod) != null) 
					methods.put(currentMethod, methods.get(currentMethod).append(javaStmt));
				else methods.put(currentMethod, javaStmt);
			}
		}

	}

	public void codeGen(boolean isTopLevel, Label labelStmt) {

		StringBuilder javaStmt = new StringBuilder(printIndent());

		javaStmt.append(labelStmt.getLabel() + ":\r\n");

		if(isTopLevel) {
			topLevel.append(javaStmt);
		}
		else {
			if(methods.get(currentMethod) != null) 
				methods.put(currentMethod, methods.get(currentMethod).append(javaStmt));
			else methods.put(currentMethod, javaStmt);
		}
	}

	public void codeGen(boolean isTopLevel, SubDef subDefStmt) {

		StringBuilder javaStmt = new StringBuilder(printIndent());
		currentMethod = subDefStmt.getName();

		javaStmt.append("public static void " + currentMethod + "() {\r\n");

		if(methods.get(currentMethod) != null) 
			methods.put(currentMethod, methods.get(currentMethod).append(javaStmt));
		else methods.put(currentMethod, javaStmt);
		javaStmt = new StringBuilder(printIndent());

		codeGen(false, subDefStmt.getBlock());

		javaStmt.append("}\r\n");

		if(methods.get(currentMethod) != null) 
			methods.put(currentMethod, methods.get(currentMethod).append(javaStmt));
		else methods.put(currentMethod, javaStmt);
	}

	public void codeGen(boolean isTopLevel, SubCallExpr subCallExpr) {

		StringBuilder javaStmt = new StringBuilder(printIndent());

		javaStmt.append(className + "." + subCallExpr.getName() + "();\r\n");

		if(isTopLevel) {
			topLevel.append(javaStmt);
		}
		else {
			if(methods.get(currentMethod) != null) 
				methods.put(currentMethod, methods.get(currentMethod).append(javaStmt));
			else methods.put(currentMethod, javaStmt);
		}
	}

	public void codeGen(boolean isTopLevel, WhileStmt whileStmt) {

		StringBuilder javaStmt = new StringBuilder(printIndent());

		javaStmt.append("While(");
		javaStmt.append(codeGen(whileStmt.getCond()));
		javaStmt.append(") {\r\n");

		if(isTopLevel) {
			topLevel.append(javaStmt);
		}
		else {
			if(methods.get(currentMethod) != null) 
				methods.put(currentMethod, methods.get(currentMethod).append(javaStmt));
			else methods.put(currentMethod, javaStmt);
		}
		javaStmt = new StringBuilder(printIndent());

		codeGen(isTopLevel, whileStmt.getBlock());

		javaStmt.append("}\r\n");
		if(isTopLevel) {
			topLevel.append(javaStmt);
		}
		else {
			if(methods.get(currentMethod) != null) 
				methods.put(currentMethod, methods.get(currentMethod).append(javaStmt));
			else methods.put(currentMethod, javaStmt);
		}
	}

	//스몰베이직 식을 받아 자바식에 대한 문자열을 만들어 리턴
	public String codeGen(Expr expr) {

		if (expr instanceof ArithExpr)
			return codeGen((ArithExpr) expr);
		else if (expr instanceof Array)
			return codeGen((Array) expr);
		else if (expr instanceof CompExpr)
			return codeGen((CompExpr) expr);
		else if (expr instanceof Lit)
			return codeGen((Lit) expr);
		else if (expr instanceof LogicalExpr)
			return codeGen((LogicalExpr) expr);
		else if (expr instanceof MethodCallExpr)
			return codeGen((MethodCallExpr) expr);
		else if (expr instanceof ParenExpr)
			return codeGen((ParenExpr) expr);
		else if (expr instanceof PropertyExpr)
			return codeGen((PropertyExpr) expr);
		else if (expr instanceof Var)
			return codeGen((Var) expr);
		else
			throw new InterpretException("Syntax Error! " + expr.getClass());

	}

	public String codeGen(ArithExpr arithExpr) {

		StringBuilder javaExpr = new StringBuilder("");

		switch (arithExpr.GetOp()) {
		case 1:
			javaExpr.append(codeGen(arithExpr.GetOperand()[0]));
			javaExpr.append(" + ");
			break;
		case 2:
			javaExpr.append(codeGen(arithExpr.GetOperand()[0]));
			javaExpr.append(" - ");
			break;
		case 3:
			javaExpr.append(codeGen(arithExpr.GetOperand()[0]));
			javaExpr.append(" * ");
			break;
		case 4:
			javaExpr.append(codeGen(arithExpr.GetOperand()[0]));
			javaExpr.append(" / ");
			break;
		case 5:
			javaExpr.append("- ");
			javaExpr.append(codeGen(arithExpr.GetOperand()[0]));
			break;
		}
		if (arithExpr.GetOperand()[1] != null)
			javaExpr.append(codeGen(arithExpr.GetOperand()[1]));

		return javaExpr.toString();
	}

	public String codeGen(Array arrayExpr) {

		StringBuilder javaExpr = new StringBuilder("");
		javaExpr.append(arrayExpr.getVar());

		for (int i = 0; i < arrayExpr.getDim(); i++) {
			javaExpr.append("[" + arrayExpr.getIndex(i) + "]");
		}
		return javaExpr.toString();

	}

	public String codeGen(CompExpr compExpr) {

		StringBuilder javaExpr = new StringBuilder("");
		javaExpr.append(codeGen(compExpr.GetOperand()[0]));

		switch (compExpr.GetOp()) {
		case CompExpr.GREATER_THAN:
			javaExpr.append(" > ");
			break;
		case CompExpr.LESS_THAN:
			javaExpr.append(" < ");
			break;
		case CompExpr.GREATER_EQUAL:
			javaExpr.append(" >= ");
			break;
		case CompExpr.LESS_EQUAL:
			javaExpr.append(" <= ");
			break;
		case CompExpr.EQUAL:
			javaExpr.append(" = ");
			break;
		case CompExpr.NOT_EQUAL:
			javaExpr.append(" <> ");
			break;
		default:
			System.err.println("Unknown CompExpr Operator " + compExpr.GetOp());
			break;
		}
		javaExpr.append(codeGen(compExpr.GetOperand()[1]));

		return javaExpr.toString();
	}

	public String codeGen(Lit litExpr) {

		return litExpr.gets();
	}

	public String codeGen(LogicalExpr logicalExpr) {

		StringBuilder javaExpr = new StringBuilder("");
		javaExpr.append(codeGen(logicalExpr.GetOperand()[0]));

		switch (logicalExpr.GetOp()) {
		case 1:
			javaExpr.append(" And ");
			break;
		case 2:
			javaExpr.append(" Or ");
			break;
		default:
			System.err.println("Unknown Logical Operator " + logicalExpr.GetOp());
			break;
		}
		javaExpr.append(codeGen(logicalExpr.GetOperand()[1]));

		return javaExpr.toString();
	}

	public String codeGen(MethodCallExpr methodCallExpr) {

		StringBuilder javaExpr = new StringBuilder("");
		javaExpr.append(methodCallExpr.getObj() + "." + methodCallExpr.getName() + "(");

		if (methodCallExpr.getArgs() != null) {
			int size = methodCallExpr.getArgs().size();
			for (int i = 0; i < size; i++) {
				javaExpr.append(codeGen(methodCallExpr.getArgs().get(i)));
				if (i != size - 1)
					javaExpr.append(", ");
			}
		}
		javaExpr.append(")");

		return javaExpr.toString();

	}

	public String codeGen(ParenExpr parenExpr) {

		return "(" + codeGen(parenExpr.get()) + ")";
	}

	public String codeGen(PropertyExpr propertyExpr) {
		StringBuilder javaExpr = new StringBuilder("");
		javaExpr.append("getPropertyExpr(" + propertyExpr.getObj() + ", " + propertyExpr.getName() + ")");

		return javaExpr.toString();
	}

	public String codeGen(Var var) {

		return var.getVarName();
	}

	public static String getClassGen(String indent) throws ClassNotFoundException {
		StringBuilder javaStmt = new StringBuilder("");

		javaStmt.append(indent);
		javaStmt.append("public static Class getClass(String name) {\r\n");
		javaStmt.append(indent);
		javaStmt.append("    return Class.forName(lib + name);");
		javaStmt.append(indent);
		javaStmt.append("}\r\n");
		javaStmt.append("\r\n");

		return javaStmt.toString();
	}

	public static String assignVarGen(String indent) {
		StringBuilder javaStmt = new StringBuilder("");

		javaStmt.append(indent);
		javaStmt.append("public static Class getClass(String name) {\r\n");
		javaStmt.append(indent);
		javaStmt.append("    return Class.forName(lib + name);");
		javaStmt.append(indent);
		javaStmt.append("}\r\n");
		javaStmt.append("\r\n");

		return javaStmt.toString();
	}

	public static String getVarGen(String indent) {
		StringBuilder javaStmt = new StringBuilder("");

		javaStmt.append(indent);
		javaStmt.append("public static String getVar(String varName) {\r\n");
		javaStmt.append(indent);
		javaStmt.append("    if (env.get(varName).equals(\"\"))");
		javaStmt.append(indent);
		javaStmt.append("        return varName;");
		javaStmt.append(indent);
		javaStmt.append("    else");
		javaStmt.append(indent);
		javaStmt.append("        return env.get(var.getVarName());");
		javaStmt.append(indent);
		javaStmt.append("}\r\n");
		javaStmt.append("\r\n");

		return javaStmt.toString();
	}

	public static String assignPropertyExprGen(String indent) {
		StringBuilder javaStmt = new StringBuilder("");

		javaStmt.append(indent);
		javaStmt.append("public static void assignPropertyExpr(String lhsObj, String lhsName, String rhsValue) {\r\n");
		javaStmt.append(indent);
		javaStmt.append("    try {\r\n");
		javaStmt.append(indent);
		javaStmt.append("        String clzName = lhsObj;\r\n");
		javaStmt.append(indent);
		javaStmt.append("	     Class clz = getClass(clzName);\r\n");
		javaStmt.append(indent);
		javaStmt.append("	     Field fld = clz.getField(lhsName);\r\n");
		javaStmt.append(indent);
		javaStmt.append("	     fld.set(null, rhsValue);\r\n");
		javaStmt.append(indent);
		javaStmt.append("	     Method mth = clz.getMethod(notifyFieldAssign, String.class);\r\n");
		javaStmt.append(indent);
		javaStmt.append("	     mth.invoke(null, lhsName);\r\n");
		javaStmt.append(indent);
		javaStmt.append("    } catch (NoSuchFieldException | SecurityException e) {\r\n");
		javaStmt.append(indent);
		javaStmt.append("	     throw new CodeGenException(\"Assign : \" + e.toString());\r\n");
		javaStmt.append(indent);
		javaStmt.append("    } catch (IllegalArgumentException e) {\r\n");
		javaStmt.append(indent);
		javaStmt.append("	     throw new CodeGenException(\"Assign : \" + e.toString());\r\n");
		javaStmt.append(indent);
		javaStmt.append("    } catch (IllegalAccessException e) {\r\n");
		javaStmt.append(indent);
		javaStmt.append("	     throw new CodeGenException(\"Assign : \" + e.toString());\r\n");
		javaStmt.append(indent);
		javaStmt.append("    } catch (ClassNotFoundException e) {\r\n");
		javaStmt.append(indent);
		javaStmt.append("	     throw new CodeGenException(\"Class Not Found \" + e.toString());\r\n");
		javaStmt.append(indent);
		javaStmt.append("    } catch (NoSuchMethodException e) {\r\n");
		javaStmt.append(indent);
		javaStmt.append("	     throw new CodeGenException(\"Method Not Found \" + e.toString());\r\n");
		javaStmt.append(indent);
		javaStmt.append("    } catch (InvocationTargetException e) {\r\n");
		javaStmt.append(indent);
		javaStmt.append("	     throw new CodeGenException(\"Target Not Found \" + e.toString() + \": \");\r\n");
		javaStmt.append(indent);
		javaStmt.append("    }\r\n");
		javaStmt.append(indent);
		javaStmt.append("}\r\n");
		javaStmt.append("\r\n");

		return javaStmt.toString();
	}

	public static String getPropertyExprGen(String indent) {
		StringBuilder javaStmt = new StringBuilder("");

		javaStmt.append(indent);
		javaStmt.append("public static String getPropertyExpr(String obj, String name) {\r\n");
		javaStmt.append(indent);
		javaStmt.append("    try {\r\n");
		javaStmt.append(indent);
		javaStmt.append("        String clzName = obj;\r\n");
		javaStmt.append(indent);
		javaStmt.append("	     Class clz = getClass(clzName);\r\n");
		javaStmt.append(indent);
		javaStmt.append("	     Field fld = clz.getField(name);\r\n");
		javaStmt.append(indent);
		javaStmt.append("	     Method mth = clz.getMethod(notifyFieldAssign, String.class);\r\n");
		javaStmt.append(indent);
		javaStmt.append("	     mth.invoke(null, name);\r\n");
		javaStmt.append(indent);
		javaStmt.append("	     return (Value) fld.get(null);\r\n");
		javaStmt.append(indent);
		javaStmt.append("    } catch (NoSuchFieldException | SecurityException e) {\r\n");
		javaStmt.append(indent);
		javaStmt.append("	     throw new CodeGenException(\"PropertyExpr : \" + e.toString());\r\n");
		javaStmt.append(indent);
		javaStmt.append("    } catch (IllegalArgumentException e) {\r\n");
		javaStmt.append(indent);
		javaStmt.append("	     throw new CodeGenException(\"PropertyExpr : \" + e.toString());\r\n");
		javaStmt.append(indent);
		javaStmt.append("    } catch (IllegalAccessException e) {\r\n");
		javaStmt.append(indent);
		javaStmt.append("	     throw new CodeGenException(\"PropertyExpr : \" + e.toString());\r\n");
		javaStmt.append(indent);
		javaStmt.append("    } catch (ClassNotFoundException e) {\r\n");
		javaStmt.append(indent);
		javaStmt.append("	     throw new CodeGenException(\"Class Not Found \" + e.toString());\r\n");
		javaStmt.append(indent);
		javaStmt.append("    } catch (NoSuchMethodException e) {\r\n");
		javaStmt.append(indent);
		javaStmt.append("	     throw new CodeGenException(\"Method Not Found \" + e.toString());\r\n");
		javaStmt.append(indent);
		javaStmt.append("    } catch (InvocationTargetException e) {\r\n");
		javaStmt.append(indent);
		javaStmt.append("	     throw new CodeGenException(\"Target Not Found \" + e.toString() + \": \");\r\n");
		javaStmt.append(indent);
		javaStmt.append("    }\r\n");
		javaStmt.append(indent);
		javaStmt.append("}\r\n");
		javaStmt.append("\r\n");

		return javaStmt.toString();
	}

	public static String assignArray(String indent) {
		return "";
	}

	public static String getArray(String indent) {
		return "";
	}


}
