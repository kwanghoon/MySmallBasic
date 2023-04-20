package com.coducation.smallbasic.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.coducation.smallbasic.BlockStmt;
import com.coducation.smallbasic.BBTransform;
import com.coducation.smallbasic.LexerAnalyzer;
import com.coducation.smallbasic.Nonterminal;
import com.coducation.smallbasic.Parser;
import com.coducation.smallbasic.PrettyPrinter;
import com.coducation.smallbasic.Stmt;

public class RegressionTest {

	@Test
	public void test() {
		File dir = new File(".//Sample/");
		File[] files = dir.listFiles();
		int Total = files.length;
		int count = 0;
		
		for (File file : files) {
			try {
				String Filename = file.getAbsolutePath();
				
				System.out.println("[ " + Filename + " ]");
	
				FileReader fr = new FileReader(Filename);
				LexerAnalyzer Lexing = new LexerAnalyzer(fr);
				Parser Parsing = new Parser(Lexing);
	
				// Parser Test Routine.
				Nonterminal stack = Parsing.Parsing();
				if (stack.getTree() instanceof BlockStmt) {
					PrettyPrinter printer = new PrettyPrinter((BlockStmt) stack.getTree());
					
					System.out.println("Pretty Printing.");
					printer.prettyPrint();
					
					System.out.println("Transformation - Continuation blocks.");
					HashMap<String,Stmt> map = new BBTransform().transform((BlockStmt)stack.getTree());
					Set<Map.Entry<String,Stmt>> set = map.entrySet();
					for (Map.Entry<String,Stmt> entry : set) {
						String key = entry.getKey();
						Stmt stmt = entry.getValue();
						System.out.println(key + ":");
						new PrettyPrinter(stmt).prettyPrint();
					}
					System.out.println();
					
	//				Interpreter interpreting = new Interpreter(stack);
	//				Interpreter.stackInit();
	//				interpreting.Interpreting();
					
					count++;
				} else {
					System.err.println("Tree is not BlockStmt.");
				}
			}
			catch(FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Tested: " + count + " / " + Total);
	}

}
