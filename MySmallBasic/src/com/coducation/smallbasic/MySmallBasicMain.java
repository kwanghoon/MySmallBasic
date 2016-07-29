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
			System.out.println("----------------------------------------------");
			System.out.println(f);
			FileReader fr = new FileReader("Sample\\" + f);
			LexerAnalyzer Lexing = new LexerAnalyzer(fr);
			Parser Parsing = new Parser(Lexing);
		
			// Line_Parser Test Routine.
			for(int line_index = 0; line_index < Lexing.get_size(); line_index++)
			{
				Parsing.Parsing(line_index);
			}
		}
	}
}
