package com.coducation.smallbasic;

public class ParseState extends Stkelem
{
	public ParseState(String state)
	{
		this.state = state;
	}
	
	public String toString()
	{
		return state;
	}

	private String state;
}
