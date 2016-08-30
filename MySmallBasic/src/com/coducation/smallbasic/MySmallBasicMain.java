package com.coducation.smallbasic;

import java.io.FileReader;
import java.io.IOException;


public class MySmallBasicMain
{
	public static void main(String[] args) throws IOException
	{
		String Filename = "Sample\\" + "ParsingTest.sb";
		System.out.println("Test File : " + Filename);
		System.out.println("-------------------------------");
		
		FileReader fr = new FileReader(Filename);
		LexerAnalyzer Lexing = new LexerAnalyzer(fr);
		Parser Parsing = new Parser(Lexing);
		
		
		// Parser Test Routine.
			Nonterminal stack = Parsing.Parsing();
			
			Interpreter interpreting = new Interpreter(stack); 
			interpreting.Interpreting();
	}
}
