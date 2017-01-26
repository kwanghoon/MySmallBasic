package com.coducation.smallbasic;

import java.util.ArrayList;
import java.util.HashMap;

public class Continuous {
	HashMap<String, Stmt> kMap;
	public static final String label = "$L";
	public static final String mainLabel = "$main";
	int count = 0;

	public Continuous() {
		kMap = new HashMap<>();
	}
	public BasicBlockEnv blocks() {
		return new BasicBlockEnv(kMap);
	}

	private String fresh() {
		String fresh = label + count;
		count++;

		return fresh;
	}

	public HashMap<String, Stmt> transform(Stmt stmt) {
		Stmt stmt_main = transform(stmt, new BlockStmt(new ArrayList<Stmt>()));
		kMap.put(mainLabel, stmt_main);

		return kMap;
	}

	public Stmt transform(Stmt stmt, Stmt stmtk) {
		if (stmt instanceof Assign)
			return transform((Assign) stmt, stmtk);
		else if (stmt instanceof BlockStmt)
			return transform((BlockStmt) stmt, stmtk);
		else if (stmt instanceof ExprStmt)
			return transform((ExprStmt) stmt, stmtk);
		else if (stmt instanceof ForStmt)
			return transform((ForStmt) stmt, stmtk);
		else if (stmt instanceof GotoStmt)
			return transform((GotoStmt) stmt, stmtk);
		else if (stmt instanceof IfStmt)
			return transform((IfStmt) stmt, stmtk);
		else if (stmt instanceof Label)
			return transform((Label) stmt, stmtk);
		else if (stmt instanceof SubDef)
			return transform((SubDef) stmt, stmtk);
		else if (stmt instanceof SubCallExpr)
			return transform((SubCallExpr) stmt, stmtk);
		else if (stmt instanceof WhileStmt)
			return transform((WhileStmt) stmt, stmtk);
		else {
			throw new RuntimeException("[transform] Syntax Error! " + stmt.getClass());
		}
	}

	public Stmt transform(Assign assignStmt, Stmt stmtk) {
		return merge(assignStmt, stmtk);
	}

	public Stmt transform(BlockStmt blockStmt, Stmt stmtk) {
		if (blockStmt.getAL().size() == 0) {
			return stmtk;
		} else {
			Stmt head = blockStmt.getAL().get(0);
			ArrayList<Stmt> stmts = (ArrayList<Stmt>) blockStmt.getAL().clone();

			stmts.remove(0);

			if (head instanceof GotoStmt) {
				transform(new BlockStmt(stmts), stmtk);

				return new GotoStmt(((GotoStmt) head).getTargetLabel());
			} else if (head instanceof Label) {
				Stmt stmt = transform(new BlockStmt(stmts), stmtk);

				kMap.put(((Label) head).getLabel(), stmt);

				return new GotoStmt(((Label) head).getLabel());
			} else {
				Stmt stmtsStmtk = transform(new BlockStmt(stmts), stmtk);			
				Stmt stmt = transform(head, stmtsStmtk);

				return stmt;
			}
		}
	}

	public Stmt transform(ExprStmt exprStmt, Stmt stmtk) {
		return merge(exprStmt, stmtk);
	}

	public Stmt transform(ForStmt forStmt, Stmt stmtk) {
		String ltest = fresh();

		// i = init value;
		// Goto ltest;
		
		ArrayList<Stmt> init = new ArrayList<>();
		init.add(new Assign(forStmt.getVar(), forStmt.getInit()));
		init.add(new GotoStmt(ltest));
		
		Stmt linitStmt = new BlockStmt(init);
		
		// step
		Expr step;

		if (forStmt.getStep() == null) {
			// forStmt의 step을 var+1로 설정해주기
			step = new Lit(1);
		} else {
			step = forStmt.getStep();
		}

		// i = i + step;
		// Goto ltest;
		Stmt update = new Assign(forStmt.getVar(), new ArithExpr(forStmt.getVar(), ArithExpr.PLUS, step));
		
		ArrayList<Stmt> blockstmts = new ArrayList<Stmt>();
		blockstmts.add(update);
		blockstmts.add(new GotoStmt(ltest));
		
		Stmt blockstmtk = new BlockStmt(blockstmts);
		
		// for body
		Stmt body = transform(forStmt.getBlock(), blockstmtk);

		Expr ltestCond = new LogicalExpr(
				new LogicalExpr(new CompExpr(forStmt.getStep(), CompExpr.GREATER_THAN, new Lit(0)), LogicalExpr.AND,
						new CompExpr(forStmt.getVar(), CompExpr.LESS_EQUAL, forStmt.getEnd())),
				LogicalExpr.OR, new LogicalExpr(new CompExpr(forStmt.getStep(), CompExpr.LESS_THAN, new Lit(0)),
						LogicalExpr.AND, new CompExpr(forStmt.getVar(), CompExpr.GREATER_EQUAL, forStmt.getEnd())));
		Stmt ltestStmt = newIfStmt(ltestCond, body, stmtk);

		kMap.put(ltest, ltestStmt);

		return linitStmt;
	}

	public Stmt transform(GotoStmt gotoStmt, Stmt stmtk) {
		// stmtk를 어떻게 다뤄야하지??

		String l = gotoStmt.getTargetLabel();

		return new GotoStmt(l);
	}

	public Stmt transform(IfStmt ifStmt, Stmt stmtk) {
		Stmt thenStmt = transform(ifStmt.getThen(), new BlockStmt(new ArrayList()));
		Stmt elseStmt;

		if (ifStmt.getElse() != null) {
			elseStmt = transform(ifStmt.getElse(), new BlockStmt(new ArrayList()));
			return merge(newIfStmt(ifStmt.getCond(), thenStmt, elseStmt).copyInfo(ifStmt), stmtk);
		} else {
			return merge(newIfStmt(ifStmt.getCond(), thenStmt, null).copyInfo(ifStmt), stmtk);
		}
	}

	public Stmt transform(Label labelStmt, Stmt stmtk) {
		kMap.put(labelStmt.getLabel(), stmtk);

		return new GotoStmt(labelStmt.getLabel());
	}

	public Stmt transform(SubDef subdefStmt, Stmt stmtk) {
		String l = subdefStmt.getName();

		Stmt stmt = transform(subdefStmt.getBlock(), new BlockStmt(new ArrayList<Stmt>()));

		ArrayList<Stmt> array = new ArrayList<>();

		array.add(stmt);

		kMap.put(l, new BlockStmt(array));

		return stmtk;
	}
	public Stmt transform(SubCallExpr subcallExpr, Stmt stmtk) {
		return merge(subcallExpr, stmtk);
	}

	public Stmt transform(WhileStmt whileStmt, Stmt stmtk) {
		String l = fresh();

		ArrayList<Stmt> array = new ArrayList<>();
		Stmt stmt = transform(whileStmt.getBlock(), new GotoStmt(l));

		array.add(newIfStmt(whileStmt.getCond(), stmt, stmtk));
		kMap.put(l, new BlockStmt(array));

		return new GotoStmt(l);
	}

	private Stmt merge(Stmt... stmts) {
		Stmt accustmt = new BlockStmt(new ArrayList<Stmt>());
		for (Stmt stmt : stmts) {
			accustmt = merge(accustmt, stmt);
		}
		return accustmt;
	}

	private Stmt merge(Stmt stmt1, Stmt stmt2) {
		if (isEmpty(stmt1))
			return stmt2;
		else if (isEmpty(stmt2))
			return stmt1;
		else {
			BlockStmt blockstmt = new BlockStmt(new ArrayList<Stmt>());
			if (stmt1 instanceof BlockStmt) {
				BlockStmt blockstmt1 = (BlockStmt) stmt1;
				for (Stmt stmt : blockstmt1.getAL()) {
					blockstmt.getAL().add(stmt);
				}
			} else
				blockstmt.getAL().add(stmt1);

			if (stmt2 instanceof BlockStmt) {
				BlockStmt blockstmt2 = (BlockStmt) stmt2;
				for (Stmt stmt : blockstmt2.getAL()) {
					blockstmt.getAL().add(stmt);
				}
			} else
				blockstmt.getAL().add(stmt2);

			return blockstmt;
		}
	}

	private boolean isEmpty(Stmt stmt) {
		if (stmt instanceof BlockStmt) {
			BlockStmt blockstmt = (BlockStmt) stmt;
			if (blockstmt.getAL().size() == 0)
				return true;
			else {
				boolean result = true;
				for (Stmt substmt : blockstmt.getAL()) {
					result = isEmpty(substmt);
					if (result == false)
						break;
				}
				return result;
			}
		} else
			return false;
	}

	private IfStmt newIfStmt(Expr cond, Stmt thenstmt, Stmt elsestmt) {
		if (isEmpty(elsestmt))
			elsestmt = null;
		return new IfStmt(cond, thenstmt, elsestmt);
	}
}
