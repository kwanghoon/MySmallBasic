package com.coducation.smallbasic;

public class GotoStmt extends Stmt
{
		public GotoStmt(String targetLabel)
		{
			super();
			this.targetLabel = targetLabel;
		} // Builder
		
		private String targetLabel;
}
