package com.coducation.smallbasic;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// Lexer Test

public class MySmallBasicMain
{
	public static void main(String[] args) throws IOException
	{
		for(String f : args)
		{
			System.out.println(f);
			FileReader fr = new FileReader("Sample\\" + f);
			LexerAnalyzer Lexing = new LexerAnalyzer(fr);
			Lexing.Lexing();
		}
	}
}