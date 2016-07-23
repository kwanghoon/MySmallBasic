package com.coducation.smallbasic;

public class SyntaxPair
{
	public SyntaxPair()
	{
		this.Syntax = "";
		this.TokenInfo = Token.NONE;
	}
	
	public SyntaxPair(String NewSyntax, Token NewTokenInfo)
	{
		this.Syntax = NewSyntax;
		this.TokenInfo = NewTokenInfo;
	}
	
	public String getSyntax()
	{
		return Syntax;
	}
	
	public Token getTokenInfo()
	{
		return TokenInfo;
	}
	
	private String Syntax;
	private Token TokenInfo;
}
