package com.coducation.smallbasic;

public class SubDef extends Stmt
{
		public SubDef(String name, Stmt block)
		{
			super();
			this.name = name;
			this.block = block;
		} // Builder
		
		public String getName() {
			return name;
		}
		public Stmt getBlock() {
			return block;
		}
		
		private String name;
		private Stmt block;
}