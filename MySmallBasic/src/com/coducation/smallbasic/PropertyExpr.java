package com.coducation.smallbasic;

public class PropertyExpr extends Expr
{
	public PropertyExpr(String obj, String name)
	{
		super();
		this.obj = obj;
		this.name = name;
	} // Builder
	public String getObj() {
		return obj;
	}
	public String getName() {
		return name;
	}
	private String obj, name; // cf. obj . name
}
