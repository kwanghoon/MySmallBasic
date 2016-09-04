package com.coducation.smallbasic;

import java.util.ArrayList;

public class BlockStmt extends Stmt
{
		public BlockStmt(ArrayList<Stmt> stmts) 
		{
			super();
			this.stmts = stmts;
		} // Builder
		
		public Result evalStmt(Env env) throws Exception {
			Result res = null;
			
			for(int size = 0; size < stmts.size(); size++){
				Interpreter.push(stmts.get(size));			//상위 stmt 저장
				res = (stmts.get(size)).evalStmt(env);
				Interpreter.pop(Interpreter.getStackSize()-1);
			}
		
			return res;
		}
		
		
		public ArrayList<Stmt> getAL(){return stmts;} // Getter added.
		private ArrayList<Stmt> stmts; // private Stmt[] -> private ArrayList<Stmt>
}
