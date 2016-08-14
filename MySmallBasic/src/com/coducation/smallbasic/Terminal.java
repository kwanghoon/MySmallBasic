package com.coducation.smallbasic;

public class Terminal extends Stkelem
{
	public Terminal()
	{
		super();
		this.Syntax = "";
		this.TokenInfo = Token.NONE;
		this.ch_index = 0;
		this.line_index = 0;
	}
	
	public Terminal(String NewSyntax, Token NewTokenInfo, int NewCh_index, int NewLine_index)
	{
		this.Syntax = NewSyntax;
		this.TokenInfo = NewTokenInfo;
		this.ch_index = NewCh_index;
		this.line_index = NewLine_index;
	}
	
	public String getSyntax()
	{
		return Syntax;
	}
	
	public Token getTokenInfo()
	{
		return TokenInfo;
	}
	public int getCh_index(){
		return ch_index;
	}
	public int getLine_index(){
		return line_index;
	}
	
	public String toString()
	{
		return Syntax;
	}
	
	private String Syntax;
	private Token TokenInfo;
	private int ch_index;
	private int line_index;
}
