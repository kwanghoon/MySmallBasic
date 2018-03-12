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
	public Parser(LexerAnalyzer Lexer) throws IOException
	{
		stack = new Stack<Stkelem>();
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
		//System.out.println("==================================");
		
		// Main Parsing Routine.
		while(true)
		{
			ParseState current_state = (ParseState) stack.lastElement(); // set current_state [0]
			Terminal a = new Terminal();
			if(!Tokens.isEmpty())
				a = Tokens.get(0); // get first SyntaxPair (Only if Tokens isn't empty)
			
			if(a.getTokenInfo() == Token.END_OF_TOKENS) // if firstToken is END_OF_TOKENS, File Parsing Success.
			{
				// System.out.println("Parsing Success." + " " + (line_index-1) + " lines.");
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
					//System.out.println(order + " " + state);
					stack.push(a);
					stack.push(new ParseState(state));
					Tokens.remove(0);
					break;
				case "LALRReduce":
				{	
					//System.out.println(order + " " + state);
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
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-3); // Block
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-5); // CondExpr
						
						WhileStmt whileStmt = new WhileStmt((Expr)sub_tree2.getTree(), (Stmt)sub_tree1.getTree());
						Terminal whileKeyword = (Terminal)stack.get(last_stack_tree_index-7);
						whileStmt.at(whileKeyword.getLine_index(), whileKeyword.getCh_index());
						tree = whileStmt;
			
					}
					else if(Grammer_rule.get(state_num).equals("Stmt -> ID :")) // case Label
					{
						Terminal sub_tree1 = (Terminal)stack.get(last_stack_tree_index-3);
						Label label = new Label(sub_tree1.getSyntax().toUpperCase());
						label.at(sub_tree1.getLine_index(), sub_tree1.getCh_index());
						tree = label;
						
					}
					else if(Grammer_rule.get(state_num).equals("Stmt -> Goto ID")) // case Goto
					{
						Terminal sub_tree1 = (Terminal)stack.get(last_stack_tree_index-1);
						GotoStmt gotostmt = new GotoStmt(sub_tree1.getSyntax().toUpperCase());
						gotostmt.at(sub_tree1.getLine_index(), sub_tree1.getCh_index());
						tree = gotostmt;
					}
					else if(Grammer_rule.get(state_num).equals("Stmt -> For ID = Expr To Expr OptStep CRStmtCRs EndFor")) // case For
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-3);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-5);
						Nonterminal sub_tree3 = (Nonterminal)stack.get(last_stack_tree_index-7);
						Nonterminal sub_tree4 = (Nonterminal)stack.get(last_stack_tree_index-11);
						Terminal sub_tree5 = (Terminal)stack.get(last_stack_tree_index-15);
						
						Terminal sub_tree6 = (Terminal)stack.get(last_stack_tree_index-17);
						
						ForStmt forstmt = new ForStmt(
												new Var(sub_tree5.getSyntax().toUpperCase()), 
												(Expr)sub_tree4.getTree(), 
												(Expr)sub_tree3.getTree(), 
												(Expr)sub_tree2.getTree(), 
												(Stmt)sub_tree1.getTree());
						
						forstmt.at(sub_tree6.getLine_index(), sub_tree6.getCh_index());
						
						tree = forstmt;
					}
					else if(Grammer_rule.get(state_num).equals("Stmt -> Sub ID CRStmtCRs EndSub")) // case SubRoutine Definition.
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-3);
						Terminal sub_tree2 = (Terminal)stack.get(last_stack_tree_index-5);
						Terminal sub_tree3 = (Terminal)stack.get(last_stack_tree_index-7);
						
						SubDef subdef = new SubDef(sub_tree2.getSyntax().toUpperCase(), (Stmt)sub_tree1.getTree());
						subdef.at(sub_tree3.getLine_index(), sub_tree3.getCh_index());
						tree = subdef;
					}
					else if(Grammer_rule.get(state_num).equals("Stmt -> If Expr Then CRStmtCRs MoreThanZeroElseIf")) // case If
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-3);
						Nonterminal sub_tree3 = (Nonterminal)stack.get(last_stack_tree_index-7);
						Terminal ifKeyword = (Terminal)stack.get(last_stack_tree_index-9);
						
						IfStmt ifStmt = new IfStmt((Expr)sub_tree3.getTree(), (Stmt)sub_tree2.getTree(), (Stmt)sub_tree1.getTree());
						ifStmt.at(ifKeyword.getLine_index(), ifKeyword.getCh_index());
						tree = ifStmt; 
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
						Terminal sub_tree4 = (Terminal)stack.get(last_stack_tree_index-9);
						
						IfStmt ifstmt = new IfStmt((Expr)sub_tree3.getTree(), (Stmt)sub_tree2.getTree(), (Stmt)sub_tree1.getTree());
						ifstmt.at(sub_tree4.getLine_index(), sub_tree4.getCh_index());
						tree = ifstmt;
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
						
						Assign assign = new Assign(new Var(sub_tree2.getSyntax().toUpperCase()), (Expr)sub_tree1.getTree());	
						assign.at(sub_tree2.getLine_index(), sub_tree2.getCh_index());
						tree = assign;
					}
					else if(Grammer_rule.get(state_num).equals("ExprStatement -> ID . ID = Expr")) // Property Assign
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Terminal sub_tree2 = (Terminal)stack.get(last_stack_tree_index-5);
						Terminal sub_tree3 = (Terminal)stack.get(last_stack_tree_index-9);
						
						Assign assign = new Assign(new PropertyExpr(sub_tree3.getSyntax(), sub_tree2.getSyntax()), (Expr)sub_tree1.getTree());
						assign.at(sub_tree3.getLine_index(), sub_tree3.getCh_index());
						tree = assign;
					}
					else if(Grammer_rule.get(state_num).equals("ExprStatement -> ID . ID ( Exprs )")) // MethodCallExprStmt
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-3);
						Terminal sub_tree2 = (Terminal)stack.get(last_stack_tree_index-7);
						Terminal sub_tree3 = (Terminal)stack.get(last_stack_tree_index-11);
		
						ExprStmt exprstmt = new ExprStmt(new MethodCallExpr(sub_tree3.getSyntax(), sub_tree2.getSyntax(),(ArrayList<Expr>)sub_tree1.getTree()));
						exprstmt.at(sub_tree3.getLine_index(), sub_tree3.getCh_index());
						tree = exprstmt;
					}
					else if(Grammer_rule.get(state_num).equals("ExprStatement -> ID ( )")) // SubRoutineCallExprStmt
					{
						Terminal sub_tree1 = (Terminal)stack.get(last_stack_tree_index-5);
						SubCallExpr subcallexpr = new SubCallExpr(sub_tree1.getSyntax().toUpperCase());
						subcallexpr.at(sub_tree1.getLine_index(), sub_tree1.getCh_index());
						tree = subcallexpr;
					}
					else if(Grammer_rule.get(state_num).equals("ExprStatement -> ID Idxs = Expr")) // Array Assign
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Terminal sub_tree2 = (Terminal)stack.get(last_stack_tree_index-7);
						
						Nonterminal sub_tree_idxs = (Nonterminal)stack.get(last_stack_tree_index-5);
						
						//Array array = new Array( sub_tree2.getSyntax(), index_list);
						Array array = (Array)sub_tree_idxs.getTree();
						array.setVar( sub_tree2.getSyntax().toUpperCase() );
						
						Assign assign =  new Assign((Expr)array, (Expr)sub_tree1.getTree());
						assign.at(sub_tree2.getLine_index(), sub_tree2.getCh_index());
						tree = assign;
					}
					else if(Grammer_rule.get(state_num).equals("CRStmtCRs -> CR TheRest")) // CRStmt => blockStmt
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						
						ArrayList<Stmt> ALStmt = (ArrayList<Stmt>)sub_tree1.getTree();
						if (ALStmt == null) {
							ALStmt = new ArrayList<Stmt>();
							tree = new BlockStmt(ALStmt);  // No line no & char pos because of the empty block
						}
						else {
							BlockStmt blockstmt = new BlockStmt(ALStmt);
							Stmt fststmt = ALStmt.get(0);
							blockstmt.at(fststmt.lineno(), fststmt.charat());
							tree = blockstmt;
						}
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
						Stmt fststmt = (Stmt)sub_tree1.getTree();
						ALStmt.add(fststmt);
						BlockStmt blockstmt = new BlockStmt(ALStmt);
						blockstmt.at(fststmt.lineno(), fststmt.charat());
						tree = blockstmt;
					}
					else if(Grammer_rule.get(state_num).equals("MoreThanOneStmt -> Stmt CR MoreThanOneStmt"))
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-5);
						
						BlockStmt block = (BlockStmt)sub_tree1.getTree();
						ArrayList<Stmt> ALStmt = block.getAL();
						Stmt fststmt = (Stmt)sub_tree2.getTree();
						ALStmt.add(0, fststmt);

						BlockStmt blockstmt = new BlockStmt(ALStmt);
						blockstmt.at(fststmt.lineno(), fststmt.charat());
						tree = blockstmt;
					}
					else if(Grammer_rule.get(state_num).equals("OptStep ->"))
					{
						tree = new Lit(1);
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
						
						Expr leftexpr = (Expr)sub_tree2.getTree();
						Expr rightexpr = (Expr)sub_tree1.getTree();
						LogicalExpr logicalexpr = new LogicalExpr(leftexpr, 2, rightexpr);
						logicalexpr.at(leftexpr.lineno(), leftexpr.charat());
						tree = logicalexpr;
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
						
						Expr leftexpr = (Expr)sub_tree2.getTree();
						Expr rightexpr = (Expr)sub_tree1.getTree();
						LogicalExpr logicalexpr = new LogicalExpr(leftexpr, 1, rightexpr);
						logicalexpr.at(leftexpr.lineno(), leftexpr.charat());
						tree = logicalexpr;
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
						
						Expr leftexpr = (Expr)sub_tree2.getTree();
						Expr rightexpr = (Expr)sub_tree1.getTree();
						CompExpr compexpr = new CompExpr(leftexpr, 5, rightexpr);
						compexpr.at(leftexpr.lineno(), leftexpr.charat());
						tree = compexpr;
					}
					else if(Grammer_rule.get(state_num).equals("EqNeqExpr -> EqNeqExpr <> CompExpr"))
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-5);
						
						Expr leftexpr = (Expr)sub_tree2.getTree();
						Expr rightexpr = (Expr)sub_tree1.getTree();
						CompExpr compexpr = new CompExpr(leftexpr, 6, rightexpr);
						compexpr.at(leftexpr.lineno(), leftexpr.charat());
						tree = compexpr;
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
						
						Expr leftexpr = (Expr)sub_tree2.getTree();
						Expr rightexpr = (Expr)sub_tree1.getTree();
						CompExpr compexpr = new CompExpr(leftexpr, 1, rightexpr);
						compexpr.at(leftexpr.lineno(), leftexpr.charat());
						tree = compexpr;
					}
					else if(Grammer_rule.get(state_num).equals("CompExpr -> CompExpr <= AdditiveExpr"))
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-5);
						
						Expr leftexpr = (Expr)sub_tree2.getTree();
						Expr rightexpr = (Expr)sub_tree1.getTree();
						CompExpr compexpr = new CompExpr(leftexpr, 2, rightexpr);
						compexpr.at(leftexpr.lineno(), leftexpr.charat());
						tree = compexpr;
					}
					else if(Grammer_rule.get(state_num).equals("CompExpr -> CompExpr > AdditiveExpr"))
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-5);
						
						Expr leftexpr = (Expr)sub_tree2.getTree();
						Expr rightexpr = (Expr)sub_tree1.getTree();
						CompExpr compexpr = new CompExpr(leftexpr, 3, rightexpr);
						compexpr.at(leftexpr.lineno(), leftexpr.charat());
						tree = compexpr;
					}
					else if(Grammer_rule.get(state_num).equals("CompExpr -> CompExpr >= AdditiveExpr"))
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-5);
						
						Expr leftexpr = (Expr)sub_tree2.getTree();
						Expr rightexpr = (Expr)sub_tree1.getTree();
						CompExpr compexpr = new CompExpr(leftexpr, 4, rightexpr);
						compexpr.at(leftexpr.lineno(), leftexpr.charat());
						tree = compexpr;
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
						
						Expr leftexpr = (Expr)sub_tree2.getTree();
						Expr rightexpr = (Expr)sub_tree1.getTree();
						ArithExpr arithexpr = new ArithExpr(leftexpr, 1, rightexpr);
						arithexpr.at(leftexpr.lineno(), leftexpr.charat());
						tree = arithexpr;
					}
					else if(Grammer_rule.get(state_num).equals("AdditiveExpr -> AdditiveExpr - MultiplicativeExpr"))
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-5);
						
						Expr leftexpr = (Expr)sub_tree2.getTree();
						Expr rightexpr = (Expr)sub_tree1.getTree();
						ArithExpr arithexpr = new ArithExpr(leftexpr, 2, rightexpr);
						arithexpr.at(leftexpr.lineno(), leftexpr.charat());
						tree = arithexpr;
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
						
						Expr leftexpr = (Expr)sub_tree2.getTree();
						Expr rightexpr = (Expr)sub_tree1.getTree();
						ArithExpr arithexpr = new ArithExpr((Expr)sub_tree2.getTree(), 3, (Expr)sub_tree1.getTree());	
						arithexpr.at(leftexpr.lineno(), leftexpr.charat());
						tree = arithexpr;
					}
					else if(Grammer_rule.get(state_num).equals("MultiplicativeExpr -> MultiplicativeExpr / UnaryExpr"))
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-1);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-5);
						
						Expr leftexpr = (Expr)sub_tree2.getTree();
						Expr rightexpr = (Expr)sub_tree1.getTree();
						ArithExpr arithexpr = new ArithExpr(leftexpr, 4, rightexpr);	
						arithexpr.at(leftexpr.lineno(), leftexpr.charat());
						tree = arithexpr;
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
						Terminal minus = (Terminal) stack.get(last_stack_tree_index-3);
						Expr expr = (Expr)temp.getTree();
						ArithExpr arithexpr = new ArithExpr(5, expr);
						arithexpr.at(minus.getLine_index(), minus.getCh_index());
						tree = arithexpr;
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
						Lit lit = new Lit(temp.getSyntax(), Lit.NUM);
						lit.at(temp.getLine_index(), temp.getCh_index());
						tree = lit;
					}
					else if(Grammer_rule.get(state_num).equals("Primary -> STR"))
					{
						Terminal temp = (Terminal) stack.get(last_stack_tree_index-1);
						String str = temp.getSyntax();
						Lit lit = new Lit(str.substring(1, str.length()-1), Lit.STRING);
						lit.at(temp.getLine_index(), temp.getCh_index());
						tree = lit;
					}
					else if(Grammer_rule.get(state_num).equals("Primary -> ( Expr )"))
					{
						Nonterminal temp = (Nonterminal) stack.get(last_stack_tree_index-3);
						Terminal openparen = (Terminal) stack.get(last_stack_tree_index-5);
						ParenExpr parenexpr = new ParenExpr ((Expr) temp.getTree());
						parenexpr.at(openparen.getLine_index(), openparen.getCh_index());
						tree = parenexpr;
					}
					else if(Grammer_rule.get(state_num).equals("Primary -> ID"))
					{
						Terminal temp = (Terminal) stack.get(last_stack_tree_index-1);
						Var var = new Var(temp.getSyntax().toUpperCase());
						var.at(temp.getLine_index(), temp.getCh_index());
						tree = var;
					}
					else if(Grammer_rule.get(state_num).equals("Primary -> ID . ID"))
					{
						Terminal temp1 = (Terminal) stack.get(last_stack_tree_index-1);
						Terminal temp2 = (Terminal) stack.get(last_stack_tree_index-5);
						
						PropertyExpr propertyexpr = new PropertyExpr(temp2.getSyntax(), temp1.getSyntax());
						propertyexpr.at(temp2.getLine_index(), temp2.getCh_index());
						tree = propertyexpr;
					}
					else if(Grammer_rule.get(state_num).equals("Primary -> ID . ID ( Exprs )"))
					{
						Nonterminal sub_tree1 = (Nonterminal)stack.get(last_stack_tree_index-3);
						Terminal sub_tree2 = (Terminal)stack.get(last_stack_tree_index-7);
						Terminal sub_tree3 = (Terminal)stack.get(last_stack_tree_index-11);
		
						MethodCallExpr  methodcallexpr = new MethodCallExpr(
								sub_tree3.getSyntax(), sub_tree2.getSyntax(),(ArrayList<Expr>)sub_tree1.getTree());
						methodcallexpr.at(sub_tree3.getLine_index(), sub_tree3.getCh_index());
						tree = methodcallexpr;
					}
					else if(Grammer_rule.get(state_num).equals("Primary -> ID Idxs"))
					{
						Terminal sub_tree1 = (Terminal)stack.get(last_stack_tree_index-3);
						Nonterminal sub_tree2 = (Nonterminal)stack.get(last_stack_tree_index-1);

						//Array array = new Array( sub_tree1.getSyntax(), index_list);
						Array array = (Array)sub_tree2.getTree();
						array.setVar(sub_tree1.getSyntax().toUpperCase());
						array.at(sub_tree1.getLine_index(), sub_tree1.getCh_index());
						tree = array;
					}
					else if(Grammer_rule.get(state_num).equals("Idxs -> [ Expr ]"))
					{
//						index_list = null;
//						index_list = new ArrayList<Expr>();
											
						Nonterminal temp = (Nonterminal) stack.get(last_stack_tree_index-3);
//						index_list.add(0, (Expr)temp.getTree() );
						
						ArrayList<Expr> list = new ArrayList<Expr>();
						list.add((Expr)temp.getTree());
						Array array = new Array(null,  list);
						tree = array;
					}
					else if(Grammer_rule.get(state_num).equals("Idxs -> [ Expr ] Idxs"))
					{	
						Nonterminal temp_Idxs = (Nonterminal) stack.get(last_stack_tree_index-1);
						
						Nonterminal temp = (Nonterminal) stack.get(last_stack_tree_index-5);
//						index_list.add(0, (Expr)temp.getTree());
						
						Array incompleteArr = (Array)temp_Idxs.getTree();
						incompleteArr.indices().add(0, (Expr)temp.getTree());
						tree = incompleteArr;
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
					// System.out.println("Accepted.");
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
		StringBuilder sb = new StringBuilder();
		
		sb.append("Expect Trans Table content is \"" + current_state.toString() + " " + index + " <Destination State>\"");
		sb.append("\n");
		sb.append("but didn't found at Trans Table... Plz Check it");
		sb.append("\n");
		
		throw new ParserException("Line : Char : " + "Parsing error (state not found)", sb.toString());
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
		StringBuilder sb = new StringBuilder();
		
		if(a.toString().equals("\n")) {
			sb.append("Expect Parsing table content is \"" + current_state.toString() + " CR"+ " <Shift/Reduce/Accecpt>\"");
			sb.append("\n");
		}
		else if(a.getTokenInfo() == Token.STR) {
			sb.append("Expect Parsing table content is \"" + current_state.toString() + " " + a.getTokenInfo() + " <Shift/Reduce/Accecpt>\"");
			sb.append("\n");
		}
		else {
			sb.append("Expect Parsing table content is \"" + current_state.toString() + " " + a.toString() + " <Shift/Reduce/Accecpt>\"");
			sb.append("\n");
		}
		
		sb.append("but didn't found at Parsing Table... Plz Check it");
		sb.append("\n");
		sb.append("[" + a.getLine_index() + "," + a.getCh_index() + "]");
		sb.append("\n");
		
		throw new ParserException("Line " + a.getLine_index() + " : Char " + a.getCh_index() + " : " + "Parsing error", sb.toString());
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