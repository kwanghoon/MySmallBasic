package com.coducation.smallbasic;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class MySmallBasicMain {
	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		
		String Filename;
		
		System.out.print("Enter your SmallBasic file name: ");
		Filename = scan.next();
		
		System.out.println("Test File : " + Filename);
		System.out.println("-------------------------------");

		FileReader fr = new FileReader(Filename);
		LexerAnalyzer Lexing = new LexerAnalyzer(fr);
		Parser Parsing = new Parser(Lexing);

		// Parser Test Routine.
		Nonterminal stack = Parsing.Parsing();
		if (stack.getTree() instanceof BlockStmt) {
			PrettyPrinter printer = new PrettyPrinter((BlockStmt) stack.getTree());
			printer.prettyPrint();
//			Interpreter interpreting = new Interpreter(stack);
//			Interpreter.stackInit();
//			interpreting.Interpreting();
		} else {
			System.err.println("Tree is not BlockStmt.");
		}
	}
}
