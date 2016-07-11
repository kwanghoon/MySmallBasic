package com.coducation.smallbasic;

import java.io.FileReader;
import java.io.IOException;

// Lexer Test

public class MySmallBasicMain
{
	public static void main(String[] args) throws IOException
	{
		FileReader fr = new FileReader("Sample/Bricks.sb");
		LexerAnalyzer Lexing = new LexerAnalyzer(fr);
		
		Lexing.Lexing();
	}
}
