package com.coducation.smallbasic;

public class GotoStmt extends Stmt
{
		public GotoStmt(String targetLabel)
		{
			super();
			this.targetLabel = targetLabel;
		} // Builder
		
		public Result evalStmt(Env env) throws Exception{ 
			
			throw new Exception(targetLabel);
		}
		public String getTargetLabel() {
			return targetLabel;
		}
		
		private String targetLabel;
}
