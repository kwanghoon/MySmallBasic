package com.coducation.smallbasic;

import java.util.ArrayList;
import java.util.HashMap;

public class Continuous {
	HashMap<String, Stmt> kMap;
	String label = "$L";
	int count = 0;

	public Continuous() {
		kMap = new HashMap<>();
	}
	private String fresh() {
		String fresh = label + count;
		count++;

		return fresh;
	}

	public Stmt transform(Stmt stmt, Stmt stmtk) {
		if (stmt instanceof Assign)
			transform((Assign) stmt, stmtk);
		else if (stmt instanceof BlockStmt)
			transform((BlockStmt) stmt, stmtk);
		else if (stmt instanceof ExprStmt)
			transform((ExprStmt) stmt, stmtk);
		else if (stmt instanceof ForStmt)
			transform((ForStmt) stmt, stmtk);
		else if (stmt instanceof GotoStmt)
			transform((GotoStmt) stmt, stmtk);
		else if (stmt instanceof IfStmt)
			transform((IfStmt) stmt, stmtk);
		else if (stmt instanceof Label)
			transform((Label) stmt, stmtk);
		else if (stmt instanceof SubDef)
			transform((SubDef) stmt, stmtk);
		else if (stmt instanceof WhileStmt)
			transform((WhileStmt) stmt, stmtk);
		else
			System.err.println("Syntax Error! " + stmt.getClass());

		return null;
	}

	public Stmt transform(Assign assignStmt, Stmt stmtk) {
		
		return null;
	}

	public Stmt transform(BlockStmt blockStmt, Stmt stmtk) {
		ArrayList<Stmt> array = new ArrayList<>();
		if(blockStmt.getAL() == null) {
			array.add(stmtk);
			return new BlockStmt(array);
		}
//		else if()
//			GotoStmt가 있는 경우
//		else if()
//			Label이 있는 경우
//		else
//			그외의 경우
		
		return null;
	}

	public Stmt transform(ExprStmt exprStmt, Stmt stmtk) {

		return null;
	}

	public Stmt transform(ForStmt forStmt, Stmt stmtk) {
		String linit = fresh();
		String ltest = fresh();

		Stmt stmt = transform(forStmt.getBlock(), new GotoStmt(ltest));

		if (forStmt.getStep() == null) {
			//forStmt의 step을 var+1로 설정해주기
		}

		ArrayList<Stmt> init = new ArrayList<>();
		init.add(new Assign(forStmt.getVar(), forStmt.getInit()));
		init.add(new GotoStmt(ltest));
		// step이 minus이거나 divide인 경우와
		// step이 plus이거나 multiplication인 경우의 조건이 다름

		ArrayList<Stmt> test = new ArrayList<>();
		// test.add();
		kMap.put(linit, new BlockStmt(init));
		kMap.put(ltest, new BlockStmt(test));

		return new GotoStmt(linit);
	}

	public Stmt transform(GotoStmt gotoStmt, Stmt stmtk) {

		return null;
	}

	public Stmt transform(IfStmt ifStmt, Stmt stmtk) {
		Stmt thenStmt = transform(ifStmt.getThen(), stmtk);
		Stmt elseStmt;
		
		if (ifStmt.getElse() != null) {
			elseStmt = transform(ifStmt.getElse(), stmtk);
		} else {
			elseStmt = null;
		}

		return new IfStmt(ifStmt.getCond(), thenStmt, elseStmt);
	}

	public Stmt transform(Label labelStmt, Stmt stmtk) {

		return null;
	}

	public Stmt transform(SubDef subdefStmt, Stmt stmtk) {
		String l = subdefStmt.getName();

		Stmt stmt = transform(subdefStmt.getBlock(), new GotoStmt(l));

		ArrayList<Stmt> array = new ArrayList<>();

		array.add(stmt);

		kMap.put(l, new BlockStmt(array));

		return new GotoStmt(l);
	}

	public Stmt transform(WhileStmt whileStmt, Stmt stmtk) {
		String l = fresh();

		ArrayList<Stmt> array = new ArrayList<>();
		Stmt stmt = transform(whileStmt.getBlock(), new GotoStmt(l));

		array.add(new IfStmt(whileStmt.getCond(), stmt, stmtk));
		kMap.put(l, new BlockStmt(array));

		return new GotoStmt(l);
	}
}
