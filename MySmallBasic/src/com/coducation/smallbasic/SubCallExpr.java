package com.coducation.smallbasic;

public class SubCallExpr extends Stmt
{
	public SubCallExpr(String name)
	{
		super();
		this.name = name;
	} // Builder
	public String getName() {
		return name;
	}
	
	private String name;

}
