package com.coducation.smallbasic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class Parser 
{
	Parser(LexerAnalyzer Lexer) throws IOException
	{
		stack = new Stack<Object>();
		Lexing_result = Lexer.Lexing();
		String temp;
		
		Tokens = new ArrayList<SyntaxPair>();
		Grammer_rule = new ArrayList<String>();
		Parsing_Table = new ArrayList<String>();
		State_trans_table = new ArrayList<String>();
		
		// initalize Grammer rule and Parsing Table and State trans table...
		FileReader Gr_fr = new FileReader("arith_grammer_rule.txt");
		FileReader Pt_fr = new FileReader("arith_parsing_table.txt");
		FileReader St_fr = new FileReader("arith_state_trans.txt");
		BufferedReader Gr_br = new BufferedReader(Gr_fr);
		BufferedReader Pt_br = new BufferedReader(Pt_fr);	
		BufferedReader St_br = new BufferedReader(St_fr);
		
		while((temp = Gr_br.readLine()) != null)
			Grammer_rule.add(temp);
		
		while((temp = Pt_br.readLine()) != null)
			Parsing_Table.add(temp);
		
		while((temp = St_br.readLine()) != null)
			State_trans_table.add(temp);
	}
	
	// Line Parser.
	// TODO : Later, Return type changes void to TreeMap And Parser Specify...
	public void Parsing(int line_index) throws FileNotFoundException
	{	
		// Initialize stack
		stack.clear();
		stack.push("[0]");
		
		// Initialize Tokens, <CR> Token ignore.(Temporary)
		int tok_index = 0;
		while(tok_index < Lexing_result.get(line_index).size())
		{
			if(Lexing_result.get(line_index).get(tok_index).getTokenInfo() != Token.CR)
			{
				Tokens.add(Lexing_result.get(line_index).get(tok_index));
				tok_index++;
			}
			else break;
		}

//		while(tok_index < Lexing_result.get(line_index).size())
//			Tokens.add(Lexing_result.get(line_index).get(tok_index++));
//		IF USE THIS CODE, OPERATION IS EQUAL, BUT MORE DIFFICULT TO UNDERSTANDING....
		
		System.out.println("==================================");
		
		// Main Parsing Routine.
		while(true)
		{
			
			boolean ParsingSuccessful = false; // if this boolean value is true, line parsing complete.
			String current_state = (String) stack.lastElement(); // set current_state [0]
			SyntaxPair a = new SyntaxPair();
			if(!Tokens.isEmpty())
				a = Tokens.get(0); // get first SyntaxPair (Only if Tokens isn't empty)
			else // if Tokens is empty, $ added(THIS IS NOT TOKEN.)
				a = new SyntaxPair("$", Token.NONE, -1, -1);
			
			if(a.getTokenInfo() == Token.END_OF_TOKENS) // if firstToken is END_OF_TOKENS, File Parsing Success.
			{
				System.out.println("Parsing Success." + " " + line_index + " lines.");
				return;
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
					stack.push(state);
					Tokens.remove(0);
					break;
				case "LALRReduce":
				{
					System.out.println(order + " " + state);
					
					int state_num = atoi(state);
				
					String[] reduce_arr = Grammer_rule.get(state_num).split("->"); // lhs, rhs split.
					String lhs = reduce_arr[0].trim();
					String rhs = reduce_arr[1].trim();
					
					int count = rhs.split(" ").length; // *2 => pop count.
	
					while(count != 0)
					{
						stack.pop();
						stack.pop();
						count--;
					}
					
					current_state = (String) stack.lastElement();
					
					stack.push(lhs);
					stack.push(get_st(current_state, lhs));
					break;
				}
				case "LALRAccept":
					System.out.println("Accepted.");
					ParsingSuccessful = true;
					break;
			}
			if(ParsingSuccessful == true) // Parsing Success. (line)
				break;
		}
	}
	
	
	public String get_st(String state, String index) throws FileNotFoundException
	{	
		int count = 0;

		String start_state;
		String key;
		int location = 0;
		
		while(count < State_trans_table.size())
		{
			String[] st_tr_arr = State_trans_table.get(count).split(" "); 
			while(location < st_tr_arr.length)
			{
				while(st_tr_arr[location].equals("") && location < st_tr_arr.length) location++;
				
				start_state = st_tr_arr[location];
				
				if(state.equals(start_state))
				{
					location++;
					while(st_tr_arr[location].equals("") && location < st_tr_arr.length) location++;
					
					if(st_tr_arr[location].equals(index))
					{
						location++;
						while(st_tr_arr[location].equals("") && location < st_tr_arr.length) location++;
						
						if(!st_tr_arr[location].equals(""))
							return st_tr_arr[location];
						else break;
					}	
					else break;
				}
				else break;
			}
			count++;
			location = 0;
		}	
		return "Error...";
	}

	public String Check_state(String current_state, SyntaxPair a)
	{
		int index = 0;
		while(index < Parsing_Table.size())
		{
			String Parsing_Table_String = Parsing_Table.get(index);
			String[] data = Parsing_Table_String.split(" ");
			
			if(current_state.equals(data[0]))
			{
				int index1 = 1;
				Token index_Token = Token.NONE;
				
				while(data[index1].equals(""))
				{
					index1++;
					continue;
				}
				
				switch(data[index1])
				{
					case "(":
					index_Token = Token.OPEN_PARA;
					break;
					case ")":
					index_Token = Token.CLOSE_PARA;
					break;
					case "+":
					index_Token = Token.PLUS;
					break;
					case "*":
					index_Token = Token.MULTIPLY;
					break;
					case "id":
					index_Token = Token.ID;
					break;
					case "$":
					index_Token = Token.NONE;
					break;
					default:
					index_Token = Token.NUM_LIT;
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
		return "Error";
	}
	
	private int atoi(String a) // this method only use Parser, So set "private".
	{
		int result = 0;
		for(int i = 0; i < a.length(); i++)
		{
			result = result + (a.charAt(i) - '0');
			result = result * (i+1);
		}
		return result;	
	}
	
	private Stack<Object> stack;
	private ArrayList<ArrayList<SyntaxPair>> Lexing_result;
	private ArrayList<SyntaxPair> Tokens;
	private ArrayList<String> Grammer_rule;
	private ArrayList<String> Parsing_Table;
	private ArrayList<String> State_trans_table;
	
}
