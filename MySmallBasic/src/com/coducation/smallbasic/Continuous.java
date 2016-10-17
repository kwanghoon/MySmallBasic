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

	private String fresh() {
		String fresh = label + count;
		count++;

		return fresh;
	}
	
	public HashMap<String,Stmt> transform(Stmt stmt) {
		Stmt stmt_main = transform(stmt, 
				new BlockStmt(new ArrayList<Stmt>()));
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
		else if (stmt instanceof WhileStmt)
			return transform((WhileStmt) stmt, stmtk);
		else {
			throw new RuntimeException("[transform] Syntax Error! " + stmt.getClass());
		}
	}

	public Stmt transform(Assign assignStmt, Stmt stmtk) {
		ArrayList<Stmt> stmts = new ArrayList<Stmt>();
		stmts.add(assignStmt);
		stmts.add(stmtk);
		return new BlockStmt(stmts);
	}

	public Stmt transform(BlockStmt blockStmt, Stmt stmtk) {
		if(blockStmt.getAL().size() == 0) {
			return stmtk;
		}
		else {
			Stmt head = blockStmt.getAL().get(0);
			ArrayList<Stmt> stmts = (ArrayList<Stmt>) blockStmt.getAL().clone();

			stmts.remove(0);
			
			if(head instanceof GotoStmt) {
				transform(new BlockStmt(stmts), stmtk);
				
				return new GotoStmt(((GotoStmt)head).getTargetLabel());
			}
			else if(head instanceof Label) {
				Stmt stmt = transform(new BlockStmt(stmts), stmtk);
				
				kMap.put(((Label)head).getLabel(), stmt);
				
				return new GotoStmt(((Label)head).getLabel());
			}
			else {
				Stmt stmt = transform(head, new BlockStmt(new ArrayList<Stmt>()));
				Stmt stmt1 = transform(new BlockStmt(stmts), stmtk);
				
				ArrayList<Stmt> block = new ArrayList<>();
				
				block.add(stmt);
				block.add(stmt1);
				
				return new BlockStmt(block); 
			}
		}
	}

	public Stmt transform(ExprStmt exprStmt, Stmt stmtk) {
		ArrayList<Stmt> block = new ArrayList<>();
		
		block.add(exprStmt);
		block.add(stmtk);
		
		return new BlockStmt(block);
	}

	public Stmt transform(ForStmt forStmt, Stmt stmtk) {
		String linit = fresh();
		String ltest = fresh();

		Stmt stmt = transform(forStmt.getBlock(),
				new BlockStmt(new ArrayList<Stmt>()));
		
		Expr step;
		
		if (forStmt.getStep() == null) {
			// forStmt의 step을 var+1로 설정해주기
			step = new Lit("1");
		}
		else {
			step = forStmt.getStep();
		}

		Stmt update = new Assign(forStmt.getVar(), 
				new ArithExpr(forStmt.getVar(), ArithExpr.PLUS, step));
		BlockStmt body = new BlockStmt(new ArrayList<Stmt>());
		body.getAL().add(stmt);
		body.getAL().add(update);
		body.getAL().add(new GotoStmt(ltest));
		
		Expr ltestCond = 
				new LogicalExpr(
						new LogicalExpr(
								new CompExpr(forStmt.getStep(), CompExpr.GREATER_EQUAL, new Lit("0")),
								LogicalExpr.AND,
								new CompExpr(forStmt.getVar(), CompExpr.LESS_EQUAL, forStmt.getEnd())),
						LogicalExpr.OR,
						new LogicalExpr(
								new CompExpr(forStmt.getStep(), CompExpr.LESS_THAN, new Lit("0")),
								LogicalExpr.AND,
								new CompExpr(forStmt.getVar(), CompExpr.GREATER_THAN, forStmt.getEnd())));
		Stmt ltestStmt = new IfStmt(ltestCond, body, stmtk);
		
		ArrayList<Stmt> init = new ArrayList<>();
		init.add(new Assign(forStmt.getVar(), forStmt.getInit()));
		init.add(new GotoStmt(ltest));
		Stmt linitStmt = new BlockStmt(init);
		
		kMap.put(linit, linitStmt);
		kMap.put(ltest, ltestStmt);

		return new GotoStmt(linit);
	}

	public Stmt transform(GotoStmt gotoStmt, Stmt stmtk) {
		// stmtk를 어떻게 다뤄야하지??
		
		String l = gotoStmt.getTargetLabel();
		
		return new GotoStmt(l);
	}

	public Stmt transform(IfStmt ifStmt, Stmt stmtk) {
		Stmt thenStmt = transform(ifStmt.getThen(), stmtk);
		Stmt elseStmt;

		if (ifStmt.getElse() != null) {
			elseStmt = transform(ifStmt.getElse(), stmtk);
		} else {
			elseStmt = stmtk;
		}

		return new IfStmt(ifStmt.getCond(), thenStmt, elseStmt);
	}

	public Stmt transform(Label labelStmt, Stmt stmtk) {
		kMap.put(labelStmt.getLabel(), stmtk);
		
		return new GotoStmt(labelStmt.getLabel());
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
