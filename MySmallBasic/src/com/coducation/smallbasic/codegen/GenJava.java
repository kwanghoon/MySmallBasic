package com.coducation.smallbasic.codegen;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.coducation.smallbasic.*;

public class GenJava {

	StringBuilder globalVar;
	StringBuilder topLevel;
	LinkedHashMap<String, StringBuilder> methods;
	String currentMethod;
	String emptyMethod;
	StringBuilder currentMethodBody;
	int numberOfIndent;
	HashMap<String, Stmt> trees;
	private static String[] programArgs;
	static String className;
	static String fileName;
	static ArrayList<String> idx_s;
	static final String lib = "com.coducation.smallbasic.lib.";

	public GenJava(BasicBlockEnv bbenv, String[] args) {
		this();
		trees = bbenv.getMap();
		programArgs = args;
	}

	public GenJava() {
		globalVar = new StringBuilder("");
		topLevel = new StringBuilder("");
		methods = new LinkedHashMap<String, StringBuilder>();
		idx_s = new ArrayList<String>();
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
	public void codeGen(String[] args) {
		fileName = args[0].split("/")[args[0].split("/").length - 1];
		className = "Java_" + fileName.substring(0,1).toUpperCase() + fileName.substring(1, fileName.length()-3);

		Set<Map.Entry<String, Stmt>> set = trees.entrySet();
		for (Map.Entry<String, Stmt> entry : set) {
			if(entry.getKey().contains("$"))
				currentMethod = entry.getKey().substring(1); // tree --> method
			else currentMethod = entry.getKey();
			Stmt stmt = entry.getValue();

			if((stmt instanceof BlockStmt)&&(((BlockStmt)stmt).getAL().size() == 0)) {
				emptyMethod = currentMethod;
			}
			else {
				methods.put(currentMethod, new StringBuilder("    public static void " + currentMethod + "() {\r\n")); // 처음
				numberOfIndent++;
				codeGen(false, stmt);
				if(methods.get(currentMethod) != null) 
					methods.put(currentMethod, methods.get(currentMethod).append("    }\r\n"));
				else methods.put(currentMethod, new StringBuilder("    }\r\n"));
				numberOfIndent--;
			}

		}

		if(emptyMethod != null) { // emptyMethod 제거
			Set<String> keySet = trees.keySet();
			for(String l : keySet) {
				String s = null;
				if(l.contains("$"))
					s = l.substring(1);
				else s = l;
				if(!s.equals(emptyMethod))
					methods.put(s, new StringBuilder(methods.get(s).toString().replaceAll("try [{](\\s)*?Util[.]env[.]label_M[(]" + className + "_C[.]getMethod[(]\"" + emptyMethod + "\", null[)](.|\r\n)*?e[()]+;(\\s)*?}", "Util.env.label_M(null);")));
			}
		}


		OutputStreamWriter osw;
		try {
			//스몰베이직파일명과 동일한 .java 파일을 오픈
			osw = new OutputStreamWriter(new FileOutputStream("./output/" + className +".java"), "UTF-8");
			System.out.println(className);

			//1~9번까지 출력
			osw.write("\r\n");
			osw.write("import java.lang.reflect.*;\r\n");
			osw.write("import java.util.*;\r\n");
			osw.write("\r\n");
			osw.write("import com.coducation.smallbasic.*;\r\n");
			osw.write("\r\n");
			osw.write("public class " + className + " {\r\n");
			osw.write("\r\n");
			osw.write("    static String className = \"" + className + "\";\r\n");
			osw.write("    static Class "+ className +"_C;\r\n");
			osw.write("\r\n");
			osw.write("    public " + className +"() {\r\n");
			osw.write("    }\r\n");
			osw.write("\r\n");
			osw.write(mainGen("    ")); // main Method 생성
			//스몰베이직의 각 서브루틴으로부터 생성된 자바메소드들을 출력
			Iterator<Entry<String, StringBuilder>> it = methods.entrySet().iterator();
			while(it.hasNext()) {
				osw.write(it.next().getValue().toString());
				osw.write("\r\n");
			}
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

		if(lhs instanceof Var) {
			Var var = (Var)lhs;
			javaStmt.append("Util.assignVar(\"" + var.getVarName() + "\", " + codeGen(rhs) + ");\r\n");

		}
		else if(lhs instanceof PropertyExpr) {
			PropertyExpr propertyExpr = (PropertyExpr)lhs;
			javaStmt.append("Util.assignPropertyExpr(\"" + propertyExpr.getObj() + "\", \"" + propertyExpr.getName() + "\", " + codeGen(rhs) + ");\r\n");

		}
		else if(lhs instanceof Array) {
			Array arr = (Array) lhs;
			idx_s.clear();

			for (int i = 0; i < arr.getDim(); i++) {
				Expr idx = arr.getIndex(i);
				String s_idx = codeGen(idx);
				//Value v = Eval.eval(env, idx);

				if (s_idx == null || s_idx.equals(""))
					idx_s.add("0");
				else if (s_idx instanceof String) {
					idx_s.add(s_idx);
				}
				else {
					throw new CodeGenException("Unexpected Index" + idx_s);
				}

			}

			javaStmt.append("Util.assignArray(\"" + arr.getVar() + "\", ");
			javaStmt.append(codeGen(rhs));
			for(int i=0;i<idx_s.size();i++) {
				javaStmt.append(", " + idx_s.get(i));
			}
			javaStmt.append(");\r\n");
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
		javaStmt.append(" ; ");

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

	public void codeGen(boolean isTopLevel, GotoStmt gotoStmt) { // subCall role
		StringBuilder javaStmt = new StringBuilder(printIndent());

		javaStmt.append("try {\r\n");
		javaStmt.append(printIndent());
		javaStmt.append("    Util.env.label_M(" + className + "_C.getMethod(\"" + gotoStmt.getTargetLabel().substring(1) + "\", null));\r\n");
		javaStmt.append(printIndent());
		javaStmt.append("} catch (NoSuchMethodException | SecurityException e) {\r\n");
		javaStmt.append(printIndent());
		javaStmt.append("    e.printStackTrace();\r\n");
		javaStmt.append(printIndent());
		javaStmt.append("}\r\n");
		//javaStmt.append(className + "." + gotoStmt.getTargetLabel().substring(1) + "();\r\n");

		if(isTopLevel) {
			topLevel.append(javaStmt);
		}
		else {
			if(methods.get(currentMethod) != null) 
				methods.put(currentMethod, methods.get(currentMethod).append(javaStmt));
			else methods.put(currentMethod, javaStmt);
		}
	}

	public void codeGen(boolean isTopLevel, IfStmt ifStmt) {

		StringBuilder javaStmt = new StringBuilder(printIndent());

		javaStmt.append("if(Util.isTrue(");
		javaStmt.append(codeGen(ifStmt.getCond()));
		javaStmt.append(")) {\r\n");

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
		throw new CodeGenException("SubDef : Unexpected");
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
		case ArithExpr.PLUS:
			javaExpr.append("Util.plus(");
			break;
		case ArithExpr.MINUS:
			javaExpr.append("Util.minus(");
			break;
		case ArithExpr.MULTIFLY:
			javaExpr.append("Util.multiply(");
			break;
		case ArithExpr.DIVIDE:
			javaExpr.append("Util.divide(");
			break;
		case ArithExpr.UNARY_MINUS:
			javaExpr.append("Util.unary_Minus(");
			break;
		}
		javaExpr.append(codeGen(arithExpr.GetOperand()[0]));
		if (arithExpr.GetOperand()[1] != null) {
			javaExpr.append(", ");
			javaExpr.append(codeGen(arithExpr.GetOperand()[1]));
		}
		javaExpr.append(")");

		return javaExpr.toString();
	}

	public String codeGen(Array arrayExpr) {

		StringBuilder javaExpr = new StringBuilder("");
		idx_s.clear();

		for (int i = 0; i < arrayExpr.getDim(); i++) {
			Expr idx = arrayExpr.getIndex(i);
			String s_idx = codeGen(idx);

			if (s_idx == null || s_idx.equals(""))
				idx_s.add("0");
			else if (s_idx instanceof String) {
				idx_s.add(s_idx);
			}
			else {
				throw new CodeGenException("Unexpected Index" + idx_s);
			}
		}

		javaExpr.append("Util.getArray(\"" + arrayExpr.getVar() + "\"");
		for(int i=0;i<idx_s.size();i++) {
			javaExpr.append(", " + idx_s.get(i));
		}
		javaExpr.append(")");

		return javaExpr.toString();
	}

	public String codeGen(CompExpr compExpr) {

		StringBuilder javaExpr = new StringBuilder("");

		switch (compExpr.GetOp()) {
		case CompExpr.GREATER_THAN:
			javaExpr.append("Util.greaterThan(");
			javaExpr.append(codeGen(compExpr.GetOperand()[0]));
			break;
		case CompExpr.LESS_THAN:
			javaExpr.append("Util.lessThan(");
			javaExpr.append(codeGen(compExpr.GetOperand()[0]));
			break;
		case CompExpr.GREATER_EQUAL:
			javaExpr.append("Util.greaterEqual(");
			javaExpr.append(codeGen(compExpr.GetOperand()[0]));
			break;
		case CompExpr.LESS_EQUAL:
			javaExpr.append("Util.lessEqual(");
			javaExpr.append(codeGen(compExpr.GetOperand()[0]));
			break;
		case CompExpr.EQUAL:
			javaExpr.append("Util.equal(");
			javaExpr.append(codeGen(compExpr.GetOperand()[0]));
			break;
		case CompExpr.NOT_EQUAL:
			javaExpr.append("Util.notEqual(");
			javaExpr.append(codeGen(compExpr.GetOperand()[0]));
			break;
		default:
			System.err.println("Unknown CompExpr Operator " + compExpr.GetOp());
			break;
		}
		javaExpr.append(", ");
		javaExpr.append(codeGen(compExpr.GetOperand()[1]) + ")");

		return javaExpr.toString();
	}

	public String codeGen(Lit litExpr) {
		String javaExpr = litExpr.gets();
		String r_javaExpr = javaExpr.replaceAll("\"", "");
		String s_javaExpr = javaExpr.replace("\\", "\\\\");
		if(isNumber(r_javaExpr))
			return "new DoubleV(" + r_javaExpr + ")";
		else return "new StrV(" + s_javaExpr + ")";
	}

	public String codeGen(LogicalExpr logicalExpr) {

		StringBuilder javaExpr = new StringBuilder("");

		switch (logicalExpr.GetOp()) {
		case 1:
			javaExpr.append("Util.AND(");
			break;
		case 2:
			javaExpr.append("Util.OR(");
			break;
		default:
			System.err.println("Unknown Logical Operator " + logicalExpr.GetOp());
			break;
		}
		javaExpr.append(codeGen(logicalExpr.GetOperand()[0]));
		javaExpr.append(", ");
		javaExpr.append(codeGen(logicalExpr.GetOperand()[1]) + ")");

		return javaExpr.toString();
	}

	public String codeGen(MethodCallExpr methodCallExpr) {

		StringBuilder javaExpr = new StringBuilder("");
		javaExpr.append(lib + methodCallExpr.getObj() + "." + methodCallExpr.getName() + "(");
		javaExpr.append("Util.getList(");

		if (methodCallExpr.getArgs() != null) {
			int size = methodCallExpr.getArgs().size();
			for (int i = 0; i < size; i++) {
				javaExpr.append(codeGen(methodCallExpr.getArgs().get(i)));
				if (i != size - 1)
					javaExpr.append(", ");
			}
		}
		javaExpr.append(")");
		javaExpr.append(")");

		return javaExpr.toString();

	}

	public String codeGen(ParenExpr parenExpr) {

		return "(" + codeGen(parenExpr.get()) + ")";
	}

	public String codeGen(PropertyExpr propertyExpr) {
		StringBuilder javaExpr = new StringBuilder("");
		javaExpr.append("Util.getPropertyExpr(\"" + propertyExpr.getObj() + "\", \"" + propertyExpr.getName() + "\")");

		return javaExpr.toString();
	}

	public String codeGen(Var var) {
		if (trees.get(var.getVarName()) != null) // Subroutine name !!
			return "new StrV(\"" + var.getVarName() + "\")";
		else
			return "Util.getVar(\"" + var.getVarName() + "\")";
	}

	public static String mainGen(String indent) {
		StringBuilder javaStmt = new StringBuilder("");

		javaStmt.append(indent);
		javaStmt.append("public static void main(String[] args) {\r\n");
		javaStmt.append(indent);
		javaStmt.append("    Util.setClassName(className);\r\n");
		javaStmt.append(indent);
		javaStmt.append("    " + className + "_C = Util.getClass(className);\r\n");
		javaStmt.append(indent);
		javaStmt.append("    Util.main(className);\r\n");
		javaStmt.append(indent);
		javaStmt.append("}\r\n");
		javaStmt.append("\r\n");

		return javaStmt.toString();
	}
	
	public static boolean isNumber(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}

}
