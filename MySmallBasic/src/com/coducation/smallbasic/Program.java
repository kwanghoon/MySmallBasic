package com.coducation.smallbasic;

public class Program
{
	public Program(String name, Stmt block)
	{
		this.name = name;
		this.block = block;
	}
	
	public void prettyprint()
	{
		//empty
	}
	
	private String name;
	private Stmt block;
}
