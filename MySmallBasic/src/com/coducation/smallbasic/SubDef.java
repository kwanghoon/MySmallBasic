package com.coducation.smallbasic;

public class SubDef extends Stmt
{
		public SubDef(String name, Stmt block)
		{
			super();
			this.name = name;
			this.block = block;
		} // Builder
		
		private String name;
		private Stmt block;
}
