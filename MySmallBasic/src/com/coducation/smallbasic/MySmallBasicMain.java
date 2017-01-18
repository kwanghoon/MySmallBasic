package com.coducation.smallbasic;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

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
			
			HashMap<String,Stmt> map = new Continuous().transform((BlockStmt)stack.getTree());
			Set<Map.Entry<String,Stmt>> set = map.entrySet();
			for (Map.Entry<String,Stmt> entry : set) {
				String key = entry.getKey();
				Stmt stmt = entry.getValue();
				System.out.println(key + ":");
				new PrettyPrinter(stmt).prettyPrint();
			}
			System.out.println();
			
			System.out.println("Excution");
			try {
				new Eval(new BasicBlockEnv(map)).eval(args);
			}
			catch(InterpretException exn) {
				if (exn.getProgramEnd() == false)
					throw exn;
			}
		} else {
			System.err.println("Tree is not BlockStmt.");
		}
	}
}
