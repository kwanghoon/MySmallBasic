package com.coducation.smallbasic;

import java.util.ArrayList;

public class BlockStmt extends Stmt
{
		public BlockStmt(ArrayList<Stmt> stmts)
		{
			super();
			this.stmts = stmts;
		} // Builder
		
		
		public ArrayList<Stmt> getAL(){return stmts;} // Getter added.
		private ArrayList<Stmt> stmts; // private Stmt[] -> private ArrayList<Stmt>
}
