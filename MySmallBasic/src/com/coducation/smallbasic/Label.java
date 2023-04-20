package com.coducation.smallbasic;

public class Label extends Stmt
{
		public Label(String label)
		{
			super();
			this.label = label;
		} // Builder
		public String getLabel() {
			return label;
		}
		private String label;
}
