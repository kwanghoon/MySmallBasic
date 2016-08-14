package com.coducation.smallbasic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Parser 
{
	Parser(LexerAnalyzer Lexer) throws IOException
	{
		stack = new Stack<Stkelem>();
		index_list = new ArrayList<Expr>();
		Lexing_result = Lexer.Lexing();
		final_line = Lexer.get_size();
		String temp_str;
		
		Tokens = new ArrayList<Terminal>();
		Parsing_Table = new ArrayList<String>();
		State_trans_table = new ArrayList<String>();
		Grammer_rule = new HashMap<Integer, String>();
		
		// initalize Grammer rule and Parsing Table and State trans table...
		FileReader Gr_fr = new FileReader("sb_grammer_rule.txt");
		FileReader Pt_fr = new FileReader("sb_parsing_table.txt");
		FileReader St_fr = new FileReader("sb_state_trans.txt");
		BufferedReader Gr_br = new BufferedReader(Gr_fr);
		BufferedReader Pt_br = new BufferedReader(Pt_fr);	
		BufferedReader St_br = new BufferedReader(St_fr);
		
		while((temp_str = Gr_br.readLine()) != null)
		{
			String GrlineStr = "";
			int GrlineNumber = 0;
			
			for(int i = 0; i < temp_str.length(); i++)
				if(temp_str.charAt(i) == ':')
				{
					GrlineNumber = atoi(GrlineStr);
					Grammer_rule.put(GrlineNumber, temp_str.substring(i+1).trim());
					break;
				}
				else
					GrlineStr = GrlineStr + temp_str.charAt(i);
		}
		while((temp_str = Pt_br.readLine()) != null)
			Parsing_Table.add(temp_str);
		
		while((temp_str = St_br.readLine()) != null)
			State_trans_table.add(temp_str);
	}
	
	@SuppressWarnings("unchecked")
	public Nonterminal Parsing() throws FileNotFoundException
	{	
		// Initialize stack
		stack.clear();
		stack.push(new ParseState("[0]"));
		
		Object tree = null;
		int line_index = 0;
		int tok_index = 0;
		
		while(line_index < final_line)
		{
			tok_index = 0;
			while(tok_index < Lexing_result.get(line_index).size())
			{
					Tokens.add(Lexing_result.get(line_index).get(tok_index++));
			}
			line_index++;
		}
		System.out.println("==================================");
		
		// Main Parsing Routine.
		while(true)
		{
			ParseState current_state = (ParseState) stack.lastElement(); // set current_state [0]
			Terminal a = new Terminal();
			if(!Tokens.isEmpty())
				a = Tokens.get(0); // get first SyntaxPair (Only if Tokens isn't empty)
			
			if(a.getTokenInfo() == Token.END_OF_TOKENS) // if firstToken is END_OF_TOKENS, File Parsing Success.
			{
				System.out.println("Parsing Success." + " " + (line_index-1) + " lines.");
				return (Nonterminal)stack.get(1);
			}
			
			String data = Check_state(current_state, a);
			String[] data_arr = data.split(" ");		
			String order = data_arr[0];
			String state = "";
			

			if(!order.equals("LALRAccept"))
				state = data_arr[1];	
			
			switch(order)
			{
				case "LALRShift":
					System.out.println(order + " " + state);
					stack.push(a);
					stack.push(new ParseState(state));
					Tokens.remove(0);
					break;
				case "LALRReduce":
				{	
					System.out.println(order + " " + state);
					int state_num = atoi(state);

					String[] reduce_arr = Grammer_rule.get(state_num).split("->"); // lhs, rhs split.
					String lhs = reduce_arr[0].trim();
					String rhs;
					int count;
					
					if(!(reduce_arr.length == 1)) // -> "empty"
					{
						rhs = reduce_arr[1].trim();
						count = rhs.split(" ").length; 	// *2 => pop count.
					}
					else
					{
						rhs = "";
						count = 0;
					}
					
					int last_stack_tree_index = stack.size()-1; // This value indicate last "Tree" Position at the stack.
					
					if(Grammer_rule.get(state_num).equals("Prog -> MoreThanOneStmt"))
					{
						
					}
					else if(Grammer_rule.get(state_num).equals("Stmt -> ExprStatement"))
					{	
						tree = stack.get(last_stack_tree_index-1);
						
						if(tree instanceof Nonterminal)
							tree = ((Nonterminal) tree).getTree();
					}
					else if(Grammer_rule.get(state_num).equals("Stmt -> While Expr CRStmtCRs EndWhile")) // case While
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-3); // CondExpr
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-5); // Block
						
						tree = new WhileStmt((CondExpr)sub_tree2.getTree(), (Stmt)sub_tree1.getTree());
			
					}
					else if(Grammer_rule.get(state_num).equals("Stmt -> ID :")) // case Label
					{
						Terminal sub_tree1 = (Terminal)stack.get(last_stack_tree_index-3);
						tree = new Label(sub_tree1.getSyntax());
						
					}
					else if(Grammer_rule.get(state_num).equals("Stmt -> Goto ID")) // case Goto
					{
						Terminal sub_tree1 = (Terminal)stack.get(last_stack_tree_index-1);
						tree = new GotoStmt(sub_tree1.getSyntax());
					}
					else if(Grammer_rule.get(state_num).equals("Stmt -> For ID = Expr To Expr OptStep CRStmtCRs EndFor")) // case For
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-3);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-5);
						Nonterminal sub_tree3 = (Nonterminal)stack.get(last_stack_tree_index-7);
						Nonterminal sub_tree4 = (Nonterminal)stack.get(last_stack_tree_index-11);
						Terminal sub_tree5 = (Terminal)stack.get(last_stack_tree_index-15);
						
						tree = new ForStmt(new Var(sub_tree5.getSyntax()), (Expr)sub_tree4.getTree(), (Expr)sub_tree3.getTree(), (Expr)sub_tree2.getTree(), (Stmt)sub_tree1.getTree());					
					}
					else if(Grammer_rule.get(state_num).equals("Stmt -> Sub ID CRStmtCRs EndSub")) // case SubRoutine Definition.
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-3);
						Terminal sub_tree2 = (Terminal)stack.get(last_stack_tree_index-5);
						
						tree = new SubDef(sub_tree2.getSyntax(), (Stmt)sub_tree1.getTree());
					}
					else if(Grammer_rule.get(state_num).equals("Stmt -> If Expr Then CRStmtCRs MoreThanZeroElseIf")) // case If
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-3);
						Nonterminal sub_tree3 = (Nonterminal)stack.get(last_stack_tree_index-7);
						
						tree = new IfStmt((CondExpr)sub_tree3.getTree(), (Stmt)sub_tree2.getTree(), (Stmt)sub_tree1.getTree());
					}
					else if(Grammer_rule.get(state_num).equals("MoreThanZeroElseIf -> OptionalElse")) // Else, or not.
					{
						tree = stack.get(last_stack_tree_index-1);
						
						if(tree instanceof Nonterminal)
							tree = ((Nonterminal) tree).getTree();
					}
					else if(Grammer_rule.get(state_num).equals("MoreThanZeroElseIf -> ElseIf Expr Then CRStmtCRs MoreThanZeroElseIf")) // case ElseIf
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-3);
						Nonterminal sub_tree3 = (Nonterminal)stack.get(last_stack_tree_index-7);
						
						tree = new IfStmt((CondExpr)sub_tree3.getTree(), (Stmt)sub_tree2.getTree(), (Stmt)sub_tree1.getTree());
					}
					else if(Grammer_rule.get(state_num).equals("OptionalElse -> EndIf")) // not Else
					{
						tree = null;
					}
					else if(Grammer_rule.get(state_num).equals("OptionalElse -> Else CRStmtCRs EndIf")) // Else
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-3);
						
						tree = (Stmt)sub_tree1.getTree();
					}
					else if(Grammer_rule.get(state_num).equals("ExprStatement -> ID = Expr")) // Assign
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Terminal sub_tree2 = (Terminal)stack.get(last_stack_tree_index-5);
						
						tree = new Assign(new Var(sub_tree2.getSyntax()), (Expr)sub_tree1.getTree());					
					}
					else if(Grammer_rule.get(state_num).equals("ExprStatement -> ID . ID = Expr")) // Property Assign
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Terminal sub_tree2 = (Terminal)stack.get(last_stack_tree_index-5);
						Terminal sub_tree3 = (Terminal)stack.get(last_stack_tree_index-9);
						
						tree = new Assign(new PropertyExpr(sub_tree3.getSyntax(), sub_tree2.getSyntax()), (Expr)sub_tree1.getTree());

					}
					else if(Grammer_rule.get(state_num).equals("ExprStatement -> ID . ID ( Exprs )")) // MethodCallExprStmt
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-3);
						Terminal sub_tree2 = (Terminal)stack.get(last_stack_tree_index-7);
						Terminal sub_tree3 = (Terminal)stack.get(last_stack_tree_index-11);
		
						tree = new ExprStmt(new MethodCallExpr(sub_tree3.getSyntax(), sub_tree2.getSyntax(),(ArrayList<Expr>)sub_tree1.getTree()));
					}
					else if(Grammer_rule.get(state_num).equals("ExprStatement -> ID ( )")) // SubRoutineCallExprStmt
					{
						Terminal sub_tree1 = (Terminal)stack.get(last_stack_tree_index-5);
						tree = new ExprStmt(new SubCallExpr(sub_tree1.getSyntax()));
					}
					else if(Grammer_rule.get(state_num).equals("ExprStatement -> ID Idxs = Expr")) // Array Assign
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Terminal sub_tree2 = (Terminal)stack.get(last_stack_tree_index-7);
						Array array = new Array( sub_tree2.getSyntax(), index_list);
						
						tree =  new Assign((Expr)array, (Expr)sub_tree1.getTree());	
					}
					else if(Grammer_rule.get(state_num).equals("CRStmtCRs -> CR TheRest")) // CRStmt => blockStmt
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						
						ArrayList<Stmt> ALStmt = (ArrayList<Stmt>)sub_tree1.getTree();
						tree = new BlockStmt(ALStmt);
					}
					else if(Grammer_rule.get(state_num).equals("TheRest ->")) // empty
					{
						tree = null;				
					}
					else if(Grammer_rule.get(state_num).equals("TheRest -> Stmt CR TheRest")) // At least One Stmt
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-5);
						
						ArrayList<Stmt> ALStmt = new ArrayList<Stmt>();
						
						if(sub_tree1.getTree() != null)
							ALStmt = (ArrayList<Stmt>)sub_tree1.getTree();
						
						ALStmt.add(0, (Stmt)sub_tree2.getTree());
						tree = ALStmt;							
					}
					else if(Grammer_rule.get(state_num).equals("MoreThanOneStmt -> Stmt"))
					{
						tree = (Nonterminal)stack.get(last_stack_tree_index-1);
						
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
							
						
						ArrayList<Stmt> ALStmt = new ArrayList<Stmt>();
						ALStmt.add((Stmt)sub_tree1.getTree());
						tree = new BlockStmt(ALStmt);
					}
					else if(Grammer_rule.get(state_num).equals("MoreThanOneStmt -> Stmt CR MoreThanOneStmt"))
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-5);
						
						BlockStmt block = (BlockStmt)sub_tree1.getTree();
						ArrayList<Stmt> ALStmt = block.getAL();
						ALStmt.add(0, (Stmt)sub_tree2.getTree());

						tree = new BlockStmt(ALStmt);
					}
					else if(Grammer_rule.get(state_num).equals("OptStep ->"))
					{
						tree = new Lit("1");
					}
					else if(Grammer_rule.get(state_num).equals("OptStep -> Step Expr"))
					{
						tree = stack.get(last_stack_tree_index-1);
						
						if(tree instanceof Nonterminal)
						{
							tree = ((Nonterminal) tree).getTree();
						}
					}
					else if(Grammer_rule.get(state_num).equals("Expr -> CondExpr"))
					{
						tree = stack.get(last_stack_tree_index-1);
						
						if(tree instanceof Nonterminal)
							tree = ((Nonterminal) tree).getTree();
					}
					else if(Grammer_rule.get(state_num).equals("Exprs ->"))
					{
						tree = null;
					}
					else if(Grammer_rule.get(state_num).equals("Exprs -> MoreThanOneExpr"))
					{
						tree = stack.get(last_stack_tree_index-1);
						
						if(tree instanceof Nonterminal)
							tree = ((Nonterminal) tree).getTree();
					}
					else if(Grammer_rule.get(state_num).equals("MoreThanOneExpr -> Expr"))
					{
						tree = (Nonterminal)stack.get(last_stack_tree_index-1);
						
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);		
						
						ArrayList<Expr> ALExpr = new ArrayList<Expr>();
						ALExpr.add((Expr)sub_tree1.getTree());
						tree = ALExpr;
					}
					else if(Grammer_rule.get(state_num).equals("MoreThanOneExpr -> Expr , MoreThanOneExpr"))
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-5);
						
						ArrayList<Expr> ALExpr = (ArrayList<Expr>)sub_tree1.getTree();
						ALExpr.add(0, (Expr)sub_tree2.getTree());
						tree = ALExpr;			
					}
					else if(Grammer_rule.get(state_num).equals("CondExpr -> OrExpr"))
					{
						tree = stack.get(last_stack_tree_index-1);
						
						if(tree instanceof Nonterminal)
							tree = ((Nonterminal) tree).getTree();
					}
					else if(Grammer_rule.get(state_num).equals("OrExpr -> OrExpr Or AndExpr"))
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-5);
						
						tree = new LogicalExpr((Expr)sub_tree2.getTree(), 2, (Expr)sub_tree1.getTree());
					}
					else if(Grammer_rule.get(state_num).equals("OrExpr -> AndExpr"))
					{
						tree = stack.get(last_stack_tree_index-1);
						
						if(tree instanceof Nonterminal)
							tree = ((Nonterminal) tree).getTree();
					}
					else if(Grammer_rule.get(state_num).equals("AndExpr -> AndExpr And EqNeqExpr"))
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-5);
						
						tree = new LogicalExpr((Expr)sub_tree2.getTree(), 1, (Expr)sub_tree1.getTree());
					}
					else if(Grammer_rule.get(state_num).equals("AndExpr -> EqNeqExpr"))
					{
						tree = stack.get(last_stack_tree_index-1);
						
						if(tree instanceof Nonterminal)
							tree = ((Nonterminal) tree).getTree();
					}
					else if(Grammer_rule.get(state_num).equals("EqNeqExpr -> EqNeqExpr = CompExpr"))
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-5);
						
						tree = new CompExpr((Expr)sub_tree2.getTree(), 5, (Expr)sub_tree1.getTree());
					}
					else if(Grammer_rule.get(state_num).equals("EqNeqExpr -> EqNeqExpr <> CompExpr"))
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-5);
						
						tree = new CompExpr((Expr)sub_tree2.getTree(), 6, (Expr)sub_tree1.getTree());						
					}
					else if(Grammer_rule.get(state_num).equals("EqNeqExpr -> CompExpr"))
					{
						tree = stack.get(last_stack_tree_index-1);
						
						if(tree instanceof Nonterminal)
							tree = ((Nonterminal) tree).getTree();
					}
					else if(Grammer_rule.get(state_num).equals("CompExpr -> CompExpr < AdditiveExpr"))
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-5);
						
						tree = new CompExpr((Expr)sub_tree2.getTree(), 1, (Expr)sub_tree1.getTree());
					}
					else if(Grammer_rule.get(state_num).equals("CompExpr -> CompExpr <= AdditiveExpr"))
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-5);
						
						tree = new CompExpr((Expr)sub_tree2.getTree(), 2, (Expr)sub_tree1.getTree());						
					}
					else if(Grammer_rule.get(state_num).equals("CompExpr -> CompExpr > AdditiveExpr"))
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-5);
						
						tree = new CompExpr((Expr)sub_tree2.getTree(), 3, (Expr)sub_tree1.getTree());						
					}
					else if(Grammer_rule.get(state_num).equals("CompExpr -> CompExpr >= AdditiveExpr"))
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-5);
						
						tree = new CompExpr((Expr)sub_tree2.getTree(), 4, (Expr)sub_tree1.getTree());						
					}
					else if(Grammer_rule.get(state_num).equals("CompExpr -> AdditiveExpr"))
					{
						tree = stack.get(last_stack_tree_index-1);
						
						if(tree instanceof Nonterminal)
							tree = ((Nonterminal) tree).getTree();
					}
					else if(Grammer_rule.get(state_num).equals("AdditiveExpr -> AdditiveExpr + MultiplicativeExpr"))
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-5);
						
						tree = new ArithExpr((Expr)sub_tree2.getTree(), 1, (Expr)sub_tree1.getTree());						
					}
					else if(Grammer_rule.get(state_num).equals("AdditiveExpr -> AdditiveExpr - MultiplicativeExpr"))
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-5);
						
						tree = new ArithExpr((Expr)sub_tree2.getTree(), 2, (Expr)sub_tree1.getTree());								
					}
					else if(Grammer_rule.get(state_num).equals("AdditiveExpr -> MultiplicativeExpr"))
					{
						tree = stack.get(last_stack_tree_index-1);
						
						if(tree instanceof Nonterminal)
							tree = ((Nonterminal) tree).getTree();
					}
					else if(Grammer_rule.get(state_num).equals("MultiplicativeExpr -> MultiplicativeExpr * UnaryExpr"))
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-5);
						
						tree = new ArithExpr((Expr)sub_tree2.getTree(), 3, (Expr)sub_tree1.getTree());								
					}
					else if(Grammer_rule.get(state_num).equals("MultiplicativeExpr -> MultiplicativeExpr / UnaryExpr"))
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-5);
						
						tree = new ArithExpr((Expr)sub_tree2.getTree(), 4, (Expr)sub_tree1.getTree());								
					}
					else if(Grammer_rule.get(state_num).equals("MultiplicativeExpr -> UnaryExpr"))
					{
						tree = stack.get(last_stack_tree_index-1);
						
						if(tree instanceof Nonterminal)
							tree = ((Nonterminal) tree).getTree();
					}
					else if(Grammer_rule.get(state_num).equals("UnaryExpr -> - Primary"))
					{
						Nonterminal temp = (Nonterminal) stack.get(last_stack_tree_index-1);
						tree = new ArithExpr(5, (Expr)temp.getTree());
					}
					else if(Grammer_rule.get(state_num).equals("UnaryExpr -> Primary"))
					{
						tree = stack.get(last_stack_tree_index-1);
						
						if(tree instanceof Nonterminal)
							tree = ((Nonterminal) tree).getTree();
					}
					else if(Grammer_rule.get(state_num).equals("Primary -> NUM"))
					{
						Terminal temp = (Terminal) stack.get(last_stack_tree_index-1);
						tree = new Lit(temp.getSyntax());
					}
					else if(Grammer_rule.get(state_num).equals("Primary -> STR"))
					{
						Terminal temp = (Terminal) stack.get(last_stack_tree_index-1);
						tree = new Lit(temp.getSyntax());
					}
					else if(Grammer_rule.get(state_num).equals("Primary -> ( Expr )"))
					{
						Nonterminal temp = (Nonterminal) stack.get(last_stack_tree_index-3);
						tree = (Expr) temp.getTree();
					}
					else if(Grammer_rule.get(state_num).equals("Primary -> ID"))
					{
						Terminal temp = (Terminal) stack.get(last_stack_tree_index-1);
						tree = new Var(temp.getSyntax());
					}
					else if(Grammer_rule.get(state_num).equals("Primary -> ID . ID"))
					{
						Terminal temp1 = (Terminal) stack.get(last_stack_tree_index-1);
						Terminal temp2 = (Terminal) stack.get(last_stack_tree_index-5);
						tree = new PropertyExpr(temp2.getSyntax(), temp1.getSyntax());
					}
					else if(Grammer_rule.get(state_num).equals("Primary -> ID . ID ( Exprs )"))
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-3);
						Terminal sub_tree2 = (Terminal)stack.get(last_stack_tree_index-7);
						Terminal sub_tree3 = (Terminal)stack.get(last_stack_tree_index-11);
		
						tree = new MethodCallExpr(sub_tree3.getSyntax(), sub_tree2.getSyntax(),(ArrayList<Expr>)sub_tree1.getTree());
					}
					else if(Grammer_rule.get(state_num).equals("Primary -> ID Idxs"))
					{
						Terminal sub_tree1 = (Terminal)stack.get(last_stack_tree_index-3);

						tree = new Array( sub_tree1.getSyntax(), index_list);
					}
					else if(Grammer_rule.get(state_num).equals("Idxs -> [ Expr ]"))
					{
						index_list = null;
						index_list = new ArrayList<Expr>();
						
						Nonterminal temp = (Nonterminal) stack.get(last_stack_tree_index-3);
						index_list.add(0, (Expr)temp.getTree() );
					}
					else if(Grammer_rule.get(state_num).equals("Idxs -> [ Expr ] Idxs"))
					{	
						Nonterminal temp = (Nonterminal) stack.get(last_stack_tree_index-5);
						index_list.add(0, (Expr)temp.getTree());				
					}
					else
					{
						// empty, Error Case.
					}
					
					while(count != 0)
					{
						stack.pop();
						stack.pop();
						count--;
					}
					
					
					current_state = (ParseState) stack.lastElement();
					
					stack.push(new Nonterminal(tree));
					stack.push(get_st(current_state, lhs));
					break;
				}
				case "LALRAccept":
					System.out.println("Accepted.");
					Tokens.remove(0);
					break;
			}
		}
	}
	
	
	private ParseState get_st(ParseState current_state, String index) throws FileNotFoundException
	{	
		int count = 0;

		String start_state;
		int location = 0;
		
		while(count < State_trans_table.size())
		{
			String[] st_tr_arr = State_trans_table.get(count).split("\t"); 
			while(location < st_tr_arr.length)
			{
				start_state = st_tr_arr[location];
				if(current_state.toString().equals(start_state))
				{
					location++;
					if(st_tr_arr[location].equals(index))
					{
						location++;
						if(!st_tr_arr[location].equals(""))
							return new ParseState(st_tr_arr[location]);
						else break;
					}	
					else break;
				}
				else break;
			}
			count++;
			location = 0;
		}	
		System.err.println("Expect Trans Table content is \"" + current_state.toString() + " " + index + " <Destination State>\"");
		System.err.println("but didn't found at Trans Table... Plz Check it");
		System.exit(0);
		return new ParseState("Error...");
	}

	private String Check_state(ParseState current_state, Terminal a)
	{
		int index = 0;
		while(index < Parsing_Table.size())
		{
			String Parsing_Table_String = Parsing_Table.get(index);
			String[] data = Parsing_Table_String.split("\t");
			
			if(current_state.toString().equals(data[0]))
			{
				int index1 = 1;
				Token index_Token = Token.NONE;
				
				while(data[index1].equals("") || data[index1].equals("\t"))
				{
					index1++;
					continue;
				}
				
				switch(data[index1])
				{
					case ".":
					index_Token = Token.DOT;
					break;
					case ",":
					index_Token = Token.COMMA;
					break;
					case ":":
					index_Token = Token.COLON;
					break;
					case "(":
					index_Token = Token.OPEN_PARA;
					break;
					case ")":
					index_Token = Token.CLOSE_PARA;
					break;
					case "{":
					index_Token = Token.OPEN_BRACE;
					break;
					case "}":
					index_Token = Token.CLOSE_BRACE;
					break;
					case "[":
					index_Token = Token.OPEN_BRAKET;
					break;
					case "]":
					index_Token = Token.CLOSE_BRAKET;
					break;
					case "+":
					index_Token = Token.PLUS;
					break;
					case "-":
					index_Token = Token.MINUS;
					break;
					case "*":
					index_Token = Token.MULTIPLY;
					break;
					case "/":
					index_Token = Token.DIVIDE;
					break;
					case "=":
					index_Token = Token.ASSIGN;
					break;
					case ">=":
					index_Token = Token.GREATER_EQUAL;
					break;
					case ">":
					index_Token = Token.GREATER_THAN;
					break;
					case "<":
					index_Token = Token.LESS_THAN;
					break;
					case "<=":
					index_Token = Token.LESS_EQUAL;
					break;
					case "<>":
					index_Token = Token.NOT_EQUAL;
					break;
					case "And":
					index_Token = Token.AND;
					break;
					case "Or":
					index_Token = Token.OR;
					break;
					case "ID":
					index_Token = Token.ID;
					break;
					case "While":
					index_Token = Token.WHILE;
					break;
					case "EndWhile":
					index_Token = Token.ENDWHILE;
					break;
					case "Goto":
					index_Token = Token.GOTO;
					break;
					case "For":
					index_Token = Token.FOR;
					break;
					case "To":
					index_Token = Token.TO;
					break;
					case "Step":
					index_Token = Token.STEP;
					break;
					case "EndFor":
					index_Token = Token.ENDFOR;
					break;
					case "Sub":
					index_Token = Token.SUB;
					break;
					case "EndSub":
					index_Token = Token.ENDSUB;
					break;
					case "If":
					index_Token = Token.IF;
					break;
					case "Then":
					index_Token = Token.THEN;
					break;
					case "Else":
					index_Token = Token.ELSE;
					break;
					case "ElseIf":
					index_Token = Token.ELSEIF;
					break;
					case "EndIf":
					index_Token = Token.ENDIF;
					break;
					case "CR":
					index_Token = Token.CR;
					break;
					case "$":
					index_Token = Token.NONE;
					break;
					case "STR":
					index_Token = Token.STR;					
					break;
					case "NUM":
					index_Token = Token.NUM;
					break;
					default:
					{
						System.err.println("Not Found Token.");
					}
				}
				
				if(a.getTokenInfo() == index_Token)
				{
					String return_string = new String();
					for(int i = index1 + 1; i < data.length; i++)
					{
						if(data[i].equals(""))
							continue;
						
						return_string = return_string.concat(" " + data[i]);
					}
					return return_string.trim();
				}
			}
			index++;
		}
		if(a.toString().equals("\n"))
			System.err.println("Expect Parsing table content is \"" + current_state.toString() + " CR"+ " <Shift/Reduce/Accecpt>\"");
		else if(a.getTokenInfo() == Token.STR)
			System.err.println("Expect Parsing table content is \"" + current_state.toString() + " " + a.getTokenInfo() + " <Shift/Reduce/Accecpt>\"");
		else
			System.err.println("Expect Parsing table content is \"" + current_state.toString() + " " + a.toString() + " <Shift/Reduce/Accecpt>\"");
		System.err.println("but didn't found at Parsing Table... Plz Check it");
		System.err.println("[" + a.getLine_index() + "," + a.getCh_index() + "]");
		System.exit(0);
		return "Dummy";
	}
	
	private int atoi(String a) // this method only use Parser, So set "private".
	{
		int result = 0;
		for(int i = 0; i < a.length(); i++)
		{
			result = result * 10;
			result = result + (a.charAt(i) - '0');
		}
		return result;	
	}
	
	private Stack<Stkelem> stack;
	private ArrayList<ArrayList<Terminal>> Lexing_result;
	private ArrayList<Terminal> Tokens;
	private ArrayList<Expr> index_list;
	private ArrayList<String> Parsing_Table;
	private ArrayList<String> State_trans_table;
	HashMap<Integer, String> Grammer_rule;
	private int final_line;
	
}
