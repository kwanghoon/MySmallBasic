package com.coducation.smallbasic;

public class Eval {
	BlockStmt tree;
	int numberOfIndent;

	
	
	public Eval() {
	}

	public Eval(BlockStmt tree) {
		this.tree = tree;
	}

	public void printIndent() {
		for (int i = 0; i <= numberOfIndent; i++) {
			System.out.print("    ");
		}
	}

	public void eval() {
		numberOfIndent = 0;
		eval(this.tree);
	}

	public void eval(Assign assignStmt) {
		printIndent();

		eval(assignStmt.getLSide());
		System.out.print(" = ");
		eval(assignStmt.getRSide());
		System.out.println();
	}

	public void eval(BlockStmt blockStmt) {
		numberOfIndent++;

		for (int i = 0; i < blockStmt.getAL().size(); i++) {
			eval(blockStmt.getAL().get(i));
		}

		numberOfIndent--;
	}

	public void eval(ExprStmt exprStmt) {
		printIndent();

		eval(exprStmt.getExpr());
		System.out.println();
	}

	public void eval(ForStmt forStmt) {
		printIndent();

		System.out.print("For ");
		eval(forStmt.getVar());
		System.out.print(" = ");
		eval(forStmt.getInit());
		System.out.print(" To ");
		eval(forStmt.getEnd());
		if (forStmt.getStep() != null) {
			System.out.print(" Step ");
			eval(forStmt.getStep());
		}
		System.out.println();
		eval(forStmt.getBlock());

		printIndent();

		System.out.println("EndFor");
	}

	public void eval(GotoStmt gotoStmt) {
		printIndent();

		System.out.println("Goto " + gotoStmt.getTargetLabel());
	}

	public void eval(IfStmt ifStmt) {
		printIndent();

		System.out.print("If ");
		eval(ifStmt.getCond());
		System.out.println(" Then");

		eval(ifStmt.getThen());
		if (ifStmt.getElse() != null) {
			printIndent();

			System.out.println("Else");
			eval(ifStmt.getElse());
		}

		printIndent();
		System.out.println("EndIf");
	}

	public void eval(Label labelStmt) {
		printIndent();
		System.out.println(labelStmt.getLabel() + ":");
	}

	public void eval(SubDef subDefStmt) {
		printIndent();

		System.out.println("Sub " + subDefStmt.getName());
		eval(subDefStmt.getBlock());

		printIndent();

		System.out.println("EndSub");
	}

	public void eval(WhileStmt whileStmt) {
		printIndent();

		System.out.print("While ");
		eval(whileStmt.getCond());
		System.out.println(" ");
		eval(whileStmt.getBlock());

		printIndent();
		System.out.println("EndWhile");
	}

	public void eval(Stmt stmt) {
		if (stmt instanceof Assign)
			eval((Assign) stmt);
		else if (stmt instanceof BlockStmt)
			eval((BlockStmt) stmt);
		else if (stmt instanceof ExprStmt)
			eval((ExprStmt) stmt);
		else if (stmt instanceof ForStmt)
			eval((ForStmt) stmt);
		else if (stmt instanceof GotoStmt)
			eval((GotoStmt) stmt);
		else if (stmt instanceof IfStmt)
			eval((IfStmt) stmt);
		else if (stmt instanceof Label)
			eval((Label) stmt);
		else if (stmt instanceof SubDef)
			eval((SubDef) stmt);
		else if (stmt instanceof WhileStmt)
			eval((WhileStmt) stmt);
		else
			System.err.println("Syntax Error!" + stmt.getClass());
	}

	public void eval(ArithExpr arithExpr) {
		switch (arithExpr.GetOp()) {
		case 1:
			eval(arithExpr.GetOperand()[0]);
			System.out.print(" + ");
			break;
		case 2:
			eval(arithExpr.GetOperand()[0]);
			System.out.print(" - ");
			break;
		case 3:
			eval(arithExpr.GetOperand()[0]);
			System.out.print(" * ");
			break;
		case 4:
			eval(arithExpr.GetOperand()[0]);
			System.out.print(" / ");
			break;
		case 5:
			System.out.print("- ");
			eval(arithExpr.GetOperand()[0]);
			break;
		}
		if (arithExpr.GetOperand()[1] != null)
			eval(arithExpr.GetOperand()[1]);
	}

	public void eval(Array arrayExpr) {
		System.out.print(arrayExpr.getVar());
		for (int i = 0; i < arrayExpr.getDim(); i++) {
			System.out.print("[");
			eval(arrayExpr.getIndex(i));
			System.out.print("]");
		}
	}

	public void eval(CompExpr compExpr) {
		eval(compExpr.GetOperand()[0]);
		switch (compExpr.GetOp()) {
		case 1:
			System.out.print(" > ");
			break;
		case 2:
			System.out.print(" < ");
			break;
		case 3:
			System.out.print(" >= ");
			break;
		case 4:
			System.out.print(" <= ");
			break;
		case 5:
			System.out.print(" = ");
			break;
		case 6:
			System.out.print(" <> ");
			break;
		default:
			System.err.println("Unknown CompExpr Operator " + compExpr.GetOp());
			break;
		}
		eval(compExpr.GetOperand()[1]);
	}

	public void eval(Lit litExpr) {
		System.out.print(litExpr.gets());
	}

	public void eval(LogicalExpr logicalExpr) {
		eval(logicalExpr.GetOperand()[0]);
		switch (logicalExpr.GetOp()) {
		case 1:
			System.out.print(" And ");
			break;
		case 2:
			System.out.print(" Or ");
			break;
		default:
			System.err.println("Unknown Logical Operator " + logicalExpr.GetOp());
			break;
		}
		eval(logicalExpr.GetOperand()[1]);
	}

	public void eval(MethodCallExpr methodCallExpr) {
		System.out.print(methodCallExpr.getObj() + "." + methodCallExpr.getName() + "(");
		if (methodCallExpr.getArgs() != null) {
			int size = methodCallExpr.getArgs().size();
			for (int i = 0; i < size; i++) {
				eval(methodCallExpr.getArgs().get(i));
				if (i != size - 1)
					System.out.print(", ");
			}
		}
		System.out.print(")");

	}
	public void eval(ParenExpr parenExpr) {
		System.out.print("(");
		eval(parenExpr.get());
		System.out.print(")");
	}
	public void eval(PropertyExpr propertyExpr) {
		System.out.print(propertyExpr.getObj() + "." + propertyExpr.getName());
	}

	public void eval(SubCallExpr subCallExpr) {
		System.out.print(subCallExpr.getName() + "()");
	}

	public void eval(Var var) {
		System.out.print(var.getVarName());
	}

	public void eval(Expr expr) {
		if (expr instanceof ArithExpr)
			eval((ArithExpr) expr);
		else if (expr instanceof Array)
			eval((Array) expr);
		else if (expr instanceof CompExpr)
			eval((CompExpr) expr);
		else if (expr instanceof Lit)
			eval((Lit) expr);
		else if (expr instanceof LogicalExpr)
			eval((LogicalExpr) expr);
		else if (expr instanceof MethodCallExpr)
			eval((MethodCallExpr) expr);
		else if (expr instanceof ParenExpr)
			eval((ParenExpr) expr);
		else if (expr instanceof PropertyExpr)
			eval((PropertyExpr) expr);
		else if (expr instanceof SubCallExpr)
			eval((SubCallExpr) expr);
		else if (expr instanceof Var)
			eval((Var) expr);
		else
			System.err.println("Syntax Error! " + expr.getClass());
	}
}
