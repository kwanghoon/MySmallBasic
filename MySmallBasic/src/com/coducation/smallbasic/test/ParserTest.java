package com.coducation.smallbasic.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import com.coducation.smallbasic.BlockStmt;
import com.coducation.smallbasic.LexerAnalyzer;
import com.coducation.smallbasic.Nonterminal;
import com.coducation.smallbasic.Parser;
import com.coducation.smallbasic.PrettyPrinter;

public class ParserTest {

	@Test
	public void testSuccess() {
		String basedir = "./regressiontest/";
		String[] testcases = { 
				"ElseIf_1.sb", "ElseIf_2.sb", "ParenExpr_1.sb"
		};
		int Total = testcases.length;
		int count = 0;
		
		for (String testcase : testcases) {
			try {
				File file = new File(basedir + testcase);
				String Filename = file.getAbsolutePath();
				
				System.out.print("[ " + Filename + " ]");
	
				FileReader fr = new FileReader(Filename);
				LexerAnalyzer Lexing = new LexerAnalyzer(fr);
				Parser Parsing = new Parser(Lexing);
	
				// Parser Test Routine.
				Nonterminal stack = Parsing.Parsing();
				if (stack.getTree() instanceof BlockStmt) {
					System.out.println(" parsed successfully.");
					count++;
				} else {
					System.out.println(" failed to be parsed.");
				}
			}
			catch(FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("# of testcases parsed: " + count + " / " + Total);
	}
	
	@Test
	public void testFail() {
		String basedir = "./regressiontest/";
		String[] testcases = { 
				"SubCallNotExpr.sb"
		};
		int Total = testcases.length;
		int count = 0;
		
		for (String testcase : testcases) {
			try {
				File file = new File(basedir + testcase);
				String Filename = file.getAbsolutePath();
				
				System.out.print("[ " + Filename + " ]");
	
				FileReader fr = new FileReader(Filename);
				LexerAnalyzer Lexing = new LexerAnalyzer(fr);
				Parser Parsing = new Parser(Lexing);
	
				// Parser Test Routine.
				Nonterminal stack = Parsing.Parsing();
				if (stack.getTree() instanceof BlockStmt) {
					System.out.println(" parsed successfully.");
					count++;
				} else {
					System.out.println(" failed to be parsed.");
				}
			}
			catch(FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("# of testcases parsed: " + count + " / " + Total);
	}

}
